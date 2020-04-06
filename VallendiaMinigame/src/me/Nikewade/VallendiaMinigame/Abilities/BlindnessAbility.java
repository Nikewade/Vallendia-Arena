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
import org.bukkit.potion.PotionEffectType;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class BlindnessAbility implements Ability {
	int duration = 12;
	int range = 15;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Blindness";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Give an enemy blindness and nausea for 30 seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack (Material.SPIDER_EYE);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		LivingEntity target = AbilityUtils.getTarget(p, range);
		
		if(target == null)
		{
			return false;
		}
		

  		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se.particle = Particle.REDSTONE;
		se.color = Color.BLACK;
		se.radius = 0.2;
		se.iterations = 10;
		se.particles = 5;
		se.speed = (float) 0;
		se.visibleRange = 200;
		se.infinite();
		
  		SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se2.particle = Particle.SUSPENDED_DEPTH;
		se2.radius = 0.2;
		se2.iterations = 10;
		se2.particles = 5;
		se2.speed = (float) 0;
		se2.visibleRange = 200;
		se2.infinite();
		
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1F, 0.8F);
		
		Runnable run = new Runnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				AbilityUtils.addPotionDuration(p, target, "Blindness", PotionEffectType.BLINDNESS, 1, duration*20);
				AbilityUtils.addPotionDuration(p, target, "Blindness", PotionEffectType.CONFUSION, 1, duration*20);
					
			}
	
		};
		
		AbilityUtils.followTargetParticle(p, target, se, false, true, run, null, 1.7, 200);
		AbilityUtils.followTargetParticle(p, target, se2, false, false, null, null, 1.7, 200);
		
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
		
		
	}

}