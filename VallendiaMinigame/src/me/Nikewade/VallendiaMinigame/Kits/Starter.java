package me.Nikewade.VallendiaMinigame.Kits;

import java.awt.List;
import java.util.ArrayList;

import org.bukkit.Sound;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class Starter implements Kit {

	@Override
	public String getName(Boolean colored) {
		// TODO Auto-generated method stub
		if (colored)
		{
			return Utils.Colorate("Starter");
		}else return "starter";

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Ability> getAbilities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sound getSound() {
		// TODO Auto-generated method stub
		return null;
	}


}
