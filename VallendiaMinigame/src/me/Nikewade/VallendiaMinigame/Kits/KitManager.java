package me.Nikewade.VallendiaMinigame.Kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class KitManager {
    private VallendiaMinigame Main;
    public HashMap<String, Kit> kits = new HashMap<String, Kit>();
 
    public KitManager(VallendiaMinigame Main) {
        this.Main = Main;
    }

    public void createKit(Player p, String kitName) {
        FileConfiguration config = Main.getConfig();
        PlayerInventory inv = p.getInventory();
 
        if (config.getConfigurationSection("kits." + kitName) != null) {
            p.sendMessage(kitName + " already exists!");
            return;
        }
 
        String path = "kits." + kitName + ".";
        config.createSection("kits." + kitName);

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
 
        Main.saveConfig();
		p.sendMessage("You created the kit " + kitName);
    }
 
   
    
    public void removeKit(Player p, String kitName)
    {
        FileConfiguration config = Main.getConfig();
        if (config.getConfigurationSection("kits." + kitName) == null) {
            p.sendMessage(kitName + " does not exist!");
            return;
        }
        
        config.set("kits." + kitName, null);
        Main.saveConfig();
		p.sendMessage("kit " + kitName + " removed");
    }
    
    
    
    @SuppressWarnings("deprecation")
    public void giveKit(Player p, String kitName) {
        FileConfiguration config = Main.getConfig();
        if (config.getConfigurationSection("kits." + kitName) == null) {
            p.sendMessage(kitName + " does not exist!");
            return;
        }
 
        p.getInventory().clear();
        String path = "kits." + kitName + ".";
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
            ItemMeta im    = is.getItemMeta();
            
            if (im == null)
                continue;
 
            if (name != null)
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
 
            if (lore != null);
            	
            if (enchants != null) {
                for (String s1 : enchants) {
                    String[] indiEnchants = s1.split(":");
                    im.addEnchant(Enchantment.getByName(indiEnchants[0].toUpperCase()), Integer.parseInt(indiEnchants[1]), true);
                }
            }
            
            //Shop item
            if(!kitName.equalsIgnoreCase("starter"))
            {
                ItemStack ishop    = new ItemStack(Material.NETHER_STAR, 1);
                ItemMeta imshop    = ishop.getItemMeta();
                imshop.setDisplayName(Utils.Colorate("&b&lShop"));	
                ishop.setItemMeta(imshop);
                p.getInventory().setItem(8, ishop);
            }
 
            is.setItemMeta(im);
            p.getInventory().setItem(slot, is);
            Main.playerdatamanager.editData(p.getUniqueId(), "Kit", kitName);
        }
 
        ItemStack helmet        = config.getItemStack(path + "armor.helmet");
        ItemStack chestplate    = config.getItemStack(path + "armor.chestplate");
        ItemStack leggings    = config.getItemStack(path + "armor.leggings");
        ItemStack boots        = config.getItemStack(path + "armor.boots");
 
        if(helmet != null)
        {
            p.getInventory().setHelmet(new ItemStack(helmet));
        }
        if(chestplate != null)
        {
            p.getInventory().setChestplate(new ItemStack(chestplate));
        }
        if(leggings != null)
        {
            p.getInventory().setLeggings(new ItemStack(leggings));
        }
        if(boots != null)
        {
            p.getInventory().setBoots(new ItemStack(boots));
        }
        p.updateInventory();
        p.setGameMode(GameMode.SURVIVAL);
    }
 

    public Kit getKit(Player p) {
    	return kits.get(Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Kit"));
    }
    
}