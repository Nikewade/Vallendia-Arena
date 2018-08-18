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
			          
			          if(!this.Config.contains("Upgrades.health")) {
				             this.Config.set("Upgrades.health", 0);
				          }
			          
			          if(!this.Config.contains("Upgrades.speed")) {
				             this.Config.set("Upgrades.speed", 0);
				          }
			          
			          if(!this.Config.contains("Upgrades.armor")) {
				             this.Config.set("Upgrades.armor", 0);
				          }
			          
			          if(!this.Config.contains("Upgrades.ArmorEnchants"))
			          {
				             this.Config.set("Upgrades.ArmorEnchants.prot", 0);
				             this.Config.set("Upgrades.ArmorEnchants.projprot", 0);
				             this.Config.set("Upgrades.ArmorEnchants.fireprot", 0);
				             this.Config.set("Upgrades.ArmorEnchants.blastprot", 0);
				             this.Config.set("Upgrades.ArmorEnchants.thorns", 0);
				             this.Config.set("Upgrades.ArmorEnchants.featherfall", 0);
			          }
			          
			          if(!this.Config.contains("Upgrades.weapon")) {
				             this.Config.set("Upgrades.weapon", 0);
				          }
			          
			          if(!this.Config.contains("Abilities.slot1")) {
				             this.Config.set("Abilities.slot 1", "empty");
				          }
			          if(!this.Config.contains("Abilities.slot2")) {
				             this.Config.set("Abilities.slot 2", "empty");
				          }
			          if(!this.Config.contains("Abilities.slot3")) {
				             this.Config.set("Abilities.slot 3", "empty");
				          }
			          if(!this.Config.contains("Abilities.slot4")) {
				             this.Config.set("Abilities.slot 4", "empty");
				          }
			          if(!this.Config.contains("Abilities.slot5")) {
				             this.Config.set("Abilities.slot 5", "empty");
				          }
			          if(!this.Config.contains("Abilities.slot6")) {
				             this.Config.set("Abilities.slot 6", "empty");
				          }

				      this.Config.save(f);
			       } catch (Exception var4) {
			          ;
			       }		
			}
	 }
	
	
}
