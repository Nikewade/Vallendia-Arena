package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class UncannyDodgeAbility implements Ability,Listener{
	int time = 15;
	int percent = 10;
	ArrayList<Player> active = new ArrayList<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Uncanny Dodge";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You break free from all stuns and slowness.",
							"for the next "+ time +" seconds you ignore "+ percent + "% of the",
							"damage you take.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.FEATHER);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		if(active.contains(p))
		{
			return false;
		}
		active.add(p);
		p.getWorld().playSound( p.getLocation(), Sound.UI_TOAST_IN, 4, 0.4F);
		
		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se.particle = Particle.CRIT;
		se.radius = 1F;
		se.particles = 5;
		se.setEntity(p);
		se.iterations = 3;
		se.particleOffsetY = 1F;
		se.yOffset = -0.8F;	
		se.start();
	
	if(AbilityUtils.isStunned(p) || p.hasPotionEffect(PotionEffectType.SLOW) || AbilityUtils.isRooted(p))
	{
		Language.sendAbilityUseMessage(p, "You break free from all stuns and slowness!", "Uncanny Dodge");
	}
	
	if(AbilityUtils.isStunned(p))
	{
		AbilityUtils.removeAllStuns(p);
	}
	if((p.hasPotionEffect(PotionEffectType.SLOW)))
	{
		p.removePotionEffect(PotionEffectType.SLOW);
	}
	if(AbilityUtils.isRooted(p))
	{
		AbilityUtils.removeAllRoots(p);
	}
	
	new BukkitRunnable()
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(active.contains(p))
			{
				active.remove(p);
			}
		}
		
	}.runTaskLater(VallendiaMinigame.getInstance(), time*20);
	
	return true;
	}
	
	@EventHandler
	public void onDamage (EntityDamageByEntityEvent e)
	{
		if(!(e.getEntity() instanceof Player))
		{
			return;
		}
        Player p = (Player) e.getEntity();


				if(active.contains(p))
				{					
		            double damage = e.getFinalDamage();
		            double highpercent = Utils.getPercentHigherOrLower(percent, false);
		            double newdamage = damage*highpercent;
					e.setDamage(0);
					e.setDamage(DamageModifier.ARMOR, newdamage);
					SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
					se.particle = Particle.CRIT;
					se.radius = 1F;
					se.particles = 5;
					se.setEntity(p);
					se.iterations = 3;
					se.particleOffsetY = 1F;
					se.yOffset = -0.8F;	
					se.start();
				}
			
		}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(active.contains(p))
		{
			active.remove(p);
		}
	}

}