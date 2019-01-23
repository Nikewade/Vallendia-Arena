package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class MomentumAbility implements Ability, Listener{
	private static ArrayList<Player> running = new ArrayList<>();
	private static HashMap<Player, Integer> currentDamage = new HashMap<>();
	private static HashMap<Player, BukkitTask> tasks = new HashMap<>();
	private static int percentPerSecond = 10;
	private static int maxSeconds = 8;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Momentum";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Running gives a damage boost of 10% per",
				"second spent running to a maximum",
				"of +80% to your next hit. This damage",
				"boost fades when you stop running.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.IRON_BOOTS);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
    public static Listener getListener() {
        return new Listener() {
        	
        	@EventHandler
        	public void sprint(PlayerToggleSprintEvent e)
        	{
        		if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(e.getPlayer(), "Momentum"))
        		{
        			return;
        		}
        		
        		Player p = e.getPlayer();

        		if(running.contains(p))
        		{
        			
            		if(running.contains(p))
            		{
            			running.remove(p);	
            		}
        			tasks.get(p).cancel();
            		if(currentDamage.containsKey(p))
            		{
            			currentDamage.remove(p);
            		}
            		return;
        		}
        		
        		
        		if(!p.isSprinting() && !running.contains(p) && p.isOnGround())
        		{
            			running.add(p);
            			
            			BukkitTask task = new BukkitRunnable() {
            				//1 second warmup
            				int x = -1;
            	            @Override
            	            public void run() {
            	            	x++;
            	            	if(x > MomentumAbility.maxSeconds || !running.contains(p))
            	            	{
            	            		this.cancel();
            	            		return;
            	            	}
            	            	//warm up
            	            	if(x == 0)
            	            	{
            	            		return;
            	            	}
                				int damage = x * percentPerSecond;
            	            	
        	            		if(!currentDamage.containsKey(p))
        	            		{
        	            			currentDamage.put(p, damage);
        	            		}else currentDamage.replace(p, damage);
        	            		
        	            		p.getWorld().playSound(p.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 2, (float) ((float) 1 + (x*0.1)));
                  	 	 		p.getWorld().spawnParticle(Particle.CRIT, p.getLocation().add(0, 1, 0), 20);
            	            	
            	            	
            	            }
            	        }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 20);
            	        
            	        tasks.put(p, task);	
        		}
        		
        	}
        	
        	
        	
        	@EventHandler
        	public void onDamage(EntityDamageByEntityEvent e)
        	{
        		if(!(e.getDamager() instanceof Player))
        		{
        			return;
        		}
        		
        		if(!currentDamage.containsKey(e.getDamager()))
        		{
        			return;	
        		}
        		Player p = (Player) e.getDamager();
        		double higherPercent =  ((currentDamage.get(p)* 0.1) * 0.1) + 1;
        		double damage = e.getDamage();
        		double higherDamage = damage * higherPercent;
        		e.setDamage(higherDamage);
  	 	 		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 2, (float) 0.8);
  	 	 		e.getEntity().getWorld().spawnParticle(Particle.CRIT, e.getEntity().getLocation().add(0, 1, 0), 20);
        		
    			tasks.get(p).cancel();
        		if(currentDamage.containsKey(p))
        		{
        			currentDamage.remove(p);
        		}
        		
        		
        	}

        		
        };
    }

}