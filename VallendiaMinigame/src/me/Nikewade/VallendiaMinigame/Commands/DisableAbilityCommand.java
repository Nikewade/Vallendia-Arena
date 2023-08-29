package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.AbilityManager;
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class DisableAbilityCommand implements CommandInterface {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
	    if(sender instanceof  Player && !sender.hasPermission("vallendia.admin"))
	    {
	    	sender.sendMessage(Utils.Colorate("&8You lack permissions!"));
	    	return false;	    	
	    }
	    if(args.length == 2)
	    {
		 	   if(VallendiaMinigame.getInstance().abilitymanager.getAbilities().contains(VallendiaMinigame.getInstance().abilitymanager.getAbility(args[1])))
			   {
		 		   if(!VallendiaMinigame.getInstance().getConfig().getConfigurationSection("DisabledAbilities").contains(args[1]))
		 		   {
			 		   VallendiaMinigame.getInstance().getConfig().set("DisabledAbilities." + args[1], "Disabled");
			 		   VallendiaMinigame.getInstance().saveConfig();
					   sender.sendMessage(Utils.Colorate( "&8Ability disabled!"));
					   if(!AbilityManager.disabledAbilities.contains(VallendiaMinigame.getInstance().abilitymanager.getAbility(args[1])))
					   {
						   AbilityManager.disabledAbilities.add(VallendiaMinigame.getInstance().abilitymanager.getAbility(args[1]));   
					   }
		 		   }else
		 		   {
		 			  VallendiaMinigame.getInstance().getConfig().set("DisabledAbilities." + args[1] , null);
			 		   VallendiaMinigame.getInstance().saveConfig();
					   sender.sendMessage(Utils.Colorate("&8This ability is no longer disabled!"));
					   if(AbilityManager.disabledAbilities.contains(VallendiaMinigame.getInstance().abilitymanager.getAbility(args[1])))
					   {
						   AbilityManager.disabledAbilities.remove(VallendiaMinigame.getInstance().abilitymanager.getAbility(args[1]));   
					   }
		 		   }
		 		   
			   }else
			   {
				   sender.sendMessage(Utils.Colorate( "&8This is not an ability!"));
			   }
	    }else
	    {
	    	if(args.length == 3)
	    	{
	    		if(VallendiaMinigame.getInstance().abilitymanager.getAbilities().contains(VallendiaMinigame.getInstance().
	    				abilitymanager.getAbility(args[1] + " " + args[2])))
	    		{
	    			String abilityname = args[1] + " " + args[2];
	    			
	    			
			 		   if(!VallendiaMinigame.getInstance().getConfig().getConfigurationSection("DisabledAbilities").contains(abilityname))
			 		   {
				 		   VallendiaMinigame.getInstance().getConfig().set("DisabledAbilities." + abilityname, "Disabled");
				 		   VallendiaMinigame.getInstance().saveConfig();
						   sender.sendMessage(Utils.Colorate( "&8Ability disabled!"));
						   if(!AbilityManager.disabledAbilities.contains(VallendiaMinigame.getInstance().abilitymanager.getAbility(abilityname)))
						   {
							   AbilityManager.disabledAbilities.add(VallendiaMinigame.getInstance().abilitymanager.getAbility(abilityname));   
						   }
			 		   }else
			 		   {
			 			  VallendiaMinigame.getInstance().getConfig().set("DisabledAbilities." + abilityname , null);
				 		   VallendiaMinigame.getInstance().saveConfig();
						   sender.sendMessage(Utils.Colorate("&8This ability is no longer disabled!"));
						   if(AbilityManager.disabledAbilities.contains(VallendiaMinigame.getInstance().abilitymanager.getAbility(abilityname)))
						   {
							   AbilityManager.disabledAbilities.remove(VallendiaMinigame.getInstance().abilitymanager.getAbility(abilityname));   
						   }
			 		   }
	    			
	    			
				}else
				 {
					sender.sendMessage(Utils.Colorate( "&8This is not an ability!"));
				 }
	    	}
	    }
		return false;
	}
}
