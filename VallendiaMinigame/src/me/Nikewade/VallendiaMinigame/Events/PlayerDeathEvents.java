package me.Nikewade.VallendiaMinigame.Events;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

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
		//points / deaths
		Main.playerdatamanager.addData(p.getUniqueId(), "Deaths", 1);
		int pointsOnDeath = Main.getConfig().getInt("Points.Points-On-Death");
		//if(!(Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Points") < pointsOnDeath))
		//{
			//Main.playerdatamanager.subtractData(p.getUniqueId(), "Points", Main.getConfig().getInt("Points.Points-On-Death"));	
			//p.sendMessage(Utils.Colorate("&b&l[Vallendia] &bYou lost " + Main.getConfig().getInt("Points.Points-On-Death") +  " points!"));
		//}else Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", 0);
		Main.upgrademanager.resetUpgrades(p);
		Main.abilitymanager.resetAbilities(p);
		e.getDrops().clear();
		p.setLevel(0);
		p.setExp(0);
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e)
	{
		Player p = e.getPlayer();
		Main.kitmanager.giveKit(p, "starter");
		int pointsOnRespawn = Main.getConfig().getInt("Points.Points-On-Respawn");
		Main.shopmanager.addPoints(p, pointsOnRespawn);
		p.sendMessage(pointsOnRespawn + " points given for testing.");
	}
	
}
