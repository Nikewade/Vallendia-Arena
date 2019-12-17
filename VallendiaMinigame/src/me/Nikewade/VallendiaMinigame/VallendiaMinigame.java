package me.Nikewade.VallendiaMinigame;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import com.alessiodp.parties.api.Parties;
import com.alessiodp.parties.api.interfaces.PartiesAPI;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

import de.slikey.effectlib.EffectManager;
import me.Nikewade.VallendiaMinigame.Abilities.AbilityManager;
import me.Nikewade.VallendiaMinigame.Abilities.BackFlipAbility;
import me.Nikewade.VallendiaMinigame.Abilities.BackstabAbility;
import me.Nikewade.VallendiaMinigame.Abilities.BlindingArrowsAbility;
import me.Nikewade.VallendiaMinigame.Abilities.BlurAbility;
import me.Nikewade.VallendiaMinigame.Abilities.BullRushAbility;
import me.Nikewade.VallendiaMinigame.Abilities.ClimbAbility;
import me.Nikewade.VallendiaMinigame.Abilities.DeflectArrowsAbility;
import me.Nikewade.VallendiaMinigame.Abilities.DivineShieldAbility;
import me.Nikewade.VallendiaMinigame.Abilities.EquipBowAbility;
import me.Nikewade.VallendiaMinigame.Abilities.ExplosiveArrowAbility;
import me.Nikewade.VallendiaMinigame.Abilities.FlyAbility;
import me.Nikewade.VallendiaMinigame.Abilities.GrapplingHookAbility;
import me.Nikewade.VallendiaMinigame.Abilities.LeapAbility;
import me.Nikewade.VallendiaMinigame.Abilities.LevitateAbility;
import me.Nikewade.VallendiaMinigame.Abilities.MagicArrowsAbility;
import me.Nikewade.VallendiaMinigame.Abilities.MomentumAbility;
import me.Nikewade.VallendiaMinigame.Abilities.MountAbility;
import me.Nikewade.VallendiaMinigame.Abilities.ParticleTestAbility;
import me.Nikewade.VallendiaMinigame.Abilities.PickPocketAbility;
import me.Nikewade.VallendiaMinigame.Abilities.PillageAbility;
import me.Nikewade.VallendiaMinigame.Abilities.PoisonArrowsAbility;
import me.Nikewade.VallendiaMinigame.Abilities.RootAbility;
import me.Nikewade.VallendiaMinigame.Abilities.SickeningArrowsAbility;
import me.Nikewade.VallendiaMinigame.Abilities.SlowingArrowsAbility;
import me.Nikewade.VallendiaMinigame.Abilities.SneakAbility;
import me.Nikewade.VallendiaMinigame.Abilities.SurvivalistAbility;
import me.Nikewade.VallendiaMinigame.Abilities.TheHighGroundAbility;
import me.Nikewade.VallendiaMinigame.Abilities.VampiricTouchAbility;
import me.Nikewade.VallendiaMinigame.Abilities.VanishAbility;
import me.Nikewade.VallendiaMinigame.Abilities.WeakeningArrowsAbility;
import me.Nikewade.VallendiaMinigame.Commands.AdminCommand;
import me.Nikewade.VallendiaMinigame.Commands.CommandHandler;
import me.Nikewade.VallendiaMinigame.Commands.KitCommand;
import me.Nikewade.VallendiaMinigame.Commands.PointsCommand;
import me.Nikewade.VallendiaMinigame.Commands.RegenCommand;
import me.Nikewade.VallendiaMinigame.Commands.ReloadCommand;
import me.Nikewade.VallendiaMinigame.Commands.ShopCommand;
import me.Nikewade.VallendiaMinigame.Commands.SpawnCommand;
import me.Nikewade.VallendiaMinigame.Commands.StatsCommand;
import me.Nikewade.VallendiaMinigame.Commands.VallendiaMainCommand;
import me.Nikewade.VallendiaMinigame.Data.PlayerDataManager;
import me.Nikewade.VallendiaMinigame.Events.AltitudeChecker;
import me.Nikewade.VallendiaMinigame.Events.PlayerBlockEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerCustomFoodCookEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerDeathEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerExpEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerFoodEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerItemEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerJoinEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerKillEvents;
import me.Nikewade.VallendiaMinigame.Events.PlayerTakeDamageEvent;
import me.Nikewade.VallendiaMinigame.Events.ProjectileEvents;
import me.Nikewade.VallendiaMinigame.Events.SignEvents;
import me.Nikewade.VallendiaMinigame.Graphics.GuiHandler;
import me.Nikewade.VallendiaMinigame.Graphics.ScoreboardHandler;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Kits.KitManager;
import me.Nikewade.VallendiaMinigame.Levels.LevelManager;
import me.Nikewade.VallendiaMinigame.Saves.FireLocations;
import me.Nikewade.VallendiaMinigame.Saves.SpawningHandler;
import me.Nikewade.VallendiaMinigame.Shop.GuiShopHandler;
import me.Nikewade.VallendiaMinigame.Shop.IO;
import me.Nikewade.VallendiaMinigame.Shop.PointsManager;
import me.Nikewade.VallendiaMinigame.Shop.ShopHandler;
import me.Nikewade.VallendiaMinigame.Upgrades.RegenUpgrade;
import me.Nikewade.VallendiaMinigame.Upgrades.UpgradeManager;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.FallingBlocksManager;
import me.Nikewade.VallendiaMinigame.Utils.FileManager;
import me.Nikewade.VallendiaMinigame.Utils.GhostManager;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;


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
	   public FireLocations firelocations;
	   public ProtocolManager protocolManager;
	   public WorldGuardPlugin worldguard;
	   public AbilityCooldown cooldowns;
	   public FallingBlocksManager fallingblocks;
	   public Permission perms;
	   public Chat chat;
	   public GhostManager ghost;
	   public PartiesAPI parties;
	   //Custom flags
	   @SuppressWarnings("rawtypes")
	   public static final Flag blockAbilities = new StateFlag("block-abilities", true);
	   @SuppressWarnings("rawtypes")
	   public static final Flag checkAltitude = new StateFlag("check-altitude", true);

	   public void onLoad() {
	       FlagRegistry registry = WorldGuardPlugin.inst().getFlagRegistry();
	       try {
	           registry.register(blockAbilities);
	           registry.register(checkAltitude);
	       } catch (IllegalStateException e) {
	           // some other plugin registered a flag by the same name already.
	           // you may want to re-register with a different name, but this
	           // could cause issues with saved flags in region files. it's better
	           // to print a message to let the server admin know of the conflict
	       }
	   }
	   
	   private WorldGuardPlugin getWorldGuard() {
		    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
		 
		    // WorldGuard may not be loaded
		    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
		        return null; // Maybe you want throw an exception instead
		    }
		 
		    return (WorldGuardPlugin) plugin;
		}
	   
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
		   this.firelocations = new FireLocations(this);
		   this.protocolManager = ProtocolLibrary.getProtocolManager();
		   this.worldguard = getWorldGuard();
	       this.fallingblocks = new FallingBlocksManager(this);
	       this.ghost = new GhostManager(this);
	       
	       
	       if (getServer().getPluginManager().getPlugin("Parties") != null) {
	    	    if (getServer().getPluginManager().getPlugin("Parties").isEnabled()) {
	    	    	this.parties = Parties.getApi();
	    	    }
	    	}
	       
	        if (!setupPermissions() ) {
	            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
	            getServer().getPluginManager().disablePlugin(this);
	            return;
	        }
	        setupPermissions();
	        setupChat();
		   
		   //Listeners
		   new PlayerJoinEvents(this);
		   new PlayerDeathEvents(this);
		   new PlayerKillEvents(this);
		   new PlayerItemEvents(this);
		   new PlayerFoodEvents(this);
		   new PlayerBlockEvents(this);
		   new PlayerExpEvents(this);
		   new ProjectileEvents(this);
		   new PlayerTakeDamageEvent(this);
		   new SignEvents(this);
		   new PlayerCustomFoodCookEvents(this);
		   Bukkit.getPluginManager().registerEvents(AdvInventory.getListener(), this);
		   this.getServer().getPluginManager().registerEvents(new GuiShopHandler(), this);
		   Bukkit.getPluginManager().registerEvents(AbilityUtils.getListener(), this);
		   
		   //Ability Listeners
		   
		   for(Ability ability : this.abilitymanager.getAbilities())
		   {
			   if(ability instanceof Listener)
			   {
				   Bukkit.getPluginManager().registerEvents((Listener) ability, this);
			   }
		   }
		   
		   
		   //Commands
		   this.registerCommands();
		   
	         Iterator var2 = Bukkit.getServer().getOnlinePlayers().iterator();

	         while(var2.hasNext()) {
	            Player p = (Player)var2.next();
	            this.sb.setupPlayerScoreboard(p);
	            p.closeInventory();
	            this.levelmanager.updateExpBar(p);
	            this.levelmanager.updateLevelBar(p);
	            p.setGravity(true);
	            this.ghost.removeGhost(p);
	            if (this.upgrademanager.getUpgradeAmount(p, "regeneration") > 0) {
	               RegenUpgrade.addTimer(p);
	            }
	         }

	         this.sb.runNameTagUpdater();
	         this.sb.runSidebarUpdater();
	         ShopHandler.loadShop();
		   
		   
	   }
	   
	   @Override
	   public void onDisable()
	   {
	      effectmanager.dispose();  
	      Utils.restoreBlocks();
		  MagicArrowsAbility.gravatizeArrows();
		  EquipBowAbility.onReload();
		  PillageAbility.removeItems();
		  PickPocketAbility.removeItems();
		  MountAbility.onReload();
		  
		  for(Player p : Bukkit.getOnlinePlayers())
		  {
			  AbilityUtils.resetAllMaxHealth(p);
			  AbilityUtils.removeCast(p);
		      for(Ability ability : VallendiaMinigame.getInstance().abilitymanager.getAbilities())
		      {
		    	  ability.DisableAbility(p);
		      }
				//item cooldowns
				for(ItemStack item : p.getInventory().getContents())
				{
					if(item != null)
					{
						if(item.getType() == Material.INK_SACK && item.getDurability() == 10 && item.getItemMeta().hasLore())
						{
				 		   	String ability = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities." + ChatColor.stripColor(Utils.Colorate(item.getItemMeta().getLore().get(0).toLowerCase())));
				 		   	if(AbilityCooldown.isInCooldown(p.getUniqueId(), ability))
				 		   	{
				 		   		item.setAmount(1);
				 		   	}
						}
					}
				}
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
	   
	    public void registerCommands() {
	        CommandHandler handler = new CommandHandler();
			this.getCommand("shop").setExecutor(new ShopCommand());
	        handler.register("vallendia", new VallendiaMainCommand());
	 
	        handler.register("points", new PointsCommand());
	        handler.register("admin", new AdminCommand());
	        handler.register("regen", new RegenCommand());
	        handler.register("reload", new ReloadCommand());
	        handler.register("stats", new StatsCommand());
	        handler.register("kit", new KitCommand());
	        handler.register("spawn", new SpawnCommand());
	        getCommand("vallendia").setExecutor(handler);
	    }
	    
	    private boolean setupChat() {
	        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
	        chat = rsp.getProvider();
	        return chat != null;
	    }
	    
	    private boolean setupPermissions() {
	        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
	        perms = rsp.getProvider();
	        return perms != null;
	        
	    }
	   

	   
	   
}
