package me.Nikewade.VallendiaMinigame.Spawning;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Data.CreatePlayerData;

public class SpawningHandler {
	VallendiaMinigame Main;
	CreatePlayerData createData;
	YamlConfiguration config;
	
	 public SpawningHandler(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	  }
	 
	 public void createFile(String name, Location loc)
	 {
			File f = new File(VallendiaMinigame.getInstance().getFileManager().getSpawnFile().getAbsolutePath() + "/" + name + ".yml");
			this.config = YamlConfiguration.loadConfiguration(f);
			
			if(!(f.exists())) 
			{
				try {
				f.createNewFile();
				} catch (IOException e1) {
				e1.printStackTrace();
				}
				
			      try {
			    	  config.set("location.World" , loc.getWorld().getName());
			    	  config.set("location.X" , loc.getX());
			    	  config.set("location.Y" , loc.getY());
			    	  config.set("location.Z" , loc.getZ());
			    	  config.set("location.Yaw" , loc.getYaw());
			    	  config.set("location.Pitch" , loc.getPitch());
				      this.config.save(f);
			       } catch (Exception var4) {
			          ;
			       }		
			}
	 }

}
