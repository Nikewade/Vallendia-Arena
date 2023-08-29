package me.Nikewade.VallendiaMinigame.Commands;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.AbilityManager;
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class AbilityCommand implements CommandInterface{
	VallendiaMinigame main  =  VallendiaMinigame.getInstance();
	public static Map<Player, BukkitTask> casting = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
	    
	    if(!(args.length >1) || args.length > 5)
	    {
			sender.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
			sender.sendMessage("");
			sender.sendMessage(Utils.Colorate("&3/vallendia ability run &9(slot)"));
			sender.sendMessage("");
			sender.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));	
			return false;
	    }
	    
	    if(args.length == 3)
	    {
	 	   if(args[1].equalsIgnoreCase("run"))
		   {
	 		   if(!(sender instanceof Player))
	 		   {
	 			   sender.sendMessage("You need to be a player to run this command!");
	 			   return false;
	 		   }
	 		   Player p = (Player) sender;
	 		   	RegionManager regionManager = VallendiaMinigame.getInstance().worldguard.getRegionManager(p.getWorld());
	 		   	ApplicableRegionSet arset = regionManager.getApplicableRegions(p.getLocation());
	 		   	LocalPlayer localPlayer = VallendiaMinigame.getInstance().worldguard.wrapPlayer(p);
	 		   	if(!arset.allows((StateFlag) VallendiaMinigame.blockAbilities, localPlayer))
	 		   	{
	 		   		Language.sendDefaultMessage(p, "You cant use abilities here!");
	 		   		return false;
	 		   	}
				   String slot = args[2];  
				   String ability = AbilityManager.getAbilitySlot(slot, (Player) sender);
				   if(main.abilitymanager.getAbility(AbilityManager.getAbilitySlot(slot, (Player) sender)) != null) 
						   {
			    		if(!AbilityCooldown.isInCooldown(p.getUniqueId(), ability))
			    		{  
				    		if(main.abilitymanager.getAbility(ability).RunAbility(p) && main.abilitymanager.getCooldown(ability, p) > 0)
				    		{
				    			if(AbilityUtils.casting.containsKey(p))
				    			{
				    				//already checking for casting
				    				if(casting.containsKey(p))
				    				{
				    					return false;
				    				}
				    	 			BukkitTask task = new BukkitRunnable() {
				    	 	            @Override
				    	 	            public void run() {
				    	 	            	if(!AbilityUtils.casting.containsKey(p))
				    	 	            	{
				    	 	            		
				    				    		AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), ability, main.abilitymanager.getCooldown(ability, p), AbilityManager.locateAbilityItem(p, ability));
				    				    		c.start();
				    				    		casting.remove(p);
				    				    		this.cancel();
				    	 	            	}
				    	 	            }
				    	 	        }.runTaskTimer(VallendiaMinigame.getInstance(), 0 , 20L);
				    	 	        casting.put(p, task);
				    			}else{
						    		AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), ability, main.abilitymanager.getCooldown(ability, p), AbilityManager.locateAbilityItem(p, ability));
						    		c.start();		
				    			}
					    		 
				    		}
			    		}else 
			    		{
			    			p.sendMessage(Utils.Colorate(" &8&lCooldown: " + AbilityCooldown.getTimeLeft(p.getUniqueId(), ability) + " secs"));
			    			return false;
			    		}
						   }else
						   {
							   sender.sendMessage(Utils.Colorate("&8This ability slot is empty!"));
							   return false;
						   }
			   return false;
		   }else
		   {
			   sender.sendMessage(Utils.Colorate("&8Invalid command. Try /vallendia ability run (slot)"));
		   }
	    }
	   
	    
	    
	    return false;
	}
}
