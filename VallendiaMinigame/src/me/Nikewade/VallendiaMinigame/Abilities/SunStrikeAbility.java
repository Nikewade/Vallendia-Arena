package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class SunStrikeAbility implements Ability, Listener{
	int castTime = 3;
	int delay = 20;
	int multiplier = 2;
	HashMap<Player, BukkitTask> timers = new HashMap<>();
	List<Player> active = new ArrayList<>();
	HashMap<Player, SphereEffect> effects = new HashMap<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Sun Strike";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You channel the power of the sun into your blade.",
							"Your next hit deals " + multiplier + "x damage and sets the",
							"enemy on fire for one minute or until they extinguish",
							"themselves. This can only be cast if you are outside and",
							"it is sunny.",
							Utils.Colorate("&8Cast: " + castTime + " seconds"));
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.DOUBLE_PLANT);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(active.contains(p))
		{
			return false;
		}
		
		if(!isOutside(p))
		{
			Language.sendAbilityUseMessage(p, "You must be outside!", "Sun Strike");
			return false;
		}		
		if(day(p) && !p.getWorld().hasStorm())
		{
			Collection<Location> locations = randomLocations(p);
 			
 			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
 			se.particle = Particle.REDSTONE;
 			se.color = Color.fromRGB(255, 255, 82);
 			se.radius = 1F;
 			se.particles = 2;
 			se.setEntity(p);
 			se.infinite();
 			se.particleOffsetY = 1.3F;
 			se.yOffset = -0.8F;
 			effects.put(p, se);
 			
			Runnable run = new Runnable()
					{
	
						@Override
						public void run() {
							// TODO Auto-generated method stub						
							p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EVOCATION_ILLAGER_CAST_SPELL, 2, 0.7F);
							active.add(p);
  							effects.get(p).start();
  							
							for(Location location : locations)
							{
				       	  		SphereEffect particleEffect = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
				     			particleEffect.particle = Particle.REDSTONE;
				     			particleEffect.color = Color.fromRGB(255, 255, 82);
				     			if(Utils.randomNumber(1, 100) <= 45)
				     			{
				     				particleEffect.color = Color.WHITE;
				     			}
				     			particleEffect.radius = 0.1F;
				     			particleEffect.particles = 2;
				     			particleEffect.speed = (float) 0;
				     			particleEffect.visibleRange = 200;
				     			particleEffect.infinite();

				     			
						 	  new BukkitRunnable(){      
					              Location loc = location;
					              double t = 0;
					              public void run(){
					            	  //t effects speed of article
					                      t = t + 0.8;
					                      Location tloc = p.getLocation();
					                      Vector direction = tloc.toVector().subtract(loc.toVector()).normalize();
					                      double x = direction.getX() * t;
					                      double y = direction.getY() * t + 1.5;
					                      double z = direction.getZ() * t;
					                      loc.add(x,y,z);
					                      particleEffect.setLocation(loc);
					                      if(loc.distance(tloc) <= 2)
					                      {
					                    	  particleEffect.cancel();
					                    	  this.cancel();
					                      }
					                      if(loc == tloc)
					                      {

					                    	  particleEffect.cancel();
					                    	  this.cancel();

					                      }
					                      
					                      loc.subtract(x,y,z);   
					                      if (t > 100){
					                    	  if(!active.contains(p))

						          			particleEffect.cancel();
					                          this.cancel();

					                  }
					                    
					              }
					 	 	  }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
					 			particleEffect.start();
							}

						}
				
					};
					
			AbilityUtils.castAbility(p, castTime, run);
			
			BukkitTask timer = new BukkitRunnable()
					{

						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(active.contains(p))
							{
								Language.sendAbilityUseMessage(p, "Your power fades", "Sun Strike");
								active.remove(p);
								if(effects.containsKey(p))
								{
									effects.get(p).cancel();
									effects.remove(p);
								}
							}

						}
				
					}.runTaskLater(VallendiaMinigame.getInstance(), delay*20);
					
					timers.put(p, timer);
					return true;
		}else
		{
			Language.sendAbilityUseMessage(p, "There is not enough sunlight to use this ability right now!", "Sun Strike");
			return false;
		}

	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(active.contains(p))
		{
			active.remove(p);
		}
		if(effects.containsKey(p))
		{
			effects.get(p).cancel();
			effects.remove(p);
		}
		if(timers.containsKey(p))
		{
			timers.get(p).cancel();
			timers.remove(p);
		}
	}
	
		@EventHandler
		public void onDamage (EntityDamageByEntityEvent e)
		{
			if(active.contains(e.getDamager()))
			{
				Player p = (Player) e.getDamager();
				if(p.getItemInHand().getType() == Material.STONE_SWORD ||
				p.getItemInHand().getType() == Material.DIAMOND_SWORD ||
				p.getItemInHand().getType() == Material.IRON_SWORD ||
				p.getItemInHand().getType() == Material.GOLD_SWORD ||
				p.getItemInHand().getType() == Material.WOOD_SWORD)
				{
				if(e.getEntity() instanceof Player)
				{
					if(AbilityUtils.partyCheck(p, (Player) e.getEntity()))
					{
						return;
					}
				}
	            double damage = e.getFinalDamage();
	            double newdamage = damage*multiplier;
				e.setDamage(0);
				e.setDamage(DamageModifier.ARMOR, newdamage);
				e.getEntity().setFireTicks(60*20);
				active.remove(p);
				if(effects.containsKey(p))
				{
					effects.get(p).cancel();
					effects.remove(p);
				}
				if(timers.containsKey(p))
				{
					timers.get(p).cancel();
					timers.remove(p);
				}
				}
				
			}
		}
	
	
	public Collection<Location> randomLocations(Player p)
	{
		Collection<Location> locations = new ArrayList<Location>();
		int radius = 30;
		new BukkitRunnable()
		{
			int t = 0;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(t >= 30)
				{
					this.cancel();
				}
		        Random r = new Random();
		        double randomRadius = r.nextDouble() * radius;
		        double theta =  Math.toRadians(r.nextDouble() * 360);
		        double phi = Math.toRadians(r.nextDouble() * 180 - 90);
		        double y = randomRadius * Math.sin(theta) * Math.cos(phi);
		        double x = randomRadius * Math.cos(theta) * Math.sin(phi);
		        double z = randomRadius * Math.cos(phi);
		        Location newLoc = p.getLocation();
		        if(Utils.randomNumber(1, 100) > 50)
		        {
		        	newLoc = p.getLocation().add(x, 0, z);
		        	newLoc.add(0, y, 0);
		        }else
		        {
			        newLoc = p.getLocation().subtract(x, 0, z);
			        newLoc.add(0, y, 0);
		        }
		        
		        locations.add(newLoc);

				t++;
			}
			
		}.runTaskTimer(VallendiaMinigame.getInstance(), 0, 0);

		return locations;
		
	}
	
	public boolean isOutside(Player p)
	{	
			if(!(p.getLocation().add(0,1,0).getBlockY() < p.getWorld().getHighestBlockYAt(p.getLocation())))
			{
				return true;
			}else
			{

				Material m = p.getWorld().getHighestBlockAt(p.getLocation()).getLocation().subtract(0,1,0).getBlock().getType();
				if(m == Material.LEAVES || m == Material.LEAVES_2)
				{
					return true;
				}
				
				return false;
			}
		
	}
	
	public boolean day(Player p) {

	    long time = p.getWorld().getTime();

	    return time < 12300 || time > 23850;
	}

}