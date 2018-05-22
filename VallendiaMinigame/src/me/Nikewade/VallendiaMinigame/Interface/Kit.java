package me.Nikewade.VallendiaMinigame.Interface;

import java.awt.List;
import java.util.ArrayList;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public interface Kit {
	
	public String getName(Boolean colored);
	
	public String getDescription();
	
	public Sound getSound();
	
	public ArrayList<Ability> getAbilities();
	
}
