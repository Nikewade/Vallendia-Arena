package me.Nikewade.VallendiaMinigame.Shop;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface IInvGuiScreen {
	
	void open();
	void click(InventoryClickEvent event);
	ItemStack atPos(int x, int y);
	Player getOpener();
	
}
