package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.AbilityManager;
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;

public class ResetKitCommand implements CommandInterface{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			if(AbilityCooldown.isInCooldown(p.getUniqueId(), "resetkit"))
			{
				Language.sendVallendiaMessage(p, "That command is on cooldown! (" +
				AbilityCooldown.getTimeLeft(p.getUniqueId(), "resetkit") + " secs)");
				return false;
			}
			
			
	        RegionManager regionManager = VallendiaMinigame.getInstance().worldguard.getRegionManager(p.getWorld());
	        ApplicableRegionSet set = regionManager.getApplicableRegions(p.getLocation());

	        for (ProtectedRegion region : set) {

	            if (region != null){

	            	if(!region.getId().equalsIgnoreCase("minigamespawn"))
	            	{
	            		Language.sendDefaultMessage(p, "You can only perform this command at spawn!");
	            		return false;
	            	}

	            }

	        }
			openYesNoMenu(p);
		}
		return false;

}
	
	
	
	
	
	public void openYesNoMenu(Player p)
	{
		AdvInventory InvYesNo = new AdvInventory(Utils.Colorate("&8&lAre you sure?"), 27, Utils.placeholder((byte) 7, " "));
		String itemTitle = Utils.Colorate("&8&lReset Kit");
		String description = Utils.Colorate("&c&lWARNING &cThis will kill you!");
		InvYesNo.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 13), itemTitle, 11, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	p.setHealth(0);
		    		AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), "resetkit", 300, null);
		    		c.start();	
		    	ep.closeInventory();
		    }
		}, description );
		
		
		InvYesNo.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14),  Utils.Colorate("&4&lCancel"), 15, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	ep.closeInventory();
		    }
		});
		
		InvYesNo.openInventory(p);
	}
}

