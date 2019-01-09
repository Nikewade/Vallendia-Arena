package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class BashAbility implements Ability {

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
		return Arrays.asList("Bash the target over the head briefly stunning them.");
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
 	 		int damage = 2;
 	 		AbilityUtils.addPotionDuration(target, PotionEffectType.SLOW, 4, 2*20);
 	 		AbilityUtils.addPotionDuration(target, PotionEffectType.BLINDNESS, 4, 3*20);
 	 		AbilityUtils.addPotionDuration(target, PotionEffectType.CONFUSION, 4, 8*20);
 	 		p.sendMessage(Utils.Colorate("&8&l[Bash] &8You bash your target over the head."));
 	 		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 1, 1);
 	 		target.getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0, 1.8, 0), 20);
 	 		target.damage(damage); 
 			return true;
 		}
		return false;
	}

}
