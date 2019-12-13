package me.Nikewade.VallendiaMinigame.Saves;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.FileManager;

public class FireLocations {
	VallendiaMinigame Main;
	YamlConfiguration config;
	public static ArrayList<String> locations = new ArrayList<>();
	
	 public FireLocations(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	    
	       for(String string : VallendiaMinigame.getInstance().getFileManager().getFireLocationsFile().list())
	       {
	    	   locations.add(string);
	       }
	       
	    
	  }
	 
	 public void createFile(String name, Location loc)
	 {
			VallendiaMinigame.getInstance().getFileManager();
			File f = new File(VallendiaMinigame.getInstance().getFileManager().getFireLocationsFile().getAbsolutePath() + "/" + name + ".yml");
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
