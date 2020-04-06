package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class RepellingBlastAbility implements Ability{
	int damage = 4;
	int range = 30;
	double forwardVelocity = 13 / 10D;;
	double upwardVelocity = 7 / 10D;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Repelling Blast";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Target an enemy up to " + range + " blocks away." ,
				"Push them back and deal " + damage + " damage to them.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
 		LivingEntity target = AbilityUtils.getTarget(p, range);
 		if(target != null)
 		{
            Vector direction = target.getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
 	  		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
 			se.particle = Particle.CLOUD;
 			se.radius = 0.5;
 			se.iterations = 10;
 			se.particles = 2;
 			se.speed = (float) 0;
 			se.visibleRange = 200;
 			se.infinite();
 			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 2F, 1.5F);
 			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 2F, 1.5F);
 			
 			Runnable run = new Runnable()
 			{

 				@Override
 				public void run() {
 					// TODO Auto-generated method stub
 					AbilityUtils.damageEntity(target, p, damage);
 					direction.multiply(forwardVelocity).setY(upwardVelocity);
 		 			p.getWorld().playSound(target.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 2F, 1.5F);
 		 			target.getWorld().playSound(target.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 2F, 1.5F);
 		 			target.getWorld().spawnParticle(Particle.CLOUD, target.getLocation().add(0, 1.3, 0), 10);
 		 			target.setVelocity(direction);
 					
 				}
 		
 			};
 			
 			AbilityUtils.followTargetParticle(p, target, se, false, true, run, null, 2, 200);
 			
 			return true;
 		}	
 		return false;
	}
		

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}
}
