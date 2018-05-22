package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;

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
	
	
	public AbilityManager(VallendiaMinigame Main)
	{
		this.Main = Main;
		
		abilities.add(new ClimbAbility());
		abilities.add(new BackFlipAbility());
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
	
	public int getPrice(String ability)
	{
		return this.getAbility(ability).getCost();
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
		int price = this.getPrice(abilityname);
		
		if(points >= price)
		{
			this.addAbility(abilityname, abilityslot, p);
			Main.shopmanager.subtractPoints(p, price);
	        p.sendTitle(Utils.Colorate("&b&lAbility " + abilityname), "", 20, 40, 40);
	        p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 2, 0);
			
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
	
}
