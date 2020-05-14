package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class ShieldAbility implements Ability,Listener{
	int time = 30;
	short durability = 269;
	// durability - Higher = lower durability -> 336 - 269 = 67 durability^
	ArrayList<Player> abilityActive = new ArrayList<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Shield";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You Equip a fragile shield for " + time + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SHIELD);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(abilityActive.contains(p))
		{
			return false;
		}
		
		abilityActive.add(p);
		
		if(!(p.getInventory().getItemInOffHand().getType() == Material.AIR))
		{
			ItemStack item = p.getInventory().getItemInOffHand();
			p.getInventory().addItem(item);
			ItemStack air = new ItemStack(Material.AIR);
			p.getInventory().setItemInOffHand(air);
		}
		
		PlayerInventory pi = p.getInventory();
		ItemStack shield = new ItemStack(Material.SHIELD);
		ItemMeta shielddata = shield.getItemMeta();
		shielddata.setDisplayName(Utils.Colorate("&3&lShield"));
		shield.setItemMeta(shielddata);
		shield.setDurability((short) durability);
		p.getWorld().playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 1, (float) 0.8);
		pi.setItemInOffHand(shield);
		
		new BukkitRunnable()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						if(pi.getItemInOffHand().getType() == Material.SHIELD)
						{
							ItemStack air = new ItemStack(Material.AIR);
							pi.setItemInOffHand(air);
							abilityActive.remove(p);
						}else
						{
							abilityActive.remove(p);
						}
						
					}
			
				}.runTaskLater(VallendiaMinigame.getInstance(), time*20);
		
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(abilityActive.contains(p))
		{
			if(p.getInventory().getItemInOffHand().getType() == Material.SHIELD)
			{
				ItemStack air = new ItemStack(Material.AIR);
				p.getInventory().setItemInOffHand(air);
				abilityActive.remove(p);
			}
		}
		
	}
	
	@EventHandler
	public void onClick (InventoryClickEvent e)
	{
	
		if(abilityActive.contains(e.getWhoClicked()))
		{
			if(e.getSlot() == 40)
			{
				e.setCancelled(true);
			}			
		}
	}

	

	@EventHandler
	public void onSwap (PlayerSwapHandItemsEvent e)
	{
		if(abilityActive.contains(e.getPlayer()))
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDrop (PlayerDropItemEvent e)
	{
		if(abilityActive.contains(e.getPlayer()))
		{
			if(e.getItemDrop().getItemStack().getType() == Material.SHIELD)
			{
				e.setCancelled(true);
			}
		}
	}
	
}