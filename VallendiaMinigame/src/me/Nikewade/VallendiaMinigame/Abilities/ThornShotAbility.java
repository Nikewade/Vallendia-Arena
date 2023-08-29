package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.ConeEffect;
import de.slikey.effectlib.effect.FountainEffect;
import de.slikey.effectlib.effect.SphereEffect;
import de.slikey.effectlib.util.DynamicLocation;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class ThornShotAbility implements Ability, Listener{
	 //made by Emma
	Map<Projectile,SphereEffect> arrow = new HashMap<>();
	ArrayList<Player> abilityActive = new ArrayList<>();
	Map<Player, BukkitTask> timers = new HashMap<>();
	Map<LivingEntity, FountainEffect> particles = new HashMap<>();
	Map<LivingEntity, BukkitTask> damagetimers = new HashMap<>();
	Map<Player, LivingEntity> targets = new HashMap<>();
	Boolean breakondamage = false;
    int delay = 15;
    int time = 5;
    int period = 1;
    int cycleCount = 5/1;
    int damage = 5;
    int persec = damage/cycleCount;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Thorn Shot";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You shoot an arrow that roots your enemy",
							"within thorns and damages them for " + damage + " health",
							"over " + time + " seconds");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.DEAD_BUSH);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		if(abilityActive.contains(p))
		{
			return false;
		}else
		{
			abilityActive.add(p);
			Language.sendAbilityUseMessage(p, "Thorn Shot is now Active", "Thorn Shot");
			
			BukkitTask timer = new BukkitRunnable()
			{

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(abilityActive.contains(p))
					{
					abilityActive.remove(p);
					Language.sendAbilityUseMessage(p, "Thorn Shot is no longer Active", "Thorn Shot");
					
					}
				}
				
			}.runTaskLater(VallendiaMinigame.getInstance(), delay*20);
			
			timers.put(p, timer);
			return true;
		}

	}
	
	
	
        	
            @EventHandler
            public void onShootBow(EntityShootBowEvent e){
            	
            	if(!(e.getEntity() instanceof Player))
            	{
            		return;
            	}
            	
            	Player p = (Player) e.getEntity();
        		if(!(abilityActive.contains(p)))
        		{
        			return;
        		}

            		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
            		se.setEntity(e.getProjectile());
            		se.particle = Particle.BLOCK_CRACK;
            		se.material = Material.DEAD_BUSH;
            		se.particleCount = 1;
            		se.radius = 0.1;
            		se.particles = 1;
            		se.start();	
            		arrow.put((Projectile)e.getProjectile(), se);	
        		
        		if(timers.containsKey(p))
        		{
        			timers.get(p).cancel();
        		}
        		abilityActive.remove(p);
            }

            @EventHandler
            public void onland(ProjectileHitEvent e){
            	if(!arrow.containsKey(e.getEntity()))
            	{
            		return;
            	}
            	Player p = (Player) e.getEntity().getShooter();
            	
        		arrow.get(e.getEntity()).cancel();
                arrow.remove(e.getEntity());
        		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
        		se.setEntity(e.getEntity());
        		se.particle = Particle.BLOCK_CRACK;
        		se.particleCount = 2;
        		se.radius = 0.8;
        		se.duration = 1;
        		se.material = Material.DEAD_BUSH;
        		se.start();	
        		if(e.getHitBlock() != null)
        		{
            		e.getHitBlock().getLocation().getWorld().playSound(e.getHitBlock().getLocation(), Sound.BLOCK_GRASS_BREAK, 2, (float) 0.7);	
        		}
        		if(e.getHitEntity() != null && e.getHitEntity() instanceof LivingEntity)
        		{
        			LivingEntity target = (LivingEntity) e.getHitEntity();
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
        						AbilityUtils.root(p, target, "Thorn Shot", time*20, breakondamage);
        						targets.put(p, target);
        						target.getWorld().playSound(target.getLocation(), Sound.BLOCK_GRASS_BREAK, 2, (float) 0.1);
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
        	        			d.particlesStrand = 5;
        	        			d.particle = Particle.BLOCK_CRACK;
        	        			d.material = Material.DEAD_BUSH;
        	        			d.iterations = 100;
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
        					if(!AbilityUtils.isRooted(target) && target.isOnGround())
        					{				
        						Disable(p, target);
        						this.cancel();
        					}

        					}
        				
        			}.runTaskTimer(VallendiaMinigame.getInstance(), 0, 2);
        			

        					
        			BukkitTask damage = new BukkitRunnable()
        					{
        				int t = 1;

								@Override
								public void run() {
									// TODO Auto-generated method stub
									if(t >= cycleCount)
									{
										damagetimers.remove(target);
										this.cancel();
									}
									
									AbilityUtils.damageEntity(target, (LivingEntity) e.getEntity().getShooter(), persec);
									
									t++;
									
								}
        				
        					}.runTaskTimer(VallendiaMinigame.getInstance(), 0, period*20);
        					
        					damagetimers.put(target, damage);
        		}
         
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
        			AbilityUtils.removeRoot(target, "Thorn Shot");
        		}
        		if(targets.containsKey(p))
        		{
        			targets.remove(p);
        		}
        		if(damagetimers.containsKey(target))
        		{
        			damagetimers.get(target).cancel();
        		}
        	}
            

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(targets.containsKey(p))
		{
			Disable(p, targets.get(p));
		}
		
		if(damagetimers.containsKey(p))
		{
			damagetimers.get(p).cancel();
		}
		
	}
 
 

}