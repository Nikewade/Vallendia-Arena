package me.Nikewade.VallendiaMinigame.Events;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

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
	
	@EventHandler
	public void onBreak(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		
		if(p.getGameMode() == GameMode.CREATIVE)
		{
			return;
		}
		
		
		Block block = e.getBlock();
		
		
		if(block.getType() == Material.COAL_ORE || block.getType() == Material.IRON_ORE || block.getType() == Material.GOLD_ORE || block.getType() == Material.DIAMOND_ORE || block.getType() == Material.EMERALD_ORE)
		{
			Utils.regenBlock(block, 2);
			e.setExpToDrop(0);
			return;
		}
		
		if(block.getType() == Material.CROPS || block.getType() == Material.CARROT || block.getType() == Material.POTATO || block.getType() == Material.NETHER_WARTS)
		{
			Utils.regenBlock(block, 300);
			e.setExpToDrop(0);
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
		        if(e.getClickedBlock().getState() instanceof InventoryHolder)
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
	
	
}
