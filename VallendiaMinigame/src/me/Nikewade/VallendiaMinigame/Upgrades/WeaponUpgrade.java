package me.Nikewade.VallendiaMinigame.Upgrades;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class WeaponUpgrade implements Upgrade {
	VallendiaMinigame Main;
	
	 public WeaponUpgrade(VallendiaMinigame Main)
	  {
		 this.Main = Main;
	  }

	public void upgrade(Player p, String enchant)
	{
		
		Enchantment meleeEnchantment = null;
		Enchantment rangedEnchantment = null;
		int enchantAmount = Main.upgrademanager.getUpgradeEnchantAmount(p, "weapon", enchant);
		
		if(enchant == "sharpness") meleeEnchantment = Enchantment.DAMAGE_ALL;	
		if(enchant == "fireaspect") meleeEnchantment = Enchantment.FIRE_ASPECT;	
		if(enchant == "knockback") meleeEnchantment = Enchantment.KNOCKBACK;	
		if(enchant == "smite") meleeEnchantment = Enchantment.DAMAGE_UNDEAD;	
		
		if(enchant == "power") rangedEnchantment = Enchantment.ARROW_DAMAGE;
		if(enchant == "punch") rangedEnchantment = Enchantment.ARROW_KNOCKBACK;
		if(enchant == "flame") rangedEnchantment = Enchantment.ARROW_FIRE;
		if(enchant == "infinity") rangedEnchantment = Enchantment.ARROW_INFINITE;
		
		for(ItemStack item : p.getInventory().getContents())
		{
		if(item != null)
		{
			ItemStack melee = item;
			ItemStack ranged = item;
			
			if(meleeEnchantment != null)
			{
				if( item.getType() == Material.IRON_SWORD || item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.STONE_SWORD || item.getType() == Material.WOOD_SWORD || item.getType() == Material.STICK)
				{
					melee.addUnsafeEnchantment(meleeEnchantment, enchantAmount + 1);	
				}
			}
			
			if(rangedEnchantment != null)
			{
				if(item.getType() == Material.BOW)
				{
					ranged.addUnsafeEnchantment(rangedEnchantment, enchantAmount + 1);
				}		
			}	
		}
		}
	}
	
	
	public void resetWeapon(Player p)
	{
		for(ItemStack item : p.getInventory().getContents())
		{
			if(item != null && item.getType() != Material.AIR)
			{
				Utils.removeEnchantments(item);
			}
		}
	}
	
	public int getPrice(String enchant)
	{
		return VallendiaMinigame.getInstance().getConfig().getInt("upgrades." + "weapon." + enchant + ".price");
	}

	@Override
	public double getMultiplier(String enchant) {
		// TODO Auto-generated method stub
		return VallendiaMinigame.getInstance().getConfig().getDouble("upgrades." + "weapon." + enchant + ".multiplier");
	}
	
	
	@Override
	public double getMultiplier2(String enchant) {
		// TODO Auto-generated method stub
		return VallendiaMinigame.getInstance().getConfig().getDouble("upgrades." + "weapon." + enchant + ".multiplier2");
	}
	
}
