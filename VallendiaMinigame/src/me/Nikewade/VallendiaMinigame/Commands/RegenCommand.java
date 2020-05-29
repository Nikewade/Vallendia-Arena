package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
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
		   new BukkitRunnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Utils.restoreBlocks();
				if(sender instanceof Player)
				{
					Player p = (Player) sender;
					Language.sendDefaultMessage(p, "All blocks regenerated");
				}
			}
			   
		   }.runTaskLater(VallendiaMinigame.getInstance(), 50);
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			Language.sendDefaultMessage(p, "Regenerating blocks...");
		}
		return false;
	}

}
