package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class LevelCommand implements CommandInterface {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
	    if(sender instanceof  Player && !sender.hasPermission("vallendia.admin"))
	    {
	    	sender.sendMessage(Utils.Colorate("&8You lack permissions!"));
	    	return false;	    	
	    }
	    
	    if(!(args.length >1) || args.length > 5)
	    {
			sender.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
			sender.sendMessage("");
			sender.sendMessage(Utils.Colorate("&3/vallendia level set &9(name) (amount)"));
			sender.sendMessage("");
			sender.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));	
			return false;
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
				   sender.sendMessage(Utils.Colorate( "&8" + p.getName() + "'s level set to " + amount + "."));
				   p.sendMessage(Utils.Colorate("&8Your level was set to "  + amount +  "."));
				   VallendiaMinigame.getInstance().levelmanager.setLevel(p, amount);
			   }

		   }else sender.sendMessage(Utils.Colorate("&8Player does not exist!"));	
	    }

	    
	    
	    return false;
	}
}
