package me.Nikewade.VallendiaMinigame.Graphics;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_12_R1.ScoreboardTeam;

public class ScoreboardHandler {
	VallendiaMinigame Main;
	YamlConfiguration Config;
	
	 public ScoreboardHandler(VallendiaMinigame Main)
	  {
	    this.Main = Main;  
	    
	  }

	
	public void setupPlayerScoreboard(Player p)
	{
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		ScoreboardManager manager = Bukkit.getScoreboardManager();
        Objective objective = sb.registerNewObjective("test", "dummy");  
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(Utils.Colorate("&8&l✶ Vallendia Stats ✶"));
        sb.registerNewTeam("gray");
        sb.registerNewTeam("lightblue");
        sb.registerNewTeam("darkblue");
        sb.registerNewTeam("yellow");
        sb.registerNewTeam("red");
        p.setScoreboard(sb);
	}
	
	
	public void runNameTagUpdater() {
		
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main, new Runnable() {
            public void run() { 
                for(Player player : Bukkit.getOnlinePlayers()) 
                {
                    Scoreboard sb = player.getScoreboard();
                    Team gray = sb.getTeam("gray");
                    Team lightblue = sb.getTeam("lightblue");
                    Team darkblue = sb.getTeam("darkblue");
                    Team yellow = sb.getTeam("yellow");
                    Team red = sb.getTeam("red");

                    

                    for(Player p : Bukkit.getOnlinePlayers()) 
                    {

                    	if(!gray.hasPlayer(p))
                    	{
                            if(Main.levelmanager.getLevel(p) >= 1 && Main.levelmanager.getLevel(p) <= 4)
                            {
                            	gray.addPlayer(p);
                            }	
                    	}

                    	if(!lightblue.hasPlayer(p))
                    	{
                            if(Main.levelmanager.getLevel(p) >= 5 && Main.levelmanager.getLevel(p) <= 8)
                            {
                            	lightblue.addPlayer(p);
                            }	
                    	}
                    	
                        
                    	if(!darkblue.hasPlayer(p))
                    	{	 
                            if(Main.levelmanager.getLevel(p) >= 9 && Main.levelmanager.getLevel(p) <= 12)
                            {
                            	darkblue.addPlayer(p);
                            }	
                    	}
                        
                    	if(!yellow.hasPlayer(p))
                    	{
                            if(Main.levelmanager.getLevel(p) >= 13 && Main.levelmanager.getLevel(p) <= 16)
                            {
                            	yellow.addPlayer(p);
                            }	
                    	}
                        
                    	if(!red.hasPlayer(p))
                    	{
                            if(Main.levelmanager.getLevel(p) >= 17 && Main.levelmanager.getLevel(p) <= 20)
                            {
                            	red.addPlayer(p);
                            }	
                    	}
                    	
                      } 
                    gray.setPrefix(ChatColor.GRAY.toString());
                    lightblue.setPrefix(ChatColor.DARK_AQUA.toString());
                    darkblue.setPrefix(ChatColor.BLUE.toString());
                    yellow.setPrefix(ChatColor.GOLD.toString());
                    red.setPrefix(ChatColor.DARK_RED.toString());
                }
            }
        },0, 20 * 1);
	}
	
	
	public void runSidebarUpdater() {
		
		
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main, new Runnable() {
            public void run() { 
	            for(Player p : Bukkit.getOnlinePlayers()) {
	            	
	            	
	                for (String str : p.getScoreboard().getEntries()) {
	                        p.getScoreboard().resetScores(str);
	                    }
	            	
	                Scoreboard sb = p.getScoreboard();
	                Objective objective = sb.getObjective(DisplaySlot.SIDEBAR);

	            	
	                Score score0 = objective.getScore(Utils.Colorate("&3Kit: &8" + Main.kitmanager.getKit(p).getName(true)));
	                score0.setScore(11);  
	                Score score1 = objective.getScore(Utils.Colorate("&3Level: &8" + Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Level")));
	                score1.setScore(10); 
	                Score score2 = objective.getScore(Utils.Colorate("&3Kills: &8" + Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "KillStreak")));
	                score2.setScore(9);                      
	                Score score5 = objective.getScore(Utils.Colorate("&3Upgrades: &8" + Main.upgrademanager.getUpgradeTotal(p)));
	                score5.setScore(8);
	                Score score6 = objective.getScore(Utils.Colorate("&3Points: &8" + Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Points")));
	                score6.setScore(7);

	                p.setScoreboard(sb);
	            }
            }
        },0, 20 * 1);
		

	}
	
	


	

}
