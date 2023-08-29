package me.Nikewade.VallendiaMinigame.Events;

<<<<<<< HEAD
=======
import java.util.Arrays;
>>>>>>> second-repo/master
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
<<<<<<< HEAD
import org.bukkit.block.Furnace;
=======
>>>>>>> second-repo/master
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
<<<<<<< HEAD
=======
import org.bukkit.inventory.meta.ItemMeta;
>>>>>>> second-repo/master
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Language;
<<<<<<< HEAD
=======
import me.Nikewade.VallendiaMinigame.Utils.Utils;
>>>>>>> second-repo/master

public class PlayerCustomFoodCookEvents implements Listener{
	VallendiaMinigame Main;
	Map<Material,Material> food = new HashMap<>();
	
	
	
	public PlayerCustomFoodCookEvents(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.getServer().getPluginManager().registerEvents(this, Main);
		food.put(Material.PORK, Material.GRILLED_PORK);
		food.put(Material.RAW_FISH, Material.COOKED_FISH);
		food.put(Material.RAW_BEEF, Material.COOKED_BEEF);
		food.put(Material.RAW_CHICKEN, Material.COOKED_CHICKEN);
		food.put(Material.MUTTON, Material.COOKED_MUTTON);
		food.put(Material.RABBIT, Material.COOKED_RABBIT);
	}
	
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			Player p = e.getPlayer();
			Block b = e.getClickedBlock();
			ItemStack mainHand = p.getInventory().getItemInMainHand();
			ItemStack offHand = p.getInventory().getItemInOffHand();
			if(b.getType() == Material.FURNACE)
			{
				if(!food.containsKey(mainHand.getType()) && !food.containsKey(offHand.getType()))
				{
<<<<<<< HEAD
					Language.sendDefaultMessage(p, "Right click me with food in your hand to cook!");
=======
					if(!(mainHand.getType() == Material.WHEAT) && !(offHand.getType() == Material.WHEAT)) // FOr baker ability
					{
						Language.sendDefaultMessage(p, "Right click me with food in your hand to cook!");
					}
>>>>>>> second-repo/master
				}
				if(food.containsKey(mainHand.getType()) || food.containsKey(offHand.getType()))
				{
					if(food.containsKey(mainHand.getType()))
					{
<<<<<<< HEAD
						mainHand.setType(food.get(mainHand.getType()));
					}
						if(food.containsKey(offHand.getType()))
						{
								offHand.setType(food.get(offHand.getType()));
=======
						
						
						mainHand.setType(food.get(mainHand.getType()));
			 			int healPercent = 0;
			 			String itemName = "";
			 			
				        switch (mainHand.getType()) {
			            case COOKED_BEEF:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.steak");
			            itemName = "Steak";
			                     break;
			            case GRILLED_PORK:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.pork");
			            itemName = "Porkchop";
			                     break;
			            case COOKED_CHICKEN:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.chicken");
			            itemName = "Chicken";
			                     break;
			            case BREAD:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.bread");
			            itemName = "Bread";
			                     break;
			            case COOKED_FISH:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.fish");
			            itemName = "Fish";
			                     break;
			            case COOKED_RABBIT:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.rabbit");
			            itemName = "Rabbit";
		                 break;
						default:
							healPercent = 0;
							break;
				        }
						ItemMeta im = mainHand.getItemMeta();
						im.setDisplayName(Utils.Colorate("&8&l" + itemName));
						im.setLore(Arrays.asList(Utils.Colorate("&8Heals " + healPercent + "% max health")));
						mainHand.setItemMeta(im);
					}
						if(food.containsKey(offHand.getType()))
						{
							
								offHand.setType(food.get(offHand.getType()));
					 			int healPercent = 0;
					 			String itemName = "";
					 			
						        switch (offHand.getType()) {
					            case COOKED_BEEF:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.steak");
					            itemName = "Steak";
					                     break;
					            case GRILLED_PORK:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.pork");
					            itemName = "Porkchop";
					                     break;
					            case COOKED_CHICKEN:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.chicken");
					            itemName = "Chicken";
					                     break;
					            case BREAD:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.bread");
					            itemName = "Bread";
					                     break;
					            case COOKED_FISH:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.fish");
					            itemName = "Fish";
					                     break;
					            case COOKED_RABBIT:  healPercent = VallendiaMinigame.getInstance().getConfig().getInt("Options.food.rabbit");
					            itemName = "Rabbit";
				                 break;
								default:
									healPercent = 0;
									break;
						        }
								ItemMeta im = offHand.getItemMeta();
								im.setDisplayName(Utils.Colorate("&8&l" + itemName));
								im.setLore(Arrays.asList(Utils.Colorate("&8Heals " + healPercent + "% max health")));
								offHand.setItemMeta(im);
>>>>>>> second-repo/master
						}
					
					p.getWorld().playSound(b.getLocation(), Sound.BLOCK_FURNACE_FIRE_CRACKLE, 1, 1);
					p.getWorld().playSound(b.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
					
					SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
					se.setLocation(b.getLocation().add(0.5 , 0 , 0.5));
					se.radius = 1;
					se.particle = Particle.SMOKE_NORMAL;
					se.particles = 1;
					se.iterations = 20;
					se.start();
					
					SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
					se2.setLocation(b.getLocation().add(0.5 , 0 , 0.5));
					se2.radius = 1;
					se2.particle = Particle.FLAME;
					se2.particles = 1;
					se2.iterations = 20;
					se2.start();
					byte tempData = b.getData();
					
					 new BukkitRunnable() {
						int x = 0;
					     @Override
					     public void run() {
					    	 x++;
					    	 if(x >= 30)
					    	 {
					    		 this.cancel();
					    	 }
					    	 b.setType(Material.BURNING_FURNACE);
					    	 b.setData(tempData);
					    	 
					     }
					}.runTaskTimer(Main, 0, 1L);
				}
				
			}
		}
	}

}
