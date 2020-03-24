package me.Nikewade.VallendiaMinigame.Data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Abilities.ClimbAbility;
import me.Nikewade.VallendiaMinigame.Abilities.RageAbility;
import me.Nikewade.VallendiaMinigame.Abilities.SneakAbility;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class PlayerDataManager {
	static VallendiaMinigame Main;
	CreatePlayerData createData;
	static YamlConfiguration config;
	
	 public PlayerDataManager(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	    this.createData = new CreatePlayerData();
	  }
	 
	 
	 public void createFile(Player p)
	 {
		 createData.createFile(p);
	 }
	 
	 //add back to these the save inventory
	 public int getPlayerIntData(UUID uuid, String data)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);
		   
		 return config.getInt(data);
	 }
	 
	 
	 public String getPlayerStringData(UUID uuid, String data)
	 {
		 File f = new File(PlayerDataManager.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 PlayerDataManager.config = YamlConfiguration.loadConfiguration(f);
		   
		 return config.getString(data);
	 }
	 
	 
	 public void editData (UUID uuid, String data, String edit)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);	
		 config.set(data, edit);
		 
		 
		 try {
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
	 
	 public void editIntData (UUID uuid, String data, int edit)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);	
		 config.set(data, edit);
		 
		 try {
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 
	 public void addData (UUID uuid, String data, int add)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);	
		 int originaldata = this.getPlayerIntData(uuid, data);
		 config.set(data, originaldata + add);
		 
		 try {
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 
	 public void subtractData (UUID uuid, String data, int subtract)
	 {
		 File f = new File(this.Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + uuid + ".yml");
		 this.config = YamlConfiguration.loadConfiguration(f);	
		 int originaldata = this.getPlayerIntData(uuid, data);
		 config.set(data, originaldata - subtract);
		 
		 try {
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 
	 /*
	  * 	 public static void saveInventory(Player p)
	 {
	        PlayerInventory inv = p.getInventory();
			 File f = new File(Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + p.getUniqueId() + ".yml");
			 config = YamlConfiguration.loadConfiguration(f);	
		     String path = "Inventory.";
		     config.set("Inventory", "");
	        for (int i = 0; i < 36; i++) {
	            ItemStack is = inv.getItem(i);
	 
	            if (is == null || is.getType() == Material.AIR)
	                continue;            
	            String slot = path + "items." + i;
	            config.set(slot + ".type", is.getType().toString().toLowerCase());
	            config.set(slot + ".amount", is.getAmount());
	 
	            if (!is.hasItemMeta())
	                continue;
	 
	            if (is.getItemMeta().hasDisplayName())
	                config.set(slot + ".name", is.getItemMeta().getDisplayName());
	 
	            if (is.getItemMeta().hasLore())
	                config.set(slot + ".lore", is.getItemMeta().getLore());
	 
	            if (is.getItemMeta().hasEnchants()) {
	                Map<Enchantment, Integer> enchants = is.getEnchantments();
	                List<String> enchantList = new ArrayList<String>();
	                for (Enchantment e : is.getEnchantments().keySet()) {
	                    int level = enchants.get(e);
	                    enchantList.add(e.getName().toLowerCase() + ":" + level);
	                }
	                config.set(slot + ".enchants", enchantList);
	            }
	            continue;
	        }
	 
	        config.set(path + "armor.helmet", inv.getHelmet() != null ? inv
	                .getHelmet() : "air");
	 
	        config.set(path + "armor.chestplate", inv.getChestplate() != null ? inv
	                .getChestplate() : "air");
	 
	        config.set(path + "armor.leggings", inv.getLeggings() != null ? inv
	                .getLeggings(): "air");
	 
	        config.set(path + "armor.boots", inv.getBoots() != null ? inv
	                .getBoots(): "air");
	 
			 try {
					config.save(f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 }
	  */
	 
	 
	 
	 /*
	  * 	 
	 public static void giveInventory(Player p)
	 {
		 File f = new File(Main.getFileManager().getUsersFile().getAbsolutePath() + "/" + p.getUniqueId() + ".yml");
		 config = YamlConfiguration.loadConfiguration(f);	
	     String path = "Inventory.";
	     
	        if (config.getConfigurationSection(path + "items") == null) {
	            return;
	        }
	     
	        ConfigurationSection s = config.getConfigurationSection(path + "items");
	        for (String str : s.getKeys(false)) {
	            int slot = Integer.parseInt(str);
	            if (0 > slot && slot > 36)
	                return;
	 
	            String string            = path + "items." + slot + ".";
	            String type            = config.getString(string + "type");
	            String name            = config.getString(string + "name");
	            List<String> lore        = config.getStringList(string + "lore");
	            List<String> enchants    = config.getStringList(string + "enchants");
	            int amount              = config.getInt(string + "amount");
	            
	            ItemStack is    = new ItemStack(Material.matchMaterial(type.toUpperCase()), amount);
	            //ink sack will be gray dye
	            ItemMeta im    = is.getItemMeta();
	            
	            if (im == null)
	            {
	                continue;	
	            }
	            
	            	
	 
	            if (name != null)
	                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
	            if(type.equalsIgnoreCase("ink_sack") && im.getDisplayName().contains("Ability Slot"))
	            {
	            	is = new ItemStack(351, 1, (short)8);
	            }else
	            {
	            	if(type.equalsIgnoreCase("ink_sack"))
	            	{
		            	is = new ItemStack(351, 1, (short)10);
	            	}
	            }

	 
	            if (lore != null)
	            {
	            	im.setLore(lore);
	            }
	            	
	            if (enchants != null) {
	                for (String s1 : enchants) {
	                    String[] indiEnchants = s1.split(":");
	                    im.addEnchant(Enchantment.getByName(indiEnchants[0].toUpperCase()), Integer.parseInt(indiEnchants[1]), true);
	                }
	            }
	            
	            if(name != null && name.equalsIgnoreCase(Utils.Colorate("&3&lWand")))
	            {
	            	List loreadd = Arrays.asList(Utils.Colorate("&7Right click to shoot weak magic"),
	            			Utils.Colorate("&7that does 1 heart."));
	            	im.setLore(loreadd);
	            }
	            im.setUnbreakable(true);
	            im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES  });

	 
	            is.setItemMeta(im);
	            p.getInventory().setItem(slot, is);
	        }
	 
	        ItemStack helmet        = config.getItemStack(path + "armor.helmet");
	        ItemStack chestplate    = config.getItemStack(path + "armor.chestplate");
	        ItemStack leggings    = config.getItemStack(path + "armor.leggings");
	        ItemStack boots        = config.getItemStack(path + "armor.boots");
	 
	        if(helmet != null)
	        {
	            ItemMeta helmetim = helmet.getItemMeta();
	            helmetim.setUnbreakable(true);
	            helmetim.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES  });
	            helmet.setItemMeta(helmetim);
	            p.getInventory().setHelmet(new ItemStack(helmet));
	        }
	        if(chestplate != null)
	        {
	            ItemMeta chestmeta = chestplate.getItemMeta();
	            chestmeta.setUnbreakable(true);
	            chestmeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES  });
	            chestplate.setItemMeta(chestmeta);
	            p.getInventory().setChestplate(new ItemStack(chestplate));
	        }
	        if(leggings != null)
	        {
	            ItemMeta legmeta = leggings.getItemMeta();
	            legmeta.setUnbreakable(true);
	            legmeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES  });
	            leggings.setItemMeta(legmeta);
	            p.getInventory().setLeggings(new ItemStack(leggings));
	        }
	        if(boots != null)
	        {
	            ItemMeta bootmeta = boots.getItemMeta();
	            bootmeta.setUnbreakable(true);
	            bootmeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES  });
	            boots.setItemMeta(bootmeta);
	            p.getInventory().setBoots(new ItemStack(boots));
	        }
	        
	        
	        p.updateInventory();
	 }
	  */
	 
	 
	 
	 
}
