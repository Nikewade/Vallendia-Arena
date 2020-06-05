package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class RageAbility implements Ability, Listener{
	int ragetime = 30;
	ArrayList<Player> active = new ArrayList<>();
	ArrayList<Player> fatigued = new ArrayList<>();
	int temppercent = 25;
	int morepercent = 40;
	int lesspercent = 30;
	int defencepercent = 10;
	
	int force = 20;
	int yForce = 8;
	int maxYForce = 10;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Rage";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Go into a frenzied rage for "  + ragetime+ " seconds," , 
				"you gain " + morepercent + "% to damage, you become faster," ,
				"you take " + defencepercent + "% less damage, and",
				"you gain " + temppercent + "% extra max health.",
				"After your rage ends your body will become fatigued," ,
				"causing you to move slower and do " + lesspercent + "% less damage",
				"for the same amount of time.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		ItemStack item = Utils.getPotionItemStack(PotionType.STRENGTH, 1, false, false, "");
		return item;
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		if(active.contains(p))
		{
			return false;
		}
		active.add(p);
		p.sendTitle(ChatColor.DARK_RED + "" + ChatColor.BOLD + "YOU RAGE!", ChatColor.RED + " Your rage will last for " + ragetime + " seconds" , 20, 90, 50);	
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 2, (float) 0.4);
		
		Location location = p.getLocation().add(0.0D, -1.0F, 0.0D);
	    Vector t = location.toVector();

		for(Entity entity : AbilityUtils.getAoeTargets(p, p.getLocation(), 8, 8, 8))
		{
	          Vector e = entity.getLocation().toVector();
	          Vector v = e.subtract(t).normalize().multiply(force / 10.0D);
	          if (force != 0) {
	            v.setY(v.getY() * (yForce / 10.0D));
	          } else {
	            v.setY(yForce / 10.0D);
	          }
	          if (v.getY() > maxYForce / 10.0D) {
	              v.setY(maxYForce / 10.0D);
	            }
	          entity.setVelocity(v);
		}
		
		  new BukkitRunnable() {

	            @Override
	            public void run() {	
	            	if(!active.contains(p))
	            	{
	            		this.cancel();
	            	}
	        		int dist = -10000 * 10 + 1300000;
	        		Utils.sendWorldBorderPacket(p, dist, 200000D, 200000D, 0);
	            }
		    }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
		
		AbilityUtils.addPotionDuration(p, p, "Rage", PotionEffectType.SPEED, 1, ragetime*20);
		double oldHealth = p.getMaxHealth();
		double healthAdd = p.getMaxHealth() * Utils.getPercentHigherOrLower(temppercent, true);
		AbilityUtils.setMaxHealth(p, healthAdd, "Rage");	
		AbilityUtils.healEntity(p, (healthAdd - oldHealth));
		new BukkitRunnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(!active.contains(p))
				{
					this.cancel();
				}
				
                SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
              se.particle = Particle.CLOUD;
              se.iterations = 2;
              se.particles = 4;
              se.radius = 0.8;
              se.speed = (float) 0.1;
              se.setEntity(p);
              se.start();
				
			}
			
		}.runTaskTimer(VallendiaMinigame.getInstance(), 0, 10);
		new BukkitRunnable() {
            @Override
            public void run() {

            	if(active.contains(p))
            	{
            		active.remove(p);
            	
            	if(!fatigued.contains(p))
            	{
            		Language.sendAbilityUseMessage(p, "You feel fatigued", "Rage");
            		fatigued.add(p);
            		AbilityUtils.addPotionDuration(p, p, "Rage", PotionEffectType.SLOW, 1, ragetime*20);
            		
            		new BukkitRunnable()
            		{

						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(fatigued.contains(p))
							{
								Language.sendAbilityUseMessage(p, "You are no longer fatigued", "Rage");
								fatigued.remove(p);
							}
						}
            			
            		}.runTaskLater(VallendiaMinigame.getInstance(), ragetime*20);
            	}
            	
        			if(!(p.getMaxHealth() < oldHealth))
        			{
            			AbilityUtils.resetMaxHealth(p, "Rage");	
        			}
            	}
        		}
            
        }.runTaskLater(VallendiaMinigame.getInstance(), ragetime*20L);
        
		return true;
	}
	
	@EventHandler
	public void onDamage (EntityDamageByEntityEvent e)
	{
        Player p = null;
        if(e.getDamager() instanceof Projectile)
        {
        	Projectile proj = (Projectile) e.getDamager();
        	
        	if(!(proj.getShooter() instanceof Player))
        	{
        		return;
        	}
        	
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
        
        if(active.contains(p))
        {
            double damage = e.getFinalDamage();
            double multiplier = Utils.getPercentHigherOrLower(morepercent, true);
            double newdamage = damage*multiplier;
			e.setDamage(0);
			e.setDamage(DamageModifier.ARMOR, newdamage);
        }
        
        if(fatigued.contains(p))
        {
            double damage = e.getFinalDamage();
            double multiplier = Utils.getPercentHigherOrLower(lesspercent, false);
            double newdamage = damage*multiplier;
			e.setDamage(0);
			e.setDamage(DamageModifier.ARMOR, newdamage);
        }
	}
	
	@EventHandler
	public void onDamaged (EntityDamageEvent e)
	{
		if(active.contains(e.getEntity()))
		{
            double damage = e.getFinalDamage();
            double multiplier = Utils.getPercentHigherOrLower(defencepercent, false);
            double newdamage = damage*multiplier;
			e.setDamage(0);
			e.setDamage(DamageModifier.ARMOR, newdamage);
		}
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(active.contains(p))
		{
			active.remove(p);
			AbilityUtils.resetMaxHealth(p, "Rage");
		}
		if(fatigued.contains(p))
		{
			fatigued.remove(p);
		}
	}

}