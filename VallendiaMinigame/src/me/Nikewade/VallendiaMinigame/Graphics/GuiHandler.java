package me.Nikewade.VallendiaMinigame.Graphics;

import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class GuiHandler {
	VallendiaMinigame Main;
	UpgradeGui upgrades;
	ShopGui shop;
	KitGui kit;
	UpgradeArmorGui armor;
	UpgradeWeaponGui weapon;
	UpgradeToolGui tool;
	AbilitiesGui abilities;
	
	 public GuiHandler(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	    this.upgrades = new UpgradeGui(Main);
	    this.shop = new ShopGui(Main);
	    this.kit = new KitGui(Main);
	    this.armor = new UpgradeArmorGui(Main);
	    this.weapon = new UpgradeWeaponGui(Main);
	    this.tool = new UpgradeToolGui(Main);
	    this.abilities = new AbilitiesGui(Main);
	    
	  }
	 
	 
	 public void openGui(Player p , String name)
	 {
		 if(name.equalsIgnoreCase("kit"))
		 {
			 kit.openKitMenu(p);
		 }
		 if(name.equalsIgnoreCase("shop"))
		 {
			 shop.openShopMenu(p);
		 }
		 if(name.equalsIgnoreCase("upgrades"))
		 {
			 upgrades.openUpgradeMenu(p);
		 }
		 if(name.equalsIgnoreCase("armor"))
		 {
			 armor.openInventory(p);
		 }
		 if(name.equalsIgnoreCase("tools"))
		 {
			 tool.openInventory(p);
		 }
		 if(name.equalsIgnoreCase("abilities"))
		 {
			 abilities.openAbilitiesMenu(p);
		 }
		 if(name.equalsIgnoreCase("weapon"))
		 {
			 weapon.openInventory(p, Main.kitmanager.getKit(p).getName(false));
		 }
	 }
	 
}
