package me.Nikewade.VallendiaMinigame.Graphics;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.CScoreboard;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class ScoreboardHandler {
	   VallendiaMinigame Main;
	   YamlConfiguration Config;

	   public ScoreboardHandler(VallendiaMinigame Main) {
	      this.Main = Main;
	   }

	   public void setupPlayerScoreboard(final Player p) {
	      final CScoreboard scoreboard = new CScoreboard("name", "criterion", "title");
	      final CScoreboard.Row row1 = scoreboard.addRow("1");
	      final CScoreboard.Row row2 = scoreboard.addRow("2");
	      final CScoreboard.Row row3 = scoreboard.addRow("3");
	      final CScoreboard.Row row4 = scoreboard.addRow("4");
	      final CScoreboard.Row row5 = scoreboard.addRow("5");
	      scoreboard.finish();
	      scoreboard.display(p);
	      (new BukkitRunnable() {
	         public void run() {
	            scoreboard.setTitle(Utils.Colorate("&8&l✶ Vallendia Stats ✶"));
	            row1.setMessage(Utils.Colorate("&3Kit: " + ScoreboardHandler.this.Main.kitmanager.getKit(p).getName(true)));
	            row2.setMessage(Utils.Colorate("&3Level: &8" + ScoreboardHandler.this.Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Level")));
	            row3.setMessage(Utils.Colorate("&3Kills: &8" + ScoreboardHandler.this.Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "KillStreak")));
	            row4.setMessage(Utils.Colorate("&3Upgrades: &8" + ScoreboardHandler.this.Main.upgrademanager.getUpgradeTotal(p)));
	            row5.setMessage(Utils.Colorate("&3Points: &8" + ScoreboardHandler.this.Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Points")));
	         }
	      }).runTaskTimer(VallendiaMinigame.getInstance(), 20L, 20L);
	      Scoreboard sb = p.getScoreboard();
	      sb.registerNewTeam("gray");
	      sb.registerNewTeam("lightblue");
	      sb.registerNewTeam("darkblue");
	      sb.registerNewTeam("yellow");
	      sb.registerNewTeam("red");
	   }

	   public void runNameTagUpdater() {
	      Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this.Main, new Runnable() {
	         public void run() {
	            Iterator var2 = Bukkit.getOnlinePlayers().iterator();

	            while(var2.hasNext()) {
	               Player player = (Player)var2.next();
	               Scoreboard sb = player.getScoreboard();
	               Team gray = sb.getTeam("gray");
	               Team lightblue = sb.getTeam("lightblue");
	               Team darkblue = sb.getTeam("darkblue");
	               Team yellow = sb.getTeam("yellow");
	               Team red = sb.getTeam("red");
	               Iterator var10 = Bukkit.getOnlinePlayers().iterator();

	               while(var10.hasNext()) {
	                  Player p = (Player)var10.next();
	                  if (!gray.hasPlayer(p) && ScoreboardHandler.this.Main.levelmanager.getLevel(p) >= 1 && ScoreboardHandler.this.Main.levelmanager.getLevel(p) <= 4) {
	                     gray.addPlayer(p);
	                  }

	                  if (!lightblue.hasPlayer(p) && ScoreboardHandler.this.Main.levelmanager.getLevel(p) >= 5 && ScoreboardHandler.this.Main.levelmanager.getLevel(p) <= 8) {
	                     lightblue.addPlayer(p);
	                  }

	                  if (!darkblue.hasPlayer(p) && ScoreboardHandler.this.Main.levelmanager.getLevel(p) >= 9 && ScoreboardHandler.this.Main.levelmanager.getLevel(p) <= 12) {
	                     darkblue.addPlayer(p);
	                  }

	                  if (!yellow.hasPlayer(p) && ScoreboardHandler.this.Main.levelmanager.getLevel(p) >= 13 && ScoreboardHandler.this.Main.levelmanager.getLevel(p) <= 16) {
	                     yellow.addPlayer(p);
	                  }

	                  if (!red.hasPlayer(p) && ScoreboardHandler.this.Main.levelmanager.getLevel(p) >= 17 && ScoreboardHandler.this.Main.levelmanager.getLevel(p) <= 20) {
	                     red.addPlayer(p);
	                  }
	               }

	               gray.setPrefix(ChatColor.GRAY.toString());
	               lightblue.setPrefix(ChatColor.DARK_AQUA.toString());
	               darkblue.setPrefix(ChatColor.BLUE.toString());
	               yellow.setPrefix(ChatColor.GOLD.toString());
	               red.setPrefix(ChatColor.DARK_RED.toString());
	            }

	         }
	      }, 0L, 20L);
	   }

	   public void runSidebarUpdater() {
	   }
	}
