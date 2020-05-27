package me.Nikewade.VallendiaMinigame.Kits;

import java.util.ArrayList;

import org.bukkit.Sound;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class Mage implements Kit {
	public static ArrayList<Ability> abilities = new ArrayList<Ability>();
	VallendiaMinigame Main;
	
	public Mage(VallendiaMinigame Main)
	{
		this.Main = Main;
		abilities.add(Main.abilitymanager.getAbility("Mage Armor"));
		abilities.add(Main.abilitymanager.getAbility("Dimension Door"));
		abilities.add(Main.abilitymanager.getAbility("Kick"));
		abilities.add(Main.abilitymanager.getAbility("Blur"));
		abilities.add(Main.abilitymanager.getAbility("Particle Test"));
		abilities.add(Main.abilitymanager.getAbility("Bandage"));
		abilities.add(Main.abilitymanager.getAbility("Fireball"));
		abilities.add(Main.abilitymanager.getAbility("Lightning Bolt"));
		abilities.add(Main.abilitymanager.getAbility("Disintegrate"));
		abilities.add(Main.abilitymanager.getAbility("Fly"));
		abilities.add(Main.abilitymanager.getAbility("Swap"));
		abilities.add(Main.abilitymanager.getAbility("Hold Person"));
		abilities.add(Main.abilitymanager.getAbility("Siphon"));
		abilities.add(Main.abilitymanager.getAbility("Martyrdom"));
		abilities.add(Main.abilitymanager.getAbility("Phoenix"));
		abilities.add(Main.abilitymanager.getAbility("Vampiric Aura"));
		abilities.add(Main.abilitymanager.getAbility("Lone Wolf"));
		abilities.add(Main.abilitymanager.getAbility("Zap"));
		abilities.add(Main.abilitymanager.getAbility("Mind Thrust"));
		abilities.add(Main.abilitymanager.getAbility("Repelling Blast"));
		abilities.add(Main.abilitymanager.getAbility("Delayed Blast Fireball"));
		abilities.add(Main.abilitymanager.getAbility("Water Mastery"));
		abilities.add(Main.abilitymanager.getAbility("Blindness"));
		abilities.add(Main.abilitymanager.getAbility("Repulsion"));
		abilities.add(Main.abilitymanager.getAbility("Heal"));
		abilities.add(Main.abilitymanager.getAbility("Healing Aura"));
		abilities.add(Main.abilitymanager.getAbility("Healing Blast"));
		abilities.add(Main.abilitymanager.getAbility("Healing Burst"));
		abilities.add(Main.abilitymanager.getAbility("Glitterdust"));
		abilities.add(Main.abilitymanager.getAbility("Invisibility"));
		abilities.add(Main.abilitymanager.getAbility("Faerie Fire"));
		abilities.add(Main.abilitymanager.getAbility("Entangle"));
		abilities.add(Main.abilitymanager.getAbility("Charm Person"));
		abilities.add(Main.abilitymanager.getAbility("Enthrall"));
		abilities.add(Main.abilitymanager.getAbility("Fire Mastery"));
		abilities.add(Main.abilitymanager.getAbility("Acid Rain"));
		abilities.add(Main.abilitymanager.getAbility("Locate"));
		abilities.add(Main.abilitymanager.getAbility("Avasculate"));
		abilities.add(Main.abilitymanager.getAbility("Investor"));
		abilities.add(Main.abilitymanager.getAbility("Acid Splash"));
		abilities.add(Main.abilitymanager.getAbility("Contingency"));
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
		return Utils.Colorate("&bA scholarly spellcaster capable");
	}

	@Override
	public String getDescription2() {
		// TODO Auto-generated method stub
		return Utils.Colorate("&bof wielding varied and powerful magic.");
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
