package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class EquipBowAbility implements Ability {
	   public static ArrayList<Player> enabled = new ArrayList();
	   private static HashMap<Player, ItemStack> swordList = new HashMap();

	   public String getName() {
	      return "Equip Bow";
	   }

	   public AbilityType getAbilityType() {
	      return AbilityType.OFFENSIVE;
	   }

	   public List<String> getDescription() {
	      return Arrays.asList("Toggle this ability to swap your sword", "for a bow, or your bow for a sword.", "All of you sword enchantments will be", "converted to your bow but halved.", "Note: You still have to buy arrows.");
	   }

	   public ItemStack getGuiItem() {
	      return new ItemStack(Material.BOW);
	   }

	   public boolean RunAbility(Player p) {
	      if (enabled.contains(p)) {
	         removeBow(p);
	         enabled.remove(p);
	         Language.sendAbilityUseMessage(p, "Disabled", "Equip Bow");
	         return true;
	      } else {
	         Language.sendAbilityUseMessage(p, "Enabled", "Equip Bow");
	         enabled.add(p);
	         int x = -1;
	         ItemStack[] var6;
	         int var5 = (var6 = p.getInventory().getContents()).length;

	         for(int var4 = 0; var4 < var5; ++var4) {
	            ItemStack item = var6[var4];
	            ++x;
	            if (item != null && (item.getType() == Material.IRON_SWORD || item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.STONE_SWORD || item.getType() == Material.WOOD_SWORD || item.getType() == Material.STICK)) {
	               int sharpness = item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
	               int fire = item.getEnchantmentLevel(Enchantment.FIRE_ASPECT);
	               int knockback = item.getEnchantmentLevel(Enchantment.KNOCKBACK);
	               ItemStack bow = new ItemStack(Material.BOW);
	               ItemMeta m = bow.getItemMeta();
	               m.setDisplayName(Utils.Colorate("&4&lLongBow"));
	               m.setUnbreakable(true);
	               m.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES});
	               bow.setItemMeta(m);
	               if (sharpness > 1) {
	                  bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, sharpness / 2);
	               }

	               if (fire > 1) {
	                  bow.addUnsafeEnchantment(Enchantment.ARROW_FIRE, fire / 2);
	               }

	               if (knockback > 1) {
	                  bow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, knockback / 2);
	               }

	               swordList.put(p, item);
	               p.getInventory().remove(item);
	               p.getInventory().setItem(x, bow);
	            }
	         }

	         return false;
	      }
	   }

	   public static void removeBow(Player p) {
	      if (enabled.contains(p)) {
	         int x = -1;
	         ItemStack[] var5;
	         int var4 = (var5 = p.getInventory().getContents()).length;

	         for(int var3 = 0; var3 < var4; ++var3) {
	            ItemStack item = var5[var3];
	            ++x;
	            if (item != null && item.getType() == Material.BOW) {
	               p.getInventory().remove(item);
	               if (swordList.containsKey(p)) {
	                  p.getInventory().setItem(x, (ItemStack)swordList.get(p));
	                  swordList.remove(p);
	               }
	            }
	         }

	      }
	   }

	   public static void onReload() {
	      Iterator var1 = swordList.keySet().iterator();

	      while(var1.hasNext()) {
	         Player p = (Player)var1.next();
	         removeBow(p);
	      }

	   }

	   public static void onDie(Player p) {
	      if (swordList.containsKey(p)) {
	    	 enabled.remove(p);
	         swordList.remove(p);
	      }

	   }

	@Override
	public void DisableAbility(Player p) {
	      if (swordList.containsKey(p)) {
	    	  EquipBowAbility.removeBow(p);
	    	 enabled.remove(p);
	         swordList.remove(p);
	      }
		
	}
	}
