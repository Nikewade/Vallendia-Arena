package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
<<<<<<< HEAD
=======
import org.bukkit.event.EventPriority;
>>>>>>> second-repo/master
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
<<<<<<< HEAD

=======
import org.bukkit.material.Door;
import org.bukkit.material.Gate;
import org.bukkit.material.TrapDoor;

import com.alessiodp.parties.bukkit.addons.external.MythicMobsHandler;

import io.lumine.xikage.mythicmobs.MythicMobs;
>>>>>>> second-repo/master
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;


public class SneakAbility implements Ability , Listener {
	public static ArrayList<Player> sneaking = new ArrayList<Player>();
	double forwardVelocity = 5 / 10D;
	double upwardVelocity = -5 / 10D;
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
		return Arrays.asList("Allows you to sneak while running.", "Also negates fall damage of up to 8 blocks.");
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
<<<<<<< HEAD
		
		
		
=======
>>>>>>> second-repo/master
		if(sneaking.contains(p))
		{
			sneaking.remove(p);
			p.setSneaking(false);
			Language.sendAbilityUseMessage(p, "You stop sneaking.", "Sneak");
			return true;
		}
		sneaking.add(p);
		p.setSneaking(true);
		Language.sendAbilityUseMessage(p, "You begin to sneak!", "Sneak");
		
 
        return false;
		}
	
	
	
            @EventHandler
            public void onPlayerToggleSneak(PlayerToggleSneakEvent e)
            {
        		if(sneaking.contains(e.getPlayer()))
            	{
                	e.getPlayer().setSneaking(true);
                	e.setCancelled(true);
            	}
            }
            
           
            
<<<<<<< HEAD
            @EventHandler
=======
            @EventHandler(priority = EventPriority.HIGHEST)
>>>>>>> second-repo/master
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
            			Language.sendAbilityUseMessage(p, "You roll.", "Sneak");
            			e.setCancelled(true);
            			return;
            		}
            		if(sneaking.contains(p))
            		{
            			sneaking.remove(p);
            			p.setSneaking(false);
            			Language.sendAbilityUseMessage(p, "You stop sneaking.", "Sneak");
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
<<<<<<< HEAD
            	if(e.getClickedBlock().getType() == Material.CHEST)
            	if(e.getPlayer().getItemInHand().getType() != Material.AIR)
            	{
        			Language.sendAbilityUseMessage(e.getPlayer(), "Cant open this while holding an item!", "Sneak");
=======
            	if(e.getClickedBlock().getType() == Material.CHEST ||
            	e.getClickedBlock().getType().toString().contains("DOOR") || 
            	e.getClickedBlock().getType().toString().contains("GATE"))
            	{
                	if(e.getPlayer().getItemInHand().getType() != Material.AIR)
                	{
            			Language.sendAbilityUseMessage(e.getPlayer(), "Un-Sneak or click with an empty hand to interact with this!", "Sneak");
                	}	
>>>>>>> second-repo/master
            	}
            	
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

	@Override
	public void DisableAbility(Player p) {
		if(sneaking.contains(p.getPlayer()))
    	{
        	sneaking.remove(p.getPlayer());
    		p.getPlayer().setSneaking(false);
    	}
<<<<<<< HEAD
=======
		AbilityManager.removeAbilityData("Sneak", p);
>>>>>>> second-repo/master
	}

}

