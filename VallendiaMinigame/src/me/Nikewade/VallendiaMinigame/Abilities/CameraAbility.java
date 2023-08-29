package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.event.NPCDamageEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Equipment.EquipmentSlot;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.TileEntitySkull;

public class CameraAbility implements Ability, Listener{
	HashMap<String, NPC> npcs = new HashMap<>();
	HashMap<String, Block> blocks = new HashMap<>();
	HashMap<Block, Player> players = new HashMap<>();
	HashMap<String, Location> locations = new HashMap<>();
	HashMap<Player, BukkitTask> timers = new HashMap<>();
	ArrayList<Player> viewing = new ArrayList<>();		

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Camera";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return  Arrays.asList("place a camera");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Utils.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQ0MjJhODJjODk5YTljMTQ1NDM4NGQzMmNjNTRjNGFlN2ExYzRkNzI0MzBlNmU0NDZkNTNiOGIzODVlMzMwIn19fQ=="));
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		openCameraInv(p);
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		String uuid = p.getUniqueId().toString();
		
		if(viewing.contains(p))
		{
			NPC npc = npcs.get(uuid);
			viewing.remove(p);
			p.teleport(npc.getEntity().getLocation());
			p.setGameMode(GameMode.SURVIVAL);
			npcs.remove(p.getUniqueId().toString());
			npc.destroy();
			
		}
		
		if(timers.containsKey(p))
		{
			timers.get(p).cancel();
			timers.remove(p);
		}
		
		if(blocks.containsKey(uuid))
		{
			Block b = blocks.get(uuid);
			Utils.removeNonRegenBlock(b.getLocation());
			b.setType(Material.AIR);
			if(players.containsKey(b))
			{
				players.remove(b);
			}
			blocks.remove(uuid);
		}
		if(locations.containsKey(uuid))
		{
			locations.remove(uuid);
		}
				
	}
	
	
	public void openCameraInv(Player p)
	{

		AdvInventory cameraInv = new AdvInventory(Utils.Colorate("&8&lCamera"), 27, Utils.placeholder((byte) 7, " "));


		  cameraInv.setItem(new ItemStack(Material.EYE_OF_ENDER), Utils.Colorate("&3&lView Camera"), 10, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	onViewCamera(ep);
			    	p.closeInventory();
			    }
			});
		  cameraInv.setItem(new ItemStack(Utils.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQ0MjJhODJjODk5YTljMTQ1NDM4NGQzMmNjNTRjNGFlN2ExYzRkNzI0MzBlNmU0NDZkNTNiOGIzODVlMzMwIn19fQ==")), 
				  Utils.Colorate("&8&lPlace Camera"), 13, new ClickRunnable() {
  
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	onPlaceCamera(ep);
			    	p.closeInventory();
			    }
			});
		  
		  cameraInv.setItem(new ItemStack(Material.BARRIER), Utils.Colorate("&4&lRemove Camera"), 16, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	onRemoveCamera(ep);
			    	p.closeInventory();
			    }
			});
		  

		  cameraInv.openInventory(p);
		
	}
	
	@EventHandler
	public void onSneak (PlayerToggleSneakEvent e)
	{
		if(viewing.contains(e.getPlayer()))
		{
				Player p = e.getPlayer();
				NPC npc = npcs.get(p.getUniqueId().toString());
				Block block = blocks.get(p.getUniqueId().toString());		
				
				if(block.getType() == Material.SKULL)
				{
					p.sendBlockChange(block.getLocation() ,setSkullUrl("http://textures.minecraft.net/texture/8e5aa048cdda7d7bc7dddd59fe43e0c6c7d5567d14518ee54a722d8a59198", block).getType(), (byte) 1);
				}
				
				viewing.remove(p);
				p.teleport(npc.getEntity().getLocation());
				npc.destroy();
				npcs.remove(p.getUniqueId().toString());
				p.setGameMode(GameMode.SURVIVAL);
			
		}
	}
	
	public void onPlaceCamera(Player p)
	{
		if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Camera"))
		{
			return;
		}
		
		
		if(locations.containsKey(p.getUniqueId().toString()))
		{
			Language.sendAbilityUseMessage(p, "You already have a camera placed!", "Camera");
			return;
		}
		
    	Location camLoc = p.getLocation().add(0,1,0);
    	
    	if(locations.containsValue(camLoc))
    	{
			Language.sendAbilityUseMessage(p, "You can't place a camera here!", "Camera");
			return;
    	}
    	
		if(camLoc.getBlock().getType() != Material.AIR)
		{
			Language.sendAbilityUseMessage(p, "You can't place a camera here!", "Camera");
			return;
		}
		    	
		locations.put(p.getUniqueId().toString(), p.getLocation());
		Utils.makeNonRegenBlock(camLoc);		
    	Utils.setSkullUrl("http://textures.minecraft.net/texture/8e5aa048cdda7d7bc7dddd59fe43e0c6c7d5567d14518ee54a722d8a59198", camLoc.getBlock());
    	Block block = camLoc.getBlock();
    	blocks.put(p.getUniqueId().toString(), block);
    	players.put(block, p);
    	Skull skull = (Skull) block.getState();
    	skull.setRotation(getDirection(p));
    	skull.update();		
    	Language.sendAbilityUseMessage(p, "Your Camera has been placed.", "Camera");
    	
		AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), this.getName(), 
				VallendiaMinigame.getInstance().abilitymanager.getCooldown(this.getName(), p), 
				AbilityManager.locateAbilityItem(p, this.getName()));
		c.start();
    	
    	BukkitTask timer = new BukkitRunnable()
    			{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						if(block.getType() != Material.SKULL)
						{
							if(players.containsKey(block))
							{
								Block b = block;
								Player p = players.get(b);
								String uuid = p.getUniqueId().toString();
								
								Language.sendAbilityUseMessage(p, "Your Camera has been destroyed!", "Camera");
								AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), "Camera", 
										VallendiaMinigame.getInstance().abilitymanager.getCooldown("Camera", p), 
										AbilityManager.locateAbilityItem(p, "Camera"));
								c.start();
								if(locations.containsKey(uuid))
								{
									locations.remove(uuid);
								}
								if(blocks.containsKey(uuid))
								{
									blocks.remove(uuid);
								}
								
								if(viewing.contains(p))
								{
									NPC npc = npcs.get(uuid);
									viewing.remove(p);
									p.teleport(npc.getEntity().getLocation());
									p.setGameMode(GameMode.SURVIVAL);
									npcs.remove(p.getUniqueId().toString());
									npc.destroy();
								}

							}
							
							this.cancel();
						}
						
					}
    		
    			}.runTaskTimer(VallendiaMinigame.getInstance(), 1, 40);
    			
    			timers.put(p, timer);
	}
	
	public void onViewCamera(Player p)
	{
		if(!locations.containsKey(p.getUniqueId().toString()))
		{
			Language.sendAbilityUseMessage(p, "You do not have a camera!", this.getName());
			return;
		}
		AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), "Camera", 
				VallendiaMinigame.getInstance().abilitymanager.getCooldown("Camera", p), 
				AbilityManager.locateAbilityItem(p, "Camera"));
		c.start();
		NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, p.getName());
		npc.setProtected(false);
		npc.getTrait(Equipment.class).set(EquipmentSlot.BOOTS, p.getInventory().getBoots());
		npc.getTrait(Equipment.class).set(EquipmentSlot.LEGGINGS, p.getInventory().getLeggings());
		npc.getTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE, p.getInventory().getChestplate());
		npc.getTrait(Equipment.class).set(EquipmentSlot.HELMET, p.getInventory().getHelmet());		
		npc.spawn(p.getLocation());
		npcs.put(p.getUniqueId().toString(), npc);			
		p.setGameMode(GameMode.SPECTATOR);	
		viewing.add(p);
		p.teleport(locations.get(p.getUniqueId().toString()));
		p.sendBlockChange(blocks.get(p.getUniqueId().toString()).getLocation(), Material.AIR, (byte) 1);

		
	}
	
	public void onRemoveCamera(Player p)
	{
		if(blocks.containsKey(p.getUniqueId().toString()))
		{
			if(timers.containsKey(p))
			{
				timers.get(p).cancel();
				timers.remove(p);
			}
			Block block = blocks.get(p.getUniqueId().toString());
			Utils.removeNonRegenBlock(block.getLocation());
			if(block.getType() == Material.SKULL)
			{
				block.setType(Material.AIR);
				players.remove(block);
				blocks.remove(p.getUniqueId().toString());
				locations.remove(p.getUniqueId().toString());
				Language.sendAbilityUseMessage(p, "You remove your Camera.", "Camera");
				AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), this.getName(), 
						VallendiaMinigame.getInstance().abilitymanager.getCooldown(this.getName(), p), 
						AbilityManager.locateAbilityItem(p, this.getName()));
				c.start();
			}
		}else
		{
			Language.sendAbilityUseMessage(p, "You don't have a Camera.", "Camera");
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBreak (BlockBreakEvent e)
	{
		if(players.containsKey(e.getBlock()))
		{
			Block b = e.getBlock();
			Player p = players.get(b);
			String uuid = p.getUniqueId().toString();
			e.setCancelled(true);
			players.remove(e.getBlock());
			if(timers.containsKey(p))
			{
				timers.get(p).cancel();
				timers.remove(p);
			}
			Utils.removeNonRegenBlock(b.getLocation());
			
			Language.sendAbilityUseMessage(p, "Your Camera has been destroyed!", "Camera");
			AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), this.getName(), 
					VallendiaMinigame.getInstance().abilitymanager.getCooldown(this.getName(), p), 
					AbilityManager.locateAbilityItem(p, this.getName()));
			c.start();
			b.setType(Material.AIR);
			if(locations.containsKey(uuid))
			{
				locations.remove(uuid);
			}
			if(blocks.containsKey(uuid))
			{
				blocks.remove(uuid);
			}
			
			if(viewing.contains(p))
			{
				NPC npc = npcs.get(uuid);
				viewing.remove(p);
				p.teleport(npc.getEntity().getLocation());
				p.setGameMode(GameMode.SURVIVAL);
				npcs.remove(p.getUniqueId().toString());
				npc.destroy();
			}

		}

	}
	

	
	@EventHandler
	public void onNPCDamage (NPCDamageByEntityEvent e)
	{

		if(npcs.containsValue(e.getNPC()))
		{		
			
			NPC npc = e.getNPC();
			LivingEntity damager = null;
			Player p = Bukkit.getPlayer(npc.getName());		
			Block block = blocks.get(p.getUniqueId().toString());	
			double damage = e.getDamage();
			e.setDamage(1);
			if(block.getType() == Material.SKULL)
			{
				p.sendBlockChange(block.getLocation() ,setSkullUrl("http://textures.minecraft.net/texture/8e5aa048cdda7d7bc7dddd59fe43e0c6c7d5567d14518ee54a722d8a59198", block).getType(), (byte) 1);
			}
			if(e.getDamager() instanceof Projectile)
			{
				Projectile arrow = (Projectile) e.getDamager();
				damager = (LivingEntity) arrow.getShooter();		
			}else
			{
				damager = (LivingEntity) e.getDamager();
			}
			
			if(viewing.contains(p))
			{
				viewing.remove(p);
				p.teleport(npc.getEntity().getLocation());
				p.setGameMode(GameMode.SURVIVAL);
				AbilityUtils.damageEntity(p, damager, damage);
			}
			npcs.remove(p.getUniqueId().toString());
			npc.destroy();
			
		}
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		if(viewing.contains(e.getPlayer())){
		if(e.getTo().getY() >= e.getFrom().getY()){
			e.setCancelled(true);
			}
		if(e.getFrom().getY() != e.getTo().getY())
		{
			e.setCancelled(true);
		}
		}
	}
	

    public BlockFace getDirection (Player p) {

        double rotation = p.getLocation().getYaw() - 180;
        if (rotation < 0) {
            rotation += 360.0;
        }

        if (0 <= rotation && rotation < 22.5) {
        	return BlockFace.NORTH;
        }
        if (22.5 <= rotation && rotation < 67.5) {
            return BlockFace.NORTH_EAST;
        }
        if (67.5 <= rotation && rotation < 112.5) {
        	return BlockFace.EAST;
        }
        if (112.5 <= rotation && rotation < 157.5) {
        	return BlockFace.SOUTH_EAST;
        }
        if (157.5 <= rotation && rotation < 202.5) {
        	return BlockFace.SOUTH;
        }
        if (202.5 <= rotation && rotation < 247.5) {
        	return BlockFace.SOUTH_WEST;
        }
        if (247.5 <= rotation && rotation < 292.5) {
        	return BlockFace.WEST;
        }
        if (292.5 <= rotation && rotation < 337.5) {
        	return BlockFace.NORTH_WEST;
        }
        if (337.5 <= rotation && rotation <= 360) {
        	return BlockFace.NORTH;
        }
        return null;
    }
    
	public Block setSkullUrl(String skinUrl, Block block) {
	    Skull skullData = (Skull)block.getState();
	    skullData.setSkullType(SkullType.PLAYER);
	 
	    TileEntitySkull skullTile = (TileEntitySkull)((CraftWorld)block.getWorld()).getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
	    skullTile.setGameProfile(getNonPlayerProfile(skinUrl));
	    block.getState().update(true);
	    return block;
	}
	
	public GameProfile getNonPlayerProfile(String skinURL) {
	    GameProfile newSkinProfile = new GameProfile(UUID.randomUUID(), null);
	    newSkinProfile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString("{textures:{SKIN:{url:\"" + skinURL + "\"}}}")));
	    return newSkinProfile;
	}
	
	@EventHandler 
	public void onSwitchSlot (PlayerItemHeldEvent e)
	{
		Player p = e.getPlayer();
		
		if(viewing.contains(p))
		{
			e.setCancelled(true);
		}
		
	}
	
	
    @EventHandler
    public void onTeleport(PlayerTeleportEvent e)
    {
    	Player p = e.getPlayer();
    	
        if (e.getCause().equals(TeleportCause.SPECTATE) && viewing.contains(p))
        {
            e.setCancelled(true);
            e.getPlayer().setSpectatorTarget(null);
        }
    }

    
    @EventHandler
    public void onInteract (PlayerInteractEvent e)
    {
    	if(viewing.contains(e.getPlayer()))
    	{
    		e.setCancelled(true);
    	}
    }
    
    @EventHandler
    public void onInteract (PlayerInteractEntityEvent e)
    {
    	if(viewing.contains(e.getPlayer()))
    	{
    		e.setCancelled(true);
    	}
    }
    
    @EventHandler
    public void onCommand (PlayerCommandPreprocessEvent e)
    {
    	if(viewing.contains(e.getPlayer()))
    	{
    		e.setCancelled(true);
    		Language.sendAbilityUseMessage(e.getPlayer(), "Sorry, you can't do that right now!", "Camera");
    	}
    }


}