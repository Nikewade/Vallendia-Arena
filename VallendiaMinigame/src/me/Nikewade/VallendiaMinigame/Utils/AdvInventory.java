package me.Nikewade.VallendiaMinigame.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.AbilityType;
import me.Nikewade.VallendiaMinigame.Interface.Ability;

public class AdvInventory {
	private static HashMap<UUID, AdvInventory> inventories = new HashMap<UUID, AdvInventory>();
	private static HashMap<UUID, UUID> openInventories = new HashMap<>();
	private HashMap<Integer, ClickRunnable> runs = new HashMap<Integer, ClickRunnable>();
	
	private Inventory inv;
	private UUID uuid;
	
	public AdvInventory(String name, int size) {
		new AdvInventory(name, size, null);
	}
		
	public AdvInventory(String name, int size, ItemStack placeholder) {
		if (size == 0) {
			return;
		}
		uuid = UUID.randomUUID();
		inv = Bukkit.getServer().createInventory(null, size, ChatColor.BLUE + name);
		inventories.put(uuid, this);
		if (placeholder != null) {			
			for (int i = 0; i < size; i++) {
				inv.setItem(i, placeholder);
			}
		}
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public Inventory getSourceInventory() {
		return inv;
	}
	
	public int getSize() {
		return inv.getSize();
	}
	
	public void setItem(ItemStack itemstack, Integer slot, ClickRunnable executeOnClick) {
		setItem(itemstack, null, slot, executeOnClick);
	}
	
	public void setItem(ItemStack itemstack, String displayname, Integer slot, ClickRunnable executeOnClick, String... description) {
		ItemStack is = itemstack;
		ItemMeta im = is.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,
				ItemFlag.HIDE_DESTROYS,
				ItemFlag.HIDE_ENCHANTS,
				ItemFlag.HIDE_PLACED_ON,
				ItemFlag.HIDE_POTION_EFFECTS,
				ItemFlag.HIDE_UNBREAKABLE);
		if (displayname != null) {
			im.setDisplayName(ChatColor.BLUE + displayname);
		}
		if (description != null) {
			List<String> lore = new ArrayList<String>();
			for (String s : description) {
				lore.add(ChatColor.GRAY + s);
			}
			im.setLore(lore);
		}
		is.setItemMeta(im);
		inv.setItem(slot, is);
		runs.put(slot, executeOnClick);
	}
	
	public void setItemAbility(ItemStack itemstack, String displayname, Integer slot, ClickRunnable executeOnClick, Ability ability, String kit) {
		ItemStack is = itemstack;
		ItemMeta im = is.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,
				ItemFlag.HIDE_DESTROYS,
				ItemFlag.HIDE_ENCHANTS,
				ItemFlag.HIDE_PLACED_ON,
				ItemFlag.HIDE_POTION_EFFECTS,
				ItemFlag.HIDE_UNBREAKABLE);
		if (displayname != null) {
			im.setDisplayName(ChatColor.BLUE + displayname);
		}
		
		ArrayList<String> lore = new ArrayList<String>();
		for (String s : ability.getDescription()) {
			lore.add(ChatColor.GRAY + s);
		}
		if(VallendiaMinigame.getInstance().getConfig().getInt( "Abilities." + ability.getName() + "." + kit.toLowerCase() + ".price") > 0)
		{
			lore.add(ChatColor.DARK_GRAY + "Essence: " + VallendiaMinigame.getInstance().getConfig().getInt( "Abilities." + ability.getName() + "." + kit.toLowerCase() + ".price"));	
		}
		if(!(ability.getAbilityType() == AbilityType.PASSIVE) && 
				VallendiaMinigame.getInstance().getConfig().getInt( "Abilities." + ability.getName() + "." + kit.toLowerCase() + ".cooldown") > 0)
		{
			lore.add(ChatColor.DARK_GRAY + "Cooldown: " + VallendiaMinigame.getInstance().getConfig().getInt( "Abilities." + ability.getName() + "." + kit.toLowerCase() + ".cooldown") + " seconds");	
		}
		im.setLore(lore);
		
		is.setItemMeta(im);
		inv.setItem(slot, is);
		runs.put(slot, executeOnClick);
	}

	
	public void removeItem(int slot) {
		inv.setItem(slot, new ItemStack(Material.AIR));
	}

	public void setItem(ItemStack itemstack, Integer slot) {
		inv.setItem(slot, itemstack);
	}
	
	public static Listener getListener() {
		return new Listener() {
			@EventHandler
			public void onClick(InventoryClickEvent e) {
				if (e.getWhoClicked() instanceof Player) {
					if (e.getCurrentItem() == null) {
						return;
					}	
					if(e.getClickedInventory() == e.getWhoClicked().getInventory())
					{
						return;
					}
					Player p = (Player) e.getWhoClicked();
					UUID playerUUID = p.getUniqueId();
					
					UUID inventoryUUID = openInventories.get(playerUUID);
					if(inventoryUUID != null)
					{
						e.setCancelled(true);
						AdvInventory gui = inventories.get(inventoryUUID);
						if (gui.runs.get(e.getSlot()) == null) {
							p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
						} else {
							p.playSound(p.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 1, 1);
							if (gui.runs.get(e.getSlot()) != null) {
								gui.runs.get(e.getSlot()).run(e);
							}
						}
					}
				}
			}
			
			
			
			@EventHandler
			public void onClose(InventoryCloseEvent e) {
				if (e.getPlayer() instanceof Player) {
					Player p = (Player) e.getPlayer();
					UUID playerUUID = p.getUniqueId();
					
					AdvInventory.openInventories.remove(playerUUID);
				}
			}
			
			
			@EventHandler
			public void onQuit(PlayerQuitEvent e) {
				if (e.getPlayer() instanceof Player) {
					Player p = (Player) e.getPlayer();
					UUID playerUUID = p.getUniqueId();
					
					AdvInventory.openInventories.remove(playerUUID);
				}
			}
		};
	}
	
	
    public void delete(){
        for (Player p : Bukkit.getOnlinePlayers()){
            UUID u = openInventories.get(p.getUniqueId());
            if (u.equals(getUuid())){
                p.closeInventory();
            }
        }
        inventories.remove(getUuid());
    }
			
	
	
	public void openInventory(Player p) {
		p.openInventory(getSourceInventory());
		openInventories.put(p.getUniqueId(), getUuid());
	}
	
	
	public static abstract class ClickRunnable {
		public abstract void run(InventoryClickEvent e);
	}
	
	public static abstract class CloseRunnable {
		public abstract void run(InventoryCloseEvent e);
	}
}