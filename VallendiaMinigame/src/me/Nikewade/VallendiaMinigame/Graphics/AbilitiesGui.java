package me.Nikewade.VallendiaMinigame.Graphics;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.AbilityType;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class AbilitiesGui {
	VallendiaMinigame Main;
	int warrioroffx = -1;
	int archeroffx = -1;
	int assassinoffx = -1;
	int mageoffx = -1;
	int warriordefx = -1;
	int archerdefx = -1;
	int assassindefx = -1;
	int magedefx = -1;
	int warriorutilx = -1;
	int archerutilx = -1;
	int assassinutilx = -1;
	int mageutilx = -1;
	int warriorpassivex = -1;
	int archerpassivex = -1;
	int assassinpassivex = -1;
	int magepassivex = -1;
	ArrayList<ItemStack> offensiveItems = new ArrayList<>(); 
	ArrayList<ItemStack> defensiveItems = new ArrayList<>(); 
	ArrayList<ItemStack> utilityItems = new ArrayList<>(); 
	ArrayList<ItemStack> passiveItems = new ArrayList<>(); 
	AdvInventory warriorOffensiveMenu;
	AdvInventory warriorDefensiveMenu;
	AdvInventory warriorUtilityMenu;
	AdvInventory warriorPassiveMenu;
	AdvInventory archerOffensiveMenu;
	AdvInventory archerDefensiveMenu;
	AdvInventory archerUtilityMenu;
	AdvInventory archerPassiveMenu;
	AdvInventory assassinOffensiveMenu;
	AdvInventory assassinDefensiveMenu;
	AdvInventory assassinUtilityMenu;
	AdvInventory assassinPassiveMenu;
	AdvInventory mageOffensiveMenu;
	AdvInventory mageDefensiveMenu;
	AdvInventory mageUtilityMenu;
	AdvInventory magePassiveMenu;
	
	public AbilitiesGui(VallendiaMinigame Main)
	{
	    this.Main = Main;
	    this.generateItems();
	}

	  public void openAbilitiesMenu(Player p)
	  {
	     AdvInventory inv = new AdvInventory(Utils.Colorate("&3&lAbilities"), 27, Utils.placeholder((byte) 7, " "));
		  inv.setItem(new ItemStack(Material.IRON_SWORD), Utils.Colorate("&4&lOffensive"), 11, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	Kit kit = Main.kitmanager.getKit(p);
			    	if(kit == Main.kitmanager.kit("warrior"))
			    	{
				    	warriorOffensiveMenu.openInventory(ep);	
			    	}
			    	if(kit == Main.kitmanager.kit("archer"))
			    	{
				    	archerOffensiveMenu.openInventory(ep);	
			    	}
			    	if(kit == Main.kitmanager.kit("assassin"))
			    	{
				    	assassinOffensiveMenu.openInventory(ep);	
			    	}
			    	if(kit == Main.kitmanager.kit("mage"))
			    	{
				    	mageOffensiveMenu.openInventory(ep);	
			    	}
			    	
			    }
			} ); 
		  
		  inv.setItem(new ItemStack(Material.IRON_CHESTPLATE), Utils.Colorate("&9&lDefensive"), 4, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	Kit kit = Main.kitmanager.getKit(p);
			    	if(kit == Main.kitmanager.kit("warrior"))
			    	{
				    	warriorDefensiveMenu.openInventory(ep);	
			    	}
			    	if(kit == Main.kitmanager.kit("archer"))
			    	{
				    	archerDefensiveMenu.openInventory(ep);	
			    	}
			    	if(kit == Main.kitmanager.kit("assassin"))
			    	{
				    	assassinDefensiveMenu.openInventory(ep);	
			    	}
			    	if(kit == Main.kitmanager.kit("mage"))
			    	{
				    	mageDefensiveMenu.openInventory(ep);	
			    	}
			    }
			} ); 
		  
		  inv.setItem(new ItemStack(Material.FEATHER), Utils.Colorate("&3&lUtility"), 15, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	Kit kit = Main.kitmanager.getKit(p);
			    	if(kit == Main.kitmanager.kit("warrior"))
			    	{
				    	warriorUtilityMenu.openInventory(ep);	
			    	}
			    	if(kit == Main.kitmanager.kit("archer"))
			    	{
				    	archerUtilityMenu.openInventory(ep);	
			    	}
			    	if(kit == Main.kitmanager.kit("assassin"))
			    	{
				    	assassinUtilityMenu.openInventory(ep);	
			    	}
			    	if(kit == Main.kitmanager.kit("mage"))
			    	{
				    	mageUtilityMenu.openInventory(ep);	
			    	}
			    }
			} ); 
		  
		  inv.setItem(new ItemStack(Material.WATCH), Utils.Colorate("&7&lPassive"), 22, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	Kit kit = Main.kitmanager.getKit(p);
			    	if(kit == Main.kitmanager.kit("warrior"))
			    	{
				    	warriorPassiveMenu.openInventory(ep);	
			    	}
			    	if(kit == Main.kitmanager.kit("archer"))
			    	{
				    	archerPassiveMenu.openInventory(ep);	
			    	}
			    	if(kit == Main.kitmanager.kit("assassin"))
			    	{
				    	assassinPassiveMenu.openInventory(ep);	
			    	}
			    	if(kit == Main.kitmanager.kit("mage"))
			    	{
				    	magePassiveMenu.openInventory(ep);	
			    	}
			    }
			} ); 
		  
		  
		  
		  inv.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 18, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	Main.guihandler.openGui(ep, "shop");;
			    }
			}); 
		  
		  inv.openInventory(p);
	  }
	
	  
	  
	  
	  
	  
	  
	  
	  public void openAbilitiesSlotMenu(Player p, Ability ability)
	  {
		  
		  
		  if(Main.abilitymanager.playerHasAbility(p, ability.getName()))
		  {
			  p.sendMessage(Utils.Colorate("&4&lYou already have this ability!"));
		      p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 1, 1);
			  return;
		  }
		  
		  
			AdvInventory abilitiesSlotInv = new AdvInventory(Utils.Colorate("&8&lAbility Slot"), 27, Utils.placeholder((byte) 7, " "));
			String slot1 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 1");
			String slot2 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 2");
			String slot3 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 3");
			String slot4 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 4");
			String slot5 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 5");
			String slot6 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 6");
			String description = Utils.Colorate("&7Put the ability (" + ability.getName() + ") into this slot.");
			String description2 = Utils.Colorate("&c&lWARNING &7you will lose the ability in this slot.");
			
			
			
			if(!(Main.kitmanager.getKit(p) == Main.kitmanager.kit("mage")))
			{
				if(slot1.equalsIgnoreCase("empty"))
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 8), Utils.Colorate("&8&lAbility Slot 1"), 10, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 1);
					    }
					}, description );	
				}else
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 10), Utils.Colorate("&8&l" + slot1), 10, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 1);
					    }
					}, description, description2 );	
				}
				
				
				
				
				if(slot2.equalsIgnoreCase("empty"))
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 8), Utils.Colorate("&8&lAbility Slot 2"), 12, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 2);
					    }
					}, description );	
				}else
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 10), Utils.Colorate("&8&l" + slot2), 12, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 2);
					    }
					}, description, description2 );	
				}
				
				
				
				if(slot3.equalsIgnoreCase("empty"))
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 8), Utils.Colorate("&8&lAbility Slot 3"), 14, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 3);
					    }
					}, description);	
				}else
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 10), Utils.Colorate("&8&l" + slot3), 14, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 3);
					    }
					}, description, description2 );	
				}
				
				
				
				if(slot4.equalsIgnoreCase("empty"))
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 8), Utils.Colorate("&8&lAbility Slot 4"), 16, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 4);
					    }
					}, description);	
				}else
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 10), Utils.Colorate("&8&l" + slot4), 16, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 4);
					    }
					}, description, description2 );	
				}	
			}else // KIT MAGEEEEEEEEEEEEEEEEEE
			{
				
				
				if(slot1.equalsIgnoreCase("empty"))
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 8), Utils.Colorate("&8&lAbility Slot 1"), 9, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 1);
					    }
					}, description );	
				}else
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 10), Utils.Colorate("&8&l" + slot1), 9, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 1);
					    }
					}, description, description2 );	
				}
				
				
				
				
				if(slot2.equalsIgnoreCase("empty"))
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 8), Utils.Colorate("&8&lAbility Slot 2"), 11, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 2);
					    }
					}, description );	
				}else
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 10), Utils.Colorate("&8&l" + slot2), 11, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 2);
					    }
					}, description, description2 );	
				}
				
				
				
				if(slot3.equalsIgnoreCase("empty"))
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 8), Utils.Colorate("&8&lAbility Slot 3"), 13, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 3);
					    }
					}, description);	
				}else
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 10), Utils.Colorate("&8&l" + slot3), 13, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 3);
					    }
					}, description, description2 );	
				}
				
				
				
				if(slot4.equalsIgnoreCase("empty"))
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 8), Utils.Colorate("&8&lAbility Slot 4"), 15, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 4);
					    }
					}, description);	
				}else
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 10), Utils.Colorate("&8&l" + slot4), 15, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 4);
					    }
					}, description, description2 );	
				}
				
				
				if(slot5.equalsIgnoreCase("empty"))
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 8), Utils.Colorate("&8&lAbility Slot 5"), 17, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 5);
					    }
					}, description);	
				}else
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 10), Utils.Colorate("&8&l" + slot5), 17, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 5);
					    }
					}, description, description2 );	
				}
				
				
				if(slot6.equalsIgnoreCase("empty"))
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 8), Utils.Colorate("&8&lAbility Slot 6"), 22, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 6);
					    }
					}, description);	
				}else
				{
					abilitiesSlotInv.setItem(new ItemStack(Material.INK_SACK, 1, (short) 10), Utils.Colorate("&8&l" + slot6), 22, new ClickRunnable() {
					    @Override
					    public void run(InventoryClickEvent e) {
					    	Player ep = (Player) e.getWhoClicked();
					    	openAbilitiesYesNoMenu(ep, ability, 6);
					    }
					}, description, description2 );	
				}
				
				
			}
			
			
			abilitiesSlotInv.openInventory(p);
	  }
	  
	  
	  
	  
	  
	  
	  
	  
		public void openAbilitiesYesNoMenu(Player p, Ability ability, int abilityslot)
		{
			AdvInventory abilitiesInvYesNo = new AdvInventory(Utils.Colorate("&8&lAre you sure?"), 27, Utils.placeholder((byte) 7, " "));
			String itemTitle = Utils.Colorate("&2&lPurchase " + ability.getName());
			String description = Utils.Colorate("&aPrice: &2" + Main.abilitymanager.getPrice(ability.getName(), p));
			abilitiesInvYesNo.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 13), itemTitle, 11, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	Main.abilitymanager.buyAbility(ability.getName(), abilityslot, ep);
			    	ep.closeInventory();
			    }
			}, description );
			
			
			abilitiesInvYesNo.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14),  Utils.Colorate("&4&lCancel"), 15, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			});
			
			abilitiesInvYesNo.openInventory(p);
		}
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  public void generateItems()
	  {
		  this.warriorOffensiveMenu = new AdvInventory(Utils.Colorate("&4&lOffensive Abilities"), 54, Utils.placeholder((byte) 7, " "));
		  this.warriorDefensiveMenu = new AdvInventory(Utils.Colorate("&9&lDefensive Abilities"), 54, Utils.placeholder((byte) 7, " "));
		  this.warriorUtilityMenu = new AdvInventory(Utils.Colorate("&3&lUtility Abilities"), 54, Utils.placeholder((byte) 7, " "));
		  this.warriorPassiveMenu = new AdvInventory(Utils.Colorate("&7&lPassive Abilities"), 54, Utils.placeholder((byte) 7, " "));
		  this.archerOffensiveMenu = new AdvInventory(Utils.Colorate("&4&lOffensive Abilities"), 54, Utils.placeholder((byte) 7, " "));
		  this.archerDefensiveMenu = new AdvInventory(Utils.Colorate("&9&lDefensive Abilities"), 54, Utils.placeholder((byte) 7, " "));
		  this.archerUtilityMenu = new AdvInventory(Utils.Colorate("&3&lUtility Abilities"), 54, Utils.placeholder((byte) 7, " "));
		  this.archerPassiveMenu = new AdvInventory(Utils.Colorate("&7&lPassive Abilities"), 54, Utils.placeholder((byte) 7, " "));
		  this.assassinOffensiveMenu = new AdvInventory(Utils.Colorate("&4&lOffensive Abilities"), 54, Utils.placeholder((byte) 7, " "));
		  this.assassinDefensiveMenu = new AdvInventory(Utils.Colorate("&9&lDefensive Abilities"), 54, Utils.placeholder((byte) 7, " "));
		  this.assassinUtilityMenu = new AdvInventory(Utils.Colorate("&3&lUtility Abilities"), 54, Utils.placeholder((byte) 7, " "));
		  this.assassinPassiveMenu = new AdvInventory(Utils.Colorate("&7&lPassive Abilities"), 54, Utils.placeholder((byte) 7, " "));
		  this.mageOffensiveMenu = new AdvInventory(Utils.Colorate("&4&lOffensive Abilities"), 54, Utils.placeholder((byte) 7, " "));
		  this.mageDefensiveMenu = new AdvInventory(Utils.Colorate("&9&lDefensive Abilities"), 54, Utils.placeholder((byte) 7, " "));
		  this.mageUtilityMenu = new AdvInventory(Utils.Colorate("&3&lUtility Abilities"), 54, Utils.placeholder((byte) 7, " "));
		  this.magePassiveMenu = new AdvInventory(Utils.Colorate("&7&lPassive Abilities"), 54, Utils.placeholder((byte) 7, " "));
		  
		  
		  for(Ability ability : Main.abilitymanager.getAbilities())
		  {
			  ItemStack i = ability.getGuiItem();
			  ItemMeta im = i.getItemMeta();
			  i.setItemMeta(im);
			  if(ability.getAbilityType() == AbilityType.OFFENSIVE)
			  {
				  if(Main.abilitymanager.hasAbility(ability.getName(), Main.kitmanager.kit("warrior")))
				  {
					  warrioroffx ++;
					  warriorOffensiveMenu.setItem(i, Utils.Colorate("&4&l" + ability.getName()), warrioroffx, new ClickRunnable() {
						    @Override
						    public void run(InventoryClickEvent e) {
						    	Player ep = (Player) e.getWhoClicked();
						    	openAbilitiesSlotMenu(ep, ability);
						    }
						}, Utils.Colorate("&8" + ability.getDescription()));  
				  }
				  if(Main.abilitymanager.hasAbility(ability.getName(), Main.kitmanager.kit("archer")))
				  {
					  archeroffx++;
					  archerOffensiveMenu.setItem(i, Utils.Colorate("&4&l" + ability.getName()), archeroffx, new ClickRunnable() {
						    @Override
						    public void run(InventoryClickEvent e) {
						    	Player ep = (Player) e.getWhoClicked();
						    	openAbilitiesSlotMenu(ep, ability);
						    }
						}, Utils.Colorate("&8" + ability.getDescription()));  
				  }
				  if(Main.abilitymanager.hasAbility(ability.getName(), Main.kitmanager.kit("assassin")))
				  {
					  assassinoffx++;
					  assassinOffensiveMenu.setItem(i, Utils.Colorate("&4&l" + ability.getName()), assassinoffx, new ClickRunnable() {
						    @Override
						    public void run(InventoryClickEvent e) {
						    	Player ep = (Player) e.getWhoClicked();
						    	openAbilitiesSlotMenu(ep, ability);
						    }
						}, Utils.Colorate("&8" + ability.getDescription()));  
				  }
				  if(Main.abilitymanager.hasAbility(ability.getName(), Main.kitmanager.kit("mage")))
				  {
					  mageoffx++;
					  mageOffensiveMenu.setItem(i, Utils.Colorate("&4&l" + ability.getName()), mageoffx, new ClickRunnable() {
						    @Override
						    public void run(InventoryClickEvent e) {
						    	Player ep = (Player) e.getWhoClicked();
						    	openAbilitiesSlotMenu(ep, ability);
						    }
						}, Utils.Colorate("&8" + ability.getDescription()));  
				  }
			  }

			  
			  
			  
			  
			  
			  
			  
			  if(ability.getAbilityType() == AbilityType.DEFENSIVE)
			  {
				  if(Main.abilitymanager.hasAbility(ability.getName(), Main.kitmanager.kit("warrior")))
				  {
					  warriordefx ++;
					  warriorDefensiveMenu.setItem(i, Utils.Colorate("&9&l" + ability.getName()), warriordefx, new ClickRunnable() {
						    @Override
						    public void run(InventoryClickEvent e) {
						    	Player ep = (Player) e.getWhoClicked();
						    	openAbilitiesSlotMenu(ep, ability);
						    }
						}, Utils.Colorate("&8" + ability.getDescription()));  
				  }
				  if(Main.abilitymanager.hasAbility(ability.getName(), Main.kitmanager.kit("archer")))
				  {
					  archerdefx++;
					  archerDefensiveMenu.setItem(i, Utils.Colorate("&9&l" + ability.getName()), archerdefx, new ClickRunnable() {
						    @Override
						    public void run(InventoryClickEvent e) {
						    	Player ep = (Player) e.getWhoClicked();
						    	openAbilitiesSlotMenu(ep, ability);
						    }
						}, Utils.Colorate("&8" + ability.getDescription()));  
				  }
				  if(Main.abilitymanager.hasAbility(ability.getName(), Main.kitmanager.kit("assassin")))
				  {
					  assassindefx++;
					  assassinDefensiveMenu.setItem(i, Utils.Colorate("&9&l" + ability.getName()), assassindefx, new ClickRunnable() {
						    @Override
						    public void run(InventoryClickEvent e) {
						    	Player ep = (Player) e.getWhoClicked();
						    	openAbilitiesSlotMenu(ep, ability);
						    }
						}, Utils.Colorate("&8" + ability.getDescription()));  
				  }
				  if(Main.abilitymanager.hasAbility(ability.getName(), Main.kitmanager.kit("mage")))
				  {
					  magedefx++;
					  mageDefensiveMenu.setItem(i, Utils.Colorate("&9&l" + ability.getName()), magedefx, new ClickRunnable() {
						    @Override
						    public void run(InventoryClickEvent e) {
						    	Player ep = (Player) e.getWhoClicked();
						    	openAbilitiesSlotMenu(ep, ability);
						    }
						}, Utils.Colorate("&8" + ability.getDescription()));  
				  }
			  }
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  if(ability.getAbilityType() == AbilityType.UTILITY)
			  {
				  if(Main.abilitymanager.hasAbility(ability.getName(), Main.kitmanager.kit("warrior")))
				  {
					  warriorutilx ++;
					  warriorUtilityMenu.setItem(i, Utils.Colorate("&3&l" + ability.getName()), warriorutilx, new ClickRunnable() {
						    @Override
						    public void run(InventoryClickEvent e) {
						    	Player ep = (Player) e.getWhoClicked();
						    	openAbilitiesSlotMenu(ep, ability);
						    }
						}, Utils.Colorate("&8" + ability.getDescription()));  
				  }
				  if(Main.abilitymanager.hasAbility(ability.getName(), Main.kitmanager.kit("archer")))
				  {
					  archerutilx++;
					  archerUtilityMenu.setItem(i, Utils.Colorate("&3&l" + ability.getName()), archerutilx, new ClickRunnable() {
						    @Override
						    public void run(InventoryClickEvent e) {
						    	Player ep = (Player) e.getWhoClicked();
						    	openAbilitiesSlotMenu(ep, ability);
						    }
						}, Utils.Colorate("&8" + ability.getDescription()));  
				  }
				  if(Main.abilitymanager.hasAbility(ability.getName(), Main.kitmanager.kit("assassin")))
				  {
					  assassinutilx++;
					  assassinUtilityMenu.setItem(i, Utils.Colorate("&3&l" + ability.getName()), assassinutilx, new ClickRunnable() {
						    @Override
						    public void run(InventoryClickEvent e) {
						    	Player ep = (Player) e.getWhoClicked();
						    	openAbilitiesSlotMenu(ep, ability);
						    }
						}, Utils.Colorate("&8" + ability.getDescription()));  
				  }
				  if(Main.abilitymanager.hasAbility(ability.getName(), Main.kitmanager.kit("mage")))
				  {
					  mageutilx++;
					  mageUtilityMenu.setItem(i, Utils.Colorate("&3&l" + ability.getName()), mageutilx, new ClickRunnable() {
						    @Override
						    public void run(InventoryClickEvent e) {
						    	Player ep = (Player) e.getWhoClicked();
						    	openAbilitiesSlotMenu(ep, ability);
						    }
						}, Utils.Colorate("&8" + ability.getDescription()));  
				  }
			  }
			  
			  
			  
			  
			  
			  
			  
			  
			  if(ability.getAbilityType() == AbilityType.PASSIVE)
			  {
				  if(Main.abilitymanager.hasAbility(ability.getName(), Main.kitmanager.kit("warrior")))
				  {
					  warriorpassivex ++;
					  warriorPassiveMenu.setItem(i, Utils.Colorate("&7&l" + ability.getName()), warriorpassivex, new ClickRunnable() {
						    @Override
						    public void run(InventoryClickEvent e) {
						    	Player ep = (Player) e.getWhoClicked();
						    	openAbilitiesSlotMenu(ep, ability);
						    }
						}, Utils.Colorate("&8" + ability.getDescription()));  
				  }
				  if(Main.abilitymanager.hasAbility(ability.getName(), Main.kitmanager.kit("archer")))
				  {
					  archerpassivex++;
					  archerPassiveMenu.setItem(i, Utils.Colorate("&7&l" + ability.getName()), archerpassivex, new ClickRunnable() {
						    @Override
						    public void run(InventoryClickEvent e) {
						    	Player ep = (Player) e.getWhoClicked();
						    	openAbilitiesSlotMenu(ep, ability);
						    }
						}, Utils.Colorate("&8" + ability.getDescription()));  
				  }
				  if(Main.abilitymanager.hasAbility(ability.getName(), Main.kitmanager.kit("assassin")))
				  {
					  assassinpassivex++;
					  assassinPassiveMenu.setItem(i, Utils.Colorate("&7&l" + ability.getName()), assassinpassivex, new ClickRunnable() {
						    @Override
						    public void run(InventoryClickEvent e) {
						    	Player ep = (Player) e.getWhoClicked();
						    	openAbilitiesSlotMenu(ep, ability);
						    }
						}, Utils.Colorate("&8" + ability.getDescription()));  
				  }
				  if(Main.abilitymanager.hasAbility(ability.getName(), Main.kitmanager.kit("mage")))
				  {
					  magepassivex++;
					  magePassiveMenu.setItem(i, Utils.Colorate("&7&l" + ability.getName()), magepassivex, new ClickRunnable() {
						    @Override
						    public void run(InventoryClickEvent e) {
						    	Player ep = (Player) e.getWhoClicked();
						    	openAbilitiesSlotMenu(ep, ability);
						    }
						}, Utils.Colorate("&8" + ability.getDescription()));  
				  }
			  }
		  }
		  
		  
		  //back button
		  warriorOffensiveMenu.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			}); 
		  warriorDefensiveMenu.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			}); 
		  warriorUtilityMenu.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			}); 
		  warriorPassiveMenu.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			}); 
		  
		  
		  
		  assassinOffensiveMenu.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			}); 
		  assassinDefensiveMenu.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			}); 
		  assassinUtilityMenu.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			}); 
		  assassinPassiveMenu.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			}); 
		  
		  
		  
		  
		  
		  archerDefensiveMenu.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			}); 
		  archerOffensiveMenu.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			}); 
		  archerUtilityMenu.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			}); 
		  archerPassiveMenu.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			}); 
		  
		  
		  
		  
		  
		  mageOffensiveMenu.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			}); 
		  mageDefensiveMenu.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			}); 
		  mageUtilityMenu.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			}); 
		  magePassiveMenu.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), Utils.Colorate("&4&lBack"), 49, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openAbilitiesMenu(ep);
			    }
			}); 
		  
		  
		  
	  }
	  
}
