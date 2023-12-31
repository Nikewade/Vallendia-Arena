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
<<<<<<< HEAD
		abilities.add(Main.abilitymanager.getAbility("Blink"));
=======
		abilities.add(Main.abilitymanager.getAbility("Dimension Door"));
>>>>>>> second-repo/master
		abilities.add(Main.abilitymanager.getAbility("Kick"));
		abilities.add(Main.abilitymanager.getAbility("Blur"));
		abilities.add(Main.abilitymanager.getAbility("Particle Test"));
		abilities.add(Main.abilitymanager.getAbility("Bandage"));
		abilities.add(Main.abilitymanager.getAbility("Fireball"));
		abilities.add(Main.abilitymanager.getAbility("Lightning Bolt"));
		abilities.add(Main.abilitymanager.getAbility("Disintegrate"));
		abilities.add(Main.abilitymanager.getAbility("Fly"));
		abilities.add(Main.abilitymanager.getAbility("Swap"));
<<<<<<< HEAD
		abilities.add(Main.abilitymanager.getAbility("Levitate"));
		abilities.add(Main.abilitymanager.getAbility("Hold Person"));
		abilities.add(Main.abilitymanager.getAbility("Siphon"));
=======
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
		abilities.add(Main.abilitymanager.getAbility("Ice Shard"));
		abilities.add(Main.abilitymanager.getAbility("Fire Blast"));
		abilities.add(Main.abilitymanager.getAbility("Ray of Enfeeblement"));
		abilities.add(Main.abilitymanager.getAbility("Heat Ray"));
		abilities.add(Main.abilitymanager.getAbility("Freeze Ray"));
		abilities.add(Main.abilitymanager.getAbility("Acidic Ray"));
		abilities.add(Main.abilitymanager.getAbility("Stone Skin"));
		abilities.add(Main.abilitymanager.getAbility("Scorching Ray"));
		abilities.add(Main.abilitymanager.getAbility("Ray Of Frost"));
		abilities.add(Main.abilitymanager.getAbility("Ghost Touch"));
		abilities.add(Main.abilitymanager.getAbility("Tunnel Vision"));
		abilities.add(Main.abilitymanager.getAbility("Baker"));
		abilities.add(Main.abilitymanager.getAbility("Expeditious Retreat"));
		abilities.add(Main.abilitymanager.getAbility("Flame Imbued Wand"));
		abilities.add(Main.abilitymanager.getAbility("Extend Wand"));
		abilities.add(Main.abilitymanager.getAbility("Quicken Wand"));
		abilities.add(Main.abilitymanager.getAbility("Disruptive Wand"));
>>>>>>> second-repo/master
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
<<<<<<< HEAD
		return Utils.Colorate("&bA weaver of powerful magicical spells.");
	}

	@Override
=======
		return Utils.Colorate("&bA scholarly spellcaster capable");
	}

	@Override
	public String getDescription2() {
		// TODO Auto-generated method stub
		return Utils.Colorate("&bof wielding varied and powerful magic.");
	}
	
	@Override
>>>>>>> second-repo/master
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
