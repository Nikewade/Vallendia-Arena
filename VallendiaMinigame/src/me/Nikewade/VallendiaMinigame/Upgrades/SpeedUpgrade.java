package me.Nikewade.VallendiaMinigame.Upgrades;

import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;

public class SpeedUpgrade implements Upgrade{

	public void upgrade(Player p)
	{
		Float speed = p.getWalkSpeed();
		if(speed >= 0.8) return;
		p.setWalkSpeed((float) (speed + 0.01));
	}
	
	public int getPrice(String enchant)
	{
		return VallendiaMinigame.getInstance().getConfig().getInt("upgrades." + "speed." + "price");
	}

	@Override
	public double getMultiplier(String enchant) {
		// TODO Auto-generated method stub
		return VallendiaMinigame.getInstance().getConfig().getDouble("upgrades." + "speed." + "multiplier");
	}
	
	@Override
	public double getMultiplier2(String enchant) {
		// TODO Auto-generated method stub
		return VallendiaMinigame.getInstance().getConfig().getDouble("upgrades." + "speed." +  ".multiplier2");
	}
	
<<<<<<< HEAD
=======
	
	public static void updateSpeed(Player p)
	{
		double speed = VallendiaMinigame.getInstance().upgrademanager.getUpgradeAmount(p, "speed");
		
		
		p.setWalkSpeed(0.2F + (float) (0.01 * speed));
	}
	
>>>>>>> second-repo/master
}
