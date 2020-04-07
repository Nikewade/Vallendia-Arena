package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class GlitterdustAbility implements Ability, Listener{
	int time = 20;
	int radius = 10;
	HashMap<LivingEntity, SphereEffect> particles = new HashMap<>();
	ArrayList <LivingEntity> list = new ArrayList<>();
	HashMap <Player, ArrayList<LivingEntity>> targets = new HashMap<>();
	

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Glitterdust";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Expose all players around you in a " + radius + " block",
							"radius who were invisible and make them unable to go",
							"invisible again for " + time + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.GLOWSTONE_DUST);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
        SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
        se2.particle = Particle.REDSTONE;
        se2.color = Color.WHITE;
        se2.radius = radius;
        se2.particles = 150;
        se2.speed = (float) 0;  
        se2.iterations = 50;
        se2.visibleRange = 50;
        se2.setLocation(p.getLocation());
        se2.start();
        
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ILLUSION_ILLAGER_CAST_SPELL, 2, 0.5F);

		for(Entity e : AbilityUtils.getAoeTargets(p, p.getLocation(), radius, radius, radius))
		{
			if(e instanceof Player)
			{
			AbilityUtils.noInvisible((Player) e);
			if(AbilityUtils.isInvisible((Player) e))
			{
				AbilityUtils.removeInvisible((Player) e);
			}
			}
			
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.setEntity(e);
			se.disappearWithOriginEntity = true;
			se.infinite();
			se.particle = Particle.FIREWORKS_SPARK;
			se.radius = 0.4;
			se.particleOffsetY = (float) 0.5;
			se.particles = 1;
			se.yOffset = -0.4;
			se.speed = (float) 0;
			se.start();
			particles.put((LivingEntity) e, se);
			list.add((LivingEntity) e);
		}
		targets.put(p, list);
		
		new BukkitRunnable()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						for(LivingEntity e : targets.get(p))
						{
							if(e instanceof Player)
							{
							AbilityUtils.removeNoInvisible((Player) e);
							}
							
							if(particles.containsKey(e))
							{
								particles.get(e).cancel();		
								particles.remove(e);
							}
						}
											
					}

			
				}.runTaskLater(VallendiaMinigame.getInstance(), time * 20);
		
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