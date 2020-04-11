package me.Nikewade.VallendiaMinigame.Graphics;

import org.bukkit.Color;
import org.bukkit.Material;
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

public class KitGui {
	VallendiaMinigame Main;
	KitManager km;
	UpgradeManager um;
	String upgradePurchase;
	
	public KitGui(VallendiaMinigame Main)
	{
	    this.Main = Main;
	    this.km = Main.kitmanager;
	    this.um = Main.upgrademanager;
	}
	
	
	//KITS
	  public void openKitMenu(Player p)
	  {
		  AdvInventory kitInv = new AdvInventory(Utils.Colorate("&8&lPick your Class"), 27, Utils.placeholder((byte) 7, " "));
		  Kit warrior = km.kit("warrior");
		  Kit archer = km.kit("archer");
		  Kit assassin = km.kit("assassin");
		  Kit mage = km.kit("mage");
		  kitInv.setItem(new ItemStack(Material.IRON_SWORD), Utils.Colorate("&4&lWarrior"), 10, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    		KitGui.this.yesNoMenu((Player) e.getWhoClicked(), warrior);
			    }
			}, warrior.getDescription(), warrior.getDescription2());
		  
		  kitInv.setItem(new ItemStack(Material.BOW), Utils.Colorate("&2&lArcher"), 12, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			        	KitGui.this.yesNoMenu((Player) e.getWhoClicked(), archer);
			    }
			}, archer.getDescription() , archer.getDescription2() );
		  
		  ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
		  LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
		  helmmeta.setColor(Color.BLACK);
		  kitInv.setItem(new ItemStack(helm), Utils.Colorate("&8&lAssassin"), 14, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    		KitGui.this.yesNoMenu((Player) e.getWhoClicked(), assassin);
			    }
			}, assassin.getDescription(), assassin.getDescription2());
		  
		  kitInv.setItem(new ItemStack(Material.EYE_OF_ENDER), Utils.Colorate("&3&lMage"), 16, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    		KitGui.this.yesNoMenu((Player) e.getWhoClicked(), mage);
			    }
			}, mage.getDescription() , mage.getDescription2() );
		  kitInv.openInventory(p);
	  }
	  
	  
	  
	  
	  
	  
	  
	  
	
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  //YES NO MENU
		public void yesNoMenu(Player p, Kit kit)
		{
			AdvInventory kitInvYesNo = new AdvInventory(Utils.Colorate("&8&lAre you sure?"), 27, Utils.placeholder((byte) 7, " "));
			kitInvYesNo.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 13), Utils.Colorate("&2&lYes"), 11, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			        Main.kitmanager.giveKit(ep, kit.getName(false));
			        ep.closeInventory();
			        ep.sendTitle(kit.getName(true), "", 20, 40, 40);
			        ep.playSound(ep.getLocation(), kit.getSound(), 2, (float) 0.5);
			    }
			}, Utils.Colorate("&aConfirm class " + kit.getName(true)));
			
			
			kitInvYesNo.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lNo"), 15, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	KitGui.this.openKitMenu(ep);
			    }
			}, Utils.Colorate("&cCancel"));
			
			kitInvYesNo.openInventory(p);
		}
	

}
