package me.Nikewade.VallendiaMinigame.Kits;

import java.util.ArrayList;

import org.bukkit.Sound;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class Assassin implements Kit {
	public static ArrayList<Ability> abilities = new ArrayList<Ability>();
	VallendiaMinigame Main;

	public Assassin(VallendiaMinigame Main)
	{
		this.Main = Main;
		abilities.add(Main.abilitymanager.getAbility("Climb"));
		abilities.add(Main.abilitymanager.getAbility("Backflip"));
		abilities.add(Main.abilitymanager.getAbility("Deflect Arrows"));
		abilities.add(Main.abilitymanager.getAbility("Sneak"));
		abilities.add(Main.abilitymanager.getAbility("Backstab"));
		abilities.add(Main.abilitymanager.getAbility("Grappling Hook"));
		abilities.add(Main.abilitymanager.getAbility("Shadowstep"));
		abilities.add(Main.abilitymanager.getAbility("Poison Arrows"));
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
		return Utils.Colorate("&7The master of stealth and deception.");
	}

	@Override
	public ArrayList<Ability> getAbilities() {
		// TODO Auto-generated method stub
		return abilities;
	}

	@Override
	public Sound getSound() {
		// TODO Auto-generated method stub
		return Sound.ENTITY_ENDERDRAGON_FLAP;
	}





}
