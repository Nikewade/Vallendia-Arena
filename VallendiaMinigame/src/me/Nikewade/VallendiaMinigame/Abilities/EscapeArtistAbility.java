package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.Interface.Ability;

public class EscapeArtistAbility implements Ability{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Escape Artist";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		return Arrays.asList("You are immune to all stun and slowing effects.");
=======
		return Arrays.asList("You have a 30% chance to avoid all stun",
							"and slowing effects.");
>>>>>>> second-repo/master
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.LEASH);
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

<<<<<<< HEAD
}
=======
}
>>>>>>> second-repo/master
