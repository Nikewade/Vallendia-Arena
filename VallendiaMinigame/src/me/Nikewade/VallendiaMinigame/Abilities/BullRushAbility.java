package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Stairs;
import org.bukkit.material.Step;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import net.minecraft.server.v1_12_R1.BlockStairs;

public class BullRushAbility implements Ability, Listener{
	private static int enabledTime = 20;
	private static int damage = 8;
	private static int force = 20;
	private static int yForce = 6;
	private static int maxYForce = 6;
	private static ArrayList<Player> enabled = new ArrayList<>();
	private static ArrayList<Player> sprinting = new ArrayList<>();
	private static ArrayList<Player> hasspeed = new ArrayList<>();
	private static HashMap<Player, BukkitTask> tasks = new HashMap<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Bull Rush";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("For " + enabledTime + " seconds, you gain a burst of speed.",
				"Running into a player knocks them back and does",
				"" + damage + " damage, and if run into a wall you will smash" , 
				"through the blocks. If at any point",
				"you stop running, the ability will cancel.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.IRON_BARDING);
	}

	@Override
	public boolean RunAbility(Player p) {
		if(enabled.contains(p))
		{
			return false;
		}
		Language.sendAbilityUseMessage(p, "You become an unstoppable force for " + enabledTime + " seconds.", "Bull Rush");
		enabled.add(p);
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 1, (float) 1);
		if(!p.hasPotionEffect(PotionEffectType.SPEED))
		{
			AbilityUtils.addPotionDuration((LivingEntity) p , p, this.getName(), PotionEffectType.SPEED, 1, enabledTime * 20);	
			hasspeed.add(p);
		}
		
		new BukkitRunnable() {
            @Override
            public void run() {
        		if(enabled.contains(p))
        		{
        			enabled.remove(p);
        			sprinting.remove(p);
        	        if(tasks.containsKey(p))
        	        {
        	        	tasks.get(p).cancel();
        	            tasks.remove(p);	
        	        }
        			Language.sendAbilityUseMessage(p, "Your rush comes to a halt.", "Bull Rush");
        			if(p.hasPotionEffect(PotionEffectType.SPEED) && hasspeed.contains(p))
        			{
            			p.removePotionEffect(PotionEffectType.SPEED);
            			hasspeed.remove(p);
        			}
        		}
            }
        }.runTaskLaterAsynchronously(VallendiaMinigame.getInstance(), enabledTime*20L);
        
        
        
		BukkitTask t = new BukkitRunnable() {
            @Override
            public void run() {
            	Location oneBlockAway1;
            	Location oneBlockAway2;
            	if(p.isSprinting())
            	{
            		
            		if(p.getLocation().add(p.getLocation().getDirection()).getY() > p.getLocation().getY())
            		{
                		oneBlockAway1 = p.getLocation().add(p.getLocation().getDirection()).add(0, 0, 0);
                		oneBlockAway2 = p.getLocation().add(p.getLocation().getDirection()).add(0, 1, 0);
            		}else
            		{
                		oneBlockAway1 = p.getLocation().add(p.getLocation().getDirection()).add(0, 1, 0);
                		oneBlockAway2 = p.getLocation().add(p.getLocation().getDirection()).add(0, 2, 0);	
            		}
            		
            		if(oneBlockAway1.getBlock().getType().isSolid() || oneBlockAway2.getBlock().getType().isSolid())
            		{
            			
            			if(!(oneBlockAway1.getBlock().getType() == Material.GRASS) && !(oneBlockAway2.getBlock().getType() == Material.GRASS) && !(oneBlockAway1.getBlock().getType() == Material.DIRT) && !(oneBlockAway2.getBlock().getType() == Material.DIRT) )
            			{
            				if(!oneBlockAway1.getBlock().getType().name().contains("SLAB")  &&  
            						!oneBlockAway1.getBlock().getType().name().contains("STEP"))
            				{
                    			AbilityUtils.explode(oneBlockAway1, p, 2, 3, false, true, false);
                    			AbilityUtils.explode(oneBlockAway2, p, 2, 3, false, true, false);
                                   
                                   
                                   
                           		if(enabled.contains(p))
                        		{
                        			enabled.remove(p);
                        			sprinting.remove(p);
                        	        if(tasks.containsKey(p))
                        	        {
                        	        	tasks.get(p).cancel();
                        	            tasks.remove(p);	
                        	        }
                        			Language.sendAbilityUseMessage(p, "Your rush comes to a halt.", "Bull Rush");
                        			if(p.hasPotionEffect(PotionEffectType.SPEED) && hasspeed.contains(p))
                        			{
                            			p.removePotionEffect(PotionEffectType.SPEED);
                            			hasspeed.remove(p);
                        			}
                        		}
                        		this.cancel();	
            				}else
                				if(oneBlockAway1.getBlock().getType().name().contains("SLAB") && oneBlockAway2.getBlock().getType() != Material.AIR ||  
                						oneBlockAway1.getBlock().getType().name().contains("STEP") && oneBlockAway2.getBlock().getType() != Material.AIR)
                				{
                        			AbilityUtils.explode(oneBlockAway1, p, 2, 3, false, true, false);
                        			AbilityUtils.explode(oneBlockAway2, p, 2, 3, false, true, false);
                                       
                                       
                               		if(enabled.contains(p))
                            		{
                            			enabled.remove(p);
                            			sprinting.remove(p);
                            	        if(tasks.containsKey(p))
                            	        {
                            	        	tasks.get(p).cancel();
                            	            tasks.remove(p);	
                            	        }
                            			Language.sendAbilityUseMessage(p, "Your rush comes to a halt.", "Bull Rush");
                            			if(p.hasPotionEffect(PotionEffectType.SPEED) && hasspeed.contains(p))
                            			{
                                			p.removePotionEffect(PotionEffectType.SPEED);
                                			hasspeed.remove(p);
                            			}
                            		}
                            		this.cancel();	
                				}
            			}
            		}                		

            		
            		
        			Location location = p.getLocation().add(0.0D, -1.0F, 0.0D);
        		    Vector t = location.toVector();
        		    //knockback
            		for(Entity entity : AbilityUtils.getAoeTargets(p, p.getLocation(), 1, 1, 1))
            		{
            			LivingEntity livingE = (LivingEntity) entity;
            		          Vector ve = entity.getLocation().toVector();
              		          Vector v = ve.subtract(t).normalize().multiply(force / 10.0D);
              		          if (force != 0) {
              		            v.setY(v.getY() * (yForce / 10.0D));
              		          } else {
              		            v.setY(yForce / 10.0D);
              		          }
              		          if (v.getY() > maxYForce / 10.0D) {
              		              v.setY(maxYForce / 10.0D);
              		            }
              		          entity.setVelocity(v);	
              		          AbilityUtils.damageEntity(livingE, p, damage);
              	 	 		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 2, (float) 0.8);
              	 	 		entity.getWorld().spawnParticle(Particle.CRIT, entity.getLocation().add(0, 1, 0), 20);
              	 	 		if(livingE != null)
              	 	 		{
              	        		if(enabled.contains(p))
              	        		{
              	        			enabled.remove(p);
              	        			sprinting.remove(p);
              	        	        if(tasks.containsKey(p))
              	        	        {
              	        	        	tasks.get(p).cancel();
              	        	            tasks.remove(p);	
              	        	        }
              	        			Language.sendAbilityUseMessage(p, "Your rush comes to a halt.", "Bull Rush");
              	        			if(p.hasPotionEffect(PotionEffectType.SPEED) && hasspeed.contains(p))
              	        			{
              	            			p.removePotionEffect(PotionEffectType.SPEED);
              	            			hasspeed.remove(p);
              	        			}
              	        		}
              	 	 		}
            		}		
            	}
            }
        }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
        if(!tasks.containsKey(p))
        {
            tasks.put(p, t);	
        }
        
		return true;
	}
	
	
        	@EventHandler
        	public void sprint(PlayerToggleSprintEvent e)
        	{
        		if(!enabled.contains(e.getPlayer()))
        		{
        			return;
        		}
        		Player p = e.getPlayer();
        		if(sprinting.contains(e.getPlayer()) && p.isSprinting())
        		{
            		if(enabled.contains(p))
            		{
            			enabled.remove(p);
            			sprinting.remove(p);
            	        if(tasks.containsKey(p))
            	        {
            	        	tasks.get(p).cancel();
            	            tasks.remove(p);	
            	        }
            			Language.sendAbilityUseMessage(p, "Your rush comes to a halt.", "Bull Rush");
            			if(p.hasPotionEffect(PotionEffectType.SPEED) && hasspeed.contains(p))
            			{
                			p.removePotionEffect(PotionEffectType.SPEED);
                			hasspeed.remove(p);
            			}
            			return;
            		};
        		}
        		
        		sprinting.add(p);
        	}
        	
        	
        	@EventHandler
        	public void onDamage(EntityDamageByEntityEvent e)
        	{
        		
        		if(e.getDamager() instanceof Player && e.getCause() == DamageCause.ENTITY_ATTACK)
        		{
            		Player p = (Player) e.getDamager();
               		if(sprinting.contains(p) && p.isSprinting())
            		{
                		if(enabled.contains(p))
                		{
                			enabled.remove(p);
                			sprinting.remove(p);
                	        if(tasks.containsKey(p))
                	        {
                	        	tasks.get(p).cancel();
                	            tasks.remove(p);	
                	        }
                			Language.sendAbilityUseMessage(p, "Your rush comes to a halt.", "Bull Rush");
                			if(p.hasPotionEffect(PotionEffectType.SPEED) && hasspeed.contains(p))
                			{
                    			p.removePotionEffect(PotionEffectType.SPEED);
                    			hasspeed.remove(p);
                			}
                		}
            		}
               		return;
        		}

        		if(e.getDamager() instanceof Player && e.getCause() == DamageCause.ENTITY_EXPLOSION)
        		{
        			if(enabled.contains(e.getDamager()))
        			{
            			e.setCancelled(true);
        			}
        		}

        	}
        	
        	
        	@EventHandler
        	public void onExplode(EntityExplodeEvent e)
        	{
        		if(e.getEntity() instanceof Player && enabled.contains(e.getEntity()))
        		{
         	 		e.getLocation().getWorld().playSound(e.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 2, 1);
         	 		e.getLocation().getWorld().spawnParticle(Particle.CRIT, e.getLocation().add(0, 0, 0), 20);
        			e.setYield(0);
        		}
        	}
        	

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
