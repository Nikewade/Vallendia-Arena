package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.FountainEffect;
import de.slikey.effectlib.util.DynamicLocation;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class EntangleAbility implements Ability{
	int time = 5;
	int range = 8;
	HashMap<LivingEntity, FountainEffect> particles = new HashMap<>();
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Entangle";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You entangle your target for " + time + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack (Material.VINE);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		LivingEntity target = AbilityUtils.getTarget(p, range);
		if(target == null)
		{
			return false;
		}
		
		AbilityUtils.root(p, target, "Entangle", time*20, false);
		
		new BukkitRunnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if(target.isOnGround())
				{
				target.getWorld().playSound(target.getLocation(), Sound.BLOCK_GRASS_BREAK, 2, (float) 0.1);
				p.getWorld().playSound(p.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 2, (float) 0.1);
				FountainEffect d = new FountainEffect(VallendiaMinigame.getInstance().effectmanager);
				d.setTargetEntity(target);
				d.disappearWithTargetEntity = true;
				d.setDynamicOrigin(new DynamicLocation(target.getLocation().add(0, 0.1, 0)));
				d.strands = 5;
				d.height = (float) 0.3;
				d.heightSpout = 0;
				d.particlesSpout = 0;
				d.radiusSpout = 0;
				d.radius = (float) 1;
				d.particlesStrand = 5;
				d.particle = Particle.BLOCK_CRACK;
				d.material = Material.VINE;
				d.infinite();
				d.start();
				
				particles.put(target, d);
				
				this.cancel();
				}
			}
		}.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);

		new BukkitRunnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if(particles.containsKey(target))
				{
					particles.get(target).cancel();
					particles.remove(target);
				}
				
			}
			
		}.runTaskLater(VallendiaMinigame.getInstance(), time*20);
		
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(particles.containsKey(p))
		{
			particles.get(p).cancel();
			particles.remove(p);
		}
		
	}

}