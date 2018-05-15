package me.Nikewade.VallendiaMinigame.Graphics;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Kits.KitManager;
import me.Nikewade.VallendiaMinigame.Upgrades.UpgradeManager;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class GuiHandler {
	VallendiaMinigame Main;
	KitManager km;
	UpgradeManager um;
	Sound kitSound;
	String upgradePurchase;
	UpgradeGui upgrades;
	
	 public GuiHandler(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	    this.km = Main.kitmanager;
	    this.um = Main.upgrademanager;
	    this.upgrades = new UpgradeGui(Main);
	  }
	 
	 
	 public void openGui(Player p , String name)
	 {
		 if(name.equalsIgnoreCase("upgrades"))
		 {
			 
		 }
	 }
	 

	 
	 //KITS
	  public void openKitMenu(Player p)
	  {
		  Kit warrior = km.kit("warrior");
		  Kit archer = km.kit("archer");
		  Kit assassin = km.kit("assassin");
		  Kit mage = km.kit("mage");
		  AdvInventory inv = new AdvInventory(Utils.Colorate("&8&lPick your Kit"), 27, Utils.placeholder((byte) 7, " "));
		  inv.setItem(new ItemStack(Material.IRON_SWORD), Utils.Colorate("&4&lWarrior"), 10, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			        	GuiHandler.this.yesNoMenu((Player) e.getWhoClicked(), warrior);
			        	kitSound = Sound.ENTITY_ENDERDRAGON_GROWL;
			    }
			}, warrior.getDescription());
		  
		  inv.setItem(new ItemStack(Material.BOW), Utils.Colorate("&2&lArcher"), 12, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			        	GuiHandler.this.yesNoMenu((Player) e.getWhoClicked(), archer);
			        	kitSound = Sound.ENTITY_ARROW_HIT;
			    }
			}, archer.getDescription());
		  
		  ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
		  LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
		  helmmeta.setColor(Color.BLACK);
		  inv.setItem(new ItemStack(helm), Utils.Colorate("&8&lAssassin"), 14, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			        	GuiHandler.this.yesNoMenu((Player) e.getWhoClicked(), assassin);
			        	kitSound = Sound.ENTITY_ENDERDRAGON_FLAP;
			    }
			}, assassin.getDescription());
		  
		  inv.setItem(new ItemStack(Material.EYE_OF_ENDER), Utils.Colorate("&3&lMage"), 16, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			        	GuiHandler.this.yesNoMenu((Player) e.getWhoClicked(), mage);
			        	kitSound = Sound.BLOCK_END_PORTAL_SPAWN;
			    }
			}, mage.getDescription());
		  inv.openInventory(p);
	  }
	  
	  
	  
	  
	  
	  //SHOP
	  public void openShopMenu(Player p)
	  {
		  AdvInventory inv = new AdvInventory(Utils.Colorate("&8&lShop"), 27, Utils.placeholder((byte) 7, " "));
		  inv.setItem(new ItemStack(Material.BLAZE_POWDER), Utils.Colorate("&3&lAbilities"), 10, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	
			    }
			} ); 
		  
		  inv.setItem(new ItemStack(Material.IRON_CHESTPLATE), Utils.Colorate("&8&lUpgrades"), 13, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	upgrades.openUpgradeMenu(p);
			    }
			} ); 
		  
		  inv.setItem(new ItemStack(Material.POTION), Utils.Colorate("&2&lConsumables"), 16, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	
			    }
			} ); 
		  
		  inv.openInventory(p);
	  }
	  
	  
	  
	  
	
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  //YES NO MENU
		public void yesNoMenu(Player p, Kit kit)
		{
			AdvInventory inv = new AdvInventory(Utils.Colorate("&8&lAre you sure?"), 27, Utils.placeholder((byte) 7, " "));
			inv.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 13), Utils.Colorate("&2&lYes"), 11, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			        Main.kitmanager.giveKit(ep, kit.getName(false));
			        ep.closeInventory();
			        ep.sendTitle(kit.getName(true), "", 20, 40, 40);
			        ep.playSound(p.getLocation(), kitSound, 2, (float) 0.5);
			    }
			}, Utils.Colorate("&aConfirm kit " + kit.getName(true)));
			
			
			inv.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lNo"), 15, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	GuiHandler.this.openKitMenu(ep);
			    }
			}, Utils.Colorate("&cCancel"));
			
			inv.openInventory(p);
		}
	
}
