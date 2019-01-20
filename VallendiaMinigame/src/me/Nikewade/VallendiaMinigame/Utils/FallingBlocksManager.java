package me.Nikewade.VallendiaMinigame.Utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class FallingBlocksManager implements Listener {

    private static FallingBlocksManager instance;
    private JavaPlugin pluginInstance;
    private ArrayList<FallingBlockSet> fallingBlockSets = new ArrayList<>();

    public FallingBlocksManager(JavaPlugin pluginInstance) {
        if (instance != null) return;
        instance = this;
        this.pluginInstance = pluginInstance;
        pluginInstance.getServer().getPluginManager().registerEvents(this, pluginInstance);
        initRegenerateBlocksTask();
    }

    public static FallingBlocksManager getInstance() {
        return instance;
    }

    private void initRegenerateBlocksTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<FallingBlockSet> setsToRemove = new ArrayList<>();
                for (FallingBlockSet fallingBlockSet : fallingBlockSets) {
                    if (fallingBlockSet.getDurationRemaining() <= 0) {
                        setsToRemove.add(fallingBlockSet);
                        continue;
                    }
                    fallingBlockSet.setDurationRemaining(fallingBlockSet.getDurationRemaining() - 0.25D);
                }
                for (FallingBlockSet fallingBlockSet : setsToRemove) {
                    regenerateFallingBlockSet(fallingBlockSet);
                    fallingBlockSets.remove(fallingBlockSet);
                }
            }
        }.runTaskTimer(pluginInstance, 5, 5);
    }

    public void regenerateFallingBlockSet(FallingBlockSet fallingBlockSet) {
        for (Entity entity : fallingBlockSet.getFallingBlocks())
            entity.remove();
        for (Location location : fallingBlockSet.getFallingBlocksLand()) {
            location.getBlock().setType(Material.AIR);
            if (location.getBlock().hasMetadata("FallingBlockMeta"))
                location.getBlock().removeMetadata("FallingBlockMeta", pluginInstance);
        }
        for (Location location : fallingBlockSet.getInitialBlocksData().keySet()) {
            Block block = location.getBlock();
            if (block == null) continue;
            ItemStack blockItem = fallingBlockSet.getInitialBlocksData().get(location);
            block.setType(blockItem.getType());
            block.setData((byte) blockItem.getDurability());
            if (block.hasMetadata("FallingBlockMeta"))
                block.removeMetadata("FallingBlockMeta", pluginInstance);
        }
    }

    public FallingBlockSet createFallingBlockSet(ArrayList<Location> blocks, double blocksDuration, Vector velocity) {
        final HashMap<Location, ItemStack> initialBlocksData = new HashMap<>();
        final ArrayList<Entity> fallingBlocks = new ArrayList<>();
        ArrayList<Location> metadataExceptions = new ArrayList<>();
        for (Location location : blocks) {
            Block block = location.getBlock();
            if (block == null || block.getType() == Material.AIR) {
                metadataExceptions.add(location);
                continue;
            }
            if (!block.hasMetadata("FallingBlockMeta")) {
                ItemStack blockItem = new ItemStack(block.getType(), 1, (short) block.getState().getData().getData());
                initialBlocksData.put(location, blockItem);
            } else
                metadataExceptions.add(location);
            if (!block.getType().isSolid()) {
                block.setType(Material.AIR);
                continue;
            }
            FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), block.getState().getData());
            fallingBlock.setDropItem(false);
            block.setType(Material.AIR);

            fallingBlock.setVelocity(velocity);
            fallingBlocks.add(fallingBlock);
        }

        FallingBlockSet fallingBlockSet = new FallingBlockSet(initialBlocksData, fallingBlocks, blocksDuration);
        for (Location location : blocks)
            if (!metadataExceptions.contains(location))
                location.getBlock().setMetadata("FallingBlockMeta", getNewFallingBlockSetMetadata(fallingBlockSet));
        for (Entity entity : fallingBlockSet.getFallingBlocks())
            entity.setMetadata("FallingBlockMeta", getNewFallingBlockSetMetadata(fallingBlockSet));
        fallingBlockSets.add(fallingBlockSet);
        return fallingBlockSet;
    }

    public ArrayList<Location> createSphereShape(Location center, int radius) {
        final double finalRadius = radius + 0.5;
        final double blockX = center.getBlockX() + 0.5;
        final double blockY = center.getBlockY() + 0.5;
        final double blockZ = center.getBlockZ() + 0.5;

        ArrayList<Location> sphereBlocks = new ArrayList<>();
        for (double x = blockX - finalRadius; x <= blockX + finalRadius; x++)
            for (double y = blockY - finalRadius; y <= blockY + finalRadius; ++y)
                for (double z = blockZ - finalRadius; z <= blockZ + finalRadius; ++z) {
                    final double distanceSq = lengthSq(blockX - x, blockY - y, blockZ - z);
                    if (!(distanceSq < finalRadius * finalRadius)) continue;
                    sphereBlocks.add(new Location(center.getWorld(), x, y, z));
                }
        return sphereBlocks;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof FallingBlock)) return;
        if (!entity.hasMetadata("FallingBlockMeta")) return;
        MetadataValue metadataValue = entity.getMetadata("FallingBlockMeta").get(0);
        if (!(metadataValue.value() instanceof FallingBlockSet)) return;
        if (event.getBlock().hasMetadata("FallingBlockMeta")) return;

        FallingBlockSet fallingBlockSet = (FallingBlockSet) metadataValue.value();
        for (FallingBlockSet otherFallingBlockSet : fallingBlockSets)
            if (otherFallingBlockSet.equals(fallingBlockSet)) {
                otherFallingBlockSet.getFallingBlocksLand().add(event.getBlock().getLocation());
                event.getBlock().setMetadata("FallingBlockMeta", getNewFallingBlockSetMetadata(fallingBlockSet));
                return;
            }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPluginDisable(PluginDisableEvent event) {
        if (!event.getPlugin().getName().equals(pluginInstance.getName()))
            return;
        for (FallingBlockSet fallingBlockSet : fallingBlockSets)
            regenerateFallingBlockSet(fallingBlockSet);
    }

    private double lengthSq(double x, double y, double z) {
        return (x * x) + (y * y) + (z * z);
    }

    private MetadataValue getNewFallingBlockSetMetadata(FallingBlockSet fallingBlockSet) {
        return new MetadataValue() {
            @Override
            public Object value() {
                return fallingBlockSet;
            }

            @Override
            public int asInt() {
                return 0;
            }

            @Override
            public float asFloat() {
                return 0;
            }

            @Override
            public double asDouble() {
                return 0;
            }

            @Override
            public long asLong() {
                return 0;
            }

            @Override
            public short asShort() {
                return 0;
            }

            @Override
            public byte asByte() {
                return 0;
            }

            @Override
            public boolean asBoolean() {
                return false;
            }

            @Override
            public String asString() {
                return null;
            }

            @Override
            public Plugin getOwningPlugin() {
                return pluginInstance;
            }

            @Override
            public void invalidate() {

            }
        };
    }

}
