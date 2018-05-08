package me.Nikewade.VallendiaMinigame.Graphics;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Kits.Warrior;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class GUIHandler {
	VallendiaMinigame Main;
	Warrior warrior;
	Sound kitSound;
	int price;
	int discount;
	
	 public GUIHandler(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	    this.warrior = new Warrior(Main);
	  }
	 

	 
	 //KITS
	  public void openKitMenu(Player p)
	  {
		  AdvInventory inv = new AdvInventory(Utils.Colorate("&8&lPick your Kit"), 27, Utils.placeholder((byte) 7, " "));
		  inv.setItem(new ItemStack(Material.IRON_SWORD), Utils.Colorate("&4&lWarrior"), 10, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			        	GUIHandler.this.yesNoMenu((Player) e.getWhoClicked(), warrior);
			        	kitSound = Sound.ENTITY_ENDERDRAGON_GROWL;
			    }
			}, warrior.getDescription());
		  
		  inv.setItem(new ItemStack(Material.BOW), Utils.Colorate("&2&lArcher"), 12, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			        	GUIHandler.this.yesNoMenu((Player) e.getWhoClicked(), Main.archer);
			        	kitSound = Sound.ENTITY_ARROW_HIT;
			    }
			}, Main.archer.getDescription());
		  
		  ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
		  LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
		  helmmeta.setColor(Color.BLACK);
		  inv.setItem(new ItemStack(helm), Utils.Colorate("&8&lAssassin"), 14, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			        	GUIHandler.this.yesNoMenu((Player) e.getWhoClicked(), Main.assassin);
			        	kitSound = Sound.ENTITY_ENDERDRAGON_FLAP;
			    }
			}, Main.assassin.getDescription());
		  
		  inv.setItem(new ItemStack(Material.EYE_OF_ENDER), Utils.Colorate("&3&lMage"), 16, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			        	GUIHandler.this.yesNoMenu((Player) e.getWhoClicked(), Main.mage);
			        	kitSound = Sound.BLOCK_END_PORTAL_SPAWN;
			    }
			}, Main.mage.getDescription());
		  inv.openInventory(p);
	  }
	  
	  
	  
	  
	  
	  //SHOP
	  public void openShopMenu(Player p)
	  {
		  AdvInventory inv = new AdvInventory(Utils.Colorate("&8&lShop"), 27, Utils.placeholder((byte) 7, " "));
		  inv.setItem(new ItemStack(Material.BLAZE_POWDER), Utils.Colorate("&3&lAbilities"), 10, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	
			    }
			} ); 
		  
		  inv.setItem(new ItemStack(Material.IRON_CHESTPLATE), Utils.Colorate("&8&lUpgrades"), 13, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	GUIHandler.this.openUpgradeMenu(p);
			    }
			} ); 
		  
		  inv.setItem(new ItemStack(Material.POTION), Utils.Colorate("&2&lConsumables"), 16, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	
			    }
			} ); 
		  
		  inv.openInventory(p);
	  }
	  
	  
	  
	  
	  
	  
	  //UPGRADES
	  public void openUpgradeMenu(Player p)
	  {
		  AdvInventory inv = new AdvInventory(Utils.Colorate("&8&lUpgrades"), 27, Utils.placeholder((byte) 7, " "));
		  inv.setItem(new ItemStack(Material.POTION), Utils.Colorate("&c&lHealth"), 10, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	discount = Main.kitmanager.getKit(p).getupgradeDiscount("Health");
			    	GUIHandler.this.openUpgradeYesNoMenu((Player) e.getWhoClicked(), "Health");
			    }
			}, Utils.Colorate("&8Current Upgrades: &c" + Main.upgrademanager.getUpgradeAmount(p, "Health")));
		  
		  
		  
		  
		  inv.setItem(new ItemStack(Material.LEATHER_BOOTS), Utils.Colorate("&f&lSpeed"), 12, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	discount = Main.kitmanager.getKit(p).getupgradeDiscount("Speed");
			    	GUIHandler.this.openUpgradeYesNoMenu((Player) e.getWhoClicked(), "Speed");
			    }
			}, Utils.Colorate("&8Current Upgrades: &f" + Main.upgrademanager.getUpgradeAmount(p, "Speed")));
		  
		  
		  
		  
		  inv.setItem(new ItemStack(Material.IRON_CHESTPLATE), Utils.Colorate("&7&lArmor"), 14, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	discount = Main.kitmanager.getKit(p).getupgradeDiscount("Armor");
			    	GUIHandler.this.openUpgradeYesNoMenu((Player) e.getWhoClicked(), "Armor");
			    }
			}, Utils.Colorate("&8Current Upgrades: &7" + Main.upgrademanager.getUpgradeAmount(p, "Armor")));
		  
		  
		  
		  
		  inv.setItem(new ItemStack(Material.DIAMOND_SWORD), Utils.Colorate("&9&lWeapon"), 16, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	discount = Main.kitmanager.getKit(p).getupgradeDiscount("Weapon");
			    	GUIHandler.this.openUpgradeYesNoMenu((Player) e.getWhoClicked(), "Weapon");
			    }
			}, Utils.Colorate("&8Current Upgrades: &9" + Main.upgrademanager.getUpgradeAmount(p, "Weapon")));
		  
		  inv.openInventory(p);
	  }
	  
	  
	  
	  
	  
	  
	  
	  //UPGRADE YES NO MENU
		public void openUpgradeYesNoMenu(Player p, String upgrade)
		{
			AdvInventory inv = new AdvInventory(Utils.Colorate("&8&lAre you sure?"), 27, Utils.placeholder((byte) 7, " "));
			inv.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 13), Utils.Colorate("&2&lUpgrade " + upgrade), 11, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    }
			}, Utils.Colorate("&aPrice: &2" + price + Utils.Colorate(" points")) , Utils.Colorate("&aKit Discount: " + discount + "%"));
			
			
			inv.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14),  Utils.Colorate("&4&lCancel"), 15, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	GUIHandler.this.openUpgradeMenu(ep);
			    }
			});
			
			inv.openInventory(p);
		}
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  //YES NO MENU
		public void yesNoMenu(Player p, Kit kit)
		{
			AdvInventory inv = new AdvInventory(Utils.Colorate("&8&lAre you sure?"), 27, Utils.placeholder((byte) 7, " "));
			inv.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 13), Utils.Colorate("&2&lYes"), 11, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			        Main.kitmanager.giveKit(ep, kit.getName(false));
			        ep.closeInventory();
			        ep.sendTitle(kit.getName(true), "", 20, 40, 40);
			        ep.playSound(p.getLocation(), kitSound, 2, (float) 0.5);
			    }
			}, Utils.Colorate("&aConfirm kit " + kit.getName(true)));
			
			
			inv.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lNo"), 15, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	GUIHandler.this.openKitMenu(ep);
			    }
			}, Utils.Colorate("&cCancel"));
			
			inv.openInventory(p);
		}
	
}
