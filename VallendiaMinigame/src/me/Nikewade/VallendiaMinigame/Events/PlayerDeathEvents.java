package me.Nikewade.VallendiaMinigame.Events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

import de.slikey.effectlib.effect.AtomEffect;
import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.BandageAbility;
import me.Nikewade.VallendiaMinigame.Abilities.EquipBowAbility;
import me.Nikewade.VallendiaMinigame.Abilities.RageAbility;
import me.Nikewade.VallendiaMinigame.Abilities.RootAbility;
import me.Nikewade.VallendiaMinigame.Abilities.SurvivalistAbility;
import me.Nikewade.VallendiaMinigame.Graphics.ScoreboardHandler;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class PlayerDeathEvents implements Listener {
	   VallendiaMinigame Main;
	   public static List<Material> drops = new ArrayList<>();

	   public PlayerDeathEvents(VallendiaMinigame Main) {
	      this.Main = Main;
	      Main.getServer().getPluginManager().registerEvents(this, Main);
	      
	      
	      drops.add(Material.STICK);
	      drops.add(Material.DIAMOND_SWORD);
	      drops.add(Material.GOLD_SWORD);
	      drops.add(Material.IRON_SWORD);
	      drops.add(Material.STONE_SWORD);
	      drops.add(Material.WOOD_SWORD);
	      drops.add(Material.BOW);
	      
	      drops.add(Material.DIAMOND_PICKAXE);
	      drops.add(Material.IRON_PICKAXE);
	      drops.add(Material.GOLD_PICKAXE);
	      drops.add(Material.STONE_PICKAXE);
	      drops.add(Material.WOOD_PICKAXE);
	      
	      drops.add(Material.NETHER_STAR);
	      drops.add(Material.INK_SACK);
	      
	      drops.add(Material.CHAINMAIL_HELMET);
	      drops.add(Material.DIAMOND_HELMET);
	      drops.add(Material.IRON_HELMET);
	      drops.add(Material.GOLD_HELMET);
	      drops.add(Material.LEATHER_HELMET);
	      drops.add(Material.CHAINMAIL_CHESTPLATE);
	      drops.add(Material.DIAMOND_CHESTPLATE);
	      drops.add(Material.IRON_CHESTPLATE);
	      drops.add(Material.GOLD_CHESTPLATE);
	      drops.add(Material.LEATHER_CHESTPLATE);
	      drops.add(Material.CHAINMAIL_LEGGINGS);
	      drops.add(Material.DIAMOND_LEGGINGS);
	      drops.add(Material.IRON_LEGGINGS);
	      drops.add(Material.GOLD_LEGGINGS);
	      drops.add(Material.LEATHER_LEGGINGS);
	      drops.add(Material.CHAINMAIL_BOOTS);
	      drops.add(Material.DIAMOND_BOOTS);
	      drops.add(Material.IRON_BOOTS);
	      drops.add(Material.GOLD_BOOTS);
	      drops.add(Material.LEATHER_BOOTS);
	   }

	   @EventHandler
	   public void onDeath(PlayerDeathEvent e) {
	      Player p = e.getEntity();
	      int pointsCarried = this.Main.shopmanager.getPoints(p);
	      int pointsSpent = this.Main.shopmanager.getPointsSpent(p);
	      int level = this.Main.levelmanager.getLevel(p);
	      this.Main.upgrademanager.resetUpgradesOnDeath(p);
	      this.Main.abilitymanager.resetAbilities(p);
          double b = this.Main.levelmanager.getParameter("b");
          double n = this.Main.levelmanager.getParameter("n");
          double d = this.Main.levelmanager.getParameter("d");
	      for(Ability ability : VallendiaMinigame.getInstance().abilitymanager.getAbilities())
	      {
	    	  ability.DisableAbility(p);
	      }
	      AbilityCooldown.stopAll(p.getUniqueId());
	      RageAbility.onDie(p);
	      EquipBowAbility.onDie(p);
	      e.getDrops().removeIf(is -> drops.contains(is.getType()));
	      e.setDroppedExp(0);
	      p.setLevel(0);
	      p.setExp(0.0F);
		  p.setMaxHealth(20);
	      RootAbility.removeLists(p);
	      AbilityUtils.removeAllStuns(p);
	      BandageAbility.removeBandage(p);
	      SurvivalistAbility.removeEnabled(p);
	      this.Main.playerdatamanager.addData(p.getUniqueId(), "Deaths", 1);
	      AbilityUtils.removeCast(p);
	      
	      	double radius = 1.5;
	      	int particles = 15;
	      	if(this.Main.levelmanager.getLevel(p) >= 10)
	      	{
		      	if(this.Main.levelmanager.getLevel(p) >= 15)
		      	{
		      		radius = 5;
		      	}else
		      	{
		      		radius = 4;
		      	}
	      	}
	      	
	        SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
	        se.particle = Particle.REDSTONE;
	        se.color = Color.BLUE;
	        se.radius = radius;
	        se.particles = (int) (15 * radius);
	        se.speed = (float) 0;  
	        se.iterations = 1;
	        se.visibleRange = 50;
	        
	         
	        se.setLocation(p.getLocation().add(0, 1, 0));
	        
	        se.start();
	        
	        
	        SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
	        se2.particle = Particle.REDSTONE;
	        se2.color = Color.TEAL;
	        se2.radius = radius;
	        se2.particles = (int) (15 * radius);
	        se2.speed = (float) 0;  
	        se2.iterations = 1;
	        se2.visibleRange = 50;
	        
	         
	        se2.setLocation(p.getLocation().add(0, 1, 0));
	        
	        se2.start();
	      
	      if (this.Main.levelmanager.getLevel(p) >= 10) {
  		   	RegionManager regionManager = Main.worldguard.getRegionManager(p.getWorld());
  		   	ApplicableRegionSet arset = regionManager.getApplicableRegions(p.getLocation());
  		   	LocalPlayer localPlayer = Main.worldguard.wrapPlayer(p);
  		   	if(arset.allows((StateFlag) VallendiaMinigame.blockAbilities, localPlayer))
  		   	{
  		         AbilityUtils.explode(p.getLocation(), p, 3, 0, true, true, true);
  		   	}
	      }

	      if (this.Main.levelmanager.getLevel(p) >= 15) {
	        	 for(Player player : p.getWorld().getPlayers())
	        	 {
	        		 Language.sendVallendiaMessage(player, Utils.Colorate("&c&l"+ p.getName() + " just died at level " + 
	        	 VallendiaMinigame.getInstance().levelmanager.getLevel(p) + "!"));
	        	 }
	         p.getWorld().strikeLightningEffect(p.getLocation());
	         p.getWorld().setStorm(true);
	         p.getWorld().setWeatherDuration(1000);
	         p.getWorld().setThundering(true);
	         p.getWorld().setThunderDuration(1000);
	         
	         
	         if(this.Main.levelmanager.getLevel(p) == 20)
	         {
	        	 for(Player player : p.getWorld().getPlayers())
	        	 {
     		        player.sendTitle(Utils.Colorate("&c&l"+ p.getName() + " died!"), null, 20, 100, 20);
	        	 }
	         }
	      }

	      if (p.getKiller() != null && p.getKiller() instanceof Player && p.getKiller() != p) {
	            Player killer = p.getKiller();
	            int levelKilledBy = this.Main.levelmanager.getLevel(killer);
	            float sumOfLvls = (float) ((float) level / (float) levelKilledBy);
	            double points = ((pointsCarried + (b * pointsSpent)) * Math.pow(Math.E, (-n * sumOfLvls)));
	            /*
	             * where c is points carried
					s is points spent
					b is the decimal multiplier of points spent to consider, should be between 0 and 1
					n is the exponential multiplier, this determines how quickly points decay as killer level goes down, also determines percentage of points to give back to someone of equal level to their killer
					default values b = 1, n = 0.916
	             */
	            this.Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", (int) points);
	            this.Main.playerdatamanager.editIntData(p.getUniqueId(), "PointsSpent", 0);
	            p.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
	            p.sendMessage("");
	            Language.sendCentredMessage(p, Utils.Colorate("&c&lYou died"));
	            Language.sendCentredMessage(p, Utils.Colorate("&3Kit: " + this.Main.kitmanager.getKit(p).getName(true)));
	            Language.sendCentredMessage(p, Utils.Colorate("&3Level: " + this.Main.levelmanager.getLevel(p)));
		        double pointsLost = pointsCarried * (1- (Math.pow(Math.E, -n* (sumOfLvls))));
		        if(pointsLost <= 0)
		        {
		        	pointsLost = pointsLost - 1;
		        }
		        double pointsRefunded = (b * pointsSpent) * (Math.pow(Math.E, -n *(sumOfLvls)));
	            Language.sendCentredMessage(p, Utils.Colorate("&3Points lost: " + (int) (pointsLost + 1)));
	            Language.sendCentredMessage(p, Utils.Colorate("&3Points Refunded: " + (int) pointsRefunded));
	            p.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));
	         }else 
	         {
	         double points = ((pointsCarried + (b * pointsSpent)) * d); 
	         this.Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", (int) points);
	         /*
	          * where d = decimal of points to give back, default d = .8
	          */
	         this.Main.playerdatamanager.editIntData(p.getUniqueId(), "PointsSpent", 0);
	         p.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
	         p.sendMessage("");
	         Language.sendCentredMessage(p, Utils.Colorate("&c&lYou died"));
	         Language.sendCentredMessage(p, Utils.Colorate("&3Kit: " + this.Main.kitmanager.getKit(p).getName(true)));
	         Language.sendCentredMessage(p, Utils.Colorate("&3Level: " + this.Main.levelmanager.getLevel(p)));
	         double pointsLost = (pointsCarried * (1-d)) + 1;
	         if(pointsCarried < 1)
	         {
	        	 pointsLost = 0;
	         }
	         double pointsRefunded = b * pointsSpent * d;
	         Language.sendCentredMessage(p, Utils.Colorate("&3Points lost: " + (int) pointsLost));
	         Language.sendCentredMessage(p, Utils.Colorate("&3Points Refunded: " + (int) pointsRefunded));
	         p.sendMessage("");
	         p.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));
	      }
	   }

	   @EventHandler
	   public void onRespawn(PlayerRespawnEvent e) {
	      Player p = e.getPlayer();
	      this.Main.kitmanager.giveRespawnKit(p, "starter");
	      ScoreboardHandler.updateHealth(p, 0, 0);
	   }
	}