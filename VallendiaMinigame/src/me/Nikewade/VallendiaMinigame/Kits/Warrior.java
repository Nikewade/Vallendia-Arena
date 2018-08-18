package me.Nikewade.VallendiaMinigame.Kits;

import java.util.ArrayList;

import org.bukkit.Sound;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class Warrior implements Kit {
	private ArrayList<Ability> abilities = new ArrayList<Ability>();
	VallendiaMinigame Main;
	
	public Warrior(VallendiaMinigame Main)
	{
		this.Main = Main;
		abilities.add(Main.abilitymanager.getAbility("Rage"));
		abilities.add(Main.abilitymanager.getAbility("Leap"));
		abilities.add(Main.abilitymanager.getAbility("Bash"));
	}
	
	@Override
	public String getName(Boolean colored) {
		// TODO Auto-generated method stub
		if (colored)
		{
			return Utils.Colorate("&4&lWarrior");
		}else return "warrior";

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return Utils.Colorate("&cYour typical fighter.");
	}

	@Override
	public ArrayList<Ability> getAbilities() {
		// TODO Auto-generated method stub
		return abilities;
	}

	@Override
	public Sound getSound() {
		// TODO Auto-generated method stub
		return Sound.ENTITY_ENDERDRAGON_GROWL;
	}





}
