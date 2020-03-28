package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class LightningBoltAbility implements Ability {
	int range = 25;
	int damage = 15;
	int castTime = 1;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Lightning Bolt";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Shoot a bolt of lightning forward " + range + " blocks",
				"damaging all entities within the line for " + damage + " damage.",
				Utils.Colorate("&8Cast: " + castTime + " seconds."));
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.CHORUS_PLANT);
	}

	@Override
	public boolean RunAbility(Player p) {
		Runnable run = new Runnable()
		{

			@Override
			public void run() {
				Location loc = p.getLocation().add(0, 1.4, 0);
				int i = 0;
				while(!loc.getBlock().getType().isSolid()){
					if(i >= range)
					{
						break;
					}
					
					for(Entity e : loc.getWorld().getNearbyEntities(loc, 0.6, 0.6, 0.6))
					{
						if(e instanceof LivingEntity && e != p)
						{
							AbilityUtils.damageEntity((LivingEntity)e, p, damage);
						}
					}
					
					i++;
				    loc = loc.add(loc.getDirection());
				}
				
						LineEffect line = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
						line.setLocation(p.getLocation().add(0, 1.2, 0));
						line.setTargetLocation(loc);
						line.particle = Particle.CRIT_MAGIC;
						line.iterations = 20;
						line.isZigZag = true;
						line.start();
						
						SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
						se.setEntity(p);
						se.disappearWithOriginEntity = true;
						se.iterations = 5;
						se.particle = Particle.CRIT_MAGIC;
						se.radius = 1;
						se.particles = 10;
						se.yOffset = -0.8;
						se.start();
		                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 2, (float) 1.6);
		                p.getWorld().strikeLightningEffect(p.getLocation().add(0, 100, 0));
				
			}
			
		};
	
	AbilityUtils.castAbility(p, castTime, run);
	return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
