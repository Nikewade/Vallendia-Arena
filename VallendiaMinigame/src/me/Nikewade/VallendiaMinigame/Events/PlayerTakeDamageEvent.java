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
			if(!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player))
			{
				return;
			}
			Player p = (Player) e.getEntity();
			Player dp = (Player) e.getDamager();
			
			//In party so dont update health
        	if(AbilityUtils.partyCheck(p, dp))
        	{
        		e.setCancelled(true);
        		return;
        	}
        	
        	if(AbilityUtils.explosivesEntities.containsKey(dp))
        	{
    			ScoreboardHandler.updateHealth((Player)e.getEntity(), 0, 
    					AbilityUtils.explosivesEntities.get(dp));
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
