package me.Nikewade.VallendiaMinigame.Events;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Config;

public class PlayerDataEvents implements Listener {

	VallendiaMinigame Main;
	YamlConfiguration Config;
	
	public PlayerDataEvents(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
	}
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + p.getUniqueId().toString() + ".yml");
		this.Config = YamlConfiguration.loadConfiguration(f);
		
		if(!(f.exists())) 
		{
			try {
			f.createNewFile();
			} catch (IOException e1) {
			e1.printStackTrace();
			}
		      try {
		          if(!this.Config.contains("Name")) {
		             this.Config.set("Name", p.getName());
		          }

		          if(!this.Config.contains("UUID")) {
		             this.Config.set("UUID", p.getUniqueId().toString());
		          }
		          
		          if(!this.Config.contains("Points")) {
			             this.Config.set("Points", 0);
			          }

			      this.Config.save(f);
		       } catch (Exception var4) {
		          ;
		       }		
		}
	      
	}
	
}
