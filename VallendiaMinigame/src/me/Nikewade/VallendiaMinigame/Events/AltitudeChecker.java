package me.Nikewade.VallendiaMinigame.Events;

<<<<<<< HEAD
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.WorldBorderAction;
=======
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

>>>>>>> second-repo/master
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
<<<<<<< HEAD
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class AltitudeChecker {
	static VallendiaMinigame Main;
	
	 public AltitudeChecker(VallendiaMinigame Main)
	  {
=======

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class AltitudeChecker implements Listener{
	static VallendiaMinigame Main;
	int altitude = 130;
	
	 public AltitudeChecker(VallendiaMinigame Main)
	  {
		 altitude = Main.getConfig().getInt("Options.altitude");
>>>>>>> second-repo/master
		AltitudeChecker.Main = Main; 
	   new BukkitRunnable() {

            @SuppressWarnings("deprecation")
			@Override
            public void run() {	
            		for(Player p : Bukkit.getOnlinePlayers())
            		{
<<<<<<< HEAD
=======
            			if(!p.getLocation().getWorld().getName().equalsIgnoreCase("Minigame2"))
            			{
            				continue;
            			}
            			if(p.getLocation().getY() > altitude)
            			{
>>>>>>> second-repo/master
    	    		   	RegionManager regionManager = Main.worldguard.getRegionManager(p.getWorld());
    	    		   	ApplicableRegionSet arset = regionManager.getApplicableRegions(p.getLocation());
    	    		   	LocalPlayer localPlayer = Main.worldguard.wrapPlayer(p);
    	    		   	if(!arset.allows((StateFlag) VallendiaMinigame.checkAltitude, localPlayer))
    	    		   	{
    	    		   		continue;
    	    		   	}
<<<<<<< HEAD
            			if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude"))
            			{
=======
>>>>>>> second-repo/master
            				if(p.getGameMode() != GameMode.CREATIVE)
            				{
                				AltitudeChecker.altTooHigh(p);
                		        p.sendTitle(Utils.Colorate("&4&lToo high!"), null, 20, 1, 1);
<<<<<<< HEAD
                		        if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude") + 5)
                		        {
                		 	 		AbilityUtils.damageEntity(p, p, 2);
                		        }
                		        
                		        if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude") + 15)
                		        {
                		 	 		AbilityUtils.damageEntity(p, p, 4);
                		        }
                		        
                		        if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude") + 20)
                		        {
                		 	 		AbilityUtils.damageEntity(p, p, 4);
                		        }
                		        
                		        if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude") + 30)
                		        {
                		 	 		AbilityUtils.damageEntity(p, p, 10);
                		        }
                		        
                		        if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude") + 35)
                		        {
                		 	 		AbilityUtils.damageEntity(p, p, 20);
=======
                		        if(p.getLocation().getY() > altitude + 5)
                		        {
                		        	p.damage((int) ((p.getMaxHealth() * 10) / 100));
                		        }
                		        
                		        if(p.getLocation().getY() > altitude + 13)
                		        {
                		        	p.damage((int) ((p.getMaxHealth() * 20) / 100));
                		        }
                		        
                		        if(p.getLocation().getY() > altitude + 16)
                		        {
                		        	p.damage((int) ((p.getMaxHealth() * 40) / 100));
                		        }
                		        
                		        if(p.getLocation().getY() > altitude + 20)
                		        {
                		        	p.damage((int) ((p.getMaxHealth() * 70) / 100));
                		        }
                		        
                		        if(p.getLocation().getY() >altitude + 35)
                		        {
                		        	p.damage((int) ((p.getMaxHealth() * 100) / 100));
>>>>>>> second-repo/master
                		        }	
            				}
            			}else
            				Utils.sendWorldBorderPacket(p, 0, 200000D, 200000D, 0);
            		}
            }
	    }.runTaskTimer(VallendiaMinigame.getInstance(), 20, 20);
	  }

	
	public static void altTooHigh(Player p)
	{
		int dist = -10000 * 10 + 1300000;
		Utils.sendWorldBorderPacket(p, dist, 200000D, 200000D, 0);
	}
	
	
<<<<<<< HEAD
=======
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		if(e.getPlayer().getGameMode() != GameMode.SURVIVAL)
		{
			return;
		}
		if(!e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase("Minigame2"))
		{
			return;
		}
		if(e.getTo().getY() >= altitude +20)
		{
		   	RegionManager regionManager = Main.worldguard.getRegionManager(e.getPlayer().getWorld());
		   	ApplicableRegionSet arset = regionManager.getApplicableRegions(e.getPlayer().getLocation());
		   	LocalPlayer localPlayer = Main.worldguard.wrapPlayer(e.getPlayer());
		   	if(!arset.allows((StateFlag) VallendiaMinigame.checkAltitude, localPlayer))
		   	{
		   		return;
		   	}
		   	if(e.getFrom().getY() >= altitude +20)
		   	{
		   		e.getPlayer().teleport(e.getPlayer().getLocation().getWorld().getHighestBlockAt(e.getFrom()).getLocation());
				Utils.sendPacketPlayOutPosition(e.getPlayer(),e.getPlayer().getLocation().getPitch(), 90);
		   	}else
		   	{
				e.getPlayer().teleport(e.getFrom());	
				Utils.sendPacketPlayOutPosition(e.getPlayer(), e.getPlayer().getLocation().getPitch(), 90);
		   	}
			Language.sendDefaultMessage(e.getPlayer(), "You are too far up!");
		}
	}
	
	
	@EventHandler
	public void onTP(PlayerTeleportEvent e)
	{
		if(e.getPlayer().getGameMode() != GameMode.SURVIVAL)
		{
			return;
		}
		if(!e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase("Minigame2"))
		{
			return;
		}
		if(e.getTo().getY() >= altitude + 20)
		{
		   	RegionManager regionManager = Main.worldguard.getRegionManager(e.getPlayer().getWorld());
		   	ApplicableRegionSet arset = regionManager.getApplicableRegions(e.getPlayer().getLocation());
		   	LocalPlayer localPlayer = Main.worldguard.wrapPlayer(e.getPlayer());
		   	if(!arset.allows((StateFlag) VallendiaMinigame.checkAltitude, localPlayer))
		   	{
		   		return;
		   	}
		   	if(e.getFrom().getY() >= altitude +20)
		   	{
		   		e.getPlayer().teleport(e.getPlayer().getLocation().getWorld().getHighestBlockAt(e.getFrom()).getLocation());
		   	}else
		   	{
				e.getPlayer().teleport(e.getFrom());	
		   	}
			Language.sendDefaultMessage(e.getPlayer(), "That location is too far up!");
		}
	}
	
	
>>>>>>> second-repo/master
	
}
