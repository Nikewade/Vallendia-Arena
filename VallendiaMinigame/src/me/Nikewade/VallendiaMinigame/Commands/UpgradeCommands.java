package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class UpgradeCommands implements CommandExecutor {
	VallendiaMinigame Main;
	
	public UpgradeCommands(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getCommand("upgradereset").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			if(!p.isOp()) return false;
			if(label.equalsIgnoreCase("upgradereset"))
			{
					Main.upgrademanager.resetUpgrades(p);
					p.sendMessage("Upgrades reset!");
			}
		}
		
		return false;
	}
}
