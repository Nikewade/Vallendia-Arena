package me.Nikewade.VallendiaMinigame.Events;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class ScoreboardEvents implements Listener {
	VallendiaMinigame Main;
	ScoreboardManager manager = Bukkit.getScoreboardManager();
	Scoreboard board = manager.getNewScoreboard();
	YamlConfiguration Config;
	
	
	
	public ScoreboardEvents(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
	}
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + p.getUniqueId().toString() + ".yml");
		this.Config = YamlConfiguration.loadConfiguration(f); //MAke a class so you dont have to do this all the time for config
		
		Objective obj = board.registerNewObjective("test","dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("KitPvp");
		
		Score score = obj.getScore(ChatColor.GREEN + "Kills:"); 
		score.setScore(this.Config.getInt("Points"));
		
		p.setScoreboard(board);
	}
	
	
	@EventHandler
	public void onPlayerKill(PlayerDeathEvent e) throws IOException
	{
		if(e.getEntity().getKiller() instanceof Player)
		{
			Player p = e.getEntity().getKiller();	
			File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + p.getUniqueId().toString() + ".yml");
			this.Config = YamlConfiguration.loadConfiguration(f); //MAke a class so you dont have to do this all the time for config
			Config.set("Points", Config.getInt("Points") + 10);
			Config.save(f);
			
			Scoreboard sb = p.getScoreboard();
			Objective obj = sb.getObjective(DisplaySlot.SIDEBAR);
			Score score = obj.getScore(ChatColor.GREEN + "Kills:");
			score.setScore(this.Config.getInt("Points"));
			p.setScoreboard(sb);
			p.sendMessage("You gained uh.. 10 points... yay?");
		}
	}
	
	
	
}
