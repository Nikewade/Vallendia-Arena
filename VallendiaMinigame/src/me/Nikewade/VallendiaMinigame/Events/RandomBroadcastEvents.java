package me.Nikewade.VallendiaMinigame.Events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class RandomBroadcastEvents {
	VallendiaMinigame Main;
	ArrayList<String> messages = new ArrayList<>();
	
	
	
	public RandomBroadcastEvents(VallendiaMinigame main)
	{
		Main = main;

		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3Remember to vote to gain some easy Essence with /vote!"));
		
		
		new BukkitRunnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int random = Utils.randomNumberBetween(0, (messages.size() - 1));
				Bukkit.broadcastMessage(messages.get(random));
			}
			
		}.runTaskTimer(main, 0, 600 * 20);
	}
}
