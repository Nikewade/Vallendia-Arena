package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.DonutEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class RallyUpAbility implements Ability{
	int range = 15;
	int speedAmplifier = 0;
	int healAmount = 1;
	int period = 2;
	int speedTime = period+1;
	int length = 30;
	int cycleCount = length/period;
	HashMap<Player, BukkitTask> timers = new HashMap<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Rally Up";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You rally up your teammates, granting yourself and all allies",
								"within " + range + " blocks speed and " + healAmount,
								"health every " + period + " seconds for " + length + "seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.APPLE);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 2, 0.6F);

		
		BukkitTask timer = new BukkitRunnable()
				{
					int t = 1;

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(t >= cycleCount)
						{
							this.cancel();
							timers.remove(p);
						}
						
						DonutEffect de = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
						de.particle = Particle.CRIT_MAGIC;
						de.radiusDonut = 5;
						de.speed = 0;
						de.iterations = 1;
						de.visibleRange = 50;
						de.xRotation = 300;
						de.particleOffsetY = 2;
						
						de.setLocation(p.getLocation());
						de.start();
						
						DonutEffect de2 = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
						de2.particle = Particle.CRIT_MAGIC;
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
						de3.particle = Particle.CRIT_MAGIC;
						de3.radiusDonut = 15;
						de3.speed = 0;
						de3.iterations = 1;
						de3.visibleRange = 50;
						de3.xRotation = 300;
						de3.delay = 10;
						de3.particleOffsetY = 2;
						
						de3.setLocation(p.getLocation());
						de3.start();
						
						AbilityUtils.addPotionDuration(p, p, "Rally Up", PotionEffectType.SPEED, speedAmplifier, speedTime*20);
						AbilityUtils.healEntity(p, healAmount);
						
						for(Entity target : AbilityUtils.getHealingAoeTargets(p, p.getLocation(), range, range, range))
						{
							
							AbilityUtils.addPotionDuration(p, (LivingEntity) target, "Rally Up", PotionEffectType.SPEED, speedAmplifier, speedTime*20);
							AbilityUtils.healEntity((LivingEntity) target, healAmount);
							
						}
						
						t++;
						
					}
			
				}.runTaskTimer(VallendiaMinigame.getInstance(), 0, period*20);
				
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