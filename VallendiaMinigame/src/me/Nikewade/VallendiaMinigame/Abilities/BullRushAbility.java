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
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class BullRushAbility implements Ability, Listener{
	private static int enabledTime = 20;
	private static int damage = 7;
	private static int force = 20;
	private static int yForce = 6;
	private static int maxYForce = 6;
	private static ArrayList<Player> enabled = new ArrayList<>();
	private static ArrayList<Player> sprinting = new ArrayList<>();
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
		return Arrays.asList("For " + enabledTime + " seconds, running into a player knocks",
				"them back and does "  + damage + " damage. If at any point" , 
				"you stop running, the ability will cancel.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.IRON_BARDING);
	}

	@Override
	public boolean RunAbility(Player p) {
		Language.sendAbilityUseMessage(p, "You become an unstoppable force for " + enabledTime + " seconds.", "Bull Rush");
		enabled.add(p);
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 1, (float) 1);
		AbilityUtils.addPotionDuration(p, PotionEffectType.SPEED, 1, enabledTime * 20);
		
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
        			if(p.hasPotionEffect(PotionEffectType.SPEED))
        			{
            			p.removePotionEffect(PotionEffectType.SPEED);	
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
                		oneBlockAway1.getBlock().setMetadata("Bull Rush", new FixedMetadataValue(VallendiaMinigame.getInstance(), oneBlockAway1));
                		oneBlockAway2.getBlock().setMetadata("Bull Rush", new FixedMetadataValue(VallendiaMinigame.getInstance(), oneBlockAway2));
            			oneBlockAway2.getWorld().createExplosion(oneBlockAway2, 2, false);
            			oneBlockAway1.getWorld().createExplosion(oneBlockAway1, 2, false);
                           
                           
                           
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
                			if(p.hasPotionEffect(PotionEffectType.SPEED))
                			{
                    			p.removePotionEffect(PotionEffectType.SPEED);	
                			}
                		}
            		}                		

            		
            		
        			Location location = p.getLocation().add(0.0D, -1.0F, 0.0D);
        		    Vector t = location.toVector();
        		    //knockback
            		for(Entity entity : AbilityUtils.getAoeTargets(p, 1, 1, 1))
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
              		          livingE.damage(5, p);
              	 	 		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 2, (float) 0.8);
              	 	 		entity.getWorld().spawnParticle(Particle.CRIT, entity.getLocation().add(0, 1, 0), 20);
 
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
	
	
    public static Listener getListener() {
        return new Listener() {
        	
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
            			if(p.hasPotionEffect(PotionEffectType.SPEED))
            			{
                			p.removePotionEffect(PotionEffectType.SPEED);	
            			}
            			return;
            		};
        		}
        		
        		sprinting.add(p);
        	}
        	
        	
        	@EventHandler
        	public void onDamage(EntityDamageByEntityEvent e)
        	{
        		if(!(e.getDamager() instanceof Player) || e.getCause() != DamageCause.ENTITY_ATTACK)
        		{
        			return;
        		}
        		
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
            			if(p.hasPotionEffect(PotionEffectType.SPEED))
            			{
                			p.removePotionEffect(PotionEffectType.SPEED);	
            			}
            			return;
            		};
        		}
        		
        	}
        	
        	
        	@EventHandler
        	public void onExplode(BlockExplodeEvent e)
        	{
        		if(e.getBlock().hasMetadata("Bull Rush"))
        		{
        			e.setYield(0);
            		for(Block b : e.blockList())
            		{
        				Utils.regenBlock(b, 30);
        				b.setType(Material.AIR);
            		}	
        		}
        	}
        	
        	
        	
        	  
        	
        	
        	
        	
        		
        };
    }

}
