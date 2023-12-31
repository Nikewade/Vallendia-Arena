package me.Nikewade.VallendiaMinigame.Kits;

import java.util.ArrayList;

import org.bukkit.Sound;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class Warrior implements Kit {
	public static ArrayList<Ability> abilities = new ArrayList<Ability>();
	VallendiaMinigame Main;
	
	public Warrior(VallendiaMinigame Main)
	{
		this.Main = Main;
		abilities.add(Main.abilitymanager.getAbility("Rage"));
		abilities.add(Main.abilitymanager.getAbility("Leap"));
		abilities.add(Main.abilitymanager.getAbility("Bash"));
		abilities.add(Main.abilitymanager.getAbility("The High Ground"));
		abilities.add(Main.abilitymanager.getAbility("Root"));
		abilities.add(Main.abilitymanager.getAbility("Bull Rush"));
		abilities.add(Main.abilitymanager.getAbility("Momentum"));
		abilities.add(Main.abilitymanager.getAbility("Equip Bow"));
		abilities.add(Main.abilitymanager.getAbility("Pillage"));
		abilities.add(Main.abilitymanager.getAbility("Kick"));
		abilities.add(Main.abilitymanager.getAbility("Mount"));
		abilities.add(Main.abilitymanager.getAbility("Divine Shield"));
		abilities.add(Main.abilitymanager.getAbility("Last Stand"));
		abilities.add(Main.abilitymanager.getAbility("Bandage"));
		abilities.add(Main.abilitymanager.getAbility("Stunning Blows"));
        abilities.add(Main.abilitymanager.getAbility("Sunder Weapon"));
        abilities.add(Main.abilitymanager.getAbility("Stomp"));
        abilities.add(Main.abilitymanager.getAbility("Piercing Roar"));
        abilities.add(Main.abilitymanager.getAbility("Sunder Armor"));
        abilities.add(Main.abilitymanager.getAbility("Pummel"));
<<<<<<< HEAD
        abilities.add(Main.abilitymanager.getAbility("Quake"));
=======
        abilities.add(Main.abilitymanager.getAbility("Quick Death"));
		abilities.add(Main.abilitymanager.getAbility("Martyrdom"));
		abilities.add(Main.abilitymanager.getAbility("Cat Fall"));
		abilities.add(Main.abilitymanager.getAbility("Initiate"));
		abilities.add(Main.abilitymanager.getAbility("Lone Wolf"));
		abilities.add(Main.abilitymanager.getAbility("One Man Army"));
		abilities.add(Main.abilitymanager.getAbility("Break Free"));
		abilities.add(Main.abilitymanager.getAbility("Quake"));
		abilities.add(Main.abilitymanager.getAbility("Lay On Hands"));
		abilities.add(Main.abilitymanager.getAbility("Rally Up"));
		abilities.add(Main.abilitymanager.getAbility("Blessing Aura"));
		abilities.add(Main.abilitymanager.getAbility("Bolas"));
		abilities.add(Main.abilitymanager.getAbility("Taunt Creatures"));
		abilities.add(Main.abilitymanager.getAbility("Through The Seams"));
		abilities.add(Main.abilitymanager.getAbility("Shield"));
		abilities.add(Main.abilitymanager.getAbility("Investor"));
		abilities.add(Main.abilitymanager.getAbility("Sun Strike"));
		abilities.add(Main.abilitymanager.getAbility("Tunnel Vision"));
		abilities.add(Main.abilitymanager.getAbility("Taunt"));
		abilities.add(Main.abilitymanager.getAbility("Whirlwind"));
		abilities.add(Main.abilitymanager.getAbility("Baker"));
		abilities.add(Main.abilitymanager.getAbility("Survivalist"));
>>>>>>> second-repo/master
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
<<<<<<< HEAD
		return Utils.Colorate("&cA master of combat.");
=======
		return Utils.Colorate("&cA martial combatant");
	}
	
	@Override
	public String getDescription2() {
		// TODO Auto-generated method stub
		return Utils.Colorate("&cskilled in the art of war.");
>>>>>>> second-repo/master
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
