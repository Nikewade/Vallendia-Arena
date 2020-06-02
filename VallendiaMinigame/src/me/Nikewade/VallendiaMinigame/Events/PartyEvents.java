package me.Nikewade.VallendiaMinigame.Events;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.alessiodp.parties.api.Parties;
import com.alessiodp.parties.api.events.bukkit.party.BukkitPartiesPartyPostCreateEvent;
import com.alessiodp.parties.api.events.bukkit.player.BukkitPartiesPlayerPostJoinEvent;
import com.alessiodp.parties.api.events.bukkit.player.BukkitPartiesPlayerPostLeaveEvent;
import com.alessiodp.parties.api.interfaces.Party;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class PartyEvents implements Listener {
	   VallendiaMinigame Main;
	   HashMap<Player, Party> noPvp = new HashMap<>();

	   public PartyEvents(VallendiaMinigame Main) {
	      this.Main = Main;
	      Main.getServer().getPluginManager().registerEvents(this, Main);
	   }
	   
	   
	   
	   @EventHandler
	   public void onJoin(BukkitPartiesPlayerPostJoinEvent e)
	   {
		   int highestLevel = 	VallendiaMinigame.getInstance().levelmanager.getLevel(Bukkit.getPlayer(e.getParty().getLeader()));
       	for(UUID uuid : e.getParty().getMembers())
		{
			   if(Bukkit.getPlayer(uuid) == null)
			   {
					   continue;
			   }
			Player pl = Bukkit.getPlayer(uuid);
			pl.playSound(pl.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
			if(pl == Bukkit.getPlayer(e.getParty().getLeader()))
			{
				continue;
			}
			int plevel = VallendiaMinigame.getInstance().levelmanager.getLevel(pl);
			if(plevel > highestLevel){
				highestLevel = plevel;
			}
		}
       	e.getParty().setExperience(highestLevel);
	   }

	   
	   
	   @EventHandler
	   public void onLeave(BukkitPartiesPlayerPostLeaveEvent e)
	   {
		   int highestLevel = 	VallendiaMinigame.getInstance().levelmanager.getLevel(Bukkit.getPlayer(e.getParty().getLeader()));
	       	for(UUID uuid : e.getParty().getMembers())
			{
				   if(Bukkit.getPlayer(uuid) == null)
				   {
						   continue;
				   }
				Player pl = Bukkit.getPlayer(uuid);
				pl.playSound(pl.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
				if(pl == Bukkit.getPlayer(e.getParty().getLeader()))
				{
					continue;
				}
				int plevel = VallendiaMinigame.getInstance().levelmanager.getLevel(pl);
				if(plevel > highestLevel){
					highestLevel = plevel;
				}
			}
	       	e.getParty().setExperience(highestLevel);
	       Bukkit.getPlayer(e.getPartyPlayer().getPlayerUUID()).playSound(Bukkit.getPlayer(e.getPartyPlayer().getPlayerUUID()).getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
	       	
	       	
	       	
	   }
	   
	   
	   @EventHandler
	   public void onCreate(BukkitPartiesPartyPostCreateEvent e)
	   {
		   int highestLevel = 	VallendiaMinigame.getInstance().levelmanager.getLevel(Bukkit.getPlayer(e.getParty().getLeader()));
	       	for(UUID uuid : e.getParty().getMembers())
			{
				   if(Bukkit.getPlayer(uuid) == null)
				   {
						   continue;
				   }
				Player pl = Bukkit.getPlayer(uuid);
				if(pl == Bukkit.getPlayer(e.getParty().getLeader()))
				{
					continue;
				}
				int plevel = VallendiaMinigame.getInstance().levelmanager.getLevel(pl);
				if(plevel > highestLevel){
					highestLevel = plevel;
				}
			}
	       	e.getParty().setExperience(highestLevel);
	   }
	   
	   
	   public static void setPartyLevel(Player p)
	   {
		   if(AbilityUtils.getPlayerParty(p) != "")
		   {
			   Party party = Parties.getApi().getParty(AbilityUtils.getPlayerParty(p));
			   int highestLevel = 	VallendiaMinigame.getInstance().levelmanager.getLevel(Bukkit.getPlayer(party.getLeader()));
		       	for(UUID uuid : party.getMembers())
				{
					   if(Bukkit.getPlayer(uuid) == null)
					   {
							   continue;
					   }
					Player pl = Bukkit.getPlayer(uuid);
					if(pl == Bukkit.getPlayer(party.getLeader()))
					{
						continue;
					}
					int plevel = VallendiaMinigame.getInstance().levelmanager.getLevel(pl);
					if(plevel > highestLevel){
						highestLevel = plevel;
					}
				}
		       	party.setExperience(highestLevel);
		   }
	   }
	   
	   
	   public static void setPartyLevelMinusOriginPlayer(Player p)
	   {
		   if(AbilityUtils.getPlayerParty(p) != "")
		   {
			   Party party = Parties.getApi().getParty(AbilityUtils.getPlayerParty(p));
			   int highestLevel = 	VallendiaMinigame.getInstance().levelmanager.getLevel(Bukkit.getPlayer(party.getLeader()));
			   if(Bukkit.getPlayer(party.getLeader()) == p)
			   {
				   highestLevel = 0;
			   }
		       	for(UUID uuid : party.getMembers())
				{
					   if(Bukkit.getPlayer(uuid) == null)
					   {
							   continue;
					   }
					Player pl = Bukkit.getPlayer(uuid);
					if(pl == Bukkit.getPlayer(party.getLeader()) || pl == p)
					{
						continue;
					}
					int plevel = VallendiaMinigame.getInstance().levelmanager.getLevel(pl);
					if(plevel > highestLevel){
						highestLevel = plevel;
					}
				}
		       	party.setExperience(highestLevel);
		   }
	   }
}
