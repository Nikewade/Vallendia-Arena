package me.Nikewade.VallendiaMinigame.Utils;

<<<<<<< HEAD
=======
import org.bukkit.Bukkit;
>>>>>>> second-repo/master
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;

public class Language {
	public static void sendAbilityUseMessage(LivingEntity target, String msg, String ability)
	{
		target.sendMessage(Utils.Colorate("&3&l[" + VallendiaMinigame.getInstance().abilitymanager.getAbility(ability).getName() + "] &8" + msg));
	}
	
	
	public static void sendDefaultMessage(Player p, String msg)
	{
		p.sendMessage(Utils.Colorate("&8" + msg));
	}
	
	
	public static void sendVallendiaMessage(Player p, String msg)
	{
<<<<<<< HEAD
		p.sendMessage(Utils.Colorate("&3&l[Vallendia] &8" + msg));
=======
		p.sendMessage(Utils.Colorate("&8&l[&3&lVallendia&8&l] &8" + msg));
	}
	
	public static void sendVallendiaBroadcast(String msg)
	{
		Bukkit.broadcastMessage(Utils.Colorate("&8&l[&3&lVallendia&8&l] &8" + msg));
>>>>>>> second-repo/master
	}
	
	public static String getPlayerPrefix(Player p)
	{
			return p.getScoreboard().getPlayerTeam(p).getPrefix();	
	}
	
	  public static void sendCentredMessage(Player p, String message) {
		    if(message == null || message.equals("")) {
		        p.sendMessage("");
		        return;
		    }
		    message = ChatColor.translateAlternateColorCodes('&', message);
		 
		    int messagePxSize = 0;
		    boolean previousCode = false;
		    boolean isBold = false;
		 
		    for(char c : message.toCharArray()){
		        if(c == '§'){
		            previousCode = true;
		        }else if(previousCode){
		            previousCode = false;
		            isBold = c == 'l' || c == 'L';
		        }else{
		            DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
		            messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
		            messagePxSize++;
		        }
		    }
		    int CENTER_PX = 120;
		    int halvedMessageSize = messagePxSize / 2;
		    int toCompensate = CENTER_PX - halvedMessageSize;
		    int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
		    int compensated = 0;
		    StringBuilder sb = new StringBuilder();
		    while(compensated < toCompensate){
		        sb.append(" ");
		        compensated += spaceLength;
		    }
		    p.sendMessage(sb.toString() + message);
		}
	  
}
