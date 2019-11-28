package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class FlyAbility implements Ability, Listener{
	private static ArrayList<Player> enabled = new ArrayList<>();
	private static ArrayList<Player> falling = new ArrayList<>();
	private static HashMap<Player, BukkitTask> fallingTimer = new HashMap<>();
	private static HashMap<Player, BukkitTask> tasks = new HashMap<>();
	private static HashMap<Player, BukkitTask> countDown = new HashMap<>();
	private static HashMap<Player, BukkitTask> timer = new HashMap<>();
	private static HashMap<Player,SphereEffect> effect = new HashMap<>();
	int time = 30;
	double upwardVelocity = 20 / 10D;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Fly";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You become as light as a feather and gain the ability" ,
				"to fly for " + time + " seconds. Landing will cancel this ability and",
				"after the flight time has ended, you will fall back to the ground slowly.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.FEATHER);
	}

	@Override
	public boolean RunAbility(Player p) {
		if(!enabled.contains(p) && !falling.contains(p))
		{
			enabled.add(p);
            CraftEntity ep = (CraftEntity)p;
            
            //shoot player up
            Vector v = p.getLocation().getDirection();
    		v.setY(0).normalize().setY(upwardVelocity);
    		p.setVelocity(v);
    		
    		//Start glide
            ep.getHandle().setFlag(7, true);
    		
    		//Effects / sounds
	 	 	p.getWorld().spawnParticle(Particle.CLOUD, p.getLocation(), 20);
	 	 	p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1, (float) 1.8);
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.setEntity(p);
			se.disappearWithOriginEntity = true;
			se.infinite();
			se.particle = Particle.CLOUD;
			se.radius = 0.6;
			se.particles = 1;
			se.yOffset = -0.8;
			se.speed = (float) 0;
			se.start();
			effect.put(p, se);
			
			//Start cancel timer
    		BukkitTask timer = new BukkitRunnable() {
                @Override
                public void run() {
        			if(enabled.contains(p))
        			{
        		         enabled.remove(p);
        		         ep.getHandle().setFlag(7, false);
        		         tasks.remove(p);
        		         FlyAbility.timer.remove(p);
        		         effect.get(p).cancel();
        		         effect.remove(p);
        		         countDown.remove(p);
        		         
        		         //Start falling
        		         	falling.add(p);
        		    		BukkitTask task = new BukkitRunnable() {
        		                @Override
        		                public void run() {
        		                	if(falling.contains(p))
        		                	{
    		                			p.setFallDistance(0);
        		                		if(p.isOnGround())
        		                		{
        		                			falling.remove(p);
        		                		}
        		                	}else{this.cancel();}
        		                }
        		            }.runTaskTimer(VallendiaMinigame.getInstance(),1L , 1L);
        		            fallingTimer.put(p, task);
        		         
        		         
        			}
                }
            }.runTaskLater(VallendiaMinigame.getInstance(),time*20L); 
            FlyAbility.timer.put(p, timer);
			
            //Flying timer
    		BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
        			if(enabled.contains(p))
        			{
        	               if (p.isOnGround()) {
        	                   ep.getHandle().setFlag(7, false);
        	                   enabled.remove(p);
        	                   this.cancel();
        	                   tasks.remove(this);
        	                   FlyAbility.timer.get(p).cancel();
              		         FlyAbility.timer.remove(p);
            		         effect.get(p).cancel();
            		         effect.remove(p);
            		         countDown.remove(p);
        	                }
        				
        	               if (!p.isOnGround()) {
        	            	   p.setFallDistance(0);
        	                   p.setVelocity(p.getLocation().getDirection().multiply(0.8));
        	                }
        			}
                }
            }.runTaskTimer(VallendiaMinigame.getInstance(),0L , 0L);
            tasks.put(p, task);
            
            
            //Count down to warn the player of the flight ending
        	BukkitTask countdown =	new BukkitRunnable() {
    			int x = time;
                @Override
                public void run() {
                	if(enabled.contains(p))
                	{
                		if(x == 10)
                		{
            		        p.sendTitle(Utils.Colorate("&3&lFlight " + x + " seconds"), null, 0, 26, 0);
                		}
                		if(x <= 5)
                		{
            		        p.sendTitle(Utils.Colorate("&3&lFlight " + x + " seconds"), null, 0, 26, 0);
                		}
                		x--;
                	}else this.cancel();
                }
            }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 20L);
            FlyAbility.countDown.put(p, countdown);
            return true;
			
			
		}else {Language.sendAbilityUseMessage(p, "You are already flying!", "Fly");}
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
        CraftEntity ep = (CraftEntity)p;
        if(enabled.contains(p))
        {
            enabled.remove(p);
            ep.getHandle().setFlag(7, false);
            tasks.remove(p);
            FlyAbility.timer.get(p).cancel();
            FlyAbility.timer.remove(p);	
	        effect.get(p).cancel();
	        effect.remove(p);
	        countDown.remove(p);
        }
	}
	
	
	
    public static Listener getListener() {
        return new Listener() {
        	
        	//Slow fall
        	@EventHandler
        	public void onMove(PlayerMoveEvent e) {
        		if(!falling.contains(e.getPlayer()))
        		{
        			return;
        		}
        	    if (e.getFrom().getBlockY() != e.getTo().getBlockY()) {
        	        Vector v = e.getPlayer().getVelocity();
        	        if (v.getY() < -0.10) {
        	            v.setY(-0.2);
        	            e.getPlayer().setVelocity(v);
        	        }

        	    }
        	}
        	
        	@EventHandler
        	public void onGlide(EntityToggleGlideEvent e)
        	{
        		if(!(e.getEntity() instanceof Player))
        		{
        			return;
        		}
        		Player p = (Player) e.getEntity();
        		if(!(p.getInventory().getChestplate().getType() == Material.ELYTRA))
        		{
            		e.setCancelled(true);	
        		}
        	}
        	
        	@EventHandler
        	public void onDamage(EntityDamageEvent e)
        	{
        		if(e.getEntity() instanceof Player && e.getCause() == DamageCause.FLY_INTO_WALL)
        		{
        			Player p = (Player) e.getEntity();
            		if(!(p.getInventory().getChestplate().getType() == Material.ELYTRA))
            		{
                		e.setCancelled(true);	
            		}
        		}
        	}
        	
        };
    }

}
