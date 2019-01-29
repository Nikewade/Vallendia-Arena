package me.Nikewade.VallendiaMinigame.Events;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.SirBlobman.combatlogx.CombatLogX;
import com.SirBlobman.combatlogx.event.PlayerTagEvent;
import com.SirBlobman.combatlogx.utility.CombatUtil;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class PlayerFoodEvents implements Listener {
	VallendiaMinigame Main;
	int task;
	ArrayList<Player> healing = new ArrayList<>();
	
	
	
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
		int healAmount = Main.getConfig().getInt("Options.food.food-heal");
		
 		if(e.getItem().getType() == Material.COOKED_BEEF || e.getItem().getType() == Material.GRILLED_PORK || 
 				e.getItem().getType() == Material.COOKED_CHICKEN || e.getItem().getType() == Material.BREAD || 
 				e.getItem().getType() == Material.COOKED_FISH)
		{
			Player p = e.getPlayer();
			
			if(healing.contains(p))
			{
				e.setCancelled(true);
				return;
			}
			
			if(p.getHealth() == p.getMaxHealth())
			{
				e.setCancelled(true);
				Language.sendDefaultMessage(p, "You are already at full health!");
				return;
			}
			
			if(!CombatUtil.isInCombat(p))
			{
            	Language.sendDefaultMessage(p, "You are regenerating health.");
	            healing.add(p);	
	            double startHealth = p.getHealth();
				new BukkitRunnable() {
				     @Override
				     public void run() {	
				 			int healPercent = 0;
					        switch (e.getItem().getType()) {
				            case COOKED_BEEF:  healPercent = Main.getConfig().getInt("Options.food.steak");
				                     break;
				            case GRILLED_PORK:  healPercent = Main.getConfig().getInt("Options.food.pork");
				                     break;
				            case COOKED_CHICKEN:  healPercent = Main.getConfig().getInt("Options.food.chicken");
				                     break;
				            case BREAD:  healPercent = Main.getConfig().getInt("Options.food.bread");
				                     break;
				            case COOKED_FISH:  healPercent = Main.getConfig().getInt("Options.food.fish");
				                     break;
							default:
								healPercent = 0;
								break;
				        }
				    	 
						    	 	if(!healing.contains(p))
				                    {
							            cancel();
							            return;
				                    }
						    	 	
						    	 	
				                    if(p.getHealth() == p.getMaxHealth() || (p.getHealth() + healAmount > p.getMaxHealth()))
				                    {
				                    	healing.remove(p);
				                    	p.setHealth(p.getMaxHealth());
				                    	Language.sendDefaultMessage(p, "You have stopped regenerating health.");
				                    	cancel();
				                    	return;
				                    }
						    	 	
						    	 	if(p.getHealth() >= startHealth + (p.getMaxHealth() * healPercent) / 100)
						    	 	{
				                    	healing.remove(p);
				                    	Language.sendDefaultMessage(p, "You have stopped regenerating health.");
						    	 		cancel();
						    	 		return;
						    	 	}
						    	 	
				                    p.setHealth(p.getHealth() + healAmount);
				                    p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 0.4, 0.4), 5);
				                    p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 0.4, 0), 5);
				                    p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0.4, 0.4, 0), 5); 
				     }
				}.runTaskTimer(Main, 0, (2*20));
				
			}else
			{
				e.setCancelled(true);
            	Language.sendDefaultMessage(p, "You can't regenerate in combat!");
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
            	Language.sendDefaultMessage(p, "You have stopped regenerating health.");
			}
	
		}
	}
	
	
	@EventHandler
	public void onCombat(PlayerTagEvent e)
	{
		Player p = e.getPlayer();
		if(healing.contains(p))
		{
        	healing.remove(p);
        	Language.sendDefaultMessage(p, "You have stopped regenerating health.");
		}
	}
	

	
}
