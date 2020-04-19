package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;

public class TutorialQuitCommand implements CommandInterface{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
		if(!AbilityCooldown.isInCooldown(p.getUniqueId(), "quit"))
		{  
    		AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), "quit", 20, null);
    		c.start();	
		}else
		{
			return false;
		}
		VallendiaMinigame.getInstance().getServer().dispatchCommand(sender, "st quit");
	}
		return false;

}
}
