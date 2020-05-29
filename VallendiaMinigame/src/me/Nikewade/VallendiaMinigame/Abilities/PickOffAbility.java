package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class PickOffAbility implements Ability, Listener{
	int range = 25;
	int multiplier = 2;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Pick Off";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Deal " + multiplier + "x damage to players who are in a", 
							"party, but are not within " + range + " blocks of a party member.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.FISHING_ROD);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@EventHandler
	public void onDamage (EntityDamageByEntityEvent e)
	{
		if(e.getDamager() instanceof Player)
		{
			Player p = (Player) e.getDamager();
			if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Pick Off"))
			{
				if(e.getEntity() instanceof Player)
				{
					Player target = (Player) e.getEntity();
					if(!(AbilityUtils.getPlayerParty(target) == ""))
					{
						if(checkPartyDistance(target, range))
						{
				            double damage = e.getFinalDamage();
				            double newdamage = damage*multiplier;
							e.setDamage(0);
							e.setDamage(DamageModifier.ARMOR, newdamage);
						}
						
					}
				}
				
			}
		}
	}
	
	public boolean checkPartyDistance(Player p, int distance)
	{
		int amount = 0;
		for(Entity e : AbilityUtils.getHealingAoeTargets(p, p.getLocation(), distance, distance, distance))
		{
			amount++;
		}
		
		if(amount == 0)
		{
			return true;
		}else
		{
			return false;
		}
			
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}