package me.Nikewade.VallendiaMinigame.Utils;

import java.io.File;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class FileManager {

	   private final VallendiaMinigame pl;
	   private File UsersFile;
	   private File SpawnFile;


	   public FileManager(VallendiaMinigame instance) {
	      this.pl = instance;
	      this.pl.getConfig().options().copyDefaults(true);
	      this.pl.saveConfig();
	      this.UsersFile = new File(this.pl.getDataFolder().getAbsolutePath(), "Users");
	      if(!this.UsersFile.exists()) {
	         this.UsersFile.mkdirs();
	      }
	      
	      this.SpawnFile = new File(this.pl.getDataFolder().getAbsolutePath(), "Spawns");
	      if(!this.SpawnFile.exists()) {
	         this.SpawnFile.mkdirs();
	      }

	   }

	   public File getUsersFile() {
	      return this.UsersFile;
	   }
	   
	   public File getSpawnFile() {
		      return this.SpawnFile;
		   }
	}
