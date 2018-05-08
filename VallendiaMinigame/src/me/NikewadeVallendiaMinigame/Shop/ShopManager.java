package me.NikewadeVallendiaMinigame.Shop;

import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;

public class ShopManager {
	VallendiaMinigame Main;
	
	public ShopManager(VallendiaMinigame Main)
	{
		this.Main = Main;
	}

	public void addtPoints(Player p, int add)
	{
		Main.playerdatamanager.addData(p.getUniqueId(), "Points", add);
	}
	
	public void subtractPoints(Player p, int subtract)
	{
		Main.playerdatamanager.subtractData(p.getUniqueId(), "Points", subtract);
	}
	
	public int getPoints(Player p)
	{
		return Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Points");
	}
}
