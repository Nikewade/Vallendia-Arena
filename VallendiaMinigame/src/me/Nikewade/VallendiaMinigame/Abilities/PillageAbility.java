package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class PillageAbility implements Ability, Listener{
	private static ArrayList<Item> items = new ArrayList<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Pillage";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Each time you melee hit a player, steal 1 point.", 
				"Sending a player into negative points will damage them.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.GOLD_NUGGET);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
    public static Listener getListener() {
        return new Listener() {
        	
        	@EventHandler
        	public void onDamage(EntityDamageByEntityEvent e)
        	{
        		if(!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player))
        		{
        			return;
        		}
        		if(e.getCause() != DamageCause.ENTITY_ATTACK)
        		{
        			return;
        		}
        		Player damager = (Player) e.getDamager();
        		Player target = (Player) e.getEntity();
        		VallendiaMinigame main = VallendiaMinigame.getInstance();
        		main.shopmanager.subtractPoints(target, 1);
        		if(main.shopmanager.getPoints(target) <= 0)
        		{
        			return;
        		}
        		main.shopmanager.addPoints(damager, 1);
        		
        		
        		ItemStack itemstack = null;
        		int random = Utils.randomNumber(1, 3);
        		if(random == 1)
        		{
            		itemstack = new ItemStack(Material.GOLD_NUGGET);	
        		}
        		if(random == 2)
        		{
            		itemstack = new ItemStack(Material.IRON_NUGGET);	
        		}
        		if(random == 3)
        		{
            		itemstack = new ItemStack(Material.DIAMOND);	
        		}
        		ItemMeta im = itemstack.getItemMeta();
        		im.setDisplayName("pillage");
        		itemstack.setItemMeta(im);
        		Item item = target.getWorld().dropItem(target.getLocation().add(0, 1, 0), itemstack);
        		item.setVelocity(item.getVelocity().multiply(1.4));
        		item.setPickupDelay(999999999);
        		items.add(item);
        		
        		new BukkitRunnable() {
                    @Override
                    public void run() {
            			item.remove();
            			items.remove(item);
                    }
                }.runTaskLaterAsynchronously(VallendiaMinigame.getInstance(), 2*20L);  
        	}
        	
        	
        };
    }
    
    
    public static void removeItems()
    {
    	for(Item item : items)
    	{
    		item.remove();
    	}
    }

}
