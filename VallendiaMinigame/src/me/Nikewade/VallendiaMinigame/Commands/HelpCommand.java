package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.combatlogx.utility.CombatUtil;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Donations.BlockTrailsMenuGUI;
import me.Nikewade.VallendiaMinigame.Donations.ParticleTrailsMenuGUI;
import me.Nikewade.VallendiaMinigame.Donations.PetMenuGUI;
import me.Nikewade.VallendiaMinigame.Donations.WingsMenuGUI;
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import me.kvq.supertrailspro.API.SuperTrailsAPI;

public class HelpCommand implements Listener{
	VallendiaMinigame  main  = VallendiaMinigame.getInstance();
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void OnCommand(PlayerCommandPreprocessEvent e) 
	{
		if(e.getMessage().equalsIgnoreCase("/help"))
		{
			e.setCancelled(true);
			openCosmeticMainMenu(e.getPlayer());	
			return;
		}
	}
	
	public static void openCosmeticMainMenu(Player p)
	{
		p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);

		AdvInventory mainMenu = new AdvInventory(Utils.Colorate("&3&lHelp Menu"), 27, Utils.placeholder((byte) 15, " "));

		mainMenu.setItem(new ItemStack(Material.CHEST), Utils.Colorate("&7&lGeneral"), 3, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	p.closeInventory();
			    	Bukkit.dispatchCommand(p, "vallendia");
			    		
			    }
			});
		  
		mainMenu.setItem(new ItemStack(Material.TOTEM), Utils.Colorate("&3&lParties"), 5, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	p.closeInventory();
			    	Bukkit.dispatchCommand(p, "party");
			    }
			});
		  
		 mainMenu.setItem(new ItemStack(Material.BOOK_AND_QUILL), Utils.Colorate("&c&lRules"), 10, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	p.closeInventory();
			    	Bukkit.dispatchCommand(p, "rules");

			    }
			});
		  
		 mainMenu.setItem(new ItemStack(Material.EMPTY_MAP), 
				 Utils.Colorate("&3&lTicket"), 16, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	p.closeInventory();
			    	Bukkit.dispatchCommand(p, "report");
			    }
			});
		 
		 mainMenu.setItem(new ItemStack(Material.NETHER_STAR), Utils.Colorate("&b&lCosmetics"), 21, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	p.closeInventory();
			    	Bukkit.dispatchCommand(p, "donate");
			    }
			});
		 
		 mainMenu.setItem(new ItemStack(Material.TRIPWIRE_HOOK), Utils.Colorate("&6&lVote"), 23, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	p.closeInventory();
			    	Bukkit.dispatchCommand(p, "vote");
			    }
			});
		 mainMenu.openInventory(p);
		
	}
		
}
