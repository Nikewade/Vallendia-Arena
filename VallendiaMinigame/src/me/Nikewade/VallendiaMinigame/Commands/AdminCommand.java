package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class AdminCommand implements CommandInterface{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
	    if(sender instanceof  Player && !sender.hasPermission("vallendia.admin"))
	    {
	    	sender.sendMessage(Utils.Colorate("&8You lack permissions!"));
	    	return false;	    	
	    }
		sender.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
		sender.sendMessage("");
		sender.sendMessage(Utils.Colorate("&3/vallendia points &8- Commands for points."));
<<<<<<< HEAD
		sender.sendMessage(Utils.Colorate("&3/vallendia kit  &8- Commands for kits."));
		sender.sendMessage(Utils.Colorate("&3/vallendia spawn  &8- Commands for spawning."));
=======
		sender.sendMessage(Utils.Colorate("&3/vallendia level &8- Commands for levels."));
		sender.sendMessage(Utils.Colorate("&3/vallendia kit  &8- Commands for kits."));
		sender.sendMessage(Utils.Colorate("&3/vallendia spawn  &8- Commands for spawning."));
		sender.sendMessage(Utils.Colorate("&3/vallendia cooldown  &8- Commands for cooldowns."));
>>>>>>> second-repo/master
		sender.sendMessage(Utils.Colorate("&3/vallendia stats &9(name)  &8- Commands for kits."));
		sender.sendMessage(Utils.Colorate("&3/shop help  &8- Commands for the  shop."));
		sender.sendMessage(Utils.Colorate("&3/vallendia regen  &8- Force all blocks to regenerate."));
		sender.sendMessage(Utils.Colorate("&3/vallendia reload  &8- Reloads (Will break things)."));
<<<<<<< HEAD
=======
		sender.sendMessage(Utils.Colorate("&3/vallendia reload config &8- Reloads just the config."));
>>>>>>> second-repo/master
		sender.sendMessage("");
		sender.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));
		return false;
	}

}
