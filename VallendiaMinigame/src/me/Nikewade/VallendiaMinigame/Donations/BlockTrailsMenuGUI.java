package me.Nikewade.VallendiaMinigame.Donations;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.Commands.DonateCommand;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;
import me.kvq.supertrailspro.API.SuperTrailsAPI;

public class BlockTrailsMenuGUI {
	
	public static void openBlockTrailMenu(Player p) {
		
	AdvInventory blocksMenu = new AdvInventory(Utils.Colorate(""), 54, Utils.placeholder((byte) 15, " "));
	
	for(int i = 10; i < 45 ; i++)
	{
		blocksMenu.setItem(new ItemStack(Material.AIR), i);
	}
	
	 blocksMenu.setItem(new ItemStack(Material.ARROW), Utils.Colorate("&8<< Back"), 45, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();			    	
		    	DonateCommand.openCosmeticMainMenu(ep);
		    }
		});
	 
	 blocksMenu.setItem(new ItemStack(159,1,(short) 14), Utils.Colorate("&4Remove your trail"), 49, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();			    	
		    	SuperTrailsAPI.getPlayerData(ep).setTrail(-100);
		    }
		});
	 
	 String luxury = Utils.Colorate("&8&lLuxury Trail");
	 if(p.hasPermission("trails.blocks.Luxury"))
	 {
		 luxury = Utils.Colorate("&6&lLuxury Trail");
	 }
	 
	 blocksMenu.setItem(new ItemStack(Material.DIAMOND_BLOCK), luxury, 9, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.blocks.Luxury"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(100);;
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}
		    }
		});
	  
	 String ores = Utils.Colorate("&8&lOres Trail");
	 if(p.hasPermission("trails.blocks.Ores"))
	 {
		 ores = Utils.Colorate("&6&lOres Trail");
	 }
	 
	 blocksMenu.setItem(new ItemStack(Material.EMERALD_ORE), ores, 10, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.blocks.Ores"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(101);;
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	  
	 String clay = Utils.Colorate("&8&lClay Trail");
	 if(p.hasPermission("trails.blocks.Clay"))
	 {
		 clay = Utils.Colorate("&6&lClay Trail");
	 }
	 
	 blocksMenu.setItem(new ItemStack(159,1,(short) 4), clay, 11, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.blocks.Clay"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(102);;
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String glass = Utils.Colorate("&8&lGlass Trail");
	 if(p.hasPermission("trails.blocks.Glass"))
	 {
		 glass = Utils.Colorate("&6&lGlass Trail");
	 }
	 
	 blocksMenu.setItem(new ItemStack(95,1,(short) 0), glass, 12, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.blocks.Glass"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(103);;
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String wool = Utils.Colorate("&8&lWool Trail");
	 if(p.hasPermission("trails.blocks.Wool"))
	 {
		 wool = Utils.Colorate("&6&lWool Trail");
	 }
	 blocksMenu.setItem(new ItemStack(35, 1, (short) 1), wool, 13, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.blocks.Wool"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(104);;
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String sea = Utils.Colorate("&8&lSea Trail");
	 if(p.hasPermission("trails.blocks.Sea"))
	 {
		 sea = Utils.Colorate("&6&lSea Trail");
	 }
	 
	 
	 blocksMenu.setItem(new ItemStack(Material.SEA_LANTERN), sea, 14, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.blocks.Sea"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(105);;
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String nether = Utils.Colorate("&8&lNether Trail");
	 if(p.hasPermission("trails.blocks.Nether"))
	 {
		 nether = Utils.Colorate("&6&lNether Trail");
	 }
	 
	 blocksMenu.setItem(new ItemStack(Material.NETHERRACK), nether, 15, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.blocks.Nether"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(106);;
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String end = Utils.Colorate("&8&lEnd Trail");
	 if(p.hasPermission("trails.blocks.End"))
	 {
		 end = Utils.Colorate("&6&lEnd Trail");
	 }
	 
	 blocksMenu.setItem(new ItemStack(Material.ENDER_STONE), end, 16, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.blocks.End"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(107);;
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		}); 
	 blocksMenu.openInventory(p);
}

}