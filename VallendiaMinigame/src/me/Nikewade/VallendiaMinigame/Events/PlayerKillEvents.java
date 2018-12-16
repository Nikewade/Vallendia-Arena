package me.Nikewade.VallendiaMinigame.Events;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class PlayerKillEvents implements Listener{
	VallendiaMinigame Main;
	
	
	
	public PlayerKillEvents(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
	}

	
	@EventHandler
	public void onPlayerKill(PlayerDeathEvent e)
	{
		Player p = e.getEntity();
		if(p.getKiller() != null)
		{
			if(p.getKiller() instanceof Player && p.getKiller() != p)
			{
				int points = 0;
				int M = Main.levelmanager.getParameter("m");
				int R = Main.levelmanager.getParameter("r");
				int P = Main.levelmanager.getParameter("p");
				int L = Main.levelmanager.getParameter("l");
				Player killer = p.getKiller();
				int levelKilled = Main.levelmanager.getLevel(p);
				int level = Main.levelmanager.getLevel(killer);
				UUID uuid = killer.getUniqueId();
				Main.playerdatamanager.addData(uuid, "Kills", 1);
				if (levelKilled >= level)
				{
					 points = -(M-R) * level + M * levelKilled + P - R;
				}
				
				if (levelKilled < level)
				{
					points = -(levelKilled * R + P - 1) * (level - levelKilled) / L + levelKilled * R + P - R;
				}
				
				if(points <1)
				{
					points = 1;
				}
				Main.playerdatamanager.addData(uuid, "Points", points);
				Utils.sendVallendiaMessage(killer, null, null, "You killed " + p.getName(), "Level: " + Main.levelmanager.getLevel(p), "Points gained: " + points, null);
			}
		}
	}
}
