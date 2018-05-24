package me.Nikewade.VallendiaMinigame.Events;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class PlayerFoodEvents implements Listener {
	VallendiaMinigame Main;
	int task;
	ArrayList<Player> healing = new ArrayList<>();
	ArrayList<Player> inCombat = new ArrayList<>();
	
	
	
	public PlayerFoodEvents(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			e.setCancelled(true);
			p.setFoodLevel(19);
		}
	}
	
	
	
	@EventHandler
	public void onEat(PlayerItemConsumeEvent e)
	{
		
 		if(e.getItem().getType() == Material.COOKED_BEEF)
		{
			Player p = e.getPlayer();
			int healAmount = Main.getConfig().getInt("Options.Food-Heal");
			
			
			
			if(healing.contains(p))
			{
				e.setCancelled(true);
				return;
			}
			
			if(p.getHealth() == p.getMaxHealth())
			{
				e.setCancelled(true);
				p.sendMessage(Utils.Colorate("&cYou are already at full health!"));
				return;
			}
			
			if(!inCombat.contains(p))
			{
            	p.sendMessage(Utils.Colorate("&cYou are regenerating health."));
	            healing.add(p);	
				new BukkitRunnable() {
				     @Override
				     public void run() {	
						    	 	if(!healing.contains(p))
				                    {
							            cancel();
							            return;
				                    }
				                    if(p.getHealth() == p.getMaxHealth() || (p.getHealth() + healAmount > p.getMaxHealth()))
				                    {
				                    	healing.remove(p);
				                    	p.setHealth(p.getMaxHealth());
				                    	p.sendMessage(Utils.Colorate("&cYou have stopped regenerating health."));
				                    	cancel();
				                    	return;
				                    }
				                    p.setHealth(p.getHealth() + healAmount);
				                    p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 0.4, 0.4), 5);
				                    p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 0.4, 0), 5);
				                    p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0.4, 0.4, 0), 5); 
				     }
				}.runTaskTimer(Main, 0, 40);
				
			}else
			{
				e.setCancelled(true);
				p.sendMessage(Utils.Colorate("&cYou can't regenerate in combat!"));
				return;
			}
			
			

			
			
		}
	}
	
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onHealthRegen(EntityRegainHealthEvent e)
	{
		if(e.getEntity() instanceof Player)
		{	
			if(e.getRegainReason() == RegainReason.SATIATED)
			{
				e.setCancelled(true);
			}	
		}
		
	}
	
	@EventHandler
	public void onDamaged(EntityDamageEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			if(p.getFoodLevel() == 20)
			{
				p.setFoodLevel(19);
			}	
			
			if(healing.contains(p))
			{
				healing.remove(p);
            	p.sendMessage(Utils.Colorate("&cYou have stopped regenerating health."));
			}
	
		}
	}
	
	
	@EventHandler 
	public void onPlayerDamage(EntityDamageByEntityEvent e)
	{
		//combattag
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player)
		{
			Player p = (Player) e.getDamager();
			Player target = (Player) e.getEntity();	
			
			if(healing.contains(p))
			{
            	healing.remove(p);
            	p.sendMessage(Utils.Colorate("&cYou have stopped regenerating health."));
			}
			
			if(!inCombat.contains(p))
			{
				inCombat.add(p);
				p.sendMessage(Utils.Colorate("&4&l*In combat*"));	
				new BukkitRunnable() {
					@Override
					public void run() {	
			    	 
						inCombat.remove(p);
						p.sendMessage(Utils.Colorate("&4&l*Out of combat*"));
					}
				}.runTaskLater(Main, 15 * 20);
			}
			
			
			if(!inCombat.contains(target))
			{
				inCombat.add(target);
				target.sendMessage(Utils.Colorate("&4&l*In combat*"));	
				new BukkitRunnable() {
					@Override
					public void run() {	
			    	 
						inCombat.remove(target);
						target.sendMessage(Utils.Colorate("&4&l*Out of combat*"));
					}
				}.runTaskLater(Main, 15 * 20);
			}
		}else
			if(e.getEntity() instanceof Player && e.getDamager() instanceof Projectile)
			{
				Projectile proj = (Projectile) e.getDamager();
				
				if(proj.getShooter() instanceof Player)
				{
					Player p = (Player) proj.getShooter();
					Player target = (Player) e.getEntity();
					
					if(healing.contains(p))
					{
		            	healing.remove(p);
		            	p.sendMessage(Utils.Colorate("&cYou have stopped regenerating health."));
					}
					
					if(!inCombat.contains(p))
					{
						inCombat.add(p);
						p.sendMessage(Utils.Colorate("&4&l*In combat*"));	
						new BukkitRunnable() {
							@Override
							public void run() {	
					    	 
								inCombat.remove(p);
								p.sendMessage(Utils.Colorate("&4&l*Out of combat*"));
							}
						}.runTaskLater(Main, 15 * 20);
					}
					
					
					if(!inCombat.contains(target))
					{
						inCombat.add(target);
						target.sendMessage(Utils.Colorate("&4&l*In combat*"));	
						new BukkitRunnable() {
							@Override
							public void run() {	
					    	 
								inCombat.remove(target);
								target.sendMessage(Utils.Colorate("&4&l*Out of combat*"));
							}
						}.runTaskLater(Main, 15 * 20);
					}
					
				}
				
			}
		
		
		
		
	}
	
}