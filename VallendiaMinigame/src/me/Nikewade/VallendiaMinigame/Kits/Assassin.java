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
		abilities.add(Main.abilitymanager.getAbility("Pick Pocket"));
		abilities.add(Main.abilitymanager.getAbility("Kick"));
		abilities.add(Main.abilitymanager.getAbility("Blur"));
		abilities.add(Main.abilitymanager.getAbility("Vanish"));
		abilities.add(Main.abilitymanager.getAbility("Vampiric Touch"));
		abilities.add(Main.abilitymanager.getAbility("Bandage"));
		abilities.add(Main.abilitymanager.getAbility("Flashbang"));
		abilities.add(Main.abilitymanager.getAbility("Reflex"));
		abilities.add(Main.abilitymanager.getAbility("Cheap Shot"));
		abilities.add(Main.abilitymanager.getAbility("Kidney Shot"));
		abilities.add(Main.abilitymanager.getAbility("Envenom"));
		abilities.add(Main.abilitymanager.getAbility("Escape Artist"));
		abilities.add(Main.abilitymanager.getAbility("Fan Of Knives"));
		abilities.add(Main.abilitymanager.getAbility("Martyrdom"));
		abilities.add(Main.abilitymanager.getAbility("Cat Fall"));
		abilities.add(Main.abilitymanager.getAbility("Vampiric Aura"));
		abilities.add(Main.abilitymanager.getAbility("Lone Wolf"));
		abilities.add(Main.abilitymanager.getAbility("Noxious Gas Trap"));
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
