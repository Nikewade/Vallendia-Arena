package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.FountainEffect;
import de.slikey.effectlib.effect.SphereEffect;
import de.slikey.effectlib.util.DynamicLocation;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class AvasculateAbility implements Ability{
	double time = 2;
	int range = 8;
	int percent = 30;

	HashMap<LivingEntity, FountainEffect> particles = new HashMap<>();
	HashMap<LivingEntity, BukkitTask> timers = new HashMap<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Avasculate";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You purge the bloodvessels of a living creature",
							"to deal " + percent + "% of their current health and",
							"entangling them for " + time + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.NETHER_STALK);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		LivingEntity target = AbilityUtils.getTarget(p, range);
		if(target == null)
		{
			return false;
		}

		target.getWorld().playSound(target.getLocation(), Sound.ENTITY_GUARDIAN_HURT, 5, (float) 0.4);
		
  		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se.particle = Particle.BLOCK_CRACK;
		se.material = Material.BLACK_GLAZED_TERRACOTTA;
		se.radius = 0.2;
		se.particles = 50;
		se.speed = (float) 0;
		se.visibleRange = 200;
		se.infinite();
		
		double lowPercent = Utils.getPercentHigherOrLower(100-percent, false);
		double targethealth = target.getHealth();
		double damage = targethealth*lowPercent;
		
		Runnable run = new Runnable()
		{
			@Override
			public void run() {
				
		if(AbilityUtils.isRooted(target))
		{
			AbilityUtils.removeAllRoots(target);;
		}
;
		AbilityUtils.damageEntity(target, p,  damage);
		AbilityUtils.root(p, target, "Entangle", (int) (time*20), true);		

		
		FountainEffect d = new FountainEffect(VallendiaMinigame.getInstance().effectmanager);
		d.setTargetEntity(target);
		d.disappearWithTargetEntity = true;
		d.setDynamicOrigin(new DynamicLocation(target.getLocation().add(0, 0.1, 0)));
		d.strands = 5;
		d.height = (float) 0.3;
		d.heightSpout = 0;
		d.particlesSpout = 0;
		d.radiusSpout = 0;
		d.radius = (float) 1;
		d.particlesStrand = 10;
		d.particle = Particle.BLOCK_CRACK;
		d.material = Material.NETHER_WARTS;
		d.infinite();
		d.start();
		
		particles.put(p, d);
		
		BukkitTask timer = new BukkitRunnable()
		{
			int t = 1;
			@Override
			public void run() {

				if(t >= time*20)
				{
					this.cancel();
					timers.remove(p);
				}
				if(!AbilityUtils.isRooted(target))
				{
					if(particles.containsKey(p))
					{
						particles.get(p).cancel();
					}
				}
				
				t++;
				
			}
		}.runTaskTimer(VallendiaMinigame.getInstance(), 1, 1);
		
		timers.put(p, timer);
		
		new BukkitRunnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if(particles.containsKey(p))
				{
					particles.get(p).cancel();
					particles.remove(p);
				}
				
				if(timers.containsKey(p))
				{
					timers.get(p).cancel();
					timers.remove(p);
				}
				
			}
			
		}.runTaskLater(VallendiaMinigame.getInstance(), (long) (time*20));
		}};
		
		AbilityUtils.followTargetParticle(p, target, se, false, true, run, null, 1, 200);
		
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(particles.containsKey(p))
		{
			particles.get(p).cancel();
			particles.remove(p);
		}
		if(timers.containsKey(p))
		{
			timers.get(p);
			timers.remove(p);
		}
		
	}

}