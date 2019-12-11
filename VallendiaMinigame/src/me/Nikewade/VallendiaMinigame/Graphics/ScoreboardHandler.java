package me.Nikewade.VallendiaMinigame.Graphics;

import java.lang.reflect.Field;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.mojang.authlib.GameProfile;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.CScoreboard;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import net.minecraft.server.v1_12_R1.EntityPlayer;

public class ScoreboardHandler{
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
	            //Had to add a space at the end for some reason Archer shows up weird without it?
	            if(ScoreboardHandler.this.Main.kitmanager.getKit(p).getName(false).equalsIgnoreCase("archer"))
	            {
		            row1.setMessage(Utils.Colorate("&3Kit: ") + ScoreboardHandler.this.Main.kitmanager.getKit(p).getName(true) + " ");	
	            }else {	            row1.setMessage(Utils.Colorate("&3Kit: ") + ScoreboardHandler.this.Main.kitmanager.getKit(p).getName(true));}
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
	        Objective objective = sb.registerNewObjective("health", "dummy");
	        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
	        objective.setDisplayName("%");
	        for(Player all : VallendiaMinigame.getInstance().getServer().getOnlinePlayers())
	        {
	        	ScoreboardHandler.updateHealth(all, 0, 0);
	        }
	   }
	   
	   
	   
	   public static void updateMaxHealth(Player p)
	   {
	        for(Player all : VallendiaMinigame.getInstance().getServer().getOnlinePlayers())
	        {
	      		Scoreboard sb = all.getScoreboard();
				Objective obj = sb.getObjective(DisplaySlot.BELOW_NAME);
	   			Score score = obj.getScore(p.getName());
	   			float health = Math.round(((float) (p.getHealth()) / (float) p.getMaxHealth()) * 100.0F);
	   			if(health <= 0)
	   			{
	   				health = 1;
	   			}
	   			if(health > 100)
	   			{
	   				health = 100;
	   			}
	   	        score.setScore((int) health);
	        } 
	   }
	   
	   public static void updateHealth(Player p, double add, double d)
	   {
	        for(Player all : VallendiaMinigame.getInstance().getServer().getOnlinePlayers())
	        {
	      		Scoreboard sb = all.getScoreboard();
				Objective obj = sb.getObjective(DisplaySlot.BELOW_NAME);
	   			Score score = obj.getScore(p.getName());
	   			float health = Math.round(((float) ((p.getHealth() - d) + add) / (float) p.getMaxHealth()) * 100.0F);
	   			if(health <= 0)
	   			{
	   				health = 1;
	   			}
	   			if(health > 100)
	   			{
	   				health = 100;
	   			}
	   	        score.setScore((int) health);
	        }
	   }

	   public void runNameTagUpdater() {
		   
		   
			BukkitTask task = new BukkitRunnable() {
	            @Override
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
	        }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 20L);
	        
		   
		   
	   }

	   public void runSidebarUpdater() {
	   }
	}
