package me.Nikewade.VallendiaMinigame.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.RootAbility;
import me.Nikewade.VallendiaMinigame.Upgrades.RegenUpgrade;

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
		RegenUpgrade.addTimer(p);
		if(!p.hasPlayedBefore())
		{
			Main.kitmanager.giveKit(p, "starter");
			p.setExp(0);
			p.setLevel(1);
		}
        Main.playerdatamanager.createFile(p);
       // Main.sb.runScoreboard(p);
        Main.sb.setupPlayerScoreboard(p);
	}
	
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLeave(PlayerQuitEvent e)
	{
		RootAbility.removeLists(e.getPlayer());
	}

}
