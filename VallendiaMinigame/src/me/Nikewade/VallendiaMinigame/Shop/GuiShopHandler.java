package me.Nikewade.VallendiaMinigame.Shop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GuiShopHandler implements Listener {
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		if(e.getInventory().getName().equals(ShopUtils.invName())) {
			GuiShop shop = ShopGuiHandler.get((Player) e.getWhoClicked());
			shop.click(e);
		}
	}
	
	@EventHandler
	public void close(InventoryCloseEvent e) {
		if(e.getInventory().getName().equals(ShopUtils.invName())) {
			Player p = (Player) e.getPlayer();
			if(ShopGuiHandler.isOpen(p)) {
				ShopGuiHandler.close(p);
			}
		}
	}
	
}