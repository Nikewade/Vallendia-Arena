package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class DelayedBlastFireballAbility implements Ability {
	int damage = 10;
	int delay = 5;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Delayed Blast Fireball";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Acts as Fireball but it takes 5 seconds to explode,",
							"and has no cast time.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack (Material.FIREBALL);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		  		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
				se.particle = Particle.FLAME;
				se.disappearWithOriginEntity = true;
				se.infinite();
				se.radius = 0.2;
				se.particles = 2;
				se.speed = (float) 0;
				se.visibleRange = 50;
				
		  		SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
				se2.particle = Particle.FLAME;
				se2.iterations = 2;
				se2.particles = 100;
				se2.radius = 7;
				se2.speed = (float) 0.1;
				se2.visibleRange = 50;
				
		  		SphereEffect se3 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
				se3.particle = Particle.EXPLOSION_HUGE;
				se3.iterations = 1;
				se3.particles = 10;
				se3.radius = 2;
				se3.speed = (float) 0.1;
				se3.visibleRange = 50;

				
				Runnable run2 = new Runnable()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						new BukkitRunnable()
								{

									@Override
									public void run() {
										// TODO Auto-generated method stub
										
										AbilityUtils.explode(se.getLocation(), p, 5, damage, true, true, false);
										for(Entity e : AbilityUtils.getAoeTargets(p, se.getLocation(), 5, 5, 5) )
										{
											LivingEntity le = (LivingEntity) e;
											e.setFireTicks(100);
										}
										
										se2.setLocation(se.getLocation());
										se2.start();
										se3.setLocation(se.getLocation());
										se3.start();
									}
							
								}.runTaskLater(VallendiaMinigame.getInstance(), delay*20);

					}
					
				};
				
				
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, (float) 1.6);
				AbilityUtils.arcParticle(p, se, 0.7, run2);	

			
			
		
		
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}