package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;

public class VallendiaMainCommand implements CommandInterface{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player p = (Player) sender;
        p.sendMessage("Example!");
        return false;

	}

}
