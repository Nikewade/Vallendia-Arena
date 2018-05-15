package me.Nikewade.VallendiaMinigame.Upgrades;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class UpgradeManager {
	VallendiaMinigame Main;
	ArmorUpgrade armor;
	HealthUpgrade health;
	SpeedUpgrade speed;
	WeaponUpgrade weapon;
    public HashMap<String, Upgrade> upgrades = new HashMap<String, Upgrade>();
	
	 public UpgradeManager(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	    this.armor = new ArmorUpgrade();
	    this.health = new HealthUpgrade();
	    this.speed = new SpeedUpgrade();
	    this.weapon = new WeaponUpgrade();
	    
	    upgrades.put("armor", armor);
	    upgrades.put("health", health);
	    upgrades.put("speed", speed);
	    upgrades.put("weapon", weapon);
	  }
	
	public void addUpgrade(Player p, String upgrade, int amount)
	{
		Main.playerdatamanager.addData(p.getUniqueId(), "Upgrades." + upgrade, amount);
		
		if(upgrade.equalsIgnoreCase("health"))
		{
			health.upgrade(p);
		}
		if(upgrade.equalsIgnoreCase("speed"))
		{
			speed.upgrade(p);
		}
		if(upgrade.equalsIgnoreCase("armor"))
		{
			armor.upgrade(p);
		}
		if(upgrade.equalsIgnoreCase("weapon"))
		{
			weapon.upgrade(p);
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
	
	public int getPrice(Player p, String upgrade)
	{
		int price = upgrades.get(upgrade.toLowerCase()).getPrice();
		int numUpgrades = this.getUpgradeAmount(p, upgrade);
		double multiplier = Main.getConfig().getDouble("upgrades." + upgrade.toLowerCase() + ".multiplier");
		double discount = (this.getDiscount(p, upgrade) * 0.01);
		return (int) (price *(Math.pow(multiplier, numUpgrades) * (1 - discount)));
	}
	
	public int getDiscount(Player p, String upgrade)
	{
        String path = "kits." + Main.kitmanager.getKit(p).getName(false) + ".";
		int discount = Main.getConfig().getInt(path + "discounts." + upgrade.toLowerCase());
		return discount;
	}
	
	public void buyUpgrade(Player p , String upgrade)
	{
		int points = Main.shopmanager.getPoints(p);
		int price = this.getPrice(p, upgrade);
		int upgradeamount = this.getUpgradeAmount(p, upgrade);
		if(points >= price)
		{
			this.addUpgrade(p, upgrade, 1);
			Main.shopmanager.subtractPoints(p, price);
	        p.sendTitle(Utils.Colorate("&b&l" + upgrade), Utils.Colorate("&b&llevel " + (upgradeamount + 1)), 20, 40, 40);
	        p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 2, 0);
		}else
		{
	        p.sendTitle(Utils.Colorate("&4&lX"), Utils.Colorate("&4&lToo expensive!"), 20, 40, 40);
	        p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 1, 1);
		}
	}
	
}
