package me.Nikewade.VallendiaMinigame.Events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class PlayerKillEvents implements Listener {
	   VallendiaMinigame Main;

	   public PlayerKillEvents(VallendiaMinigame Main) {
	      this.Main = Main;
	      Main.getServer().getPluginManager().registerEvents(this, Main);
	   }

	   @EventHandler
	   public void onPlayerKill(PlayerDeathEvent e) {
	      Player p = e.getEntity();
	      if (p.getKiller() != null && p.getKiller() instanceof Player && p.getKiller() != p) {
	         double points = 0;
	         Player killer = p.getKiller();
	         int level = this.Main.levelmanager.getLevel(p);
	         int levelKilledBy = this.Main.levelmanager.getLevel(killer);
	         UUID uuid = killer.getUniqueId();
	         this.Main.playerdatamanager.addData(uuid, "KillStreak", 1);
	         int kills = Main.playerdatamanager.getPlayerIntData(uuid, "KillStreak");
	         this.Main.playerdatamanager.addData(uuid, "Kills", 1);
	         if (levelKilledBy >= level) {
	            points = (float) (15 * ((Math.pow(level, 2) / levelKilledBy))) * 1.6;
	         }

	         if (levelKilledBy < level) {
	            points = ((15 * level) + 40 * (level - levelKilledBy)) * 1.6;
	         }

	         if (points <= 0) {
	            points = 0;
	         }
	         
        	 if(kills >= 5)
        	 {
        		points =  points  * ( 1 + (0.01 * (kills - 4)));
        	 }

	         this.Main.playerdatamanager.addData(uuid, "Points", (int) points);
	         killer.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
	         killer.sendMessage("");
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Player killed: " + p.getName()));
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Kit: " + this.Main.kitmanager.getKit(p).getName(true)));
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Level: " + this.Main.levelmanager.getLevel(p)));
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Points gained: " + (int) points));
	         killer.sendMessage("");
	         killer.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));
	         
	         
	         killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BELL, 1, 1.2F);
	         
	         
	         
	         //KILL STREAK MESSAGES
	         
		        new BukkitRunnable()
		         {

					@Override
					public void run() {
				        	 
						
			        	 if(kills == 5)
			        	 {
				        	 Language.sendVallendiaBroadcast(ChatColor.RED + killer.getName() + ChatColor.RED + " is on a streak of 5 kills!");
					         killer.playSound(killer.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 1, 0.8F);
				        	 return;
			        	 }
				        	 
				        	 if(kills == 15)
				        	 {
					        	 Language.sendVallendiaBroadcast(ChatColor.RED + killer.getName() + ChatColor.RED + " is on a streak of 15 kills!");
						         killer.playSound(killer.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 1, 0.8F);
					        	 return;
				        	 }
				        	 
				        	 if(kills % 10 == 0)
				        	 {
					        	 Language.sendVallendiaBroadcast(ChatColor.RED + killer.getName() + ChatColor.RED + " is on a streak of " + kills+  " kills!");
						         killer.playSound(killer.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 1, 0.8F);
					        	 return;
				        	 }

				        	 
				        	 
						
					}
		        	 
		         }.runTaskLater(VallendiaMinigame.getInstance(), 10);
	      }

	   }
	   
	   
	   @EventHandler
	   public void killMobEvent(EntityDeathEvent e)
	   {
		   if(e.getEntity() instanceof Creature)
		   {
			   e.setDroppedExp(0);
		   }
	   }
	   
	   
	   
	   
	}