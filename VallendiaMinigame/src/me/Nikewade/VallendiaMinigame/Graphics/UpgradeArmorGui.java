package me.Nikewade.VallendiaMinigame.Graphics;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Kits.KitManager;
import me.Nikewade.VallendiaMinigame.Upgrades.UpgradeManager;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;

public class UpgradeArmorGui {
	VallendiaMinigame Main;
	UpgradeManager um;
	UpgradeGui upgrades;
	
	public UpgradeArmorGui(VallendiaMinigame Main)
	{
	    this.Main = Main;
	    this.um = Main.upgrademanager;
	    this.upgrades = new UpgradeGui(Main); //may cause problems because it was already made in upgradegui class
	}

	public void openInventory(Player p)
	{
		  AdvInventory armorInv = new AdvInventory(Utils.Colorate("&8&lArmor Upgrade"), 27, Utils.placeholder((byte) 7, " "));
		  String path = "Upgrades.ArmorEnchants.";
		  armorInv.setItem(new ItemStack(Material.ENCHANTED_BOOK), Utils.Colorate("&7&lProtection"), 10, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player p = (Player) e.getWhoClicked();
			    	upgrades.openUpgradeYesNoMenu(p, "Armor", "prot");
			    }
			}, Utils.Colorate("&8Level &7" + um.getUpgradeEnchantAmount(p, "armor", "prot")));
		  
		  armorInv.setItem(new ItemStack(Material.ENCHANTED_BOOK), Utils.Colorate("&7&lProjectile Protection"), 11, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player p = (Player) e.getWhoClicked();
			    	upgrades.openUpgradeYesNoMenu(p, "Armor", "projprot");
			    }
			}, Utils.Colorate("&8Level &7" + um.getUpgradeEnchantAmount(p, "armor", "projprot")));
		  
		  armorInv.setItem(new ItemStack(Material.ENCHANTED_BOOK), Utils.Colorate("&7&lFire Protection"), 12, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player p = (Player) e.getWhoClicked();
			    	upgrades.openUpgradeYesNoMenu(p, "Armor", "fireprot");
			    }
			}, Utils.Colorate("&8Level &7" + um.getUpgradeEnchantAmount(p, "armor", "fireprot")));
		  
		  armorInv.setItem(new ItemStack(Material.ENCHANTED_BOOK), Utils.Colorate("&7&lBlast Protection"), 14, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player p = (Player) e.getWhoClicked();
			    	upgrades.openUpgradeYesNoMenu(p, "Armor", "blastprot");
			    }
			}, Utils.Colorate("&8Level &7" + um.getUpgradeEnchantAmount(p, "armor", "blastprot")));
		  
		  armorInv.setItem(new ItemStack(Material.ENCHANTED_BOOK), Utils.Colorate("&7&lThorns"), 15, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player p = (Player) e.getWhoClicked();
			    	upgrades.openUpgradeYesNoMenu(p, "Armor", "thorns");
			    }
			}, Utils.Colorate("&8Level &7" + um.getUpgradeEnchantAmount(p, "armor", "thorns")));
		  
		  armorInv.setItem(new ItemStack(Material.ENCHANTED_BOOK), Utils.Colorate("&7&lFeather Falling"), 16, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player p = (Player) e.getWhoClicked();
			    	upgrades.openUpgradeYesNoMenu(p, "Armor", "featherfall");
			    }
			}, Utils.Colorate("&8Level &7" + um.getUpgradeEnchantAmount(p, "armor", "featherfall")));
		  
		  armorInv.openInventory(p);
	}
	
	
	
}
