package me.Nikewade.VallendiaMinigame.Graphics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Shop.GuiShop;
import me.Nikewade.VallendiaMinigame.Shop.ShopGuiHandler;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class ShopGui {
	VallendiaMinigame Main;
	
	public ShopGui(VallendiaMinigame Main)
	{
	    this.Main = Main;
	}

	  public void openShopMenu(Player p)
	  {
	     AdvInventory shop = new AdvInventory(Utils.Colorate("&8&lShop"), 27, Utils.placeholder((byte) 7, " "));
		  shop.setItem(new ItemStack(Material.BLAZE_POWDER), Utils.Colorate("&3&lAbilities"), 10, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
		    		Main.guihandler.openGui(p, "abilities"); 
			    	
			    }
			} ); 
		  
		  shop.setItem(new ItemStack(Material.IRON_CHESTPLATE), Utils.Colorate("&8&lUpgrades"), 13, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
		    		Main.guihandler.openGui(p, "upgrades"); 
			    }
			} ); 
		  
		  shop.setItem(new ItemStack(Material.POTION), Utils.Colorate("&2&lItems"), 16, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
					ShopGuiHandler.open(p, new GuiShop(p, 0));
			    }
			} ); 
		  
		  shop.openInventory(p);
	  }
	
}
