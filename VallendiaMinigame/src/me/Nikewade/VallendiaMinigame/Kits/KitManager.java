package me.Nikewade.VallendiaMinigame.Kits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
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
import me.Nikewade.VallendiaMinigame.Data.PlayerDataManager;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class KitManager {
    private VallendiaMinigame Main;
    public HashMap<String, Kit> kits = new HashMap<String, Kit>();
	private ArrayList<Kit> allKits = new ArrayList<Kit>();
    Starter starter;
	Warrior warrior;
	Archer archer;
	Assassin assassin;
	Mage mage;
 
    public KitManager(VallendiaMinigame Main) {
        this.Main = Main;
        this.starter = new Starter();
        this.warrior = new Warrior(Main);
        this.archer = new Archer(Main);
        this.assassin = new Assassin(Main);
        this.mage = new Mage(Main);

        kits.put("starter", starter);
        kits.put("warrior", warrior);
        kits.put("archer", archer);
        kits.put("assassin",  assassin);
        kits.put("mage", mage);
        
        allKits.add(warrior);
        allKits.add(archer);
        allKits.add(assassin);
        allKits.add(mage);
        
        
		int archerAbilities = Archer.abilities.size();
		int assassinAbilities = Assassin.abilities.size();
		int mageAbilities = Mage.abilities.size();
		int warriorAbilities = Warrior.abilities.size();
		Utils.log("&3Warrior: " + warriorAbilities);
		Utils.log("&3Archer: " + archerAbilities);
		Utils.log("&3Assassin: " + assassinAbilities);
		Utils.log("&3Mage: " + mageAbilities);
        
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
        
        String discounts = path + "discounts.";
        config.set(discounts + "health", 0);
        config.set(discounts + "regeneration", 0);
        config.set(discounts + "speed", 0);
        config.set(discounts + "armor", 0);
        config.set(discounts + "weapon.melee", 0);
        config.set(discounts + "weapon.ranged", 0);
        config.set(discounts + "tools", 0);
        config.set(discounts + "abilityupgrade", 0);
        

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
    public void giveRespawnKit(Player p, String kitName) {
        FileConfiguration config = Main.getConfig();
        if (config.getConfigurationSection("kits." + kitName) == null) {
            p.sendMessage(kitName + " does not exist!");
            return;
        }
   
        p.getInventory().clear();
        Main.upgrademanager.resetUpgrades(p);
        Main.abilitymanager.resetAbilities(p);
        Main.levelmanager.resetLevel(p);
        Main.levelmanager.resetExp(p);
		p.setFoodLevel(19);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "KillStreak", 0);
        SneakAbility.onDie(p);
        ClimbAbility.onDie(p);
        RageAbility.onDie(p);
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
            //ink sack will be gray dye
            if(type.equalsIgnoreCase("ink_sack"))
            {
            	is = new ItemStack(351, 1, (short)8);
            }
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
            
            im.setUnbreakable(true);
            im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES  });
            
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
        p.setGameMode(GameMode.SURVIVAL);  
    }
    
    
    
    @SuppressWarnings("deprecation")
    public void giveKit(Player p, String kitName) {
        FileConfiguration config = Main.getConfig();
		int pointsCarried = Main.shopmanager.getPoints(p);
		int pointsSpent = Main.shopmanager.getPointsSpent(p);
		int level = Main.levelmanager.getLevel(p);
        if (config.getConfigurationSection("kits." + kitName) == null) {
            p.sendMessage(kitName + " does not exist!");
            return;
        }
        
        AbilityCooldown.stopAll(p.getUniqueId());
        p.getInventory().clear();
		p.setFoodLevel(19);
        Main.upgrademanager.resetUpgrades(p);
        Main.abilitymanager.resetAbilities(p);
        Main.levelmanager.resetLevel(p);
        Main.levelmanager.resetExp(p);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "KillStreak", 0);
		Main.playerdatamanager.editIntData(p.getUniqueId(), "Points", (int) ((pointsCarried + pointsSpent) * (1/Math.pow(level, 0.35))));
		Main.playerdatamanager.editIntData(p.getUniqueId(), "PointsSpent", 0);
	      for(Ability ability : VallendiaMinigame.getInstance().abilitymanager.getAbilities())
	      {
	    	  ability.DisableAbility(p);
	      }
        SneakAbility.onDie(p);
        ClimbAbility.onDie(p);
        RageAbility.onDie(p);
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
            //ink sack will be gray dye
            if(type.equalsIgnoreCase("ink_sack"))
            {
            	is = new ItemStack(351, 1, (short)8);
            }
            ItemMeta im    = is.getItemMeta();
            
            if (im == null)
                continue;
 
            if (name != null)
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
 
            if (lore != null){
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
        p.setGameMode(GameMode.SURVIVAL);
        
        if(!Main.kitmanager.getKit(p).getName(false).equalsIgnoreCase("starter"))
        {
            p.sendTitle(Main.kitmanager.getKit(p).getName(true), "", 20, 40, 40);
            p.playSound(p.getLocation(), Main.kitmanager.getKit(p).getSound(), 2, (float) 0.5);	
        }
        
		//PlayerDataManager.saveInventory(p);

        
        
        
    }
    
    
 

    public ArrayList<Kit> getKits()
    {
    	return allKits;
    }
    
    public Kit kit(String kitname)
    {
		return kits.get(kitname.toLowerCase());
    }
    
    public Kit getKit(Player p) {
    	return kits.get(Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Kit"));
    }
    
}
