package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class BandageAbility implements Ability{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Bandage";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.PAPER);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		Runnable run = new Runnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				p.sendMessage("boom");
			}
			
		};
		AbilityUtils.castAbility(p, 5, run);

		return true;
	}

}
