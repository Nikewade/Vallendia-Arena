package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class KitCommand implements  CommandInterface{
	VallendiaMinigame  main = VallendiaMinigame.getInstance();

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
			sender.sendMessage(Utils.Colorate("&3/vallendia kit get &9(kitname)"));
			sender.sendMessage(Utils.Colorate("&3/vallendia kit give &9(kitname) (playername)"));
			sender.sendMessage(Utils.Colorate("&3/vallendia kit create &9(kitname)"));
			sender.sendMessage(Utils.Colorate("&3/vallendia kit remove &9(kitname)"));
			sender.sendMessage("");
			sender.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));	    
			return false;
	    }
	    
	    
	    if(sender instanceof Player)
	    {
	    	Player p = (Player) sender;
		    if(args.length == 3)
		    {
		    		if(args[1].equalsIgnoreCase("get"))
		    		{
						String kitname = args[2].toLowerCase();
						main.kitmanager.giveKit(p, kitname);
						p.sendMessage(Utils.Colorate("&8You have been given the " + main.kitmanager.getKit(p).getName(true) + " &8kit!"));
						return false;
		    		}
		    		if(args[1].equalsIgnoreCase("create"))
		    		{
						String kitname = args[2].toLowerCase();
						main.kitmanager.createKit(p, kitname);
						p.sendMessage(Utils.Colorate("&7Kit " +  kitname + " created!"));
						return false;
		    		}
		    		if(args[1].equalsIgnoreCase("remove"))
		    		{
						String kitname = args[2].toLowerCase();
						main.kitmanager.removeKit(p, kitname);
						p.sendMessage(Utils.Colorate("&7Kit " +  kitname + " removed!"));
						return false;
		    		}
		    }
		    
		    if(args.length == 4)
		    {
	    		if(args[1].equalsIgnoreCase("give"))
	    		{
					String kitname = args[2].toLowerCase();
					   if(Bukkit.getPlayer(args[3]) != null)
					   {
						   Player targetp = Bukkit.getPlayer(args[3]);  
							main.kitmanager.giveKit(targetp, kitname);
							targetp.sendMessage(Utils.Colorate("&8You have been given the " + main.kitmanager.getKit(p).getName(true) + " &8kit!"));
							p.sendMessage(Utils.Colorate("&8Kit " +  main.kitmanager.getKit(p).getName(true)+ " &8given to " +  targetp.getName() + "."));
					   }else sender.sendMessage(Utils.Colorate("&8Player does not exist!"));
	    		}
		    }
	    }
		return false;

	    
	}

}
