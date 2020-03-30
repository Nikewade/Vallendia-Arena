package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
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

public class BreakFreeAbility implements Ability{
	int radius = 5;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Break Free";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Use this ability while slowed or stunned to break",
							"yourself and allies free from stuns" , "within " + radius + " blocks.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.IRON_FENCE);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		Location loc = p.getLocation();
		p.playSound(loc, Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 1, 0.1F);
		
  		DonutEffect de = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
  		
        de.particle = Particle.BLOCK_CRACK;
        de.material = Material.IRON_FENCE;
        de.particleCount = 1;
		de.radiusDonut = 5F;
		de.radiusTube = 1F;
		de.iterations = 1;
		de.speed = 0;
		de.visibleRange = 50;
		de.xRotation = 300;
		de.particleOffsetY = 2;
		de.delay = 10;
		de.setLocation(loc);
		
		de.start();
		
  		DonutEffect de2 = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
  		
        de2.particle = Particle.BLOCK_CRACK;
        de2.material = Material.IRON_FENCE;
        de2.particleCount = 1;
		de2.radiusDonut = 3F;
		de2.radiusTube = 1F;
		de2.iterations = 1;
		de2.speed = 0;
		de2.visibleRange = 50;
		de2.xRotation = 300;
		de2.particleOffsetY = 2;
		de2.delay = 5;
		de2.setLocation(loc);

		de2.start();
		
  		DonutEffect de3 = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
  		
        de3.particle = Particle.BLOCK_CRACK;
        de3.material = Material.IRON_FENCE;
        de3.particleCount = 1;
		de3.radiusDonut = 1F;
		de3.radiusTube = 1F;
		de3.iterations = 1;
		de3.speed = 0;
		de3.visibleRange = 50;
		de3.xRotation = 300;
		de3.particleOffsetY = 2;
		de3.setLocation(loc);

		de3.start();
		
		if(AbilityUtils.isStunned(p) || p.hasPotionEffect(PotionEffectType.SLOW))
		{
			Language.sendAbilityUseMessage(p, "You break free from all stuns and slowness!", "Break Free");
		}
		
		if(AbilityUtils.isStunned(p))
		{
			AbilityUtils.removeAllStuns(p);
		}
		if((p.hasPotionEffect(PotionEffectType.SLOW)))
		{
			p.removePotionEffect(PotionEffectType.SLOW);
		}
		
		
		for(Entity e: AbilityUtils.getHealingAoeTargets(p, p.getLocation(), radius, radius, radius))
		{
			if(e instanceof LivingEntity)
			{			
				if(AbilityUtils.isStunned((LivingEntity) e) || ((LivingEntity) e).hasPotionEffect(PotionEffectType.SLOW))
				{
					Language.sendAbilityUseMessage((LivingEntity) e, "You break free from all stuns and slowness!", "Break Free");
				}

				if(AbilityUtils.isStunned((LivingEntity) e) )
				{
					AbilityUtils.removeAllStuns((LivingEntity) e);
				}
				
				if(((LivingEntity) e).hasPotionEffect(PotionEffectType.SLOW))
				{
					((LivingEntity) e).removePotionEffect(PotionEffectType.SLOW);
				}
			}
		}
		
		
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}