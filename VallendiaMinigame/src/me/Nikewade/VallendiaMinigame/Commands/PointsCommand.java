package me.Nikewade.VallendiaMinigame.Commands;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
<<<<<<< HEAD
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
=======
import me.Nikewade.VallendiaMinigame.Events.PlayerKillEvents;
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
>>>>>>> second-repo/master
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
	    
<<<<<<< HEAD
	    if(!(args.length >1) || args.length > 5)
=======
	    if(!(args.length >1) || args.length > 6)
>>>>>>> second-repo/master
	    {
			sender.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
			sender.sendMessage("");
			sender.sendMessage(Utils.Colorate("&3/vallendia points get &9(name)"));
			sender.sendMessage(Utils.Colorate("&3/vallendia points set &9(name) (amount)"));
			sender.sendMessage(Utils.Colorate("&3/vallendia points add &9(name) (amount)"));
<<<<<<< HEAD
			sender.sendMessage(Utils.Colorate("&3/vallendia points addrandom &9(name) (amountlowest) (amounthighest)"));
=======
			sender.sendMessage(Utils.Colorate("&3/vallendia points addrandom &9(name) (amountlowest) (amounthighest) (string)"));
>>>>>>> second-repo/master
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
<<<<<<< HEAD
				   sender.sendMessage(Utils.Colorate("&8" + p.getName() + " has " + main.shopmanager.getPoints(p) + " points."));
=======
				   sender.sendMessage(Utils.Colorate("&8" + p.getName() + " has " + main.shopmanager.getPoints(p) + " essence."));
>>>>>>> second-repo/master
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
<<<<<<< HEAD
				   sender.sendMessage(Utils.Colorate( "&8" + p.getName() + "'s points set to " + amount + "."));
				   p.sendMessage(Utils.Colorate("&8Your points were set to "  + amount +  "."));
=======
				   sender.sendMessage(Utils.Colorate( "&8" + p.getName() + "'s essence set to " + amount + "."));
				   p.sendMessage(Utils.Colorate( "&8&l[&3Essence&8&l] Set to " + amount));
>>>>>>> second-repo/master
			   }
			   
			   if(args[1].equalsIgnoreCase("add"))
			   {
				   main.shopmanager.addPoints(p, amount);
<<<<<<< HEAD
				   sender.sendMessage(Utils.Colorate( "&8Gave " + amount +  " points to " +  p.getName() +  "."));
				   p.sendMessage(Utils.Colorate( "&8You were given " + amount +  " points."));
=======
				   sender.sendMessage(Utils.Colorate( "&8Gave " + amount +  " essence to " +  p.getName() +  "."));
				   p.sendMessage(Utils.Colorate( "&8&l[&3Essence&8&l] +" + amount));
>>>>>>> second-repo/master
			   }
			   
			   if(args[1].equalsIgnoreCase("subtract"))
			   {
				   main.shopmanager.subtractPoints(p, amount);
<<<<<<< HEAD
				   sender.sendMessage(Utils.Colorate( "&8Took " + amount +  " points from " +  p.getName() +  "."));
				   p.sendMessage(Utils.Colorate("&8"  + amount + " points were taken from you."));
=======
				   sender.sendMessage(Utils.Colorate( "&8Took " + amount +  " essence from " +  p.getName() +  "."));
				   p.sendMessage(Utils.Colorate( "&8&l[&3Essence&8&l] -" + amount));
>>>>>>> second-repo/master
			   }
		   }else sender.sendMessage(Utils.Colorate("&8Player does not exist!"));	
	    }
	    
<<<<<<< HEAD
	    if(args.length == 5)
=======
	    if(args.length == 6)
>>>>>>> second-repo/master
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
				   if(args[1].equalsIgnoreCase("addrandom"))
				   {
					   int maxAmount;
					   int lowestAmount;
<<<<<<< HEAD
=======
					   String string = args[5].replace(".", " ");
>>>>>>> second-repo/master
					    try {
							   maxAmount = Integer.parseInt(args[4]);
							   lowestAmount = Integer.parseInt(args[3]);
					    } catch (NumberFormatException nfe) {
					        return false;
					    }
					    int randomAmount = ThreadLocalRandom.current().nextInt(lowestAmount, maxAmount + 1);
<<<<<<< HEAD
					   main.shopmanager.addPoints(p, randomAmount);
					   sender.sendMessage(Utils.Colorate( "&8Gave " + randomAmount +  " points to " +  p.getName() +  "."));
					   p.sendMessage(Utils.Colorate( "&8You gained " + randomAmount +  " points."));
=======
			        	 if(AbilityUtils.getPlayerParty(p) != "")
			        	 {
			        		 randomAmount = PlayerKillEvents.shareEssence(p, (int) randomAmount, true, string);
			        	 }
					   main.shopmanager.addPoints(p, randomAmount);
					   if(sender instanceof Player)
					   {
						   sender.sendMessage(Utils.Colorate( "&8Gave " + randomAmount +  " essence to " +  p.getName() +  "."));   
					   }
					   if(args[5].equalsIgnoreCase("null"))
					   {
						   p.sendMessage(Utils.Colorate( "&8&l[&3Essence&8&l] +" + randomAmount));
					   }else
					   {
						   p.sendMessage(Utils.Colorate( "&8&l[&3Essence&8&l] &8+" + randomAmount + " " + string));   
					   }
>>>>>>> second-repo/master
				   }
			   }
			   
	    }
	    
	    
	    return false;
	}
}

