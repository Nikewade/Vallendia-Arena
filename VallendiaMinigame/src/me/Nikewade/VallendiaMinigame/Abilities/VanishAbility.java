package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
<<<<<<< HEAD
=======
import org.bukkit.event.EventPriority;
>>>>>>> second-repo/master
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class VanishAbility implements Ability, Listener{
	private static ArrayList<Player> enabled = new ArrayList<>();
	private static HashMap<Player, BukkitTask> tasks = new HashMap<>();
	private static HashMap<Player, BukkitTask> countDown = new HashMap<>();
<<<<<<< HEAD
	int enabledTime = 20;
=======
	int enabledTime = 30;
>>>>>>> second-repo/master

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Vanish";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Vanish into thin air turning",
				"invisible for " + enabledTime + " seconds.",
				"any damage delt or taken will",
				"cause you to reappear.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.STAINED_GLASS_PANE);
	}

	@Override
	public boolean RunAbility(Player p) {
		if(enabled.contains(p))
		{
			Language.sendAbilityUseMessage(p, "You are already using this.", this.getName());
			return false;
		}
<<<<<<< HEAD
		enabled.add(p);

		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se.setLocation(p.getLocation());
		se.particle = Particle.SMOKE_NORMAL;
		se.radius = 1;
		se.particles = 10;
		se.yOffset = 0.6;
		se.iterations = 3;
		se.start();
		Language.sendAbilityUseMessage(p, "You vanish.", "Vanish");
		
		AbilityUtils.makeInvisible(p);
		
		
		BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
            	removeVanish(p);
            }
        }.runTaskLater(VallendiaMinigame.getInstance(), enabledTime*20L);
        tasks.put(p, task);
        
        
        
        
    	BukkitTask countdown =	new BukkitRunnable() {
			int x = enabledTime;
            @Override
            public void run() {
            	if(enabled.contains(p))
            	{
            		if(x == 10)
            		{
        		        p.sendTitle(Utils.Colorate("&3&lVanish " + x + " seconds"), null, 0, 26, 0);
            		}
            		if(x <= 5)
            		{
        		        p.sendTitle(Utils.Colorate("&3&lVanish " + x + " seconds"), null, 0, 26, 0);
            		}
            		x--;
            	}else this.cancel();
            }
        }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 20L);
        countDown.put(p, countdown);
        return true;
=======
		if(	AbilityUtils.makeInvisible(p, this.getName()))
		{
			enabled.add(p);

			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.setLocation(p.getLocation());
			se.particle = Particle.SMOKE_NORMAL;
			se.radius = 1;
			se.particles = 10;
			se.yOffset = 0.6;
			se.iterations = 3;
			se.start();
			Language.sendAbilityUseMessage(p, "You vanish.", "Vanish");
			
			
			
			BukkitTask task = new BukkitRunnable() {
	            @Override
	            public void run() {
	            	removeVanish(p);
	            }
	        }.runTaskLater(VallendiaMinigame.getInstance(), enabledTime*20L);
	        tasks.put(p, task);
	        
	        
	        
	        
	    	BukkitTask countdown =	new BukkitRunnable() {
				int x = enabledTime;
	            @Override
	            public void run() {
	            	if(enabled.contains(p))
	            	{
	            		if(x == 10)
	            		{
	        		        p.sendTitle(Utils.Colorate("&3&lVanish " + x + " seconds"), null, 0, 26, 0);
	            		}
	            		if(x <= 5)
	            		{
	        		        p.sendTitle(Utils.Colorate("&3&lVanish " + x + " seconds"), null, 0, 26, 0);
	            		}
	            		x--;
	            	}else this.cancel();
	            }
	        }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 20L);
	        countDown.put(p, countdown);
	        return true;	
		}
		return false;
>>>>>>> second-repo/master
	}
	

	private void removeVanish(Player p)
	{
		if(enabled.contains(p))
		{
			enabled.remove(p);
    		AbilityUtils.removeInvisible(p);
    		countDown.get(p).cancel();
    		countDown.remove(p);
    		tasks.get(p).cancel();
    		tasks.remove(p);
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.setLocation(p.getLocation());
			se.particle = Particle.SMOKE_NORMAL;
			se.radius = 1;
			se.particles = 10;
			se.yOffset = 0.6;
			se.iterations = 3;
			se.start();
			Language.sendAbilityUseMessage(p, "You reappear.", "Vanish");
		}
	}
	
	
	
<<<<<<< HEAD
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e)
	{
=======
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamage(EntityDamageByEntityEvent e)
	{
		if(e.isCancelled()) //e.getDamage() == 0
		{
			return;
		}
>>>>>>> second-repo/master
		
		if(e.getDamager() instanceof Player && enabled.contains(e.getDamager()))
		{
			removeVanish((Player)e.getDamager());
		}
		
		if(e.getDamager() instanceof Projectile)
		{
			Projectile proj = (Projectile) e.getDamager();
			if(enabled.contains(proj.getShooter()))
			{
				removeVanish((Player)proj.getShooter());
			}
		}
	}
	
	
<<<<<<< HEAD
	@EventHandler
	public void onDamage(EntityDamageEvent e)
	{
=======
	@EventHandler(priority = EventPriority.HIGH)
	public void onDamage(EntityDamageEvent e)
	{
		if(e.isCancelled())
		{
			return;
		}
>>>>>>> second-repo/master
		if(!(e.getEntity() instanceof Player))
		{
			return;	
		}
		if(enabled.contains(e.getEntity()))
		{
			removeVanish((Player)e.getEntity());
		}
		
	}
	
	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(enabled.contains(p))
		{	
			enabled.remove(p);
    		AbilityUtils.removeInvisible(p);
    		countDown.remove(p);
			Language.sendAbilityUseMessage(p, "You reappear.", "Vanish");
		};
	}
}
