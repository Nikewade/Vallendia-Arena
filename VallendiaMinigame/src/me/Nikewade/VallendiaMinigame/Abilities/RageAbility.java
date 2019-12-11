package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Events.AltitudeChecker;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class RageAbility implements Ability{
private static ArrayList<Player> raging = new ArrayList<>();
private static HashMap<Player, BukkitTask> tasks = new HashMap<>();
private  static Map<Entity,Effect> particle = new HashMap<>();
int ragetime = 30;
int force = 20;
int yForce = 8;
int maxYForce = 10;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Rage";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	
	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Go into a frenzied rage for "  +ragetime+ " seconds" , 
				"becoming faster, stronger, and more resilient." ,
				"After your rage ends your body will become fatigued" ,
				"causing you to move slower and do less damage.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		ItemStack item = Utils.getPotionItemStack(PotionType.STRENGTH, 1, false, false, "");
		return item;
	}

	@Override
	public boolean RunAbility(Player p) {
		if(raging.contains(p))
		{
			return false;
		}
			p.sendTitle(ChatColor.DARK_RED + "" + ChatColor.BOLD + "YOU RAGE!", ChatColor.RED + " Your rage will last for " + ragetime + " seconds" , 20, 90, 50);	
			if(p.getHealth() < p.getMaxHealth() && p.getHealth() + 5 < p.getMaxHealth())
			{
		    	p.setHealth(p.getHealth() + 5 );	
			}else p.setHealth(p.getMaxHealth());
			AbilityUtils.addPotionDuration(p, PotionEffectType.INCREASE_DAMAGE, 0, ragetime * 20);
			AbilityUtils.addPotionDuration(p, PotionEffectType.SPEED, 1, ragetime * 20);
			AbilityUtils.addPotionDuration(p, PotionEffectType.DAMAGE_RESISTANCE, 0, ragetime * 20);
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 2, (float) 0.4);
			raging.add(p);
			Location location = p.getLocation().add(0.0D, -1.0F, 0.0D);
		    Vector t = location.toVector();

			for(Entity entity : AbilityUtils.getAoeTargets(p, p.getLocation(), 8, 8, 8))
			{
		          Vector e = entity.getLocation().toVector();
		          Vector v = e.subtract(t).normalize().multiply(force / 10.0D);
		          if (force != 0) {
		            v.setY(v.getY() * (yForce / 10.0D));
		          } else {
		            v.setY(yForce / 10.0D);
		          }
		          if (v.getY() > maxYForce / 10.0D) {
		              v.setY(maxYForce / 10.0D);
		            }
		          entity.setVelocity(v);
			}
			
			
			//red screen
			  BukkitTask task = new BukkitRunnable() {

		            @Override
		            public void run() {	
		        		int dist = -10000 * 10 + 1300000;
		        		Utils.sendWorldBorderPacket(p, dist, 200000D, 200000D, 0);
		            }
			    }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
			    tasks.put(p, task);
			
			    //particles
        		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
        		se.setEntity(p);
        		se.particle = Particle.EXPLOSION_NORMAL;
        		se.disappearWithOriginEntity = true;
        		se.infinite();
        		se.radius = 0.2;
        		se.particles = 1;
        		se.yOffset = -1;
        		se.particleCount = 1;
        		
        		se.start();	
        		
        			if(particle.containsKey(p))
        			{
            			RageAbility.particle.get(p).cancel();
            			RageAbility.particle.remove(p);	
        			}
        		RageAbility.particle.put(p, se);
			
	    	//rage off
	    	new BukkitRunnable() {
	    		int ragetimer = ragetime;
	    		int maxtime = 0;
	            @Override
	            public void run() {	
	            		ragetimer--;
	            		maxtime++;
	            		if(maxtime == 300) //5mins
	            		{
	            			if(tasks.containsKey(p))
	            			{
		            			tasks.get(p).cancel();
		            			tasks.remove(p);	
	            			}
	            			if(particle.containsKey(p))
	            			{
		            			RageAbility.particle.get(p).cancel();
		            			RageAbility.particle.remove(p);	
	            			}
	            			raging.remove(p);
	            			this.cancel();
	            		}
	            		if(!raging.contains(p))
	            		{
	            			if(tasks.containsKey(p))
	            			{
		            			tasks.get(p).cancel();
		            			tasks.remove(p);	
	            			}
	            			
	            			if(particle.containsKey(p))
	            			{
		            			RageAbility.particle.get(p).cancel();
		            			RageAbility.particle.remove(p);	
	            			}
	            			this.cancel();
	            		}
	                	if (!p.isOnline())
	                	{
	                		ragetimer = ragetimer + 1;
	                	}
	                	if(ragetimer  == 5)
	                	{
	                		Bukkit.getServer().getPlayer(p.getName()).sendTitle(ChatColor.RED + "You begin to tire", ChatColor.RED + " Your rage will end in " + ragetimer + " seconds...", 20, 50, 20);	
	                	}	
	                	if(ragetimer == 0)
	                	{
	    	                AbilityUtils.addPotionDuration(p, PotionEffectType.WEAKNESS, 0, ragetime*20);
	    	                AbilityUtils.addPotionDuration(p, PotionEffectType.CONFUSION, 1, 8*20);
	    	                AbilityUtils.addPotionDuration(p, PotionEffectType.SLOW, 1, ragetime*20);
	    	                Language.sendAbilityUseMessage(p, "You feel fatigued.", "Rage");
            				Utils.sendWorldBorderPacket(p, 0, 200000D, 200000D, 0);
            				
	            			if(tasks.containsKey(p))
	            			{
		            			tasks.get(p).cancel();
		            			tasks.remove(p);	
	            			}
	            			if(particle.containsKey(p))
	            			{
		            			RageAbility.particle.get(p).cancel();
		            			RageAbility.particle.remove(p);	
	            			}
	            			raging.remove(p);
	                		this.cancel();
	                	}
	            }
		}.runTaskTimer(VallendiaMinigame.getInstance(), 20L, 20L);	
		return true;
	}
	
	
	public static void onDie(Player p)
	{
		raging.remove(p);
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
