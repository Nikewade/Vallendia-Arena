package me.Nikewade.VallendiaMinigame.Commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import nl.martenm.servertutorialplus.api.events.TutorialEndEvent;

public class TutorialNextCommand implements CommandInterface{
	public static ArrayList<Player> next = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			if(!next.contains(p))
			{
				return false;
			}
		if(!AbilityCooldown.isInCooldown(p.getUniqueId(), "next"))
		{  
    		AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), "next", 20, null);
    		c.start();	
		}else
		{
			return false;
		}
		
		VallendiaMinigame.getInstance().getServer().dispatchCommand(sender, "st next");
		}else
		{
			if(args.length == 2 && Bukkit.getPlayer(args[1]) != null)
			{
				Player p = Bukkit.getPlayer(args[1]);
				if(next.contains(p))
				{
					next.remove(p);
				}else
				{
					next.add(p);
				}
			}
		}
		return false;
	}
	

}
