package me.Nikewade.VallendiaMinigame.Events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

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
				if(food.containsKey(mainHand.getType()) || food.containsKey(offHand.getType()))
				{
					if(food.containsKey(mainHand.getType()))
					{
						mainHand.setType(food.get(mainHand.getType()));
					}
						if(food.containsKey(offHand.getType()))
						{
								offHand.setType(food.get(offHand.getType()));
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
