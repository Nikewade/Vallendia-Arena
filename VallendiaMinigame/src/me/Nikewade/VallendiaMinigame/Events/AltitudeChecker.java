package me.Nikewade.VallendiaMinigame.Events;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.WorldBorderAction;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class AltitudeChecker {
	static VallendiaMinigame Main;
	 static ProtocolManager protocolManager;
	
	 public AltitudeChecker(VallendiaMinigame Main)
	  {
		AltitudeChecker.Main = Main; 
		protocolManager = ProtocolLibrary.getProtocolManager();
	   new BukkitRunnable() {

            @Override
            public void run() {	
            		for(Player p : Bukkit.getOnlinePlayers())
            		{
            			if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude"))
            			{
            				if(p.getGameMode() == GameMode.CREATIVE)
            				{
            					return;
            				}
            				AltitudeChecker.altTooHigh(p);
            		        p.sendTitle(Utils.Colorate("&4&lToo high!"), null, 20, 1, 1);
            		        if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude") + 5)
            		        {
            		        	p.damage(2);
            		        }
            		        
            		        if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude") + 10)
            		        {
            		        	p.damage(4);
            		        }
            		        
            		        if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude") + 15)
            		        {
            		        	p.damage(6);
            		        }
            		        
            		        if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude") + 20)
            		        {
            		        	p.damage(10);
            		        }
            		        
            		        if(p.getLocation().getY() > Main.getConfig().getInt("Options.altitude") + 30)
            		        {
            		        	p.damage(20);
            		        }
            			}else
            				sendWorldBorderPacket(p, 0, 200000D, 200000D, 0);
            		}
            }
	    }.runTaskTimer(VallendiaMinigame.getInstance(), 20, 20);
	  }

	
	public static void altTooHigh(Player p)
	{
		int dist = -10000 * 10 + 1300000;
		sendWorldBorderPacket(p, dist, 200000D, 200000D, 0);
	}
	
	protected static void sendWorldBorderPacket(Player p, int dist, double oldradius, double newradius, long delay) {
    	PacketContainer border = protocolManager.createPacket(PacketType.Play.Server.WORLD_BORDER);
        
		border.getWorldBorderActions().write(0, WorldBorderAction.INITIALIZE);
		border.getIntegers()
		.write(0, 29999984)
		.write(1, 15)
		.write(2, dist);
		border.getLongs()
		.write(0, (long) 1);
		border.getDoubles()
		.write(0, p.getLocation().getX())
		.write(1, p.getLocation().getY())
		.write(2, newradius)
		.write(3, oldradius);
		try {
			protocolManager.sendServerPacket(p, border);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Cannot send packet " + border, e);
		}
	}
	
	
}
