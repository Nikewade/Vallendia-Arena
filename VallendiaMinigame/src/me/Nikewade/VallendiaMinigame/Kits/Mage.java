package me.Nikewade.VallendiaMinigame.Kits;

import java.awt.List;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.ClimbAbility;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class Mage implements Kit {
	private ArrayList<Ability> abilities = new ArrayList<Ability>();
	VallendiaMinigame Main;
	
	public Mage(VallendiaMinigame Main)
	{
		this.Main = Main;
		abilities.add(Main.abilitymanager.getAbility("Backflip"));
	}

	@Override
	public String getName(Boolean colored) {
		// TODO Auto-generated method stub
		if (colored)
		{
			return Utils.Colorate("&3&lMage");
		}else return "mage";

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return Utils.Colorate("&bWielder of magic.");
	}

	@Override
	public ArrayList<Ability> getAbilities() {
		// TODO Auto-generated method stub
		return abilities;
	}

	@Override
	public Sound getSound() {
		// TODO Auto-generated method stub
		return Sound.BLOCK_END_PORTAL_SPAWN;
	}





}
