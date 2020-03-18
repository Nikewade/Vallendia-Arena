package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class TrueStrikeAbility implements Listener, Ability{
	//made by emma
	ArrayList<Player> abilityActive = new ArrayList<>();
	int duration = 20;
	// percent has to be + 100 eg. 300% of initial damage you would need to put 200, 400% would be 300 etc..
	// make sure to change description accordingly 
	int percent = 200;
	

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "True Strike";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You ready your weapon, your next attack does 300% damage");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.DIAMOND_SWORD);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(abilityActive.contains(p))
		{
			return false;
		}
		
		abilityActive.add(p);
		Language.sendAbilityUseMessage(p, "You ready your weapon", "True Strike");
		
		BukkitTask timer = new BukkitRunnable() 
		{

			@Override
			public void run() {
				
				if(abilityActive.contains(p))
				{
				abilityActive.remove(p);
				Language.sendAbilityUseMessage(p, "True strike is no longer active", "True Strike");

				}
				
			}
		}.runTaskLater(VallendiaMinigame.getInstance(), duration*20);
		
		
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(abilityActive.contains(p))
		{
			abilityActive.remove(p);
		}
		
	}
	
	@EventHandler
	public void onDamage (EntityDamageByEntityEvent e)
	{
		if(!(e.getDamager() instanceof Player))
		{
			return;
		}
		Player p = (Player) e.getDamager();
		if(abilityActive.contains(p))
		{
		
			double damage = e.getDamage();
			double highpercent = Utils.getPercentHigherOrLower(percent, true);
			e.setDamage(damage * highpercent);
			abilityActive.remove(p);
			
			p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, (float) 0.6, 1);

			
		}else
		{
			return;
		}
	}
	
	
	
	
	

}