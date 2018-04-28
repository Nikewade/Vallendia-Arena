package me.Nikewade.VallendiaMinigame;

import org.bukkit.plugin.java.JavaPlugin;

import me.Nikewade.VallendiaMinigame.Events.PlayerDataEvents;
import me.Nikewade.VallendiaMinigame.Events.ScoreboardEvents;
import me.Nikewade.VallendiaMinigame.Utils.FileManager;

public class VallendiaMinigame extends JavaPlugin{
	   private static VallendiaMinigame Main;
	   private FileManager FileManager;
	
	
	   public void onEnable()
	   {
		   Main = this;
		   this.FileManager = new FileManager(this);
		   
		   new PlayerDataEvents(this);
		   new ScoreboardEvents(this);
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
