package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

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

public class FireBlastAbility implements Ability{
	int damage = 4;
	int radius = 2;
	double speed = 0.7;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Fire Blast";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Throw a projectile in a straight line which",
							"explodes, dealing " + damage + " damage in a " + radius,
							"block radius and setting enemies on fire");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.MAGMA_CREAM);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		p.getWorld().playSound(p.getLocation(), Sound.ITEM_FIRECHARGE_USE, 1, (float) 1);
		
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
             		se.particle = Particle.FLAME;
           		se.iterations = 1;
           		se.particles = 4;
           		se.radius = 0.4;
           		se.speed = (float) 0;
           		se.visibleRange = 50;
         			se.setLocation(loc);
         			se.start();

         			
              		SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
             		se2.particle = Particle.FLAME;
             		se2.iterations = 1;
             		se2.particles = 10;
             		se2.radius = 4;
             		se2.speed = (float) 0;
             		se2.visibleRange = 50;
	                
	               if (loc2.getBlock().getType().isSolid())
	                {
        				onHit(p, loc);
                     	 se2.setLocation(loc);
                     	 se2.start();
	                	this.cancel();
	                	return;
	                }
	                
         			
         			//block behind the particle incase the particle passes thru a block
 	                Location locBehind = se.getLocation();
 	                Vector dir2 = locBehind.getDirection().normalize().multiply(-1);
 	                locBehind.add(dir2);
	                     if(loc.getBlock().getType().isSolid() || 
	                    		 locBehind.getBlock().getType().isSolid())
	                     {
	         				onHit(p, loc);
	                      	 se2.setLocation(loc);
	                      	 se2.start();
	                    	 this.cancel();
	                    	 return;
	                     }
         			for(Entity e : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5))
         			{
         				if(e instanceof LivingEntity && e != p)
         				{
                            if(AbilityUtils.partyCheck(p, (Player) e))
                            {
                                continue;
                            }
         					onHit(p, loc);
                			se2.setEntity(e);
                			se2.start();
         					this.cancel();
         					return;
         				}
         			}
	             
	                loc.subtract(x,y,z);
	                loc2.subtract(x2,y2,z2);
	             
	                if(t >= 30){
	                    this.cancel();
	                }
	             
	                t++;
	                t2++;
	             
	            }
	         
	        }.runTaskTimer(VallendiaMinigame.getInstance(), 0l, 0l);
		return true;
	}
	
	public void onHit(Player p, Location loc)
	{
		AbilityUtils.explode(loc, p, 2, damage, true, true, false);
		for(Entity e: AbilityUtils.getAoeTargets(p, loc, radius, radius, radius))
		{
			e.setFireTicks(25);
		}
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}