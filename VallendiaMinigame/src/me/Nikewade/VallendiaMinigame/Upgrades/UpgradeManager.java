package me.Nikewade.VallendiaMinigame.Upgrades;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.EquipBowAbility;
import me.Nikewade.VallendiaMinigame.Graphics.ScoreboardHandler;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class UpgradeManager {
	VallendiaMinigame Main;
	ArmorUpgrade armor;
	HealthUpgrade health;
	SpeedUpgrade speed;
	WeaponUpgrade weapon;
	ToolUpgrade tool;
	RegenUpgrade regeneration;
    public HashMap<String, Upgrade> upgrades = new HashMap<String, Upgrade>();
	
	 public UpgradeManager(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	    this.armor = new ArmorUpgrade(Main);
	    this.health = new HealthUpgrade();
	    this.speed = new SpeedUpgrade();
	    this.weapon = new WeaponUpgrade(Main);
	    this.tool = new ToolUpgrade(Main);
	    this.regeneration = new RegenUpgrade(Main);
	    
	    upgrades.put("armor", armor);
	    upgrades.put("health", health);
	    upgrades.put("speed", speed);
	    upgrades.put("weapon", weapon);
	    upgrades.put("tools", tool);
	    upgrades.put("regeneration", regeneration);
	  }
	
	public void addUpgrade(Player p, String upgrade, int amount, String enchant)
	{
		Main.playerdatamanager.addData(p.getUniqueId(), "Upgrades." + upgrade.toLowerCase(), amount);
		
		
		if(upgrade.equalsIgnoreCase("health"))
		{
			health.upgrade(p);
	        Main.levelmanager.addEXP(p, Main.getConfig().getInt("upgrades." + upgrade.toLowerCase() + ".exp"));
		}
		
		if(upgrade.equalsIgnoreCase("regeneration"))
		{
			regeneration.upgrade(p);
	        Main.levelmanager.addEXP(p, Main.getConfig().getInt("upgrades." + upgrade.toLowerCase() + ".exp"));
		}
		
		if(upgrade.equalsIgnoreCase("speed"))
		{
			speed.upgrade(p);
	        Main.levelmanager.addEXP(p, Main.getConfig().getInt("upgrades." + upgrade.toLowerCase() + ".exp"));
		}
		
		
		if(upgrade.equalsIgnoreCase("armor"))
		{
			armor.upgrade(p, enchant);
			Main.playerdatamanager.addData(p.getUniqueId(), "Upgrades.ArmorEnchants." + enchant, amount);
	        Main.levelmanager.addEXP(p, Main.getConfig().getInt("upgrades.armor." + enchant + ".exp"));
		}
		
		
		if(upgrade.equalsIgnoreCase("weapon"))
		{
			weapon.upgrade(p, enchant);
			Main.playerdatamanager.addData(p.getUniqueId(), "Upgrades.WeaponEnchants." + enchant, amount);
	        Main.levelmanager.addEXP(p, Main.getConfig().getInt("upgrades.weapon." + enchant + ".exp"));
		}
		
		if(upgrade.equalsIgnoreCase("tools"))
		{
			tool.upgrade(p, enchant);
			Main.playerdatamanager.addData(p.getUniqueId(), "Upgrades.ToolEnchants." + enchant, amount);
	        Main.levelmanager.addEXP(p, Main.getConfig().getInt("upgrades.tools." + enchant + ".exp"));
		}
		
		
	}
	
	public void resetUpgrades(Player p)
	{
		p.setHealth(20);
		p.setMaxHealth(20);
		ScoreboardHandler.updateHealth(p, 0, 0);
		p.setWalkSpeed((float) 0.2);
		armor.resetArmor(p);
		weapon.resetWeapon(p);
		tool.resetTool(p);
		regeneration.resetRegen(p);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.health", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.speed", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.regeneration", 0);
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
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.tools", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.ToolEnchants.fortune", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.ToolEnchants.efficiency", 0);
	}
	
	public void resetUpgradesOnDeath(Player p)
	{
		p.setMaxHealth(20);
		p.setWalkSpeed((float) 0.2);
		armor.resetArmor(p);
		weapon.resetWeapon(p);
		tool.resetTool(p);
		regeneration.resetRegen(p);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.health", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.speed", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.regeneration", 0);
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
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.tools", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.ToolEnchants.fortune", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Upgrades.ToolEnchants.efficiency", 0);
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
		if(upgrade.equalsIgnoreCase("tools"))
		{
			amount = Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Upgrades.ToolEnchants." + enchant);	
		}
		return amount;
	}
	
	public int getUpgradeTotal(Player p)
	{
		UUID uuid = p.getUniqueId();
		int total = 0;
		
		total = total + Main.playerdatamanager.getPlayerIntData(uuid, "Upgrades.health");
		total = total + Main.playerdatamanager.getPlayerIntData(uuid, "Upgrades.regeneration");
		total = total + Main.playerdatamanager.getPlayerIntData(uuid, "Upgrades.speed");
		total = total + Main.playerdatamanager.getPlayerIntData(uuid, "Upgrades.armor");
		total = total + Main.playerdatamanager.getPlayerIntData(uuid, "Upgrades.weapon");
		total = total + Main.playerdatamanager.getPlayerIntData(uuid, "Upgrades.tools");
		
		return total;
	}
	
	public int getPrice(Player p, String upgrade, String enchant)
	{
		int price = upgrades.get(upgrade.toLowerCase()).getPrice(enchant);
		int numUpgrades = this.getUpgradeAmount(p, upgrade);
		double multiplier = upgrades.get(upgrade.toLowerCase()).getMultiplier(enchant);
		double multiplier2 = upgrades.get(upgrade.toLowerCase()).getMultiplier2(enchant); 
		double discount = (this.getDiscount(p, upgrade, enchant) * 0.01);
		return  (int) ((int) ((price + (numUpgrades * multiplier2)) +  ((price * 0.1) *(Math.pow(multiplier, numUpgrades)))) * (1 - discount));
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
	
	
	public int getMaxUpgrade(String upgrade, String enchant)
	{
		if(enchant != null)
		{
			return Main.getConfig().getInt("upgrades." + upgrade.toLowerCase() + "." + enchant.toLowerCase() + ".max");
		}
		return Main.getConfig().getInt("upgrades." + upgrade.toLowerCase() + ".max");
	}
	
	public void buyUpgrade(Player p , String upgrade, String enchant, int amount)
	{
		int points = Main.shopmanager.getPoints(p);
		int price = this.getPrice(p, upgrade, enchant) * amount;
		int upgradeamount = this.getUpgradeAmount(p, upgrade);
		
		
		if(EquipBowAbility.enabled.contains(p) && upgrade.equalsIgnoreCase("weapon"))
		{
	        p.sendTitle(Utils.Colorate("&4&lX"), Utils.Colorate("&4&lMelee weapon not equiped!"), 20, 40, 40);
	        p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 1, 1);
	        return;
		}
		if(upgradeamount + amount > this.getMaxUpgrade(upgrade , enchant))
		{
	        p.sendTitle(Utils.Colorate("&4&lX"), Utils.Colorate("&4&lMax upgrade!"), 20, 40, 40);
	        p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 1, 1);
	        return;
		}
		
		if(points >= price)
		{
			Main.shopmanager.subtractPoints(p, price);
			Main.playerdatamanager.addData(p.getUniqueId(), "PointsSpent", price);
	        p.sendTitle(Utils.Colorate("&3&l" + upgrade), Utils.Colorate("&3&llevel " + (upgradeamount + amount)), 20, 40, 40);
	        p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 2, 0);
			this.addUpgrade(p, upgrade, amount, enchant);
		}else
		{
	        p.sendTitle(Utils.Colorate("&4&lX"), Utils.Colorate("&4&lToo expensive!"), 20, 40, 40);
	        p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 1, 1);
		}
	}
	
}
