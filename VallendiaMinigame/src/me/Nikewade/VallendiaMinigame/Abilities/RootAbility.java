package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class RootAbility implements Ability{
	private static ArrayList<Player> sneaking = new ArrayList<>();
	private static ArrayList<Player> soundCooldown = new ArrayList<>();
	private static ArrayList<Player> enabled = new ArrayList<>();
	private static Map<Player, BukkitTask> timers = new HashMap<Player, BukkitTask>();
	private static double percent =  0.8 ; //20%

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Root";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Sneak to extend roots into the ground making you" , "immovable. You also take 20% less damage and", "regenerate health over time.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SAPLING);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		if(enabled.contains(p))
		{
			enabled.remove(p);
			p.sendMessage(Utils.Colorate("&3&l[Root] &8Disabled"));
			if(timers.containsKey(p))
			{
	        	timers.get(p).cancel();
	        	timers.remove(p);				
			}
			return true;
		}
		enabled.add(p);
		p.sendMessage(Utils.Colorate("&3&l[Root] &8Enabled"));
		return false;
	}
	
	
	
	   public static Listener getListener() {
	        return new Listener() {
	        	
	        	
	        	
	        	
	            @EventHandler
	            public void onPlayerToggleSneak(PlayerToggleSneakEvent e)
	            {
            		if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(e.getPlayer(), "Root"))
            		{
            			return;
            		}
            		if(!enabled.contains(e.getPlayer()))
            		{
            			return;
            		}
	            	Player p = e.getPlayer();
	        		if(p.isSneaking())
	            	{
	                	sneaking.remove(p);
	                	if(timers.containsKey(p))
	                	{
		                	timers.get(p).cancel();
		                	timers.remove(p);	
	                	}
	                	return;
	            	}
	        		sneaking.add(p);
	        		
	        		
	        		
	        	    BukkitTask task = new BukkitRunnable() {

	                    @Override
	                    public void run() {	
	                        	if(p.getHealth() < p.getMaxHealth())
	                        	{
	                            	if(p.getHealth() >= p.getMaxHealth() - 1)
	                            	{
	                            		p.setHealth(p.getMaxHealth());
	                            	}else
	                        		p.setHealth(p.getHealth() + 1);
	                                p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 0.4, 0.4), 5);
	                                p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 0.4, 0), 5);
	                                p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0.4, 0.4, 0), 5); 
	        	        			p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHOVEL_FLATTEN, 1, (float) 0.1);
	                        	}     
	                    }
	        	    }.runTaskTimer(VallendiaMinigame.getInstance(), 20* 3, 20 * 3);	
	        	
	        	    timers.put(p, task);
	        		
	        		
	        		
	        		
	        		//sound cooldown
	        		if(!soundCooldown.contains(p))
	        		{
		        		soundCooldown.add(p);
	        			p.getWorld().playSound(p.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 1, (float) 0.5);	
	        			
		        		new BukkitRunnable() {
		                    @Override
		                    public void run() {
		            			soundCooldown.remove(p);
		                    }
		                }.runTaskLaterAsynchronously(VallendiaMinigame.getInstance(), 3*20L);
	        		}
	            }
	            
	            
	            @EventHandler
	            public void onMove(PlayerMoveEvent e)
	            {
	            	
            		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(e.getPlayer(), "Root") && e.getPlayer().isSneaking() && !e.getPlayer().isOnGround())
            		{
                		if(!enabled.contains(e.getPlayer()))
                		{
                			return;
                		}
            			if(e.getTo().getY() > e.getFrom().getY())
            			{
        	            	Location to = e.getFrom();
        	            	to.setPitch(e.getTo().getPitch());
        	            	to.setYaw(e.getTo().getYaw());
        	            	e.setTo(to);
            			}
            		
            		}
            		if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(e.getPlayer(), "Root") || !e.getPlayer().isSneaking() || !e.getPlayer().isOnGround())
            		{
            			return;
            		}
            		if(!enabled.contains(e.getPlayer()))
            		{
            			return;
            		}
            		
            			if(!(e.getPlayer().getFallDistance() > 3))
            			{
            				e.getPlayer().setFallDistance(0);
            			}
            		
	            	Location to = e.getFrom();
	            	to.setPitch(e.getTo().getPitch());
	            	to.setYaw(e.getTo().getYaw());
	            	e.setTo(to);
	            }
	            
	            @EventHandler
	            public void onVelocity(PlayerVelocityEvent e)
	            {
            		if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(e.getPlayer(), "Root") || !e.getPlayer().isSneaking() || !e.getPlayer().isOnGround())
            		{
            			return;
            		}
            		if(!enabled.contains(e.getPlayer()))
            		{
            			return;
            		}
            		e.setCancelled(true);
	            }
	            
            	@EventHandler
            	public void onDamage(EntityDamageEvent e)
            	{
            		double damage = e.getDamage();
            		double lowerDamage = damage * percent;

            		if(e.getEntity() instanceof Player)
            		{
            			
            			Player p = (Player) e.getEntity();
                		if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Root") || !p.isSneaking() || !p.getPlayer().isOnGround())
                		{
                			return;
                		}
                		if(!enabled.contains(p))
                		{
                			return;
                		}
                		p.sendMessage("d");
                		e.setDamage(lowerDamage);

            		}
            	}



	            
	            
	            
	            
	            
	            
	        };
	    }
	
	   public static void removeLists(Player p)
	   {
		   if(enabled.contains(p))
		   {
			   RootAbility.enabled.remove(p);   
		   }
		   if(sneaking.contains(p))
		   {
			   RootAbility.sneaking.remove(p);   
		   }
		   if(timers.containsKey(p))
		   {
			   RootAbility.timers.get(p).cancel();
			   RootAbility.timers.remove(p);   
		   }
	   }
	   
}
