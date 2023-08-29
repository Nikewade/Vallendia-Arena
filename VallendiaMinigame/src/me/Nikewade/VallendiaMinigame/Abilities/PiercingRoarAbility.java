package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import de.slikey.effectlib.effect.DonutEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class PiercingRoarAbility implements Ability {
	//made by Emma
	int radius = 15;
	int duration = 5;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Piercing Roar";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You let out a loud roar, slowing",
<<<<<<< HEAD
				" and disorienting enemies within a " + radius + "",
				" block range");
=======
				"and disorienting enemies within a " + radius,
				"block range");
>>>>>>> second-repo/master
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.HOPPER);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 2, (float) 1.6);
		

		DonutEffect de = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
		de.particle = Particle.CRIT;
		de.radiusDonut = 5;
		de.speed = 0;
		de.iterations = 1;
		de.visibleRange = 50;
		de.xRotation = 300;
		de.particleOffsetY = 2;
		
		de.setLocation(p.getLocation());
		de.start();
		
		DonutEffect de2 = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
		de2.particle = Particle.CRIT;
		de2.radiusDonut = 10;
		de2.speed = 0;
		de2.iterations = 1;
		de2.visibleRange = 50;
		de2.xRotation = 300;
		de2.delay = 5;
		de2.particleOffsetY = 2;
		
		de2.setLocation(p.getLocation());
		de2.start();
		
		DonutEffect de3 = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
		de3.particle = Particle.CRIT;
		de3.radiusDonut = 15;
		de3.speed = 0;
		de3.iterations = 1;
		de3.visibleRange = 50;
		de3.xRotation = 300;
		de3.delay = 10;
		de3.particleOffsetY = 2;
		
		de3.setLocation(p.getLocation());
		de3.start();
		
		
		
		for(Entity e : AbilityUtils.getAoeTargets(p, p.getLocation(), radius, radius, radius))
		{
<<<<<<< HEAD
=======
			if(e instanceof LivingEntity)
			{
>>>>>>> second-repo/master
			
			AbilityUtils.addPotionDuration(p, (LivingEntity)e, "Piercing Roar", PotionEffectType.CONFUSION, 0, duration*20);
			AbilityUtils.addPotionDuration(p, (LivingEntity)e, "Piercing Roar", PotionEffectType.BLINDNESS, 0, duration*20);
			AbilityUtils.addPotionDuration(p, (LivingEntity)e, "Piercing Roar", PotionEffectType.SLOW, 0, duration*20);
			AbilityUtils.silenceAbilities((LivingEntity) e, duration, "Piercing Roar");
<<<<<<< HEAD
			Language.sendAbilityUseMessage(p, "Your abilities are silenced.", "Piercing Roar");
		}
		
		return false;
=======
			Language.sendAbilityUseMessage((LivingEntity) e, "Your abilities are silenced.", "Piercing Roar");
			
			}
		}
		
		return true;
>>>>>>> second-repo/master
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}