package me.Nikewade.VallendiaMinigame.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class PlayerJoinEvents implements Listener{
	VallendiaMinigame Main;
	
	
	
	public PlayerJoinEvents(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		if(!p.hasPlayedBefore())
		{
			Main.kitmanager.giveKit(p, "starter");
		}
        Main.playerdatamanager.createFile(p);
        Main.sb.runScoreboard(p);
	}

}
