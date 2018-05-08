package me.Nikewade.VallendiaMinigame.Interface;

import java.awt.List;

import org.bukkit.entity.Player;

public interface Kit {
	
	public String getName(Boolean colored);
	
	public String getDescription();
	
	public List getAbilities();
	
	public int getupgradeDiscount(String upgrade);
}
