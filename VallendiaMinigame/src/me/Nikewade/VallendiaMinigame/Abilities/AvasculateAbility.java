package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	int time = 2;
	int range = 8;
	int percent = 30;
	
	Map<LivingEntity, FountainEffect> particles = new HashMap<>();
	Map<Player, LivingEntity> targets = new HashMap<>();
	Boolean breakondamage = true;

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
					AbilityUtils.removeAllRoots(target);
					Disable(p, target);
				}
				new BukkitRunnable()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(target.isOnGround())
						{
							AbilityUtils.damageEntity(target, p, damage);
							AbilityUtils.root(p, target, "Entangle", time*20, breakondamage);
							targets.put(p, target);
							p.getWorld().playSound(p.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 2, (float) 0.1);	
							
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
							
							particles.put(target, d);
							this.cancel();
						}
						
					}
					
				}.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
				
				new BukkitRunnable()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(!AbilityUtils.isRooted(target)  && target.isOnGround())
						{				
							Disable(p, target);
							this.cancel();
						}

						}
					
				}.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);


		
		}};
		
		AbilityUtils.followTargetParticle(p, target, se, false, true, run, null, 1, 200);
		
		return true;
	}
	
	public void Disable(Player p, LivingEntity target)
	{
		if(particles.containsKey(target))
		{
			particles.get(target).cancel();
			particles.remove(target);
		}
		if(AbilityUtils.isRooted(target))
		{
			AbilityUtils.removeRoot(target, "Avasculate");
		}
		if(targets.containsKey(p))
		{
			targets.remove(p);
		}
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(targets.containsKey(p))
		{
			Disable(p, targets.get(p));
		}
		
	}

}