package me.Nikewade.VallendiaMinigame;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.Nikewade.VallendiaMinigame.Data.CreatePlayerData;
import me.Nikewade.VallendiaMinigame.Data.PlayerDataManager;
import me.Nikewade.VallendiaMinigame.Events.PlayerDeathEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerJoinEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerKillEvents;
import me.Nikewade.VallendiaMinigame.Graphics.ScoreboardHandler;
import me.Nikewade.VallendiaMinigame.Utils.FileManager;

public class VallendiaMinigame extends JavaPlugin{
	   private static VallendiaMinigame Main;
	   private FileManager FileManager;
	   YamlConfiguration config;
	   public ScoreboardHandler sb;
	   public CreatePlayerData createplayerdata;
	   public PlayerDataManager playerdatamanager;
	
	
	   public void onEnable()
	   {
		   Main = this;
		   
		   //Methods etc..
		   this.FileManager = new FileManager(this);
		   this.sb = new ScoreboardHandler(this);
		   this.createplayerdata = new CreatePlayerData(this);
		   this.playerdatamanager = new PlayerDataManager(this);
		   
		   //Listeners
		   new PlayerJoinEvents(this);
		   new PlayerDeathEvents(this);
		   new PlayerKillEvents(this);
		   
		   for(Player p : Bukkit.getServer().getOnlinePlayers()) { //Runs scoreboard on reload
			   sb.runScoreboard(p);
		   }
	   }
	   
	   public FileManager getFileManager()
	   {
		   return this.FileManager;
	   }
	  

	   public static VallendiaMinigame getInstance()
	   {
		   return Main;
	   }
}
