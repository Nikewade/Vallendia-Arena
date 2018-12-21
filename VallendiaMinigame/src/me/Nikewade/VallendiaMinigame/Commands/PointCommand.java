package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;

public class PointCommand implements CommandInterface{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
	    if(args.length > 1) return false;
        Player p = (Player) sender;
	    p.sendMessage("example args!");
	    return false;
	}
}

