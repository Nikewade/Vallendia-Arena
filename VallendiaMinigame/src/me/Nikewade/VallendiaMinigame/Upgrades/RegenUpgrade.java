package me.Nikewade.VallendiaMinigame.Upgrades;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class RegenUpgrade implements Upgrade{
	static VallendiaMinigame Main;
	private static Map<Player, BukkitTask> timers = new HashMap<Player, BukkitTask>();
	
	 public RegenUpgrade(VallendiaMinigame Main)
	  {
		 RegenUpgrade.Main = Main; 
	  }
	
	public void upgrade(Player p)
	{
		RegenUpgrade.addTimer(p);
    	int time = (30 + 1- Main.upgrademanager.getUpgradeAmount(p, "regeneration"));
    	if(time < 1)
    	{
    		time = 1;
    	}
		p.sendMessage(Utils.Colorate("&cRegeneration every " + time + Utils.Colorate(" &cseconds.")));
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
		
    	int time = (30 + 1- Main.upgrademanager.getUpgradeAmount(p, "regeneration"));
    	if(time < 1)
    	{
    		time = 1;
    	}
		
	    BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {	
                	if(p.getHealth() < p.getMaxHealth())
                	{
                    	if(p.getHealth() >= p.getMaxHealth() - 1)
                    	{
                    		p.setHealth(p.getMaxHealth());
                    	}else
                		p.setHealth(p.getHealth() + 1);
                        p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 0.4, 0.4), 5);
                        p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 0.4, 0), 5);
                        p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0.4, 0.4, 0), 5); 
                	}     
            }
	    }.runTaskTimer(VallendiaMinigame.getInstance(), 20* 3, 20 * time);	
	
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
