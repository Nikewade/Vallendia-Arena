package me.Nikewade.VallendiaMinigame.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.BandageAbility;
import me.Nikewade.VallendiaMinigame.Abilities.EquipBowAbility;
import me.Nikewade.VallendiaMinigame.Abilities.RageAbility;
import me.Nikewade.VallendiaMinigame.Abilities.RootAbility;
import me.Nikewade.VallendiaMinigame.Abilities.SurvivalistAbility;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
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
		int pointsCarried = Main.shopmanager.getPoints(p);
		int pointsSpent = Main.shopmanager.getPointsSpent(p);
		int level = Main.levelmanager.getLevel(p);
		Main.upgrademanager.resetUpgradesOnDeath(p);
		Main.abilitymanager.resetAbilities(p);
		RageAbility.onDie(p);
		EquipBowAbility.onDie(p);
		e.getDrops().clear();
		p.setLevel(0);
		p.setExp(0);
		RootAbility.removeLists(p);
		BandageAbility.removeBandage(p);
		SurvivalistAbility.removeEnabled(p);
		Main.playerdatamanager.addData(p.getUniqueId(), "Deaths", 1);
		AbilityUtils.removeCast(p);
		AbilityUtils.removeSoftCast(p);
		
		
		//death effects
		if(Main.levelmanager.getLevel(p) >= 10)
		{
			AbilityUtils.explode(p.getLocation(), p, 3, 0, true, true, true);
		}
		
		if(Main.levelmanager.getLevel(p) >= 15)
		{
			p.getWorld().strikeLightningEffect(p.getLocation());
			p.getWorld().setStorm(true);
			p.getWorld().setWeatherDuration(20 * 20);
			p.getWorld().setThundering(true);
			p.getWorld().setThunderDuration(20 * 20);
		}
		
		
		
		if(p.getKiller() != null && p.getKiller() instanceof Player && p.getKiller() != p)
		{
			if(pointsCarried + pointsSpent < 0)
			{
				Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", pointsCarried + pointsSpent);
				p.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
				p.sendMessage("");
				Language.sendCentredMessage(p, Utils.Colorate("&c&lYou died"));
				Language.sendCentredMessage(p, Utils.Colorate("&3Kit: " + Main.kitmanager.getKit(p).getName(true)));
				Language.sendCentredMessage(p, Utils.Colorate("&3Level: " + Main.levelmanager.getLevel(p)));
				Language.sendCentredMessage(p, Utils.Colorate("&3Points lost: " + (pointsCarried - Main.shopmanager.getPoints(p))));
				p.sendMessage("");
				p.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));
				return;
			}
				int N = Main.levelmanager.getParameter("n");
				Player killer = p.getKiller();
				int levelKilledBy = Main.levelmanager.getLevel(killer);
				int points = (int) ((pointsCarried + pointsSpent) * (1/Math.pow(level, 0.35)) * 
				(((-0.45 * (level - levelKilledBy - N))/Math.sqrt(Math.pow((level - levelKilledBy - N), 2) + Math.pow(N, 2)))+.55));
				Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", points);
				Main.playerdatamanager.editIntData(p.getUniqueId(), "PointsSpent", 0);
				p.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
				p.sendMessage("");
				Language.sendCentredMessage(p, Utils.Colorate("&c&lYou died"));
				Language.sendCentredMessage(p, Utils.Colorate("&3Kit: " + Main.kitmanager.getKit(p).getName(true)));
				Language.sendCentredMessage(p, Utils.Colorate("&3Level: " + Main.levelmanager.getLevel(p)));
				Language.sendCentredMessage(p, Utils.Colorate("&3Points lost: " + (pointsCarried - Main.shopmanager.getPoints(p))));
				p.sendMessage("");
				p.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));
				return;
		}
		
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", (int) ((pointsCarried + pointsSpent) * (1/Math.pow(level, 0.35))));
		Main.playerdatamanager.editIntData(p.getUniqueId(), "PointsSpent", 0);
		
		p.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
		p.sendMessage("");
		Language.sendCentredMessage(p, Utils.Colorate("&c&lYou died"));
		Language.sendCentredMessage(p, Utils.Colorate("&3Kit: " + Main.kitmanager.getKit(p).getName(true)));
		Language.sendCentredMessage(p, Utils.Colorate("&3Level: " + Main.levelmanager.getLevel(p)));
		Language.sendCentredMessage(p, Utils.Colorate("&3Points lost: " + (pointsCarried - Main.shopmanager.getPoints(p))));
		p.sendMessage("");
		p.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e)
	{
		Player p = e.getPlayer();
		Main.kitmanager.giveRespawnKit(p, "starter");
	}
	
	
}
