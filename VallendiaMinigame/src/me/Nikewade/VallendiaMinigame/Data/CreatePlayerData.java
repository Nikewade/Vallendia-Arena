package me.Nikewade.VallendiaMinigame.Data;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class CreatePlayerData {
	VallendiaMinigame Main;
	YamlConfiguration Config;
	
	 public CreatePlayerData(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	  }
	 
	 
	 public void createFile(Player p)
	 {
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
			          
			          if(!this.Config.contains("Kills")) {
				             this.Config.set("Kills", 0);
				          }
			          
			          if(!this.Config.contains("Deaths")) {
				             this.Config.set("Deaths", 0);
				          }

				      this.Config.save(f);
			       } catch (Exception var4) {
			          ;
			       }		
			}
	 }
	
	
}
