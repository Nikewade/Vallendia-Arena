package me.Nikewade.VallendiaMinigame.Utils;

import java.io.File;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class FileManager {

	   private final VallendiaMinigame pl;
	   private File UsersFile;


	   public FileManager(VallendiaMinigame instance) {
	      this.pl = instance;
	      this.pl.getConfig().options().copyDefaults(true);
	      this.pl.saveConfig();
	      this.UsersFile = new File(this.pl.getDataFolder().getAbsolutePath(), "Users");
	      if(!this.UsersFile.exists()) {
	         this.UsersFile.mkdirs();
	      }

	   }

	   public File getUsersFile() {
	      return this.UsersFile;
	   }
	}
