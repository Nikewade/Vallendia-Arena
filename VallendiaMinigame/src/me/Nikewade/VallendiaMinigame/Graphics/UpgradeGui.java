package me.Nikewade.VallendiaMinigame.Graphics;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Kits.KitManager;
import me.Nikewade.VallendiaMinigame.Upgrades.UpgradeManager;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;

public class UpgradeGui {
	AdvInventory upgradeInventory;
	AdvInventory yesnoInventory;
	VallendiaMinigame Main;
	KitManager km;
	UpgradeManager um;
	UpgradeGui upgrades;
	GuiHandler guihandler;

	
	public UpgradeGui(VallendiaMinigame Main)
	{
	    this.Main = Main;
	    this.km = Main.kitmanager;
	    this.um = Main.upgrademanager;
	    this.guihandler = Main.guihandler;
	}
	
	
	
	
	
	
	
	  public void openUpgradeMenu(Player p)
	  {
		  AdvInventory upgradeInv = upgradeInv = new AdvInventory(Utils.Colorate("&8&lUpgrades"), 27, Utils.placeholder((byte) 7, " "));
		  upgradeInv.setItem(new ItemStack(Material.POTION), Utils.Colorate("&c&lHealth"), 10, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openUpgradeYesNoMenu(ep, "Health", null);
			    }
			}, Utils.Colorate("&8Level &c" + Main.upgrademanager.getUpgradeAmount(p, "Health")));
		  
		  
		  
		  
		  upgradeInv.setItem(new ItemStack(Material.LEATHER_BOOTS), Utils.Colorate("&f&lSpeed"), 12, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openUpgradeYesNoMenu(ep, "Speed", null);

			    }
			}, Utils.Colorate("&8Level &f" + Main.upgrademanager.getUpgradeAmount(p, "Speed")));
		  
		  
		  
		  
		  upgradeInv.setItem(new ItemStack(Material.IRON_CHESTPLATE), Utils.Colorate("&7&lArmor"), 14, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	Main.guihandler.openGui(ep, "armor");
			    }
			}, Utils.Colorate("&8Level &7" + Main.upgrademanager.getUpgradeAmount(p, "Armor")));
		  
		  
		  
		  
		  upgradeInv.setItem(new ItemStack(Material.DIAMOND_SWORD), Utils.Colorate("&9&lWeapon"), 16, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	Main.guihandler.openGui(ep, "weapon");
			    }
			}, Utils.Colorate("&8Level &9" + Main.upgrademanager.getUpgradeAmount(p, "Weapon")));
		  
		  
		  
		  upgradeInv.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 18, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	Main.guihandler.openGui(ep, "shop");;
			    }
			}); 
		  
		  upgradeInv.openInventory(p);
	  }
	  
	  
	  
	  
	  
	  
	  
		public void openUpgradeYesNoMenu(Player p, String upgrade, String enchant)
		{
			AdvInventory upgradeInvYesNo = new AdvInventory(Utils.Colorate("&8&lAre you sure?"), 27, Utils.placeholder((byte) 7, " "));
			String itemTitle = "";
			
			if(enchant == null)
			{
				itemTitle = Utils.Colorate("&2&lUpgrade " + upgrade);
			}else {itemTitle = Utils.Colorate("&2&lUpgrade " + upgrade + " (" + enchant + ")");}
			
			String description1 =  Utils.Colorate("&aPrice: &2" + um.getPrice(p, upgrade, enchant) + Utils.Colorate(" points"));
			String description2 = Utils.Colorate("&aKit Discount: &2" + um.getDiscount(p, upgrade, enchant) + "%");
			upgradeInvYesNo.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 13), itemTitle, 11, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	um.buyUpgrade(ep, upgrade, enchant, 1);
			    	if(Main.getConfig().getBoolean("Options.Auto-Close-Upgrade-Menu"))
			    	{
				    	ep.closeInventory();	
			    	}
			    }
			}, description1 , description2);
			
			
			upgradeInvYesNo.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14),  Utils.Colorate("&4&lCancel"), 15, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openUpgradeMenu(ep);
			    }
			});
			
			upgradeInvYesNo.openInventory(p);
		}
	  
}
