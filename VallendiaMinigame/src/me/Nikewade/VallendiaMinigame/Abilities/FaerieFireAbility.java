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
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class FaerieFireAbility implements Ability {
	int length = 30;
	int range = 15;
	HashMap<Player, SphereEffect> particles = new HashMap<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Faerie Fire";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Give opponents the glow effect and make them",
							"unable to go invisible for " + length + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SEA_LANTERN);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		LivingEntity target = AbilityUtils.getTarget(p, range);
		if(target == null)
		{
		return false;
		}
		
		if(!(target instanceof Player))
		{
			return false;
		}

		target.getWorld().playSound(target.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);	
		if(!AbilityUtils.isNoInvisible((Player) target))
		{
			if(AbilityUtils.isInvisible((Player) target))
			{
				AbilityUtils.removeInvisible((Player) target);
			}
			AbilityUtils.noInvisible((Player) target);
		}
		
		AbilityUtils.addPotionDuration(p, target, "Faerie Fire", PotionEffectType.GLOWING, 1, length*20);
		
		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se.disappearWithOriginEntity = true;
		se.infinite();
		se.particle = Particle.REDSTONE;
		se.radius = 0.8;
		se.particleOffsetY = (float) 0.5;
		se.particles = 3;
		se.yOffset = -0.8;
		se.speed = 1;
		se.setEntity(target);
		se.start();
		
		particles.put((Player) target, se);
		
		BukkitTask timer = new BukkitRunnable()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(particles.containsKey(target))
						{
						particles.get(target).cancel();
						particles.remove(target);
						}
						if(AbilityUtils.isNoInvisible((Player) target))
						{
							AbilityUtils.removeNoInvisible((Player) target);
						}
						
					}
			
				}.runTaskLater(VallendiaMinigame.getInstance(), length*20);
		
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
		
		if(AbilityUtils.isNoInvisible(p))
		{
			AbilityUtils.removeNoInvisible(p);
		}
		
		if(p.hasPotionEffect(PotionEffectType.GLOWING))
		{
			
			p.removePotionEffect(PotionEffectType.GLOWING);
			
		}
	
	}

}