package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

<<<<<<< HEAD
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
=======
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.Language;
>>>>>>> second-repo/master
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class ReloadCommand implements  CommandInterface{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
	    if(sender instanceof  Player && !sender.hasPermission("vallendia.admin"))
	    {
	    	sender.sendMessage(Utils.Colorate("&8You lack permissions!"));
	    	return false;	    	
	    }
<<<<<<< HEAD
	    Bukkit.dispatchCommand(sender, "plugman reload VallendiaMinigame");
	    sender.sendMessage(Utils.Colorate("&4&lRELOADED This will break things like worldguard flags!"));
	    sender.sendMessage(Utils.Colorate("&4&lMake sure to do a proper reload / restart to fix."));
=======
	    
	    if(args.length == 2 && args[1].equalsIgnoreCase("config"))
	    	{
	    		VallendiaMinigame.getInstance().reloadConfig();
	    		Language.sendVallendiaBroadcast("reloaded ");
	    		return false;
	    	}
	    
		    Bukkit.dispatchCommand(sender, "plugman reload VallendiaMinigame");
		    sender.sendMessage(Utils.Colorate("&4&lRELOADED This will break things like worldguard flags!"));
		    sender.sendMessage(Utils.Colorate("&4&lMake sure to do a proper reload / restart to fix."));	
>>>>>>> second-repo/master
		return false;
	}

}
