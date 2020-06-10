package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class SurvivalistAbility implements Ability, Listener{
	private static HashMap<Player, BukkitTask> enabled = new HashMap<>();
	private static double healTime = 6;
	private static int percent = 20;
	private static int nonPlayerPercent = 10;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Survivalist";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("After you kill a player you",
				"and your pet (if you have one) gain",
				+ percent + "% of your max health over",
				(int)healTime + " seconds. If you kill a non-player",
				"you will only gain " + nonPlayerPercent + "% over time.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SKULL_ITEM);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
        	@EventHandler
        	public void onDeath(EntityDeathEvent e)
        	{
        		if(!(e.getEntity() instanceof LivingEntity) || !(e.getEntity().getKiller() instanceof Player))
        		{
        			return;
        		}
    			Player p = e.getEntity().getKiller();
    			if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Survivalist"))
    			{
    				return;
    			}
        		if(e.getEntity() instanceof Player)
        		{
    	 			if(enabled.containsKey(p))
    	 			{
    	 				enabled.get(p).cancel();
    	 				enabled.remove(p);
    	 			}
    	 			double healthToHeal = ((p.getMaxHealth() * percent) / 100) / healTime;
    	 			double startHealth = p.getHealth();
    	 			double maxHealth = p.getMaxHealth();
    	 			
    	 			BukkitTask task = new BukkitRunnable() {
    		 			double newHealth = p.getHealth();
    	 	            @Override
    	 	            public void run() {
    	 	            	newHealth = newHealth + healthToHeal;
    			    	 	if(newHealth >= startHealth + (maxHealth * percent) / 100 || p.getHealth() >= maxHealth)
    			    	 	{
    		 	            	this.cancel();
    		 	            	return;
    			    	 	}
    	 	            	AbilityUtils.healEntity(p, healthToHeal);
    	 	            	
    	 	            }
    	 	        }.runTaskTimer(VallendiaMinigame.getInstance(), 0 , 20L);  
    	 	        enabled.put(p, task);
        		}else
        			
        			
        			
        		{
    	 			if(enabled.containsKey(p))
    	 			{
    	 				enabled.get(p).cancel();
    	 				enabled.remove(p);
    	 			}
    	 			double healthToHeal = ((p.getMaxHealth() * nonPlayerPercent) / 100) / healTime;
    	 			double startHealth = p.getHealth();
    	 			double maxHealth = p.getMaxHealth();
    	 			
    	 			BukkitTask task = new BukkitRunnable() {
    		 			double newHealth = p.getHealth();
    	 	            @Override
    	 	            public void run() {
    	 	            	newHealth = newHealth + healthToHeal;
    			    	 	if(newHealth >= startHealth + (maxHealth * nonPlayerPercent) / 100 || p.getHealth() >= maxHealth)
    			    	 	{
    		 	            	this.cancel();
    		 	            	return;
    			    	 	}
    	 	            	AbilityUtils.healEntity(p, healthToHeal);
    	 	            	
    	 	            }
    	 	        }.runTaskTimer(VallendiaMinigame.getInstance(), 0 , 20L);  
    	 	        enabled.put(p, task);
        		}
        		
        		
        		
        		
        	}
        	
    
	public static void removeEnabled(LivingEntity p)
	{
		if(enabled.containsKey(p))
		{
			enabled.get(p).cancel();
			enabled.remove(p);
		}
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
