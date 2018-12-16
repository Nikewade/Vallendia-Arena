package me.Nikewade.VallendiaMinigame.Shop;

import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;

public class PointsManager {
	VallendiaMinigame Main;
	
	public PointsManager(VallendiaMinigame Main)
	{
		this.Main = Main;
	}
	
	public void setPoints(Player p, int points)
	{
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", points);
	}

	public void addPoints(Player p, int add)
	{
		Main.playerdatamanager.addData(p.getUniqueId(), "Points", add);
	}
	
	public void subtractPoints(Player p, int subtract)
	{
		Main.playerdatamanager.subtractData(p.getUniqueId(), "Points", subtract);
		Main.playerdatamanager.addData(p.getUniqueId(), "PointsSpent", subtract);
	}
	
	public int getPoints(Player p)
	{
		return Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Points");
	}
	
	public int getPointsSpent(Player p)
	{
		return Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "PointsSpent");
	}
	
}
