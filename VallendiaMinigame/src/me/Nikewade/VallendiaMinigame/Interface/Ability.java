package me.Nikewade.VallendiaMinigame.Interface;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.Abilities.AbilityType;

public interface Ability {

	public String getName(Boolean colored);
	
	public AbilityType getAbilityType();
	
	public String getDescription();
	
	public ItemStack getGuiItem();
	
	public int getCost();
	
	public boolean RunAbility(Player p);
	
}
