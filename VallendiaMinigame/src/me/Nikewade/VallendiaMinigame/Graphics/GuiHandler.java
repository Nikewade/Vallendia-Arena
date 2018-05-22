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
	UpgradeGui upgrades;
	ShopGui shop;
	KitGui kit;
	UpgradeArmorGui armor;
	UpgradeWeaponGui weapon;
	AbilitiesGui abilities;
	
	 public GuiHandler(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	    this.upgrades = new UpgradeGui(Main);
	    this.shop = new ShopGui(Main);
	    this.kit = new KitGui(Main);
	    this.armor = new UpgradeArmorGui(Main);
	    this.weapon = new UpgradeWeaponGui(Main);
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
