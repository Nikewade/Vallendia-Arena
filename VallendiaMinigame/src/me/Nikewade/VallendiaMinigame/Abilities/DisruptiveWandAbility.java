package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.Interface.Ability;

public class DisruptiveWandAbility implements Ability{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Disruptive Wand";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Your wand hits have a 15% chance",
							"to silence enemies for 1 second.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.LEVER);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}