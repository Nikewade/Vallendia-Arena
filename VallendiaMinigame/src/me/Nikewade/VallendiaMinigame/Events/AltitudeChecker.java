package me.Nikewade.VallendiaMinigame.Events;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.WorldBorderAction;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class AltitudeChecker {
	static VallendiaMinigame Main;
	
	 public AltitudeChecker(VallendiaMinigame Main)
	  {
		AltitudeChecker.Main = Main; 
	   new BukkitRunnable() {

            @SuppressWarnings("deprecation")
			@Override
            public void run() {	
            		for(Player p : Bukkit.getOnlinePlayers())
            		{
    	    		   	RegionManager regionManager = Main.worldguard.getRegionManager(p.getWorld());
    	    		   	ApplicableRegionSet arset = regionManager.getApplicableRegions(p.getLocation());
    	    		   	LocalPlayer localPlayer = Main.worldguard.wrapPlayer(p);
    	    		   	if(!arset.allows((StateFlag) VallendiaMinigame.checkAltitude, localPlayer))
    	    		   	{
    	    		   		continue;
    	    		   	}
            			if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude"))
            			{
            				if(p.getGameMode() != GameMode.CREATIVE)
            				{
                				AltitudeChecker.altTooHigh(p);
                		        p.sendTitle(Utils.Colorate("&4&lToo high!"), null, 20, 1, 1);
                		        if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude") + 5)
                		        {
                		        	p.damage((int) ((p.getMaxHealth() * 10) / 100));
                		        }
                		        
                		        if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude") + 15)
                		        {
                		        	p.damage((int) ((p.getMaxHealth() * 30) / 100));
                		        }
                		        
                		        if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude") + 25)
                		        {
                		        	p.damage((int) ((p.getMaxHealth() * 40) / 100));
                		        }
                		        
                		        if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude") + 30)
                		        {
                		        	p.damage((int) ((p.getMaxHealth() * 60) / 100));
                		        }
                		        
                		        if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude") + 40)
                		        {
                		        	p.damage((int) ((p.getMaxHealth() * 100) / 100));
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
	
	
	
}
