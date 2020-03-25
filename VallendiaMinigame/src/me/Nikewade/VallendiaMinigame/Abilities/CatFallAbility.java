package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class CatFallAbility implements Listener, Ability {
	int distance = 30;
	int amplifier = 2;
	int duration = 10;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Cat Fall";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("immune to fall damage unless " + distance + " blocks or more = slowed");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack (Material.LEATHER_BOOTS);
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
	public void onFall (EntityDamageEvent e)
	{
		if(!(e.getEntity() instanceof Player))
		{
			return;
		}
		Player p = (Player) e.getEntity();
		if(e.getCause() == DamageCause.FALL)	
		{
		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Cat Fall"))
		{
			e.setCancelled(true);
			if(p.getFallDistance() >= distance)
			{
				AbilityUtils.addPotionDuration(p, p, "Cat Fall", PotionEffectType.SLOW, amplifier, duration*20);
			}
				
			
		}
		
		}
	}

}