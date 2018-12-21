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
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			if(!p.isOp()) return false;
			if(label.equalsIgnoreCase("upgrade"))
			{
				if(args.length != 0)
				{
					if(args[0].equalsIgnoreCase("resetall"))
					{
						Main.upgrademanager.resetUpgrades(p);
						p.sendMessage("All upgrades reset!");
					}
				} else p.sendMessage("Try /upgrade resetall");
			}
		}
		
		return false;
	}
}
