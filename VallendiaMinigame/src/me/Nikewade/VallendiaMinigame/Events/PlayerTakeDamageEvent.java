package me.Nikewade.VallendiaMinigame.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Graphics.ScoreboardHandler;

public class PlayerTakeDamageEvent implements Listener{
	VallendiaMinigame Main;



	public PlayerTakeDamageEvent(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
	}

		@EventHandler
		public void onDamage(EntityDamageEvent e)
		{
			if(!(e.getEntity() instanceof Player))
			{
				return;
			}
			Player p = (Player) e.getEntity();
			ScoreboardHandler.updateHealth((Player)e.getEntity(), 0, e.getFinalDamage());
			
			
		}

}
