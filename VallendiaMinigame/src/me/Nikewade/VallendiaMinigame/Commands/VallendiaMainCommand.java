package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class VallendiaMainCommand implements CommandInterface{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		sender.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
		sender.sendMessage("");
		sender.sendMessage(Utils.Colorate("&3/vallendia stats &8- Displays your player stats."));
		sender.sendMessage(Utils.Colorate("&3/vallendia ability  &8- Commands for abilities."));
		sender.sendMessage(Utils.Colorate("&3/vallendia admin  &8- Commands for admins."));
		sender.sendMessage(Utils.Colorate("&3/vallendia resetkit  &8- Reset your kit and upgrades."));
		sender.sendMessage(Utils.Colorate("&3/spawn  &8- Teleports you to spawn. This has a 15 second warmup."));
		sender.sendMessage("");
		sender.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));
        
        return false;

	}

}
