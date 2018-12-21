package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class ClimbAbility implements Ability , Listener {
	public static ArrayList<Player> climbing = new ArrayList<>();
	public static ArrayList<Player> holdingOn = new ArrayList<>();
	public static ArrayList<Player> falling = new ArrayList<>();
	public static HashMap<String, Integer> jumps = new HashMap<>();
	

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Climb";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Allows you to climb up things and hang on ledges.";
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.TRIPWIRE_HOOK);
	}

	@Override
	public boolean RunAbility(Player p) {
		if(climbing.contains(p))
		{
			climbing.remove(p);
			p.sendMessage(Utils.Colorate("&8&l[Climb] &7Disabled"));
			return true;
		}
		p.sendMessage(Utils.Colorate("&8&l[Climb] &7Enabled"));
		p.sendMessage(Utils.Colorate("&7With climb enabled, you can right click a block to jump up."));
		p.sendMessage(Utils.Colorate("&7You have 3 jumps total that you can use while climbing. Once you land, these jumps reset."));
		p.sendMessage(Utils.Colorate("&7When next to a ledge, you can crouch to hang on which will allow you to jump once more."));
		climbing.add(p);
		return false;
	}
	
	
	public static boolean climbableBlock(Block b)
	{
		if ((b.getType() != Material.AIR) && (b.getType() != Material.WATER) && (b.getType() != Material.STATIONARY_WATER) && (b.getType() != Material.SAPLING) && (b.getType() != Material.CARPET) 
	    		&& (b.getType() != Material.CROPS) && (b.getType() != Material.DOUBLE_PLANT) && (b.getType() != Material.FIRE) && (b.getType() != Material.FLOWER_POT) && (b.getType() != Material.IRON_TRAPDOOR) 
	    		&& (b.getType() != Material.LADDER) && (b.getType() != Material.LAVA) && (b.getType() != Material.LEVER) && (b.getType() != Material.LONG_GRASS)  && (b.getType() != Material.MELON_SEEDS) 
	    		&& (b.getType() != Material.MELON_STEM) && (b.getType() != Material.NETHER_STALK) && (b.getType() != Material.NETHER_WARTS) && (b.getType() != Material.PAINTING) && (b.getType() != Material.POWERED_RAIL) 
	    		&& (b.getType() != Material.RAILS) && (b.getType() != Material.ACTIVATOR_RAIL) && (b.getType() != Material.DETECTOR_RAIL) && (b.getType() != Material.POWERED_RAIL) && (b.getType() != Material.RED_MUSHROOM) 
	    		&& (b.getType() != Material.BROWN_MUSHROOM) && (b.getType() != Material.SNOW) && (b.getType() != Material.SAPLING) && (b.getType() != Material.TRAP_DOOR) && (b.getType() != Material.SKULL) && (b.getType() != Material.SKULL) 
	    		&& (b.getType() != Material.STATIONARY_LAVA) && (b.getType() != Material.BANNER) && (b.getType() != Material.STANDING_BANNER) && (b.getType() != Material.WALL_SIGN) && (b.getType() != Material.WALL_BANNER))	
	    {	
	    	return true;	
	    }
		return false;
	}
	
	
	
	public static boolean touchingBlock(Player p)
	{
		int intPlayerX = p.getLocation().getBlockX();
		int intPlayerY = p.getLocation().getBlockY();
		int intPlayerZ = p.getLocation().getBlockZ();
		double dubPlayerX = p.getLocation().getX();
		double dubPlayerZ = p.getLocation().getZ();
		Block b = p.getWorld().getBlockAt(new Location(p.getWorld(), intPlayerX + 1, intPlayerY, intPlayerZ));
		if((dubPlayerX - intPlayerX <= 69))
		{
			if (climbableBlock(b))	
			{
				return true;
			}
		}
		    
			Block b2 = p.getWorld().getBlockAt(new Location(p.getWorld(), intPlayerX - 1, intPlayerY, intPlayerZ));
			if((dubPlayerX - intPlayerX <= 69))
			{
				if (climbableBlock(b2))	
				{
					return true;
				}
			}
		    
		    
			Block b3 = p.getWorld().getBlockAt(new Location(p.getWorld(), intPlayerX, intPlayerY, intPlayerZ + 1));
			if((dubPlayerX - intPlayerX <= 69))
			{
				if (climbableBlock(b3))	
				{
					return true;
				}
			}
		    
		    
			Block b4 = p.getWorld().getBlockAt(new Location(p.getWorld(), intPlayerX, intPlayerY, intPlayerZ - 1));
			if((dubPlayerX - intPlayerX <= 69))
			{
				if (climbableBlock(b4))	
				{
					return true;
				}
			}
	    
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
    public static Listener getListener() {
        return new Listener() {
        	
        	
        	
        	@EventHandler
        	public void onClick(PlayerInteractEvent e)
        	{
        		if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        		{
        			return;
        		}
        		if(!ClimbAbility.climbing.contains(e.getPlayer()))
        		{
        			return;
        		}
        			Player p = e.getPlayer();
        			if(p.getItemInHand().getType() != Material.AIR)
        			{
        				p.sendMessage(Utils.Colorate("&8&l[Climb] &7You can only climb with empty hands!"));
        				return;
        			}
        				EquipmentSlot eslot = e.getHand();
        				if(eslot.equals(EquipmentSlot.HAND))
        				{
        						if(ClimbAbility.touchingBlock(p))
        						{	
        							if(!(e.getPlayer().getFallDistance() < 12))
        							{
        								e.getPlayer().sendMessage(Utils.Colorate("&8&l[Climb] &7You could not catch yourself!"));
        								return;
        							}
        								if(!ClimbAbility.jumps.containsKey(p.getName()))
        								{
        									
        									if(ClimbAbility.holdingOn.contains(e.getPlayer()))
        									{
        										ClimbAbility.holdingOn.remove(p);
        										p.setGravity(true);
        										e.getPlayer().setVelocity(new Vector(0, 0.6D, 0));
        										return;
        									}
        									
        									ClimbAbility.jumps.put(p.getName(), 1);	
        									p.sendMessage(Utils.Colorate("&8&l[Climb] &7Jumps: 1/3"));
        									p.setVelocity(new Vector(0, 0.6D, 0));	
        									ClimbAbility.falling.add(p);
        									
        									
        										new BukkitRunnable() {
        						                   @Override
        						                   public void run() {
        						                	   if(p.isOnGround())
        						                	   {
        						                		   ClimbAbility.falling.remove(p);
        						                		   ClimbAbility.jumps.remove(p.getName());
        						                		   this.cancel();
        						                	   }
        						                   }
        						               }.runTaskTimer(VallendiaMinigame.getInstance(), 5L, 0L);  
        									
        									return;
        								}
        								
        								if(ClimbAbility.holdingOn.contains(e.getPlayer()))
        								{
        									ClimbAbility.holdingOn.remove(p);
        									p.setGravity(true);
        									e.getPlayer().setVelocity(new Vector(0, 0.6D, 0));
        									return;
        								}
        								
        								if((ClimbAbility.jumps.get(p.getName()) >= 3))
        								{
        										return;
        								}
        								ClimbAbility.jumps.replace(p.getName(), ClimbAbility.jumps.get(p.getName()), ClimbAbility.jumps.get(p.getName()) + 1);
        								p.sendMessage(Utils.Colorate("&8&l[Climb] &7Jumps: " + ClimbAbility.jumps.get(p.getName()) + "&7/3"));
        								p.setVelocity(new Vector(0, 0.6D, 0));	
        						}
        				}
        				
        	}


        	@EventHandler
        	public void onShift(PlayerToggleSneakEvent e)
        	{
        		Player p = e.getPlayer();
        		if(!ClimbAbility.climbing.contains(p))
        		{
        			return;
        		}
        		if(!ClimbAbility.touchingBlock(p))
        		{
        			p.setGravity(true);
        			ClimbAbility.holdingOn.remove(p);
        			return;
        		}
        		if(p.getItemInHand().getType() != Material.AIR && !holdingOn.contains(p))
        		{
        			p.sendMessage(Utils.Colorate("&8&l[Climb] &7You can only climb with empty hands!"));
        			return;
        		}
        		if(!e.isSneaking())
        		{
        			ClimbAbility.holdingOn.remove(p);
        			p.setGravity(true);
        			return;
        		}
        		if(!p.isOnGround())
        		if(p.getTargetBlock((Set<Material>) null, 1).getLocation().add(0, 1, 0).getBlock().getType() == Material.AIR)
        		{
        			ClimbAbility.holdingOn.add(p);
        			p.sendMessage(Utils.Colorate("&8&l[Climb] &7You are holding onto a ledge, right click for one more jump."));
        		}

        	}

        	@EventHandler
        	public void onMove(PlayerMoveEvent e)
        	{
        		if(!ClimbAbility.climbing.contains(e.getPlayer()))
        		{
        			return;
        		}
        		if(!ClimbAbility.holdingOn.contains(e.getPlayer()))
        		{
        			return;
        		}
        			Location to = e.getFrom();
        			to.setPitch(e.getTo().getPitch());
        			to.setYaw(e.getTo().getYaw());
        			e.setTo(to);
        			e.getPlayer().setGravity(false);
        	}

        	
        	
            @EventHandler
            public void onCharacterLeave(PlayerQuitEvent e)
            {
        		if(ClimbAbility.climbing.contains(e.getPlayer()))
            	{
                	ClimbAbility.climbing.remove(e.getPlayer());
            	}
            }
            

            
        };
    }
	
	
    public static void onDie(Player p)
    {
		if(ClimbAbility.climbing.contains(p))
    	{
        	ClimbAbility.climbing.remove(p);
    	}
    }
    
	
	
	
	

}
