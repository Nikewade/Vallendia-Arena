package me.Nikewade.VallendiaMinigame.Graphics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Upgrades.UpgradeManager;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class UpgradeToolGui {
	VallendiaMinigame Main;
	UpgradeManager um;
	UpgradeGui upgrades;
	String meleeEnchants;
	String rangedEnchants;
	AdvInventory inv;
	
	public UpgradeToolGui(VallendiaMinigame Main)
	{
	    this.Main = Main;
	    this.um = Main.upgrademanager;
	    this.upgrades = new UpgradeGui(Main); //may cause problems because it was already made in upgradegui class
	}

	
	
	public void openInventory(Player p)
	{
		  AdvInventory invMelee = new AdvInventory(Utils.Colorate("&2&lTool Upgrade"), 27, Utils.placeholder((byte) 7, " "));
		  
		  
		  invMelee.setItem(new ItemStack(Material.ENCHANTED_BOOK), Utils.Colorate("&2&lFortune"), 11, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	upgrades.openUpgradeYesNoMenu(ep, "Tools", "fortune");
			    }
			}, Utils.Colorate("&8Level &2" + um.getUpgradeEnchantAmount(p, "tools", "fortune")));
		  
		  
		  
		  
		  invMelee.setItem(new ItemStack(Material.ENCHANTED_BOOK), Utils.Colorate("&2&lEfficiency"), 15, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	upgrades.openUpgradeYesNoMenu(ep, "Tools", "efficiency");
			    }
			}, Utils.Colorate("&8Level &2" + um.getUpgradeEnchantAmount(p, "tools", "efficiency")));

		  
		  invMelee.openInventory(p);

	}
}
