package me.Nikewade.VallendiaMinigame.Events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.SirBlobman.combatlogx.event.PlayerTagEvent;
import com.SirBlobman.combatlogx.event.PlayerUntagEvent;
import com.kirelcodes.miniaturepets.api.events.pets.PetFinishedSpawnEvent;
import com.kirelcodes.miniaturepets.api.events.pets.PetRemovedEvent;
import com.kirelcodes.miniaturepets.api.events.pets.PetSpawnEvent;
import com.kirelcodes.miniaturepets.pets.Pet;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class CosmeticHideEvents implements Listener{
	VallendiaMinigame Main;
	public static HashMap<Player, Pet> pets = new HashMap<>();
	
	public CosmeticHideEvents(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
	}
	
	@EventHandler
	public void onCombat(PlayerTagEvent e)
	{
		Player p = e.getPlayer();
		Utils.removeCosmetics(p);
	}
	
	@EventHandler
	public void onUnTag (PlayerUntagEvent e)
	{
		Player p = e.getPlayer();
		Utils.addCosmetics(p);
	}
	
	@EventHandler
	public void onBeforeSpawn (PetSpawnEvent e)
	{
		e.setShouldSendSpawnMessage(false);
	}
	
	@EventHandler
	public void onRemove (PetRemovedEvent e)
	{
		if(pets.containsKey(e.getOwner()))
		{
			pets.remove(e.getOwner());
		}
	}
	
	@EventHandler
	public void onFinishSpawn (PetFinishedSpawnEvent e)
	{
		Player p = e.getOwner();
		if(pets.containsKey(p))
		{
			pets.remove(p);
			pets.put(p, e.getPet());			
		}else
		{
			pets.put(p, e.getPet());
		}
	}
	
}