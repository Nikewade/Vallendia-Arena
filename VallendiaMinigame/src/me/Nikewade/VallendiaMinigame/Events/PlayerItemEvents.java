package me.Nikewade.VallendiaMinigame.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.SunderArmorAbility;
import me.Nikewade.VallendiaMinigame.Abilities.SunderWeaponAbility;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class PlayerItemEvents implements Listener {
	VallendiaMinigame Main;
	ArrayList<Player> wandCooldown = new ArrayList<>();
	double wandCooldownAmount = 0.5;
	public static Map<Player, BukkitTask> casting = new HashMap<>();
	private static List<String> noStunAbilities = new ArrayList<>();
	
	
	
	public PlayerItemEvents(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
		noStunAbilities.add("Break Free");
		

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void rightClickItem(PlayerInteractEvent e) {
 	   	if(!(e.getHand() == EquipmentSlot.HAND) || e.getHand() == EquipmentSlot.OFF_HAND) return;
	    Action a = e.getAction();
	    if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)
	    {
		    Player p = e.getPlayer();
		    ItemStack item = p.getInventory().getItemInMainHand();
		    Material itemtype = item.getType();
			if(!AbilityUtils.isStunned(p))
			{
		    	if(itemtype != Material.AIR && item.getItemMeta().hasDisplayName())
		    	{
			 	   	String itemname = item.getItemMeta().getDisplayName();
			    	   if(itemtype == Material.NETHER_STAR && itemname.equals(Utils.Colorate("&b&lKit")))
			    	   {
				    		Main.guihandler.openGui(p, "kit");   
				    		p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
				    		return;
			    	   }	
			    	   
			    	   if(itemtype == Material.NETHER_STAR && itemname.equals(Utils.Colorate("&b&lShop")))
			    	   {
			    		   if(SunderWeaponAbility.sundered.contains(p)||SunderArmorAbility.sundered.contains(p))
	                       {
	                           return;
	                       }
				    		Main.guihandler.openGui(p, "shop");  
				    		p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
				    		return;
			    	   }
			    	   
			    	   
			    	   //MAGE WAND
			    	   if(itemtype == Material.STICK && itemname.equals(Utils.Colorate("&3&lWand")))
			    	   {
			    		   
			    		   if(!wandCooldown.contains(p))
			    		   {
			    			   
			    		        RegionManager regionManager = Main.worldguard.getRegionManager(p.getWorld());
			    		        ApplicableRegionSet set = regionManager.getApplicableRegions(p.getLocation());

			    		        for (ProtectedRegion region : set) {

			    		            if (region != null){

			    		            	if(region.getId().equalsIgnoreCase("minigamespawn"))
			    		            	{
			    		            		Language.sendDefaultMessage(p, "You can't use that here!");
			    		            		return;
			    		            	}

			    		            }

			    		        }
			    			   
			    			   
				    		   new BukkitRunnable(){
				    		         
				    	            double t = 1;
				    	            Location loc = p.getLocation();
				    	            Vector dir = loc.getDirection().normalize();
				    	            
				    	            double t2 = 1;
				    	            Location loc2 = p.getLocation();
				    	            Vector dir2 = loc2.getDirection().normalize();
				    	         
				    	            @Override
				    	            public void run() {
		    	            			
				    	            	t = t + 0.7;
				    	                double x = dir.getX() * t;
				    	                double y = dir.getY() * t + 1.5D;
				    	                double z = dir.getZ() * t;
				    	                loc.add(x,y,z);
				    	                
				    	            	t2 = t2 + 0.7;
				    	                double x2 = dir.getX() * t2;
				    	                double y2 = dir.getY() * t2 + 1.5D;
				    	                double z2 = dir.getZ() * t2;
				    	                loc2.add(x2,y2,z2);
				    	                
				    	                
				    	               if (loc2.getBlock().getType().isSolid())
				    	                {
				    	                	this.cancel();
				    	                	return;
				    	                }
				    	                

			    	              		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			    	            		se.particle = Particle.END_ROD;
			    	            		se.iterations = 1;
			    	            		se.particles = 1;
			    	            		se.radius = 0.2;
			    	            		se.speed = (float) 0;
			    	            		se.visibleRange = 50;
			    	            			se.setLocation(loc);
			    	            			se.start();
			    	            			
			    	            			//block behind the particle incase the particle passes thru a block
					    	                Location locBehind = se.getLocation();
					    	                Vector dir2 = locBehind.getDirection().normalize().multiply(-1);
					    	                locBehind.add(dir2);
				    	                     if(loc.getBlock().getType().isSolid() || 
				    	                    		 locBehind.getBlock().getType().isSolid())
				    	                     {
				    	                    	 this.cancel();
				    	                    	 return;
				    	                     }
			    	            			for(Entity e : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5))
			    	            			{
			    	            				if(e instanceof LivingEntity && e != p)
			    	            				{
			    	            					AbilityUtils.damageEntity((LivingEntity)e, p, 1);
			    	            					this.cancel();
			    	            					return;
			    	            				}
			    	            			}
				    	             
				    	                loc.subtract(x,y,z);
				    	                loc2.subtract(x2,y2,z2);
				    	             
				    	                if(t >= 30){
				    	                    this.cancel();
				    	                }
				    	             
				    	                t++;
				    	                t2++;
				    	             
				    	            }
				    	         
				    	        }.runTaskTimer(VallendiaMinigame.getInstance(), 0l, 0l);
			    			   
				    	 	 	  
				    		 	  new BukkitRunnable(){

									@Override
									public void run() {
										// TODO Auto-generated method stub
										if(wandCooldown.contains(p))
										{
											wandCooldown.remove(p);
										}
									}                         
		
				    		 	  }.runTaskLater(VallendiaMinigame.getInstance(), (long) (wandCooldownAmount * 20));
				    		 	  wandCooldown.add(p);
					    		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 2, (float) 1.5);
					    		return;   
			    		   }
			    	   }
			    	   	
		    	}	
			}
	    	
	    	
	    	
	    	//ABILITIES
	    	   if(itemtype == Material.INK_SACK && item.getDurability() == 10 && item.getItemMeta().getLore() != null) // green dye
	    	   {
	    		   	String ability = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities." + ChatColor.stripColor(Utils.Colorate(item.getItemMeta().getLore().get(0).toLowerCase())));
	    		   	if(AbilityUtils.isStunned(p) && !noStunAbilities.contains(ability))
	    		   	{
	    		   		return;
	    		   	}
	    		   	if(Main.abilitymanager.getAbility(ability) == null)
	    		   	{
	    		   		return;
	    		   	}
	    		   	RegionManager regionManager = Main.worldguard.getRegionManager(p.getWorld());
	    		   	ApplicableRegionSet arset = regionManager.getApplicableRegions(p.getLocation());
	    		   	LocalPlayer localPlayer = Main.worldguard.wrapPlayer(p);
	    		   	if(!arset.allows((StateFlag) VallendiaMinigame.blockAbilities, localPlayer))
	    		   	{
	    		   		Language.sendDefaultMessage(p, "You cant use abilities here!");
	    		   		return;
	    		   	}
	    		   	if(AbilityUtils.silenced.containsKey(p))
	    		   	{
	    		   		Language.sendDefaultMessage(p, "Your abilities are silenced.");
	    		   		return;
	    		   	}
		    		if(!AbilityCooldown.isInCooldown(p.getUniqueId(), ability))
		    		{  
			    		if(Main.abilitymanager.getAbility(ability).RunAbility(p) && Main.abilitymanager.getCooldown(ability, p) > 0)
			    		{
			    			if(AbilityUtils.casting.containsKey(p))
			    			{
			    				//already checking for casting
			    				if(casting.containsKey(p))
			    				{
			    					return;
			    				}
			    	 			BukkitTask task = new BukkitRunnable() {
			    	 	            @Override
			    	 	            public void run() {
			    	 	            	if(!AbilityUtils.casting.containsKey(p))
			    	 	            	{
			    	 	            		
			    				    		AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), ability, Main.abilitymanager.getCooldown(ability, p), item);
			    				    		c.start();
			    				    		casting.remove(p);
			    				    		this.cancel();
			    	 	            	}
			    	 	            }
			    	 	        }.runTaskTimer(VallendiaMinigame.getInstance(), 0 , 20L);
			    	 	        casting.put(p, task);
			    			}else{
					    		AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), ability, Main.abilitymanager.getCooldown(ability, p), item);
					    		c.start();		
			    			}
				    		 
			    		}
		    		}else 
		    		{
		    			p.sendMessage(Utils.Colorate(" &8&lCooldown: " + AbilityCooldown.getTimeLeft(p.getUniqueId(), ability) + " secs"));
		    			return;
		    		}
		    		return;
	    	   }
	    	   
	    	   	
	    	   
	    	   
	    }
	}
	
	
	
	//stop from moving item
	@EventHandler(priority = EventPriority.LOWEST)
	public void onClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player && e.getWhoClicked().getGameMode() != GameMode.CREATIVE) {	
		    ItemStack item = e.getCurrentItem();
		    Material itemtype = null;
		    ItemStack item2 = null;
		    if(e.getClickedInventory() == null)
		    {
		    	return;
		    }
		    if(e.getClickedInventory().getItem(e.getSlot()) == null)
		    {
		    	return;
		    }else
		    {
			    item2 = e.getClickedInventory().getItem(e.getSlot());	
		    }
		    String itemname = "Air";

			if(e.getSlot() == 8 )
			{
				e.setCancelled(true);
			}
			
			if(e.getSlot() == 4 && VallendiaMinigame.getInstance().kitmanager.getKit((Player) e.getWhoClicked()).getName(false).equalsIgnoreCase("Starter"))
			{
				e.setCancelled(true);
			}
			
	    	   if(item.getType() == Material.INK_SACK && item2.getDurability() == 10 && item2.getItemMeta().getLore() != null)
	    	   {
	    		   Player p = (Player) e.getWhoClicked();
	    		   	String ability = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities." + ChatColor.stripColor(Utils.Colorate(item2.getItemMeta().getLore().get(0).toLowerCase())));
	    		   if(ability != null && AbilityCooldown.isInCooldown(p.getUniqueId(), ability))
	    		   {
	    			   e.setCancelled(true);
	    		   }
	    	   }
		    
			if (!(e.getCurrentItem() == null) && !(e.getCurrentItem().getType() == Material.AIR)) {
				itemtype = item.getType();
				itemname = item.getItemMeta().getDisplayName();
			}else {itemtype = Material.AIR;}
		    
		    
			if(e.getSlotType() == SlotType.ARMOR)
			{
				e.setCancelled(true);
			}
			
			
			//Stops moving stuff with number keys
			
			
			if (e.getAction().name().contains("HOTBAR")) {
                item = e.getView().getBottomInventory().getItem(e.getHotbarButton());
            } else {
                item = e.getCurrentItem();
            }
			
			if(itemtype != null && item != null)
			{
				itemtype = item.getType();	
			}else
			{
				return;
			}
			if(e.getClick() == ClickType.NUMBER_KEY && itemtype == Material.NETHER_STAR)
			{	
			e.setCancelled(true);	
			}
			if (itemtype == Material.NETHER_STAR && itemname != null) {
				if(itemname.equals(Utils.Colorate("&b&lKit")) || itemname.equals(Utils.Colorate("&b&lShop")))
				{
					e.setCancelled(true);
				}
			}
			
	    	   if(itemtype == Material.INK_SACK && item.getDurability() == 10 && item.getItemMeta().getLore() != null)
	    	   {
	    		   Player p = (Player) e.getWhoClicked();
	    		   	String ability = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities." + ChatColor.stripColor(Utils.Colorate(item.getItemMeta().getLore().get(0).toLowerCase())));
	    		   if(ability != null && AbilityCooldown.isInCooldown(p.getUniqueId(), ability))
	    		   {
	    			   e.setCancelled(true);
	    		   }
	    	   }
	    	   
			
			
		}
	}
	
	
	
	
	@EventHandler
	public void onSwitchHand(PlayerSwapHandItemsEvent e)
	{		
	    ItemStack item = e.getOffHandItem();
	    Material itemtype = item.getType();
	    
	 	   if(itemtype == Material.INK_SACK && item.getDurability() == 10 && item.getItemMeta().getLore() != null)
	 	   {
	 		   Player p = e.getPlayer();
	 		   	String ability = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities." + ChatColor.stripColor(Utils.Colorate(item.getItemMeta().getLore().get(0).toLowerCase())));
	 		   if(ability != null && AbilityCooldown.isInCooldown(p.getUniqueId(), ability))
	 		   {
	 			   e.setCancelled(true);
	 		   }
	 	   }
	    
	    if(itemtype != Material.NETHER_STAR || !item.getItemMeta().hasDisplayName())
	    {
	    	return;
	    }
	    String itemname = item.getItemMeta().getDisplayName();
		if (itemname.equals(Utils.Colorate("&b&lKit")) || itemname.equals(Utils.Colorate("&b&lShop"))) {
			e.setCancelled(true);
		}
		
		
		
	}
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void dropItem(PlayerDropItemEvent e)
	{
		Player p = e.getPlayer();
		if (p.getGameMode() != GameMode.CREATIVE) {
			if(PlayerDeathEvents.drops.contains(e.getItemDrop().getItemStack().getType()))
			{
			    ItemStack item = e.getItemDrop().getItemStack();
			    Material itemtype = item.getType();
			 	   if(itemtype == Material.INK_SACK && item.getDurability() == 10 && item.getItemMeta().getLore() != null)
			 	   {
			 		   	String ability = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities." + ChatColor.stripColor(Utils.Colorate(item.getItemMeta().getLore().get(0).toLowerCase())));
				 		   if(ability != null && AbilityCooldown.isInCooldown(p.getUniqueId(), ability))
				 		   {
				 			   if(item.getAmount() > 30)
				 			   {
				 				 item.setAmount(AbilityCooldown.getTimeLeft(p.getUniqueId(), ability));
				 				 e.getItemDrop().remove();
				 				 p.getInventory().setItem(p.getInventory().getHeldItemSlot(), item);
									Language.sendDefaultMessage(p, "You can't drop that!");
				 				 return;
				 			   }
				 			   if(AbilityCooldown.getTimeLeft(p.getUniqueId(), ability) > 64)
				 			   {
				 				   item.setAmount(0);
									Language.sendDefaultMessage(p, "You can't drop that!");
				 				   return;
				 			   }else
				 			   {
					 			   e.setCancelled(true);
									Language.sendDefaultMessage(p, "You can't drop that!");
					 			   return;
				 			   }
				 		   }
			 	   }

			 	   e.setCancelled(true);
					Language.sendDefaultMessage(p, "You can't drop that!");
			}
			
		}
		
	}
	
    @EventHandler
    public void onShootBow(EntityShootBowEvent e){
    	if(!(e.getEntity() instanceof Player))
    	{
    		return;
    	}
    	Player p = (Player) e.getEntity();
        RegionManager regionManager = Main.worldguard.getRegionManager(p.getWorld());
        ApplicableRegionSet set = regionManager.getApplicableRegions(p.getLocation());

        for (ProtectedRegion region : set) {

            if (region != null){

            	if(region.getId().equalsIgnoreCase("minigamespawn"))
            	{
            		e.setCancelled(true);
            		Language.sendDefaultMessage(p, "You can't use that here!");
            	}

            }

        }
    }
	
}
