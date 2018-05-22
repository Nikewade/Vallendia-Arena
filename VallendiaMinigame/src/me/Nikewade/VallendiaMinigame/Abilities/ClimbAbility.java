package me.Nikewade.VallendiaMinigame.Abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.Interface.Ability;

public class ClimbAbility implements Ability {
	

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Climb";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Allowes you to climb up things.";
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.TRIPWIRE_HOOK);
	}

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		p.sendMessage("CLIMBED!!");
		return false;
	}

}
