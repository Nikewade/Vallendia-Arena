package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class PummelAbility implements Ability{
	//made by Emma
	int duration = 5;
	int damage = 6;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Pummel";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You pummel your target interrupting",
				"Spell casting and silencing them ",
				"for " + duration + "seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.BONE);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(!(AbilityUtils.getTarget(p, 5) instanceof Player) && (!(AbilityUtils.getTarget(p, 5)==null)))
		{
			Language.sendAbilityUseMessage(p, "You can only Pummel players!", "Pummel");
			return false;
		}else
		{
			Player target = (Player) AbilityUtils.getTarget(p, 5);
			
			//sounds and particles
			target.getWorld().playSound(target.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 0.8F);
			target.getWorld().playSound(target.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 1, 1);
			
 	 		target.getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0, 1.8, 0), 20);
 	 		
 	 		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
 	 		se.particle = Particle.BLOCK_CRACK;
 	 		se.material = Material.REDSTONE_BLOCK;
 	 		se.radius = 0.6;
 	 		se.particles = 8;
 	 		se.speed = (float) 0.1;
 	 		se.visibleRange = 50;
 	 		se.iterations = 2;
 	 		se.yOffset = 0.8;
 	 				
 	 		se.setLocation(target.getLocation());
 	 		se.start();
 	 					
			AbilityUtils.silenceAbilities(target, duration, "Pummel");
			AbilityUtils.damageEntity(target, p, damage);
			Language.sendAbilityUseMessage(target, "Your abilities are silenced", "Pummel");
			return true;
		}
	
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}