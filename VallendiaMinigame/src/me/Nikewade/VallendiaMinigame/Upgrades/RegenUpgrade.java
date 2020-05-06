package me.Nikewade.VallendiaMinigame.Upgrades;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class RegenUpgrade implements Upgrade{
	static VallendiaMinigame Main;
	private static Map<Player, BukkitTask> timers = new HashMap<Player, BukkitTask>();
	 static ProtocolManager protocolManager;
	
	 public RegenUpgrade(VallendiaMinigame Main)
	  {
		 RegenUpgrade.Main = Main; 
		protocolManager = ProtocolLibrary.getProtocolManager();
	  }
	
	public void upgrade(Player p)
	{
		RegenUpgrade.addTimer(p);
    	double time = 0.00506 * Math.pow((0.25 * Main.upgrademanager.getUpgradeAmount(p, "regeneration") -8.985), 4) + 0.5;
    	DecimalFormat format = new DecimalFormat("0.0");

    	String output = format.format(time);
		p.sendMessage(Utils.Colorate("&cRegeneration every " + output + Utils.Colorate(" &cseconds.")));
	}
	
	public void resetRegen(Player p)
	{
		if(timers.containsKey(p))
		{
			timers.get(p).cancel();
			timers.remove(p);	
		}
	}
	
	public static void addTimer(Player p)
	{
		
		if(!(Main.upgrademanager.getUpgradeAmount(p, "regeneration") > 0))
		{
			return;
		}
		
		if(timers.containsKey(p))
		{
			timers.get(p).cancel();
			timers.remove(p);
		}
		
    	double time = 0.00506 * Math.pow((0.25 * Main.upgrademanager.getUpgradeAmount(p, "regeneration") -8.985), 4) + 0.5;
		
	    BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {	
                	if(p.getHealth() < p.getMaxHealth())
                	{
                		AbilityUtils.healEntity(p, 1);
                		if(!AbilityUtils.isInvisible(p))
                		{
                	        p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 0.4, 0.4), 5);
                	        p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 0.4, 0), 5);
                	        p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0.4, 0.4, 0), 5);	
                		}
                	}     
            }
	    }.runTaskTimer(VallendiaMinigame.getInstance(), 20* 3, (long) (20 * time));
	
	    timers.put(p, task);
	}
	
	public int getPrice(String enchant)
	{
		return VallendiaMinigame.getInstance().getConfig().getInt("upgrades." + "regeneration." + "price");
	}

	@Override
	public double getMultiplier(String enchant) {
		// TODO Auto-generated method stub
		return VallendiaMinigame.getInstance().getConfig().getDouble("upgrades." + "regeneneration." + "multiplier");
	}
	
	@Override
	public double getMultiplier2(String enchant) {
		// TODO Auto-generated method stub
		return VallendiaMinigame.getInstance().getConfig().getDouble("upgrades." + "regeneration." + ".multiplier2");
	}

}
