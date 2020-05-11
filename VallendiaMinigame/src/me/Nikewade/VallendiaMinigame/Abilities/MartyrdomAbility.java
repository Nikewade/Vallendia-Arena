package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class MartyrdomAbility implements Listener, Ability {
	int power = 3;
	int damage = 12;
	double delay = 1.5;
				

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Martyrdom";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("When you die, you leave a surprise!",
				"You explode 1.5 seconds after death, dealing",
				damage + " damage to all enemies within melee range.");
						
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(397 , 1 , (short) 4);
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
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	public void onDeath (PlayerDeathEvent e)
	{
		
		Player p = e.getEntity();
		
		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Martyrdom"))
		{
		Location ploc = p.getLocation();
		p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1, 1);
		
		BukkitTask timer = new BukkitRunnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				AbilityUtils.explode(ploc, p, power, damage, false, true, true);		
				
			}
			
		}.runTaskLater(VallendiaMinigame.getInstance(), (long) (delay*20));
		

		
		}
	}

	
	
	
	
	
	
	
	
}