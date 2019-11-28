package me.Nikewade.VallendiaMinigame.Events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.BandageAbility;
import me.Nikewade.VallendiaMinigame.Abilities.EquipBowAbility;
import me.Nikewade.VallendiaMinigame.Abilities.LastStandAbility;
import me.Nikewade.VallendiaMinigame.Abilities.RootAbility;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Upgrades.RegenUpgrade;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class PlayerJoinEvents implements Listener{
	VallendiaMinigame Main;
	
	
	
	public PlayerJoinEvents(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		RegenUpgrade.addTimer(p);
		if(!p.hasPlayedBefore())
		{
			Main.kitmanager.giveKit(p, "starter");
			p.setExp(0);
			p.setLevel(1);
		}
        Main.playerdatamanager.createFile(p);
       // Main.sb.runScoreboard(p);
        Main.sb.setupPlayerScoreboard(p);
		e.getPlayer().setGravity(true);
	}
	
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLeave(PlayerQuitEvent e)
	{
	      for(Ability ability : VallendiaMinigame.getInstance().abilitymanager.getAbilities())
	      {
	    	  ability.DisableAbility(e.getPlayer());
	      }
		RootAbility.removeLists(e.getPlayer());
		e.getPlayer().setGravity(true);
		EquipBowAbility.removeBow(e.getPlayer());
		Player p = e.getPlayer();
		AbilityUtils.resetAllMaxHealth(p);
		AbilityUtils.removeCast(p);
		AbilityUtils.removeSoftCast(p);
		BandageAbility.removeBandage(p);
		
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
		}
		
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChat(PlayerChatEvent e)
	{
		Player p = e.getPlayer();
		e.setFormat (Utils.Colorate("&8&l[" + Language.getPlayerPrefix(p) + "&llvl" + Main.levelmanager.getLevel(p) + "&8&l]"
					+ "&8&l[" + Main.chat.getPlayerPrefix(p) + "&8&l]" + "&7" + p.getName() + ": ") + e.getMessage());
	}

}
