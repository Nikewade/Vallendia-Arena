package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class CamouflageAbility implements Ability, Listener {
	private static ArrayList<Player> enabled = new ArrayList<>();
	private static HashMap<Player, BukkitTask> tasks = new HashMap<>();
	private static HashMap<Player, BukkitTask> healTasks = new HashMap<>();
	private static HashMap<Player, BukkitTask> countDown = new HashMap<>();
	private HashMap<Player, Location> locationStarted = new HashMap<>();
	int enabledTime = 60;
	double healPercent = 2;
	int healPerSecond = 5;
	int healDelay = 3;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Camouflage";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You camouflage yourself to match your surroundings,",
				"becoming invisible for " + enabledTime + " seconds. You also regenerate ",
				"health over time. Moving, taking damage, or dealing",
				"damage will cause you to reappear.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.LEAVES);
	}

	@Override
	public boolean RunAbility(Player p) {
		if(enabled.contains(p))
		{
			Language.sendAbilityUseMessage(p, "You are already using this.", this.getName());
			return false;
		}
		if(!p.isOnGround())
		{
			Language.sendAbilityUseMessage(p, "You must be on the ground.", this.getName());
			return false;
		}
		
		if(AbilityUtils.makeInvisible(p, this.getName()))
		{
			enabled.add(p);
			Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);

			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.setLocation(p.getLocation());
			se.particle = Particle.BLOCK_CRACK;
			if(b.getType() == Material.GRASS || b.getType() == Material.AIR)
			{
				se.material = Material.LEAVES;
			}else
			{
				se.material = b.getType();	
			}
			se.radius = 1.2;
			se.particles = 15;
			se.yOffset = 0.6;
			se.iterations = 3;
			se.start();
			Language.sendAbilityUseMessage(p, "You camouflage yourself.", this.getName());
			this.locationStarted.put(p, p.getLocation());
			
			
			
			BukkitTask task = new BukkitRunnable() {
	            @Override
	            public void run() {
	            	removeVanish(p);
	            }
	        }.runTaskLater(VallendiaMinigame.getInstance(), enabledTime*20L);
	        tasks.put(p, task);
	        
	        
				BukkitTask healTask = new BukkitRunnable() {
	 	            @Override
	 	            public void run() {
	 	            	double healAmount = p.getMaxHealth() * (healPercent / 100);
	 	            	if(p.getHealth() >= p.getMaxHealth())
	 	            	{
	 	            		this.cancel();
	 	            	}
	 	            	AbilityUtils.healEntity(p, healAmount);
	 	            	
	 	            }
	 	        }.runTaskTimer(VallendiaMinigame.getInstance(), healDelay * 20, healPerSecond * 20L); 
	 	        
	 	        healTasks.put(p, healTask);
	        
	        
	        
	    	BukkitTask countdown =	new BukkitRunnable() {
				int x = enabledTime;
	            @Override
	            public void run() {
	            	if(enabled.contains(p))
	            	{
	            		if(x == 10)
	            		{
	        		        p.sendTitle(Utils.Colorate("&3&lCamouflage " + x + " seconds"), null, 0, 26, 0);
	            		}
	            		if(x <= 5)
	            		{
	        		        p.sendTitle(Utils.Colorate("&3&lCamouflage " + x + " seconds"), null, 0, 26, 0);
	            		}
	            		x--;
	            	}else this.cancel();
	            }
	        }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 20L);
	        countDown.put(p, countdown);
	        return true;	
		}
		return false;
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
    		healTasks.get(p).cancel();
    		healTasks.remove(p);
    		this.locationStarted.remove(p);
    		Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);

    		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
    		se.setLocation(p.getLocation());
    		se.particle = Particle.BLOCK_CRACK;
    		if(b.getType() == Material.GRASS || b.getType() == Material.AIR)
    		{
    			se.material = Material.LEAVES;
    		}else
    		{
    			se.material = b.getType();	
    		}
    		se.radius = 1.2;
    		se.particles = 15;
    		se.yOffset = 0.6;
    		se.iterations = 3;
    		se.start();
			Language.sendAbilityUseMessage(p, "You reappear.", "Camouflage");
		}
	}
	
	
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamage(EntityDamageByEntityEvent e)
	{
		if(e.isCancelled()) //e.getDamage() == 0
		{
			return;
		}
		
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
	
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onDamage(EntityDamageEvent e)
	{
		if(e.isCancelled()) //e.getDamage() == 0
		{
			return;
		}
		if(!(e.getEntity() instanceof Player))
		{
			return;	
		}
		if(enabled.contains(e.getEntity()))
		{
			removeVanish((Player)e.getEntity());
		}
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		if(enabled.contains(e.getPlayer()))
		{
			if(!e.getPlayer().isOnGround() && !(e.getTo().distance(this.locationStarted.get(e.getPlayer())) > 2)  )
			{
				return;
			}
			if(e.getTo().distance(this.locationStarted.get(e.getPlayer())) >= 0.9 )
			{
			removeVanish(e.getPlayer());
			}
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
			Language.sendAbilityUseMessage(p, "You reappear.", "Camouflage");
		};
	}
}

