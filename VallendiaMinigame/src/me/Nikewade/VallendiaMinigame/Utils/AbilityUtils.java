package me.Nikewade.VallendiaMinigame.Utils;

import java.io.File;
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
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.FlyAbility;
import me.Nikewade.VallendiaMinigame.Events.PlayerItemEvents;
import me.Nikewade.VallendiaMinigame.Graphics.ScoreboardHandler;
import me.Nikewade.VallendiaMinigame.Saves.FireLocations;
import net.minecraft.server.v1_12_R1.Explosion;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;

public class AbilityUtils implements Listener {
	public static Set<Material> transparentBlocks = null;
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
	private static List<LivingEntity> stunned = new ArrayList<>();
	private static List<LivingEntity> stunnedOnDamage = new ArrayList<>();
	private static HashMap<LivingEntity ,String> stunName = new HashMap<>();
	private static HashMap<LivingEntity, BukkitTask> stunTimer = new HashMap<>();
	private static HashMap<LivingEntity, BukkitTask> stunCountdown = new HashMap<>();
	private static HashMap<LivingEntity ,SphereEffect> stunParticle = new HashMap<>();
	private static HashMap<String ,Location> traps = new HashMap<>();
	private static HashMap<Location ,SphereEffect> trapParticle = new HashMap<>();
	private static HashMap<Location ,BukkitTask> trapTasks = new HashMap<>();
	private static List<LivingEntity> invisible = new ArrayList<>();
	private static HashMap<Entity, Player> evokerFangs = new HashMap<>();
	private static HashMap<Entity, Integer> evokerFangsDamage = new HashMap<>();
	private static ArrayList<Player> noTargetCooldown = new ArrayList<>();
	static int castingHealthPercent = 70;
	
	
	//If the player already has a potion effect, this will add the existing effects duration to the new one.
	public static void addPotionDuration(LivingEntity caster, LivingEntity e, String abilityname, PotionEffectType p, int amplifier, int duration )
	{
		//If blindness, remove target if its a mob. I cancel targeting below in the events if they have the blond effect
		if(e instanceof Creature && p == PotionEffectType.BLINDNESS)
		{
			Creature mob = (Creature) e;
			if(mob.getTarget() != null)
			{
				mob.setTarget(null);
			}
		}
		
		if(e instanceof Player && p == PotionEffectType.SLOW)
		{
    		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility((Player) e, "Escape Artist"))
    		{
    			Player newP = (Player) e;
    			Language.sendAbilityUseMessage((Player)caster, "The target evades your slow.", abilityname);
    			Language.sendAbilityUseMessage((Player)e, "You break free from the slowness.", abilityname);
    			newP.getWorld().playSound(newP.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1, (float) 1.6);
    			return;	
    		}
		}
		
		
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
		if(p1 instanceof Player && p2 instanceof Player)
		{
			if(!getPlayerParty(p1).isEmpty() && !getPlayerParty(p2).isEmpty())
			{
				if(getPlayerParty(p1).equalsIgnoreCase(getPlayerParty(p2)))
				{
					return true;
				}
			}	
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
			if(partyCheck(p,p2))
			{
				return false;
			}	
		}
		if(!(p instanceof Player))
		{
			return false;
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
	        	//Can damage
	        	if(!Utils.canDamage(p, entity))
	        	{
	      	      	sendNoTargetMessage(p);
		            return null;
	        	}
	  	        if(entity instanceof Player)
		        {
		        	Player player = (Player) entity;
		        	
		        	
		        	//In party
		        	if(partyCheck(player, p) == true)
		        	{
		      	      	sendNoTargetMessage(p);
			            return null;
		        	}

					if(!(player.getGameMode() == GameMode.SURVIVAL) && !(player.getGameMode() == GameMode.ADVENTURE))
		        	{
		      	      	sendNoTargetMessage(p);
			            return null;
		        	}
		        }
	        	
	          if ((!(entity instanceof Player)) || (p.canSee((Player)entity))) {
	            return (LivingEntity)entity;
	          }
	        }
	      }
	      sendNoTargetMessage(p);
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
		        	if(!partyCheck((Player)entity, p))
		        	{
		      	      	sendNoTargetMessage(p);
			            return null;
		        	}
		        	
					if(!(player.getGameMode() == GameMode.SURVIVAL) && !(player.getGameMode() == GameMode.ADVENTURE))
		        	{
		      	      	sendNoTargetMessage(p);
			            return null;
		        	}
		        }
	        	
	          if ((!(entity instanceof Player)) || (p.canSee((Player)entity))) {
	            return (LivingEntity)entity;
	          }
	        }
	      }
	      sendNoTargetMessage(p);
	      return null;
	    }
	
	
	public static LivingEntity getTargetBoth(Player p, int range)
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
		        	
					if(!(player.getGameMode() == GameMode.SURVIVAL) && !(player.getGameMode() == GameMode.ADVENTURE))
		        	{
		      	      	sendNoTargetMessage(p);
			            return null;
		        	}
		        }
	        	
	          if ((!(entity instanceof Player)) || (p.canSee((Player)entity))) {
	            return (LivingEntity)entity;
	          }
	        }
	      }
	      sendNoTargetMessage(p);
	      return null;
	    }
	
	
	
	
	public static void sendNoTargetMessage(Player p)
	{
		if(noTargetCooldown.contains(p))
		{
			return;
		}
		noTargetCooldown.add(p);
		
		new BukkitRunnable()
		{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(noTargetCooldown.contains(p))
				{
					noTargetCooldown.remove(p);
				}
			}
			
		}.runTaskLater(VallendiaMinigame.getInstance(), 3 * 20);
		
	    p.sendMessage(Utils.Colorate("&8&l Target not found."));
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
			        	if(partyCheck(entityplayer, originplayer))
			        	{
			        		continue;
			        	}
			        	
			        	//Can damage
			        	if(!Utils.canDamage(originplayer, entity))
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
		
		
		
		public static Collection<Entity> getAoeTargetsNonDamage(Player originplayer, Location loc, double Radiusx, double Radiusy, double Radiusz)
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
			        	if(partyCheck(entityplayer, originplayer))
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
		
		//Gets even arrows
		public static Collection<Entity> getAoeTargetsAndArrows(Player originplayer, Location loc, double Radiusx, double Radiusy, double Radiusz)
		{
			Collection<Entity> nearbyEntities = new ArrayList<Entity>();
			for(Entity entity : loc.getWorld().getNearbyEntities(loc, Radiusx, Radiusy, Radiusz))
			{
				if(entity instanceof LivingEntity && !(entity == originplayer) && !(entity instanceof ArmorStand) || entity instanceof Projectile)
				{
		        	
		        	if(entity instanceof Projectile)
		        	{
		        		Projectile proj = (Projectile) entity;
		        		if(proj.getShooter() == originplayer)
		        		{
		        			continue;
		        		}
		        	}
		        	
					if(entity instanceof Player)
					{
						Player entityplayer = (Player) entity;
						
						
			        	//In party
			        	if(partyCheck(entityplayer, originplayer))
			        	{
			        		continue;
			        	}
			        	//Can damage
			        	if(!Utils.canDamage(originplayer, entity))
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
			        	if(!partyCheck(originplayer, entityplayer))
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
	
		
		
		
		public static Collection<Entity> getAoeTargetsBoth(Player originplayer, Location loc, double Radiusx, double Radiusy, double Radiusz)
		{
			Collection<Entity> nearbyEntities = new ArrayList<Entity>();
			for(Entity entity : loc.getWorld().getNearbyEntities(loc, Radiusx, Radiusy, Radiusz))
			{
				if(entity instanceof LivingEntity && !(entity == originplayer) && !(entity instanceof ArmorStand))
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
    	if(!isInvisible(p))
    	{
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
    	}
    	
    	
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
    		if(castingParticles.containsKey(p))
    		{
        		castingParticles.get(p).cancel();
        		castingParticles.remove(p);	
    		}
    	}
    }
    
    
    
    public static void stun(LivingEntity caster, LivingEntity e, String abilityname, int tickTime, boolean cancelOnDamage)
    {
    	if(e instanceof Player)
    	{
    		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility((Player) e, "Escape Artist"))
    		{
    			Language.sendAbilityUseMessage((Player)caster, "The target evades your stun.", abilityname);
    			Language.sendAbilityUseMessage((Player)e, "You break free from the stun.", abilityname);
    			caster.getWorld().playSound(caster.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1, (float) 1.6);
    			return;
    		}
    	}
    		removeAllStuns(e);
    		stunned.add(e);
    		if(e instanceof Player)
    		{
    			Player p = (Player) e;
    		}
    		if(e instanceof Creature)
    		{
    			AbilityUtils.addPotionDuration(caster, e, abilityname, PotionEffectType.SLOW, 10, tickTime);
    			Creature mob = (Creature) e;
    			if(mob.getTarget() != null)
    			{
    				mob.setTarget(null);
    			}
    		}
    		
        		BukkitTask countdown = new BukkitRunnable() {
        			int x = (tickTime / 20) + 1;
                    @Override
                    public void run() {
                    	x--;
                		if(e instanceof Player)
                		{
                			Player p = (Player) e;
                        	if(x <= 0)
                        	{
                        		x = 1;
                		        p.sendTitle(Utils.Colorate("&3&lStunned < " + x), null, 0, 26, 0);	
                        	}else
                        	{
                		        p.sendTitle(Utils.Colorate("&3&lStunned " + x), null, 0, 26, 0);	
                        	}	
                		}
                    }
                }.runTaskTimer(VallendiaMinigame.getInstance(),0, 20L);	
    		
    		BukkitTask timer = new BukkitRunnable() {
                @Override
                public void run() {
                	removeStun(e, abilityname);
                }
            }.runTaskLater(VallendiaMinigame.getInstance(), tickTime);
            
            stunTimer.put(e, timer);
            stunCountdown.put(e, countdown);
            stunName.put(e, abilityname);
            
            
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.setEntity(e);
			se.disappearWithOriginEntity = true;
			se.infinite();
			se.particle = Particle.CRIT;
			se.radius = 0.1;
			se.particles = 1;
			se.yOffset = 0.8;
			se.start();
			
			stunParticle.put(e, se);
			
			
			if(cancelOnDamage)
			{
				stunnedOnDamage.add(e);
			}
            
    	}
    
    
    public static void removeStun(LivingEntity e, String abilityname)
    {
    	if(stunned.contains(e))
    	{
    		if(stunName.containsKey(e) && stunName.get(e) == abilityname)
    		{
        		if(e instanceof Creature)
        		{
        			Creature mob = (Creature) e;
        			if(mob.hasPotionEffect(PotionEffectType.SLOW))
        			{
        				mob.removePotionEffect(PotionEffectType.SLOW);
        			}
        		}
        		stunned.remove(e);
        		stunTimer.get(e).cancel();
        		stunTimer.remove(e);
        		stunCountdown.get(e).cancel();
        		stunCountdown.remove(e);	
        		stunName.remove(e);
        		stunParticle.get(e).cancel();
        		stunParticle.remove(e);
        		if(stunnedOnDamage.contains(e))
        		{
        			stunnedOnDamage.remove(e);
        		}
    		}
    	}
    }
    
    public static void removeAllStuns(LivingEntity e)
    {
    	if(stunned.contains(e))
    	{
        		if(e instanceof Creature)
        		{
        			Creature mob = (Creature) e;
        			if(mob.hasPotionEffect(PotionEffectType.SLOW))
        			{
        				mob.removePotionEffect(PotionEffectType.SLOW);
        			}
        		}
        		stunned.remove(e);
        		stunTimer.get(e).cancel();
        		stunTimer.remove(e);
        		stunCountdown.get(e).cancel();
        		stunCountdown.remove(e);	
        		stunName.remove(e);
        		stunParticle.get(e).cancel();
        		stunParticle.remove(e);
        		if(stunnedOnDamage.contains(e))
        		{
        			stunnedOnDamage.remove(e);
        		}
    		}
    }
    
    
    public static boolean isStunned(LivingEntity e)
    {
    	if(stunned.contains(e))
    	{
    		return true;
    	}else
    	{
    		return false;
    	}
    }
    
    
    
    public static boolean makeInvisible(Player p)
    {
    	if(invisible.contains(p))
    	{
    		return false;
    	}
    	invisible.add(p);
    	
		//Untarget entities
		for(Entity en : p.getNearbyEntities(50, 50, 50))
		{
			if(!(en instanceof Player) && en instanceof Creature)
			{
				Creature mob = (Creature) en;
				if(mob.getTarget() != null)
				{
					if(invisible.contains(mob.getTarget()))
					{
						mob.setTarget(null);
					}
				}
			}
		}
		
		
		//Invis task
		BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
        		if(invisible.contains(p))
        		{
        			for(Player player : Bukkit.getOnlinePlayers())
        			{
        				player.hidePlayer(p);
        			}
        		}else 
        		{
        			this.cancel();
        		}
            }
        }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 20);
        
        return true;
        
    }
    
    
    
    public static void removeInvisible(Player p)
    {
    	if(invisible.contains(p))
    	{
			for(Player player : Bukkit.getOnlinePlayers())
			{
				player.showPlayer(p);
			}
			invisible.remove(p);
    	}
    }
    
    public static boolean isInvisible(Player p)
    {
    	if(invisible.contains(p))
    	{
    		return true;
    	}	
    	return false;

    }
    
    
    
    public static boolean makeTrap(String playername, String abilityname, Location loc, Runnable run)
    {
    	if(!traps.containsKey(playername+abilityname))
    	{
    		Player p = VallendiaMinigame.getInstance().getServer().getPlayer(playername);
    		if(traps.containsValue(loc))
    		{
    			Language.sendAbilityUseMessage(p, "There is already a trap here!", abilityname);
    			return false;
    		}
    		
    		
        	for(Entity e : AbilityUtils.getAoeTargetsAndArrows(p, loc, 2, 2, 2))
        	{
        		if(e != null && e instanceof Projectile)
        		{
            		Projectile pro = (Projectile) e;
            		e.remove();
        		}
        	}
    		
			Runnable runnable = new Runnable()
			{
				@Override
				public void run() {
					
		      		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		      		se.particle = Particle.SUSPENDED_DEPTH;
		    		se.radius = 0.2;
		    		se.infinite();
		    		se.particles = 2;
		    		//This makes it flat-ish
		    		se.particleOffsetX = 0.3F;
		    		se.particleOffsetZ = 0.3F;
		    		
		    		if(loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.STEP || loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.WOOD_STEP)
		    		{
		    			Location newloc = loc.clone();
		    			se.setLocation(newloc.subtract(0, 0.4, 0));
		    		}else
		    		{
			        	se.setLocation(loc);	
		    		}
		        	se.start();	
		        	
		        	
		    	    BukkitTask task = new BukkitRunnable() {
		                @Override
		                public void run() {	
		                	if(!loc.getBlock().getRelative(BlockFace.DOWN).getType().isSolid())
		                	{
	                    		run.run();
	                    		removeTrap(p, abilityname);
	                    		this.cancel();
		                	}
		                	for(Entity e : AbilityUtils.getAoeTargetsAndArrows(p, loc, 1, 1.8, 1))
		                	{
		                		if(e != null)
		                		{
		                    		run.run();
		                    		removeTrap(p, abilityname);
		                    		e.getWorld().playSound(loc, Sound.BLOCK_TRIPWIRE_CLICK_ON, 1, 1);
		                    		this.cancel();
		                		}
		                	}
		                }
		    	    }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);	
		    	    
		    	    trapTasks.put(loc, task);
		        	
		        	
		        	
		        	traps.put(playername+abilityname, loc);
		        	trapParticle.put(loc, se);
		    	}
				
			};
			
    		AbilityUtils.castAbility(p, 3, runnable);
    		
    		
    	return true;
    	}
		return false;
    }
    
    public static boolean removeTrap(Player p, String abilityname)
    {
    	
    	if(traps.containsKey(p.getName()+abilityname))
    	{
    		trapParticle.get(getTrap(p, abilityname)).cancel();
    		trapParticle.remove(getTrap(p, abilityname)).cancel();
    		trapTasks.get(getTrap(p, abilityname)).cancel();
    		trapTasks.remove(getTrap(p, abilityname));
    		traps.remove(p.getName()+abilityname);
    		return true;
    	}
    	return false;
    }
    
    
    public static Location getTrap(Player p, String abilityname)
    {
    	if(traps.containsKey(p.getName()+abilityname))
    	{
    		return traps.get(p.getName()+abilityname);	
    	}
    	return null;
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
                			
                			if((b.getType() == Material.TORCH) || (b.getType() == Material.REDSTONE_TORCH_ON) || 
                			(b.getType() == Material.REDSTONE_TORCH_OFF) || (b.getType() == Material.BANNER)||
                			(b.getType() == Material.STANDING_BANNER)|| (b.getType() == Material.WALL_BANNER) || 
                			(b.getType() == Material.FLOWER_POT) || (b.getType() == Material.ITEM_FRAME) || 
                			(b.getType() == Material.PAINTING) || FireLocations.locations.contains(b.getLocation() + ".yml"))
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
        	public void onEntityDamage(EntityDamageByEntityEvent e)
        	{
        		
        		if(evokerFangs.containsKey(e.getDamager()))
        		{
        			e.setCancelled(true);
        			if(evokerFangs.get(e.getDamager()) != e.getEntity())
        			{
            			AbilityUtils.damageEntity((LivingEntity) e.getEntity(), evokerFangs.get(e.getDamager()), evokerFangsDamage.get(e.getDamager()));	
        			}
        		}
        		
        		if(e.getEntity() instanceof ArmorStand)
        				{
        				return;
        				}
        		
        		if(e.getDamager() instanceof LivingEntity)
        		{
            		if(isStunned((LivingEntity)e.getDamager()))
            		{
            			e.setCancelled(true);
            		}	
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
        			if(e.getEntity() instanceof Player)
        			{
            			e.setDamage(DamageModifier.ARMOR, handleDamage.get(e.getEntity()));	
        			}else
        			{
            			e.setDamage(handleDamage.get(e.getEntity()));
        			}
        			handleDamage.remove(e.getEntity());
        		}
        	}
        	
        	
        	@EventHandler
        	public void onDamage(EntityDamageEvent e)
        	{
        		if(stunnedOnDamage.contains(e.getEntity()))
        		{
        			AbilityUtils.removeAllStuns((LivingEntity) e.getEntity());
        		}
        	}
        	
        	@EventHandler
        	public void onMove(PlayerMoveEvent e)
        	{
        		
        		if(isStunned(e.getPlayer()) && (e.getTo().getY() >= e.getFrom().getY())){
        			e.setCancelled(true);
        			}
        		
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
        	public void onSwing(PlayerAnimationEvent e)
        	{
        		if(isStunned(e.getPlayer()) && e.getAnimationType() == PlayerAnimationType.ARM_SWING)
        		{
        			e.setCancelled(true);
        		}
        	}
        	
        	
        	@EventHandler
        	public void playerInteract(PlayerInteractEvent e)
        	{
        		if(isStunned(e.getPlayer()))
        		{
        			e.setCancelled(true);
        			return;
        		}
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
        			if(e.getClickedBlock() != null)
        			{
        				Block relative = e.getClickedBlock().getRelative(e.getBlockFace());
        				String location = relative.getLocation().toString();
            			
        				if(relative.getType()==Material.FIRE && FireLocations.locations.contains(location + ".yml")) {
        					
                			if(e.getPlayer().getGameMode() == GameMode.CREATIVE)
                			{
        						File f = new File(VallendiaMinigame.getInstance().getFileManager().getFireLocationsFile().getAbsolutePath() + "/" + location + ".yml");
        						if(f.exists())
        						{
        							f.delete();
        							Language.sendVallendiaMessage(e.getPlayer(), "Fire location removed.");
        							return;
        						}
                			}
        					
        					e.setCancelled(true);
        					Language.sendVallendiaMessage(e.getPlayer(), "You can not remove this fire.");
        				}	
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
        	
        	@EventHandler
        	public void onIgnite(BlockIgniteEvent e)
        	{
        		if(e.getIgnitingEntity() instanceof Player)
        		{
        			Player p = (Player) e.getIgnitingEntity();
        			Material mainhand = p.getInventory().getItemInMainHand().getType();
        			Material offhand = p.getInventory().getItemInOffHand().getType();
        			Location loc = e.getBlock().getLocation();
        			if(p.getGameMode() == GameMode.CREATIVE)
        			{
        				if(mainhand == Material.FLINT_AND_STEEL || offhand == Material.FLINT_AND_STEEL)
        				{
        					if(mainhand == Material.INK_SACK || offhand == Material.INK_SACK)
        					{
        						return;
        					}
            				VallendiaMinigame.getInstance().firelocations.createFile(loc.toString(), loc);
            				FireLocations.locations.add(loc.toString() + ".yml");
                			Language.sendVallendiaMessage(p, "Saved fire location.");		
        				}
        			}
        		}
        	}
        	
        	@EventHandler
        	public void onTarget(EntityTargetEvent e)
        	{
        		if(e.getEntity() instanceof Creature)
        		{
        			Creature mob = (Creature) e.getEntity();
        			if(mob.hasPotionEffect(PotionEffectType.BLINDNESS))
        			{
        				e.setCancelled(true);
        				return;
        			}
        			
        			if(isStunned(mob))
        			{
        				e.setCancelled(true);
        			}
        			
        			if(invisible.contains(e.getTarget()))
        			{
        				e.setCancelled(true);
        			}
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
    	
		if(!invisible.contains(p))
		{
	        p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 0.4, 0.4), 5);
	        p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 0.4, 0), 5);
	        p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0.4, 0.4, 0), 5);	
		}
        if(p instanceof Player)
        {
            ScoreboardHandler.updateMaxHealth((Player) p);	
        }
    }
    
    public static void damageEntity(LivingEntity target, LivingEntity damager, int amount)
    {
		handleDamage.put(target, amount);
		Vector vel = target.getVelocity();
		target.damage(amount, damager);
		target.setVelocity(vel);
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
    
    
    
    
    
    
    
    
    public static boolean hasReagents(Player p, ItemStack i,int amount, String abilityName, boolean sendFalseMessage)
    {
    	Inventory inv = p.getInventory();
    	
		if(inv.containsAtLeast(i, i.getAmount()))
		{
			inv.removeItem(new ItemStack(i.getType(), i.getAmount()));
			return true;
		}
    	
        if(sendFalseMessage)
        {
            Language.sendAbilityUseMessage(p, "You don't have enough reagents for this ability!", abilityName);	
        }
		return false;
        }
    
    
    
    
    
    public static boolean hasReagentsMultiple(Player p, ArrayList<ItemStack> items, String abilityName, boolean sendFalseMessage)
    {
    	Inventory inv = p.getInventory();
    	int trues = 0;
    	for(ItemStack i: items)
    	{
    		if(!inv.containsAtLeast(i, i.getAmount()))
    		{
    	        if(sendFalseMessage)
    	        {
    	            Language.sendAbilityUseMessage(p, "You don't have enough reagents for this ability!", abilityName);	
    	        }
    			return false;
    		}
    		trues ++;
    	}
    	for(ItemStack i: items)
    	{
        	if(trues == items.size())
        	{
    			inv.removeItem(new ItemStack(i.getType(), i.getAmount()));
        	}	
    	}
    	
        return true;
    }
    
    
    
    public static void arcParticle(LivingEntity e, de.slikey.effectlib.Effect effect, double velocity, Runnable run)
    {
		Snowball ball = e.launchProjectile(Snowball.class);
		ball.setSilent(true);
		ball.setVelocity(ball.getVelocity().multiply(velocity));
		arcProjectiles.put(ball, run);
		 
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
		    PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(ball.getEntityId());
		    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
			effect.setEntity(ball);
			effect.start();
    }
    
    
    
    public static void spawnEvokerFang(Player p, Location l, int damage)
    {
	  		Entity e = l.getWorld().spawnEntity(l, EntityType.EVOKER_FANGS);
	  		evokerFangs.put(e, p);
	  		evokerFangsDamage.put(e, damage);
	  		
			new BukkitRunnable() {
	            @Override
	            public void run() {
	            	if(evokerFangs.containsKey(e))
	            	{
	            		evokerFangs.remove(e);
	            		evokerFangsDamage.remove(e);
	            	}
	            }
	        }.runTaskLater(VallendiaMinigame.getInstance(), 20 * 5); 
    }
    
    
    public static void followTargetParticle(LivingEntity caster, LivingEntity target, de.slikey.effectlib.Effect particleEffect, 
    		boolean hitWalls, boolean hitEntity, Runnable entityHit, Runnable wallHit, double particleSpeed, 
    		int maxDistance)
    {
	 	  new BukkitRunnable(){      
              Location loc = caster.getLocation();
              double t = 0;
              public void run(){
            	  //t effects speed of article
                      t = t + particleSpeed;
                      Location tloc = target.getLocation();
                      Vector direction = tloc.toVector().subtract(loc.toVector()).normalize();
                      double x = direction.getX() * t;
                      double y = direction.getY() * t + 1.5;
                      double z = direction.getZ() * t;
                      loc.add(x,y,z);
                      particleEffect.setLocation(loc);

            			if(hitWalls && loc.getBlock().getType().isSolid())
            			{
            				if(wallHit != null)
            				{
            					wallHit.run();
            				}
            				this.cancel();
            				particleEffect.cancel();
            			}
            			if(hitEntity && loc.distance(tloc) <=2)
            			{
            				if(entityHit != null)
            				{
            					entityHit.run();
            				}
                            this.cancel();
            				particleEffect.cancel();
            			}
                      loc.subtract(x,y,z);   
                      if (t > maxDistance){
                          this.cancel();
          				particleEffect.cancel();
                  }
                    
              }
 	 	  }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
 			particleEffect.start();
    }
    
    
    
    
    	
    }


