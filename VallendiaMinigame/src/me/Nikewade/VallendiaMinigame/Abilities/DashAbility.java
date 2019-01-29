package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class DashAbility implements Ability{
	double forwardVelocity = 15 / 10D;
	double upwardVelocity = 0 / 10D;
	Location oldLocation;
	Location newLocation;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Dash";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Dash forward at a great speed. You become",
				"tired after, slowing you down for a few seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.LEATHER_BOOTS);
	}

	@Override
	public boolean RunAbility(Player p) {
		oldLocation = p.getLocation();
		newLocation = null;
		p.setVelocity(p.getVelocity().multiply(0));
		Vector v = p.getLocation().getDirection();
		v.setY(0).normalize().multiply(forwardVelocity).setY(upwardVelocity);
		p.teleport(oldLocation.add(0, 0.8, 0).setDirection(oldLocation.getDirection()));
		p.setVelocity(v);
		p.setFallDistance(0);
		
    	new BukkitRunnable() {
    		int dashtime = 1;
            @Override
            public void run() {	
            		dashtime--;
            		v.setY(0).normalize().multiply(forwardVelocity).setY(upwardVelocity);
            		p.setVelocity(v);
                	if(dashtime == 0)
                	{
    	        		
                		this.cancel();
    	                AbilityUtils.addPotionDuration(p, PotionEffectType.SLOW, 1, 2*20);
    	                newLocation = p.getLocation();
                	}
            }
	}.runTaskTimer(VallendiaMinigame.getInstance(), 1L, 1L);
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 2, (float) 1);
		
		return true;
	}

}
