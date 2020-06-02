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

public class IceShardAbility implements Ability{
	int slow = 5;
	int damage = 3;
	double speed = 1;
	int amplifier = 1;
	int distance = 100;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Ice Shard";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Throw a long range projectile in a straight line",
							"dealing " + damage + " damage to an enemy directly",
							"hit and slowing them for " + slow + " seconds");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.PRISMARINE_SHARD);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SPLASH_POTION_THROW, 1, (float) 1);
		
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
              		se.particle = Particle.BLOCK_CRACK;
              		se.material = Material.PACKED_ICE;
            		se.iterations = 1;
            		se.particles = 3;
            		se.radius = 0.2;
            		se.speed = (float) 0;
            		se.visibleRange = 101;
          			se.setLocation(loc);
          			se.start();
          			
               		SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
              		se2.particle = Particle.BLOCK_CRACK;
              		se2.material = Material.PACKED_ICE;
              		se2.iterations = 1;
              		se2.particles = 4;
              		se2.radius = 2;
              		se2.particleOffsetX = 1.2F;
              		se2.speed = (float) 0;
              		se2.visibleRange = 101;
              		
              		
                    SphereEffect se3 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
                   se3.particle = Particle.REDSTONE;
                   se3.color = Color.WHITE;
                   se3.iterations = 1;
                   se3.particles = 4;
                   se3.radius = 0.2;
                   se3.particleOffsetX = 1.2F;
                   se3.speed = (float) 0;
                   se3.visibleRange = 101;
                   
                   se3.setLocation(loc);
                   se3.start();
	                
	               if (loc2.getBlock().getType().isSolid())
	                {
                  	 p.getWorld().playSound(loc, Sound.BLOCK_GLASS_BREAK, 2, 1.2F);
                 	 p.getWorld().playSound(loc, Sound.BLOCK_GLASS_BREAK, 2, 0.6F);
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
	                      	 p.getWorld().playSound(loc, Sound.BLOCK_GLASS_BREAK, 2, 1.2F);
                         	 p.getWorld().playSound(loc, Sound.BLOCK_GLASS_BREAK, 2, 0.6F);
	                      	 se2.setLocation(loc);
	                      	 se2.start();
	                    	 this.cancel();
	                    	 return;
	                     }
          			for(Entity e : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5))
          			{
          				if(e instanceof LivingEntity && e != p)
          				{
          					if(e instanceof Player)
          					{
                                if(AbilityUtils.partyCheck(p, (Player) e))
                                {
                                    continue;
                                }	
          					}
                         	 p.getWorld().playSound(loc, Sound.BLOCK_GLASS_BREAK, 2, 1.2F);
                         	 p.getWorld().playSound(loc, Sound.BLOCK_GLASS_BREAK, 2, 0.6F);
                 			se2.setEntity(e);
                 			se2.start();
              				AbilityUtils.damageEntity((LivingEntity) e, p, damage);
              				AbilityUtils.addPotionDuration(p, (LivingEntity) e, "Ice Shard", PotionEffectType.SLOW, amplifier, slow*20);
          					this.cancel();
          					return;
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

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}