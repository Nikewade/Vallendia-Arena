package me.Nikewade.VallendiaMinigame.Shop;

import org.bukkit.event.inventory.ClickType;

@FunctionalInterface
public interface ClickEvent {
	
	public abstract void click(ClickType click);
	
}