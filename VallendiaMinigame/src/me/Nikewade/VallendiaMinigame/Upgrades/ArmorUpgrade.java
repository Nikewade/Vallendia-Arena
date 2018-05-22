package me.Nikewade.VallendiaMinigame.Upgrades;

import java.util.Map.Entry;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class ArmorUpgrade implements Upgrade{
	VallendiaMinigame Main;
	
	 public ArmorUpgrade(VallendiaMinigame Main)
	  {
		 this.Main = Main;
	  }

	public void upgrade(Player p, String enchant)
	{
		ItemStack helmet = p.getInventory().getHelmet();
		ItemStack chestplate = p.getInventory().getChestplate();
		ItemStack leggings = p.getInventory().getLeggings();
		ItemStack boots = p.getInventory().getBoots();
		
		Enchantment enchantment = Enchantment.PROTECTION_ENVIRONMENTAL;
		int enchantAmount = Main.upgrademanager.getUpgradeEnchantAmount(p, "armor", enchant);
		
		if(enchant == "prot") enchantment = Enchantment.PROTECTION_ENVIRONMENTAL;	
		if(enchant == "projprot") enchantment = Enchantment.PROTECTION_PROJECTILE;	
		if(enchant == "fireprot") enchantment = Enchantment.PROTECTION_FIRE;	
		if(enchant == "blastprot") enchantment = Enchantment.PROTECTION_EXPLOSIONS;	
		if(enchant == "thorns") enchantment = Enchantment.THORNS;	
		
		if(enchant == "featherfall")
		{
			enchantment = Enchantment.PROTECTION_FALL;
			boots.addUnsafeEnchantment(enchantment, enchantAmount + 1);
			return;
		}
		
		if(helmet != null)
		{
			helmet.addUnsafeEnchantment(enchantment, enchantAmount + 1);	
		}
		if(chestplate != null)
		{
			chestplate.addUnsafeEnchantment(enchantment, enchantAmount + 1);	
		}
		if(leggings != null)
		{
			leggings.addUnsafeEnchantment(enchantment, enchantAmount + 1);	
		}
		if(boots != null)
		{
			boots.addUnsafeEnchantment(enchantment, enchantAmount + 1);
		}
		
	}
	
	public void resetArmor(Player p)
	{
		ItemStack helmet = p.getInventory().getHelmet();
		ItemStack chestplate = p.getInventory().getChestplate();
		ItemStack leggings = p.getInventory().getLeggings();
		ItemStack boots = p.getInventory().getBoots();

		Utils.removeEnchantments(helmet);
		Utils.removeEnchantments(chestplate);
		Utils.removeEnchantments(leggings);
		Utils.removeEnchantments(boots);
		
	}
	
	public int getPrice(String enchant)
	{
		return VallendiaMinigame.getInstance().getConfig().getInt("upgrades." + "armor." + enchant + ".price");
	}

	@Override
	public double getMultiplier(String enchant) {
		// TODO Auto-generated method stub
		return VallendiaMinigame.getInstance().getConfig().getDouble("upgrades." + "armor." + enchant + ".multiplier");
	}


	
}
