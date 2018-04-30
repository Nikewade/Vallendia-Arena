package me.Nikewade.VallendiaMinigame.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class PlayerDeathEvents implements Listener {
	VallendiaMinigame Main;
	
	
	
	public PlayerDeathEvents(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
	}
	
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e)
	{
		Player p = e.getEntity();
		Main.playerdatamanager.addData(p.getUniqueId(), "Deaths", 1);
		
		if(Main.playerdatamanager.getPlayerData(p.getUniqueId(), "Points") != 0)
		{
			Main.playerdatamanager.subtractData(p.getUniqueId(), "Points", Main.getConfig().getInt("Points-On-Death"));	
			p.sendMessage(Utils.Colorate("&b&l[Vallendia] &bYou lost " + Main.getConfig().getInt("Points-On-Death") +  " points!"));
		}
	}
}
