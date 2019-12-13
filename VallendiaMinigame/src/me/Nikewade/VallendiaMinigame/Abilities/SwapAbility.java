package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class SwapAbility implements Ability{
//made by Emma
	int range = 20;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Swap";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Swap places with an enemy or party member",
				"that is within " + range + " blocks.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.DIODE);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
			
		if(AbilityUtils.getTargetBoth(p, range)!= null)
		{
			LivingEntity target = AbilityUtils.getTargetBoth(p, range);
			Location tloc = target.getLocation();
			Location ploc = p.getLocation();
			
			p.teleport(tloc);
			target.teleport(ploc);
			p.setFallDistance(0);
			
			//sounds and particles
			
 	 		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 2, (float) 1.4);
 	 		p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().add(0, 1, 0), 20);
 	 		p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().add(0, 1, 0), 20);
 	 		p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().add(0, 1.8, 0), 20);
 	 		
 	 		target.getWorld().playSound(target.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 2, (float) 1.4);
 	 		target.getWorld().spawnParticle(Particle.PORTAL, target.getLocation().add(0, 1, 0), 20);
 	 		target.getWorld().spawnParticle(Particle.PORTAL, target.getLocation().add(0, 1, 0), 20);
 	 		target.getWorld().spawnParticle(Particle.PORTAL, target.getLocation().add(0, 1.8, 0), 20);
 	 		
 	 		return true;
		}
		
						
		
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}