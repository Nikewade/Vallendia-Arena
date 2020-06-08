package me.Nikewade.VallendiaMinigame.Data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class PlayerDataManager {
	static VallendiaMinigame Main;
	CreatePlayerData createData;
	static YamlConfiguration config;
	public static HashMap<UUID, HashMap<String, String>> data = new HashMap<>();
	
	 public PlayerDataManager(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	    this.createData = new CreatePlayerData();
	    for(Player p : Bukkit.getOnlinePlayers())
	    {
	    	createData(p);
	    }
	  }
	 
	 
	 public void createFile(Player p)
	 {
		 createData.createFile(p);
	 }
	 
	 
	 public static void createData(Player p)
	 {
		 File f = new File(Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + p.getUniqueId() + ".yml");
		 config = YamlConfiguration.loadConfiguration(f);
		 
		 HashMap<String, String> map = new HashMap<>();		
		 for(String key : config.getConfigurationSection("").getKeys(true)){
			 if(key.equalsIgnoreCase("upgrades"))
			 {
				 continue;
			 }
			 if(key.equalsIgnoreCase("upgrades.armorenchants"))
			 {
				 continue;
			 }
			 if(key.equalsIgnoreCase("upgrades.weaponenchants"))
			 {
				 continue;
			 }
			 if(key.equalsIgnoreCase("upgrades.toolenchants"))
			 {
				 continue;
			 }
			 if(key.equalsIgnoreCase("abilities"))
			 {
				 continue;
			 }
			 
			 map.put(key, config.getString(key));
			 
			 data.put(p.getUniqueId(), map);
		 }
	 }
	 
	 
	 

	 public int getPlayerIntData(UUID uuid, String data)
	 {
		 try {
				return Integer.parseInt(this.data.get(uuid).get(data));
			}
			catch(Exception e) {

			}
		 
		 File f = new File(Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 config = YamlConfiguration.loadConfiguration(f);	
		 
		return config.getInt(data);
	 }
	 
	 
	 public String getPlayerStringData(UUID uuid, String data)
	 {
		 return this.data.get(uuid).get(data);
	 }
	 
	 
	 
	 public String getRawPlayerStringData(UUID uuid, String data)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 config = YamlConfiguration.loadConfiguration(f);	
		 
		return config.getString(data);
	 }
	 
	 
	 
	 
	 public int getRawPlayerIntData(UUID uuid, String data)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 config = YamlConfiguration.loadConfiguration(f);	
		 
		return config.getInt(data);
	 }
	 
	 
	 public void editData (UUID uuid, String data, String edit)
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
		 
		 this.data.get(uuid).remove(data);
		 this.data.get(uuid).put(data, edit);
		 
	 }
	 
	 public void editIntData (UUID uuid, String data, int edit)
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
		 
		 this.data.get(uuid).remove(data);
		 this.data.get(uuid).put(data, Integer.toString(edit));
	 }
	 
	 
	 public void addData (UUID uuid, String data, int add)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);	
		 int originaldata = this.getPlayerIntData(uuid, data);
		 config.set(data, originaldata + add);
		 
		 try {
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 this.data.get(uuid).remove(data);
		 this.data.get(uuid).put(data, Integer.toString(originaldata + add));
	 }
	 
	 
	 public void subtractData (UUID uuid, String data, int subtract)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);	
		 int originaldata = this.getPlayerIntData(uuid, data);
		 config.set(data, originaldata - subtract);
		 
		 try {
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 this.data.get(uuid).remove(data);
		 this.data.get(uuid).put(data, Integer.toString(originaldata - subtract));
	 }
	 
	 
	 
	 
	 
}
