package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class MainCommands implements CommandExecutor{
	VallendiaMinigame Main;
	
	public MainCommands(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getCommand("Vallendia").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("Vallendia") && args.length > 0)
		{
			if(args[0].equalsIgnoreCase("reload"))
			{
				Bukkit.getServer().dispatchCommand(sender, "plugman reload VallendiaMinigame");
			}
		}
		return false;
}

	
	
}