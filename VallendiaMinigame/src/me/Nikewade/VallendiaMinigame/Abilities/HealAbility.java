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
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class HealAbility implements Ability{
	//made by emma
	int range = 5;
	int maxPercent = 50;
	int castTime = 2;
	int time = 20;
	int period = 1;
	int cycleCount = time/period;
	//percent = maxPercent/cycleCount
	double percent = 2.5;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Heal";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Heal an ally within " + range + " blocks or yourself for",
				+ maxPercent + "% of their max health over " + time + " seconds.",
				"Cast Time: " + castTime + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		ItemStack potion = new ItemStack(Material.POTION, 1);

        PotionMeta potionmeta = (PotionMeta) potion.getItemMeta();
        potionmeta.setColor(Color.RED);
        potion.setItemMeta(potionmeta);
		
		return new ItemStack (potion);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub#
		
		LivingEntity target = AbilityUtils.getHealingTarget(p, range);
		
		Runnable run = new Runnable()
		{
			@Override
			public void run() {
				
				BukkitTask timer = new BukkitRunnable()
						{
					int t = 1;
					@Override
					public void run() {
						
						if(t >= cycleCount)
						{
							this.cancel();
						}
				
				if(target == null)
				{
					double lowpercent = percent/100;
					double maxhealth = p.getMaxHealth();
					double healamount = maxhealth*lowpercent;
					
					p.getWorld().playSound(p.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 1, 1.6F);
					AbilityUtils.healEntity(p, healamount);
					t++;
					return;
				}
				
				SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
				se.disappearWithOriginEntity = true;
				se.iterations = 10;
				se.particle = Particle.REDSTONE;
				se.color = Color.FUCHSIA;
				se.radius = 0.6;
				se.particleOffsetY = (float) 0.5;
				se.particles = 3;
				se.yOffset = -0.8;
				se.speed = (float) 0;
				se.setEntity(target);
				se.start();
				
				double lowpercent = percent/100;
				double maxhealth = target.getMaxHealth();
				double healamount = maxhealth*lowpercent;
				target.getWorld().playSound(target.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 1, 1.6F);
				AbilityUtils.healEntity(target, healamount);
				
				t++;
				
					}
					}.runTaskTimer(VallendiaMinigame.getInstance(), 0 , period*20);
			}
			
		};
		
		AbilityUtils.castAbility(p, castTime, run);
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}