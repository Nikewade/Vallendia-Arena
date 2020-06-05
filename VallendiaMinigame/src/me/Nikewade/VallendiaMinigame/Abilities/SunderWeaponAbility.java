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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class SunderWeaponAbility implements Ability, Listener{
	//made by Emma
	public static List<Player> sundered = new ArrayList<>();
	HashMap<Player, Integer> storeSlot = new HashMap<>();
	HashMap<Player, ItemStack> storeWeapon = new HashMap<>();
	int duration = 10;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Sunder Weapon";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Sunder your enemies weapon for", + duration + "seconds");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack (Material.STONE_SWORD);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(!(AbilityUtils.getTarget(p, 5) instanceof Player))
		{
			return false;
		}
		
		Player target = (Player) AbilityUtils.getTarget(p, 5);
					
		if(target.getInventory().getItemInMainHand().getType() == Material.DIAMOND_SWORD ||
				target.getInventory().getItemInMainHand().getType() == Material.GOLD_SWORD||
				target.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD||
				target.getInventory().getItemInMainHand().getType() == Material.STONE_SWORD||
				target.getInventory().getItemInMainHand().getType() == Material.WOOD_SWORD||
				target.getInventory().getItemInMainHand().getType() == Material.STICK||
				target.getInventory().getItemInMainHand().getType() == Material.BOW)
		{
			
			ItemStack weapon = new ItemStack(target.getInventory().getItemInMainHand());
			target.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
			int slot = target.getInventory().getHeldItemSlot();
			sundered.add(target);
			storeSlot.put(target, slot);
			storeWeapon.put(target, weapon);
			Language.sendAbilityUseMessage(p, "You sunder your targets weapon.", "Sunder Weapon");
			
			target.getWorld().playSound(target.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, (float) 1.5);
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);

			Location loc = target.getLocation();
			loc.setY(loc.getY()+1);

			se.particle = Particle.BLOCK_DUST;
			se.material = Material.IRON_BLOCK;
			se.radius = 1;
			se.particles = 1;
			se.iterations = 1;
			
			se.setLocation(loc);
			
			se.start();
			
			BukkitTask timer2 = new BukkitRunnable()
			
					{

						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(sundered.contains(target))
							{
								
								target.getInventory().setItem(slot, weapon);
								sundered.remove(target);
								
							}
							
							
						}
				
					}.runTaskLater(VallendiaMinigame.getInstance(), duration*20);
			

		
	}else
	{
		Language.sendAbilityUseMessage(p, "You can only sunder enemies holding a weapon!", "Sunder Weapon");
		return false;
	}

		return true;
		
		
	}
	
	@EventHandler
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

	@Override
	public void DisableAbility(Player p) {

		if(sundered.contains(p))
		{
			p.getInventory().setItem(storeSlot.get(p), storeWeapon.get(p));
			sundered.remove(p);
			storeWeapon.remove(p);
			storeSlot.remove(p);
		}

		
	}

	
	
	
	
	
	
	
}