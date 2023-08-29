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
import org.bukkit.entity.Entity;
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

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class ThunderShotAbility implements Ability, Listener{
	 //made by Emma
	Map<Projectile,SphereEffect> arrow = new HashMap<>();
	ArrayList<Player> abilityActive = new ArrayList<>();
	Map<Player, BukkitTask> timers = new HashMap<>();
	Map<LivingEntity, BukkitTask> targets = new HashMap<>();
    int delay = 15;
    int radius = 8;
    int initialdamage = 5;
    int tickdamage = 3;
    int time = 10;
    int period = 2;
    int cycleCount = time/period;
    int damage = tickdamage/cycleCount;
   

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Thunder Shot";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Your arrow calls down a lightning bolt that",
				"damages enemies within " + radius + " blocks for " + initialdamage,
				"damage. The enemies are then electrified for " + time,
				"seconds, taking " + tickdamage + " damage every " + period + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack (Material.CHORUS_PLANT);
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
			Language.sendAbilityUseMessage(p, "Thunder Shot is now Active", "Thunder Shot");
			
			BukkitTask timer = new BukkitRunnable()
			{

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(abilityActive.contains(p))
					{
					
					abilityActive.remove(p);
					Language.sendAbilityUseMessage(p, "Thunder Shot is no longer active", "Thunder Shot");
					
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
        		

        		{
            		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
            		se.setEntity(e.getProjectile());
            		se.particle = Particle.REDSTONE;
            		se.color = Color.TEAL;
            		se.particleCount = 1;
            		se.radius = 0.1;
            		se.particles = 1;
            		se.start();	
            		arrow.put((Projectile)e.getProjectile(), se);	
        		}
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
            	if(!(e.getEntity().getShooter() instanceof Player))
            	{
            		return;
            	}
            	Player p = (Player) e.getEntity().getShooter();
        		arrow.get(e.getEntity()).cancel();
                arrow.remove(e.getEntity());
        		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
        		se.setEntity(e.getEntity());
        		se.particle = Particle.REDSTONE;
        		se.particleCount = 1;
        		se.radius = 7;
        		se.duration = 1;
        		se.color = Color.AQUA;
        		se.start();	
        		if(e.getHitBlock() != null)
        		{
            		
        			Location loc = e.getHitBlock().getLocation();
        			new BukkitRunnable()
        			{

						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							p.getWorld().strikeLightningEffect(loc);	
							
							for(Entity e : AbilityUtils.getAoeTargets(p, loc, radius, radius, radius))
							{
								
								AbilityUtils.damageEntity((LivingEntity)e, p, initialdamage);
								
								BukkitTask timer = new BukkitRunnable()
										{
									int t = 1;
										
											@Override
											public void run() {
												// TODO Auto-generated method stub
												if(t>=cycleCount)
												{
													targets.remove(e);
													this.cancel();
												}
												
												AbilityUtils.damageEntity((LivingEntity) e, p, damage);
												
												SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
												se.setEntity(e);
												se.disappearWithOriginEntity = true;
												se.iterations = 10;
												se.particle = Particle.CRIT_MAGIC;
												se.radius = 0.5;
												se.particleOffsetY = (float) 0.5;
												se.particles = 1;
												se.yOffset = -0.8;
												se.speed = (float) 0;
												se.start();
												
												SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
												se2.setEntity(e);
												se2.disappearWithOriginEntity = true;
												se2.iterations = 10;
												se2.particle = Particle.REDSTONE;
												se2.color = Color.SILVER;
												se2.radius = 0.3;
												se2.particleOffsetY = (float) 0.5;
												se2.particles = 1;
												se2.yOffset = -0.8;
												se2.speed = (float) 0;
												se2.start();
								        		
												t++;
											}
									
										}.runTaskTimer(VallendiaMinigame.getInstance(), 10 , period*20);
										
										targets.put((LivingEntity) e, timer);
								
							}
							
						}
        				
        			}.runTaskLater(VallendiaMinigame.getInstance(), 10);

        			
        			
        		}
        		if(e.getHitEntity() != null && e.getHitEntity() instanceof LivingEntity)
        		{
        			LivingEntity entity = (LivingEntity) e.getHitEntity();
        			Location loc = entity.getLocation();
        			new BukkitRunnable()
        			{

						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							p.getWorld().strikeLightningEffect(loc);	
							
							for(Entity e : AbilityUtils.getAoeTargets(p, loc, radius, radius, radius))
							{
								
								AbilityUtils.damageEntity((LivingEntity) e, p, initialdamage);
								
								BukkitTask timer = new BukkitRunnable()
										{
									int t = 1;
										
											@Override
											public void run() {
												// TODO Auto-generated method stub
												if(t>=cycleCount)
												{
													targets.remove(e);
													this.cancel();
												}
												
												AbilityUtils.damageEntity((LivingEntity) e, p, damage);
												
												SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
												se.setEntity(e);
												se.disappearWithOriginEntity = true;
												se.iterations = 10;
												se.particle = Particle.CRIT_MAGIC;
												se.radius = 0.5;
												se.particleOffsetY = (float) 0.5;
												se.particles = 1;
												se.yOffset = -0.8;
												se.speed = (float) 0;
												se.start();
												
												SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
												se2.setEntity(e);
												se2.disappearWithOriginEntity = true;
												se2.iterations = 10;
												se2.particle = Particle.REDSTONE;
												se2.color = Color.SILVER;
												se2.radius = 0.3;
												se2.particleOffsetY = (float) 0.5;
												se2.particles = 1;
												se2.yOffset = -0.8;
												se2.speed = (float) 0;
												se2.start();
								        		
												
												
												t++;
												
											}
									
										}.runTaskTimer(VallendiaMinigame.getInstance(), 10 , period*20);
										
										targets.put((LivingEntity) e, timer);
								
							}
							
						}
        				
        			}.runTaskLater(VallendiaMinigame.getInstance(), 10);
        			

        		}
         
            }
            

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(abilityActive.contains(p))
		{
			abilityActive.remove(p);
		}
		
		if(targets.containsKey(p))
		{
			targets.get(p).cancel();
			targets.remove(p);
		}
		
	}
 
 

}