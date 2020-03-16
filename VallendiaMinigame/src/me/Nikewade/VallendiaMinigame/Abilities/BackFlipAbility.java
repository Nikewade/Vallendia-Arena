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
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;

public class BackFlipAbility implements Ability , Listener{
	private static ArrayList<UUID> backflipping  = new ArrayList<>();
	double forwardVelocity = -10 / 10D;;
	double upwardVelocity = 8 / 10D;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Backflip";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}
	
	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Backflip into the air. Sneaking negates the sound.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.RABBIT_FOOT);
	}

	@Override
	public boolean RunAbility(Player p) {
		Vector v = p.getLocation().getDirection().normalize();
		v.multiply(this.forwardVelocity).setY(this.upwardVelocity);
		p.setVelocity(v);
		if(!p.isSneaking())
		{
			p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 2, (float) 1.3);
		}
		p.setFallDistance(0);
			backflipping.add(p.getUniqueId());		
		
			new BukkitRunnable() {
                @Override
                public void run() {
             	   if(p.isOnGround())
             	   {
             		   backflipping.remove(p.getUniqueId());
             		   this.cancel();
             	   }
                }
            }.runTaskTimer(VallendiaMinigame.getInstance(), 30L, 0L);  	
		return true;

	}
	
	
	
            @EventHandler
            public void onDamage(EntityDamageEvent e) {
            	if(!(e.getEntity() instanceof Player))
            	{
            		return;
            	}
            	
            	
            	if(e.getCause() == DamageCause.FALL)
            	{
                	Player p = (Player) e.getEntity();
                	
                	if(backflipping.contains(p.getUniqueId()) && p.getFallDistance() <= 12)
                	{
                		e.setCancelled(true);	
                	}	
                	
                	
            	}


            }
            
            
            
            

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}
	
	

}
