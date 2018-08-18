package me.Nikewade.VallendiaMinigame.Utils;

import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class Utils {
	
	  public static String Colorate(String msg) // Allows the use of & color codes.
	  {
	    return ChatColor.translateAlternateColorCodes('&', msg);
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
