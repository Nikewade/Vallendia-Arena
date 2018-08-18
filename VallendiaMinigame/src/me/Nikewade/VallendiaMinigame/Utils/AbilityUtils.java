package me.Nikewade.VallendiaMinigame.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityUtils {
	private static Set<Material> transparentBlocks = null;
	
	
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
	
	
	
	
		public static Collection<Entity> getAoeTargets(Player originplayer, int Radiusx, int Radiusy, int Radiusz)
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
	
	
	
	
}
