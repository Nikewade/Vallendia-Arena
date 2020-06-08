package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

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
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class GhostTouchAbility implements Ability{
	int damage = 4;
	int blind = 10;
	int distance = 30;
	double speed = 0.5;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Ghost Touch";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You shoot a blast of energy that travels through",
							"another plane of existence allowing it to fly through",
							"solid matter. This energy damages your target for " + damage,
							"for " + blind + " seconds. This will pass through walls and players.");
		
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Utils.getItem("eyJ0aW1lc3RhbXAiOjE1NzA3Mzg4NjMzODIsInByb2ZpbGVJZCI6ImZkNjBmMzZmNTg2MTRmMTJiM2NkNDdjMmQ4NTUyOTlhIiwicHJvZmlsZU5hbWUiOiJSZWFkIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9iMWM0NDUxZWIzYmNiMDVlZTU4MDNlNDRhNDAyNWU1ZDk4YjYxYjYxZDU1OTc4ZmY5M2M3ZGI0NmFhODc5YjQwIn19fQ=="));
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_AMBIENT, 2, (float) 0.3);
		
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
             		se.particle = Particle.REDSTONE;
             		se.color = Color.fromRGB(47, 0, 92);
             		se.iterations = 3;
             		se.particles = 10;
             		se.radius = 0.8;
             		se.particleOffsetX = (float) 0.8;
             		se.speed = (float) 0;
             		se.visibleRange = 50;
         			se.setLocation(loc);
         			se.start();
         			
               		SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
             		se2.particle = Particle.REDSTONE;
             		se2.color = Color.fromRGB(81, 73, 89);
             		se2.iterations = 3;
             		se2.particles = 10;
             		se2.radius = 0.6;
             		se2.particleOffsetX = (float) 0.8;
             		se2.speed = (float) 0;
             		se2.visibleRange = 50;
         			se2.setLocation(loc);
         			se2.start();
             		
              		SphereEffect se3 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
             		se3.particle = Particle.SUSPENDED_DEPTH;
             		se3.iterations = 1;
             		se3.particles = 20;
             		se3.radius = 0.5;
             		se3.speed = (float) 0;
             		se3.visibleRange = 50;
             		se3.setLocation(loc);
             		se3.start();

         			for(Entity e : AbilityUtils.getAoeTargets(p, loc, 0.8, 0.8, 0.8))
         			{
         				if(e instanceof LivingEntity && e != p)
         				{
                      		SphereEffect se4 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
                     		se4.particle = Particle.REDSTONE;
                     		se4.color = Color.fromRGB(47, 0, 92);
                     		se4.iterations = 100;
                     		se4.particles = 2;
                     		se4.radius = 0.5;
                     		se4.particleOffsetX = 1.7F;
                     		se4.speed = (float) 0;
                     		se4.visibleRange = 50;
                     		se4.disappearWithOriginEntity = true;
                     		se4.setEntity(e);
                     		se4.start();
                     		
         					e.getWorld().playSound(e.getLocation(), Sound.ENTITY_ENDERMEN_AMBIENT, 1, (float) 0.4);
             				AbilityUtils.damageEntity((LivingEntity) e, p, damage);
             				AbilityUtils.addPotionDuration(p, (LivingEntity) e, "Ghost Touch", PotionEffectType.BLINDNESS, 2, blind*20);
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
	        
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}