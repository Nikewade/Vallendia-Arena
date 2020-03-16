package me.Nikewade.VallendiaMinigame.Upgrades;

import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Graphics.ScoreboardHandler;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;

public class HealthUpgrade implements Upgrade{

	public void upgrade(Player p)
	{
		Double health = p.getMaxHealth();
		p.setMaxHealth(health + 2);
		ScoreboardHandler.updateHealth(p, 0, 0);
	}
	
	public int getPrice(String enchant)
	{
		return VallendiaMinigame.getInstance().getConfig().getInt("upgrades." + "health." + "price");
	}

	@Override
	public double getMultiplier(String enchant) {
		// TODO Auto-generated method stub
		return VallendiaMinigame.getInstance().getConfig().getDouble("upgrades." + "health." + "multiplier");
	}
	
	@Override
	public double getMultiplier2(String enchant) {
		// TODO Auto-generated method stub
		return VallendiaMinigame.getInstance().getConfig().getDouble("upgrades." + "health." + ".multiplier2");
	}

	
}
