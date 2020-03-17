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
	         double points = 0;
	         Player killer = p.getKiller();
	         int level = this.Main.levelmanager.getLevel(p);
	         int levelKilledBy = this.Main.levelmanager.getLevel(killer);
	         UUID uuid = killer.getUniqueId();
	         this.Main.playerdatamanager.addData(uuid, "Kills", 1);
	         this.Main.playerdatamanager.addData(uuid, "KillStreak", 1);
	         if (levelKilledBy >= level) {
	            points = (float) 10 * ((Math.pow((level), 2) / levelKilledBy));
	         }

	         if (levelKilledBy < level) {
	            points = (10 * level) + 40 * (level - levelKilledBy);
	         }

	         if (points <= 0) {
	            points = 0;
	         }

	         this.Main.playerdatamanager.addData(uuid, "Points", (int) points);
	         killer.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
	         killer.sendMessage("");
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Player killed: " + p.getName()));
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Kit: " + this.Main.kitmanager.getKit(p).getName(true)));
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Level: " + this.Main.levelmanager.getLevel(p)));
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Points gained: " + (int) points));
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