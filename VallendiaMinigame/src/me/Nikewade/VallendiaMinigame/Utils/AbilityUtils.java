package me.Nikewade.VallendiaMinigame.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.FlyAbility;
import me.Nikewade.VallendiaMinigame.Events.PlayerItemEvents;
import me.Nikewade.VallendiaMinigame.Graphics.ScoreboardHandler;
import net.minecraft.server.v1_12_R1.Explosion;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;

public class AbilityUtils implements Listener {
	private static Set<Material> transparentBlocks = null;
	private static HashMap<Block, Integer> explosives = new HashMap<>();
	public static HashMap<Entity, Integer> explosivesEntities = new HashMap<>();
	public static HashMap<LivingEntity, BukkitTask> silenced = new HashMap<>();
	public static HashMap<Player, Float> casting = new HashMap<>();
	public static HashMap<Player, BukkitTask> castingTask = new HashMap<>();
	private static HashMap<Player, BukkitTask> castingTask2 = new HashMap<>();
	private static HashMap<String, Double> maxHealth = new HashMap<>();
	private static HashMap<Projectile, Runnable> arcProjectiles = new HashMap<>();
	private static HashMap<LivingEntity, Integer> handleDamage = new HashMap<>();
	private static HashMap<Player, SphereEffect> castingParticles = new HashMap<>();
	static int castingHealthPercent = 70;
	
	
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
	
	
	public static boolean partyCheck(Player p1, Player p2)
	{
		if(getPlayerParty(p1).equalsIgnoreCase(getPlayerParty(p2)))
		{
			return true;
		}
		return false;

	}
	
	public static String getPlayerParty(Player p)
	{
		return VallendiaMinigame.getInstance().parties.getPartyPlayer(p.getUniqueId()).getPartyName();
	}
	
	
	public static boolean runPassive(Player p, Player p2)
	{
		//Checking for party
		if(p2 != null)
		{
			if(AbilityUtils.getPlayerParty(p).equalsIgnoreCase(AbilityUtils.getPlayerParty(p2)))
			{
				return false;
			}	
		}
		
        RegionManager regionManager = VallendiaMinigame.getInstance().worldguard.getRegionManager(p.getWorld());
        ApplicableRegionSet set = regionManager.getApplicableRegions(p.getLocation());

        for (ProtectedRegion region : set) {

            if (region != null){

            	if(region.getId().equalsIgnoreCase("minigamespawn"))
            	{
            		return false;
            	}

            }

        }
		return true;
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
	          (locs.contains(entity.getLocation().getBlock().getLocation())) && !(entity instanceof ArmorStand)) {
	        	
	  	        if(entity instanceof Player)
		        {
		        	Player player = (Player) entity;
		        	
		        	//In party
		        	if(AbilityUtils.getPlayerParty(player).equalsIgnoreCase(AbilityUtils.getPlayerParty(p)))
		        	{
		      	      	p.sendMessage(Utils.Colorate("&8&l Target not found."));
			            return null;
		        	}
		        	
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
	
	
	
	
	public static LivingEntity getHealingTarget(Player p, int range)
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
	          (locs.contains(entity.getLocation().getBlock().getLocation())) && !(entity instanceof ArmorStand)) {
	        	
	  	        if(entity instanceof Player)
		        {
		        	Player player = (Player) entity;
		        	
		        	//Not in party
		        	if(!AbilityUtils.getPlayerParty(player).equalsIgnoreCase(AbilityUtils.getPlayerParty(p)))
		        	{
		      	      	p.sendMessage(Utils.Colorate("&8&l Target not found."));
			            return null;
		        	}
		        	
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
	
	
	
	
	public static List<Block> getLine(Player p, int range)
	{
		List<Block> lineOfSight = null;
	    if (p.getLocation().getBlockY() > p.getLocation().getWorld().getMaxHeight()) {
	        return null;
	      }
	      try
	      {
	       lineOfSight = p.getLineOfSight(AbilityUtils.transparentBlocks, range);
	      }
	      catch (IllegalStateException e)
	      {
	        return null;
	      }
	      Set<Location> locs = new HashSet();
	      int x = 0;
	      for (Block block : p.getLineOfSight(AbilityUtils.transparentBlocks, range))
	      {
	    	  if(x >= range)
	    	  {
	  	        locs.add(block.getRelative(BlockFace.UP).getLocation());
		        locs.add(block.getLocation());
		        locs.add(block.getRelative(BlockFace.DOWN).getLocation());  
	    	  }
	      }
	      return lineOfSight;
	    }
	
	
	
	
		public static Collection<Entity> getAoeTargets(Player originplayer, Location loc, double Radiusx, double Radiusy, double Radiusz)
		{
			Collection<Entity> nearbyEntities = new ArrayList<Entity>();
			for(Entity entity : loc.getWorld().getNearbyEntities(loc, Radiusx, Radiusy, Radiusz))
			{
				if(entity instanceof LivingEntity && !(entity == originplayer) && !(entity instanceof ArmorStand))
				{
					if(entity instanceof Player)
					{
						Player entityplayer = (Player) entity;
						
						
			        	//In party
			        	if(AbilityUtils.getPlayerParty(entityplayer).equalsIgnoreCase(AbilityUtils.getPlayerParty(originplayer)))
			        	{
			        		continue;
			        	}
						
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
		
		
		
		
		public static Collection<Entity> getHealingAoeTargets(Player originplayer, Location loc, double Radiusx, double Radiusy, double Radiusz)
		{
			Collection<Entity> nearbyEntities = new ArrayList<Entity>();
			for(Entity entity : loc.getWorld().getNearbyEntities(loc, Radiusx, Radiusy, Radiusz))
			{
				if(entity instanceof LivingEntity && !(entity == originplayer) && !(entity instanceof ArmorStand))
				{
					if(entity instanceof Player)
					{
						Player entityplayer = (Player) entity;
						
						
			        	//Not in party
			        	if(!AbilityUtils.getPlayerParty(entityplayer).equalsIgnoreCase(AbilityUtils.getPlayerParty(originplayer)))
			        	{
			        		continue;
			        	}
						
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
	
	
	//Makes and  explosion and stores it so you can  set its damage.. The damageSubtraction is what we subtract from the normal explosion damage.
	   public static void explode(Location loc, Entity explodeAs, int power, int damage, boolean setFires, boolean terrainDamage, boolean particles) {
		      Explosion explosion = new Explosion(((CraftWorld)loc.getWorld()).
		      getHandle(), ((CraftEntity)explodeAs).getHandle(), loc.getX(), 
		      loc.getY(), loc.getZ(), (float)power, setFires, terrainDamage);
		      explosives.put(loc.getBlock(), damage);
		      explosivesEntities.put(explodeAs, damage);
		      explosion.a();
		      explosion.a(true);
		      if (particles) {
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
    	
    	if(e instanceof Player )
    	{
    		Player p = (Player) e;
    		if(casting.containsKey(p))
    		{
    			removeCast(p);
    			Language.sendDefaultMessage(p, "Your casting was interrupted.");
    		}	
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
        }.runTaskLater(VallendiaMinigame.getInstance(), seconds*20L);
        
        silenced.put(e, task);
    }
    
    
    public static void removeSilence(LivingEntity e)
    {
    	if(silenced.containsKey(e))
    	{
    		silenced.remove(e);
    	}
    }
    
    
    public static boolean castAbility(Player p, int seconds, Runnable run)
    {
    	if(!casting.containsKey(p))
    	{
        	casting.put(p, p.getWalkSpeed());	
    	}else 
    		{
    		Language.sendDefaultMessage(p, "You are already casting!");
    		return false;
    		}
    	
    	p.setWalkSpeed((float) 0.07);
		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se.setEntity(p);
		se.disappearWithOriginEntity = true;
		se.infinite();
		se.particle = Particle.ENCHANTMENT_TABLE;
		se.radius = 0.4;
		se.particles = 1;
		se.yOffset = -0.4;
		se.particleOffsetZ = (float) 0.4;
		se.speed = (float) 0;
		se.start();
		castingParticles.put(p, se);
    	
    	
    	BukkitTask task2 =	new BukkitRunnable() {
			int x = seconds;
            @Override
            public void run() {
            	if(casting.containsKey(p))
            	{
    		        p.sendTitle(Utils.Colorate("&3&lCasting " + x), null, 0, 21, 0);
            		x--;
            	}else this.cancel();
            }
        }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 20L);
    	
		BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
            	if(casting.containsKey(p))
            	{
            		removeCast(p);
		        	run.run();
            	}
            }
        }.runTaskLater(VallendiaMinigame.getInstance(), seconds*20L);
        castingTask.put(p, task);
        castingTask2.put(p, task2);
        
        
        
		return true;
    }
    
    
    
    
    public static void removeCast(Player p)
    {
    	if(casting.containsKey(p))
    	{
    		p.setWalkSpeed(casting.get(p));
    		casting.remove(p);
    		castingTask.get(p).cancel();
    		castingTask.remove(p);
    		castingTask2.get(p).cancel();
    		castingTask2.remove(p);
    		castingParticles.get(p).cancel();
    		castingParticles.remove(p);
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
            	        for (Block b : new ArrayList<Block>(e.blockList()))
                		{
                			
                			if((b.getType() == Material.TORCH) || (b.getType() == Material.REDSTONE_TORCH_ON) || (b.getType() == Material.REDSTONE_TORCH_OFF) || (b.getType() == Material.BANNER)|| (b.getType() == Material.STANDING_BANNER)|| (b.getType() == Material.WALL_BANNER) || (b.getType() == Material.FLOWER_POT) || (b.getType() == Material.ITEM_FRAME) || (b.getType() == Material.PAINTING))
                			{ 
                				e.blockList().remove(b);
                				continue;
                			}

            				Utils.regenBlock(b, 30);
            				b.setType(Material.AIR);
                		}	
        			}
        		}
        	
        	
        	@EventHandler
        	public void onEntityExplode(EntityExplodeEvent e)
        	{
        		if(explosivesEntities.containsKey(e.getEntity()))
        		{
        			explosivesEntities.remove(e.getEntity());
        		}
        	}
        	
        	@EventHandler
        	public void onDamage(EntityDamageByEntityEvent e)
        	{
        		
        		if(e.getEntity() instanceof ArmorStand)
        				{
        				return;
        				}
        		if(explosivesEntities.containsKey(e.getDamager()))
        		{
            		if(e.getDamager() instanceof Player && e.getCause() == DamageCause.ENTITY_EXPLOSION)
            		{
            			e.setDamage(0);
            			e.setDamage(DamageModifier.ARMOR, (explosivesEntities.get(e.getDamager())));
            			explosivesEntities.remove(e.getDamager());
            		}
        		}
        		
        		if(e.getEntity() instanceof Player && casting.containsKey(e.getEntity()))
        		{
        			Player p = (Player) e.getEntity();
        			double currentHealth = p.getHealth() - e.getFinalDamage();
        			double lowestHealth = p.getMaxHealth() * Utils.getPercentHigherOrLower(castingHealthPercent, false);
        			if(currentHealth <= lowestHealth)
        			{
            			removeCast((Player) e.getEntity());
            			Language.sendDefaultMessage((Player) e.getEntity(), "Your casting was interrupted.");	
        			}
        		}
        		
        		if(handleDamage.containsKey(e.getEntity()))
        		{
        			e.setDamage(0);
        			e.setDamage(DamageModifier.ARMOR, handleDamage.get(e.getEntity()));
        			handleDamage.remove(e.getEntity());
        		}
        	}
        	
        	@EventHandler
        	public void onMove(PlayerMoveEvent e)
        	{
        		
        		if(casting.containsKey(e.getPlayer()) &&
        		   !FlyAbility.enabled.contains(e.getPlayer()))
        		{
            		if(e.getTo().getY() > e.getFrom().getY() && !e.getPlayer().isOnGround())
            		{
            			e.setCancelled(true);
            		}
        		}
        	}
        	
        	@EventHandler
        	public void leftClick(PlayerInteractEvent e)
        	{
        		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
        		{
        			if(casting.containsKey(e.getPlayer()))
        			{
        				if(PlayerItemEvents.casting.containsKey(e.getPlayer()))
        				{
            				PlayerItemEvents.casting.get(e.getPlayer()).cancel();
            				PlayerItemEvents.casting.remove(e.getPlayer());
        				}
        				AbilityUtils.removeCast(e.getPlayer());
        				Language.sendDefaultMessage(e.getPlayer(), "You stop casting.");
        			}
        		}
        	}
        	
        	@EventHandler
        	public void projectileHit(ProjectileHitEvent e)
        	{
        		if(e.getEntityType() == EntityType.SNOWBALL && e.getEntity().getShooter() instanceof Player && arcProjectiles.containsKey(e.getEntity()))
        		{
        			arcProjectiles.get(e.getEntity()).run();
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
        if(p instanceof Player)
        {
            ScoreboardHandler.updateMaxHealth((Player) p);	
        }
    }
    
    public static void damageEntity(LivingEntity target, LivingEntity damager, int amount)
    {
		handleDamage.put(target, amount);
		target.damage(amount, damager);
    }
    
    
    public static void setMaxHealth(Player p, double amount, String ability)
    {
    	double healthAdded = amount - p.getMaxHealth();
    	p.setMaxHealth(amount);
    	if(!maxHealth.containsKey(p.toString()+ability))
    	{
        	maxHealth.put(p.toString()+ability, healthAdded);	
    	}
        ScoreboardHandler.updateMaxHealth(p);	
    }
    
    public static void resetMaxHealth(Player p, String ability)
    {
    	
    	if(maxHealth.containsKey(p.toString()+ability))
    	{
    		p.setMaxHealth(p.getMaxHealth() - maxHealth.get(p.toString()+ability));
    		maxHealth.remove(p.toString()+ability);
    	}
        ScoreboardHandler.updateMaxHealth(p);
    }
    
    public static void resetAllMaxHealth(Player p)
    {
    	for(String s : maxHealth.keySet())
    	{
    		if(s.contains(p.toString()))
    		{
    			p.setMaxHealth(p.getMaxHealth() - maxHealth.get(s));
        		maxHealth.remove(s);
                ScoreboardHandler.updateMaxHealth(p);
    		}
    	}
    }
    
    
    
    public static void arcParticle(LivingEntity e, de.slikey.effectlib.Effect effect, double velocity, Runnable run)
    {
		Snowball ball = e.launchProjectile(Snowball.class);
		ball.setSilent(true);
		ball.setVelocity(ball.getVelocity().multiply(velocity));
		arcProjectiles.put(ball, run);
			
			BukkitTask task = new BukkitRunnable() {
	            @Override
	            public void run() {
	            	if(ball.isDead())
	            	{
	            		this.cancel();
	            	}

        			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
        			    PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(ball.getEntityId());
        			    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        			}
	            	
	            }
	        }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
	        
			effect.setEntity(ball);
			effect.start();
    }
    

}
