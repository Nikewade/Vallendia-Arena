package me.Nikewade.VallendiaMinigame.Events;

import java.util.HashMap;

<<<<<<< HEAD
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
=======
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
>>>>>>> second-repo/master
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
<<<<<<< HEAD
=======
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
>>>>>>> second-repo/master

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Utils;


public class PlayerBlockEvents implements Listener {
HashMap<Location, BlockState> blocks = new HashMap<>();
<<<<<<< HEAD
VallendiaMinigame Main;


=======
public static double regenTime = 1200;
VallendiaMinigame Main;

//Saving fire in in ability utils
>>>>>>> second-repo/master

public PlayerBlockEvents(VallendiaMinigame Main)
{
	this.Main = Main;
	Main.getServer().getPluginManager().registerEvents(this, Main);
<<<<<<< HEAD
}
=======
	PlayerBlockEvents.regenTime = (30* Math.pow(Math.E, -0.17328 * (Bukkit.getOnlinePlayers().size() - 1)) * 60);
	if(regenTime < 120)
	{
		regenTime = 120;
	}
}

>>>>>>> second-repo/master
	//END GATE TELEPORT
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		Block b = e.getTo().getBlock();
		Material a = b.getType();
		Material abovea = b.getLocation().add(0, 1, 0).getBlock().getType();
		Player p = e.getPlayer();
		if(a == Material.END_GATEWAY || abovea == Material.END_GATEWAY)
		{
<<<<<<< HEAD
			Main.spawnhandler.teleportPlayerRandom(p);
=======
			if(Main.kitmanager.getKit(p).getName(false).equalsIgnoreCase("starter") && p.getGameMode() != GameMode.CREATIVE)
			{
				Vector v = b.getLocation().getDirection();
				v.multiply(-10 / 10D);
				p.setVelocity(v);
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, (float) 0.7);
		        p.sendTitle(Utils.Colorate(Utils.Colorate("&3&lPick a class!")), null, 0, 26, 0);
			}else
			{
				Main.spawnhandler.teleportPlayerRandom(p);	
			}
>>>>>>> second-repo/master
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		
		if(p.getGameMode() == GameMode.CREATIVE)
		{
			return;
		}
		
		
<<<<<<< HEAD
=======
		
>>>>>>> second-repo/master
		Block block = e.getBlock();
		
		
				if(VallendiaMinigame.getInstance().worldguard.canBuild(p
				, block))
				{
					if(block.getType() == Material.COAL_ORE || block.getType() == Material.IRON_ORE || block.getType() == Material.GOLD_ORE || block.getType() == Material.DIAMOND_ORE || block.getType() == Material.EMERALD_ORE)
					{
<<<<<<< HEAD
						Utils.regenBlock(block, 300);
=======
						Utils.regenBlock(block, (int) regenTime);
>>>>>>> second-repo/master
						e.setExpToDrop(0);
						return;
					}
					
					if(block.getType() == Material.CROPS && block.getData() == (byte) 7 || block.getType() == Material.BROWN_MUSHROOM ||
						block.getType() == Material.RED_MUSHROOM || block.getType() == Material.CARROT ||
						block.getType() == Material.POTATO || block.getType() == Material.NETHER_WARTS && block.getData() == (byte) 3)
					{
						if(block.getType() == Material.CROPS)
						{
							e.setDropItems(false);
							block.getWorld().dropItemNaturally(block.getLocation(), 
									new ItemStack(Material.WHEAT));
<<<<<<< HEAD
							Utils.regenBlock(block, 300);
=======
							Utils.regenBlock(block, (int) regenTime);
>>>>>>> second-repo/master
							block.setType(Material.CROPS);
							e.setExpToDrop(0);
							e.setCancelled(true);
							return;
						}
<<<<<<< HEAD
						Utils.regenBlock(block, 300);
=======
						Utils.regenBlock(block, (int) regenTime);
>>>>>>> second-repo/master
						Material tempData = block.getState().getType();
						block.breakNaturally();
						if(tempData == Material.NETHER_WARTS)
						{
							block.setType(Material.NETHER_WARTS);
						}else
						{
							block.setType(Material.CROPS);	
						}	
						e.setExpToDrop(0);
						e.setCancelled(true);
						return;
					}
					
<<<<<<< HEAD
					if(block.getType() == Material.LEAVES || block.getTypeId() == 95 || block.getTypeId() == 20 || block.getTypeId() == 160 || block.getTypeId() == 102)
					{
						Utils.regenBlock(block, 15);
						block.setType(Material.AIR);
=======
					if(block.getType() == Material.LEAVES || block.getType() == Material.LONG_GRASS ||
							block.getType() == Material.DOUBLE_PLANT)
					{
						Utils.regenBlock(block, 15);
						block.setType(Material.AIR);
						block.getWorld().playSound(block.getLocation(), Sound.BLOCK_GRASS_BREAK, 1.0F, 1.0F);
>>>>>>> second-repo/master
						e.setDropItems(false);
						e.setCancelled(true);
						return;

					}
<<<<<<< HEAD
=======
					
					if(block.getType() == Material.WEB)
					{
						Utils.regenBlock(block, 15);
						block.setType(Material.AIR);
						block.getWorld().playSound(block.getLocation(), Sound.BLOCK_STONE_BREAK, 1.0F, 1.0F);
						e.setDropItems(false);
						e.setCancelled(true);
						return;
					}
					
					if(block.getTypeId() == 95 || block.getTypeId() == 20 || block.getTypeId() == 160 || block.getTypeId() == 102)
					{
						Utils.regenBlock(block, 60);
						block.breakNaturally();
						e.setDropItems(false);
						e.setCancelled(true);
						block.getWorld().playSound(block.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0F, 1.0F);
						return;
					}
>>>>>>> second-repo/master
				}
		
		
		e.setCancelled(true);
	}
	
	
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		Player p = e.getPlayer();
		
		if(p.getGameMode() == GameMode.CREATIVE)
		{
			return;
		}
		
		e.setCancelled(true);
		
	}

	
	
	@EventHandler
	public void trampleCrop(PlayerInteractEvent event)
	{
	    if(event.getAction() == Action.PHYSICAL)
	    	if(event.getClickedBlock().getType() == Material.SOIL || event.getClickedBlock().getType() == Material.CROPS)
	    	{
		        event.setCancelled(true);	
	    	}
	}
	
	@EventHandler
	public void blockChestInterract(PlayerInteractEvent e) 
	{
		if (!e.isCancelled())
		{ 
			Player p = e.getPlayer();
			
			if(p.getGameMode() == GameMode.CREATIVE)
			{
				return;
			}
		    if(e.getAction() == Action.RIGHT_CLICK_BLOCK) 
		    {
<<<<<<< HEAD
		        if(e.getClickedBlock().getState() instanceof InventoryHolder || e.getClickedBlock().getType() == Material.ENDER_CHEST)
=======
		        if(e.getClickedBlock().getState() instanceof InventoryHolder || e.getClickedBlock().getType() == Material.ENDER_CHEST ||
		        		e.getClickedBlock().getType() == Material.FLOWER_POT || e.getClickedBlock().getType() == Material.ITEM_FRAME || 
		        		e.getClickedBlock().getType() == Material.WORKBENCH || e.getClickedBlock().getType() == Material.ANVIL ||
		        		e.getClickedBlock().getType() == Material.BED)
>>>>>>> second-repo/master
		        {
		            e.setCancelled(true);
		        }   
		    }
		} 
		
	}
	
	@EventHandler
	public void entityBreak(HangingBreakByEntityEvent e)
	{
		if(e.getEntity() instanceof ItemFrame || e.getEntity() instanceof Painting )
		{
			if(e.getRemover() instanceof Player)
			{
				Player p = (Player) e.getRemover();
				if(p.getGameMode() == GameMode.CREATIVE)
				{
					return;
				}
			}
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void zombieBreakDoor(EntityBreakDoorEvent e)
	{
		Utils.regenBlock(e.getBlock(), 30);
	}
	
	
    @EventHandler
    public void onVinesGrow(BlockGrowEvent e)
    {
        if(e.getBlock().getType() == Material.CROPS || e.getBlock().getType() == Material.NETHER_WARTS)
        {
            e.setCancelled(true);
        }
    }
<<<<<<< HEAD
=======
    
    
    
    
>>>>>>> second-repo/master
	
	
}
