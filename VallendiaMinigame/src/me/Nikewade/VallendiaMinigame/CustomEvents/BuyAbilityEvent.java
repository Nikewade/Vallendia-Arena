package me.Nikewade.VallendiaMinigame.CustomEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BuyAbilityEvent extends Event{
	private Player player;
	private String abilityName;
	private static final HandlerList handlers = new HandlerList();

	
	public BuyAbilityEvent(Player player, String abilityName)
	{
		this.player = player;
		this.abilityName = abilityName;
	}
	
	
	public Player getPlayer(){
		return this.player;
	}
	
	
	public String getAbility(){
		return this.abilityName;
	}
	
	
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	

}
