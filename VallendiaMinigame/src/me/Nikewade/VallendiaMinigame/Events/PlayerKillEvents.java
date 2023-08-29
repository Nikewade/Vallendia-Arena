package me.Nikewade.VallendiaMinigame.Events;

<<<<<<< HEAD
import java.util.UUID;

import org.bukkit.entity.Creature;
=======
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
>>>>>>> second-repo/master
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
<<<<<<< HEAD

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
=======
import org.bukkit.scheduler.BukkitRunnable;

import com.alessiodp.parties.api.Parties;
import com.alessiodp.parties.api.interfaces.Party;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
>>>>>>> second-repo/master
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
<<<<<<< HEAD
	      if (p.getKiller() != null && p.getKiller() instanceof Player && p.getKiller() != p) {
	         int points = 0;
	         int M = this.Main.levelmanager.getParameter("m");
	         int R = this.Main.levelmanager.getParameter("r");
	         int P = this.Main.levelmanager.getParameter("p");
	         int L = this.Main.levelmanager.getParameter("l");
	         Player killer = p.getKiller();
	         int levelKilled = this.Main.levelmanager.getLevel(p);
	         int level = this.Main.levelmanager.getLevel(killer);
	         UUID uuid = killer.getUniqueId();
	         this.Main.playerdatamanager.addData(uuid, "Kills", 1);
	         this.Main.playerdatamanager.addData(uuid, "KillStreak", 1);
	         if (levelKilled >= level) {
	            points = -(M - R) * level + M * levelKilled + P - R;
	         }

	         if (levelKilled < level) {
	            points = -(levelKilled * R + P - 1) * (level - levelKilled) / L + levelKilled * R + P - R;
	         }

	         if (points < 1) {
	            points = 1;
	         }

	         this.Main.playerdatamanager.addData(uuid, "Points", points);
	         killer.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
	         killer.sendMessage("");
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Player killed: " + p.getName()));
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Kit: " + this.Main.kitmanager.getKit(p).getName(true)));
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Level: " + this.Main.levelmanager.getLevel(p)));
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Points gained: " + points));
	         killer.sendMessage("");
	         killer.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));
=======
	      if(p.hasMetadata("NPC"))
	        {
	            return;
	        }
	      if (p.getKiller() != null && p.getKiller() instanceof Player && p.getKiller() != p) {
	         double points = 0;
	         Player killer = p.getKiller();
	         int level = this.Main.levelmanager.getLevel(p);
	         int levelKilledBy = this.Main.levelmanager.getLevel(killer);
	         
	         //PARTY AVERAGE LEVEL
	         if(AbilityUtils.getPlayerParty(killer) != "")
	         {
	        	Party party = Parties.getApi().getParty(AbilityUtils.getPlayerParty(killer));
	        	levelKilledBy = (int) party.getExperience();
	         }
	         
	         UUID uuid = killer.getUniqueId();
	         this.Main.playerdatamanager.addData(uuid, "KillStreak", 1);
	         int kills = Main.playerdatamanager.getPlayerIntData(uuid, "KillStreak");
	         this.Main.playerdatamanager.addData(uuid, "Kills", 1);
	         if (levelKilledBy >= level) {
	            points = (float) (15 * ((Math.pow(level, 2) / levelKilledBy))) * 3;
	         }

	         if (levelKilledBy < level) {
	            points = 3 *((15 * level)) + Math.pow(level, 1.9) * (level - levelKilledBy);
	         }

	         if (points <= 0) {
	            points = 0;
	         }
	         
        	 if(kills >= 5)
        	 {
        		points =  points  * ( 1 + (0.03 * (kills - 4)));
        	 }
        	 
        	 if(AbilityUtils.getPlayerParty(killer) != "")
        	 {
        		 points = PlayerKillEvents.shareEssence(killer, (int) points, true, "");
        	 }
        	 
	         this.Main.playerdatamanager.addData(uuid, "Points", (int) points);
	         killer.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
	         killer.sendMessage("");
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Player killed: " + p.getName()));
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Level: " + this.Main.levelmanager.getLevel(p)));
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Class: " + this.Main.kitmanager.getKit(p).getName(true)));
	         Language.sendCentredMessage(killer, Utils.Colorate("&3Essence gained: " + (int) points));
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
>>>>>>> second-repo/master
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
<<<<<<< HEAD
=======
	   
	   
	   
	   
	   
	   public static int shareEssence(Player p, int amount, boolean killer, String string)
	   {
		   if(AbilityUtils.getPlayerParty(p) != "")
		   {
			   double killerMult = 0.25;
			   double perMember = 0.10;
			   
			   Party party = Parties.getApi().getParty(AbilityUtils.getPlayerParty(p));
			   List<UUID> list = new ArrayList<>();
			   list.remove(p);
			   int partyTotal = party.getMembers().size() - 1;
			   if(partyTotal <= 0)
			   {
				   return amount;
			   }
			   for(UUID uuid : party.getMembers())
			   {
				   if(Bukkit.getPlayer(uuid) == null)
				   {
						   partyTotal--;
						   list.remove(Bukkit.getOfflinePlayer(uuid));
						   continue;
				   }
				   Player pl = Bukkit.getPlayer(uuid);
				   if(pl == p)
				   {
					   continue;
				   }
				   if(p.getLocation().distance(pl.getLocation()) > 50)
				   {
					   partyTotal--;
					   list.remove(pl);
					   continue;
				   }
				   list.add(pl.getUniqueId());
			   }
			   double newAmount = (amount * (1 + (perMember * partyTotal))) / (partyTotal + 1);
			   if(newAmount <= 1)
			   {
				   newAmount = 1;
			   }
			   
			   for(UUID uuid : list)
			   {
				   Player pl = Bukkit.getPlayer(uuid);
				   VallendiaMinigame.getInstance().shopmanager.addPoints(pl, (int) newAmount);
				   pl.sendMessage(Utils.Colorate( "&8&l[&3Party Essence&8&l] &8+" + (int) newAmount + " " + string));   
			   }
			   if(killer)
			   {
				   if(!(partyTotal <= 0))
				   {
					   newAmount = newAmount * (1 + killerMult);   
				   }
			   }
			   
			   
			   return (int) newAmount;
			   
		   }
		return amount;
	   }
	   
		
		@EventHandler
		public void mmdeath(MythicMobDeathEvent e)
		{
			if(e.getMob().getSpawner() != null)
			{
				new BukkitRunnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
							e.getMob().getSpawner().setWarmupSeconds( (int) (PlayerBlockEvents.regenTime / 2 ));	
					}
					
				}.runTaskLater(VallendiaMinigame.getInstance(), 30);	
			}
		}
	   
	   
>>>>>>> second-repo/master
	}