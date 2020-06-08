package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.HashMap;
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
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.DonutEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class BlessingAuraAbility implements Ability{
	int healAmount = 10;
	int radius = 10;
	int period = 1;
	int length = 20;
	double perSec = 0.5;
	HashMap <Player, BukkitTask> timers = new HashMap<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Blessing Aura";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Allies in a " + radius + " block radius will",
				"heal for " + perSec + " health every second, lasting",
				length + " seconds.");
		
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack (Material.GOLDEN_APPLE);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		Language.sendAbilityUseMessage(p, "Blessing Aura is now Active!", "Blessing Aura");
		
		BukkitTask timer = new BukkitRunnable() {					
			int t = 1;
		@Override
		public void run() {
		// TODO Auto-generated method stub
			if(t >= length/period)
			{		
				Language.sendAbilityUseMessage(p, "Blessing Aura is no longer Active", "Blessing Aura");
				timers.remove(p);
				this.cancel();
			}
//-----------------------------------------------------------------------------------------------------------------
			DonutEffect de = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
			de.particle = Particle.REDSTONE;
			de.color = Color.YELLOW;
			de.radiusDonut = 2;
			de.speed = 0;
			de.iterations = 1;
			de.visibleRange = 50;
			de.xRotation = 300;
			de.particleOffsetY = 2;
			
			de.setLocation(p.getLocation());
			de.start();
			
			DonutEffect de2 = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
			de2.particle = Particle.REDSTONE;
			de2.color = Color.WHITE;
			de2.radiusDonut = 5;
			de2.speed = 0;
			de2.iterations = 1;
			de2.visibleRange = 50;
			de2.xRotation = 300;
			de2.delay = 5;
			de2.particleOffsetY = 2;
			
			de2.setLocation(p.getLocation());
			de2.start();
			
			DonutEffect de4 = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
			de4.particle = Particle.REDSTONE;
			de4.color = Color.YELLOW;
			de4.radiusDonut = 5;
			de4.speed = 0;
			de4.iterations = 1;
			de4.visibleRange = 50;
			de4.xRotation = 300;
			de4.delay = 5;
			de4.particleCount = 100;
			de4.particleOffsetY = 2;
			
			de4.setLocation(p.getLocation());
			de4.start();
			
			DonutEffect de3 = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
			de3.particle = Particle.REDSTONE;
			de3.color = Color.YELLOW;
			de3.radiusDonut = 10;
			de3.speed = 0;
			de3.iterations = 1;
			de3.visibleRange = 50;
			de3.xRotation = 300;
			de3.delay = 10;
			de3.particleOffsetY = 2;
			
			de3.setLocation(p.getLocation());
			de3.start();
//---------------------------------------------------------------------------------------------------------------------
			

			AbilityUtils.healEntity(p, perSec);
			
		for(Entity target : AbilityUtils.getHealingAoeTargets(p, p.getLocation(), radius, radius, radius))
		{
			

			target.getWorld().playSound(target.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 1, 1.6F);
			AbilityUtils.healEntity((LivingEntity) target, perSec);	

		}
		
		t++;
		
		}}.runTaskTimer(VallendiaMinigame.getInstance(), 0, period*20);
		
		timers.put(p, timer);
		
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(timers.containsKey(p))
		{
			timers.get(p).cancel();
			timers.remove(p);
		}
	}

}