package me.Nikewade.VallendiaMinigame.Donations;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Commands.DonateCommand;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;
import me.kvq.supertrailspro.API.SuperTrailsAPI;

public class WingsMenuGUI {
	
	public static void openWingsMenu(Player p)
	{
		AdvInventory wingsMenu = new AdvInventory(Utils.Colorate(""), 54, Utils.placeholder((byte) 15, " "));	
		for(int i = 10; i < 45 ; i++)
		{
			wingsMenu.setItem(new ItemStack(Material.AIR), i);
		}
		
		 wingsMenu.setItem(new ItemStack(Material.ARROW), Utils.Colorate("&8<< Back"), 45, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();			    	
			    	DonateCommand.openCosmeticMainMenu(ep);
			    }
			});
		 
		 wingsMenu.setItem(new ItemStack(159,1,(short) 14), Utils.Colorate("&4Remove your wings"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();			    	
			    	SuperTrailsAPI.getPlayerData(ep).resetWings();
			    }
			});
		 
		 String vampire = Utils.Colorate("&8&lVampire Wings");
		 if(p.hasPermission("trails.wings.Vampire"))
		 {
			 vampire = Utils.Colorate("&6&lVampire Wings");
		 }
		 
		wingsMenu.setItem(new ItemStack(Material.GHAST_TEAR), vampire, 9, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	if(ep.hasPermission("trails.wings.Vampire"))
			    	{
			       	SuperTrailsAPI.getPlayerData(ep).setPattenWings(300, "VampirePattern");
			       	ep.closeInventory();
			    	}else
			    	{
			    		ep.sendMessage(Utils.Colorate("&cYou don't have these wings!"));
			    	}
			    }
			});
		
		 String angel = Utils.Colorate("&8&lAngel Wings");
		 if(p.hasPermission("trails.wings.Angel"))
		 {
			 angel = Utils.Colorate("&6&lAngel Wings");
		 }
		  
		wingsMenu.setItem(new ItemStack(Material.FEATHER), angel, 10, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	if(ep.hasPermission("trails.wings.Angel"))
			    	{
				       	SuperTrailsAPI.getPlayerData(ep).setPattenWings(301, "AngelPattern");
				       	ep.closeInventory();
			    	}else
			    	{
			    		ep.sendMessage(Utils.Colorate("&cYou don't have these wings!"));
			    	}

			    }
			});
		
		 String phoenix = Utils.Colorate("&8&lPhoenix Wings");
		 if(p.hasPermission("trails.wings.Phoenix"))
		 {
			 phoenix = Utils.Colorate("&6&lPhoenix Wings");
		 }
		  
		 wingsMenu.setItem(new ItemStack(Material.BLAZE_POWDER), phoenix, 11, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	if(ep.hasPermission("trails.wings.Phoenix"))
			    	{
				       	SuperTrailsAPI.getPlayerData(ep).setPattenWings(302, "PhoenixPattern");
				       	ep.closeInventory();
			    	}else
			    	{
			    		ep.sendMessage(Utils.Colorate("&cYou don't have these wings!"));
			    	}

			    }
			});
		 
		 String bbutterfly = Utils.Colorate("&8&lBlue Butterfly Wings");
		 if(p.hasPermission("trails.wings.BlueButterfly"))
		 {
			 bbutterfly = Utils.Colorate("&6&lBlue Butterfly Wings");
		 }
		  
		 wingsMenu.setItem(new ItemStack(95,1,(short) 3), bbutterfly, 12, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	
			    	if(ep.hasPermission("trails.wings.BlueButterfly"))
			    	{
				       	SuperTrailsAPI.getPlayerData(ep).setPattenWings(303, "ButterflyPattern");
				       	ep.closeInventory();
			    	}else
			    	{
			    		ep.sendMessage(Utils.Colorate("&cYou don't have these wings!"));
			    	}

			    }
			});
		 
		 String pbutterfly = Utils.Colorate("&8&lPink Butterfly Wings");
		 if(p.hasPermission("trails.wings.PinkButterfly"))
		 {
			 pbutterfly = Utils.Colorate("&6&lPink Butterfly Wings");
		 }
		 
		 wingsMenu.setItem(new ItemStack(95, 1, (short) 6), pbutterfly, 13, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	if(ep.hasPermission("trails.wings.PinkButterfly"))
			    	{
				       	SuperTrailsAPI.getPlayerData(ep).setPattenWings(303, "ButterflyPatternPink");
				       	ep.closeInventory();
			    	}else
			    	{
			    		ep.sendMessage(Utils.Colorate("&cYou don't have these wings!"));
			    	}

			    }
			});
		 
		 String obutterfly = Utils.Colorate("&8&lOrange Butterfly Wings");
		 if(p.hasPermission("trails.wings.OrangeButterfly"))
		 {
			obutterfly = Utils.Colorate("&6&lOrange Butterfly Wings");
		 }
		 
		 wingsMenu.setItem(new ItemStack(95, 1, (short) 1), obutterfly, 14, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	if(ep.hasPermission("trails.wings.OrangeButterfly"))
			    	{
				       	SuperTrailsAPI.getPlayerData(ep).setPattenWings(303, "ButterflyPatternOrange");
				       	ep.closeInventory();
			    	}else
			    	{
			    		ep.sendMessage(Utils.Colorate("&cYou don't have these wings!"));
			    	}

			    }
			});
		 
		 String pfairy = Utils.Colorate("&8&lPink Fairy Wings");
		 if(p.hasPermission("trails.wings.PinkFairy"))
		 {
			 pfairy = Utils.Colorate("&6&lPink Fairy Wings");
		 }
		 
		 wingsMenu.setItem(new ItemStack(160, 1, (short) 6), pfairy, 15, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	if(ep.hasPermission("trails.wings.PinkFairy"))
			    	{
				       	SuperTrailsAPI.getPlayerData(ep).setPattenWings(304, "FairyPatternPink");
				       	ep.closeInventory();
			    	}else
			    	{
			    		ep.sendMessage(Utils.Colorate("&cYou don't have these wings!"));
			    	}

			    }
			});
		 
		 String dfairy = Utils.Colorate("&8&lDark Fairy Wings");
		 if(p.hasPermission("trails.wings.DarkFairy"))
		 {
			 dfairy = Utils.Colorate("&6&lDark Fairy Wings");
		 }
		 
		 wingsMenu.setItem(new ItemStack(160, 1, (short) 7), dfairy, 16, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	if(ep.hasPermission("trails.wings.DarkFairy"))
			    	{
				       	SuperTrailsAPI.getPlayerData(ep).setPattenWings(304, "FairyPatternBlack");
				       	ep.closeInventory();
			    	}else
			    	{
			    		ep.sendMessage(Utils.Colorate("&cYou don't have these wings!"));
			    	}

			    }
			});
		 
		 String wfairy = Utils.Colorate("&8&lWhite Fairy Wings");
		 if(p.hasPermission("trails.wings.WhiteFairy"))
		 {
			 wfairy = Utils.Colorate("&6&lWhite Fairy Wings");
		 }
		 
		 wingsMenu.setItem(new ItemStack(160, 1, (short) 0), wfairy, 17, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	if(ep.hasPermission("trails.wings.WhiteFairy"))
			    	{
				       	SuperTrailsAPI.getPlayerData(ep).setPattenWings(304, "FairyPatternWhite");
				       	ep.closeInventory();
			    	}else
			    	{
			    		ep.sendMessage(Utils.Colorate("&cYou don't have these wings!"));
			    	}

			    }
			});
		 
		 String bfairy = Utils.Colorate("&8&lBlue Fairy Wings");
		 if(p.hasPermission("trails.wings.BlueFairy"))
		 {
			 bfairy = Utils.Colorate("&6&lBlue Fairy Wings");
		 }
		 
		 wingsMenu.setItem(new ItemStack(160, 1, (short) 3), bfairy, 18, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	if(ep.hasPermission("trails.wings.BlueFairy"))
			    	{
				       	SuperTrailsAPI.getPlayerData(ep).setPattenWings(304, "FairyPatternBlue");
				       	ep.closeInventory();
			    	}else
			    	{
			    		ep.sendMessage(Utils.Colorate("&cYou don't have these wings!"));
			    	}

			    }
			});
		 
		 wingsMenu.openInventory(p);
	}

}