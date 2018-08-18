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
    	upgrades.openUpgradeYesNoMenu(p, "Armor", "prot");
	}
	
	
	
}
