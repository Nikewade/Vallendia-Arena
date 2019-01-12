package me.Nikewade.VallendiaMinigame.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.RageAbility;
import me.Nikewade.VallendiaMinigame.Abilities.RootAbility;

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
		int pointsCarried = Main.shopmanager.getPoints(p);
		int pointsSpent = Main.shopmanager.getPointsSpent(p);
		int level = Main.levelmanager.getLevel(p);
		Main.upgrademanager.resetUpgradesOnDeath(p);
		Main.abilitymanager.resetAbilities(p);
		RageAbility.onDie(p);
		e.getDrops().clear();
		p.setLevel(0);
		p.setExp(0);
		RootAbility.removeLists(p);
		Main.playerdatamanager.addData(p.getUniqueId(), "Deaths", 1);
		if(p.getKiller() != null && p.getKiller() instanceof Player && p.getKiller() != p)
		{
			if(pointsCarried + pointsSpent < 0)
			{
				Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", pointsCarried + pointsSpent);
				return;
			}
				int N = Main.levelmanager.getParameter("n");
				Player killer = p.getKiller();
				int levelKilledBy = Main.levelmanager.getLevel(killer);
				int points = (int) ((pointsCarried + pointsSpent) * (1/Math.pow(level, 0.35)) * 
				(((-0.45 * (level - levelKilledBy - N))/Math.sqrt(Math.pow((level - levelKilledBy - N), 2) + Math.pow(N, 2)))+.55));
				Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", points);
				Main.playerdatamanager.editIntData(p.getUniqueId(), "PointsSpent", 0);
				return;
		}
		
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", (int) ((pointsCarried + pointsSpent) * (1/Math.pow(level, 0.35))));
		Main.playerdatamanager.editIntData(p.getUniqueId(), "PointsSpent", 0);
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e)
	{
		Player p = e.getPlayer();
		Main.kitmanager.giveRespawnKit(p, "starter");
	}
	
}
