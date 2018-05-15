package me.Nikewade.VallendiaMinigame.Data;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class CreatePlayerData {
	YamlConfiguration Config;
	 
	 public void createFile(Player p)
	 {
			File f = new File(VallendiaMinigame.getInstance().getFileManager().getUsersFile().getAbsolutePath() + "/" + p.getUniqueId().toString() + ".yml");
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

			          if(!this.Config.contains("Kit")) {
				             this.Config.set("Kit", "starter");
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
			          
			          if(!this.Config.contains("Upgrades.Health")) {
				             this.Config.set("Upgrades.Health", 0);
				          }
			          
			          if(!this.Config.contains("Upgrades.Health")) {
				             this.Config.set("Upgrades.Speed", 0);
				          }
			          
			          if(!this.Config.contains("Upgrades.Health")) {
				             this.Config.set("Upgrades.Armor", 0);
				          }
			          
			          if(!this.Config.contains("Upgrades.Health")) {
				             this.Config.set("Upgrades.Weapon", 0);
				          }

				      this.Config.save(f);
			       } catch (Exception var4) {
			          ;
			       }		
			}
	 }
	
	
}
