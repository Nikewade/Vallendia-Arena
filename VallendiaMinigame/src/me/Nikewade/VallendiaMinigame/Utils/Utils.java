package me.Nikewade.VallendiaMinigame.Utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class Utils {
	public static HashMap<Location, BlockState> blocks = new HashMap<>();
    private static List<String> changes = new LinkedList<String>();
    private static Map<Entity,Effect> particle = new HashMap<>();
	private static Random random = new Random();
	

	  public static void sendVallendiaMessage(Player p, String one, String two , String three , String four , String five)
	  {  
		  
	  }
	  public static void sendVallendiaCenterMessage(Player p, String one, String two , String three , String four , String five)
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
			Utils.sendCentredMessage(p, "&8&m-----------------------------------");
			Utils.sendCentredMessage(p, "&9" + one);
			Utils.sendCentredMessage(p, "&9" + two);
			Utils.sendCentredMessage(p, "&9" + three);
			Utils.sendCentredMessage(p, "&9" + four);
			Utils.sendCentredMessage(p, "&9" + five);
			Utils.sendCentredMessage(p, "&8&m-----------------------------------");
	  }
	
	  public static String Colorate(String msg) // Allows the use of & color codes.
	  {
	    return ChatColor.translateAlternateColorCodes('&', msg);
	  }
	  
	  
		public static final void log(Object msg) {
			Bukkit.getServer().getConsoleSender().sendMessage(Utils.Colorate("" + msg));
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
	 
	  public static Location getBlockBehindPlayer(LivingEntity target) {
	        Vector inverseDirectionVec = target.getLocation().getDirection().normalize().multiply(-1);
	        return target.getLocation().add(inverseDirectionVec);
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
	  
	  public static ItemStack getTippedArrowItemStack(PotionType type, int level, boolean extend, boolean upgraded, String displayName){
	        ItemStack arrow = new ItemStack(Material.TIPPED_ARROW, 1);
	        PotionMeta meta = (PotionMeta) arrow.getItemMeta();
	        meta.setBasePotionData(new PotionData(type, extend, upgraded));
	        arrow.setItemMeta(meta);
	        return arrow;
	    }
	  
	  
	  public static void regenBlock(Block b, int seconds)
	  {
				BlockState state = b.getState();
				Location location = b.getLocation();
				blocks.put(location, state);
				String block = b.getTypeId() + ":" + b.getData() + ":" + b.getWorld().getName() + ":" + b.getX() + ":" + b.getY() + ":" + b.getZ();
				Utils.changes.add(block);
				
		    	new BukkitRunnable() {
		    		Location loc = b.getLocation();
		            @Override
		            public void run() {	
		            	if(!blocks.containsKey(loc))
		            	{
		            		return;	
		            	}
		            	
		            	changes.remove(block);
		            	Material mat = blocks.get(loc).getType();
		            	
		            	blocks.get(loc).getBlock().setType(mat);
		            	blocks.get(loc).update();
		            	blocks.remove(loc);
		            }
			}.runTaskLater(VallendiaMinigame.getInstance(), 20 * seconds);	
			return;
	  }
	  
	   @SuppressWarnings("deprecation")
	    public static void restoreBlocks() {
	        int blocks = 0;
	        for (String b : changes) {
	            String[] blockdata = b.split(":");
	          
	            int id = Integer.parseInt(blockdata[0]);
	            byte data = Byte.parseByte(blockdata[1]);
	            World world =Bukkit.getWorld(blockdata[2]);
	            int x = Integer.parseInt(blockdata[3]);
	            int y = Integer.parseInt(blockdata[4]);
	            int z = Integer.parseInt(blockdata[5]);
	          
	            world.getBlockAt(x, y, z).setTypeId(id);
	            world.getBlockAt(x, y, z).setData(data);
	            blocks++;
	        }
	      
	        System.out.println(blocks+" blocks regenerated!");
	    }
	   
	   
	   
	   public static int randomNumber(int lowest, int max)
	   {
		int randomAmount = random.nextInt(max) + lowest;
		return randomAmount;
	   }
	   
	   public static void entityParticleTimer(Entity entity, Effect effect, int seconds)
	   {
		   	effect.start();
   			particle.put(entity, effect);
   			
   			new BukkitRunnable() {
   	            @Override
   	            public void run() {
   	            	if(particle.containsKey(entity))
   	            	{
   	            		particle.get(entity).cancel();
   	            		particle.remove(entity);
   	            	}
   	            }
   	        }.runTaskLaterAsynchronously(VallendiaMinigame.getInstance(), seconds*20L); 
		   
	   }
	   
	   


}
