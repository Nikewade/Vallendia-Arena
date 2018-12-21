package me.Nikewade.VallendiaMinigame.Shop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class ShopHandler {

	
	private static final List<ShopItem> shopItems = new ArrayList<ShopItem>();
	
	public static final void loadShop() {
		long start = System.nanoTime();
		VallendiaMinigame.getInstance().reloadConfig();
		VallendiaMinigame.getInstance().saveConfig();
		shopItems.clear();
		if(IO.getShopFile().exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(IO.getShopFile()));
				String line = "";
				while((line = reader.readLine()) != null) {
					if(!line.startsWith("#")) {
						String[] split = line.split(";");
						double buy = 0.0d;
						double sell = 0.0d;
						double amount = 0.0d;
						ShopItem item = null;
						if(split.length >= 4) {
							if(ShopUtils.fromString(split[0]).getType() == Material.SPLASH_POTION || ShopUtils.fromString(split[0]).getType() == Material.POTION || ShopUtils.fromString(split[0]).getType() == Material.LINGERING_POTION)
							{
								try { buy = Double.parseDouble(split[3].trim()); sell = Double.parseDouble(split[4].trim()); amount = Double.parseDouble(split[5].trim()); } catch(Exception e) {  }
								item = new ShopItem(ShopUtils.fromString(split[0]), buy, sell);
								PotionMeta pm = (PotionMeta) item.getStack().getItemMeta();
								if(split[1].equalsIgnoreCase("extended"))
								{
									pm.setBasePotionData(new PotionData(PotionType.valueOf(split[2].trim()), true, false));
								}
								
								if(split[1].equalsIgnoreCase("upgraded"))
								{
									pm.setBasePotionData(new PotionData(PotionType.valueOf(split[2].trim()), false, true));
								}
								
								if(split[1].equalsIgnoreCase("none"))
								{
									pm.setBasePotionData(new PotionData(PotionType.valueOf(split[2].trim()), false, false));
								}
								item.getStack().setItemMeta(pm);
							}else
							{
								try { buy = Double.parseDouble(split[1].trim()); sell = Double.parseDouble(split[2].trim()); amount = Double.parseDouble(split[3].trim()); } catch(Exception e) {  }
								item = new ShopItem(ShopUtils.fromString(split[0]), buy, sell);	
							}
							ItemStack is = item.getStack();
							is.setAmount((int) amount);
							if(item.init()) shopItems.add(item); else ShopUtils.log("&4&lThere was an error while initializing the shop item '" + split[0].trim() + "'.");
						}
					}
				}
				reader.close();
			} catch(Exception e) { ShopUtils.error(e, "An error occurred while reading your shop file."); }
		}
		long end = System.nanoTime();
		long time = end - start;
		ShopUtils.log("&2&lReloaded shop in " + time + " nanoseconds.");
	}
	
	public static final void saveShop() {
		IO.getShopFile().delete();
		try {
			IO.getShopFile().createNewFile();
			FileWriter writer = new FileWriter(IO.getShopFile());
			String add = "# V: "  + "\n\n";
			add += "# Lines with # at the beginning are not loaded.\n";
			add += "# Format: ITEM_NAME[:DATA_VALUE];BUY_PRICE;SELL_PRICE;AMOUNT\n";
			add += "# Example: WOOL:2;5;2;64\n";
			add += "# Example: STICK;5;2\n\n";
			for(ShopItem item : shopItems) {
				{
					if(item.getStack().getType() == Material.SPLASH_POTION || item.getStack().getType() == Material.POTION || item.getStack().getType() == Material.LINGERING_POTION)
					{
						PotionMeta pm = (PotionMeta) item.getStack().getItemMeta();
						
						if(!pm.getBasePotionData().isUpgraded() && !pm.getBasePotionData().isExtended() )
						{
							add += item.getStack().getType() + ";" + "none" + ";" + pm.getBasePotionData().getType() + ";"  + item.getBuyPrice() + ";" + item.getSellPrice() +  ";" + item.getStack().getAmount() +   "\n";	
						}
						
						if(pm.getBasePotionData().isExtended())
						{
							add += item.getStack().getType() + ";" + "extended" + ";" + pm.getBasePotionData().getType() + ";"  + item.getBuyPrice() + ";" + item.getSellPrice() +  ";" + item.getStack().getAmount() +   "\n";	
						}
						if(pm.getBasePotionData().isUpgraded())
						{
							add += item.getStack().getType() + ";" + "upgraded" + ";" + pm.getBasePotionData().getType() + ";"  + item.getBuyPrice() + ";" + item.getSellPrice() +  ";" + item.getStack().getAmount() +   "\n";	
						}
					}else
					{
						add += item.getStack().getType() + ((item.getStack().getDurability() != 0) ? (":" + item.getStack().getDurability()) : "") + ";" + item.getBuyPrice() + ";" + item.getSellPrice() +  ";" + item.getStack().getAmount() +   "\n";
					}	
				}
			}
			writer.write(add);
			writer.close();
		} catch(Exception e) {
			ShopUtils.error(e, "Couldn't create default shop file.");
		}
	}
	
	public static final void addItem(ShopItem item) {
		loadShop();
		shopItems.add(item);
		saveShop();
	}
	
	public static final boolean removeItem(ItemStack stack) {
		loadShop();
		for(ShopItem item : shopItems) {
			if(item.getStack().getType().equals(stack.getType())) {
				if(item.getStack().getDurability() == stack.getDurability()) {
					shopItems.remove(item);
					saveShop();
					return true;
				}
			}
		}
		return false;
	}
	
	public static final boolean buyItem(Player player, ItemStack stack, double cost) {
		if(VallendiaMinigame.getInstance().shopmanager.getPoints(player) >= cost) {
			VallendiaMinigame.getInstance().shopmanager.subtractPoints(player, (int) cost);
			player.getInventory().addItem(stack);
			return true;
		}
		ShopUtils.chat(player, VallendiaMinigame.getInstance().getConfig().getString("langNotEnoughMoney"));
		return false;
	}
	
	public static final boolean sellItem(Player player, ItemStack stahck, double cost) {
		if(player.getInventory().containsAtLeast(stahck, stahck.getAmount())) {
			player.getInventory().removeItem(stahck);
			VallendiaMinigame.getInstance().shopmanager.addPoints(player, (int) cost);
			return true;
		}
		ShopUtils.chat(player, VallendiaMinigame.getInstance().getConfig().getString("langNotEnoughItems"));
		return false;
	}
	
	public static final ShopItem[] getShopItems() {
		return shopItems.toArray(new ShopItem[shopItems.size()]);
	}
	
}
