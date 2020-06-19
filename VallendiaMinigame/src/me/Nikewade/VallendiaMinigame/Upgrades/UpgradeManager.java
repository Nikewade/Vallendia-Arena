package me.Nikewade.VallendiaMinigame.Upgrades;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.EquipBowAbility;
import me.Nikewade.VallendiaMinigame.Graphics.ScoreboardHandler;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class UpgradeManager {
	VallendiaMinigame Main;
	ArmorUpgrade armor;
	HealthUpgrade health;
	SpeedUpgrade speed;
	WeaponUpgrade weapon;
	ToolUpgrade tool;
	RegenUpgrade regeneration;
	double xpFactor;
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
	    
		double m = Main.getConfig().getDouble("upgrades." + "multi1");
		double n = Main.getConfig().getDouble("upgrades." + "multi2");
		int p = Main.getConfig().getInt("Levels." + "p"); 
		int total20XP = 0;
		
		for(int x = 1; x < 20; x++)
		{
			int xp = Main.getConfig().getInt("Levels." + x); 
			total20XP = total20XP + xp;
			
		}
		
		
		xpFactor = ((0.5*n*Math.pow(total20XP, 2)) + total20XP +(0.1 * Math.pow(m, total20XP)) / Math.log(m)) / p;
		
		
	    
	    
	  }
	
	public void addUpgrade(Player p, String upgrade, int amount, String enchant)
	{
		Main.playerdatamanager.addData(p.getUniqueId(), "Upgrades." + upgrade.toLowerCase(), amount);
		if(enchant == null)
		{
			 double discount = (this.getDiscount(p, upgrade, "") * 0.01);
			 Main.levelmanager.addEXP(p, (int) ((upgrades.get(upgrade.toLowerCase()).getPrice("") * this.xpFactor) *(1 - discount)));
		}else
		{
			double discount = (this.getDiscount(p, upgrade, enchant) * 0.01);
			 Main.levelmanager.addEXP(p, (int) ((upgrades.get(upgrade.toLowerCase()).getPrice(enchant.toLowerCase()) * this.xpFactor) *(1 - discount)));	
		}
		
		if(upgrade.equalsIgnoreCase("health"))
		{
			health.upgrade(p);
		}
		
		if(upgrade.equalsIgnoreCase("regeneration"))
		{
			regeneration.upgrade(p);
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
		
		if(upgrade.equalsIgnoreCase("tools"))
		{
			tool.upgrade(p, enchant);
			Main.playerdatamanager.addData(p.getUniqueId(), "Upgrades.ToolEnchants." + enchant, amount);
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
	
	
	public double currentUpgradeXP(Player p)
	{
		double healthPrice = (upgrades.get("health")).getPrice("");
		double speedPrice = (upgrades.get("speed")).getPrice("");
		double regenerationPrice = (upgrades.get("regeneration")).getPrice("");
		double armorPrice = (upgrades.get("armor")).getPrice("prot");
		double sharpnessPrice = (upgrades.get("weapon")).getPrice("sharpness");
		double fireaspectPrice = (upgrades.get("weapon")).getPrice("fireaspect");
		double knockbackPrice = (upgrades.get("weapon")).getPrice("knockback");
		double smitePrice = (upgrades.get("weapon")).getPrice("smite");
		double powerPrice = (upgrades.get("weapon")).getPrice("power");
		double punchPrice = (upgrades.get("weapon")).getPrice("punch");
		double flamePrice = (upgrades.get("weapon")).getPrice("flame");
		double infinityPrice = (upgrades.get("weapon")).getPrice("infinity");
		double fortunePrice = (upgrades.get("tools")).getPrice("fortune");
		double efficiencyPrice = (upgrades.get("tools")).getPrice("efficiency");
		int health = Main.playerdatamanager.getRawPlayerIntData(p.getUniqueId(), "Upgrades.health");
		int speed =Main.playerdatamanager.getRawPlayerIntData(p.getUniqueId(), "Upgrades.speed");
		int regen = Main.playerdatamanager.getRawPlayerIntData(p.getUniqueId(), "Upgrades.regeneration");
		int armor = Main.playerdatamanager.getRawPlayerIntData(p.getUniqueId(), "Upgrades.ArmorEnchants.prot");
		int sharpness = Main.playerdatamanager.getRawPlayerIntData(p.getUniqueId(), "Upgrades.WeaponEnchants.sharpness");
		int fireaspect = Main.playerdatamanager.getRawPlayerIntData(p.getUniqueId(), "Upgrades.WeaponEnchants.fireaspect");
		int knockback = Main.playerdatamanager.getRawPlayerIntData(p.getUniqueId(), "Upgrades.WeaponEnchants.knockback");
		int smite = Main.playerdatamanager.getRawPlayerIntData(p.getUniqueId(), "Upgrades.WeaponEnchants.smite");
		int power = Main.playerdatamanager.getRawPlayerIntData(p.getUniqueId(), "Upgrades.WeaponEnchants.power");
		int punch = Main.playerdatamanager.getRawPlayerIntData(p.getUniqueId(), "Upgrades.WeaponEnchants.punch");
		int flame = Main.playerdatamanager.getRawPlayerIntData(p.getUniqueId(), "Upgrades.WeaponEnchants.flame");
		int infinity = Main.playerdatamanager.getRawPlayerIntData(p.getUniqueId(), "Upgrades.WeaponEnchants.infinity");
		int fortune = Main.playerdatamanager.getRawPlayerIntData(p.getUniqueId(), "Upgrades.ToolEnchants.fortune");
		int efficiency = Main.playerdatamanager.getRawPlayerIntData(p.getUniqueId(), "Upgrades.ToolEnchants.efficiency");
		
		
		
		return xpFactor * ((healthPrice * health) + (speedPrice * speed) + (regenerationPrice * regen) + (armorPrice * armor)
				+ (sharpnessPrice * sharpness)+ (fireaspectPrice * fireaspect)+ (knockbackPrice * knockback)+ (smitePrice * smite)+ (powerPrice * power)
				+ (punchPrice * punch)+ (flamePrice * flame)+ (infinityPrice * infinity)+ (fortunePrice * fortune)+ (efficiencyPrice * efficiency));
	}
	
	public int getPrice(Player p, String upgrade, String enchant)
	{
		double price = (upgrades.get(upgrade.toLowerCase()).getPrice(enchant));
		int currentUpgradeXP = (int) this.currentUpgradeXP(p);
		double multiplier = VallendiaMinigame.getInstance().getConfig().getDouble("upgrades." + "multi1");
		double multiplier2 = VallendiaMinigame.getInstance().getConfig().getDouble("upgrades." + "multi2");
		double discount = (this.getDiscount(p, upgrade, enchant) * 0.01);
		return  (int) (((price + ((price * currentUpgradeXP) * multiplier2)) +  ((price * 0.1) *(Math.pow(multiplier, currentUpgradeXP)))) * (1 - discount));
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
		if(enchant != null)
		{
			upgradeamount = this.getUpgradeEnchantAmount(p, upgrade, enchant);
		}
		
		
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
