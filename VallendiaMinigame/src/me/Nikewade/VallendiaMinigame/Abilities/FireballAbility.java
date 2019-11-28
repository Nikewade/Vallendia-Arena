package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class FireballAbility implements Ability{
	int damage = 10;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Fireball";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Shoot an arced bead of fire that",
				"explodes on impact destroying blocks" ,
				"and damaging anything in a large",
				"radius for " + damage + " damage.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.FIREBALL);
	}

	@Override
	public boolean RunAbility(Player p) {
	
  		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se.particle = Particle.FLAME;
		se.disappearWithOriginEntity = true;
		se.infinite();
		se.radius = 0.2;
		se.particles = 2;
		se.speed = (float) 0;
		se.visibleRange = 50;
		
  		SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se2.particle = Particle.FLAME;
		se2.iterations = 2;
		se2.particles = 100;
		se2.radius = 7;
		se2.speed = (float) 0.1;
		se2.visibleRange = 50;
		
  		SphereEffect se3 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se3.particle = Particle.EXPLOSION_HUGE;
		se3.iterations = 1;
		se3.particles = 10;
		se3.radius = 2;
		se3.speed = (float) 0.1;
		se3.visibleRange = 50;
		
		Runnable run = new Runnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				AbilityUtils.explode(se.getLocation(), p, 5, damage, true, true, false);
				se2.setLocation(se.getLocation());
				se2.start();
				se3.setLocation(se.getLocation());
				se3.start();
			}
			
		};
		
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, (float) 1.6);
		AbilityUtils.arcParticle(p, se, 0.7, run);
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
