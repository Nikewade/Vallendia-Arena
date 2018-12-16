package me.Nikewade.VallendiaMinigame.Upgrades;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class ToolUpgrade implements Upgrade{
	VallendiaMinigame Main;

	
	public ToolUpgrade(VallendiaMinigame Main)
	{
	    this.Main = Main;
	}
	
	
	public int getPrice(String enchant)
	{
		return VallendiaMinigame.getInstance().getConfig().getInt("upgrades." + "tool." + enchant + ".price");
	}

	@Override
	public double getMultiplier(String enchant) {
		// TODO Auto-generated method stub
		return VallendiaMinigame.getInstance().getConfig().getDouble("upgrades." + "tool." + enchant + ".multiplier");
	}
	
	
	@Override
	public double getMultiplier2(String enchant) {
		// TODO Auto-generated method stub
		return VallendiaMinigame.getInstance().getConfig().getDouble("upgrades." + "tool." + enchant + ".multiplier2");
	}


	public void upgrade(Player p, String enchant) {
		int enchantAmount = Main.upgrademanager.getUpgradeEnchantAmount(p, "tools", enchant);
		Enchantment enchantment = Enchantment.PROTECTION_ENVIRONMENTAL;
		
		if(enchant == "fortune") enchantment = Enchantment.LOOT_BONUS_BLOCKS;	
		if(enchant == "efficiency") enchantment = Enchantment.DIG_SPEED;	
		
		for(ItemStack item : p.getInventory().getContents())
		{
		if(item != null)
		{
				if( item.getType() == Material.DIAMOND_PICKAXE)
				{
					item.addUnsafeEnchantment(enchantment, enchantAmount + 1);	
				}
			
		}
		}
		
	}


	public void resetTool(Player p) {
		for(ItemStack item : p.getInventory().getContents())
		{
			if(item != null && item.getType() != Material.AIR)
			{
				Utils.removeEnchantments(item);
			}
		}
		
	}

}
