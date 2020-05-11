package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.DonutEffect;
import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class HealingBurstAbility implements Ability{
	//made by emma
	int radius = 10;
	int maxPercent = 30;
	int castTime = 2;
	int period = 1;
	int length = 20;
	int cycleCount = length/period;
	double percent = 1.5;
	HashMap <Player, BukkitTask> timers = new HashMap<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Healing Burst";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Heal allies in a " + radius + " block radius and",
							"yourself for " + percent + "% of their max health every ",
							+ period + " second for " + length + " seconds.",
							Utils.Colorate("&8Cast: " + castTime + " seconds"));
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		
		ItemStack potion = new ItemStack(Material.SPLASH_POTION, 1);

        PotionMeta potionmeta = (PotionMeta) potion.getItemMeta();
        potionmeta.setColor(Color.RED);
        potion.setItemMeta(potionmeta);
		
		return new ItemStack (potion);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub

		Runnable run = new Runnable()
		{
			@Override
			public void run() {
					// TODO Auto-generated method stub
//-----------------------------------------------------------------------------------------------------------------
				DonutEffect de = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
				de.particle = Particle.REDSTONE;
				de.color = Color.FUCHSIA;
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
				de2.color = Color.FUCHSIA;
				de2.radiusDonut = 5;
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
				de3.color = Color.FUCHSIA;
				de3.radiusDonut = 10;
				de3.speed = 0;
				de3.iterations = 1;
				de3.visibleRange = 50;
				de3.xRotation = 300;
				de3.delay = 10;
				de3.particleOffsetY = 2;
				
				de3.setLocation(p.getLocation());
				de3.start();
//-------------------------------------------------------------------------------------------------------------
				BukkitTask timer = new BukkitRunnable()
						{
					int t = 1;
					@Override
					public void run() {
							// TODO Auto-generated method stub
						if(t >= length/period)
						{
							this.cancel();
						}
				
					double lowpercent = percent/100;
					double maxhealth = p.getMaxHealth();
					double healamount = maxhealth*lowpercent;
					p.sendMessage("low percent = " + lowpercent);
					p.sendMessage("percent " + percent);
					p.sendMessage("heal amount " + healamount);

					AbilityUtils.healEntity(p, healamount);
					
			
				for(Entity target : AbilityUtils.getHealingAoeTargets(p, p.getLocation(), radius, radius, radius))
				{

					double lowpercent2 = percent/100;
					double maxhealth2 = ((Damageable) target).getMaxHealth();
					double healamount2 = maxhealth2*lowpercent2;
					target.sendMessage("low percent = " + lowpercent2);
					target.sendMessage("percent " + percent);
					target.sendMessage("heal amount " + healamount2);
					target.getWorld().playSound(target.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 1, 1.6F);
					AbilityUtils.healEntity((LivingEntity) target, healamount2);		
				}
					
				t++;
	
			}}.runTaskTimer(VallendiaMinigame.getInstance(), 0, period*20);
			
		timers.put(p, timer);		
			
			
			}};
		
		AbilityUtils.castAbility(p, castTime, run);
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