package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
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
		
		Runnable run = new Runnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				AbilityUtils.explode(se.getLocation(), p, 5, damage, true, true, true);
			}
			
		};
		
		
		AbilityUtils.arcParticle(p, se, 0.7, run);
		return false;
	}

}
