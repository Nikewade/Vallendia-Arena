package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class KickAbility implements Ability{
	public static ArrayList<LivingEntity> list = new ArrayList<LivingEntity>();
	int time = 5;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Kick";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Kick your target, dealing 1 heart,",
				"silencing all abilities for " + time + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.LEATHER_BOOTS);
	}

	@Override
	public boolean RunAbility(Player p) {
 		LivingEntity target = AbilityUtils.getTarget(p, 5);
 		if(target != null)
 		{
 	 		int damage = 2;
 	 		p.getWorld().playSound(p.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 2, (float) 0.7);
 	 		target.getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0, 1.8, 0), 20);
 	 		AbilityUtils.damageEntity(target, p, 2); 
 	 		AbilityUtils.silenceAbilities(target, time, "Kick");
         	Language.sendAbilityUseMessage(target, "Your abilities are silenced.", "Kick");
         	
 	 		
 	 		
 			return true;
 		}
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
