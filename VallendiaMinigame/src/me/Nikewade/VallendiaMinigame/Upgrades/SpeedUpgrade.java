package me.Nikewade.VallendiaMinigame.Upgrades;

import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;

public class SpeedUpgrade implements Upgrade{
	VallendiaMinigame Main;
	
	 public SpeedUpgrade(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	  }

	public void upgrade(Player p)
	{
		Float speed = p.getWalkSpeed();
		p.setWalkSpeed((float) (speed + 0.1));
	}
	
	public int getPrice()
	{
		return 300;
	}
	
}
