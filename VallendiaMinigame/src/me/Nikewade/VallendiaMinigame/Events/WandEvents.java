package me.Nikewade.VallendiaMinigame.Events;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
import nl.martenm.servertutorialplus.api.ServerTutorialApi;

public class WandEvents implements Listener{
	VallendiaMinigame Main;
	ArrayList<Player> wandCooldown = new ArrayList<>();
	int wandRange = 30;

	public WandEvents(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
	}

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
			    	   //MAGE WAND
			    	   if(itemtype == Material.STICK && itemname.equals(Utils.Colorate("&3&lWand")))
			    	   {
			    		   if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Extend Wand"))
			    		   {
			    			   wandRange = 60;
			    		   }

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
			    	            		
    				    		        if(item.hasItemMeta())
    				    		        {
    				    		        	ItemMeta meta = item.getItemMeta();
			    	            		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Flame Imbued Wand"))
			    	            		{
	    	            					if(meta.hasEnchant(Enchantment.FIRE_ASPECT))
	    	            					{
			    	              		SphereEffect flame = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			    	            		flame.particle = Particle.FLAME;
			    	            		flame.iterations = 1;
			    	            		flame.particles = 1;
			    	            		flame.radius = 0.2;
			    	            		flame.speed = (float) 0;
			    	            		flame.visibleRange = 50;
			    	            		flame.setLocation(loc);
			    	            		flame.start();
	    	            					}
			    	            		}
    				    		        }
    				    		        
    				    		        if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Disruptive Wand"))
    				    		        {
    			    	              		SphereEffect particle = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
    			    	              		particle.particle = Particle.CRIT_MAGIC;
    			    	              		particle.iterations = 1;
    			    	              		particle.particles = 1;
    			    	              		particle.radius = 0.2;
    			    	              		particle.speed = (float) 0;
    			    	              		particle.visibleRange = 50;
    			    	              		particle.setLocation(loc);
    			    	              		particle.start();
    				    		        }
			    	            			
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
			    	            					int sharpness = 0;
			    	            					if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Disruptive Wand"))
			    	            					{
			    	            						int num = Utils.randomNumber(1, 100);
			    	            						if(num <= 15)
			    	            						{
			    	            							AbilityUtils.silenceAbilities((LivingEntity) e, 1, "Disruptive Wand");
			    	            							Language.sendAbilityUseMessage((LivingEntity) e, "Your abilities have been silenced", "Disruptive Wand");
			    	            						}
			    	            					}
			    				    		        if(item.hasItemMeta())
			    				    		        {
			    				    		        	ItemMeta meta = item.getItemMeta();
				    	            					if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Flame Imbued Wand"))
				    	            					{
				    	            						int fire = 0;
				    	            					
				    	            					if(meta.hasEnchant(Enchantment.FIRE_ASPECT))
				    	            					{
				    	            						if(Utils.canDamage(p, e))
				    	            						{
					    	            						fire = meta.getEnchantLevel(Enchantment.FIRE_ASPECT);
					    	            						e.setFireTicks(40*fire);
				    	            						}
				    	            					}
				    	            					}
			    				    		        	if(meta.hasEnchant(Enchantment.DAMAGE_ALL))
			    				    		        	{
			    				    		        		sharpness = meta.getEnchantLevel(Enchantment.DAMAGE_ALL);
			    				    		        	}
			    				    		        }
			    				    		        double damage = 2 + sharpness;
			    				    		        if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Extend Wand"))
			    				    		        {
			    				    		            double highpercent = Utils.getPercentHigherOrLower(20, false);
			    				    		            double newdamage = damage*highpercent;
			    				    		            damage = newdamage;
			    				    		        }
			    	            					AbilityUtils.damageEntityNoArmor((LivingEntity)e, p, damage);
			    	            					p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1.3F);
			    	            					this.cancel();
			    	            					return;
			    	            				}
			    	            			}
				    	             
				    	                loc.subtract(x,y,z);
				    	                loc2.subtract(x2,y2,z2);
				    	             
				    	                if(t >= wandRange){
				    	                    this.cancel();
				    	                }
				    	             
				    	                t++;
				    	                t2++;
				    	             
				    	            }
				    	         
				    	        }.runTaskTimer(VallendiaMinigame.getInstance(), 0l, 0l);
				    	        	double wandCooldownAmount = 0.5;
					    		   if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Quicken Wand"))
					    		   {
					    			   wandCooldownAmount = 0.4;
					    		   }
				    	 	 	  
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
  
	    }
	}


}