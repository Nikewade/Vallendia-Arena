package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class LastStandAbility implements Ability{
	private static ArrayList<Player> enabled = new ArrayList<>();
	int time = 20;
<<<<<<< HEAD
	int percent = 30;
=======
	int percent = 40;
>>>>>>> second-repo/master

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Last Stand";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Increases your maximum health by " + percent + "% for" ,
				time + " seconds and instantly heals you for" , 
				"that amount.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.TOTEM);
	}

	@Override
	public boolean RunAbility(Player p) {
		if(enabled.contains(p))
		{
			return false;
		}
		enabled.add(p);
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, (float) 0.2);
<<<<<<< HEAD
		double oldHealth = p.getMaxHealth();
=======
		double oldHealth = p.getMaxHealth(); // I DONT CHECK IF HEALTH IS LESS THAN OLD HEALTH ANYMORE This may cause issues if something lowers their max health
											//while this is active
>>>>>>> second-repo/master
		double healthAdd = p.getMaxHealth() * Utils.getPercentHigherOrLower(percent, true);
		AbilityUtils.setMaxHealth(p, healthAdd, "Last Stand");	
		AbilityUtils.healEntity(p, (healthAdd - oldHealth));
		new BukkitRunnable() {
            @Override
            public void run() {
        		if(enabled.contains(p))
        		{
        			enabled.remove(p);
<<<<<<< HEAD
        			if(!(p.getMaxHealth() < oldHealth))
        			{
            			AbilityUtils.resetMaxHealth(p, "Last Stand");	
        			}
=======
            			AbilityUtils.resetMaxHealth(p, "Last Stand");	
>>>>>>> second-repo/master
        		}
            }
        }.runTaskLaterAsynchronously(VallendiaMinigame.getInstance(), time*20L);
        return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		
=======
		enabled.remove(p);
		AbilityUtils.resetMaxHealth(p, "Last Stand");
>>>>>>> second-repo/master
	}

}
