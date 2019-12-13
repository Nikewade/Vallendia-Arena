package me.Nikewade.VallendiaMinigame.Events;

import java.util.UUID;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class PlayerKillEvents implements Listener {
	   VallendiaMinigame Main;

	   public PlayerKillEvents(VallendiaMinigame Main) {
	      this.Main = Main;
	      Main.getServer().getPluginManager().registerEvents(this, Main);
	   }

	   @EventHandler
	   public void onPlayerKill(PlayerDeathEvent e) {
	      Player p = e.getEntity();
	      if (p.getKiller() != null && p.getKiller() instanceof Player && p.getKiller() != p) {
	         int points = 0;
	         int M = this.Main.levelmanager.getParameter("m");
	         int R = this.Main.levelmanager.getParameter("r");
	         int P = this.Main.levelmanager.getParameter("p");
	         int L = this.Main.levelmanager.getParameter("l");
	         Player killer = p.getKiller();
	         int levelKilled = this.Main.levelmanager.getLevel(p);
	         int level = this.Main.levelmanager.getLevel(killer);
	         UUID uuid = killer.getUniqueId();
	         this.Main.playerdatamanager.addData(uuid, "Kills", 1);
	         this.Main.playerdatamanager.addData(uuid, "KillStreak", 1);
	         if (levelKilled >= level) {
	            points = -(M - R) * level + M * levelKilled + P - R;
	         }

	         if (levelKilled < level) {
	            points = -(levelKilled * R + P - 1) * (level - levelKilled) / L + levelKilled * R + P - R;
	         }

	         if (points < 1) {
	            points = 1;
	         }

	         this.Main.playerdatamanager.addData(uuid, "Points", points);
	         killer.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
	         killer.sendMessage("");
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Player killed: " + p.getName()));
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Kit: " + this.Main.kitmanager.getKit(p).getName(true)));
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Level: " + this.Main.levelmanager.getLevel(p)));
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Points gained: " + points));
	         killer.sendMessage("");
	         killer.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));
	      }

	   }
	   
	   
	   @EventHandler
	   public void killMobEvent(EntityDeathEvent e)
	   {
		   if(e.getEntity() instanceof Creature)
		   {
			   e.setDroppedExp(0);
		   }
	   }
	}