package me.Nikewade.VallendiaMinigame.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class PlayerExpEvents implements Listener{
	VallendiaMinigame Main;
	
	
	
	public PlayerExpEvents(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
	}

	@EventHandler
	public void expChange​ (PlayerExpChangeEvent e)
	{
		e.setAmount(0);
	}
	
}
