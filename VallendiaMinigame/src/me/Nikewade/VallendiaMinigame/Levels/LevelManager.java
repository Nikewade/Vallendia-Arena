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
		this.updateLevelBar(p);
        p.setExp(0);
	}
	
	public void addLevel (Player p, int amount)
	{
		Main.playerdatamanager.addData(p.getUniqueId(), "Level", amount);
		this.updateLevelBar(p);
        p.setExp(0);
	}
	
	public void subtractLevel (Player p, int amount)
	{
		Main.playerdatamanager.subtractData(p.getUniqueId(), "Level", amount);
		this.updateLevelBar(p);
        p.setExp(0);
	}
	
	public void resetLevel (Player p)
	{
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Level", 1);
        p.setLevel(1);
	}
	
	public void updateLevelBar (Player p)
	{
		p.setLevel(this.getLevel(p));
	}
	
	
	//EXP
	public int getExp (Player p){
		return Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Exp");
	}
	
	public void setExp (Player p, int amount)
	{
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Exp", amount);
		this.levelUpCheck(p);
		this.updateExpBar(p);
	}
	
	public void addEXP (Player p, int amount)
	{
		Main.playerdatamanager.addData(p.getUniqueId(), "Exp", amount);
		this.levelUpCheck(p);
		this.updateExpBar(p);
	}
	
	public void subtractExp (Player p, int amount)
	{
		Main.playerdatamanager.subtractData(p.getUniqueId(), "Exp", amount);
		this.updateExpBar(p);
	}
	
	public int getTotalExp (String level)
	{
		return Main.getConfig().getInt("Levels." + level);
	}
	
	public void resetExp (Player p)
	{
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Exp", 0);
		this.updateExpBar(p);
	}
	
	public void levelUpCheck(Player p) {
		int level = this.getLevel(p);
		int totalExp = this.getTotalExp(Integer.toString(level));
		
		if(level == 20)
		{
			return;
		}
		
		if(this.getExp(p) >= totalExp)
		{
			this.addLevel(p, 1);
			this.resetExp(p);
			p.sendMessage("YOU LEVELD UP!");
		}

	}
	
	public void updateExpBar(Player p)
	{
		int level = Main.levelmanager.getLevel(p);
		float totalExp = this.getTotalExp(Integer.toString(level));
		float percent = (this.getExp(p) / totalExp);
		p.sendMessage("" + percent + " lvl "  + level + " totalexp "  + totalExp);
		p.setExp(percent);
	}
	
	
	
	
}
