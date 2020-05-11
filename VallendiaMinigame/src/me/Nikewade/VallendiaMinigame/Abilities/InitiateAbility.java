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
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class InitiateAbility implements Listener, Ability{
	int percent = 20;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Initiate";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Hits while you are at full health do ",
				percent + "% extra damage");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.IRON_SWORD);
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
		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility((Player) e.getDamager(), "Initiate"))
		{
			Player p = (Player) e.getDamager();
			
			if(p.getHealth() >= p.getMaxHealth())
			{
				double higherpercent = Utils.getPercentHigherOrLower(percent, true);
				double higherDamage = e.getFinalDamage() * higherpercent;
    			e.setDamage(0);
    			e.setDamage(DamageModifier.ARMOR, higherDamage);
				
			}
			
		}
		
	}

}