package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class BashAbility implements Ability {
	int time = 3;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Bash";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	
	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Bash the target over the head briefly stunning them",
				"for " +time+  " seconds. This will cause the target to go blind and",
				"become confused for a short time.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SKULL_ITEM);
	}

	@Override
	public boolean RunAbility(Player p) {
 		LivingEntity target = AbilityUtils.getTarget(p, 5);
 		if(target != null)
 		{
 			AbilityUtils.stun(p,target, this.getName(), time);
 	 		AbilityUtils.addPotionDuration(p, target, this.getName(), PotionEffectType.BLINDNESS, 4, 3*20);
 	 		AbilityUtils.addPotionDuration(p, target, this.getName(), PotionEffectType.CONFUSION, 4, 8*20);
 	 		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 2, 1);
 	 		target.getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0, 1.8, 0), 20);
 	 		AbilityUtils.damageEntity(target, p, 2);
 	 		return true;
 		}
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
