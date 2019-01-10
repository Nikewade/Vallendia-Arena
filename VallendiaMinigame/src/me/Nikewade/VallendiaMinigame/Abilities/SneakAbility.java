package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.LineUtil;
import me.Nikewade.VallendiaMinigame.Utils.Utils;


public class SneakAbility implements Ability , Listener {
	public static ArrayList<Player> sneaking = new ArrayList<Player>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Sneak";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Allows you to sneak while running.");
	}

	@Override
	public ItemStack getGuiItem() {
		  SkullMeta  meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
		  meta.setOwner("Nikewade");
		  ItemStack item = new ItemStack(Material.SKULL_ITEM,1 , (byte)3);
		  item.setItemMeta(meta);
		return item;
	}

	@Override
	public boolean RunAbility(Player p) {
		
		if(sneaking.contains(p))
		{
			sneaking.remove(p);
			p.setSneaking(false);
			p.sendMessage(Utils.Colorate("&8&l[Sneak] &7You stop sneaking."));
			return true;
		}
		sneaking.add(p);
		p.setSneaking(true);
		p.sendMessage(Utils.Colorate("&8&l[Sneak] &7You begin to sneak!"));
		
 
        return false;
		}
	
	
	
	
    public static Listener getListener() {
        return new Listener() {
        	
        	
        	
        	
            @EventHandler
            public void onPlayerToggleSneak(PlayerToggleSneakEvent e)
            {
        		if(sneaking.contains(e.getPlayer()))
            	{
                	e.getPlayer().setSneaking(true);
                	e.setCancelled(true);
            	}
            }
            
           
            
            @EventHandler
            public void onDamage(EntityDamageEvent e)
            {
            	if(e.getEntity() instanceof Player)
            	{
            		if(e.isCancelled())
            		{
            			return;
            		}
            		Player p = (Player) e.getEntity();
            		
            		if(e.getCause() == DamageCause.FALL && sneaking.contains(p) && p.getFallDistance() <= 8)
            		{
            			p.sendMessage(Utils.Colorate("&8&l[Sneak] &7You roll."));
            			e.setCancelled(true);
            			return;
            		}
            		if(sneaking.contains(p))
            		{
            			sneaking.remove(p);
            			p.setSneaking(false);
            			p.sendMessage(Utils.Colorate("&8&l[Sneak] &7You stop sneaking."));
            		}
            	}
            }
         
            
            @EventHandler
            public void onCharacterLeave(PlayerQuitEvent e)
            {
        		if(sneaking.contains(e.getPlayer()))
            	{
                	sneaking.remove(e.getPlayer());
            		e.getPlayer().setSneaking(false);
            	}
            }
            
            
            @EventHandler
            public void clickBlock(PlayerInteractEvent e)
            {
            	if(sneaking.contains(e.getPlayer()))
            	if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
            	if(e.getClickedBlock().getType() == Material.CHEST)
            	if(e.getPlayer().getItemInHand().getType() != Material.AIR)
            	{
            		e.getPlayer().sendMessage(Utils.Colorate("&8&l[Sneak] &7Un-sneak to open a chest while holding an item!"));
            	}
            	
            }


            
            
            
            
            
            
        };
    }
    
    
    public static void onDie(Player p)
    {
		if(sneaking.contains(p))
    	{
        	sneaking.remove(p);
    	}
    }
    
	public static void onReload() 
	{
		for (Player p : Bukkit.getOnlinePlayers())
        {
            	p.setSneaking(false);
        }
	}

}

