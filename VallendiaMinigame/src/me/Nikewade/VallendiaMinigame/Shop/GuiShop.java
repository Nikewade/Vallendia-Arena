package me.Nikewade.VallendiaMinigame.Shop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class GuiShop implements IInvGuiScreen {
	
	private final int size = 3;
	private InvGuiItem[] items;
	private Player player;
	private int page;
	
	public GuiShop(Player ply, int page) {
		this.player = ply;
		this.page = page;
	}
	
	public void open() {
		Inventory inv = Bukkit.createInventory(this.player, 9 * size, ShopUtils.invName());
		ShopItem[] item = ShopHandler.getShopItems();
		ItemStack[] toAdd = new ItemStack[item.length];
		items = new InvGuiItem[this.size * 9];

		int start = this.page * ((this.size - 1) * 9);
		int listSize = Math.min(9 * (this.size - 1), item.length - start);
		for(int num = 0; num < listSize; num ++) {
			ShopItem currentShopItem = item[start + num];
			toAdd[start + num] = currentShopItem.getStack().clone();
			
			List<String> lore = new ArrayList<String>();
			lore.add("&aLeft-click Buy: " + ShopUtils.formatPrice(currentShopItem.getBuyPrice()));
			ShopUtils.loreItemStack(toAdd[start + num], lore);
	 		if(currentShopItem.getStack().getType() == Material.RAW_BEEF || currentShopItem.getStack().getType() == Material.PORK || 
	 				currentShopItem.getStack().getType() == Material.RAW_CHICKEN || currentShopItem.getStack().getType() == Material.BREAD || 
	 				currentShopItem.getStack().getType() == Material.COOKED_FISH || currentShopItem.getStack().getType() == Material.RABBIT)
			{
	 			int healPercent = 0;
	 			String itemName = "";
	 			
		        switch (currentShopItem.getStack().getType()) {
	            case RAW_BEEF:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.steak");
	            itemName = "Raw Beef";
	                     break;
	            case PORK:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.pork");
	            itemName = "Raw Porkchop";
	                     break;
	            case RAW_CHICKEN:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.chicken");
	            itemName = "Raw Chicken";
	                     break;
	            case BREAD:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.bread");
	            itemName = "Bread";
	                     break;
	            case COOKED_FISH:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.fish");
	            itemName = "Fish";
	                     break;
	            case RABBIT:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.rabbit");
	            itemName = "Raw Rabbit";
                 break;
				default:
					healPercent = 0;
					break;
	        }
		        
		        ShopUtils.nameItemStack(toAdd[start + num], Utils.Colorate("&f" + itemName + " (Heals " + healPercent + "%)"));
			}
			
			items[num] = new InvGuiItem(toAdd[start + num], (type) -> {
				ItemStack toBuy = currentShopItem.getStack().clone();
				if(type.isLeftClick()) {
					ShopHandler.buyItem(this.player, toBuy, currentShopItem.getBuyPrice());
				} else if(type.isRightClick()) {
					ShopHandler.sellItem(this.player, toBuy, currentShopItem.getSellPrice());
				}
			});
		}
		
		for(int i = 0; i < items.length; i ++) {
			InvGuiItem igi = items[i];
			if(igi != null) {
				//inv.addItem(igi.getStack());
				inv.setItem(i, igi.getStack());
			}
		}
		ItemStack backStack = ShopUtils.fromString(VallendiaMinigame.getInstance().getConfig().getString("guiBackButton"));
		ItemStack nextStack = ShopUtils.fromString(VallendiaMinigame.getInstance().getConfig().getString("guiNextButton"));
		
		ShopUtils.nameItemStack(backStack, VallendiaMinigame.getInstance().getConfig().getString("langBackPage"));
		ShopUtils.nameItemStack(nextStack, VallendiaMinigame.getInstance().getConfig().getString("langNextPage"));
		
		InvGuiItem back = new InvGuiItem(backStack, (type) -> {
			ShopGuiHandler.close(this.player);
			ShopGuiHandler.open(this.player, new GuiShop(this.player, this.page - 1));
		});
		
		InvGuiItem forw = new InvGuiItem(nextStack, (type) -> {
			ShopGuiHandler.close(this.player);
			ShopGuiHandler.open(this.player, new GuiShop(this.player, this.page + 1));
		});
		
		items[items.length - 3] = back;
		items[items.length - 2] = forw;
		
		//if(this.page > 0) inv.setItem(45, backStack);
		if(this.page < this.numberOfPages()) inv.setItem(17, nextStack);
		
		player.openInventory(inv);
	}
	
	public int numberOfPages() {
		return (int) Math.ceil(ShopHandler.getShopItems().length / ((this.size - 1) * 9));
	}

	public void click(InventoryClickEvent event) {
		event.setCancelled(true);
		ItemStack stahck = event.getCurrentItem();
		for(InvGuiItem item : items) {
			if(item != null && item.getStack().equals(stahck)) {
				item.click(event.getClick());
				return;
			}
		}
	}

	public InvGuiItem[] getItems() {
		return this.items;
	}
	
	public ItemStack atPos(int x, int y) {
		return items[x + 9 * y].getStack();
	}

	public Player getOpener() {
		return player;
	}
	
}