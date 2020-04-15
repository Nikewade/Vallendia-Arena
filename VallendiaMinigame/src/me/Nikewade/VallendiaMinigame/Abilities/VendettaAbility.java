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
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class VendettaAbility implements Ability, Listener{
	//made by emma
	int morepercent = 20;
	int lesspercent = 30;
	int range = 20;
	int delay = 30;
	ArrayList<Player> abilityActive = new ArrayList<>();
	HashMap<Player, LivingEntity> vendetta = new HashMap<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Vendetta";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You mark your target. Damage you deal to them",
							"is now increased by " + morepercent + "% and damage you",
							"deal to other players will be decreased by" + lesspercent + "%.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.NAME_TAG);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		if(abilityActive.contains(p))
		{
			
			return false; 
		}
		LivingEntity target1 = AbilityUtils.getTarget(p, range);
		
		if(!(target1 instanceof LivingEntity))
		{
			return false;
		}
		
		LivingEntity target = target1;
		abilityActive.add(p);
		Language.sendAbilityUseMessage(p, "You mark " + target.getName() + " as your target.", "Vendetta");
		target.getWorld().playSound(target.getLocation(), Sound.ITEM_ARMOR_EQUIP_ELYTRA, 1, 0.1F);
		target.getWorld().playSound(target.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 0.1F);
		
		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se.disappearWithOriginEntity = true;
		se.iterations = 5;
		se.particle = Particle.REDSTONE;
		se.color = Color.MAROON;
		se.radius = 0.8;
		se.particleOffsetY = (float) 0.5;
		se.particles = 3;
		se.yOffset = -0.8;
		se.speed = (float) 0;
		se.setEntity(target);
		se.start();

		vendetta.put(p, target);
		
		 new BukkitRunnable()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						if(abilityActive.contains(p))
						{
							abilityActive.remove(p);
						}
						
						if(vendetta.containsKey(p))
						{
							Language.sendAbilityUseMessage(p, target.getName() + " is no longer your target", "Vendetta");
							vendetta.remove(p);
						}
						
					}
			
				}.runTaskLater(VallendiaMinigame.getInstance(), delay*20);
				
		
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(abilityActive.contains(p))
		{
			abilityActive.remove(p);
		}
		
		if(vendetta.containsKey(p))
		{
			Language.sendAbilityUseMessage(p, vendetta.get(p).getName() + " is no longer your target", "Vendetta");
			vendetta.remove(p);
		}
		
	}
	
	@EventHandler
	public void onDamage (EntityDamageByEntityEvent e)
	{
        Player p = null;
        if(e.getDamager() instanceof Projectile)
        {
        	Projectile proj = (Projectile) e.getDamager();
        	
        	if(proj.getShooter() instanceof Player)
        	{
        		
        	p = (Player) proj.getShooter();
        	
        	}
        }else
        {
        	if(e.getDamager() instanceof Player)
        	{
        	p = (Player) e.getDamager();
        	}
        	if(!(e.getDamager() instanceof Player))
        	{
        		return;
        	}
        }
        
        if(!(e.getEntity() instanceof LivingEntity))
        {
        	return;
        }
		
		LivingEntity target = (LivingEntity) e.getEntity();
		
		if(vendetta.containsKey(p))
		{
			if(vendetta.get(p) == target)
			{
	            double damage = e.getFinalDamage();
	            double highpercent = Utils.getPercentHigherOrLower(morepercent, true);
	            double newdamage = damage*highpercent;
				e.setDamage(0);
				e.setDamage(DamageModifier.ARMOR, newdamage);
				
				SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
				se.disappearWithOriginEntity = true;
				se.iterations = 5;
				se.particle = Particle.REDSTONE;
				se.color = Color.MAROON;
				se.radius = 0.8;
				se.particleOffsetY = (float) 0.5;
				se.particles = 3;
				se.yOffset = -0.8;
				se.speed = (float) 0;
				se.setEntity(target);
				se.start();
				
				
			}else
			{
	            double damage = e.getFinalDamage();;
	            double lowpercent = Utils.getPercentHigherOrLower(lesspercent, false);
	            double newdamage = damage*lowpercent;
				e.setDamage(0);
				e.setDamage(DamageModifier.ARMOR, newdamage);
			}
		}
			
	}
	

}


















