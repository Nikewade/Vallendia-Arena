package me.Nikewade.VallendiaMinigame.Events;

import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
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
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Utils;


public class PlayerBlockEvents implements Listener {
HashMap<Location, BlockState> blocks = new HashMap<>();
VallendiaMinigame Main;



public PlayerBlockEvents(VallendiaMinigame Main)
{
	this.Main = Main;
	Main.getServer().getPluginManager().registerEvents(this, Main);
}
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
			Main.spawnhandler.teleportPlayerRandom(p);
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
		
		
		Block block = e.getBlock();
		
		
				if(VallendiaMinigame.getInstance().worldguard.canBuild(p
				, block))
				{
					if(block.getType() == Material.COAL_ORE || block.getType() == Material.IRON_ORE || block.getType() == Material.GOLD_ORE || block.getType() == Material.DIAMOND_ORE || block.getType() == Material.EMERALD_ORE)
					{
						Utils.regenBlock(block, 300);
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
							Utils.regenBlock(block, 300);
							block.setType(Material.CROPS);
							e.setExpToDrop(0);
							e.setCancelled(true);
							return;
						}
						Utils.regenBlock(block, 300);
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
					
					if(block.getType() == Material.LEAVES || block.getTypeId() == 95 || block.getTypeId() == 20 || block.getTypeId() == 160 || block.getTypeId() == 102)
					{
						Utils.regenBlock(block, 15);
						block.setType(Material.AIR);
						e.setDropItems(false);
						e.setCancelled(true);
						return;

					}
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
		        if(e.getClickedBlock().getState() instanceof InventoryHolder || e.getClickedBlock().getType() == Material.ENDER_CHEST)
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
    public void onVinesGrow(BlockGrowEvent e)
    {
        if(e.getBlock().getType() == Material.CROPS || e.getBlock().getType() == Material.NETHER_WARTS)
        {
            e.setCancelled(true);
        }
    }
	
	
}
