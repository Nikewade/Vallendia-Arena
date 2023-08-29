package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
<<<<<<< HEAD
=======
import org.bukkit.event.entity.PlayerDeathEvent;
>>>>>>> second-repo/master
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SmokeEffect;
import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class SunderArmorAbility implements Ability, Listener{
	//made by Emma
	int duration = 10;
<<<<<<< HEAD
	public static List<Player> sundered = new ArrayList<>();
	HashMap<Player, ItemStack> storePant = new HashMap<>();
	HashMap<Player, ItemStack> storeBoot = new HashMap<>();
=======
	int range = 5;
	public static List<Player> sundered = new ArrayList<>();
	HashMap<Player, ItemStack> storePant = new HashMap<>();
>>>>>>> second-repo/master

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Sunder Armor";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		return Arrays.asList("You sunder your targets armor for " + duration + "",
				" seconds.");
=======
		return Arrays.asList("You sunder your targets leg armor for " + duration,
				"seconds.");
>>>>>>> second-repo/master
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.IRON_LEGGINGS);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
<<<<<<< HEAD
		if(!(AbilityUtils.getTarget(p, 5) instanceof Player))
		{
			Language.sendAbilityUseMessage(p, "You can only sunder players!", "Sunder Armor");
			return false;
		}
		
		Player target = (Player) AbilityUtils.getTarget(p, 5);
		
=======
		
		Player target = (Player) AbilityUtils.getTarget(p, range);
		
		if(target == null)
		{
			return false;
		}
		
>>>>>>> second-repo/master
		if(sundered.contains(target))
		{
			Language.sendAbilityUseMessage(p, "This Player has already been sundered!", "Sunder Armor");
		}
		
		if(!(target.getInventory().getLeggings().getType()==Material.AIR))
		{
			
			ItemStack pants = new ItemStack(target.getInventory().getLeggings());
			storePant.put(target, pants);
			target.getInventory().setLeggings(new ItemStack(Material.AIR));
			Language.sendAbilityUseMessage(p, "You successfully sunder your target", "Sunder Armor");
			Language.sendAbilityUseMessage(target, "Your armor has been sundered!", "Sunder Armor");
			sundered.add(target);
			
			target.getWorld().playSound(target.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, (float) 1.3);
			
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);

			Location loc = target.getLocation();
			loc.setY(loc.getY()+1);

			se.particle = Particle.BLOCK_DUST;
			se.material = Material.IRON_BLOCK;
			se.radius = 1;
			se.particleCount = 1;
			se.iterations = 1;
			se.yOffset= -0.8;
			
			se.setLocation(loc);
			
			se.start();
			
<<<<<<< HEAD
			BukkitTask timer = new BukkitRunnable() 
=======
				new BukkitRunnable() 
>>>>>>> second-repo/master
			{

				@Override
				public void run() {
					
<<<<<<< HEAD
					target.getInventory().setLeggings(pants);
					Language.sendAbilityUseMessage(target, "You are no longer Sundered", "Sunder Armor");
					sundered.remove(target);
					
=======
					if(sundered.contains(target))
					{
					
					target.getInventory().setLeggings(pants);
					Language.sendAbilityUseMessage(target, "You are no longer Sundered", "Sunder Armor");
					sundered.remove(target);
				}
>>>>>>> second-repo/master
				}
				
			}.runTaskLater(VallendiaMinigame.getInstance(), duration*20);

			
		}
<<<<<<< HEAD
		if(!(target.getInventory().getBoots().getType()==Material.AIR))
		{
			
			ItemStack boots = new ItemStack(target.getInventory().getBoots());
			storeBoot.put(target, boots);
			target.getInventory().setBoots(new ItemStack(Material.AIR));

			
			BukkitTask timer = new BukkitRunnable() 
			{

				@Override
				public void run() {
					
					target.getInventory().setBoots(boots);
					
				}
				
			}.runTaskLater(VallendiaMinigame.getInstance(), duration*20);

			
		}
			

		
	
		
		return false;
=======
		
		return true;
>>>>>>> second-repo/master
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(sundered.contains(p))
		{
<<<<<<< HEAD
		
=======
>>>>>>> second-repo/master
	
			if(storePant.containsKey(p))
			{
				p.getInventory().setLeggings(storePant.get(p));
			}
<<<<<<< HEAD
			if(storeBoot.containsKey(p))
			{
				p.getInventory().setBoots(storeBoot.get(p));
			}
=======
>>>>>>> second-repo/master
			sundered.remove(p);
		}
		
		
	}
	
	@EventHandler
<<<<<<< HEAD
=======
	public void onDeath (PlayerDeathEvent e)
	{
		Player p = e.getEntity();
		
		if(sundered.contains(p))
		{
	
			if(storePant.containsKey(p))
			{
				storePant.remove(p);
			}
			sundered.remove(p);
		}
	}
	
	@EventHandler
>>>>>>> second-repo/master
	public void onInvClick (InventoryClickEvent e)
	{
		if(sundered.contains(e.getWhoClicked()))
				{
					e.setCancelled(true);
				}

	}
	

	
	@EventHandler
	public void onSwitchHand(PlayerSwapHandItemsEvent e)
	{
		if(sundered.contains(e.getPlayer()))
		{
			e.setCancelled(true);
		}
			
	}


	@EventHandler
	public void onPickup(EntityPickupItemEvent e) 
	{
		if(e.getEntity()instanceof Player)
		{
		if(sundered.contains(e.getEntity()))
		{
		e.setCancelled(true);	
		}
		}
	}

}