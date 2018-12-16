package me.Nikewade.VallendiaMinigame.Utils;

import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class Utils {
	
	
	  public static void sendVallendiaMessage(Player p, String one, String two , String three , String four , String five, String six)
	  {  
		  
		  if(one == null)
		  {
			  one = "";
		  }
		  if(two == null)
		  {
			  two = "";
		  }
		  if(three == null)
		  {
			  three = "";
		  }
		  if(four == null)
		  {
			  four = "";
		  }
		  if(five == null)
		  {
			  five = "";
		  }
		  if(six == null)
		  {
			  six = "";
		  }
			Utils.sendCentredMessage(p, "&8&m-----------------------------------");
			Utils.sendCentredMessage(p, "&b&l" + one);
			Utils.sendCentredMessage(p, "&b&l" + two);
			Utils.sendCentredMessage(p, "&b&l" + three);
			Utils.sendCentredMessage(p, "&b&l" + four);
			Utils.sendCentredMessage(p, "&b&l" + five);
			Utils.sendCentredMessage(p, "&b&l" + six);
			Utils.sendCentredMessage(p, "&8&m-----------------------------------");
	  }
	
	  public static String Colorate(String msg) // Allows the use of & color codes.
	  {
	    return ChatColor.translateAlternateColorCodes('&', msg);
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
		    int CENTER_PX = 154;
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
	  
	  public static ItemStack placeholder(byte data, String n) {
		    @SuppressWarnings("deprecation")
		    ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1, data);
		    ItemMeta placeholdermeta = placeholder.getItemMeta();
		    placeholdermeta.setDisplayName(n);
		    placeholder.setItemMeta(placeholdermeta);
		    return placeholder;
		}
	 
	  
	  
	  public static void removeEnchantments(ItemStack item)
	  {
    	  if(item != null)
    	  {
    	      for(Entry<Enchantment, Integer> e : item.getEnchantments().entrySet()){
		          item.removeEnchantment(e.getKey());  
	      }   
    	  } 
	  }
	  
	  
	  public static ItemStack getPotionItemStack(PotionType type, int level, boolean extend, boolean upgraded, String displayName){
	        ItemStack potion = new ItemStack(Material.POTION, 1);
	        PotionMeta meta = (PotionMeta) potion.getItemMeta();
	        meta.setBasePotionData(new PotionData(type, extend, upgraded));
	        potion.setItemMeta(meta);
	        return potion;
	    }

}
