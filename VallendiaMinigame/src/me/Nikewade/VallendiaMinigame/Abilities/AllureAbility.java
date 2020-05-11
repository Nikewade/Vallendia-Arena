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

public class AllureAbility implements Ability {
	int duration = 10;
	int range = 15;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Allure";
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
				"overwhelmed with lust towards you.",
				"The target is charmed and is unable to",
				"look at anything but you, for " + duration +" seconds." ,
				"Any damage delt to the target will cancel this effect.");
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
 			AbilityUtils.charm(p, target, "Allure", duration * 20, true, false);
 			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 2, 0.8F);
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
 			return true;
 		}
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
