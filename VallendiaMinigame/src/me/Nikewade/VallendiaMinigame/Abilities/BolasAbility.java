package me.Nikewade.VallendiaMinigame.Abilities;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.FountainEffect;
import de.slikey.effectlib.effect.SphereEffect;
import de.slikey.effectlib.util.DynamicLocation;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class BolasAbility implements Ability{
	int time = 10;
	int distance = 20;
	int damage = 4;
	double speed = 0.06;
	Map<LivingEntity, FountainEffect> particles = new HashMap<>();
	Map<Player, LivingEntity> targets = new HashMap<>();
	Boolean breakondamage = true;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Bolas";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Throw bolas forward " + distance + " blocks.",
				"Enemies hit, are rooted for " + time + " seconds",
				"and delt " + damage + " damage.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(402, 1);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LINGERINGPOTION_THROW, 1, 0.3F);
		   new BukkitRunnable(){
	            double t = 1;
	            Location loc = p.getLocation();
	            Vector dir = loc.getDirection().normalize();
	            
	            double t2 = 1;
	            Location loc2 = p.getLocation();
	            Vector dir2 = loc2.getDirection().normalize();
	         
	            @Override
	            public void run() {
    			
	            	t = t + speed;
	                double x = dir.getX() * t;
	                double y = dir.getY() * t + 1.5D;
	                double z = dir.getZ() * t;
	                loc.add(x,y,z);
	                
	            	t2 = t2 + speed;
	                double x2 = dir.getX() * t2;
	                double y2 = dir.getY() * t2 + 1.5D;
	                double z2 = dir.getZ() * t2;
	                loc2.add(x2,y2,z2);
	                

          		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
            		se.particle = Particle.CRIT;
            		se.iterations = 2;
            		se.particles = 10;
            		se.radius = 0.2;
            		se.speed = (float) 0;
            		se.visibleRange = 50;
            		se.particleOffsetX = 0.5F;
            		se.particleOffsetZ = 0.5F;
        			se.setLocation(loc);
        			se.start();
        			

        			for(Entity target : AbilityUtils.getAoeTargets(p, loc, 0.8, 0.8, 0.8))
        			{
        				if(target instanceof LivingEntity && target != p)
        				{
        					p.getWorld().playSound(p.getLocation(), Sound.UI_TOAST_OUT, 5, (float) 1.4);
            				AbilityUtils.damageEntity((LivingEntity) target, p, damage);
            				if(AbilityUtils.isRooted((LivingEntity) target))
            				{
            					AbilityUtils.removeAllRoots((LivingEntity) target);
            					Disable(p, (LivingEntity) target);
            				}
            				new BukkitRunnable()
            				{

            					@Override
            					public void run() {
            						// TODO Auto-generated method stub
            						if(target.isOnGround())
            						{
            							AbilityUtils.root(p, (LivingEntity) target, "Entangle", time*20, breakondamage);
            							targets.put(p, (LivingEntity) target);
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
            							d.particle = Particle.CRIT;
            							d.iterations = 500;
            							d.start();
            							particles.put((LivingEntity) target, d);
            							this.cancel();
            						}
            						
            					}
            					
            				}.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
            				
            				new BukkitRunnable()
            				{

            					@Override
            					public void run() {
            						// TODO Auto-generated method stub
            						if(!AbilityUtils.isRooted((LivingEntity) target)  && target.isOnGround())
            						{				
            							Disable(p, (LivingEntity) target);
            							this.cancel();
            						}

            						}
            					
            				}.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
            				this.cancel();
            				
        				}
        			}
	             
	                loc.subtract(x,y,z);
	                loc2.subtract(x2,y2,z2);
	             
	                if(t >= distance){
	                    this.cancel();
	                }
	             
	                t++;
	                t2++;
	             
	            }
	         
	        }.runTaskTimer(VallendiaMinigame.getInstance(), 0l, 0l);
		
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
			AbilityUtils.removeRoot(target, "Entangle");
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