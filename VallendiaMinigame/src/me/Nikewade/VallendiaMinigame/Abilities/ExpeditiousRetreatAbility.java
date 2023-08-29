package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class ExpeditiousRetreatAbility implements Ability {
	int amplifier = 2;
	int duration = 10;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Expeditious Retreat";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You break free from all slows, stuns, and roots",
							"and gain speed " + amplifier+  " for " + duration + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.FEATHER);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		p.getWorld().playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 2, 0.3F);
				
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.particle = Particle.CLOUD;
			se.radius = 1F;
			se.particles = 5;
			se.setEntity(p);
			se.iterations = 3;
			se.particleOffsetY = 1.3F;
			se.yOffset = -0.8F;
			
			se.start();
			
		AbilityUtils.addPotionDuration(p, p, "Expeditious Retreat", PotionEffectType.SPEED, amplifier, duration*20);
		
		new BukkitRunnable()
		{
			int t = 0;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(t >= duration*4)
				{
					this.cancel();
				}
				Location loc = p.getLocation();
				SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
				se.iterations = 2;
				se.particle = Particle.CLOUD;
				se.radius = 0.1F;
				se.particles = 1;
				se.speed = (float) 0;
				se.setLocation(loc);
				se.start();
				t++;
			}
			
		}.runTaskTimer(VallendiaMinigame.getInstance(), 5, 5);
		
		if(AbilityUtils.isStunned(p) || p.hasPotionEffect(PotionEffectType.SLOW) || AbilityUtils.isRooted(p))
		{
			Language.sendAbilityUseMessage(p, "You break free from all stuns and slowness!", "Expeditious Retreat");
		}
		
		if(AbilityUtils.isStunned(p))
		{
			AbilityUtils.removeAllStuns(p);
		}
		if((p.hasPotionEffect(PotionEffectType.SLOW)))
		{
			p.removePotionEffect(PotionEffectType.SLOW);
		}
		if(AbilityUtils.isRooted(p))
		{
			AbilityUtils.removeAllRoots(p);
		}
		
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}