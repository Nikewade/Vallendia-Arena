package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class RegenCommand implements CommandInterface {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		// TODO Auto-generated method stub
	    if(sender instanceof  Player && !sender.hasPermission("vallendia.admin"))
	    {
	    	sender.sendMessage(Utils.Colorate("&8You lack permissions!"));
	    	return false;	    	
	    }
		Utils.restoreBlocks();
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			Language.sendDefaultMessage(p, "All blocks have been regenerated.");
		}
		return false;
	}

}
