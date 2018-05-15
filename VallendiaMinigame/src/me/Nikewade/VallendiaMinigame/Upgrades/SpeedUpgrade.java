package me.Nikewade.VallendiaMinigame.Upgrades;

import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;

public class SpeedUpgrade implements Upgrade{

	public void upgrade(Player p)
	{
		Float speed = p.getWalkSpeed();
		if(speed >= 0.8) return;
		p.setWalkSpeed((float) (speed + 0.01));
	}
	
	public int getPrice()
	{
		return VallendiaMinigame.getInstance().getConfig().getInt("upgrades." + "speed." + "price");
	}
	
}
