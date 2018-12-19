package me.Nikewade.VallendiaMinigame;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import de.slikey.effectlib.EffectManager;
import me.Nikewade.VallendiaMinigame.Abilities.AbilityManager;
import me.Nikewade.VallendiaMinigame.Abilities.BackFlipAbility;
import me.Nikewade.VallendiaMinigame.Abilities.BackstabAbility;
import me.Nikewade.VallendiaMinigame.Abilities.ClimbAbility;
import me.Nikewade.VallendiaMinigame.Abilities.DeflectArrowsAbility;
import me.Nikewade.VallendiaMinigame.Abilities.GrapplingHookAbility;
import me.Nikewade.VallendiaMinigame.Abilities.LeapAbility;
import me.Nikewade.VallendiaMinigame.Abilities.SneakAbility;
import me.Nikewade.VallendiaMinigame.Commands.KitCommands;
import me.Nikewade.VallendiaMinigame.Commands.MainCommands;
import me.Nikewade.VallendiaMinigame.Commands.PointCommands;
import me.Nikewade.VallendiaMinigame.Commands.ShopCommands;
import me.Nikewade.VallendiaMinigame.Commands.UpgradeCommands;
import me.Nikewade.VallendiaMinigame.Data.PlayerDataManager;
import me.Nikewade.VallendiaMinigame.Events.AltitudeChecker;
import me.Nikewade.VallendiaMinigame.Events.PlayerBlockEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerDeathEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerExpEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerFoodEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerItemEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerJoinEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerKillEvents;
import me.Nikewade.VallendiaMinigame.Graphics.GuiHandler;
import me.Nikewade.VallendiaMinigame.Graphics.ScoreboardHandler;
import me.Nikewade.VallendiaMinigame.Kits.KitManager;
import me.Nikewade.VallendiaMinigame.Levels.LevelManager;
import me.Nikewade.VallendiaMinigame.Shop.GuiShopHandler;
import me.Nikewade.VallendiaMinigame.Shop.IO;
import me.Nikewade.VallendiaMinigame.Shop.PointsManager;
import me.Nikewade.VallendiaMinigame.Shop.ShopHandler;
import me.Nikewade.VallendiaMinigame.Upgrades.RegenUpgrade;
import me.Nikewade.VallendiaMinigame.Upgrades.UpgradeManager;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.FileManager;
import me.Nikewade.VallendiaMinigame.Utils.Utils;


public class VallendiaMinigame extends JavaPlugin{
	   private static VallendiaMinigame Main;
	   private FileManager FileManager;
	   YamlConfiguration config;
	   ScoreboardManager manager;
	   public ScoreboardHandler sb;
	   public PlayerDataManager playerdatamanager;
	   public KitManager kitmanager;
	   public PointsManager shopmanager;
	   public UpgradeManager upgrademanager;
	   public GuiHandler guihandler;
	   public AbilityManager abilitymanager;
	   public EffectManager effectmanager;
	   public LevelManager levelmanager;
	   public AltitudeChecker altchecker;
	
	   @Override
	   public void onEnable()
	   {
		   Main = this;
		   if(!IO.getShopFile().exists()) ShopHandler.saveShop();
		   AbilityUtils.addBlocks();
		   SneakAbility.onReload();
		   //Methods etc..
		   this.FileManager = new FileManager(this);
		   this.sb = new ScoreboardHandler(this);
		   this.playerdatamanager = new PlayerDataManager(this);
		   this.abilitymanager = new AbilityManager(this);
		   this.kitmanager = new KitManager(this);
		   this.shopmanager = new PointsManager(this);
		   this.upgrademanager = new UpgradeManager(this);
		   this.levelmanager = new LevelManager(this);
		   this.guihandler = new GuiHandler(this);
		   this.effectmanager = new EffectManager(this);
		   this.altchecker = new AltitudeChecker(this);
		  
		   
		   //Listeners
		   new PlayerJoinEvents(this);
		   new PlayerDeathEvents(this);
		   new PlayerKillEvents(this);
		   new PlayerItemEvents(this);
		   new PlayerFoodEvents(this);
		   new PlayerBlockEvents(this);
		   new PlayerExpEvents(this);
		   Bukkit.getPluginManager().registerEvents(AdvInventory.getListener(), this);
		   this.getServer().getPluginManager().registerEvents(new GuiShopHandler(), this);
		   
		   //Ability Listeners
		   Bukkit.getPluginManager().registerEvents(DeflectArrowsAbility.getListener(), this);
		   Bukkit.getPluginManager().registerEvents(BackFlipAbility.getListener(), this);
		   Bukkit.getPluginManager().registerEvents(LeapAbility.getListener(), this);
		   Bukkit.getPluginManager().registerEvents(ClimbAbility.getListener(), this);
		   Bukkit.getPluginManager().registerEvents(SneakAbility.getListener(), this);
		   Bukkit.getPluginManager().registerEvents(BackstabAbility.getListener(), this);
		   Bukkit.getPluginManager().registerEvents(GrapplingHookAbility.getListener(), this);
		   
		   //Commands
		   new KitCommands(this);
		   new UpgradeCommands(this);
		   new PointCommands(this);
		   new MainCommands(this);
		   this.getCommand("shop").setExecutor(new ShopCommands());
		   
		   for(Player p : Bukkit.getServer().getOnlinePlayers()) { 
			   //sb.runScoreboard(p);
			   sb.setupPlayerScoreboard(p);
			   p.closeInventory();
			   this.levelmanager.updateExpBar(p);
			   this.levelmanager.updateLevelBar(p);
			if(this.upgrademanager.getUpgradeAmount(p, "regeneration") > 0)
				{
				   RegenUpgrade.addTimer(p); 
				}
		   }
		   sb.runNameTagUpdater();
		   sb.runSidebarUpdater();
		   ShopHandler.loadShop();
		   
		    
		   
		   
	   }
	   
	   
	   @Override
	   public void onDisable()
	   {
	      effectmanager.dispose();  
	      Utils.restoreBlocks();
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
