package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class ThroughTheSeamsAbility implements Ability, Listener{
	 //made by Emma
	int damage = 1;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Through the Seams";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You deal an extra " + damage + " damage",
				"with your sword, that extra damage goes through armor.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.LEATHER_CHESTPLATE);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}
	
	@EventHandler
	public void onDamage (EntityDamageByEntityEvent e)
	{

		if(!(e.getDamager() instanceof Player))
		{
			return;
		}
		
		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility((Player) e.getDamager(), "Through the Seams"))
		{
			Player p = (Player) e.getDamager();
			
			if(p.getInventory().getItemInHand().getType() == Material.WOOD_SWORD ||
					p.getInventory().getItemInHand().getType() == Material.STONE_SWORD ||
							p.getInventory().getItemInHand().getType() == Material.IRON_SWORD||
									p.getInventory().getItemInHand().getType() == Material.GOLD_SWORD||
											p.getInventory().getItemInHand().getType() == Material.DIAMOND_SWORD)
			{
			
			double edamage = e.getDamage();
			double newDamage = edamage+damage;
			e.setDamage(newDamage);
			}


		}
		
	}

}