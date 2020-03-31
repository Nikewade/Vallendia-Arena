package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class ZapAbility implements Ability {
	// stun is in ticks (*20)
	int stun = 10;
	int damage = 4;
	int range = 15;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Zap";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Target an enemy up to " + range + " blocks away.",
				"Deal " + damage + " damage to them and stun ",
						"them for " + (float) (stun) / 20 + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.CHORUS_FLOWER);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
 		LivingEntity target = AbilityUtils.getTarget(p, 5);
 		if(target != null)
 		{
 	  		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
 			se.particle = Particle.CRIT_MAGIC;
 			se.radius = 0.2;
 			se.iterations = 10;
 			se.particles = 5;
 			se.speed = (float) 0;
 			se.visibleRange = 200;
 			se.infinite();
 			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 1F, 0.5F);
 			
 			Runnable run = new Runnable()
 			{

 				@Override
 				public void run() {
 					// TODO Auto-generated method stub
 					AbilityUtils.damageEntity(target, p, damage);
 					AbilityUtils.stun(p, target, "Zap", stun, true);
 					
 				}
 		
 			};
 			
 			AbilityUtils.followTargetParticle(p, target, se, false, true, run, null, 1.7, 200);
 			
 			return true;
 		}
 		return false;
		
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}