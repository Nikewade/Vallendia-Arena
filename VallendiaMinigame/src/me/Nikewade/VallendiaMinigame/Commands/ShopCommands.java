package me.Nikewade.VallendiaMinigame.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Shop.GuiShop;
import me.Nikewade.VallendiaMinigame.Shop.ShopGuiHandler;
import me.Nikewade.VallendiaMinigame.Shop.ShopHandler;
import me.Nikewade.VallendiaMinigame.Shop.ShopItem;
import me.Nikewade.VallendiaMinigame.Shop.ShopUtils;


public class ShopCommands implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		/*if(sender instanceof Player) {
			player((Player) sender);
		} else {
			console(sender);
		}*/
		if(args.length == 0) {
			if(sender instanceof Player && sender.hasPermission("burkeyshop.use")) {
				Player player = (Player) sender;
				ShopGuiHandler.open(player, new GuiShop(player, 0));
				ShopUtils.chat(player, VallendiaMinigame.getInstance().getConfig().getString("langShopOpen"));
				return true;
			} else if(!sender.hasPermission("burkeyshop.use")) {
				ShopUtils.chat(sender, VallendiaMinigame.getInstance().getConfig().getString("langNoPerm"));
				return true;
			} else {
				ShopUtils.log("&4&lOnly in-game players may use &6/shop&r");
				return true;
			}
		} else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("reload")) {
				if(sender.hasPermission("burkshop.admin")) {
					ShopHandler.loadShop();
					ShopUtils.chat(sender, VallendiaMinigame.getInstance().getConfig().getString("langReloaded"));
					return true;
				} else {
					ShopUtils.chat(sender, VallendiaMinigame.getInstance().getConfig().getString("langNoPerm"));
					return true;
				}
			} else if(args[0].equalsIgnoreCase("remove")) {
				if(sender.hasPermission("burkshop.admin")) {
					Player ply = (Player) sender;
					ItemStack hand = ply.getInventory().getItemInMainHand();
					if(hand != null && hand.getType() != null && !hand.getType().equals(Material.AIR)) {
						if(ShopHandler.removeItem(hand)) {
							ShopUtils.chat(sender, VallendiaMinigame.getInstance().getConfig().getString("langRemovedItem"));
							return true;
						} else {
							ShopUtils.chat(sender, VallendiaMinigame.getInstance().getConfig().getString("langNoRem"));
							return true;
						}
					}
				} else {
					ShopUtils.chat(sender, VallendiaMinigame.getInstance().getConfig().getString("langNoPerm"));
					return true;
				}
			}
		} else if(args.length == 3) {
			if(args[0].equalsIgnoreCase("add")) {
				if(sender instanceof Player) {
					if(sender.hasPermission("burkshop.admin")) {
						Player ply = (Player) sender;
						ItemStack hand = ply.getInventory().getItemInMainHand();
						if(hand != null && !hand.getType().equals(Material.AIR)) {
							double buy = Double.parseDouble(args[1]);
							double sell = Double.parseDouble(args[2]);
							ShopHandler.addItem(new ShopItem(hand, buy, sell));
							ShopUtils.chat(sender, VallendiaMinigame.getInstance().getConfig().getString("langAddedItem"));
							return true;
						} else {
							ShopUtils.chat(sender, VallendiaMinigame.getInstance().getConfig().getString("langNoHand"));
							return true;
						}
					} else {
						ShopUtils.chat(sender, VallendiaMinigame.getInstance().getConfig().getString("langNoPerm"));
						return true;
					}
				} else {
					ShopUtils.log("&4&lOnly in-game players may add items to the shop.");
					return true;
				}
			}
		}
		
		sender.sendMessage(ShopUtils.color("&4&lUsage:"));
		sender.sendMessage(ShopUtils.color("&4&l  /shop"));
		sender.sendMessage(ShopUtils.color("&4&l  /shop reload"));
		sender.sendMessage(ShopUtils.color("&4&l  /shop add <buyPrice> <sellPrice>"));
		sender.sendMessage(ShopUtils.color("&4&l  /shop remove"));
		return true;
	}
	
	/*private void player(Player player) {
		
	}
	
	private void console(CommandSender sender) {
		
	}*/
}
