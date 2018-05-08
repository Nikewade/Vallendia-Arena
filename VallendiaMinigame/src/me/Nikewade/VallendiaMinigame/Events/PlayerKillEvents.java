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
				Player killer = p.getKiller();
				UUID uuid = killer.getUniqueId();
				Main.playerdatamanager.addData(uuid, "Kills", 1);
				Main.playerdatamanager.addData(uuid, "Points", Main.getConfig().getInt("Points.Points-On-Kill"));
				killer.sendMessage(Utils.Colorate("&b&l[Vallendia] &bYou gained " + Main.getConfig().getInt("Points.Points-On-Kill") +  " points!"));
			}
		}
	}
}
