package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class MountAbility implements Ability, Listener{
	private static HashMap<Player, Horse> enabled = new HashMap<>();
	private static HashMap<Player, BukkitTask> tasks= new HashMap<>();
	int time = 60;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Mount";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You mount your loyal steed for " + time + " seconds.",
				"Your steed will increase in power based on your level.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SADDLE);
	}  

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		if(!enabled.containsKey(p))
		{
			
			int health = 20;
			double jump = 0.5;
			double speed = 0.20;
			ItemStack armor = new ItemStack(Material.IRON_BARDING);
			int enchantAmount = 0;
			double maxJump = 1.5;
			double maxSpeed = 0.50;
			
			int level = VallendiaMinigame.getInstance().levelmanager.getLevel(p);
			health = health + (level * 2);
			jump = jump + (0.03 * level);
			speed = speed + (0.02 * level);
			enchantAmount = enchantAmount + level;
			
			if(jump > maxJump)
			{
				jump = maxJump;
			}
			
			if(speed > maxSpeed)
			{
				speed = maxSpeed;
			}
			
			if(level >= 5)
			{
				armor = new ItemStack(Material.GOLD_BARDING);
			}
			
			if(level >= 10)
			{
				armor = new ItemStack(Material.DIAMOND_BARDING);
			}
			
			
			Horse horse = (Horse) p.getWorld().spawnEntity(p.getLocation(), EntityType.HORSE); // Spawns the horse
            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
            horse.getInventory().setArmor(armor);// Gives horse saddle
            horse.setTamed(true); // Sets horse to tamed
            horse.setOwner(p);
            horse.setStyle(Style.BLACK_DOTS);
            horse.setCustomName(Utils.Colorate("&4&lMount"));
            horse.setColor(org.bukkit.entity.Horse.Color.BROWN);
            horse.setMaxHealth(health);
            horse.setHealth(health);
            horse.setTamed(true);
            horse.setAdult();
            horse.setJumpStrength(jump);
            AttributeInstance attributes = horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
            attributes.setBaseValue(speed);
            horse.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999, 2), true);
    		horse.setPassenger(p);
    		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_HORSE_ANGRY, 2, (float) 1);
    		
    		enabled.put(p, horse);
    		
    		BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
        			if(enabled.containsKey(p))
        			{
        				p.setAllowFlight(true);
        				p.leaveVehicle();
        				enabled.remove(p);
        				p.setAllowFlight(false);
        			}
                }
            }.runTaskLater(VallendiaMinigame.getInstance(),time*20L);  
    		
    		tasks.put(p, task);
    		return true;
		}
		
		Language.sendAbilityUseMessage(p, "You are already mounted, sneak to unmount.", "Mount");
		return false;
	}
	
	
	public static void onReload()
	{
		for(Horse h : enabled.values())
		{
			h.remove();
		}
	}
	
        	@EventHandler
        	public void unMount(VehicleExitEvent e)
        	{
        		if(!enabled.containsKey(e.getExited()))
        		{
        			return;
        		}	
        		enabled.get(e.getExited()).setGravity(false);
        		enabled.get(e.getExited()).remove();
        		enabled.remove(e.getExited());
        		if(tasks.containsKey(e.getExited()))
        		{
            		tasks.get(e.getExited()).cancel();
            		tasks.remove(e.getExited());	
        		}
        	}
        	
        	
        	
        	@EventHandler
        	public void openHorseInventory(InventoryOpenEvent e)
        	{
        		if(e.getInventory().getTitle().equalsIgnoreCase(Utils.Colorate("&4&lMount")))
        		{
        			e.setCancelled(true);
        		}
        		
        	}
        	
        	@EventHandler
        	public void horseDath(EntityDeathEvent e)
        	{
<<<<<<< HEAD
        		if(e.getEntity() instanceof Horse && e.getEntity().getCustomName().equalsIgnoreCase(Utils.Colorate("&4&lMount")))
        		{
        			Entity en = e.getEntity();
        			e.getDrops().clear();
            		if(!enabled.containsKey(e.getEntity()))
            		{
            			return;
            		}	
            		enabled.remove(e.getEntity());
            		if(tasks.containsKey(en))
            		{
                		tasks.get(en).cancel();
                		tasks.remove(en);	
            		}
=======
        		if(e.getEntity() instanceof Horse)
        		{
        			Horse horse = (Horse) e.getEntity();
        			if(horse.getCustomName() == null)
        			{
        				return;
        			}
        			if(e.getEntity().getCustomName().equalsIgnoreCase(Utils.Colorate("&4&lMount")))
        					{
            			Entity en = e.getEntity();
            			e.getDrops().clear();
                		if(!enabled.containsKey(e.getEntity()))
                		{
                			return;
                		}	
                		enabled.remove(e.getEntity());
                		if(tasks.containsKey(en))
                		{
                    		tasks.get(en).cancel();
                    		tasks.remove(en);	
                		}
        					}
>>>>>>> second-repo/master
        		}
        	}
        	
        	@EventHandler
        	public void playerLeave(PlayerQuitEvent e)
        	{
        		if(enabled.containsKey(e.getPlayer()))
        		{
        			e.getPlayer().leaveVehicle();
        		}
        	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
