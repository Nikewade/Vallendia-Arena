package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class PointsCommand implements CommandInterface{
	VallendiaMinigame main  =  VallendiaMinigame.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
	    if(sender instanceof  Player && !sender.hasPermission("vallendia.admin"))
	    {
	    	sender.sendMessage(Utils.Colorate("&8You lack permissions!"));
	    	return false;	    	
	    }
	    
	    if(!(args.length >1) || args.length > 4)
	    {
			sender.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
			sender.sendMessage("");
			sender.sendMessage(Utils.Colorate("&3/vallendia points get &9(name)"));
			sender.sendMessage(Utils.Colorate("&3/vallendia points set &9(name) (amount)"));
			sender.sendMessage(Utils.Colorate("&3/vallendia points add &9(name) (amount)"));
			sender.sendMessage(Utils.Colorate("&3/vallendia points subtract &9(name) (amount)"));
			sender.sendMessage("");
			sender.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));	
			return false;
	    }
	    
	    if(args.length == 3)
	    {
	 	   if(args[1].equalsIgnoreCase("get"))
		   {
			   if(Bukkit.getPlayer(args[2]) != null)
			   {
				   Player p = Bukkit.getPlayer(args[2]);  
				   sender.sendMessage(Utils.Colorate("&8" + p.getName() + " has " + main.shopmanager.getPoints(p) + " points."));
			   }else sender.sendMessage(Utils.Colorate("&8Player does not exist!"));
			   return false;
		   }	
	    }
	   
	    
	    if(args.length == 4)
	    {
	 	   if(Bukkit.getPlayer(args[2]) != null)
		   {
	 		   int amount;
			   Player p = Bukkit.getPlayer(args[2]); 
			    try {
					   amount = Integer.parseInt(args[3]);
			    } catch (NumberFormatException nfe) {
			        return false;
			    }
			   if(args[1].equalsIgnoreCase("set"))
			   {
				   main.shopmanager.setPoints(p, amount);
				   sender.sendMessage(Utils.Colorate( "&8" + p.getName() + "'s points set to " + amount + "."));
				   p.sendMessage(Utils.Colorate("&8Your points were set to "  + amount +  "."));
			   }
			   
			   if(args[1].equalsIgnoreCase("add"))
			   {
				   main.shopmanager.addPoints(p, amount);
				   sender.sendMessage(Utils.Colorate( "&8Gave " + amount +  " points to " +  p.getName() +  "."));
				   p.sendMessage(Utils.Colorate( "&8You were given " + amount +  " points."));
			   }
			   
			   if(args[1].equalsIgnoreCase("subtract"))
			   {
				   main.shopmanager.subtractPoints(p, amount);
				   sender.sendMessage(Utils.Colorate( "&8Took " + amount +  " points from " +  p.getName() +  "."));
				   p.sendMessage(Utils.Colorate("&8"  + amount + " points were taken from you."));
			   }
		   }else sender.sendMessage(Utils.Colorate("&8Player does not exist!"));	
	    }
	    return false;
	}
}

