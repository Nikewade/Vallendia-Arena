package me.Nikewade.VallendiaMinigame.Events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.AbilityManager;
import me.Nikewade.VallendiaMinigame.Abilities.BandageAbility;
import me.Nikewade.VallendiaMinigame.Abilities.EquipBowAbility;
import me.Nikewade.VallendiaMinigame.Abilities.RootAbility;
import me.Nikewade.VallendiaMinigame.Commands.TutorialNextCommand;
import me.Nikewade.VallendiaMinigame.Data.PlayerDataManager;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Upgrades.RegenUpgrade;
import me.Nikewade.VallendiaMinigame.Upgrades.SpeedUpgrade;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import nl.martenm.servertutorialplus.ServerTutorialPlus;
import nl.martenm.servertutorialplus.api.ServerTutorialApi;
import nl.martenm.servertutorialplus.api.events.TutorialEndEvent;

public class PlayerJoinEvents implements Listener{
	VallendiaMinigame Main;
	ArrayList<Player> joining = new ArrayList<>();
	
	
	
	public PlayerJoinEvents(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		e.setJoinMessage("");
		if(p.hasPlayedBefore())
		{
			Bukkit.getServer().broadcastMessage(Utils.Colorate("&8&l[&3+&8&l] &3" +p.getName()));
		}
		
		if(!joining.contains(p))
		{
			joining.add(p);
		}
		//Player data
        Main.playerdatamanager.createFile(p);
        new BukkitRunnable()
        {

			@Override
			public void run() {
				if(!p.isOnline())
				{
					return;
				}
				if(!PlayerDataManager.data.containsKey(p))
				{
					PlayerDataManager.createData(p);
				}
		        new BukkitRunnable()
		        {

					@Override
					public void run() {
						if(!p.hasPlayedBefore())
						{
							Main.kitmanager.giveKit(p, "starter");
							p.setExp(0);
							p.setLevel(1);
							ServerTutorialApi.getApi().startTutorial("ValTut", p);
						}
				        Main.sb.setupPlayerScoreboard(p);
				        new BukkitRunnable()
				        {

							@Override
							public void run() {
								if(joining.contains(p))
								{
									joining.remove(p);
								}
							}
				        	
				        }.runTaskLater(Main, 20);
					}
		        	
		        }.runTaskLater(Main, 20);
				e.getPlayer().setGravity(true);
				RegenUpgrade.addTimer(p);	
				PartyEvents.setPartyLevel(p);
				SpeedUpgrade.updateSpeed(p);
				
				AbilityManager.saveAbilities(p);
			}
        	
        }.runTaskLater(Main, 40);
        
        
		
		
		//PlayerDataManager.giveInventory(p);
		
		PlayerBlockEvents.regenTime = (20* Math.pow(Math.E, -0.17328 * (Bukkit.getOnlinePlayers().size() - 1)) * 60);
		if(PlayerBlockEvents.regenTime < 120)
		{
			PlayerBlockEvents.regenTime = 120;
		}
		
		
		
	}
	
	
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onLeave(PlayerQuitEvent e)
	{
		e.setQuitMessage("");
	      for(Ability ability : VallendiaMinigame.getInstance().abilitymanager.getAbilities())
	      {
	    	  ability.DisableAbility(e.getPlayer());
	      }
		RootAbility.removeLists(e.getPlayer());
		e.getPlayer().setGravity(true);
		EquipBowAbility.removeBow(e.getPlayer());
		Player p = e.getPlayer();
		new BukkitRunnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Bukkit.getServer().broadcastMessage(Utils.Colorate("&8&l[&c-&8&l] &3" +p.getName()));
			}
			
		}.runTaskLater(VallendiaMinigame.getInstance(), 4);
		AbilityUtils.resetAllMaxHealth(p);
		AbilityUtils.removeCast(p);
		BandageAbility.removeBandage(p);
		if(TutorialNextCommand.next.contains(p))
		{
			TutorialNextCommand.next.remove(p);
		}
		
		//item cooldowns
		for(ItemStack item : p.getInventory().getContents())
		{
			if(item != null)
			{
				if(item.getType() == Material.INK_SACK && item.getDurability() == 10 && item.getItemMeta().hasLore())
				{
		 		   	String ability = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities." + ChatColor.stripColor(Utils.Colorate(item.getItemMeta().getLore().get(0).toLowerCase())));
		 		   	if(AbilityCooldown.isInCooldown(p.getUniqueId(), ability))
		 		   	{
		 		   		item.setAmount(1);
		 		   	}
				}
			}
		}
		AbilityManager.unsaveAbilities(p);
		//PlayerDataManager.saveInventory(p);
		PlayerBlockEvents.regenTime = (20* Math.pow(Math.E, -0.17328 * (Bukkit.getOnlinePlayers().size() - 1)) * 60);
		if(PlayerBlockEvents.regenTime < 120)
		{
			PlayerBlockEvents.regenTime = 120;
		}
		
		PartyEvents.setPartyLevelMinusOriginPlayer(p);
		
		}
		
	
	//Tutorial leave
	@EventHandler
	public void handlestop(TutorialEndEvent e){
		new BukkitRunnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				e.getPlayer().teleport(new Location(e.getPlayer().getLocation().getWorld(), -107.5, 65, -638.5, 0, 0));
			}
			
		}.runTaskLater(VallendiaMinigame.getInstance(), 2);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		if(joining.contains(p))
		{
			e.setCancelled(true);
			return;
		}
		
		if(ServerTutorialApi.getApi().isInTutorial(p.getUniqueId()))
		{
			Language.sendDefaultMessage(p, "You can not speak in the tutorial! If you need help, please /msg a staff member.");
			e.setCancelled(true);
			return;
		}
		e.setMessage(Utils.Colorate("&8&l[" + Language.getPlayerPrefix(p) + "&lLevel " + Main.levelmanager.getLevel(p) + "&8&l] "
				+ "&8&l" + Main.chat.getPlayerPrefix(p) + p.getName() + " > ") + e.getMessage());
		
		e.setFormat("%2$s");
	}
	
	
	@EventHandler
	public void tutorialStop(TutorialEndEvent e){
		if(e.getTutorial().getId().equalsIgnoreCase("valtut"))
		{
					Bukkit.getServer().broadcastMessage(Utils.Colorate("&3&lWelcome &7" + e.getPlayer().getDisplayName() + " &3to the server!"));

		}
	}

}
