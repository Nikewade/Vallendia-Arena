package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class CooldownCommand implements CommandInterface{
	VallendiaMinigame main  =  VallendiaMinigame.getInstance();

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
			sender.sendMessage(Utils.Colorate("&3/vallendia cooldown reset &9(name)"));
			sender.sendMessage(Utils.Colorate("&3/vallendia cooldown resetall"));
			sender.sendMessage("");
			sender.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));	
			return false;
	    }
	    
	    if(args.length == 2)
	    {
		 	   if(args[1].equalsIgnoreCase("resetall"))
			   {
		 		   for(Player pla : Bukkit.getOnlinePlayers())
		 		   {
		 		        AbilityCooldown.stopAll(pla.getUniqueId());
						pla.sendMessage(Utils.Colorate( "&8Your cooldowns have been reset."));
		 		   }
			   }
	    }
	    
	    
	    if(args.length == 3)
	    {
	 	   if(args[1].equalsIgnoreCase("reset"))
		   {
			   if(Bukkit.getPlayer(args[2]) != null)
			   {
				   Player p = Bukkit.getPlayer(args[2]);  
				   sender.sendMessage(Utils.Colorate( "&8You reset " + p.getName() + "'s cooldowns."));
				   p.sendMessage(Utils.Colorate( "&8Your cooldowns have been reset."));
			       AbilityCooldown.stopAll(p.getUniqueId());
			   }else sender.sendMessage(Utils.Colorate("&8Player does not exist!"));
			   return false;
		   }	
	    }
	    
	    
	    return false;
	}
	
}
