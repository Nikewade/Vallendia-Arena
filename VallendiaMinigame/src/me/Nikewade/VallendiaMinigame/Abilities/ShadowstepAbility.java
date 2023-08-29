package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class ShadowstepAbility implements Ability{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Shadowstep";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}


	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		return Arrays.asList("Step through the shadows appearing behind your enemy.");
=======
		return Arrays.asList("Step through the shadows",
				"appearing behind your enemy.");
>>>>>>> second-repo/master
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.EYE_OF_ENDER);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
 		LivingEntity target = AbilityUtils.getTarget(p, 15);
 		if(target != null)
 		{ 	 		
 	 		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 2, (float) 0.6);
 			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, (float) 0.7);
 	 		p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, p.getLocation().add(0, 1.8, 0), 20);
 	 		p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, p.getLocation().add(0, 1.8, 0), 20);
 	 		p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, p.getLocation().add(0, 1.8, 0), 20);
	 		
			p.setFallDistance(0);
 			p.teleport(Utils.getBlockBehindPlayer(target));
 			
 	 		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 2, (float) 0.6);
 			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, (float) 0.7);
 	 		p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, p.getLocation().add(0, 1.8, 0), 20);
 	 		p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, p.getLocation().add(0, 1.8, 0), 20);
 	 		p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, p.getLocation().add(0, 1.8, 0), 20);
 			return true;
 		}
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
