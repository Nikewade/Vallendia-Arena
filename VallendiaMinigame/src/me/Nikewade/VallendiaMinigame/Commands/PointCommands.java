package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class PointCommands implements CommandExecutor{
	VallendiaMinigame Main;
	
	public PointCommands(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getCommand("points").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			if(!p.isOp()) return false;
			if(label.equalsIgnoreCase("points"))
			{
				if(args.length == 3)
				{
					String target = args[1];
					String amount = args[2];
					if(Bukkit.getPlayer(target) != null)
					{
						Player player = Bukkit.getPlayer(target);
						if(args[0].equalsIgnoreCase("add"))
						{
								Main.shopmanager.addPoints(player, Integer.valueOf(amount));
								p.sendMessage(amount + " points given to " + player.getName());
								player.sendMessage("You recieved " + amount + " points!");
							
						}
						if(args[0].equalsIgnoreCase("set"))
						{
								Main.shopmanager.setPoints(player, Integer.valueOf(amount));
								p.sendMessage(player.getName() + "'s points set to " + amount);
								player.sendMessage("Your points were set to " + amount);
							
						}
						if(args[0].equalsIgnoreCase("remove"))
						{
							Main.shopmanager.subtractPoints(player, Integer.valueOf(amount));
							p.sendMessage(amount + " points remove from " + player.getName());
							player.sendMessage("You lost " + amount + " points!");
						}	
					}else p.sendMessage("Player does not exist!");
				} else p.sendMessage("Try /points /add/set/remove (player) (amount)");
			}
		}
		
		return false;
	}
}
