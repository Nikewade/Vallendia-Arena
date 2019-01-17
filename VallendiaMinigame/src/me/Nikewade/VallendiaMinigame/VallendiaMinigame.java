package me.Nikewade.VallendiaMinigame;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.ScoreboardManager;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import de.slikey.effectlib.EffectManager;
import me.Nikewade.VallendiaMinigame.Abilities.AbilityManager;
import me.Nikewade.VallendiaMinigame.Abilities.BackFlipAbility;
import me.Nikewade.VallendiaMinigame.Abilities.BackstabAbility;
import me.Nikewade.VallendiaMinigame.Abilities.BlindingArrowsAbility;
import me.Nikewade.VallendiaMinigame.Abilities.BullRushAbility;
import me.Nikewade.VallendiaMinigame.Abilities.ClimbAbility;
import me.Nikewade.VallendiaMinigame.Abilities.DeflectArrowsAbility;
import me.Nikewade.VallendiaMinigame.Abilities.ExplosiveArrowAbility;
import me.Nikewade.VallendiaMinigame.Abilities.GrapplingHookAbility;
import me.Nikewade.VallendiaMinigame.Abilities.LeapAbility;
import me.Nikewade.VallendiaMinigame.Abilities.MagicArrowsAbility;
import me.Nikewade.VallendiaMinigame.Abilities.MomentumAbility;
import me.Nikewade.VallendiaMinigame.Abilities.PoisonArrowsAbility;
import me.Nikewade.VallendiaMinigame.Abilities.RootAbility;
import me.Nikewade.VallendiaMinigame.Abilities.SickeningArrowsAbility;
import me.Nikewade.VallendiaMinigame.Abilities.SlowingArrowsAbility;
import me.Nikewade.VallendiaMinigame.Abilities.SneakAbility;
import me.Nikewade.VallendiaMinigame.Abilities.TheHighGroundAbility;
import me.Nikewade.VallendiaMinigame.Abilities.WeakeningArrowsAbility;
import me.Nikewade.VallendiaMinigame.Commands.AdminCommand;
import me.Nikewade.VallendiaMinigame.Commands.CommandHandler;
import me.Nikewade.VallendiaMinigame.Commands.KitCommand;
import me.Nikewade.VallendiaMinigame.Commands.PointsCommand;
import me.Nikewade.VallendiaMinigame.Commands.ReloadCommand;
import me.Nikewade.VallendiaMinigame.Commands.ShopCommand;
import me.Nikewade.VallendiaMinigame.Commands.SpawnCommand;
import me.Nikewade.VallendiaMinigame.Commands.StatsCommand;
import me.Nikewade.VallendiaMinigame.Commands.VallendiaMainCommand;
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
import me.Nikewade.VallendiaMinigame.Spawning.SpawningHandler;
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
	   public SpawningHandler spawnhandler;
	   public ProtocolManager protocolManager;
	
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
		   this.spawnhandler = new SpawningHandler(this);
		   this.protocolManager = ProtocolLibrary.getProtocolManager();
		   
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
		   Bukkit.getPluginManager().registerEvents(TheHighGroundAbility.getListener(), this);
		   Bukkit.getPluginManager().registerEvents(BlindingArrowsAbility.getListener(), this);
		   Bukkit.getPluginManager().registerEvents(PoisonArrowsAbility.getListener(), this);
		   Bukkit.getPluginManager().registerEvents(SickeningArrowsAbility.getListener(), this);
		   Bukkit.getPluginManager().registerEvents(WeakeningArrowsAbility.getListener(), this);
		   Bukkit.getPluginManager().registerEvents(SlowingArrowsAbility.getListener(), this);
		   Bukkit.getPluginManager().registerEvents(MagicArrowsAbility.getListener(), this);
		   Bukkit.getPluginManager().registerEvents(RootAbility.getListener(), this);
		   Bukkit.getPluginManager().registerEvents(BullRushAbility.getListener(), this);
		   Bukkit.getPluginManager().registerEvents(MomentumAbility.getListener(), this);
		   Bukkit.getPluginManager().registerEvents(ExplosiveArrowAbility.getListener(), this);
		   
		   //Commands
		   this.registerCommands();
		   
		   for(Player p : Bukkit.getServer().getOnlinePlayers()) { 
			   //sb.runScoreboard(p);
			   sb.setupPlayerScoreboard(p);
			   p.closeInventory();
			   this.levelmanager.updateExpBar(p);
			   this.levelmanager.updateLevelBar(p);
				p.setGravity(true);
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
		  MagicArrowsAbility.gravatizeArrows();
	   }
	   
	   public FileManager getFileManager()
	   {
		   return this.FileManager;
	   }
	  

	   public static VallendiaMinigame getInstance()
	   {
		   return Main;
	   }
	   
	    public void registerCommands() {
	        CommandHandler handler = new CommandHandler();
			this.getCommand("shop").setExecutor(new ShopCommand());
	        handler.register("vallendia", new VallendiaMainCommand());
	 
	        handler.register("points", new PointsCommand());
	        handler.register("admin", new AdminCommand());
	        handler.register("reload", new ReloadCommand());
	        handler.register("stats", new StatsCommand());
	        handler.register("kit", new KitCommand());
	        handler.register("spawn", new SpawnCommand());
	        getCommand("vallendia").setExecutor(handler);
	    }
	   

	   
	   
}
