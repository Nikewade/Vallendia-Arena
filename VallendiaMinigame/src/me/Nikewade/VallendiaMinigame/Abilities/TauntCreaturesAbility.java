package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.DonutEffect;
import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class TauntCreaturesAbility implements Ability {
	int duration = 30;
	int radius = 15;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Taunt Creatures";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You yell loud and hurtful words",
				"that taunt all non-player targets within a",
				+ radius + " block radius. The creatures will",
				"be unable to target anything but you for" ,
				duration + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.RED_GLAZED_TERRACOTTA);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_IRONGOLEM_DEATH, 2, 0.5F);
			
			new BukkitRunnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 0.6F);
				}
				
			}.runTaskLater(VallendiaMinigame.getInstance(), 8);
			
			
			new BukkitRunnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 0.7F);
				}
				
			}.runTaskLater(VallendiaMinigame.getInstance(), 15);
			
			
			
			DonutEffect de = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
			de.particle = Particle.REDSTONE;
			de.color = Color.GRAY;
			de.radiusDonut = 5;
			de.speed = 0;
			de.iterations = 1;
			de.visibleRange = 50;
			de.xRotation = 300;
			de.particleOffsetY = 2;
			
			de.setLocation(p.getLocation());
			de.start();
			
			DonutEffect de2 = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
			de2.particle = Particle.REDSTONE;
			de2.color = Color.GRAY;
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
			de3.particle = Particle.REDSTONE;
			de3.color = Color.GRAY;
			de3.radiusDonut = 15;
			de3.speed = 0;
			de3.iterations = 1;
			de3.visibleRange = 50;
			de3.xRotation = 300;
			de3.delay = 10;
			de3.particleOffsetY = 2;
			
			de3.setLocation(p.getLocation());
			de3.start();
			
		for(Entity e: AbilityUtils.getAoeTargets(p, p.getLocation(), radius, radius, radius))
		{
			if(e instanceof Player)
			{
				continue;
			}
			LivingEntity target = (LivingEntity) e;
 			
	 			AbilityUtils.charm(p, target, "Charm Person", duration * 20, false, true);
		        target.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, target.getLocation().add(0, 0.4, 0.4), 5);
		        target.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, target.getLocation().add(0, 0.4, 0), 5);
		        target.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, target.getLocation().add(0.4, 0.4, 0), 5);
					SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
					se.setEntity(target);
					se.disappearWithOriginEntity = true;
					se.iterations = 5;
					se.particle = Particle.REDSTONE;
					se.color = Color.GRAY;
					se.radius = 1;
					se.particles = 5;
					se.yOffset = -0.8;
					se.start();
		}
 			
 			
 			return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
