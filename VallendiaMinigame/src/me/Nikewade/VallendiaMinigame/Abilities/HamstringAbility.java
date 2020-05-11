package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class HamstringAbility implements Listener, Ability {
	//made by emma
	
	ArrayList<Player> abilityActive = new ArrayList<>();
	HashMap <LivingEntity, BukkitTask> timers = new HashMap<>();
	int delay = 10;
	int amplifier = 2;
	int duration = 20;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Hamstring";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Once activated, for " + delay + " seconds, your",
				"next hit will severely slow your target for",
				duration + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.PORK);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		if(abilityActive.contains(p))
		{
			return false;
		}
		
		abilityActive.add(p);
		
		Language.sendAbilityUseMessage(p, "is now active.", "Hamstring");
		
		new BukkitRunnable()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						if(abilityActive.contains(p))
						{
							abilityActive.remove(p);
							Language.sendAbilityUseMessage(p, "is no longer active.", "Hamstring");
						}
						
					}
			
			
				}.runTaskLater(VallendiaMinigame.getInstance(), delay*20);
		
		
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(timers.containsKey(p))
		{
			timers.get(p).cancel();
			timers.remove(p);
		}
		if(abilityActive.contains(p))
		{
			abilityActive.remove(p);
		}
		
	}
	
	@EventHandler
	public void onHit (EntityDamageByEntityEvent e)
	{
		if(!(e.getDamager() instanceof Player))
		{
			return;
		}
		Player p = (Player) e.getDamager();
		
		if(abilityActive.contains(p))
		{
			if(!(e.getEntity() instanceof LivingEntity))
			{
				return;
			}
			
			abilityActive.remove(p);
			LivingEntity target = (LivingEntity) e.getEntity();
			AbilityUtils.addPotionDuration(p, target, "Hamstring", PotionEffectType.SLOW, amplifier, duration*20);	
			Language.sendAbilityUseMessage(p, "You slow your target!", "Hamstring");

			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BIG_FALL , 1, 0.1F);
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.disappearWithOriginEntity = true;
			se.infinite();
			se.particle = Particle.REDSTONE;
			se.color = Color.BLACK;
			se.radius = 0.8;
			se.particleOffsetY = (float) 0.5;
			se.particles = 1;
			se.yOffset = -0.8;
			se.speed = (float) 0;
			se.setEntity(target);
			se.start();
			
			BukkitTask timer = new BukkitRunnable()
					{

						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							se.cancel();
							timers.remove(p);
						}
				
					}.runTaskLater(VallendiaMinigame.getInstance(), duration*20);
			
					timers.put(target, timer);
		}
	}

}