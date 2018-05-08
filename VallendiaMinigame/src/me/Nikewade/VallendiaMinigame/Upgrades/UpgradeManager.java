package me.Nikewade.VallendiaMinigame.Upgrades;

import java.util.UUID;

import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;

public class UpgradeManager {
	VallendiaMinigame Main;
	
	 public UpgradeManager(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	  }
	
	public void addUpgrade(Player p, String upgrade, int amount)
	{
		Main.playerdatamanager.addData(p.getUniqueId(), "Upgrades." + upgrade, amount);
		
		if(upgrade.equalsIgnoreCase("health"))
		{
			Main.healthUpgrade.upgrade(p);
		}
		if(upgrade.equalsIgnoreCase("speed"))
		{
			Main.speedUpgrade.upgrade(p);
		}
		if(upgrade.equalsIgnoreCase("armor"))
		{
			Main.armorUpgrade.upgrade(p);
		}
		if(upgrade.equalsIgnoreCase("weapon"))
		{
			Main.weaponUpgrade.upgrade(p);
		}
		
	}
	
	public void resetUpgrades(Player p)
	{
		p.resetMaxHealth();
		p.setWalkSpeed((float) 0.2);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.Health", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.Speed", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.Armor", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.Weapon", 0);
	}
	
	public int getUpgradeAmount(Player p, String upgrade)
	{
		return Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Upgrades." + upgrade);
	}
	
	public int getUpgradeTotal(Player p)
	{
		UUID uuid = p.getUniqueId();
		int total = 0;
		
		total = total + Main.playerdatamanager.getPlayerIntData(uuid, "Upgrades.Health");
		total = total + Main.playerdatamanager.getPlayerIntData(uuid, "Upgrades.Speed");
		total = total + Main.playerdatamanager.getPlayerIntData(uuid, "Upgrades.Armor");
		total = total + Main.playerdatamanager.getPlayerIntData(uuid, "Upgrades.Weapon");
		
		return total;
	}
	
	public int getPrice(Player p, Upgrade upgrade)
	{
		return 0;
	}
	
}
