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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.effect.FountainEffect;
import de.slikey.effectlib.util.DynamicLocation;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class RootAbility implements Ability{
	private static ArrayList<Player> sneaking = new ArrayList<>();
	private static ArrayList<Player> soundCooldown = new ArrayList<>();
	private static ArrayList<Player> enabled = new ArrayList<>();
	private static Map<Player, BukkitTask> timers = new HashMap<Player, BukkitTask>();
	private  static Map<Entity,Effect> particle1 = new HashMap<>();
	private  static Map<Entity,Effect> particle2 = new HashMap<>();
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
	        			if(particle1.containsKey(p))
	        			{
	        				particle1.get(p).cancel();
	        				particle1.remove(p);
	        				particle2.get(p).cancel();
	        				particle2.remove(p);
	        			}
	                	return;
	            	}
	        		sneaking.add(p);
	        		
	        		if(p.isOnGround())
	        		{
	        			if(!particle1.containsKey(p))
	        			{
	    	        		FountainEffect f = new FountainEffect(VallendiaMinigame.getInstance().effectmanager);
        	        		f.setTargetEntity(e.getPlayer());
        	        		f.disappearWithTargetEntity = true;
	    	        		f.setDynamicOrigin(new DynamicLocation(e.getPlayer().getLocation().add(0, 0.1, 0)));
	    	        		f.strands = 3;
	    	        		f.height = (float) 0.6;
	    	        		f.heightSpout = 0;
	    	        		f.radiusSpout = 0;
	    	        		f.radius = (float) 1.8;
	    	        		f.particlesStrand = 5;
	    	        		f.particlesSpout = 0;
	    	        		f.particle = Particle.BLOCK_CRACK;
	    	        		f.material = Material.DIRT;
        	        		f.infinite();
        	        		f.start();
 
	    	        		
	    	        		
	    	        		FountainEffect d = new FountainEffect(VallendiaMinigame.getInstance().effectmanager);
        	        		d.setTargetEntity(e.getPlayer());
        	        		d.disappearWithTargetEntity = true;
	    	        		d.setDynamicOrigin(new DynamicLocation(e.getPlayer().getLocation().add(0, 0.1, 0)));
	    	        		d.strands = 3;
	    	        		d.height = (float) 0.6;
	    	        		d.heightSpout = 0;
	    	        		d.particlesSpout = 0;
	    	        		d.radiusSpout = 0;
	    	        		d.radius = (float) 1.8;
	    	        		d.particlesStrand = 5;
	    	        		d.particle = Particle.CRIT;
        	        		d.infinite();
	    	        		d.start();
	    	        		
	    	        		particle1.put((Entity)e.getPlayer(), f);
	    	        		particle2.put((Entity)e.getPlayer(), d);
	        			}
	        		}
	        		
	        		
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
	        	        			
	        		        		FountainEffect e = new FountainEffect(VallendiaMinigame.getInstance().effectmanager);
	        		        		e.setDynamicOrigin(new DynamicLocation(p.getLocation().add(0, 0.1, 0)));
	        		        		e.strands = 3;
	        		        		e.height = (float) 0.6;
	        		        		e.iterations = 5;
	        		        		e.heightSpout = 0;
	        		        		e.radiusSpout = 0;
	        		        		e.radius = (float) 1.8;
	        		        		e.particlesStrand = 5;
	        		        		e.particle = Particle.TOTEM;
	        		        		e.start();
	                        	}     
	                    }
	        	    }.runTaskTimer(VallendiaMinigame.getInstance(), 20* 3, 20 * 3);	
	        	
	        	    timers.put(p, task);
	        		
	        		
	        		
	        		
	        		//sound cooldown
	        		if(!soundCooldown.contains(p))
	        		{
		        		soundCooldown.add(p);
	        			p.getWorld().playSound(p.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 2, (float) 0.5);	
	        			
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
            			
            			if(e.getFrom().getY() > e.getTo().getY())
            			{
            				return;
            			}
            		
            			if(!particle1.containsKey(e.getPlayer()))
            			{
        	        		FountainEffect f = new FountainEffect(VallendiaMinigame.getInstance().effectmanager);
        	        		f.setTargetEntity(e.getPlayer());
        	        		f.disappearWithTargetEntity = true;
        	        		f.setDynamicOrigin(new DynamicLocation(e.getPlayer().getLocation().add(0, 0.1, 0)));
        	        		f.strands = 3;
        	        		f.height = (float) 0.6;
        	        		f.heightSpout = 0;
	    	        		f.particlesSpout = 0;
        	        		f.radiusSpout = 0;
        	        		f.radius = (float) 1.8;
        	        		f.particlesStrand = 5;
        	        		f.particle = Particle.BLOCK_CRACK;
        	        		f.material = Material.DIRT;
        	        		f.infinite();
        	        		f.start();
        	        		
        	        		
        	        		FountainEffect d = new FountainEffect(VallendiaMinigame.getInstance().effectmanager);
        	        		d.setTargetEntity(e.getPlayer());
        	        		d.disappearWithTargetEntity = true;
        	        		d.setDynamicOrigin(new DynamicLocation(e.getPlayer().getLocation().add(0, 0.1, 0)));
        	        		d.strands = 3;
        	        		d.height = (float) 0.6;
        	        		d.heightSpout = 0;
        	        		d.radiusSpout = 0;
	    	        		d.particlesSpout = 0;
        	        		d.radius = (float) 1.8;
        	        		d.particlesStrand = 5;
        	        		d.particle = Particle.CRIT;
        	        		d.infinite();
        	        		d.start();
        	        		
        	        		particle1.put((Entity)e.getPlayer(), f);
        	        		particle2.put((Entity)e.getPlayer(), d);
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
