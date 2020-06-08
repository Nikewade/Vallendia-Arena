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
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class SiphonAbility implements Ability, Listener {
	private static ArrayList<Item> items = new ArrayList<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Siphon";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Each time you damage a player, steal 3 Essence.", 
				"Sending a player into negative Essence will damage them.");
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
	
	
	
        	
        	@EventHandler
        	public void onDamage(EntityDamageByEntityEvent e)
        	{
        		if(!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player))
        		{
        			return;
        		}
        		Player damager = (Player) e.getDamager();
        		Player target = (Player) e.getEntity();
        		if(!AbilityUtils.runPassive(damager, target))
        		{
        			return;
        		}
        		VallendiaMinigame main = VallendiaMinigame.getInstance();
        		if(!main.abilitymanager.playerHasAbility(damager, "Siphon"))
        		{
        			return;
        		}
        		main.shopmanager.subtractPoints(target, 3);
        		if(main.shopmanager.getPoints(target) <= 0)
        		{
        			return;
        		}
        		main.shopmanager.addPoints(damager, 3);
        		
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
        	
        	
    
    
    public static void removeItems()
    {
    	for(Item item : items)
    	{
    		item.remove();
    	}
    }

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
