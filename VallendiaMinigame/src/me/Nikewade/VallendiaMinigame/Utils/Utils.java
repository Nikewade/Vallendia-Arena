package me.Nikewade.VallendiaMinigame.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.WorldBorderAction;

import de.slikey.effectlib.Effect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagDouble;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagString;

public class Utils {
	public static HashMap<Location, BlockState> blocks = new HashMap<>();
    private static List<String> changes = new LinkedList<String>();
    public static Map<Entity,Effect> particle = new HashMap<>();
	private static Random random = new Random();
	
	  public static String Colorate(String msg) // Allows the use of & color codes.
	  {
	    return ChatColor.translateAlternateColorCodes('&', msg);
	  }
	  
	  
	  public static ArrayList<String> ColorateList(ArrayList<String> list, ChatColor color) 
	  {
		  ArrayList<String> newList = new ArrayList<>();
		  for(String line : list)
		  {
			  line.replaceAll(line, Utils.Colorate("&7" + line));
			  newList.add(line);
		  }
	    return newList;
	  }
	  
	  
		public static final void log(Object msg) {
			Bukkit.getServer().getConsoleSender().sendMessage(Utils.Colorate("" + msg));
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
	   
	   

	   
		public static void sendWorldBorderPacket(Player p, int dist, double oldradius, double newradius, long delay) {
			ProtocolManager protocolManager = VallendiaMinigame.getInstance().protocolManager;
	    	PacketContainer border = protocolManager.createPacket(PacketType.Play.Server.WORLD_BORDER);
	        
			border.getWorldBorderActions().write(0, WorldBorderAction.INITIALIZE);
			border.getIntegers()
			.write(0, 29999984)
			.write(1, 15)
			.write(2, dist);
			border.getLongs()
			.write(0, (long) 1);
			border.getDoubles()
			.write(0, p.getLocation().getX())
			.write(1, p.getLocation().getY())
			.write(2, newradius)
			.write(3, oldradius);
			try {
				protocolManager.sendServerPacket(p, border);
			} catch (InvocationTargetException e) {
				throw new RuntimeException("Cannot send packet " + border, e);
			}
		}
	   
	   


}
