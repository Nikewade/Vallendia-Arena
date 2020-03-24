package me.Nikewade.VallendiaMinigame.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
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
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEquipment;

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
		
		
	    public static List<Block> getNearbyBlocks(Location location, double radius) {
	        List<Block> blocks = new ArrayList<Block>();
	        for(double x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
	            for(double y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
	                for(double z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
	                   blocks.add(location.getWorld().getBlockAt((int)x, (int)y, (int)z));
	                }
	            }
	        }
	        return blocks;
	    }
	  
		
		
		public static  void setMetaDataPlacedBlock(Block b, boolean placedBlock) {
		    b.setMetadata("PlacedBlock", new FixedMetadataValue(VallendiaMinigame.getInstance(), placedBlock));
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
				if(!blocks.containsKey(location))
				{
					blocks.put(location, state);	
				}else return;
				String block = b.getTypeId() + ":" + b.getData() + ":" + b.getWorld().getName() + ":" + b.getX() + ":" + b.getY() + ":" + b.getZ();
				if(!changes.contains(block))
				{
					Utils.changes.add(block);	
				}else return;
				
		    	new BukkitRunnable() {
		    		Location loc = b.getLocation();
		            @Override
		            public void run() {	
		            	if(!blocks.containsKey(loc))
		            	{
		            		return;	
		            	}

		            	for(Entity e : loc.getWorld().getNearbyEntities(loc, 1, 1, 1))
		            	{
		            		if(e instanceof LivingEntity)
		            		{
		            			int x = 1;
		            			while(e.getLocation().getBlock().getType().isSolid() || 
		            					e.getLocation().add(0, 1, 0).getBlock().getType().isSolid() ||
		            					e.getLocation().add(0, 2, 0).getBlock().getType().isSolid())
		            			{
		            				
		            				if(x >= 100)
		            				{
			                			e.teleport(e.getLocation().add(0, 1, 0));
		            				}
		            				x++;
		            				Block n = e.getLocation().getBlock().getRelative(BlockFace.NORTH);
		            				Block s = e.getLocation().getBlock().getRelative(BlockFace.SOUTH);
		            				Block east = e.getLocation().getBlock().getRelative(BlockFace.EAST);
		            				Block w = e.getLocation().getBlock().getRelative(BlockFace.WEST);
		                			e.teleport(e.getLocation().add(0, 1, 0));
		            				if(!n.getType().isSolid())
		            				{
		            					e.teleport(n.getLocation());
		            				}
		            				
		            				if(!s.getType().isSolid())
		            				{
		            					e.teleport(s.getLocation());
		            				}
		            				
		            				if(!east.getType().isSolid())
		            				{
		            					e.teleport(east.getLocation());
		            				}
		            				
		            				if(!w.getType().isSolid())
		            				{
		            					e.teleport(w.getLocation());
		            				}
		            					
		            			}
		            		}
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
            	for(Entity e : world.getNearbyEntities(world.getBlockAt(x, y, z).getLocation(), 1, 1, 1))
            	{
            		if(e instanceof LivingEntity)
            		{
            			int time = 1;
            			while(e.getLocation().getBlock().getType().isSolid() || e.getLocation().add(0, 1, 0).getBlock().getType().isSolid())
            			{
            				
            				if(time >= 50)
            				{
	                			e.teleport(e.getLocation().add(0, 1, 0));
            				}
            				time++;
            				Block n = e.getLocation().getBlock().getRelative(BlockFace.NORTH);
            				Block s = e.getLocation().getBlock().getRelative(BlockFace.SOUTH);
            				Block east = e.getLocation().getBlock().getRelative(BlockFace.EAST);
            				Block w = e.getLocation().getBlock().getRelative(BlockFace.WEST);
                			e.teleport(e.getLocation().add(0, 1, 0));
            				if(!n.getType().isSolid())
            				{
            					e.teleport(n.getLocation());
            				}
            				
            				if(!s.getType().isSolid())
            				{
            					e.teleport(s.getLocation());
            				}
            				
            				if(!east.getType().isSolid())
            				{
            					e.teleport(east.getLocation());
            				}
            				
            				if(!w.getType().isSolid())
            				{
            					e.teleport(w.getLocation());
            				}
            					
            			}
            		}
            	}
            	
	            
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
		
	    //from BKCommonLib
	    public static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
	    public static final BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };

	    /**
	     * Gets the horizontal Block Face from a given yaw angle<br>
	     * This includes the NORTH_WEST faces
	     *
	     * @param yaw angle
	     * @return The Block Face of the angle
	     */
	    public static BlockFace yawToFace(float yaw) {
	        return yawToFace(yaw, false);
	    }

	    /**
	     * Gets the horizontal Block Face from a given yaw angle
	     *
	     * @param yaw angle
	     * @param useSubCardinalDirections setting, True to allow NORTH_WEST to be returned
	     * @return The Block Face of the angle
	     */
	    public static BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
	        if(yaw > 360) yaw -= 360;
	        else if(yaw < 360) yaw += 360;
	        if (useSubCardinalDirections) {
	            return radial[Math.round(yaw / 45f) & 0x7];
	        } else {
	            return axis[Math.round(yaw / 90f) & 0x3];
	        }
	    }
	    
	    public static double getPercentHigherOrLower(int Percent, boolean add)
	    {
	    	double percentReturn = 1;
	    	if(add)
	    	{
        		percentReturn =  ((Percent* 0.1) * 0.1) + 1;
	    	}else  percentReturn =  1 - ((Percent* 0.1) * 0.1);
			return percentReturn;
	    	
	    }
	    
	    
	    public static void hideArmor(Player p)
	    {
	        PacketPlayOutEntityEquipment helmetPacket = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)));
	        PacketPlayOutEntityEquipment chestPacket = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)));
	        PacketPlayOutEntityEquipment legPacket = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)));
	        PacketPlayOutEntityEquipment bootsPacket = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.FEET, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)));
	        
	    	            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(helmetPacket);
	    	            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(chestPacket);
	    	            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(legPacket);
	    	            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(bootsPacket);
	        	        for(Entity player : p.getNearbyEntities(20, 20, 20)) 
	        	        {
	        	        	if(!(player instanceof Player)) continue;
	        	            Player reciever = (Player) player;
	        	            ((CraftPlayer)reciever).getHandle().playerConnection.sendPacket(helmetPacket);
	        	            ((CraftPlayer)reciever).getHandle().playerConnection.sendPacket(chestPacket);
	        	            ((CraftPlayer)reciever).getHandle().playerConnection.sendPacket(legPacket);
	        	            ((CraftPlayer)reciever).getHandle().playerConnection.sendPacket(bootsPacket);
	        	        }
	    }
	    
	    public static void showArmor(Player p)
	    {
			VallendiaMinigame.getInstance().protocolManager.updateEntity(p, (List<Player>) Bukkit.getOnlinePlayers());
	    }
	    
	    
	    
	    public static int randomNumberBetween(int lowestAmount, int maxAmount)
	    {
	    	return ThreadLocalRandom.current().nextInt(lowestAmount, maxAmount + 1);
	    }
	    
	    @SuppressWarnings("deprecation")
	    public static boolean canDamage(Entity attacker, Entity damaged) {
	        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(attacker, damaged, DamageCause.ENTITY_ATTACK, 0);
	        Bukkit.getPluginManager().callEvent(event);
	        return !event.isCancelled();
	    }
	    
	    
	    public static final List<Class<?>> getClassesInPackage(String packageName) {
	        String path = packageName.replaceAll("\\.", File.separator);
	        List<Class<?>> classes = new ArrayList<>();
	        String[] classPathEntries = System.getProperty("java.class.path").split(
	                System.getProperty("path.separator")
	        );

	        String name;
	        for (String classpathEntry : classPathEntries) {
	            if (classpathEntry.endsWith(".jar")) {
	                File jar = new File(classpathEntry);
	                try {
	                    JarInputStream is = new JarInputStream(new FileInputStream(jar));
	                    JarEntry entry;
	                    while((entry = is.getNextJarEntry()) != null) {
	                        name = entry.getName();
	                        if (name.endsWith(".class")) {
	                            if (name.contains(path) && name.endsWith(".class")) {
	                                String classPath = name.substring(0, entry.getName().length() - 6);
	                                classPath = classPath.replaceAll("[\\|/]", ".");
	                                classes.add(Class.forName(classPath));
	                            }
	                        }
	                    }
	                } catch (Exception ex) {
	                    // Silence is gold
	                }
	            } else {
	                try {
	                    File base = new File(classpathEntry + File.separatorChar + path);
	                    for (File file : base.listFiles()) {
	                        name = file.getName();
	                        if (name.endsWith(".class")) {
	                            name = name.substring(0, name.length() - 6);
	                            classes.add(Class.forName(packageName + "." + name));
	                        }
	                    }
	                } catch (Exception ex) {
	                    // Silence is gold
	                }
	            }
	        }

	        return classes;
	    }

	    

}
