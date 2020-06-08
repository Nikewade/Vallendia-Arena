package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class BakerAbility implements Ability, Listener{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Baker";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Right click a furnace with 10 or more",
							"wheat in your hand to turn it into bread");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.BREAD);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}
	
	public int breadAmount(Player p, int x, int six)
	{
		if(x<six)
		{
			Language.sendAbilityUseMessage(p, "You don't have enough wheat!", "Baker");
			return 0;
		}
		double number = x/six;
		number = Math.floor(number);
		
		return (int) number;
		
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			Player p = e.getPlayer();
			if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Baker"))
			{
			Block b = e.getClickedBlock();
			ItemStack mainHand = p.getInventory().getItemInMainHand();
			ItemStack offHand = p.getInventory().getItemInOffHand();
			if(b.getType() == Material.FURNACE)
			{
				if(mainHand.getType() == Material.WHEAT && breadAmount(p, mainHand.getAmount(), 10) > 0)
				{
					int bread = breadAmount(p, mainHand.getAmount(), 10);
					int subtract = bread*10;
					int amount = mainHand.getAmount();
					int newAmount = amount - subtract;
					mainHand.setAmount(newAmount);
					ItemStack item = new ItemStack(Material.BREAD);
					item.setAmount(bread);
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(Utils.Colorate("&8&lBread"));
					meta.setLore(Arrays.asList(Utils.Colorate("&8Heals 30% max health")));
					item.setItemMeta(meta);
					p.getInventory().addItem(item);	
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
					}.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1L);
							
				}
				if(offHand.getType() == Material.WHEAT && breadAmount(p, offHand.getAmount(), 10) > 0)
				{
					int bread = breadAmount(p, offHand.getAmount(), 10);
					int subtract = bread*10;
					int amount = offHand.getAmount();
					int newAmount = amount - subtract;
					offHand.setAmount(newAmount);
					ItemStack item = new ItemStack(Material.BREAD);
					item.setAmount(bread);
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(Utils.Colorate("&8&lBread"));
					meta.setLore(Arrays.asList(Utils.Colorate("&8Heals 30% max health")));
					item.setItemMeta(meta);
					p.getInventory().addItem(item);			
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
					}.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1L);
							
				}
				{
					
				}
				
			}
		}
		}
	}
	

}