package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityData;
import me.Nikewade.VallendiaMinigame.Utils.FallingBlocksManager;
import me.Nikewade.VallendiaMinigame.Utils.StaticValues;


public class EarthQuakeAbility implements Ability, Listener {

    private static HashMap<String, AbilityData> abilityUsers = new HashMap<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Quake";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.DIRT);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	

    public static void RunAbility(Player player, int radius, int maxy, Vector velocity) {
    	if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(player, "Quake"))
    	{
    		return;
    	}
        Location center = player.getLocation();
        ArrayList<Location> blocks = FallingBlocksManager.getInstance().createSphereShape(center, radius);
        for (Iterator<Location> i = blocks.iterator(); i.hasNext(); ) {
            Location location = i.next();
            if (location.getY() > (center.getY() + radius) - 3 || location.getY() < center.getY() - maxy) i.remove();
        }
        FallingBlocksManager.getInstance().createFallingBlockSet(blocks, StaticValues.FALLINGBLOCK_INITIALDURATION, velocity);
    }

    
    
    public static Listener getListener() {
        return new Listener() {
        	
            @EventHandler(priority = EventPriority.HIGHEST)
            public void onToggleSneakEvent(PlayerToggleSneakEvent event) {
                if (event.isCancelled()) return;
                if (event.isSneaking()) return;
                Player player = event.getPlayer();
                if (abilityUsers.containsKey(player.getUniqueId().toString()))
                    abilityUsers.remove(player.getUniqueId().toString());
            }

            @EventHandler(priority = EventPriority.HIGHEST)
            public void onToggleFlight(PlayerToggleFlightEvent event) {
                if (event.isCancelled()) return;
                if (!event.isFlying()) return;
                Player player = event.getPlayer();
                if (abilityUsers.containsKey(player.getUniqueId().toString()))
                    abilityUsers.remove(player.getUniqueId().toString());

            }

            @EventHandler(priority = EventPriority.HIGHEST)
            public void onPlayerMoveEvent(PlayerMoveEvent event) {
                if (event.isCancelled()) return;
                if (event.getFrom().getBlockY() == event.getTo().getBlockY()) return;
                Player player = event.getPlayer();
                if (player.isFlying()) return;
                if (!player.isSneaking()) return;
                abilityUsers.putIfAbsent(player.getUniqueId().toString(), new AbilityData());
                AbilityData abilityData = abilityUsers.get(player.getUniqueId().toString());
                int distance = 0;
                if (abilityData.getAllData().containsKey("EARTHQUAKE_Distance"))
                    distance = (int) abilityData.getAllData().get("EARTHQUAKE_Distance");
                distance++;
                abilityData.getAllData().put("EARTHQUAKE_Distance", distance);
                if (distance < StaticValues.EARTHQUAKEABILITY_MINHEIGHT - 1) return;
                if (!event.getTo().clone().subtract(0, 1, 0).getBlock().getType().isSolid()) return;
                abilityUsers.remove(player.getUniqueId().toString());
                Vector velocity = new Vector(ThreadLocalRandom.current().nextDouble(-1, 1.1), 0.5, ThreadLocalRandom.current().nextDouble(-1, 1.1));
                RunAbility(player, StaticValues.EARTHQUAKEABILITY_RADIUS, StaticValues.EARTHQUAKEABILITY_MAXY, velocity);
            }
	
        };
    }
    
    
	
}
