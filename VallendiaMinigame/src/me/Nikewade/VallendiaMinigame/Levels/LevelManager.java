package me.Nikewade.VallendiaMinigame.Levels;

import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class LevelManager {
    private VallendiaMinigame Main;
    
	public LevelManager(VallendiaMinigame Main) {
        this.Main = Main; 
	}




	public int getLevel (Player p){
		return Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Level");
	}
	
	public void setLevel (Player p, int amount)
	{
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Level", amount);
	}
	
	public void addLevel (Player p, int amount)
	{
		Main.playerdatamanager.addData(p.getUniqueId(), "Level", amount);
	}
	
	public void subtractLevel (Player p, int amount)
	{
		Main.playerdatamanager.subtractData(p.getUniqueId(), "Level", amount);
	}
	
	public void resetLevel (Player p)
	{
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Level", 1);
	}
	
	public void levelUp (Player p)
	{
		
	}
	
	
	
}
