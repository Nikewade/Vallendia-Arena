package me.Nikewade.VallendiaMinigame.Kits;

import java.awt.List;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class Assassin implements Kit {
	VallendiaMinigame Main;
	
	public Assassin(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.kitmanager.kits.put(this.getName(false), this);
	}

	@Override
	public String getName(Boolean colored) {
		// TODO Auto-generated method stub
		if (colored)
		{
			return Utils.Colorate("&8&lAssassin");
		}else return "assassin";

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return Utils.Colorate("&7The master of stealth and diception.");
	}

	@Override
	public List getAbilities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getupgradeDiscount(String upgrade) {
		// TODO Auto-generated method stub
		if(upgrade.equalsIgnoreCase("Weapon"))
		{
			return 20;
		}
		return 0;
	}




}
