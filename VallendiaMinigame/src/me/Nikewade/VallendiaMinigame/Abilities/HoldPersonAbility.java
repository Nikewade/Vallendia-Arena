package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.ConeEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class HoldPersonAbility implements Ability, Listener {
	int time = 200;
	int range = 15;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Hold Person";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Stun your target from up to " + range + " blocks away", 
				"for " + time / 20 + " seconds. Any damage done to the",
				"target will cancel this ability.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.TOTEM);
	}

	@Override
	public boolean RunAbility(Player p) {
 		LivingEntity target = AbilityUtils.getTarget(p, range);
 		if(target != null)
 		{
 			AbilityUtils.stun(p,target, this.getName(), time, true);
 	 		p.getWorld().playSound(p.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 1, 0.8F);
 	 		
 			ConeEffect se = new ConeEffect(VallendiaMinigame.getInstance().effectmanager);
 			se.particle = Particle.CRIT_MAGIC;
 			Location loc = target.getLocation();
 			loc.setPitch(-90);
 			se.lengthGrow = (float) 0.01;
 			se.iterations = 20;
 			se.setLocation(loc);
 			se.start();
 	 		

 	 		return true;
 		}
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}
	


}
