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
	    this.armor = new ArmorUpgrade(Main);
	    this.health = new HealthUpgrade();
	    this.speed = new SpeedUpgrade();
	    this.weapon = new WeaponUpgrade(Main);
	    
	    upgrades.put("armor", armor);
	    upgrades.put("health", health);
	    upgrades.put("speed", speed);
	    upgrades.put("weapon", weapon);
	  }
	
	public void addUpgrade(Player p, String upgrade, int amount, String enchant)
	{
		Main.playerdatamanager.addData(p.getUniqueId(), "Upgrades." + upgrade.toLowerCase(), amount);
		
		
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
			armor.upgrade(p, enchant);
			Main.playerdatamanager.addData(p.getUniqueId(), "Upgrades.ArmorEnchants." + enchant, amount);
		}
		
		
		if(upgrade.equalsIgnoreCase("weapon"))
		{
			weapon.upgrade(p, enchant);
			Main.playerdatamanager.addData(p.getUniqueId(), "Upgrades.WeaponEnchants." + enchant, amount);
		}
		
	}
	
	public void resetUpgrades(Player p)
	{
		p.resetMaxHealth();
		p.setWalkSpeed((float) 0.2);
		armor.resetArmor(p);
		weapon.resetWeapon(p);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.health", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.speed", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.armor", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.ArmorEnchants.prot", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.ArmorEnchants.projprot", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.ArmorEnchants.fireprot", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.ArmorEnchants.blastprot", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.ArmorEnchants.thorns", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.ArmorEnchants.featherfall", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.weapon", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.WeaponEnchants.sharpness", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.WeaponEnchants.fireaspect", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.WeaponEnchants.knockback", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.WeaponEnchants.smite", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.WeaponEnchants.power", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.WeaponEnchants.punch", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.WeaponEnchants.flame", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.WeaponEnchants.infinity", 0);
	}
	
	
	public int getUpgradeAmount(Player p, String upgrade)
	{
		return Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Upgrades." + upgrade.toLowerCase());
	}
	
	public int getUpgradeEnchantAmount(Player p, String upgrade, String enchant)
	{
		int amount = 0;
		if(upgrade.equalsIgnoreCase("armor"))
		{
			amount = Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Upgrades.ArmorEnchants." + enchant);	
		}

		if(upgrade.equalsIgnoreCase("weapon"))
		{
			amount = Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Upgrades.WeaponEnchants." + enchant);	
		}
		return amount;
	}
	
	public int getUpgradeTotal(Player p)
	{
		UUID uuid = p.getUniqueId();
		int total = 0;
		
		total = total + Main.playerdatamanager.getPlayerIntData(uuid, "Upgrades.health");
		total = total + Main.playerdatamanager.getPlayerIntData(uuid, "Upgrades.speed");
		total = total + Main.playerdatamanager.getPlayerIntData(uuid, "Upgrades.armor");
		total = total + Main.playerdatamanager.getPlayerIntData(uuid, "Upgrades.weapon");
		
		return total;
	}
	
	public int getPrice(Player p, String upgrade, String enchant)
	{
		int price = upgrades.get(upgrade.toLowerCase()).getPrice(enchant);
		int numUpgrades = this.getUpgradeAmount(p, upgrade);
		double multiplier = upgrades.get(upgrade.toLowerCase()).getMultiplier(enchant);
		double discount = (this.getDiscount(p, upgrade, enchant) * 0.01);
		return (int) (price *(Math.pow(multiplier, numUpgrades) * (1 - discount)));
	}
	
	public int getDiscount(Player p, String upgrade, String enchant)
	{
		int discount = 0;
        String path = "kits." + Main.kitmanager.getKit(p).getName(false) + ".";
        if(upgrade.equalsIgnoreCase("weapon"))
        {
            if(enchant == "sharpness" || enchant == "fireaspect" || enchant == "knockback" || enchant == "smite")
            {
            	discount = Main.getConfig().getInt(path + "discounts.weapon.melee");
            }	
            if(enchant == "power" || enchant == "flame" || enchant == "punch" || enchant == "infinity")
            {
            	discount = Main.getConfig().getInt(path + "discounts.weapon.ranged");
            }	
        }else discount = Main.getConfig().getInt(path + "discounts." + upgrade.toLowerCase());
		return discount;
	}
	
	public void buyUpgrade(Player p , String upgrade, String enchant, int amount)
	{
		int points = Main.shopmanager.getPoints(p);
		int price = this.getPrice(p, upgrade, enchant) * amount;
		int upgradeamount = this.getUpgradeAmount(p, upgrade);
		if(points >= price)
		{
			this.addUpgrade(p, upgrade, amount, enchant);
			Main.shopmanager.subtractPoints(p, price);
	        p.sendTitle(Utils.Colorate("&b&l" + upgrade), Utils.Colorate("&b&llevel " + (upgradeamount + amount)), 20, 40, 40);
	        p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 2, 0);
		}else
		{
	        p.sendTitle(Utils.Colorate("&4&lX"), Utils.Colorate("&4&lToo expensive!"), 20, 40, 40);
	        p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 1, 1);
		}
	}
	
}
