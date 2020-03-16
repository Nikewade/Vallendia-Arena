package me.Nikewade.VallendiaMinigame.Interface;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.Abilities.AbilityType;

public interface Ability {

	public String getName();
	
	public AbilityType getAbilityType();
	
	public List<String> getDescription();
	
	public ItemStack getGuiItem();
	
	public boolean RunAbility(Player p);
	
	public void DisableAbility(Player p);
	
	
}
