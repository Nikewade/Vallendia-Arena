package me.Nikewade.VallendiaMinigame.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.alessiodp.parties.api.events.bukkit.unique.BukkitPartiesFriendlyFireBlockedEvent;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Graphics.ScoreboardHandler;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class PlayerTakeDamageEvent implements Listener{
	VallendiaMinigame Main;



	public PlayerTakeDamageEvent(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
	}
	
	
	
	
		@EventHandler
		public void onDamageByEntity(EntityDamageByEntityEvent e)
		{
			if(!(e.getEntity() instanceof Player))
			{
				return;
			}
			Player p = (Player) e.getEntity();
			if((e.getDamager() instanceof Player))
			{
				Player dp = (Player) e.getDamager();
				//check if can damage
				if(e.isCancelled())
				{
					return;
				}
				
				
				//In party so dont update health
	        	if(AbilityUtils.partyCheck(p, dp))
	        	{
	        		e.setDamage(0);
	        		e.setCancelled(true);
	        		return;
	        	}	
			}
        	
        	if(AbilityUtils.explosivesEntities.containsKey(e.getDamager()))
        	{
    			ScoreboardHandler.updateHealth((Player)e.getEntity(), 0, 
    					AbilityUtils.explosivesEntities.get(e.getDamager()));
    			return;
        	}

			ScoreboardHandler.updateHealth((Player)e.getEntity(), 0, e.getFinalDamage());
		}

		@EventHandler
		public void onDamage(EntityDamageEvent e)
		{
			if(!(e.getEntity() instanceof Player))
			{
				return;
			}
			//Deal with this stuff in seperate on eentityhit event so we can use parties
			if(e.getCause() == DamageCause.ENTITY_ATTACK || e.getCause() == DamageCause.ENTITY_EXPLOSION
					|| e.getCause() == DamageCause.ENTITY_SWEEP_ATTACK)
			{
				return;
			}
			Player p = (Player) e.getEntity();

			ScoreboardHandler.updateHealth((Player)e.getEntity(), 0, e.getFinalDamage());
			
			
		}

}
