package me.Nikewade.VallendiaMinigame.Shop;

import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class PointsManager {
	VallendiaMinigame Main;
	
	public PointsManager(VallendiaMinigame Main)
	{
		this.Main = Main;
	}
	
	public void setPoints(Player p, int points)
	{
		if(points < 0)
		{
			Language.sendDefaultMessage(p, "Could not set your essence to a negative.");
			return;
		}
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", points);
	}

	public void addPoints(Player p, int add)
	{
		if(add < 0)
		{
			Language.sendDefaultMessage(p, "Could not set your essence to a negative.");
			return;
		}
		Main.playerdatamanager.addData(p.getUniqueId(), "Points", add);
	}
	
	public void subtractPoints(Player p, int subtract)
	{
		if(this.getPoints(p) - subtract < 0)
		{
			this.setPoints(p, 0);
			Language.sendDefaultMessage(p, "You are taking damage from negative essence!");
			p.damage(1);
			return;
		}
		Main.playerdatamanager.subtractData(p.getUniqueId(), "Points", subtract);
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
