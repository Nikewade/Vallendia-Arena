package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class SniperAbility implements Ability, Listener{
	Map<Projectile, Location> locations = new HashMap<>();
	Map<Projectile, BukkitTask> tasks = new HashMap<>();
	
	int lowestRange = 20; 
	int percentAdded = 20;
	int perBlock = 10; 

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Sniper";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Starting at a range of " + lowestRange + " blocks, for every" ,
				perBlock + " blocks your arrows travel, they will deal " + percentAdded + "%" ,
				"extra damage.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.ARROW);
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
	
	
	
	@EventHandler
	public void onShoot(EntityShootBowEvent e)
	{
		if(!(e.getEntity() instanceof Player))
		{
			return;
		}
		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility((Player)e.getEntity(), "Sniper"))
		{
			Player p = (Player) e.getEntity();
			Location loc = p.getLocation().getBlock().getLocation();
			locations.put((Projectile) e.getProjectile(), loc);	
			
			BukkitTask timer = new BukkitRunnable() {
				int blocksTraveled = (lowestRange - perBlock) + perBlock;
                @Override
                public void run()
                {
        			int distance = (int) loc.distance(e.getProjectile().getLocation());
        			if(distance >= blocksTraveled)
        			{
        				blocksTraveled = blocksTraveled + perBlock;
        				SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
        		        se.particle = Particle.EXPLOSION_NORMAL;
        		        se.radius = 0.1;
        		        se.particles = 1;
        		        se.speed = (float) 0;
        		        se.visibleRange = 70;
        		        se.iterations = 2;
        		        se.setLocation(e.getProjectile().getLocation());
        		        se.start();
        		        
        				SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
        		        se2.particle = Particle.CRIT;
        		        se2.radius = 1;
        		        se2.particles = 5;
        		        se2.speed = (float) 0;
        		        se2.visibleRange = 70;
        		        se2.iterations = 2;
        		        se2.setLocation(e.getProjectile().getLocation());
        		        se2.start();
        			}

                }
            
            }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
            
            tasks.put((Projectile)e.getProjectile(), timer);
			
			
		}
	}
	
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e)
	{
		if(e.getDamager() instanceof Arrow && locations.containsKey(e.getDamager()))
		{
			Projectile arrow = (Projectile) e.getDamager();
			Player shooter = (Player) arrow.getShooter();
			int distance = (int)e.getEntity().getLocation().distance(locations.get(arrow));
			if(distance < lowestRange)
			{
				return;
			}
			int amountAdd = distance / perBlock;
    		double higherPercent =  Utils.getPercentHigherOrLower((percentAdded * amountAdd), true);
    		double higherDamage = e.getDamage() * higherPercent;
    		e.setDamage(higherDamage);

			SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
	        se2.particle = Particle.CRIT;
	        se2.radius = 1;
	        se2.particles = 10;
	        se2.speed = (float) 2;
	        se2.visibleRange = 50;
	        se2.iterations = 2;
	        se2.setLocation(e.getEntity().getLocation().add(0, 0.3, 0));
	        se2.start();
			
			
			locations.remove(arrow);
			tasks.get(arrow).cancel();
			tasks.remove(arrow);
			
		}
	}
	
	
}
