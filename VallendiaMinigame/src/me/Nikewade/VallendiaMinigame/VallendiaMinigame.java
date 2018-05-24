package me.Nikewade.VallendiaMinigame;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.Nikewade.VallendiaMinigame.Abilities.AbilityManager;
import me.Nikewade.VallendiaMinigame.Abilities.BackFlipAbility;
import me.Nikewade.VallendiaMinigame.Commands.CreateKitCommand;
import me.Nikewade.VallendiaMinigame.Commands.PointCommands;
import me.Nikewade.VallendiaMinigame.Commands.UpgradeCommands;
import me.Nikewade.VallendiaMinigame.Data.PlayerDataManager;
import me.Nikewade.VallendiaMinigame.Events.PlayerDeathEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerFoodEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerItemEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerJoinEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerKillEvents;
import me.Nikewade.VallendiaMinigame.Graphics.GuiHandler;
import me.Nikewade.VallendiaMinigame.Graphics.ScoreboardHandler;
import me.Nikewade.VallendiaMinigame.Kits.KitManager;
import me.Nikewade.VallendiaMinigame.Shop.PointsManager;
import me.Nikewade.VallendiaMinigame.Upgrades.UpgradeManager;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.FileManager;

public class VallendiaMinigame extends JavaPlugin{
	   private static VallendiaMinigame Main;
	   private FileManager FileManager;
	   YamlConfiguration config;
	   public ScoreboardHandler sb;
	   public PlayerDataManager playerdatamanager;
	   public KitManager kitmanager;
	   public PointsManager shopmanager;
	   public UpgradeManager upgrademanager;
	   public GuiHandler guihandler;
	   public AbilityManager abilitymanager;
	
	
	   public void onEnable()
	   {
		   Main = this;
		   //Methods etc..
		   this.FileManager = new FileManager(this);
		   this.sb = new ScoreboardHandler(this);
		   this.playerdatamanager = new PlayerDataManager(this);
		   this.abilitymanager = new AbilityManager(this);
		   this.kitmanager = new KitManager(this);
		   this.shopmanager = new PointsManager(this);
		   this.upgrademanager = new UpgradeManager(this);
		   this.guihandler = new GuiHandler(this);
		  
		   
		   //Listeners
		   new PlayerJoinEvents(this);
		   new PlayerDeathEvents(this);
		   new PlayerKillEvents(this);
		   new PlayerItemEvents(this);
		   new PlayerFoodEvents(this);
		   Bukkit.getPluginManager().registerEvents(AdvInventory.getListener(), this);
		   
		   //Commands
		   new CreateKitCommand(this);
		   new UpgradeCommands(this);
		   new PointCommands(this);
		   
		   for(Player p : Bukkit.getServer().getOnlinePlayers()) { 
			   sb.runScoreboard(p);
			   p.closeInventory();;
		   }
	   }
	   
	   public FileManager getFileManager()
	   {
		   return this.FileManager;
	   }
	  

	   public static VallendiaMinigame getInstance()
	   {
		   return Main;
	   }
	   
	   
}
