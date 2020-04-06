package me.Nikewade.VallendiaMinigame.Kits;

import java.util.ArrayList;

import org.bukkit.Sound;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class Archer implements Kit {
	public static ArrayList<Ability> abilities = new ArrayList<Ability>();

	VallendiaMinigame Main;

	public Archer(VallendiaMinigame Main)
	{
		this.Main = Main;
		abilities.add(Main.abilitymanager.getAbility("Climb"));
		abilities.add(Main.abilitymanager.getAbility("Dash"));
		abilities.add(Main.abilitymanager.getAbility("Sneak"));
		abilities.add(Main.abilitymanager.getAbility("Grappling Hook"));
		abilities.add(Main.abilitymanager.getAbility("Blinding Arrows"));
		abilities.add(Main.abilitymanager.getAbility("Poison Arrows"));
		abilities.add(Main.abilitymanager.getAbility("Sickening Arrows"));
		abilities.add(Main.abilitymanager.getAbility("Weakening Arrows"));
		abilities.add(Main.abilitymanager.getAbility("Slowing Arrows"));
		abilities.add(Main.abilitymanager.getAbility("Magic Arrows"));
		abilities.add(Main.abilitymanager.getAbility("Explosive Arrow"));
		abilities.add(Main.abilitymanager.getAbility("Kick"));
		abilities.add(Main.abilitymanager.getAbility("Bandage"));
		abilities.add(Main.abilitymanager.getAbility("Survivalist"));
		abilities.add(Main.abilitymanager.getAbility("Sniper"));
		abilities.add(Main.abilitymanager.getAbility("Repurpose"));
		abilities.add(Main.abilitymanager.getAbility("Camouflage"));
		abilities.add(Main.abilitymanager.getAbility("Ice Trap"));
		abilities.add(Main.abilitymanager.getAbility("Reflex"));
		abilities.add(Main.abilitymanager.getAbility("Bear Trap"));
		abilities.add(Main.abilitymanager.getAbility("Explosive Trap"));
		abilities.add(Main.abilitymanager.getAbility("Martyrdom"));
		abilities.add(Main.abilitymanager.getAbility("Healing Arrow"));
		abilities.add(Main.abilitymanager.getAbility("Cat Fall"));
		abilities.add(Main.abilitymanager.getAbility("Healing Arrow"));
		abilities.add(Main.abilitymanager.getAbility("Sonar Arrow"));
		abilities.add(Main.abilitymanager.getAbility("Lone Wolf"));
		abilities.add(Main.abilitymanager.getAbility("Noxious Gas Trap"));
		abilities.add(Main.abilitymanager.getAbility("Water Mastery"));
		abilities.add(Main.abilitymanager.getAbility("One Man Army"));
	}
	
	@Override
	public String getName(Boolean colored) {
		// TODO Auto-generated method stub
		if (colored)
		{
			return Utils.Colorate("&2&lArcher");
		}else return "archer";

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return Utils.Colorate("&aA master marksman.");
	}

	@Override
	public ArrayList<Ability> getAbilities() {
		// TODO Auto-generated method stub
		return abilities;
	}

	@Override
	public Sound getSound() {
		// TODO Auto-generated method stub
		return Sound.ENTITY_ARROW_HIT;
	}




}
