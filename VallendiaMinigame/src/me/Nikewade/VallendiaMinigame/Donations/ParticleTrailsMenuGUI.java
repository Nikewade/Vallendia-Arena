package me.Nikewade.VallendiaMinigame.Donations;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.Commands.DonateCommand;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import me.kvq.supertrailspro.SuperTrails;
import me.kvq.supertrailspro.API.SuperTrailsAPI;

public class ParticleTrailsMenuGUI {	
	public static void openDefaultMenu(Player p) {
		
	AdvInventory defaultMenu = new AdvInventory(Utils.Colorate("&9&lParticle Trails"), 54, Utils.placeholder((byte) 15, " "));
	
	for(int i = 10; i < 45 ; i++)
	{
		defaultMenu.setItem(new ItemStack(Material.AIR), i);
	}
	
	 defaultMenu.setItem(new ItemStack(Material.ANVIL), Utils.Colorate("&8Circle &7< &f&lDefault &7> &8Spiral"), 4, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();		
		    	if(e.getClick() == ClickType.LEFT)
		    	{
		    		if(ep.hasPermission("trails.mode.Circle"))
		    		{
		    			SuperTrailsAPI.getPlayerData(ep).setMode(1);
		    		}
		    		openCirclemenu(ep);
		    	}
		    	if(e.getClick() == ClickType.RIGHT)
		    	{
		    		if(ep.hasPermission("trails.mode.Spiral"))
		    		{
		    			SuperTrailsAPI.getPlayerData(ep).setMode(4);

		    		}
		    		openSpiralmenu(ep);
		    	}

		    }
		});
	
	 defaultMenu.setItem(new ItemStack(Material.ARROW), Utils.Colorate("&8<< Back"), 45, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();			    	
		    	DonateCommand.openCosmeticMainMenu(ep);
		    }
		});
	 	 
	 defaultMenu.setItem(new ItemStack(159,1,(short) 14), Utils.Colorate("&4Remove your trail"), 49, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();			    	
		    	SuperTrailsAPI.getPlayerData(ep).setTrail(0);

		    }
		});
	 
	 String damagehearts = Utils.Colorate("&8&lDamage Hearts");
	 if(p.hasPermission("trails.particles.Damage"))
	 {
		 damagehearts = Utils.Colorate("&6&lDamage Hearts");
	 }
	 
	 defaultMenu.setItem(new ItemStack(Material.FERMENTED_SPIDER_EYE), damagehearts, 9, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Damage"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(26);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String angry = Utils.Colorate("&8&lAngry Villager");
	 if(p.hasPermission("trails.particles.Angry"))
	 {
		 angry = Utils.Colorate("&6&lAngry Villager");
	 }
	  
	 defaultMenu.setItem(new ItemStack(Material.BLAZE_POWDER), angry, 10, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Angry"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(2);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String magic = Utils.Colorate("&8&lMagic Crit");
	 if(p.hasPermission("trails.particles.Magic"))
	 {
		 magic = Utils.Colorate("&6&lMagic Crit");
	 }
	  
	 defaultMenu.setItem(new ItemStack(Material.BOOK), magic, 11, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Magic"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(3);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String rainbow = Utils.Colorate("&8&lRainbow");
	 if(p.hasPermission("trails.particles.Rainbow"))
	 {
		 rainbow = Utils.Colorate("&6&lRainbow");
	 }
	  
	 defaultMenu.setItem(new ItemStack(Material.REDSTONE), rainbow, 12, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.blocks.Rainbow"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(4);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String cloud = Utils.Colorate("&8&lCloud");
	 if(p.hasPermission("trails.particles.Cloud"))
	 {
		 cloud = Utils.Colorate("&6&lCloud");
	 }
	 
	 defaultMenu.setItem(new ItemStack(Material.QUARTZ), cloud, 13, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Cloud"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(5);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String witch = Utils.Colorate("&8&lWitch");
	 if(p.hasPermission("trails.particles.Witch"))
	 {
		 witch = Utils.Colorate("&6&lWitch");
	 }
	 
	 defaultMenu.setItem(new ItemStack(Material.GLASS_BOTTLE), witch, 14, new ClickRunnable() {
		 
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Witch"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(6);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String ender = Utils.Colorate("&8&lEnder");
	 if(p.hasPermission("trails.particles.Ender"))
	 {
		 ender = Utils.Colorate("&6&lEnder");
	 }
	 
	 defaultMenu.setItem(new ItemStack(Material.ENDER_PEARL), ender, 15, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Ender"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(7);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String green = Utils.Colorate("&8&lHappy Villager");
	 if(p.hasPermission("trails.particles.Green"))
	 {
		 green = Utils.Colorate("&6&lHappy Villager");
	 }
	 
	 defaultMenu.setItem(new ItemStack(Material.EMERALD), green, 16, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Green"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(8);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		}); 
	 
	 String sparks = Utils.Colorate("&8&lSparks");
	 if(p.hasPermission("trails.particles.Sparks"))
	 {
		 sparks = Utils.Colorate("&6&lSparks");
	 }
	 
	 defaultMenu.setItem(new ItemStack(Material.FIREWORK), sparks, 17, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Sparks"))
		    	{
		    		//white magic
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(11);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);
		    	
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String flames = Utils.Colorate("&8&lFlames");
	 if(p.hasPermission("trails.particles.Flames"))
	 {
		 flames = Utils.Colorate("&6&lFlames");
	 }
	 
	 defaultMenu.setItem(new ItemStack(Material.FLINT_AND_STEEL), flames, 18, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Flames"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(10);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String notes = Utils.Colorate("&8&lNotes");
	 if(p.hasPermission("trails.particles.Notes"))
	 {
		 notes = Utils.Colorate("&6&lNotes");
	 }
	 
	 
	 defaultMenu.setItem(new ItemStack(2258, 1), notes, 19, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Notes"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(12);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);
		    	
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String snow = Utils.Colorate("&8&lSnow");
	 if(p.hasPermission("trails.particles.Snow"))
	 {
		 snow = Utils.Colorate("&6&lSnow");
	 }
	 
	 defaultMenu.setItem(new ItemStack(Material.SNOW_BLOCK), snow, 20, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Snow"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(13);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String rain = Utils.Colorate("&8&lRain");
	 if(p.hasPermission("trails.particles.Rain"))
	 {
		 rain = Utils.Colorate("&6&lRain");
	 }
	 
	 defaultMenu.setItem(new ItemStack(Material.WATER_BUCKET), rain, 21, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Rain"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(14);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String lava = Utils.Colorate("&8&lLava Drip");
	 if(p.hasPermission("trails.particles.Lava"))
	 {
		 lava = Utils.Colorate("&6&lLava Drip");
	 }
	 defaultMenu.setItem(new ItemStack(Material.LAVA_BUCKET), lava, 22, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Lava"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(15);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String crit = Utils.Colorate("&8&lCrit");
	 if(p.hasPermission("trails.particles.Crit"))
	 {
		 crit = Utils.Colorate("&6&lCrit");
	 }
	 
	 defaultMenu.setItem(new ItemStack(Material.IRON_SWORD), crit , 23, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Crit"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(16);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String smoke = Utils.Colorate("&8&lSmoke");
	 if(p.hasPermission("trails.particles.Smoke"))
	 {
		 smoke = Utils.Colorate("&6&lSmoke");
	 }
	 defaultMenu.setItem(new ItemStack(Material.COAL_BLOCK), smoke, 24, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Smoke"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(17);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String endrod = Utils.Colorate("&8&lEnd Rod");
	 if(p.hasPermission("trails.particles.EndRod"))
	 {
		 endrod = Utils.Colorate("&6&lEnd Rod");
	 }
	 
	 defaultMenu.setItem(new ItemStack(Material.END_ROD), endrod, 25, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.EndRod"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(27);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String totem = Utils.Colorate("&8&lTotem");
	 if(p.hasPermission("trails.particles.Totem"))
	 {
		 totem = Utils.Colorate("&6&lTotem");
	 }
	 
	 
	 defaultMenu.setItem(new ItemStack(Material.TOTEM), totem, 26, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Totem"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(28);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String splash = Utils.Colorate("&8&lSplash");
	 if(p.hasPermission("trails.particles.Splash"))
	 {
		 splash = Utils.Colorate("&6&lSplash");
	 }
	 
	 defaultMenu.setItem(new ItemStack(373, 1), splash, 27, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Splash"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(20);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String slime = Utils.Colorate("&8&lSlime");
	 if(p.hasPermission("trails.particles.Slime"))
	 {
		 slime = Utils.Colorate("&6&lSlime");
	 }
	 
	 defaultMenu.setItem(new ItemStack(Material.SLIME_BALL), slime, 28, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Slime"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(21);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String snowball = Utils.Colorate("&8&lSnowball");
	 if(p.hasPermission("trails.particles.Snowball"))
	 {
		 snowball = Utils.Colorate("&6&lSnowball");
	 }
	 
	 
	 defaultMenu.setItem(new ItemStack(Material.SNOW_BALL), snowball, 29, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Snowball"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(22);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String voids = Utils.Colorate("&8&lVoid");
	 if(p.hasPermission("trails.particles.Void"))
	 {
		 voids = Utils.Colorate("&6&lVoid");
	 }
	 
	 defaultMenu.setItem(new ItemStack(Material.ENDER_STONE), voids, 29, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Void"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(23);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(0);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});

	 
	 defaultMenu.openInventory(p);
}
	
	public static void openCirclemenu(Player p) {
		
	AdvInventory circleMenu = new AdvInventory(Utils.Colorate("&9&lParticle Trails"), 54, Utils.placeholder((byte) 15, " "));
	
	for(int i = 10; i < 45 ; i++)
	{
		circleMenu.setItem(new ItemStack(Material.AIR), i);
	}
	
	 circleMenu.setItem(new ItemStack(Material.ANVIL), Utils.Colorate("&8Pulse &7< &f&lCircle &7> &8Default"), 4, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();		
		    	if(e.getClick() == ClickType.LEFT)
		    	{
		    		if(ep.hasPermission("trails.mode.Pulse"))
		    		{
		    			SuperTrailsAPI.getPlayerData(ep).setMode(2);

		    		}
		    		openPulsemenu(ep);
		    	}
		    	if(e.getClick() == ClickType.RIGHT)
		    	{
		    		SuperTrailsAPI.getPlayerData(ep).setMode(0);

		    		openDefaultMenu(ep);
		    	}
		    	
		    }
		});
	
	 circleMenu.setItem(new ItemStack(Material.ARROW), Utils.Colorate("&8<< Back"), 45, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();			    	
		    	DonateCommand.openCosmeticMainMenu(ep);
		    }
		});
	 
	 circleMenu.setItem(new ItemStack(159,1,(short) 14), Utils.Colorate("&4Remove your trail"), 49, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();			    	
		    	SuperTrailsAPI.getPlayerData(ep).setTrail(0);

		    }
		});
	 
	 String damagehearts = Utils.Colorate("&8&lDamage Hearts");
	 if(p.hasPermission("trails.particles.Damage"))
	 {
		 damagehearts = Utils.Colorate("&6&lDamage Hearts");
	 }
	 
	 circleMenu.setItem(new ItemStack(Material.FERMENTED_SPIDER_EYE), damagehearts, 9, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Damage") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Damage") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(26);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}
		    }
		});
	 
	 String angry = Utils.Colorate("&8&lAngry Villager");
	 if(p.hasPermission("trails.particles.Angry"))
	 {
		 angry = Utils.Colorate("&6&lAngry Villager");
	 }
	  
	 circleMenu.setItem(new ItemStack(Material.BLAZE_POWDER), angry, 10, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Angry") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Angry") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(2);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String magic = Utils.Colorate("&8&lMagic Crit");
	 if(p.hasPermission("trails.particles.Magic"))
	 {
		 magic = Utils.Colorate("&6&lMagic Crit");
	 }
	  
	 circleMenu.setItem(new ItemStack(Material.BOOK), magic, 11, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Magic") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Magic") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(3);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	  
	 String rainbow = Utils.Colorate("&8&lRainbow");
	 if(p.hasPermission("trails.particles.Rainbow"))
	 {
		 rainbow = Utils.Colorate("&6&lRainbow");
	 }
	 
	 circleMenu.setItem(new ItemStack(Material.REDSTONE), rainbow, 12, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.blocks.Rainbow") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.blocks.Rainbow") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(4);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String cloud = Utils.Colorate("&8&lCloud");
	 if(p.hasPermission("trails.particles.Cloud"))
	 {
		 cloud = Utils.Colorate("&6&lCloud");
		 
	 }
	 circleMenu.setItem(new ItemStack(Material.QUARTZ), cloud, 13, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Cloud") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Cloud") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(5);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String witch = Utils.Colorate("&8&lWitch");
	 if(p.hasPermission("trails.particles.Witch"))
	 {
		 witch = Utils.Colorate("&6&lWitch");
	 }
	 
	 circleMenu.setItem(new ItemStack(Material.GLASS_BOTTLE), witch, 14, new ClickRunnable() {
		 
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Witch") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Witch") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(6);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String ender = Utils.Colorate("&8&lEnder");
	 if(p.hasPermission("trails.particles.Ender"))
	 {
		 ender = Utils.Colorate("&6&lEnder");
	 }
	 
	 circleMenu.setItem(new ItemStack(Material.ENDER_PEARL), ender, 15, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Ender") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Ender") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(7);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String green = Utils.Colorate("&8&lHappy Villager");
	 if(p.hasPermission("trails.particles.Green"))
	 {
		 green = Utils.Colorate("&6&lHappy Villager");
	 }
	 circleMenu.setItem(new ItemStack(Material.EMERALD), green, 16, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Green") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Green") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(8);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		}); 
	 
	 String sparks = Utils.Colorate("&8&lSparks");
	 if(p.hasPermission("trails.particles.Sparks"))
	 {
		 sparks = Utils.Colorate("&6&lSparks");
	 }
	 
	 circleMenu.setItem(new ItemStack(Material.FIREWORK), sparks, 17, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Sparks") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Sparks") && ep.hasPermission("trails.mode.Circle"))
		    	{
		    		//white magic
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(11);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String flames = Utils.Colorate("&8&lFlames");
	 if(p.hasPermission("trails.particles.Flames"))
	 {
		 flames = Utils.Colorate("&6&lFlames");
	 }
	 
	 circleMenu.setItem(new ItemStack(Material.FLINT_AND_STEEL), flames, 18, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Flames") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Flames") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(10);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String notes = Utils.Colorate("&8&lNotes");
	 if(p.hasPermission("trails.particles.Notes"))
	 {
		 notes = Utils.Colorate("&6&lNotes");
	 }
	 circleMenu.setItem(new ItemStack(2258, 1), notes, 19, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Notes") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Notes") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(12);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String snow = Utils.Colorate("&8&lSnow");
	 if(p.hasPermission("trails.particles.Snow"))
	 {
		 snow = Utils.Colorate("&6&lSnow");
	 }
	 circleMenu.setItem(new ItemStack(Material.SNOW_BLOCK), snow, 20, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Snow") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Snow") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(13);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String rain = Utils.Colorate("&8&lRain");
	 if(p.hasPermission("trails.particles.Rain"))
	 {
		 rain = Utils.Colorate("&6&lRain");
	 }
	 
	 circleMenu.setItem(new ItemStack(Material.WATER_BUCKET), rain, 21, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Rain") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Rain") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(14);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String lava = Utils.Colorate("&8&lLava Drip");
	 if(p.hasPermission("trails.particles.Lava"))
	 {
		 lava = Utils.Colorate("&6&lLava Drip");
	 }
	 circleMenu.setItem(new ItemStack(Material.LAVA_BUCKET), lava, 22, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Lava") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Lava") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(15);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String crit = Utils.Colorate("&8&lCrit");
	 if(p.hasPermission("trails.particles.Crit"))
	 {
		 crit = Utils.Colorate("&6&lCrit");
	 }
	 
	 circleMenu.setItem(new ItemStack(Material.IRON_SWORD), crit, 23, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Crit") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Crit") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(16);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String smoke = Utils.Colorate("&8&lSmoke");
	 if(p.hasPermission("trails.particles.Smoke"))
	 {
		 smoke = Utils.Colorate("&6&lSmoke");
	 }
	 circleMenu.setItem(new ItemStack(Material.COAL_BLOCK), smoke, 24, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Smoke") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Smoke") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(17);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String endrod = Utils.Colorate("&8&lEnd Rod");
	 if(p.hasPermission("trails.particles.EndRod"))
	 {
		 endrod = Utils.Colorate("&6&lEnd Rod");
	 }
	 
	 circleMenu.setItem(new ItemStack(Material.END_ROD), endrod, 25, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.EndRod") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.EndRod") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(27);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String totem = Utils.Colorate("&8&lTotem");
	 if(p.hasPermission("trails.particles.Totem"))
	 {
		 totem = Utils.Colorate("&6&lTotem");
	 }
	 
	 circleMenu.setItem(new ItemStack(Material.TOTEM), totem, 26, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Totem") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Totem") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(28);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String splash = Utils.Colorate("&8&lSplash");
	 if(p.hasPermission("trails.particles.Splash"))
	 {
		 splash = Utils.Colorate("&6&lSplash");
	 }
	 
	 circleMenu.setItem(new ItemStack(373, 1), splash, 27, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Splash") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Splash") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(20);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String slime = Utils.Colorate("&8&lSlime");
	 if(p.hasPermission("trails.particles.Slime"))
	 {
		 slime = Utils.Colorate("&6&lSlime");
	 }
	 
	 circleMenu.setItem(new ItemStack(Material.SLIME_BALL), slime, 28, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Slime") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Slime") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(21);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String snowball = Utils.Colorate("&8&lSnowball");
	 if(p.hasPermission("trails.particles.Snowball"))
	 {
		 snowball = Utils.Colorate("&6&lSnowball");
	 }
	 
	 circleMenu.setItem(new ItemStack(Material.SNOW_BALL), snowball, 29, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Snowball") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Snowball") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(22);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String voids = Utils.Colorate("&8&lVoid");
	 if(p.hasPermission("trails.particles.Void"))
	 {
		 voids = Utils.Colorate("&6&lVoid");
	 }
	 
	 circleMenu.setItem(new ItemStack(Material.ENDER_STONE), voids, 29, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Void") && !ep.hasPermission("trails.mode.Circle"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Circle mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Void") && ep.hasPermission("trails.mode.Circle"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(23);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(1);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});

	 
	 circleMenu.openInventory(p);
}
	
	public static void openPulsemenu(Player p) {
		
	AdvInventory pulseMenu = new AdvInventory(Utils.Colorate("&9&lParticle Trails"), 54, Utils.placeholder((byte) 15, " "));
	
	for(int i = 10; i < 45 ; i++)
	{
		pulseMenu.setItem(new ItemStack(Material.AIR), i);
	}
	
	 pulseMenu.setItem(new ItemStack(Material.ANVIL), Utils.Colorate("&8Spiral &7< &f&lPulse &7> &8Circle"), 4, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();	
		    	if(e.getClick() == ClickType.LEFT)
		    	{
		    		if(ep.hasPermission("trails.mode.Spiral"))
		    		{
		    			SuperTrailsAPI.getPlayerData(ep).setMode(4);

		    		}
		    		openSpiralmenu(ep);
		    	}
		    	if(e.getClick() == ClickType.RIGHT)
		    	{
		    		if(ep.hasPermission("trails.mode.Circle"))
		    		{
		    			SuperTrailsAPI.getPlayerData(ep).setMode(1);

		    		}
		    		openCirclemenu(ep);
		    	}
		    	
		    }
		});
	
	 pulseMenu.setItem(new ItemStack(Material.ARROW), Utils.Colorate("&8<< Back"), 45, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();			    	
		    	DonateCommand.openCosmeticMainMenu(ep);
		    }
		});
	 
	 pulseMenu.setItem(new ItemStack(159,1,(short) 14), Utils.Colorate("&4Remove your trail"), 49, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();			    	
		    	SuperTrailsAPI.getPlayerData(ep).setTrail(0);

		    }
		});
	 
	 String damagehearts = Utils.Colorate("&8&lDamage Hearts");
	 if(p.hasPermission("trails.particles.Damage"))
	 {
		 damagehearts = Utils.Colorate("&6&lDamage Hearts");
	 }
	 
	 pulseMenu.setItem(new ItemStack(Material.FERMENTED_SPIDER_EYE), damagehearts, 9, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Damage") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Damage") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(26);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}
		    }
		});
	 
	 String angry = Utils.Colorate("&8&lAngry Villager");
	 if(p.hasPermission("trails.particles.Angry"))
	 {
		 angry = Utils.Colorate("&6&lAngry Villager");
	 }
	  
	 pulseMenu.setItem(new ItemStack(Material.BLAZE_POWDER), angry, 10, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Angry") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Angry") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(2);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String magic = Utils.Colorate("&8&lMagic Crit");
	 if(p.hasPermission("trails.particles.Magic"))
	 {
		 magic = Utils.Colorate("&6&lMagic Crit");
	 }
	  
	 pulseMenu.setItem(new ItemStack(Material.BOOK), magic, 11, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Magic") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Magic") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(3);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	  
	 String rainbow = Utils.Colorate("&8&lRainbow");
	 if(p.hasPermission("trails.particles.Rainbow"))
	 {
		 rainbow = Utils.Colorate("&6&lRainbow");
	 }
	 
	 pulseMenu.setItem(new ItemStack(Material.REDSTONE), rainbow, 12, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Rainbow") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.blocks.Rainbow") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(4);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String cloud = Utils.Colorate("&8&lCloud");
	 if(p.hasPermission("trails.particles.Cloud"))
	 {
		 cloud = Utils.Colorate("&6&lCloud");
	 }
	 
	 pulseMenu.setItem(new ItemStack(Material.QUARTZ),cloud, 13, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Cloud") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Cloud") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(5);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);
		    
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String witch = Utils.Colorate("&8&lWitch");
	 if(p.hasPermission("trails.particles.Witch"))
	 {
		 witch = Utils.Colorate("&6&lWitch");
	 }
	 
	 pulseMenu.setItem(new ItemStack(Material.GLASS_BOTTLE), witch, 14, new ClickRunnable() {
		 
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Witch") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Witch") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(6);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String ender = Utils.Colorate("&8&lEnder");
	 if(p.hasPermission("trails.particles.Ender"))
	 {
		 ender = Utils.Colorate("&6&lEnder");
	 }
	 
	 pulseMenu.setItem(new ItemStack(Material.ENDER_PEARL), ender, 15, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Ender") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Ender") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(7);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String green = Utils.Colorate("&8&lHappy Villager");
	 if(p.hasPermission("trails.particles.Green"))
	 {
		 green = Utils.Colorate("&6&lHappy Villager");
	 }
	 
	 pulseMenu.setItem(new ItemStack(Material.EMERALD), green, 16, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Green") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Green") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(8);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		}); 
	 String sparks = Utils.Colorate("&8&lSparks");
	 if(p.hasPermission("trails.particles.Sparks"))
	 {
		 sparks = Utils.Colorate("&6&lSparks");
	 }
	 pulseMenu.setItem(new ItemStack(Material.FIREWORK), sparks, 17, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Sparks") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Sparks") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		//white magic
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(11);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		       	 }else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String flames = Utils.Colorate("&8&lFlames");
	 if(p.hasPermission("trails.particles.Flames"))
	 {
		 flames = Utils.Colorate("&6&lFlames");
	 }
	 
	 pulseMenu.setItem(new ItemStack(Material.FLINT_AND_STEEL), flames, 18, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Flames") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Flames") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(10);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String notes = Utils.Colorate("&8&lNotes");
	 if(p.hasPermission("trails.particles.Notes"))
	 {
		 notes = Utils.Colorate("&6&lNotes");
	 }
	 pulseMenu.setItem(new ItemStack(2258, 1), notes, 19, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Notes") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Notes") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(12);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String snow = Utils.Colorate("&8&lSnow");
	 if(p.hasPermission("trails.particles.Snow"))
	 {
		 snow = Utils.Colorate("&6&lSnow");
	 }
	 pulseMenu.setItem(new ItemStack(Material.SNOW_BLOCK), snow, 20, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Snow") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Snow") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(13);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String rain = Utils.Colorate("&8&lRain");
	 if(p.hasPermission("trails.particles.Rain"))
	 {
		 rain = Utils.Colorate("&6&lRain");
	 }
	 
	 pulseMenu.setItem(new ItemStack(Material.WATER_BUCKET), rain, 21, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Rain") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Rain") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(14);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String lava = Utils.Colorate("&8&lLava Drip");
	 if(p.hasPermission("trails.particles.Lava"))
	 {
		 lava = Utils.Colorate("&6&lLava Drip");
	 }
	 
	 pulseMenu.setItem(new ItemStack(Material.LAVA_BUCKET), lava, 22, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Lava") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Lava") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(15);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String crit = Utils.Colorate("&8&lCrit");
	 if(p.hasPermission("trails.particles.Crit"))
	 {
		 crit = Utils.Colorate("&6&lCrit");
	 }
	 pulseMenu.setItem(new ItemStack(Material.IRON_SWORD), crit, 23, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Crit") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Crit") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(16);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String smoke = Utils.Colorate("&8&lSmoke");
	 if(p.hasPermission("trails.particles.Smoke"))
	 {
		 smoke = Utils.Colorate("&6&lSmoke");
	 }
	 
	 pulseMenu.setItem(new ItemStack(Material.COAL_BLOCK), smoke, 24, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Smoke") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Smoke") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(17);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String endrod = Utils.Colorate("&8&lEnd Rod");
	 if(p.hasPermission("trails.particles.EndRod"))
	 {
		 endrod = Utils.Colorate("&6&lEnd Rod");
	 }
	 
	 pulseMenu.setItem(new ItemStack(Material.END_ROD), endrod, 25, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.EndRod") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.EndRod") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(27);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String totem = Utils.Colorate("&8&lTotem");
	 if(p.hasPermission("trails.particles.Totem"))
	 {
		 totem = Utils.Colorate("&6&lTotem");
	 }
	 
	 pulseMenu.setItem(new ItemStack(Material.TOTEM), totem, 26, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Totem") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Totem") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(28);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String splash = Utils.Colorate("&8&lSplash");
	 if(p.hasPermission("trails.particles.Splash"))
	 {
		 splash = Utils.Colorate("&6&lSplash");
	 }
	 
	 pulseMenu.setItem(new ItemStack(373, 1), splash, 27, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Splash") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Splash") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(20);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String slime = Utils.Colorate("&8&lSlime");
	 if(p.hasPermission("trails.particles.Slime"))
	 {
		 slime = Utils.Colorate("&6&lSlime");
	 }
	 
	 pulseMenu.setItem(new ItemStack(Material.SLIME_BALL), slime, 28, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Slime") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Slime") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(21);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String snowball = Utils.Colorate("&8&lSnowball");
	 if(p.hasPermission("trails.particles.Snowball"))
	 {
		 snowball = Utils.Colorate("&6&lSnowball");
	 }
	 
	 pulseMenu.setItem(new ItemStack(Material.SNOW_BALL), snowball, 29, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Snowball") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Snowball") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(22);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);
		    
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String voids = Utils.Colorate("&8&lVoid");
	 if(p.hasPermission("trails.particles.Void"))
	 {
		 voids = Utils.Colorate("&6&lVoid");
	 }
	 
	 pulseMenu.setItem(new ItemStack(Material.ENDER_STONE), voids, 29, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Void") && !ep.hasPermission("trails.mode.Pulse"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Pulse mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Void") && ep.hasPermission("trails.mode.Pulse"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(23);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(2);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});

	 
	 pulseMenu.openInventory(p);
}
	
	public static void openSpiralmenu(Player p) {
		
	AdvInventory spiralMenu = new AdvInventory(Utils.Colorate("&9&lParticle Trails"), 54, Utils.placeholder((byte) 15, " "));
	
	for(int i = 10; i < 45 ; i++)
	{
		spiralMenu.setItem(new ItemStack(Material.AIR), i);
	}
	
	 spiralMenu.setItem(new ItemStack(Material.ANVIL), Utils.Colorate("&8Default &7< &f&lSpiral &7> &8Pulse"), 4, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();		
		    	if(e.getClick() == ClickType.LEFT)
		    	{
		    			SuperTrailsAPI.getPlayerData(ep).setMode(0);

		    		openDefaultMenu(ep);
		    	}
		    	if(e.getClick() == ClickType.RIGHT)
		    	{
		    		if(ep.hasPermission("trails.mode.Pulse"))
		    		{
		    			SuperTrailsAPI.getPlayerData(ep).setMode(2);

		    		}
		    		openPulsemenu(ep);
		    	}
		    	
		    }
		});
	
	 spiralMenu.setItem(new ItemStack(Material.ARROW), Utils.Colorate("&8<< Back"), 45, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();			    	
		    	DonateCommand.openCosmeticMainMenu(ep);
		    }
		});
	 
	 spiralMenu.setItem(new ItemStack(159,1,(short) 14), Utils.Colorate("&4Remove your trail"), 49, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();			    	
		    	SuperTrailsAPI.getPlayerData(ep).setTrail(0);

		    }
		});
	 
	 String damagehearts = Utils.Colorate("&8&lDamage Hearts");
	 if(p.hasPermission("trails.particles.Damage"))
	 {
		 damagehearts = Utils.Colorate("&6&lDamage Hearts");
	 }
	 
	 spiralMenu.setItem(new ItemStack(Material.FERMENTED_SPIDER_EYE), damagehearts, 9, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Damage") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Damage") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(26);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}
		    }
		});
	 
	 String angry = Utils.Colorate("&8&lAngry Villager");
	 if(p.hasPermission("trails.particles.Angry"))
	 {
		 angry = Utils.Colorate("&6&lAngry Villager");
	 }
	  
	 spiralMenu.setItem(new ItemStack(Material.BLAZE_POWDER), angry, 10, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Angry") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Angry") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(2);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String magic = Utils.Colorate("&8&lMagic Crit");
	 if(p.hasPermission("trails.particles.Magic"))
	 {
		 magic = Utils.Colorate("&6&lMagic Crit");
	 }
	  
	 spiralMenu.setItem(new ItemStack(Material.BOOK), magic, 11, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Magic") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Magic") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(3);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	  
	 String rainbow = Utils.Colorate("&8&lRainbow");
	 if(p.hasPermission("trails.particles.Rainbow"))
	 {
		 rainbow = Utils.Colorate("&6&lRainbow");
	 }
	 
	 spiralMenu.setItem(new ItemStack(Material.REDSTONE), rainbow, 12, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Rainbow") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.blocks.Rainbow") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(4);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String cloud = Utils.Colorate("&8&lCloud");
	 if(p.hasPermission("trails.particles.Cloud"))
	 {
		 cloud = Utils.Colorate("&6&lCloud");
		 
		 
	 }
	 spiralMenu.setItem(new ItemStack(Material.QUARTZ), cloud, 13, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Cloud") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Cloud") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(5);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String witch = Utils.Colorate("&8&lWitch");
	 if(p.hasPermission("trails.particles.Witch"))
	 {
		 witch = Utils.Colorate("&6&lWitch");
	 }
	 
	 spiralMenu.setItem(new ItemStack(Material.GLASS_BOTTLE), witch, 14, new ClickRunnable() {
		 
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Witch") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Witch") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(6);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String ender = Utils.Colorate("&8&lEnder");
	 if(p.hasPermission("trails.particles.Ender"))
	 {
		 ender = Utils.Colorate("&6&lEnder");
	 }
	 spiralMenu.setItem(new ItemStack(Material.ENDER_PEARL), ender, 15, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Ender") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Ender") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(7);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String green = Utils.Colorate("&8&lHappy Villager");
	 if(p.hasPermission("trails.particles.Green"))
	 {
		 green = Utils.Colorate("&6&lHappy Villager");
	 }
	 spiralMenu.setItem(new ItemStack(Material.EMERALD), green, 16, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Green") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Green") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(8);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		}); 
	 String sparks = Utils.Colorate("&8&lSparks");
	 if(p.hasPermission("trails.particles.Sparks"))
	 {
		 sparks = Utils.Colorate("&6&lSparks");
	 }
	 spiralMenu.setItem(new ItemStack(Material.FIREWORK), sparks, 17, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Sparks") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Sparks") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		//white magic
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(11);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);
		    	
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String flames = Utils.Colorate("&8&lFlames");
	 if(p.hasPermission("trails.particles.Flames"))
	 {
		 flames = Utils.Colorate("&6&lFlames");
	 }
	 
	 spiralMenu.setItem(new ItemStack(Material.FLINT_AND_STEEL), flames, 18, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Flames") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Flames") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(10);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String notes = Utils.Colorate("&8&lNotes");
	 if(p.hasPermission("trails.particles.Notes"))
	 {
		 notes = Utils.Colorate("&6&lNotes");
	 }
	 spiralMenu.setItem(new ItemStack(2258, 1), notes, 19, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Notes") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Notes") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(12);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String snow = Utils.Colorate("&8&lSnow");
	 if(p.hasPermission("trails.particles.Snow"))
	 {
		 snow = Utils.Colorate("&6&lSnow");
	 }
	 spiralMenu.setItem(new ItemStack(Material.SNOW_BLOCK), snow, 20, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Snow") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Snow") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(13);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String rain = Utils.Colorate("&8&lRain");
	 if(p.hasPermission("trails.particles.Rain"))
	 {
		 rain = Utils.Colorate("&6&lRain");
	 }
	 
	 spiralMenu.setItem(new ItemStack(Material.WATER_BUCKET), rain, 21, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Rain") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Rain") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(14);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String lava = Utils.Colorate("&8&lLava Drip");
	 if(p.hasPermission("trails.particles.Lava"))
	 {
		 lava = Utils.Colorate("&6&lLava Drip");
	 }
	 spiralMenu.setItem(new ItemStack(Material.LAVA_BUCKET), lava, 22, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Lava") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Lava") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(15);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String crit = Utils.Colorate("&8&lCrit");
	 if(p.hasPermission("trails.particles.Crit"))
	 {
		 crit = Utils.Colorate("&6&lCrit");
	 }
	 spiralMenu.setItem(new ItemStack(Material.IRON_SWORD), crit, 23, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Crit") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Crit") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(16);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String smoke = Utils.Colorate("&8&lSmoke");
	 if(p.hasPermission("trails.particles.Smoke"))
	 {
		 smoke = Utils.Colorate("&6&lSmoke");
	 }
	 
	 spiralMenu.setItem(new ItemStack(Material.COAL_BLOCK), smoke, 24, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Smoke") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Smoke") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(17);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String endrod = Utils.Colorate("&8&lEnd Rod");
	 if(p.hasPermission("trails.particles.EndRod"))
	 {
		 endrod = Utils.Colorate("&6&lEnd Rod");
	 }
	 
	 spiralMenu.setItem(new ItemStack(Material.END_ROD), endrod, 25, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.EndRod") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.EndRod") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(27);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String totem = Utils.Colorate("&8&lTotem");
	 if(p.hasPermission("trails.particles.Totem"))
	 {
		 totem = Utils.Colorate("&6&lTotem");
	 }
	 
	 spiralMenu.setItem(new ItemStack(Material.TOTEM), totem, 26, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Totem") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Totem") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(28);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String splash = Utils.Colorate("&8&lSplash");
	 if(p.hasPermission("trails.particles.Splash"))
	 {
		 splash = Utils.Colorate("&6&lSplash");
	 }
	 
	 spiralMenu.setItem(new ItemStack(373, 1), splash, 27, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Splash") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Splash") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(20);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 String slime = Utils.Colorate("&8&lSlime");
	 if(p.hasPermission("trails.particles.Slime"))
	 {
		 slime = Utils.Colorate("&6&lSlime");
	 }
	 spiralMenu.setItem(new ItemStack(Material.SLIME_BALL), slime, 28, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Slime") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Slime") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(21);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String snowball = Utils.Colorate("&8&lSnowball");
	 if(p.hasPermission("trails.particles.Snowball"))
	 {
		 snowball = Utils.Colorate("&6&lSnowball");
	 }
	 
	 spiralMenu.setItem(new ItemStack(Material.SNOW_BALL), snowball, 29, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Snowball") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Snowball") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(22);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});
	 
	 String voids = Utils.Colorate("&8&lVoid");
	 if(p.hasPermission("trails.particles.Void"))
	 {
		 voids = Utils.Colorate("&6&lVoid");
	 }
	 
	 spiralMenu.setItem(new ItemStack(Material.ENDER_STONE), voids, 29, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("trails.particles.Void") && !ep.hasPermission("trails.mode.Spiral"))
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have the Spiral mode!"));
		    		return;
		    	}
		    	if(ep.hasPermission("trails.particles.Void") && ep.hasPermission("trails.mode.Spiral"))
		    	{
		       	SuperTrailsAPI.getPlayerData(ep).setTrail(23);;
		       	SuperTrailsAPI.getPlayerData(ep).setMode(4);

		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this trail!"));
		    	}

		    }
		});

	 
	 spiralMenu.openInventory(p);
}

}