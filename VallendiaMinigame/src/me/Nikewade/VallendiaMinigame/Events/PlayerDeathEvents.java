package me.Nikewade.VallendiaMinigame.Events;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
=======
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.alessiodp.parties.api.Parties;
import com.alessiodp.parties.api.interfaces.Party;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

import de.slikey.effectlib.effect.SphereEffect;
import email.com.gmail.cosmoconsole.bukkit.deathmsg.DeathMessageBroadcastEvent;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.AbilityManager;
>>>>>>> second-repo/master
import me.Nikewade.VallendiaMinigame.Abilities.BandageAbility;
import me.Nikewade.VallendiaMinigame.Abilities.EquipBowAbility;
import me.Nikewade.VallendiaMinigame.Abilities.RageAbility;
import me.Nikewade.VallendiaMinigame.Abilities.RootAbility;
import me.Nikewade.VallendiaMinigame.Abilities.SurvivalistAbility;
import me.Nikewade.VallendiaMinigame.Graphics.ScoreboardHandler;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
<<<<<<< HEAD
=======
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
>>>>>>> second-repo/master
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
<<<<<<< HEAD
=======
	      if(p.hasMetadata("NPC"))
	        {
	            return;
	        }
>>>>>>> second-repo/master
	      int pointsCarried = this.Main.shopmanager.getPoints(p);
	      int pointsSpent = this.Main.shopmanager.getPointsSpent(p);
	      int level = this.Main.levelmanager.getLevel(p);
	      this.Main.upgrademanager.resetUpgradesOnDeath(p);
	      this.Main.abilitymanager.resetAbilities(p);
<<<<<<< HEAD
=======
          double b = this.Main.levelmanager.getParameter("b");
          double n = this.Main.levelmanager.getParameter("n");
          double d = this.Main.levelmanager.getParameter("d");
>>>>>>> second-repo/master
	      for(Ability ability : VallendiaMinigame.getInstance().abilitymanager.getAbilities())
	      {
	    	  ability.DisableAbility(p);
	      }
<<<<<<< HEAD
	      RageAbility.onDie(p);
	      EquipBowAbility.onDie(p);
	      e.getDrops().removeIf(is -> drops.contains(is.getType()));
=======
	      AbilityCooldown.stopAll(p.getUniqueId());
	      EquipBowAbility.onDie(p);
	      e.getDrops().removeIf(is -> drops.contains(is.getType()));
	      e.getDrops().removeIf(is -> is.getItemMeta().hasDisplayName());
>>>>>>> second-repo/master
	      e.setDroppedExp(0);
	      p.setLevel(0);
	      p.setExp(0.0F);
	      RootAbility.removeLists(p);
	      AbilityUtils.removeAllStuns(p);
	      BandageAbility.removeBandage(p);
	      SurvivalistAbility.removeEnabled(p);
<<<<<<< HEAD
	      this.Main.playerdatamanager.addData(p.getUniqueId(), "Deaths", 1);
	      AbilityUtils.removeCast(p);
	      if (this.Main.levelmanager.getLevel(p) >= 10) {
	         AbilityUtils.explode(p.getLocation(), p, 3, 0, true, true, true);
	      }

	      if (this.Main.levelmanager.getLevel(p) >= 15) {
	         p.getWorld().strikeLightningEffect(p.getLocation());
	         p.getWorld().setStorm(true);
	         p.getWorld().setWeatherDuration(400);
	         p.getWorld().setThundering(true);
	         p.getWorld().setThunderDuration(400);
	      }

	      if (p.getKiller() != null && p.getKiller() instanceof Player && p.getKiller() != p) {
	         if (pointsCarried + pointsSpent < 0) {
	            this.Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", pointsCarried + pointsSpent);
	            p.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
	            p.sendMessage("");
	            Language.sendCentredMessage(p, Utils.Colorate("&c&lYou died"));
	            Language.sendCentredMessage(p, Utils.Colorate("&3Kit: " + this.Main.kitmanager.getKit(p).getName(true)));
	            Language.sendCentredMessage(p, Utils.Colorate("&3Level: " + this.Main.levelmanager.getLevel(p)));
	            Language.sendCentredMessage(p, Utils.Colorate("&3Points lost: " + (pointsCarried - this.Main.shopmanager.getPoints(p))));
	            p.sendMessage("");
	            p.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));
	         } else {
	            int N = this.Main.levelmanager.getParameter("n");
	            Player killer = p.getKiller();
	            int levelKilledBy = this.Main.levelmanager.getLevel(killer);
	            int points = (int)((double)(pointsCarried + pointsSpent) * (1.0D / Math.pow((double)level, 0.35D)) * (-0.45D * (double)(level - levelKilledBy - N) / Math.sqrt(Math.pow((double)(level - levelKilledBy - N), 2.0D) + Math.pow((double)N, 2.0D)) + 0.55D));
	            this.Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", points);
=======
	      AbilityManager.removeAllAbilityData(p);
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
		         //PARTY AVERAGE LEVEL
		         if(AbilityUtils.getPlayerParty(killer) != "")
		         {
		        	Party party = Parties.getApi().getParty(AbilityUtils.getPlayerParty(killer));
		        	levelKilledBy = (int) party.getExperience();
		         }
		        double percentageFromEq;
		        
		        if(level >= levelKilledBy)
		        {
		        	percentageFromEq =  0.85 * (Math.pow(( 10), -((0.03 * level) * (level-1) / levelKilledBy))) + 0.02;
		        }else
		        {
		        	percentageFromEq = 1 - (0.99 * Math.pow( Math.E, (- 2.05 * levelKilledBy) / Math.pow(level, 1.45))) - 0.01;
		        }
		        
		        
	            double points = (pointsCarried + (b * pointsSpent)) * 0.9 * percentageFromEq;
		        double pointsLost = pointsCarried * (1- 0.9 * percentageFromEq);
		        if(pointsLost <= 0)
		        {
		        	pointsLost = pointsLost - 1;
		        }
		        double pointsRefunded = (b * pointsSpent) * 0.9 * percentageFromEq;
	            this.Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", (int) points);
>>>>>>> second-repo/master
	            this.Main.playerdatamanager.editIntData(p.getUniqueId(), "PointsSpent", 0);
	            p.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
	            p.sendMessage("");
	            Language.sendCentredMessage(p, Utils.Colorate("&c&lYou died"));
<<<<<<< HEAD
	            Language.sendCentredMessage(p, Utils.Colorate("&3Kit: " + this.Main.kitmanager.getKit(p).getName(true)));
	            Language.sendCentredMessage(p, Utils.Colorate("&3Level: " + this.Main.levelmanager.getLevel(p)));
	            Language.sendCentredMessage(p, Utils.Colorate("&3Points lost: " + (pointsCarried - this.Main.shopmanager.getPoints(p))));
	            p.sendMessage("");
	            p.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));
	         }
	      } else {
	         this.Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", (int)((double)(pointsCarried + pointsSpent) * (1.0D / Math.pow((double)level, 0.35D))));
=======
	            Language.sendCentredMessage(p, Utils.Colorate("&3Level: " + this.Main.levelmanager.getLevel(p)));
	            Language.sendCentredMessage(p, Utils.Colorate("&3Class: " + this.Main.kitmanager.getKit(p).getName(true)));
	            Language.sendCentredMessage(p, Utils.Colorate("&3Essence Lost: " + (int) (pointsLost + 1)));
	            Language.sendCentredMessage(p, Utils.Colorate("&3Essence Returned: " + (int) pointsRefunded));
		        p.sendMessage("");
	            p.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));
	         }else 
	         {
	         d = 0.85 * (Math.pow(( 10), -(0.03 * (level-1)))) + 0.02;
	         // d is different so amount lossed is basedo n your level
	         double points = ((pointsCarried + (b * pointsSpent)) * d); 
	         this.Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", (int) points);
	         /*
	          * where d = decimal of points to give back, default d = .8
	          */
>>>>>>> second-repo/master
	         this.Main.playerdatamanager.editIntData(p.getUniqueId(), "PointsSpent", 0);
	         p.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
	         p.sendMessage("");
	         Language.sendCentredMessage(p, Utils.Colorate("&c&lYou died"));
<<<<<<< HEAD
	         Language.sendCentredMessage(p, Utils.Colorate("&3Kit: " + this.Main.kitmanager.getKit(p).getName(true)));
	         Language.sendCentredMessage(p, Utils.Colorate("&3Level: " + this.Main.levelmanager.getLevel(p)));
	         Language.sendCentredMessage(p, Utils.Colorate("&3Points lost: " + (pointsCarried - this.Main.shopmanager.getPoints(p))));
=======
	         Language.sendCentredMessage(p, Utils.Colorate("&3Level: " + this.Main.levelmanager.getLevel(p)));
	         Language.sendCentredMessage(p, Utils.Colorate("&3Class: " + this.Main.kitmanager.getKit(p).getName(true)));
	         double pointsLost = (pointsCarried * (1-d)) + 1;
	         if(pointsCarried < 1)
	         {
	        	 pointsLost = 0;
	         }
	         double pointsRefunded = b * pointsSpent * d;
	         Language.sendCentredMessage(p, Utils.Colorate("&3Essence Lost: " + (int) pointsLost));
	         Language.sendCentredMessage(p, Utils.Colorate("&3Essence Returned: " + (int) pointsRefunded));
>>>>>>> second-repo/master
	         p.sendMessage("");
	         p.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));
	      }
	   }

	   @EventHandler
	   public void onRespawn(PlayerRespawnEvent e) {
	      Player p = e.getPlayer();
<<<<<<< HEAD
	      this.Main.kitmanager.giveRespawnKit(p, "starter");
	      ScoreboardHandler.updateHealth(p, 0, 0);
	   }
=======
		  p.setMaxHealth(20);
	      this.Main.kitmanager.giveRespawnKit(p, "starter");
	      ScoreboardHandler.updateHealth(p, 0, 0);
			Location loc = new Location(p.getWorld(), -107.5, 65, -638.5, 0.0F, 0.0F);
			e.setRespawnLocation(loc);
			p.setFoodLevel(19);
	   }
	   
	   
	   @EventHandler
	   public void npcDeath(DeathMessageBroadcastEvent e)
	   {
		      if(e.getPlayer().hasMetadata("NPC"))
		        {
		            e.setCancelled(true);
		        }
	   }
	   
	   
	   
	   
	   
>>>>>>> second-repo/master
	}