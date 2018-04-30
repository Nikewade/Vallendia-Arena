package me.Nikewade.VallendiaMinigame.Graphics;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

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
                Score score = objective.getScore(Utils.Colorate("&bKills: &8" + Main.playerdatamanager.getPlayerData(p.getUniqueId(), "Kills")));
                score.setScore(10);                      
                Score score2 = objective.getScore(Utils.Colorate("&bDeaths: &8" + Main.playerdatamanager.getPlayerData(p.getUniqueId(), "Deaths")));
                score2.setScore(9);
                Score score4 = objective.getScore(Utils.Colorate("&bPoints: &8" + Main.playerdatamanager.getPlayerData(p.getUniqueId(), "Points")));
                score4.setScore(8);
                p.setScoreboard(board);
            }
        },0, 20 * 1);
		
	}
	

}
