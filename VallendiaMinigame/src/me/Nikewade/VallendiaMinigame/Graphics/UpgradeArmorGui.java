package me.Nikewade.VallendiaMinigame.Graphics;

import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Upgrades.UpgradeManager;

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
