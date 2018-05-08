package me.Nikewade.VallendiaMinigame;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.Nikewade.VallendiaMinigame.Commands.CreateKitCommand;
import me.Nikewade.VallendiaMinigame.Data.CreatePlayerData;
import me.Nikewade.VallendiaMinigame.Data.PlayerDataManager;
import me.Nikewade.VallendiaMinigame.Events.PlayerClickItemEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerDeathEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerJoinEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerKillEvents;
import me.Nikewade.VallendiaMinigame.Graphics.GUIHandler;
import me.Nikewade.VallendiaMinigame.Graphics.ScoreboardHandler;
import me.Nikewade.VallendiaMinigame.Kits.Archer;
import me.Nikewade.VallendiaMinigame.Kits.Assassin;
import me.Nikewade.VallendiaMinigame.Kits.KitManager;
import me.Nikewade.VallendiaMinigame.Kits.Mage;
import me.Nikewade.VallendiaMinigame.Kits.Starter;
import me.Nikewade.VallendiaMinigame.Kits.Warrior;
import me.Nikewade.VallendiaMinigame.Shop.ShopManager;
import me.Nikewade.VallendiaMinigame.Upgrades.ArmorUpgrade;
import me.Nikewade.VallendiaMinigame.Upgrades.HealthUpgrade;
import me.Nikewade.VallendiaMinigame.Upgrades.SpeedUpgrade;
import me.Nikewade.VallendiaMinigame.Upgrades.UpgradeManager;
import me.Nikewade.VallendiaMinigame.Upgrades.WeaponUpgrade;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.FileManager;

public class VallendiaMinigame extends JavaPlugin{
	   private static VallendiaMinigame Main;
	   private FileManager FileManager;
	   YamlConfiguration config;
	   public ScoreboardHandler sb;
	   public CreatePlayerData createplayerdata;
	   public PlayerDataManager playerdatamanager;
	   public KitManager kitmanager;
	   public ShopManager shopmanager;
	   public UpgradeManager upgrademanager;
	   public HealthUpgrade healthUpgrade;
	   public SpeedUpgrade speedUpgrade;
	   public ArmorUpgrade armorUpgrade;
	   public WeaponUpgrade weaponUpgrade;
	   public GUIHandler guihandler;
	   public Warrior warrior;
	   public Archer archer;
	   public Assassin assassin;
	   public Mage mage;
	   public Starter starter;
	
	
	   public void onEnable()
	   {
		   Main = this;
		   //Methods etc..
		   this.FileManager = new FileManager(this);
		   this.sb = new ScoreboardHandler(this);
		   this.createplayerdata = new CreatePlayerData(this);
		   this.playerdatamanager = new PlayerDataManager(this);
		   this.kitmanager = new KitManager(this);
		   this.shopmanager = new ShopManager(this);
		   this.upgrademanager = new UpgradeManager(this);
		   this.guihandler = new GUIHandler(this);
		   
		   //Listeners
		   new PlayerJoinEvents(this);
		   new PlayerDeathEvents(this);
		   new PlayerKillEvents(this);
		   new PlayerClickItemEvents(this);
		   Bukkit.getPluginManager().registerEvents(AdvInventory.getListener(), this);
		   
		   //Commands
		   new CreateKitCommand(this);
		   
		   //Kits
		   this.warrior = new Warrior(this);
		   this.archer = new Archer(this);
		   this.assassin = new Assassin(this);
		   this.mage = new Mage(this);
		   this.starter = new Starter(this);
		   
		   //Upgrades
		   this.healthUpgrade = new HealthUpgrade(this);
		   this.speedUpgrade = new SpeedUpgrade(this);
		   this.armorUpgrade = new ArmorUpgrade(this);
		   this.weaponUpgrade = new WeaponUpgrade(this);
		   
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
