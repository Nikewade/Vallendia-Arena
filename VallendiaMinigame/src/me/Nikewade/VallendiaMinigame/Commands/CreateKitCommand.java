package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class CreateKitCommand implements CommandExecutor {
	VallendiaMinigame Main;
	
	public CreateKitCommand(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getCommand("setkit").setExecutor(this);
		Main.getCommand("removekit").setExecutor(this);
		Main.getCommand("kit").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			if(!p.isOp()) return false;
			if(label.equalsIgnoreCase("setkit"))
			{
				if(args.length != 0)
				{	
					String kitname = args[0].toLowerCase();
					Main.kitmanager.createKit(p, kitname);
				}else p.sendMessage("Try /setkit (kitname)");
			}
			
			if(label.equalsIgnoreCase("removekit"))
			{
				if(args.length != 0)
				{	
					String kitname = args[0].toLowerCase();
					this.Main.kitmanager.removeKit(p, kitname);
				}else p.sendMessage("Try /removekit (kitname)");
			}
			
			if(label.equalsIgnoreCase("kit"))
			{
				if(args.length != 0)
				{	
					String kitname = args[0].toLowerCase();
					this.Main.kitmanager.giveKit(p, kitname);
				}else p.sendMessage("Try /kit (kitname)");
			}
		}
		
		return false;
	}

}
