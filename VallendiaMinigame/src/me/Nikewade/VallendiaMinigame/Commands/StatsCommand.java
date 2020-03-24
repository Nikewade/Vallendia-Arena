package me.Nikewade.VallendiaMinigame.Commands;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class StatsCommand implements  CommandInterface {
	VallendiaMinigame  main  = VallendiaMinigame.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(!(args.length > 1) && sender instanceof Player)
		{
			Player p = (Player) sender;
			double  kdr;
			if(main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Deaths") <= 0)
			{
				kdr = main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Kills");
			}else 
			{
				kdr  =  (double) main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Kills") / (double)  main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Deaths");	
			}
			kdr = Double.parseDouble(new DecimalFormat("#.###").format(kdr));
			sender.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
			sender.sendMessage("");
			sender.sendMessage(Utils.Colorate("&8&l" + p.getName()));			
			sender.sendMessage(Utils.Colorate("&3Level: " + main.levelmanager.getLevel(p)));			
			sender.sendMessage(Utils.Colorate("&3Total Kills: " + main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Kills")));
			sender.sendMessage(Utils.Colorate("&3Total Deaths: " + main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Deaths")));
			sender.sendMessage(Utils.Colorate("&3KDR: " + kdr));
			sender.sendMessage(Utils.Colorate("&3Current Kills: " + main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "KillStreak")));
			sender.sendMessage(Utils.Colorate("&3Upgrades: " +main.upgrademanager.getUpgradeTotal(p)));
			sender.sendMessage(Utils.Colorate("&3Points Spent: " + main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "PointsSpent")));
			sender.sendMessage(Utils.Colorate("&3Points: " + main.shopmanager.getPoints(p)));
			sender.sendMessage("");
			sender.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));	
		}
		
		
		if(args.length == 2)
		{
		    if(sender instanceof  Player && !sender.hasPermission("vallendia.admin"))
		    {
		    	sender.sendMessage(Utils.Colorate("&8You lack permissions!"));
		    	return false;	    	
		    }
			   if(Bukkit.getPlayer(args[1]) != null)
			   {
				   Player p = Bukkit.getPlayer(args[1]); 
					double  kdr;
					if(main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Deaths") <= 0)
					{
						kdr = main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Kills");
					}else 
					{
						kdr  =  (double) main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Kills") / (double)  main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Deaths");	
					}
					sender.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
					sender.sendMessage("");
					sender.sendMessage(Utils.Colorate("&8&l" + p.getName()));			
					sender.sendMessage(Utils.Colorate("&3Level: " + main.levelmanager.getLevel(p)));			
					sender.sendMessage(Utils.Colorate("&3TotalKills: " + main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Kills")));
					sender.sendMessage(Utils.Colorate("&3TotalDeaths: " + main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "Deaths")));
					sender.sendMessage(Utils.Colorate("&3KDR: " + kdr));
					sender.sendMessage(Utils.Colorate("&3CurrentKills: " + main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "KillStreak")));
					sender.sendMessage(Utils.Colorate("&3Upgrades: " +main.upgrademanager.getUpgradeTotal(p)));
					sender.sendMessage(Utils.Colorate("&3PointsSpent: " + main.playerdatamanager.getPlayerIntData(p.getUniqueId(), "PointsSpent")));
					sender.sendMessage(Utils.Colorate("&3Points: " + main.shopmanager.getPoints(p)));
					sender.sendMessage("");
					sender.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));	
			   } else
			   {
				   sender.sendMessage(Utils.Colorate("&8That player doesn't exist!"));
			   }
		}
		return false;
	}

}
	