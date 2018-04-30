package me.Nikewade.VallendiaMinigame.Data;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class PlayerDataManager {
	VallendiaMinigame Main;
	YamlConfiguration config;
	
	 public PlayerDataManager(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	  }
	 
	 
	 
	 public int getPlayerData(UUID uuid, String data)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);
		   
		 return config.getInt(data);
	 }
	 
	 
	 public void editData (UUID uuid, String data, int edit)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);	
		 
		 config.set(data, edit);
		 
		 try {
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 
	 public void addData (UUID uuid, String data, int add)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);	
		 int originaldata = this.getPlayerData(uuid, data);
		 
		 config.set(data, originaldata + add);
		 
		 try {
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 
	 public void subtractData (UUID uuid, String data, int subtract)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);	
		 int originaldata = this.getPlayerData(uuid, data);
		 
		 config.set(data, originaldata - subtract);
		 
		 try {
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 
	 
}
