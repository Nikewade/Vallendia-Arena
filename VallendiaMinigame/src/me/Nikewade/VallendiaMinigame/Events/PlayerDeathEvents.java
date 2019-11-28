package me.Nikewade.VallendiaMinigame.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.BandageAbility;
import me.Nikewade.VallendiaMinigame.Abilities.EquipBowAbility;
import me.Nikewade.VallendiaMinigame.Abilities.RageAbility;
import me.Nikewade.VallendiaMinigame.Abilities.RootAbility;
import me.Nikewade.VallendiaMinigame.Abilities.SurvivalistAbility;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class PlayerDeathEvents implements Listener {
	   VallendiaMinigame Main;

	   public PlayerDeathEvents(VallendiaMinigame Main) {
	      this.Main = Main;
	      Main.getServer().getPluginManager().registerEvents(this, Main);
	   }

	   @EventHandler
	   public void onDeath(PlayerDeathEvent e) {
	      Player p = e.getEntity();
	      int pointsCarried = this.Main.shopmanager.getPoints(p);
	      int pointsSpent = this.Main.shopmanager.getPointsSpent(p);
	      int level = this.Main.levelmanager.getLevel(p);
	      this.Main.upgrademanager.resetUpgradesOnDeath(p);
	      this.Main.abilitymanager.resetAbilities(p);
	      for(Ability ability : VallendiaMinigame.getInstance().abilitymanager.getAbilities())
	      {
	    	  ability.DisableAbility(p);
	      }
	      RageAbility.onDie(p);
	      EquipBowAbility.onDie(p);
	      e.getDrops().clear();
	      e.setDroppedExp(0);
	      p.setLevel(0);
	      p.setExp(0.0F);
	      RootAbility.removeLists(p);
	      BandageAbility.removeBandage(p);
	      SurvivalistAbility.removeEnabled(p);
	      this.Main.playerdatamanager.addData(p.getUniqueId(), "Deaths", 1);
	      AbilityUtils.removeCast(p);
	      AbilityUtils.removeSoftCast(p);
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
	            this.Main.playerdatamanager.editIntData(p.getUniqueId(), "PointsSpent", 0);
	            p.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
	            p.sendMessage("");
	            Language.sendCentredMessage(p, Utils.Colorate("&c&lYou died"));
	            Language.sendCentredMessage(p, Utils.Colorate("&3Kit: " + this.Main.kitmanager.getKit(p).getName(true)));
	            Language.sendCentredMessage(p, Utils.Colorate("&3Level: " + this.Main.levelmanager.getLevel(p)));
	            Language.sendCentredMessage(p, Utils.Colorate("&3Points lost: " + (pointsCarried - this.Main.shopmanager.getPoints(p))));
	            p.sendMessage("");
	            p.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));
	         }
	      } else {
	         this.Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", (int)((double)(pointsCarried + pointsSpent) * (1.0D / Math.pow((double)level, 0.35D))));
	         this.Main.playerdatamanager.editIntData(p.getUniqueId(), "PointsSpent", 0);
	         p.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
	         p.sendMessage("");
	         Language.sendCentredMessage(p, Utils.Colorate("&c&lYou died"));
	         Language.sendCentredMessage(p, Utils.Colorate("&3Kit: " + this.Main.kitmanager.getKit(p).getName(true)));
	         Language.sendCentredMessage(p, Utils.Colorate("&3Level: " + this.Main.levelmanager.getLevel(p)));
	         Language.sendCentredMessage(p, Utils.Colorate("&3Points lost: " + (pointsCarried - this.Main.shopmanager.getPoints(p))));
	         p.sendMessage("");
	         p.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));
	      }
	   }

	   @EventHandler
	   public void onRespawn(PlayerRespawnEvent e) {
	      Player p = e.getPlayer();
	      this.Main.kitmanager.giveRespawnKit(p, "starter");
	   }
	}