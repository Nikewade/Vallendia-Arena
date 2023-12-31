package me.Nikewade.VallendiaMinigame.Saves;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Data.CreatePlayerData;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class SpawningHandler {
	VallendiaMinigame Main;
	CreatePlayerData createData;
	YamlConfiguration config;
	private static Random random = new Random();
	
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
			    	  config.set("location.Name" , name.toLowerCase());
				      this.config.save(f);
			       } catch (Exception var4) {
			          ;
			       }		
			}
	 }
	 
	 public boolean teleportPlayer(Player p, String spawnname)
	 {
			spawnname = spawnname.toLowerCase();
			File f = new File(VallendiaMinigame.getInstance().getFileManager().getSpawnFile().getAbsolutePath() + "/" + spawnname + ".yml");
			if(f.exists())
			{
				this.config = YamlConfiguration.loadConfiguration(f);
				World w = Bukkit.getServer().getWorld(config.getString("location.World"));
				double x = config.getDouble("location.X");
				double y = config.getDouble("location.Y");
				double z = config.getDouble("location.Z");
				double yaw = config.getDouble("location.Yaw");
				double pitch = config.getDouble("location.Pitch");
				Location l = new Location(w, x, y, z, (float)yaw , (float)pitch);
				p.teleport(l);	
				p.setFallDistance(0);
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1, (float)0.1);
	 	 		p.getWorld().spawnParticle(Particle.SMOKE_LARGE, p.getLocation().add(0, 1.8, 0), 20);
	 	 		p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, p.getLocation().add(0, 1.8, 0), 20);
	 	 		p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().add(0, 1.8, 0), 20);
	 	 		p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().add(0, 1.8, 0), 20);
	 	 		p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().add(0, 1.8, 0), 20);
				return true;
			}else 
				{
				return false;
				}
	 }
	 
	 
	 public void teleportPlayerRandom(Player p)
	 {
			File f = new File(VallendiaMinigame.getInstance().getFileManager().getSpawnFile().getAbsolutePath());
			   File[] files = f.listFiles();
			   if(files.length != 0)
			   {
				   int randomFile = random.nextInt(files.length);
				   this.config = YamlConfiguration.loadConfiguration(files[randomFile]);
				   
					World w = Bukkit.getServer().getWorld(config.getString("location.World"));
					double x = config.getDouble("location.X");
					double y = config.getDouble("location.Y");
					double z = config.getDouble("location.Z");
					double yaw = config.getDouble("location.Yaw");
					double pitch = config.getDouble("location.Pitch");
					Location l = new Location(w, x, y, z, (float)yaw , (float)pitch);
					int cap = 1;
					while(this.playerNearby(l, p))
					{
						if(cap >= 20)
						{
							   this.teleportPlayer(p, config.getString("location.Name"));
						       p.sendTitle(Utils.Colorate("&3&lGood luck!"), null, 20, 40, 40);
						       break;
						}
						cap++;
						   randomFile = random.nextInt(files.length);
						   this.config = YamlConfiguration.loadConfiguration(files[randomFile]);
						   
							w = Bukkit.getServer().getWorld(config.getString("location.World"));
							x = config.getDouble("location.X");
							y = config.getDouble("location.Y");
							z = config.getDouble("location.Z");
							yaw = config.getDouble("location.Yaw");
							pitch = config.getDouble("location.Pitch");
							l = new Location(w, x, y, z, (float)yaw , (float)pitch);
					} 
				   
			   }else p.sendMessage(Utils.Colorate("&8No spawns exist!"));
	 }
	 
	 //while this is true cancel stuff
	 public boolean playerNearby(Location l, Player p)
	 {
		int spawnRange = Main.getConfig().getInt("Options.spawn-range");
			for(Entity e : l.getWorld().getNearbyEntities(l, spawnRange, spawnRange, spawnRange))
			{
				if(e instanceof Player)
				{
					if(e != p)
					{
						return true;	
					}
				}
			}
			   this.teleportPlayer(p, config.getString("location.Name"));
		       p.sendTitle(Utils.Colorate("&3&lGood luck!"), null, 20, 40, 40);
		return false;
	 }

}
