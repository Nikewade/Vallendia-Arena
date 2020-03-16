package me.Nikewade.VallendiaMinigame.Graphics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Upgrades.UpgradeManager;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class UpgradeWeaponGui {
	VallendiaMinigame Main;
	UpgradeManager um;
	UpgradeGui upgrades;
	String meleeEnchants;
	String rangedEnchants;
	AdvInventory inv;
	
	public UpgradeWeaponGui(VallendiaMinigame Main)
	{
	    this.Main = Main;
	    this.um = Main.upgrademanager;
	    this.upgrades = new UpgradeGui(Main); //may cause problems because it was already made in upgradegui class
	}

	public void openInventory(Player p, String kit)
	{
		  inv = new AdvInventory(Utils.Colorate("&8&lWeapon Upgrade"), 27, Utils.placeholder((byte) 7, " "));
		  meleeEnchants = "Upgrades.MeleeEnchants.";
		  rangedEnchants = "Upgrades.RangedEnchants.";
		  
		  if(kit.equalsIgnoreCase("Archer") || kit.equalsIgnoreCase("Assassin"))
		  {
			  this.openArcherAndAssassinInventory(p);
		  }
		  
		  if(kit.equalsIgnoreCase("Mage"))
		  {
			  this.openMageInventory(p);
		  }
		  
		  if(kit.equalsIgnoreCase("Warrior"))
		  {
			  this.openWarriorInventory(p);
		  }
	}
	
	
	private void openArcherAndAssassinInventory(Player p)
	{
		  inv.setItem(new ItemStack(Material.DIAMOND_SWORD), Utils.Colorate("&9&lMelee"), 11, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	UpgradeWeaponGui.this.openMeleeEnchants(ep);
			    }
			});
		  
		  inv.setItem(new ItemStack(Material.BOW), Utils.Colorate("&9&lRanged"), 15, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	UpgradeWeaponGui.this.openRangedEnchants(ep);
			    }
			});
		  inv.openInventory(p);
	}
	
	
	
	
	private void openMageInventory(Player p)
	{
		  inv.setItem(new ItemStack(Material.STICK), Utils.Colorate("&9&lMelee"), 13, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	UpgradeWeaponGui.this.openMeleeEnchants(ep);
			    }
			});
		  inv.openInventory(p);
	}
	
	
	
	
	private void openWarriorInventory(Player p)
	{
		  inv.setItem(new ItemStack(Material.DIAMOND_SWORD), Utils.Colorate("&9&lMelee"), 13, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	UpgradeWeaponGui.this.openMeleeEnchants(ep);
			    }
			});
		  inv.openInventory(p);
	}
	
	
	
	public void openMeleeEnchants(Player p)
	{
		  AdvInventory invMelee = new AdvInventory(Utils.Colorate("&8&lMelee Weapon Upgrade"), 27, Utils.placeholder((byte) 7, " "));
		  invMelee.setItem(new ItemStack(Material.ENCHANTED_BOOK), Utils.Colorate("&9&lSharpness"), 11, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	upgrades.openUpgradeYesNoMenu(ep, "Weapon", "sharpness");
			    }
			}, Utils.Colorate("&8Level &9" + um.getUpgradeEnchantAmount(p, "weapon", "sharpness")));
		  
		  
		  
		  
		  invMelee.setItem(new ItemStack(Material.ENCHANTED_BOOK), Utils.Colorate("&9&lFire Aspect"), 12, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	upgrades.openUpgradeYesNoMenu(ep, "Weapon", "fireaspect");
			    }
			}, Utils.Colorate("&8Level &9" + um.getUpgradeEnchantAmount(p, "weapon", "fireaspect")));
		  
		  
		  
		  
		  invMelee.setItem(new ItemStack(Material.ENCHANTED_BOOK), Utils.Colorate("&9&lKnockback"), 14, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	upgrades.openUpgradeYesNoMenu(ep, "Weapon", "knockback");
			    }
			}, Utils.Colorate("&8Level &9" + um.getUpgradeEnchantAmount(p, "weapon", "knockback")));
		  
		  
		  
		  
		  invMelee.setItem(new ItemStack(Material.ENCHANTED_BOOK), Utils.Colorate("&9&lSmite"), 15, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	upgrades.openUpgradeYesNoMenu(ep, "Weapon", "smite");
			    }
			}, Utils.Colorate("&8Level &9" + um.getUpgradeEnchantAmount(p, "weapon", "smite")));
		  
		  invMelee.openInventory(p);

	}
	
	
	
	public void openRangedEnchants(Player p)
	{
		  AdvInventory invRanged = new AdvInventory(Utils.Colorate("&8&lRanged Weapon Upgrade"), 27, Utils.placeholder((byte) 7, " "));
		  invRanged.setItem(new ItemStack(Material.ENCHANTED_BOOK), Utils.Colorate("&9&lPower"), 11, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	upgrades.openUpgradeYesNoMenu(ep, "Weapon", "power");
			    }
			}, Utils.Colorate("&8Level &9" + um.getUpgradeEnchantAmount(p, "weapon", "power")));
		  
		  
		  
		  
		  invRanged.setItem(new ItemStack(Material.ENCHANTED_BOOK), Utils.Colorate("&9&lPunch"), 12, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	upgrades.openUpgradeYesNoMenu(ep, "Weapon", "punch");
			    }
			}, Utils.Colorate("&8Level &9" + um.getUpgradeEnchantAmount(p, "weapon", "punch")));
		  
		  
		  
		  
		  invRanged.setItem(new ItemStack(Material.ENCHANTED_BOOK), Utils.Colorate("&9&lFlame"), 14, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	upgrades.openUpgradeYesNoMenu(ep, "Weapon", "flame");
			    }
			}, Utils.Colorate("&8Level &9" + um.getUpgradeEnchantAmount(p, "weapon", "flame")));
		  
		  
		  
		  
		  invRanged.setItem(new ItemStack(Material.ENCHANTED_BOOK), Utils.Colorate("&9&lInfinity"), 15, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	upgrades.openUpgradeYesNoMenu(ep, "Weapon", "infinity");
			    }
			}, Utils.Colorate("&8Level &9" + um.getUpgradeEnchantAmount(p, "weapon", "infinity")));
		  
		  invRanged.openInventory(p);
	}
	
}
