package me.Nikewade.VallendiaMinigame.Data;

import java.io.File;
import java.io.IOException;
<<<<<<< HEAD
import java.util.UUID;

=======
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
>>>>>>> second-repo/master
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class PlayerDataManager {
<<<<<<< HEAD
	VallendiaMinigame Main;
	CreatePlayerData createData;
	YamlConfiguration config;
=======
	static VallendiaMinigame Main;
	CreatePlayerData createData;
	static YamlConfiguration config;
	public static HashMap<UUID, HashMap<String, String>> data = new HashMap<>();
>>>>>>> second-repo/master
	
	 public PlayerDataManager(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	    this.createData = new CreatePlayerData();
<<<<<<< HEAD
=======
	    for(Player p : Bukkit.getOnlinePlayers())
	    {
	    	createData(p);
	    }
>>>>>>> second-repo/master
	  }
	 
	 
	 public void createFile(Player p)
	 {
		 createData.createFile(p);
	 }
	 
	 
<<<<<<< HEAD
	 public int getPlayerIntData(UUID uuid, String data)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);
		   
		 return config.getInt(data);
=======
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
>>>>>>> second-repo/master
	 }
	 
	 
	 public String getPlayerStringData(UUID uuid, String data)
	 {
<<<<<<< HEAD
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);
		   
		 return config.getString(data);
=======
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
>>>>>>> second-repo/master
	 }
	 
	 
	 public void editData (UUID uuid, String data, String edit)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);	
<<<<<<< HEAD
		 
		 config.set(data, edit);
		 
=======
		 config.set(data, edit);
		 
		 
>>>>>>> second-repo/master
		 try {
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
<<<<<<< HEAD
=======
		 
		 this.data.get(uuid).remove(data);
		 this.data.get(uuid).put(data, edit);
		 
>>>>>>> second-repo/master
	 }
	 
	 public void editIntData (UUID uuid, String data, int edit)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);	
<<<<<<< HEAD
		 
=======
>>>>>>> second-repo/master
		 config.set(data, edit);
		 
		 try {
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
<<<<<<< HEAD
=======
		 
		 this.data.get(uuid).remove(data);
		 this.data.get(uuid).put(data, Integer.toString(edit));
>>>>>>> second-repo/master
	 }
	 
	 
	 public void addData (UUID uuid, String data, int add)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);	
		 int originaldata = this.getPlayerIntData(uuid, data);
<<<<<<< HEAD
		 
=======
>>>>>>> second-repo/master
		 config.set(data, originaldata + add);
		 
		 try {
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
<<<<<<< HEAD
=======
		 
		 this.data.get(uuid).remove(data);
		 this.data.get(uuid).put(data, Integer.toString(originaldata + add));
>>>>>>> second-repo/master
	 }
	 
	 
	 public void subtractData (UUID uuid, String data, int subtract)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);	
		 int originaldata = this.getPlayerIntData(uuid, data);
<<<<<<< HEAD
		 
=======
>>>>>>> second-repo/master
		 config.set(data, originaldata - subtract);
		 
		 try {
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
<<<<<<< HEAD
=======
		 
		 this.data.get(uuid).remove(data);
		 this.data.get(uuid).put(data, Integer.toString(originaldata - subtract));
>>>>>>> second-repo/master
	 }
	 
	 
	 
<<<<<<< HEAD
=======
	 
	 
>>>>>>> second-repo/master
}
