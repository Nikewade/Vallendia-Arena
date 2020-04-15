package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class NightCrawlerAbility implements Ability, Listener{
	int percent = 15;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Night Crawler";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("deal " + percent + "% extra damage at night");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack (Material.WATCH);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(day(p))
		{
			Language.sendAbilityUseMessage(p, "It is currently day time.", "Night Crawler");
		}else
		{
			Language.sendAbilityUseMessage(p, "It is currently night time.", "Night Crawler");
		}

		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
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
			if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, this.getName()))
			{
				if(!day(p))
				{
					
		            double damage = e.getFinalDamage();
		            double highpercent = Utils.getPercentHigherOrLower(percent, true);
		            double newdamage = damage*highpercent;
					e.setDamage(0);
					e.setDamage(DamageModifier.ARMOR, newdamage);
					
					SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
					se.disappearWithOriginEntity = true;
					se.iterations = 5;
					se.particle = Particle.REDSTONE;
					se.color = Color.BLACK;
					se.radius = 0.8;
					se.particleOffsetY = (float) 0.5;
					se.particles = 1;
					se.yOffset = -0.8;
					se.speed = (float) 0;
					se.setEntity(p);
					se.start();
					
				}
			}
			
		}
		
	
	public boolean day(Player p) {

	    long time = p.getWorld().getTime();

	    return time < 12300 || time > 23850;
	}

}









