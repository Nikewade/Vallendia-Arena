package me.Nikewade.VallendiaMinigame.Events;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class PlayerItemEvents implements Listener {
	VallendiaMinigame Main;
	ArrayList<Player> wandCooldown = new ArrayList<>();
	double wandCooldownAmount = 0.5;
	
	
	
	public PlayerItemEvents(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
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
			    		Main.guihandler.openGui(p, "shop");  
			    		p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
			    		return;
		    	   }
		    	   
		    	   
		    	   //MAGE WAND
		    	   if(itemtype == Material.STICK && itemname.equals(Utils.Colorate("&3&lWand")))
		    	   {
		    		   if(!wandCooldown.contains(p))
		    		   {
			    		 	  new BukkitRunnable(){                         
			    	              double t = 0;
			    	              Location loc = p.getLocation();
			    	            
			    	              public void run(){
			    	            	  //t effects speed of article
			    	                      t = t + 2;
			    	                      Vector direction = loc.getDirection().normalize();
			    	                      double x = direction.getX() * t;
			    	                      double y = direction.getY() * t + 1.5;
			    	                      double z = direction.getZ() * t;
			    	                      loc.add(x,y,z);
			    	            			for(Entity e : loc.getWorld().getNearbyEntities(loc, 0.6, 0.6, 0.6))
			    	            			{
			    	            				if(e instanceof LivingEntity && e != p)
			    	            				{
			    	            					AbilityUtils.damageEntity((LivingEntity)e, p, 2);
			    	            					this.cancel();
			    	            				}
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
			    	            			if(loc.getBlock().getType().isSolid())
			    	            			{
			    	                            this.cancel();
			    	            			}
			    	                      loc.subtract(x,y,z);
			    	                      if (t > 50){
			    	                          this.cancel();
			    	                  }
			    	                    
			    	              }
			    	 	 	  }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
			    	 	 	  
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
	    	
	    	
	    	
	    	
	    	//ABILITIES
	    	   if(itemtype == Material.INK_SACK && item.getDurability() == 10 && item.getItemMeta().getLore() != null) // green dye
	    	   {
	    		   	String ability = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities." + ChatColor.stripColor(Utils.Colorate(item.getItemMeta().getLore().get(0).toLowerCase())));
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
				    		AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), ability, Main.abilitymanager.getCooldown(ability, p), item);
				    		c.start();	
				    		 
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
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player && e.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
				return;
			}			
			if(e.getAction() == InventoryAction.HOTBAR_SWAP)
			{
				e.setCancelled(true);
			}
		    ItemStack item = e.getCurrentItem();
		    Material itemtype = item.getType();
		    String itemname = item.getItemMeta().getDisplayName();
		    
			if(e.getSlotType() == SlotType.ARMOR)
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
	
	
	@EventHandler
	public void dropItem(PlayerDropItemEvent e)
	{
		Player p = e.getPlayer();
		if (p.getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
		
	}
	
}
