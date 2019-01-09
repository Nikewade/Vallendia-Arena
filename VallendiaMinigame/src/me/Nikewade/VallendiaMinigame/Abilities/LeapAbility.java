package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;

public class LeapAbility implements Ability , Listener{
	private static ArrayList<UUID> leaping  = new ArrayList<>();
	double forwardVelocity = 1 / 10D;
	double upwardVelocity = 10 / 10D;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Leap";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}
	
	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Leap into the air.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.LEATHER_BOOTS);
	}

	@Override
	public boolean RunAbility(Player p) {
		Vector v = p.getLocation().getDirection();
		v.setY(0).normalize().multiply(forwardVelocity).setY(upwardVelocity);
		p.setVelocity(v);
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 2, (float) 0.7);
		leaping.add(p.getUniqueId());	
		p.setFallDistance(0);
		
		new BukkitRunnable() {
            @Override
            public void run() {
         	   if(p.isOnGround())
         	   {
         		   leaping.remove(p.getUniqueId());
         		   this.cancel();
         	   }
            }
        }.runTaskTimer(VallendiaMinigame.getInstance(), 30L, 0L);  	
	
	
	return true;
	}
	
	
	
	
	
    public static Listener getListener() {
        return new Listener() {
        	
        	
        	
        	
            @EventHandler
            public void onDamage(EntityDamageEvent e) {
            	if(!(e.getEntity() instanceof Player))
            	{
            		return;
            	}
            	
            	
            	if(e.getCause() == DamageCause.FALL)
            	{
                	Player p = (Player) e.getEntity();
                	if(leaping.contains(p.getUniqueId()) && p.getFallDistance() <= 12)
                	{
                		e.setCancelled(true);	
                	}	
            	}

            }
            
            
            
            
        };
    }
	
	
	

}
