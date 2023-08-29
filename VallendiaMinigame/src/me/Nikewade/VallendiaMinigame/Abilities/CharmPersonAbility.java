package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
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

public class CharmPersonAbility implements Ability {
	int duration = 15;
	int range = 20;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Charm Person";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("A target within " + range +" blocks becomes",
				"overwhelmed with lust towards you. The target is charmed and",
				"is unable to look at anything but you, for " + duration +" seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.PINK_GLAZED_TERRACOTTA);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
 		LivingEntity target = AbilityUtils.getTarget(p, range);
 		if(target != null)
 		{
 			
 	  		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.particle = Particle.REDSTONE;
			se.color = Color.FUCHSIA;
 			se.radius = 0.2;
 			se.iterations = 10;
 			se.particles = 5;
 			se.speed = (float) 0;
 			se.visibleRange = 200;
 			se.infinite();
	 			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 2, 0.8F);
 			
 			Runnable run = new Runnable()
 			{

 				@Override
 				public void run() {
 					// TODO Auto-generated method stub
 		 			AbilityUtils.charm(p, target, "Charm Person", duration * 20, false, false);
 			        target.getWorld().spawnParticle(Particle.DAMAGE_INDICATOR, target.getLocation().add(0, 0.4, 0.4), 5);
 			        target.getWorld().spawnParticle(Particle.DAMAGE_INDICATOR, target.getLocation().add(0, 0.4, 0), 5);
 			        target.getWorld().spawnParticle(Particle.DAMAGE_INDICATOR, target.getLocation().add(0.4, 0.4, 0), 5);
 			        
 					SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
 					se.setEntity(target);
 					se.disappearWithOriginEntity = true;
 					se.iterations = 5;
 					se.particle = Particle.REDSTONE;
 					se.color = Color.FUCHSIA;
 					se.radius = 1;
 					se.particles = 5;
 					se.yOffset = -0.8;
 					se.start();
 					
 					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 2, 1.3F);

 					
 				}
 		
 			};
 			
 			AbilityUtils.followTargetParticle(p, target, se, false, true, run, null, 1.7, 200);
 			
 			
 			return true;
 		}
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
