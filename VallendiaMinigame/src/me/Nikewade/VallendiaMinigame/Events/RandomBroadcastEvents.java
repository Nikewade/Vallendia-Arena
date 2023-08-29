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
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3Right click a furnace with raw food in your hand to cook it."));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3Do /party for help on how to party up to play with friends."));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3A maximum of four players can be in one party."));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3Parties get more essence than solo players, but it is split amongst the members."));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3When you walk through the portal at spawn, you will be teleported somewhere at least 30 blocks away from other players."));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3Obtain food by buying it in the shop or killing animals around the map."));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3The vault in the castle is the highest-value place resources can spawn other than the spider caves."));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3The spider caves are very dangerous!  You should be at least level 10 before considering going in there."));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3Rumor has it magical Essence comes from an occult entity in the abyss.  How did it get here?"));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3The townsfolk fleeing from Valendale are often broken mentally, unable to speak, or severely disturbed by what they saw."));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3Whatever happened in Valendale spread Essence far and wide.  Everything here is pulsing with magical energy!"));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3The castle in Valendale was owned by Lord Valek.  No one has seen him since the incident."));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3Some have said dark rituals went on in this town.  Could that be connected to the magical Essence spread around the area?"));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3Players' names will change color as they become more powerful"));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3Remember to vote to gain some easy Essence with /vote!"));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3Grab yourself a limited time Alpha founder rank with /donate!"));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3Join our discord to follow updates! https://discord.gg/FKGbf9u"));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3We recommend downloading a keybind mod to bind your abilities. /vall ability!"));
		messages.add(Utils.Colorate("&8&l[&3Info&8&l] &3This is Alpha so please use /report to report any bugs that you find!"));
		
		
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
