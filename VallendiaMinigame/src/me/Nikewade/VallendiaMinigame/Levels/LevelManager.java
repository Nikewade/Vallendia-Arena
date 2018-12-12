package me.Nikewade.VallendiaMinigame.Levels;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

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
		if(amount > 20)
		{
			return;
		}
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Level", amount);
		this.updateLevelBar(p);
        p.setExp(0);
	}
	
	public void addLevel (Player p, int amount)
	{
		if(this.getLevel(p) == 20)
		{
			return;
		}
		Main.playerdatamanager.addData(p.getUniqueId(), "Level", amount);
		this.updateLevelBar(p);
        p.setExp(0);
        p.sendTitle(Utils.Colorate("&b&lLevel up!"), Utils.Colorate("&b&llevel " + this.getLevel(p)), 20, 40, 40);
        p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1, 1);
        p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_TWINKLE, 1, 1);
        p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, 1, 1);
	}
	
	public void subtractLevel (Player p, int amount)
	{
		if(this.getLevel(p) - amount <= 0)
		{
			return;
		}
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
		if(this.getLevel(p) == 20)
		{
			return;
		}
		Main.playerdatamanager.addData(p.getUniqueId(), "Exp", amount);
		this.levelUpCheck(p);
		this.updateExpBar(p);
	}
	
	public void subtractExp (Player p, int amount)
	{
		if(this.getLevel(p) == 1 && this.getExp(p) - amount <= 0)
		{
			this.resetLevel(p);
			this.resetExp(p);
			return;
		}
		
		Main.playerdatamanager.subtractData(p.getUniqueId(), "Exp", amount);
		this.levelDownCheck(p);
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
		
		
		if(this.getExp(p) > totalExp)
		{
			int leftOverExp = this.getExp(p) - totalExp;
			this.addLevel(p, 1);
			this.resetExp(p);
			Main.playerdatamanager.addData(p.getUniqueId(), "Exp", leftOverExp);
			this.levelUpCheck(p);
			return;
		}
		
		if(this.getExp(p) == totalExp)
		{
			this.addLevel(p, 1);
			this.resetExp(p);
		}
		
	}
	
	
	
	public void levelDownCheck(Player p) {
		
		if(this.getLevel(p) == 1)
		{
			return;
		}
		
		if(this.getExp(p) < 0)
		{
			this.subtractLevel(p, 1);
			int level = this.getLevel(p) - 1;
			int currentExp = ((this.getTotalExp(Integer.toString(level)) + this.getExp(p) * -1 ));
			this.resetExp(p);
			this.addEXP(p, currentExp);
			return;
		}
		
	}
	
	
	public void updateExpBar(Player p)
	{
		int level = Main.levelmanager.getLevel(p);
		float totalExp = this.getTotalExp(Integer.toString(level));
		float percent = (this.getExp(p) / totalExp);
		if(!(percent >= 0) || !(percent <= 1.0))
		{
			return;
		}
		p.setExp(percent);
	}
	
	
	
	
}
