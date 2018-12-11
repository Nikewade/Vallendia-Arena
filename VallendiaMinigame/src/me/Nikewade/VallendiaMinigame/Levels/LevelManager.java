package me.Nikewade.VallendiaMinigame.Levels;

import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class LevelManager {
    private VallendiaMinigame Main;
    
	public LevelManager(VallendiaMinigame Main) {
        this.Main = Main; 
	}



	//LEVEL
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
        p.setLevel(1);
        p.setExp(0);
	}
	
	public void levelUp (Player p)
	{
		
	}
	
	
	//EXP
	public int getExp (Player p){
		return Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Exp");
	}
	
	public void setExp (Player p, int amount)
	{
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Exp", amount);
	}
	
	public void addEXP (Player p, int amount)
	{
		Main.playerdatamanager.addData(p.getUniqueId(), "eXP", amount);
	}
	
	public void subtractExp (Player p, int amount)
	{
		Main.playerdatamanager.subtractData(p.getUniqueId(), "Exp", amount);
	}
	
	
	
}
