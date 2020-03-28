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

public class VampiricAuraAbility implements Ability{
	//made by emma
	HashMap<Player, BukkitTask> timers = new HashMap<>();
	int period = 2;
	int radius = 5;
	int amount = 1;
	// duration needs to be divisable by period into an int eg. if period = 2, duration needs to be an even number not 21 etc..
	int duration = 20;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Vampiric Aura";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Activate this ability to create an aura",
				"around you for 15 seconds dealing minor DoT.  All health",
				"taken is transferred to the caster.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.REDSTONE);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		Language.sendAbilityUseMessage(p, "Vampiric Aura is now active", "Vampiric Aura");
		
		BukkitTask timer = new BukkitRunnable()
			{

				int time = 0;
				int num = 0;
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						if(time==duration/period)
						{
							Language.sendAbilityUseMessage(p, "Vampiric Aura is no longer active", "Vampiric Aura");
							this.cancel();
						}
						

						DonutEffect de3 = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
						de3.particleCount = 1;
						de3.particle = Particle.REDSTONE;
						de3.radiusDonut = 2;
						de3.speed = 0;
						de3.iterations = 1;
						de3.visibleRange = 50;
						de3.xRotation = 300;
						de3.particleOffsetY = 2;
						
						de3.setLocation(p.getLocation());
						de3.start();
						
						DonutEffect de4 = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
						de4.particleCount = 1;
						de4.particle = Particle.SMOKE_NORMAL;
						de4.radiusDonut = 2;
						de4.speed = 0;
						de4.iterations = 1;
						de4.visibleRange = 50;
						de4.xRotation = 300;
						de4.particleOffsetY = 2;
						
						de4.setLocation(p.getLocation());
						de4.start();
						
						DonutEffect de2 = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
						de2.particleCount = 1;
						de2.particle = Particle.REDSTONE;
						de2.radiusDonut = 5;
						de2.speed = 0;
						de2.iterations = 1;
						de2.visibleRange = 50;
						de2.xRotation = 300;
						de2.delay = 5;
						de2.particleOffsetY = 2;
						
						de2.setLocation(p.getLocation());
						de2.start();
						
						DonutEffect de = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
						de.particleCount = 1;
						de.particle = Particle.SMOKE_NORMAL;
						de.radiusDonut = 5;
						de.speed = 0;
						de.iterations = 1;
						de.visibleRange = 50;
						de.xRotation = 300;
						de.delay = 5;
						de.particleOffsetY = 2;
						
						de.setLocation(p.getLocation());
						de.start();
						
						

												
						for(Entity e : AbilityUtils.getAoeTargets(p, p.getLocation(), radius, radius, radius))
						{
							if(e instanceof LivingEntity)
							{
								
								AbilityUtils.damageEntity((LivingEntity) e, p, amount);
								
								num++;
							}
							
						}
						
						AbilityUtils.healEntity(p, num*amount);
						time++;
						num = 0;
						
        				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_DRINK, 2, (float) 1.6);
        				
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
		}
		
	}

}