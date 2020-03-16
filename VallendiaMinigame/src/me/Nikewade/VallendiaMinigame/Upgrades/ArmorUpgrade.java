package me.Nikewade.VallendiaMinigame.Upgrades;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
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
		
		
		//Every 5 upgrades it moves to the next piece.. like a loading bar of armor idk... max upgrade to prot will be 20
		
		
		if(Main.kitmanager.getKit(p).getName(false).equalsIgnoreCase("Mage") )
		{
			if(enchantAmount >= 10)
			{
		        if(chestplate != null)
		        {
		            chestplate.addUnsafeEnchantment(enchantment, enchantAmount - 9);
		            return;
		        }
			}
	        
	        if(enchantAmount >= 0)
	        {
	            if(leggings != null)
	            {
	                leggings.addUnsafeEnchantment(enchantment, enchantAmount + 1);
	                return;
	            }	
	        }
		}else
		{
			if(enchantAmount >= 15)
			{
				if(helmet != null)
		        {
		            helmet.addUnsafeEnchantment(enchantment, enchantAmount - 14);
		            return;
		        }	
			}
			
			
			if(enchantAmount >= 10)
			{
		        if(chestplate != null)
		        {
		            chestplate.addUnsafeEnchantment(enchantment, enchantAmount - 9);
		            return;
		        }
			}
	        
	        if(enchantAmount >= 5)
	        {
	            if(leggings != null)
	            {
	                leggings.addUnsafeEnchantment(enchantment, enchantAmount - 4);
	                return;
	            }	
	        }
	        
	        
	        if(enchantAmount >= 0)
	        {
	            if(boots != null)
	            {
	                boots.addUnsafeEnchantment(enchantment, enchantAmount + 1);
	                return;
	            }	
	        }	
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
	
	@Override
	public double getMultiplier2(String enchant) {
		// TODO Auto-generated method stub
		return VallendiaMinigame.getInstance().getConfig().getDouble("upgrades." + "armor." + enchant + ".multiplier2");
	}


	
}
