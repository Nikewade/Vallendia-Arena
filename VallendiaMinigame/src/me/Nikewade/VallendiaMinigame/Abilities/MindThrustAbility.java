package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class MindThrustAbility implements Ability {
	// stun is in ticks (*20)
	int damage = 6;
	int range = 50;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Mind Thrust";
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
				"and deal " + damage + " damage to them.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.BLUE_GLAZED_TERRACOTTA);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
 		LivingEntity target = AbilityUtils.getTarget(p, range);
 		if(target != null)
 		{
 	  		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
 			se.particle = Particle.FOOTSTEP;
 			se.radius = 0.5;
 			se.iterations = 10;
 			se.particles = 2;
 			se.speed = (float) 0;
 			se.visibleRange = 200;
 			se.infinite();
 			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_STRAY_DEATH, 1F, 1.9F);
 			
 			Runnable run = new Runnable()
 			{

 				@Override
 				public void run() {
 					// TODO Auto-generated method stub
 					AbilityUtils.damageEntity(target, p, damage);
 		 			p.getWorld().playSound(target.getLocation(), Sound.ENTITY_STRAY_DEATH, 1F, 1.9F);
 					
 				}
 		
 			};
 			
 			AbilityUtils.followTargetParticle(p, target, se, false, true, run, null, 2.5, 200);
 			
 			return true;
 		}	
 		return false;
	}
		

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
