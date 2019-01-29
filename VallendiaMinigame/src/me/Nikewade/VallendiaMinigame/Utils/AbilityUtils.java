package me.Nikewade.VallendiaMinigame.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import net.minecraft.server.v1_12_R1.Explosion;

public class AbilityUtils implements Listener {
	private static Set<Material> transparentBlocks = null;
	private static HashMap<Block, Integer> explosives = new HashMap<>();
	private static HashMap<Entity, Integer> explosivesEntities = new HashMap<>();
	public static HashMap<LivingEntity, BukkitTask> silenced = new HashMap<>();
	private static HashMap<String, Double> maxHealth = new HashMap<>();
	
	
	//If the player already has a potion effect, this will add the existing effects duration to the new one.
	public static void addPotionDuration(LivingEntity e, PotionEffectType p, int amplifier, int duration )
	{
		if(!e.hasPotionEffect(p))
		{
			e.addPotionEffect(new PotionEffect(p, duration, amplifier));
			return;
		}
		int existingAmp = e.getPotionEffect(p).getAmplifier();
		int existingDuration = e.getPotionEffect(p).getDuration();
		
			if(existingAmp < amplifier)
			{
				e.removePotionEffect(p);
				e.addPotionEffect(new PotionEffect(p, duration, existingAmp + amplifier));	
			}
			
			if(existingAmp > amplifier)
			{
				e.removePotionEffect(p);
				e.addPotionEffect(new PotionEffect(p, existingDuration, existingAmp + amplifier));
			}
			
			if(existingAmp == amplifier)
			{
			  if(existingDuration > duration)
			  {
					e.removePotionEffect(p);
					e.addPotionEffect(new PotionEffect(p, existingDuration, existingAmp));
			  }else
			  {
					e.removePotionEffect(p);
					e.addPotionEffect(new PotionEffect(p, duration, existingAmp));
			  }
			} 
	}
	
	
	
	
	
	
	
	public static LivingEntity getTarget(Player p, int range)
	{
		
		if(p.hasPotionEffect(PotionEffectType.BLINDNESS))
		{
			p.sendMessage(Utils.Colorate("&8&l You have to be able to see to do that!"));
			return null;
		}
	    if (p.getLocation().getBlockY() > p.getLocation().getWorld().getMaxHeight()) {
	        return null;
	      }
	      try
	      {
	       List lineOfSight = p.getLineOfSight(AbilityUtils.transparentBlocks, range);
	      }
	      catch (IllegalStateException e)
	      {
	        return null;
	      }
	      Set<Location> locs = new HashSet();
	      for (Block block : p.getLineOfSight(AbilityUtils.transparentBlocks, range))
	      {
	        locs.add(block.getRelative(BlockFace.UP).getLocation());
	        locs.add(block.getLocation());
	        locs.add(block.getRelative(BlockFace.DOWN).getLocation());
	      }
	      List<Block> lineOfSight = null;
	      List<Entity> nearbyEntities = p.getNearbyEntities(range, range, range);
	      for (Entity entity : nearbyEntities) {
	        if (((entity instanceof LivingEntity)) && (!entity.isDead()) && (((LivingEntity)entity).getHealth() != 0.0D) && 
	          (locs.contains(entity.getLocation().getBlock().getLocation()))) {
	        	
	  	        if(entity instanceof Player)
		        {
		        	Player player = (Player) entity;
					if(!(player.getGameMode() == GameMode.SURVIVAL) && !(player.getGameMode() == GameMode.ADVENTURE))
		        	{
		      	      	p.sendMessage(Utils.Colorate("&8&l Target not found."));
			            return null;
		        	}
		        }
	        	
	          if ((!(entity instanceof Player)) || (p.canSee((Player)entity))) {
	            return (LivingEntity)entity;
	          }
	        }
	      }
	      p.sendMessage(Utils.Colorate("&8&l Target not found."));
	      return null;
	    }
	
	
	
	
	
	
	
		public static Collection<Entity> getAoeTargets(Player originplayer, double Radiusx, double Radiusy, double Radiusz)
		{
			Collection<Entity> nearbyEntities = new ArrayList<Entity>();
			for(Entity entity : originplayer.getWorld().getNearbyEntities(originplayer.getLocation(), Radiusx, Radiusy, Radiusz))
			{
				if(entity instanceof LivingEntity && !(entity == originplayer))
				{
					if(entity instanceof Player)
					{
						Player entityplayer = (Player) entity;
						if(!(entityplayer.getGameMode() == GameMode.SURVIVAL) && !(entityplayer.getGameMode() == GameMode.ADVENTURE))
						{
							continue;
						}
					}
					nearbyEntities.add(entity);
					continue;
				}
			}
			return nearbyEntities;
			
		}
	
	
	
	
	
	public static void addBlocks()
	{
	    transparentBlocks = new HashSet(42);
	    transparentBlocks.add(Material.AIR);
	    transparentBlocks.add(Material.CARPET);
	    transparentBlocks.add(Material.CROPS);
	    transparentBlocks.add(Material.DEAD_BUSH);
	    transparentBlocks.add(Material.DETECTOR_RAIL);
	    transparentBlocks.add(Material.DIODE_BLOCK_OFF);
	    transparentBlocks.add(Material.DIODE_BLOCK_ON);
	    transparentBlocks.add(Material.DIODE);
	    transparentBlocks.add(Material.FENCE_GATE);
	    transparentBlocks.add(Material.FLOWER_POT);
	    transparentBlocks.add(Material.LADDER);
	    transparentBlocks.add(Material.LEVER);
	    transparentBlocks.add(Material.LONG_GRASS);
	    transparentBlocks.add(Material.DOUBLE_PLANT);
	    transparentBlocks.add(Material.NETHER_WARTS);
	    transparentBlocks.add(Material.PORTAL);
	    transparentBlocks.add(Material.POWERED_RAIL);
	    transparentBlocks.add(Material.RAILS);
	    transparentBlocks.add(Material.RED_ROSE);
	    transparentBlocks.add(Material.REDSTONE_COMPARATOR_OFF);
	    transparentBlocks.add(Material.REDSTONE_COMPARATOR_ON);
	    transparentBlocks.add(Material.REDSTONE_COMPARATOR);
	    transparentBlocks.add(Material.REDSTONE_TORCH_OFF);
	    transparentBlocks.add(Material.REDSTONE_TORCH_ON);
	    transparentBlocks.add(Material.REDSTONE_WIRE);
	    transparentBlocks.add(Material.SAPLING);
	    transparentBlocks.add(Material.SIGN_POST);
	    transparentBlocks.add(Material.SIGN);
	    transparentBlocks.add(Material.SNOW);
	    transparentBlocks.add(Material.STATIONARY_LAVA);
	    transparentBlocks.add(Material.STATIONARY_WATER);
	    transparentBlocks.add(Material.STONE_BUTTON);
	    transparentBlocks.add(Material.STONE_PLATE);
	    transparentBlocks.add(Material.SUGAR_CANE_BLOCK);
	    transparentBlocks.add(Material.TORCH);
	    transparentBlocks.add(Material.TRIPWIRE);
	    transparentBlocks.add(Material.VINE);
	    transparentBlocks.add(Material.WALL_SIGN);
	    transparentBlocks.add(Material.WATER_LILY);
	    transparentBlocks.add(Material.WATER);
	    transparentBlocks.add(Material.WEB);
	    transparentBlocks.add(Material.WOOD_BUTTON);
	    transparentBlocks.add(Material.WOOD_PLATE);
	    transparentBlocks.add(Material.YELLOW_FLOWER);
	    
	}
	
	
	//Makes and  explsion and stores it so you can  set its damage.. The damageSubtraction is what we subtract from the normal explosion damage.
    public static void explode(Location loc, Entity explodeAs, int power, int damage, boolean setFires, boolean terrainDamage, boolean particles) {
        explosives.put(loc.getBlock(), damage);
        explosivesEntities.put(explodeAs, damage);
        Explosion explosion = new Explosion(((CraftWorld) loc.getWorld()).getHandle(),
               ((CraftEntity) explodeAs).getHandle(), loc.getX(), loc.getY(), loc.getZ(), power, setFires,
               terrainDamage);
        explosion.a();
        explosion.a(true);
        if(particles)
        {
            loc.getWorld().playEffect(loc, Effect.EXPLOSION_HUGE, power);	
        }
        loc.getBlock().setMetadata(explodeAs.getName(), new FixedMetadataValue(VallendiaMinigame.getInstance(), loc.getBlock()));
    }
	
	
    public static void silenceAbilities(LivingEntity e, int seconds, String ability)
    {
    	if(silenced.containsKey(e))
    	{
    		silenced.remove(e);
    	}
    	
		BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
            	if(silenced.containsKey(e))
            	{
            		silenced.remove(e);
            		Language.sendAbilityUseMessage(e, "Your abilities are no longer silenced.", ability);
            	}
            }
        }.runTaskLaterAsynchronously(VallendiaMinigame.getInstance(), seconds*20L);
        
        silenced.put(e, task);
    }
    
    
    public static void removeSilence(LivingEntity e)
    {
    	if(silenced.containsKey(e))
    	{
    		silenced.remove(e);
    	}
    }
    
    public static Listener getListener() {
        return new Listener() {
        	
        	@EventHandler
        	public void onExplode(EntityExplodeEvent e)
        	{
        			if(explosives.containsKey(e.getLocation().getBlock()))
        			{
            			e.setYield(0);
                		for(Block b : e.blockList())
                		{
                			if(!(b.getType() == Material.TORCH) || !(b.getType() == Material.REDSTONE_TORCH_ON) || !(b.getType() == Material.REDSTONE_TORCH_OFF))
                			{
                				Utils.regenBlock(b, 30);
                				b.setType(Material.AIR);
                			}
                		}	
        			}
        		}
        	
        	
        	@EventHandler
        	public void onDamage(EntityDamageByEntityEvent e)
        	{
        		if(!explosivesEntities.containsKey(e.getDamager()))
        		{
        			return;
        		}
        		if(e.getDamager() instanceof Player && e.getCause() == DamageCause.ENTITY_EXPLOSION)
        		{
        			e.setDamage(explosivesEntities.get(e.getDamager()));
        			explosivesEntities.remove(e.getEntity());
        		}

        	}
            
            
            
            
            
        };
    }
    
    
    public static void healEntity(LivingEntity p, double amount)
    {
		if(p.getHealth() + amount >= p.getMaxHealth())
		{
			p.setHealth(p.getMaxHealth());
		}else p.setHealth(p.getHealth() + amount);
    	
        p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 0.4, 0.4), 5);
        p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 0.4, 0), 5);
        p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0.4, 0.4, 0), 5);
    }
    
    
    public static void setMaxHealth(Player p, double amount, String ability)
    {
    	double healthAdded = amount - p.getMaxHealth();
    	p.setMaxHealth(amount);
    	if(!maxHealth.containsKey(p.toString()+ability))
    	{
        	maxHealth.put(p.toString()+ability, healthAdded);	
    	}
    }
    
    public static void resetMaxHealth(Player p, String ability)
    {
    	
    	if(maxHealth.containsKey(p.toString()+ability))
    	{
    		p.setMaxHealth(p.getMaxHealth() - maxHealth.get(p.toString()+ability));
    		maxHealth.remove(p.toString()+ability);
    	}
    }
    
    public static void resetAllMaxHealth(Player p)
    {
    	for(String s : maxHealth.keySet())
    	{
    		if(s.contains(p.toString()))
    		{
    			p.setMaxHealth(p.getMaxHealth() - maxHealth.get(s));
        		maxHealth.remove(s);
    		}
    	}
    }
    
    
    /*
     * Particle tests
     * 
     * sends particle foward from location
     * 
     * We can get the location and stop the particle with se.getlocation... we can track the particle and do things like damage players
     *  	 	  new BukkitRunnable(){                         
              double t = 0;
            
              public void run(){
                      t = t + 0.5;
                      Location loc = p.getLocation();
                      Vector direction = loc.getDirection().normalize();
                      double x = direction.getX() * t;
                      double y = direction.getY() * t + 1.5;
                      double z = direction.getZ() * t;
                      loc.add(x,y,z);
            			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
            			se.setLocation(loc);
            			se.particle = Particle.END_ROD;
            			se.iterations = 10;
            			se.particles = 2;
            			se.speed = (float) 0;
            			se.start();
                      loc.subtract(x,y,z);
                      if (t > 30){
                          this.cancel();
                  }
                    
              }
 	 	  }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 5);
     */
	
	
	
	
	
	
}
