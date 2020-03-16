package me.Nikewade.VallendiaMinigame.Utils;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config extends YamlConfiguration {

	   private File File;


	   public Config(File File) 
	   {
	      this.File = File;

	      try {
	         this.load(File);
	      } catch (Exception var3) {
	         ;
	      }

	   }

	   public File getFile() 
	   {
	      return this.File;
	   }

	   public void SaveConfig() 
	   {
	      try {
	         this.save(this.File);
	      } catch (Exception var2) {
	         ;
	      }

	   }
	}