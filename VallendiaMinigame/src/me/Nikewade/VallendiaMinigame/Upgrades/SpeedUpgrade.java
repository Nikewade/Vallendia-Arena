package me.Nikewade.VallendiaMinigame.Upgrades;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;

public class SpeedUpgrade implements Upgrade{

	public void upgrade(Player p)
	{
		Float speed = p.getWalkSpeed();
		if(speed >= 0.8) return;
		p.setWalkSpeed((float) (speed + 0.01));
		ItemStack helmet = p.getInventory().getHelmet();
	}
	
	public int getPrice(String enchant)
	{
		return VallendiaMinigame.getInstance().getConfig().getInt("upgrades." + "speed." + "price");
	}

	@Override
	public double getMultiplier(String enchant) {
		// TODO Auto-generated method stub
		return VallendiaMinigame.getInstance().getConfig().getDouble("upgrades." + "speed." + "multiplier");
	}
	
}
