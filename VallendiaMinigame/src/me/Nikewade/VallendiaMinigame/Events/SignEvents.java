package me.Nikewade.VallendiaMinigame.Events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
<<<<<<< HEAD
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
=======
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.PlayerInventory;
>>>>>>> second-repo/master

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class SignEvents implements Listener{
	VallendiaMinigame Main;
<<<<<<< HEAD
	Map<Material, Integer> prices = new HashMap<>();
=======
	Map<Material, Integer> orePrices = new HashMap<>();
	Map<Material, Integer> producePrices = new HashMap<>();
>>>>>>> second-repo/master
	  int coal= 1;
	  int iron= 2;
	  int gold= 3;
	  int diamond= 4;
	  int emerald= 5;
	  int carrot= 1;
	  int potato= 2;
	  int poison_potato= 10;
	  int red_mushroom = 1;
	  int brown_mushroom = 1;
	  int wheat= 4;
	  int nether_wart= 5;
<<<<<<< HEAD
=======
	  
	  int oreBulk = 1;
	  int produceBulk = 4;
>>>>>>> second-repo/master

	public SignEvents(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
		coal = Main.getConfig().getInt("Resources.coal");
		iron = Main.getConfig().getInt("Resources.iron");
		gold = Main.getConfig().getInt("Resources.gold");
		diamond = Main.getConfig().getInt("Resources.diamond");
		emerald = Main.getConfig().getInt("Resources.emerald");
		carrot = Main.getConfig().getInt("Resources.carrot");
		potato = Main.getConfig().getInt("Resources.potato");
		poison_potato = Main.getConfig().getInt("Resources.poison_potato");
		wheat = Main.getConfig().getInt("Resources.wheat");
		red_mushroom = Main.getConfig().getInt("Resources.red_mushroom");
		brown_mushroom = Main.getConfig().getInt("Resources.brown_mushroom");
		nether_wart = Main.getConfig().getInt("Resources.nether_wart");
		
<<<<<<< HEAD
		prices.put(Material.COAL, coal);
		prices.put(Material.IRON_ORE, iron);
		prices.put(Material.GOLD_ORE, gold);
		prices.put(Material.DIAMOND, diamond);
		prices.put(Material.EMERALD, emerald);
		prices.put(Material.CARROT_ITEM, carrot);
		prices.put(Material.POTATO_ITEM, potato);
		prices.put(Material.WHEAT, wheat);
		prices.put(Material.BROWN_MUSHROOM, brown_mushroom);
		prices.put(Material.RED_MUSHROOM, red_mushroom);
		prices.put(Material.WHEAT, wheat);
		prices.put(Material.POISONOUS_POTATO, poison_potato);
		prices.put(Material.NETHER_STALK, nether_wart);
=======
		orePrices.put(Material.COAL, coal);
		orePrices.put(Material.IRON_ORE, iron);
		orePrices.put(Material.GOLD_ORE, gold);
		orePrices.put(Material.DIAMOND, diamond);
		orePrices.put(Material.EMERALD, emerald);
		
		producePrices.put(Material.CARROT_ITEM, carrot);
		producePrices.put(Material.POTATO_ITEM, potato);
		producePrices.put(Material.WHEAT, wheat);
		producePrices.put(Material.BROWN_MUSHROOM, brown_mushroom);
		producePrices.put(Material.RED_MUSHROOM, red_mushroom);
		producePrices.put(Material.WHEAT, wheat);
		producePrices.put(Material.POISONOUS_POTATO, poison_potato);
		producePrices.put(Material.NETHER_STALK, nether_wart);
>>>>>>> second-repo/master
		
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent e)
	{
<<<<<<< HEAD
		if(e.getLine(0).equalsIgnoreCase("[Sell Resources]"))
		{
			e.setLine(0, Utils.Colorate("&3[Right Click]"));
			e.setLine(1, "Sell Resources");
=======
		if(e.getLine(0).equalsIgnoreCase("[Ores]"))
		{
			e.setLine(0, Utils.Colorate("&3[Ores]"));
			e.setLine(1, "Left Click: Sell");
			e.setLine(2, "Right Click: Sell All");
		}
		
		if(e.getLine(0).equalsIgnoreCase("[Produce]"))
		{
			e.setLine(0, Utils.Colorate("&3[Produce]"));
			e.setLine(1, "Left Click: Sell");
			e.setLine(2, "Right Click: Sell All");
>>>>>>> second-repo/master
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getState() instanceof Sign)
		{
			Player p = e.getPlayer();
<<<<<<< HEAD
			  int coal= 0;
			  int iron= 0;
			  int gold= 0;
			  int diamond= 0;
			  int emerald= 0;
			  int carrot= 0;
			  int potato= 0;
			  int poison_potato= 0;
			  int wheat= 0;
			  int red_mushroom= 0;
			  int brown_mushroom= 0;
			  int nether_wart= 0;
			Inventory inv = p.getInventory();
			Sign sign = (Sign) e.getClickedBlock().getState();
			if(sign.getLine(0).equals(Utils.Colorate("&3[Right Click]")) &&
					sign.getLine(1).equals(Utils.Colorate("Sell Resources")))
			{
=======
			Inventory inv = p.getInventory();
			Sign sign = (Sign) e.getClickedBlock().getState();
			if(sign.getLine(0).equals(Utils.Colorate("&3[Ores]")))
			{
				  int coal= 0;
				  int iron= 0;
				  int gold= 0;
				  int diamond= 0;
				  int emerald= 0;
>>>>>>> second-repo/master
				int points = 0;
		        for (int i = 0; i < 45; i++) {
		            if(i == 44)
		            {
		            	if(points > 0)
		            	{
			            	Main.shopmanager.addPoints(p, points);
				            p.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
				            p.sendMessage("");
				            Language.sendCentredMessage(p, Utils.Colorate("&3&lYou sold"));
				            if(coal > 0)
				            {
<<<<<<< HEAD
					            Language.sendCentredMessage(p, Utils.Colorate("&3Coal x" + coal + " (" + this.coal * coal + " points)" ));	
				            }
				            if(iron > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Iron Ore x" + iron + " (" + this.iron * iron + " points)" ));
				            }
				            if(gold > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Gold Ore x" + gold + " (" + this.gold * gold + " points)" ));
				            }
				            if(diamond > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Diamond x" + diamond + " (" + this.diamond * diamond + " points)" ));
				            }
				            if(emerald > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Emerald x" + emerald + " (" + this.emerald * emerald + " points)" ));
				            }
				            if(wheat > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Wheat x" + wheat + " (" + this.wheat * wheat + " points)" ));
				            }
				            if(red_mushroom > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Red Mushroom x" + red_mushroom + " (" + this.red_mushroom * red_mushroom + " points)" ));
				            }
				            if(brown_mushroom > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Brown Mushroom x" + brown_mushroom + " (" + this.brown_mushroom * brown_mushroom + " points)" ));
				            }
				            if(carrot > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Carrot x" + carrot + " (" + this.carrot * carrot + " points)" ));
				            }
				            if(potato > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Potato x" + potato + " (" + this.potato * potato + " points)" ));
				            }
				            if(poison_potato > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Poison Potato x" + poison_potato + " (" + this.poison_potato * poison_potato + " points)" ));
				            }
				            if(nether_wart > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Nether Wart x" + nether_wart + " (" + this.nether_wart * nether_wart + " points)" ));
				            }
				            Language.sendCentredMessage(p, Utils.Colorate("&3Total: " + points + " points"));
				            p.sendMessage("");
				            p.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));	
		            	}else {Language.sendDefaultMessage(p, "You have nothing to sell.");}
=======
					            Language.sendCentredMessage(p, Utils.Colorate("&3Coal x" + coal + " (" + this.coal * (coal / oreBulk) + " essence)" ));	
				            }
				            if(iron > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Iron Ore x" + iron + " (" + this.iron * (iron / oreBulk) + " essence)" ));
				            }
				            if(gold > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Gold Ore x" + gold + " (" + this.gold * (gold / oreBulk) + " essence)" ));
				            }
				            if(diamond > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Diamond x" + diamond + " (" + this.diamond * (diamond / oreBulk) + " essence)" ));
				            }
				            if(emerald > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Emerald x" + emerald + " (" + this.emerald * (emerald / oreBulk) + " essence)" ));
				            }
				            Language.sendCentredMessage(p, Utils.Colorate("&3Total: " + points + " essence"));
				            p.sendMessage("");
				            p.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));	
		            	}else {Language.sendDefaultMessage(p, "You have no ores to sell.");}
>>>>>>> second-repo/master
		            }
		            ItemStack is = inv.getItem(i);
		            if(is == null)
		            {
		            	continue;
		            }
<<<<<<< HEAD
		            Material mat = is.getType();
		            if(prices.containsKey(mat))
		            {
		            	points = points + (prices.get(mat) * is.getAmount());
		            	inv.setItem(i, new ItemStack(Material.AIR));
=======
		            if(is.getAmount() < oreBulk)
		            {
		            	continue;
		            }
		            int totalStackBulk = is.getAmount() % oreBulk;
		            Material mat = is.getType();
		            is.setAmount(is.getAmount() - totalStackBulk);
		            if(orePrices.containsKey(mat))
		            {
		            	points = points + (orePrices.get(mat) * (is.getAmount() / oreBulk));
		            	inv.setItem(i, new ItemStack(Material.AIR));
		            	inv.setItem(i, new ItemStack(mat, totalStackBulk ));
>>>>>>> second-repo/master
		            	
		            	switch(mat){
		                case COAL:
		                	coal = coal + is.getAmount();
		                    break;
		                case IRON_ORE:
		                	iron = iron + is.getAmount();
		                    break;
		                case GOLD_ORE:
		                	gold = gold + is.getAmount();
		                    break;
		                case DIAMOND:
		                	diamond = diamond + is.getAmount();;
		                    break;
		                case EMERALD:
		                	emerald = emerald + is.getAmount();;
		                    break;
<<<<<<< HEAD
=======
						default:
							break;

		            }
		            }
		        }
			}
			
			
			
			
			
			
			
			
			
			
			if(sign.getLine(0).equals(Utils.Colorate("&3[Produce]")))
			{
				  int carrot= 0;
				  int potato= 0;
				  int poison_potato= 0;
				  int wheat= 0;
				  int red_mushroom= 0;
				  int brown_mushroom= 0;
				  int nether_wart= 0;
				int points = 0;
		        for (int i = 0; i < 45; i++) {
		            if(i == 44)
		            {
		            	if(points > 0)
		            	{
			            	Main.shopmanager.addPoints(p, points);
				            p.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
				            p.sendMessage("");
				            Language.sendCentredMessage(p, Utils.Colorate("&3&lYou sold"));
				            if(wheat > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Wheat x" + wheat + " (" + this.wheat * ( wheat / produceBulk) + " essence)" ));
				            }
				            if(red_mushroom > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Red Mushroom x" + red_mushroom + " (" + this.red_mushroom * ( red_mushroom / produceBulk) + " essence)" ));
				            }
				            if(brown_mushroom > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Brown Mushroom x" + brown_mushroom + " (" + this.brown_mushroom * (brown_mushroom  /produceBulk )+ " essence)" ));
				            }
				            if(carrot > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Carrot x" + carrot + " (" + this.carrot * (carrot /produceBulk)+ " essence)" ));
				            }
				            if(potato > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Potato x" + potato + " (" + this.potato * (potato/produceBulk) + " essence)" ));
				            }
				            if(poison_potato > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Poison Potato x" + poison_potato + " (" + this.poison_potato * (poison_potato/produceBulk) + " essence)" ));
				            }
				            if(nether_wart > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Nether Wart x" + nether_wart + " (" + this.nether_wart * (nether_wart/produceBulk) + " essence)" ));
				            }
				            Language.sendCentredMessage(p, Utils.Colorate("&3Total: " + points + " essence"));
				            p.sendMessage("");
				            p.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));	
		            	}else {Language.sendDefaultMessage(p, "You need a stack of 4 produce to sell.");}
		            }
		            ItemStack is = inv.getItem(i);
		            if(is == null)
		            {
		            	continue;
		            }
		            if(is.getAmount() < produceBulk)
		            {
		            	continue;
		            }
		            int totalStackBulk = is.getAmount() % produceBulk;
		            Material mat = is.getType();
		            if(producePrices.containsKey(mat))
		            {
			            is.setAmount(is.getAmount() - totalStackBulk);
		            	points = points + (producePrices.get(mat) * (is.getAmount() / produceBulk));
		            	inv.setItem(i, new ItemStack(Material.AIR));
		            	inv.setItem(i, new ItemStack(mat, totalStackBulk ));
		            	
		            	switch(mat){
>>>>>>> second-repo/master
		                case WHEAT:
		                	wheat = wheat + is.getAmount();;
		                    break;
		                case RED_MUSHROOM:
		                	red_mushroom =red_mushroom + is.getAmount();;
		                    break;
		                case BROWN_MUSHROOM:
		                	brown_mushroom =brown_mushroom + is.getAmount();;
		                    break;
		                case CARROT_ITEM:
		                	carrot = carrot + is.getAmount();;
		                    break;
		                case POTATO_ITEM:
		                	potato = potato + is.getAmount();;
		                    break;
		                case POISONOUS_POTATO:
		                	poison_potato = poison_potato + is.getAmount();;
		                    break;
		                case NETHER_STALK:
		                	nether_wart = nether_wart + is.getAmount();;
		                    break;
						default:
							break;

		            }
		            }
		        }
			}
<<<<<<< HEAD
				
		}
=======
			
			
			
				
		}
		
		
		
		
		//LEFT CLICK ORES
		if(e.getAction() == Action.LEFT_CLICK_BLOCK && e.getClickedBlock().getState() instanceof Sign)
		{
			Player p = e.getPlayer();
			Sign sign = (Sign) e.getClickedBlock().getState();
			PlayerInventory inv = p.getInventory();
			if(sign.getLine(0).equals(Utils.Colorate("&3[Ores]")))
			{
				  
		            ItemStack is = inv.getItem(inv.getHeldItemSlot());
		            if(is == null || is.getAmount() < oreBulk)
		            {
		            	Language.sendDefaultMessage(p, "You have no ores to sell.");
		            	return;
		            }
		            Material mat = is.getType();
		            if(!orePrices.containsKey(mat))
		            {
		            	Language.sendDefaultMessage(p, "You have no ores to sell.");
		            	return;
		            }
					  int coal= 0;
					  int iron= 0;
					  int gold= 0;
					  int diamond= 0;
					  int emerald= 0;
					  int points = 0;
		            int totalStackBulk = is.getAmount() % oreBulk;
		            is.setAmount(is.getAmount() - totalStackBulk);
		            	points = points + (orePrices.get(mat) * (is.getAmount() / oreBulk));
		            	inv.setItem(inv.getHeldItemSlot(), new ItemStack(Material.AIR));
		            	inv.setItem(inv.getHeldItemSlot(), new ItemStack(mat, totalStackBulk ));
		            	
		            	switch(mat){
		                case COAL:
		                	coal = coal + is.getAmount();
		                    break;
		                case IRON_ORE:
		                	iron = iron + is.getAmount();
		                    break;
		                case GOLD_ORE:
		                	gold = gold + is.getAmount();
		                    break;
		                case DIAMOND:
		                	diamond = diamond + is.getAmount();;
		                    break;
		                case EMERALD:
		                	emerald = emerald + is.getAmount();;
		                    break;
						default:
							break;

		            }

				  
				  
		            	if(points > 0)
		            	{
			            	Main.shopmanager.addPoints(p, points);
				            p.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
				            p.sendMessage("");
				            Language.sendCentredMessage(p, Utils.Colorate("&3&lYou sold"));
				            if(coal > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Coal x" + coal + " (" + this.coal * (coal / oreBulk) + " essence)" ));	
				            }
				            if(iron > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Iron Ore x" + iron + " (" + this.iron * (iron / oreBulk) + " essence)" ));
				            }
				            if(gold > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Gold Ore x" + gold + " (" + this.gold * (gold / oreBulk) + " essence)" ));
				            }
				            if(diamond > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Diamond x" + diamond + " (" + this.diamond * (diamond / oreBulk) + " essence)" ));
				            }
				            if(emerald > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Emerald x" + emerald + " (" + this.emerald * (emerald / oreBulk) + " essence)" ));
				            }
				            p.sendMessage("");
				            p.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));	
		            	}else {Language.sendDefaultMessage(p, "You have no ores to sell.");}
		            }
		}
		
		
		
		
		
		
		
		//LEFT CLICK PRODUCE
		if(e.getAction() == Action.LEFT_CLICK_BLOCK && e.getClickedBlock().getState() instanceof Sign)
		{
			Player p = e.getPlayer();
			Sign sign = (Sign) e.getClickedBlock().getState();
			PlayerInventory inv = p.getInventory();
			if(sign.getLine(0).equals(Utils.Colorate("&3[Produce]")))
			{
				  
		            ItemStack is = inv.getItem(inv.getHeldItemSlot());
		            if(is == null || is.getAmount() < produceBulk)
		            {
		            	Language.sendDefaultMessage(p, "You need a stack of 4 produce to sell.");
		            	return;
		            }
		            Material mat = is.getType();
		            if(!producePrices.containsKey(mat))
		            {
		            	Language.sendDefaultMessage(p, "You need a stack of 4 produce to sell.");
		            	return;
		            }
					  int carrot= 0;
					  int potato= 0;
					  int poison_potato= 0;
					  int wheat= 0;
					  int red_mushroom= 0;
					  int brown_mushroom= 0;
					  int nether_wart= 0;
					  int points = 0;
		            int totalStackBulk = is.getAmount() % produceBulk;
		            is.setAmount(is.getAmount() - totalStackBulk);
		            	points = points + (producePrices.get(mat) * (is.getAmount() / produceBulk));
		            	inv.setItem(inv.getHeldItemSlot(), new ItemStack(Material.AIR));
		            	inv.setItem(inv.getHeldItemSlot(), new ItemStack(mat, totalStackBulk ));
		            	
		            	switch(mat){
		                case WHEAT:
		                	wheat = wheat + is.getAmount();;
		                    break;
		                case RED_MUSHROOM:
		                	red_mushroom =red_mushroom + is.getAmount();;
		                    break;
		                case BROWN_MUSHROOM:
		                	brown_mushroom =brown_mushroom + is.getAmount();;
		                    break;
		                case CARROT_ITEM:
		                	carrot = carrot + is.getAmount();;
		                    break;
		                case POTATO_ITEM:
		                	potato = potato + is.getAmount();;
		                    break;
		                case POISONOUS_POTATO:
		                	poison_potato = poison_potato + is.getAmount();;
		                    break;
		                case NETHER_STALK:
		                	nether_wart = nether_wart + is.getAmount();;
		                    break;
						default:
							break;

		            }

				  
				  
		            	if(points > 0)
		            	{
			            	Main.shopmanager.addPoints(p, points);
				            p.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
				            p.sendMessage("");
				            Language.sendCentredMessage(p, Utils.Colorate("&3&lYou sold"));
				            if(wheat > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Wheat x" + wheat + " (" + this.wheat * ( wheat / produceBulk) + " essence)" ));
				            }
				            if(red_mushroom > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Red Mushroom x" + red_mushroom + " (" + this.red_mushroom * ( red_mushroom / produceBulk) + " essence)" ));
				            }
				            if(brown_mushroom > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Brown Mushroom x" + brown_mushroom + " (" + this.brown_mushroom * (brown_mushroom  /produceBulk )+ " essence)" ));
				            }
				            if(carrot > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Carrot x" + carrot + " (" + this.carrot * (carrot /produceBulk)+ " essence)" ));
				            }
				            if(potato > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Potato x" + potato + " (" + this.potato * (potato/produceBulk) + " essence)" ));
				            }
				            if(poison_potato > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Poison Potato x" + poison_potato + " (" + this.poison_potato * (poison_potato/produceBulk) + " essence)" ));
				            }
				            if(nether_wart > 0)
				            {
					            Language.sendCentredMessage(p, Utils.Colorate("&3Nether Wart x" + nether_wart + " (" + this.nether_wart * (nether_wart/produceBulk) + " essence)" ));
				            }
				            p.sendMessage("");
				            p.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));	
		            	}else {Language.sendDefaultMessage(p, "You need a stack of 4 produce to sell.");}
		            }
		}
		
		
		
		
>>>>>>> second-repo/master
	}
	
	
	
	
	
}
