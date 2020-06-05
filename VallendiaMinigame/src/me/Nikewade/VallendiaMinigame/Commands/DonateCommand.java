package me.Nikewade.VallendiaMinigame.Commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.SirBlobman.combatlogx.utility.CombatUtil;
import com.kirelcodes.miniaturepets.MiniaturePets;
import com.kirelcodes.miniaturepets.utils.APIUtils;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Donations.BlockTrailsMenuGUI;
import me.Nikewade.VallendiaMinigame.Donations.ParticleTrailsMenuGUI;
import me.Nikewade.VallendiaMinigame.Donations.PetMenuGUI;
import me.Nikewade.VallendiaMinigame.Donations.WingsMenuGUI;
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.kvq.supertrailspro.SuperTrails;
import me.kvq.supertrailspro.API.SuperTrailsAPI;
import me.kvq.supertrailspro.API.SuperTrailsGui;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class DonateCommand implements CommandInterface, CommandExecutor {
	VallendiaMinigame  main  = VallendiaMinigame.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
            if(CombatUtil.isInCombat(p))
            {
                return false;
            }
			openCosmeticMainMenu(p);		
		}

		return false;
		}
	
	public static void openCosmeticMainMenu(Player p)
	{
		p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
		AdvInventory mainMenu = new AdvInventory(Utils.Colorate("&3&lCosmetics"), 27, Utils.placeholder((byte) 15, " "));

		mainMenu.setItem(new ItemStack(Material.ELYTRA), Utils.Colorate("&9&lWings"), 10, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	WingsMenuGUI.openWingsMenu(ep);
			    		
			    }
			});
		  
		mainMenu.setItem(new ItemStack(159,1,(short) 4), Utils.Colorate("&9&lBlock Trails"), 12, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	BlockTrailsMenuGUI.openBlockTrailMenu(ep);
			    }
			});
		  
		 mainMenu.setItem(new ItemStack(Material.FIREWORK), Utils.Colorate("&9&lParticle Trails"), 14, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	if(SuperTrailsAPI.getPlayerData(ep).getMode() == 0)
			    	{
				    	ParticleTrailsMenuGUI.openDefaultMenu(ep);
				    	return;
			    	}
			    	if(SuperTrailsAPI.getPlayerData(ep).getMode() == 1)
			    	{
				    	ParticleTrailsMenuGUI.openCirclemenu(ep);
				    	return;
			    	}
			    	if(SuperTrailsAPI.getPlayerData(ep).getMode() == 2)
			    	{
				    	ParticleTrailsMenuGUI.openPulsemenu(ep);
				    	return;
			    	}
			    	if(SuperTrailsAPI.getPlayerData(ep).getMode() == 4)
			    	{
				    	ParticleTrailsMenuGUI.openSpiralmenu(ep);
				    	return;
			    	}
			    	ParticleTrailsMenuGUI.openDefaultMenu(ep);

			    }
			});
		  
		 mainMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc5MzI4NzM4MSwKICAicHJvZmlsZUlkIiA6ICI3ZGEyYWIzYTkzY2E0OGVlODMwNDhhZmMzYjgwZTY4ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJHb2xkYXBmZWwiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTQxZWNmNDYyM2FlMTNkZWE3MzM4NTU1OTNkMzA4NTFhZmE4N2VmMmQ5ZmY5YTY4YzMyZWQ0YmJhMTgxYTMyZCIKICAgIH0KICB9Cn0=")), 
				 Utils.Colorate("&9&lPets"), 16, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	PetMenuGUI.openPetMenu(ep);
			    }
			});
		 
		 mainMenu.setItem(new ItemStack(Material.NETHER_STAR), Utils.Colorate("&b&lDonate"), 22, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	ep.sendMessage(Utils.Colorate("&3&lDonate Here: &3http://vallendia.tebex.io/"));
			    	ep.closeInventory();
			    }
			});
		 mainMenu.openInventory(p);
		
	}
		
}

