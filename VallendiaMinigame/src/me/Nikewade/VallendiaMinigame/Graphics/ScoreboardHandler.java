package me.Nikewade.VallendiaMinigame.Graphics;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class ScoreboardHandler {
	VallendiaMinigame Main;
	ScoreboardManager manager = Bukkit.getScoreboardManager();
    final Scoreboard board = manager.getNewScoreboard();
    final Objective objective = board.registerNewObjective("test", "dummy");
	YamlConfiguration Config;
	
	 public ScoreboardHandler(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	  }
	
	public void runScoreboard(Player p) {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main, new Runnable() {
            public void run() {        
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                final Scoreboard board = manager.getNewScoreboard();
                final Objective objective = board.registerNewObjective("test", "dummy");        
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName(Utils.Colorate("&8&l✶ Vallendia Stats ✶"));
                Score score0 = objective.getScore(Utils.Colorate("&bKit: &8" + Main.kitmanager.getKit(p).getName(true)));
                score0.setScore(11);  
                Score score1 = objective.getScore(Utils.Colorate("&bLevel: &8" + Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Level")));
                score1.setScore(10); 
                Score score2 = objective.getScore(Utils.Colorate("&bKills: &8" + Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Kills")));
                score2.setScore(9);                      
                Score score3 = objective.getScore(Utils.Colorate("&bDeaths: &8" + Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Deaths")));
                score3.setScore(8);
                Score score4 = objective.getScore(Utils.Colorate("&bUpgrades: &8" + Main.upgrademanager.getUpgradeTotal(p)));
                score4.setScore(7);
                Score score5 = objective.getScore(Utils.Colorate("&bPoints: &8" + Main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Points")));
                score5.setScore(6);
               
                p.setScoreboard(board);
            }
        },0, 20 * 1);
        
        
		
	}
	

}
