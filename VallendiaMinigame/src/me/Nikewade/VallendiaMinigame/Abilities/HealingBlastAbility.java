package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class HealingBlastAbility implements Ability{
	//made by emma
	int range = 30;
	int healAmount = 8;


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Healing Blast";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("you heal and ally up to " + range + "",
							"blocks away for " + healAmount + "damage.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		ItemStack potion = new ItemStack(Material.LINGERING_POTION, 1);

        PotionMeta potionmeta = (PotionMeta) potion.getItemMeta();
        potionmeta.setColor(Color.RED);
        potion.setItemMeta(potionmeta);
		
		return new ItemStack (potion);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		if(AbilityUtils.getHealingTarget(p, range) == null)
		{
			return false;
		}
		
		LivingEntity e = AbilityUtils.getHealingTarget(p, range);
		
  		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se.particle = Particle.REDSTONE;
		se.color = Color.FUCHSIA;
		se.radius = 0.2;
		se.particles = 5;
		se.speed = (float) 0;
		se.visibleRange = 200;
		se.infinite();
		
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EVOCATION_ILLAGER_CAST_SPELL, 1, 1.4F);
		
		Runnable run = new Runnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				AbilityUtils.healEntity(e, healAmount);
				e.getWorld().playSound(e.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 1, 1.6F);
				
			}
	
		};
		
		AbilityUtils.followTargetParticle(p, e, se, false, true, run, null, 1.2, 200);
		
		return true;
		
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}