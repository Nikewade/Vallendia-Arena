package me.Nikewade.VallendiaMinigame.Events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class PlayerItemEvents implements Listener {
	VallendiaMinigame Main;
	
	
	
	public PlayerItemEvents(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
	}

	@EventHandler
	public void rightClickItem(PlayerInteractEvent e) {
 	   	if(!(e.getHand() == EquipmentSlot.HAND)) return;
	    Action a = e.getAction();
	    if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)
	    {
		    Player p = e.getPlayer();
		    ItemStack item = p.getInventory().getItemInMainHand();
		    Material itemtype = item.getType();
	    	if(itemtype != Material.AIR && item.getItemMeta().hasDisplayName())
	    	{
		 	   	String itemname = item.getItemMeta().getDisplayName();
		    	   if(itemtype == Material.NETHER_STAR && itemname.equals(Utils.Colorate("&b&lKit")))
		    	   {
			    		Main.guihandler.openGui(p, "kit");   
			    		p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
			    		return;
		    	   }	
		    	   
		    	   if(itemtype == Material.NETHER_STAR && itemname.equals(Utils.Colorate("&b&lShop")))
		    	   {
			    		Main.guihandler.openGui(p, "shop");  
			    		p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
			    		return;
		    	   }	
	    	}
	    	
	    	
	    	
	    	
	    	//ABILITIES
	    	   if(itemtype == Material.INK_SACK && item.getDurability() == 10 && item.getItemMeta().getLore() != null) // green dye
	    	   {
	    		   	String ability = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities." + ChatColor.stripColor(Utils.Colorate(item.getItemMeta().getLore().get(0).toLowerCase())));
	    		   	if(Main.abilitymanager.getAbility(ability) == null)
	    		   	{
	    		   		return;
	    		   	}
		    		if(!AbilityCooldown.isInCooldown(p.getUniqueId(), ability))
		    		{
			    		if(Main.abilitymanager.getAbility(ability).RunAbility(p))
			    		{
				    		AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), ability, Main.abilitymanager.getCooldown(ability, p));
				    		c.start();	
			    		}
		    		}else 
		    		{
		    			p.sendMessage(Utils.Colorate(" &8&lCooldown: " + AbilityCooldown.getTimeLeft(p.getUniqueId(), ability) + " secs"));
		    			return;
		    		}
		    		return;
	    	   }
	    }
	}
	
	
	//stop from moving item
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player && e.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
				return;
			}			
		    ItemStack item = e.getCurrentItem();
		    Material itemtype = item.getType();
		    String itemname = item.getItemMeta().getDisplayName();
		    
			if(e.getSlotType() == SlotType.ARMOR)
			{
				e.setCancelled(true);
			}
			
			if (itemtype == Material.NETHER_STAR && itemname != null) {
				if(itemname.equals(Utils.Colorate("&b&lKit")) || itemname.equals(Utils.Colorate("&b&lShop")))
				{
					e.setCancelled(true);
				}
			}
			
		}
	}
	
	
	@EventHandler
	public void onSwitchHand(PlayerSwapHandItemsEvent e)
	{		
		Main.levelmanager.addEXP(e.getPlayer(), 20);
	    ItemStack item = e.getOffHandItem();
	    Material itemtype = item.getType();
	    if(itemtype != Material.NETHER_STAR || !item.getItemMeta().hasDisplayName())
	    {
	    	return;
	    }
	    String itemname = item.getItemMeta().getDisplayName();
		if (itemname.equals(Utils.Colorate("&b&lKit")) || itemname.equals(Utils.Colorate("&b&lShop"))) {
			e.setCancelled(true);
		}
	}
	
	
	@EventHandler
	public void dropItem(PlayerDropItemEvent e)
	{
		Player p = e.getPlayer();
		if (p.getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
		
	}
	
}
