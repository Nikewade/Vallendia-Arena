package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class AbilityManager {
	VallendiaMinigame Main;
	private ArrayList<Ability> abilities = new ArrayList<Ability>();
	private HashMap<UUID, Long> cooldown = new HashMap<>();
	
	
	public AbilityManager(VallendiaMinigame Main)
	{
		this.Main = Main;
		
		abilities.add(new ClimbAbility());
		abilities.add(new BackFlipAbility());
		abilities.add(new DeflectArrowsAbility());
		abilities.add(new MageArmorAbility());
		abilities.add(new RageAbility());
		abilities.add(new LeapAbility());
		abilities.add(new BashAbility());
		abilities.add(new DashAbility());
		abilities.add(new SneakAbility());
		abilities.add(new BackstabAbility());
		abilities.add(new GrapplingHookAbility());
		abilities.add(new BlinkAbility());
		
		
		
		
		this.generateAbilityPrices();
	}
	
	
	public ArrayList<Ability> getAbilities() {
		return abilities;
	}
	
		
	
	public Ability getAbility(String name) 
		{
					for(Ability ability : abilities)
					{
						if(ability.getName().equalsIgnoreCase(name))
						{
							return ability;
						}
					}
			return null;
		}
	
	public String getAbilitySlot(int slot, Player p)
	{
		return Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot" + slot);
	}
	
	public int getPrice(String ability, Player p)
	{
		return Main.getConfig().getInt("Abilities." + ability + "." + Main.kitmanager.getKit(p).getName(false).toLowerCase() + ".price");
	}
	
	public int getCooldown(String ability, Player p)
	{
		return Main.getConfig().getInt("Abilities." + ability + "." + Main.kitmanager.getKit(p).getName(false).toLowerCase() + ".cooldown");
	}
	
	public boolean hasAbility(String ability ,Kit kit)
	{
		if(kit.getAbilities().contains(this.getAbility(ability)))
		{
			return true;
		}else return false;
	}
	
	public boolean playerHasAbility(Player p, String ability)
	{
		String slot1 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 1");
		String slot2 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 2");
		String slot3 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 3");
		String slot4 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 4");
		String slot5 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 5");
		String slot6 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 6");
		if(slot1.equalsIgnoreCase(ability) || slot2.equalsIgnoreCase(ability) || slot3.equalsIgnoreCase(ability) || slot4.equalsIgnoreCase(ability) || slot5.equalsIgnoreCase(ability) || slot6.equalsIgnoreCase(ability))
		{
			return true;
		}
		return false;
	}
	
	
	public void runAbility(String ability, Player p)
	{
		
		if(this.hasAbility(ability, Main.kitmanager.getKit(p)))
		{
			this.getAbility(ability).RunAbility(p);
		}else
		{
			p.sendMessage("YOU DONT HAVE THIS ABILITY!");
		}
	}
	
	
	public void addAbility(String abilityname, int abilityslot, Player p)
	{
		
		if(this.hasAbility(abilityname, Main.kitmanager.getKit(p)))
		{
			String slot = "Abilities.slot " + abilityslot;
			Ability ability = this.getAbility(abilityname);
			ItemStack abilityItem = new ItemStack(Material.INK_SACK, 1 , (short)10);
			ItemMeta abilityim = abilityItem.getItemMeta();
			abilityim.setDisplayName(Utils.Colorate("&8&l" + abilityname +  " &7(" + ability.getAbilityType() + ")"));
			ArrayList<String> lore = new ArrayList<String>();
			lore.add( Utils.Colorate("&8&lSlot " + abilityslot));
			if(ability.getAbilityType() != AbilityType.PASSIVE)
			{
				lore.add(Utils.Colorate("&8&lCooldown: &7" + this.getCooldown(abilityname, p) + " seconds"));	
			}
			lore.add(Utils.Colorate("&7") + ability.getDescription());
			abilityim.setLore(lore);
			abilityItem.setItemMeta(abilityim);
			
			if(Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), slot).equalsIgnoreCase(abilityname)) //player already has that ability
			{
				return;
			}
			
			if(Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), slot).equalsIgnoreCase("empty"))
			{
				
				int x = -1;
				for(ItemStack item : p.getInventory().getContents())
				{
					x++;
				if(item != null)
				{
					if(item.getType() == Material.INK_SACK && item.getDurability() == 8 && item.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.Colorate("&8&lAbility Slot " + abilityslot)))
					{
							p.getInventory().setItem(x, abilityItem);
							break;
					}
				}
				}
				Main.playerdatamanager.editData(p.getUniqueId(), slot, abilityname);	
			}else
			{
				int x = -1;
				for(ItemStack item : p.getInventory().getContents())
				{
					x++;
				if(item != null)
				{
					if(item.getType() == Material.INK_SACK && item.getDurability() == 10 && ChatColor.stripColor(item.getItemMeta().getLore().get(0)).equalsIgnoreCase("Slot " + abilityslot))
					{
							p.getInventory().setItem(x, abilityItem);
							break;
					}
				}
				}
				Main.playerdatamanager.editData(p.getUniqueId(), slot, abilityname);
			}
			
			
			
			
			
		}
	}
	
	public void buyAbility(String abilityname, int abilityslot ,Player p)
	{
		int points = Main.shopmanager.getPoints(p);
		int price = this.getPrice(abilityname, p);
		String slot = "Abilities.slot " + abilityslot;
		
		if(points >= price)
		{
			if(!(Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), slot).equalsIgnoreCase("empty")))
	        {
	        	String abilityName = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), slot);
	        	Main.levelmanager.subtractExp(p,Main.getConfig().getInt("Abilities." + abilityName + "." + Main.kitmanager.getKit(p).getName(false).toLowerCase() + ".exp"));
	        }
			this.addAbility(abilityname, abilityslot, p);
			Main.shopmanager.subtractPoints(p, price);
	        p.sendTitle(Utils.Colorate("&3&lAbility " + abilityname), "", 20, 40, 40);
	        p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 2, 0);
	        Main.levelmanager.addEXP(p, Main.getConfig().getInt("Abilities." + abilityname + "." + Main.kitmanager.getKit(p).getName(false).toLowerCase() + ".exp"));
			
		}else
		{
	        p.sendTitle(Utils.Colorate("&4&lX"), Utils.Colorate("&4&lToo expensive!"), 20, 40, 40);
	        p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 1, 1);
		}
		
	}
	
	
	public void resetAbilities(Player p)
	{
		Main.playerdatamanager.editData(p.getUniqueId(), "Abilities.slot 1", "empty");
		Main.playerdatamanager.editData(p.getUniqueId(), "Abilities.slot 2", "empty");
		Main.playerdatamanager.editData(p.getUniqueId(), "Abilities.slot 3", "empty");
		Main.playerdatamanager.editData(p.getUniqueId(), "Abilities.slot 4", "empty");
		Main.playerdatamanager.editData(p.getUniqueId(), "Abilities.slot 5", "empty");
		Main.playerdatamanager.editData(p.getUniqueId(), "Abilities.slot 6", "empty");
	}
	
	
	
	
	public void generateAbilityPrices()
	{
		for(Ability ability : this.getAbilities())
		{
			String path = "Abilities." + ability.getName() + ".";
			
			if(Main.getConfig().get(path + "warrior.price") == null || Main.getConfig().get(path + "warrior.cooldown", 0)	== null)
			{
				Main.getConfig().set(path + "warrior.price", 0);	
				Main.getConfig().set(path + "warrior.cooldown", 0);	
				Main.getConfig().set(path + "warrior.exp", 0);	
			}
			
			if(Main.getConfig().get(path + "assassin.price") == null || Main.getConfig().get(path + "assassin.cooldown", 0) == null)
			{
				Main.getConfig().set(path + "assassin.price", 0);	
				Main.getConfig().set(path + "assassin.cooldown", 0);	
				Main.getConfig().set(path + "assassin.exp", 0);	
			}
			
			if(Main.getConfig().get(path + "mage.price") == null || Main.getConfig().get(path + "mage.cooldown", 0) == null)
			{
				Main.getConfig().set(path + "mage.price", 0);	
				Main.getConfig().set(path + "mage.cooldown", 0);	
				Main.getConfig().set(path + "mage.exp", 0);	
			}
			
			if(Main.getConfig().get(path + "archer.price") == null || Main.getConfig().get(path + "archer.cooldown", 0)	== null)
			{
				Main.getConfig().set(path + "archer.price", 0);	
				Main.getConfig().set(path + "archer.cooldown", 0);	
				Main.getConfig().set(path + "archer.exp", 0);	
			}
			
			Main.saveConfig();
		}
	}
	
	
	
}
