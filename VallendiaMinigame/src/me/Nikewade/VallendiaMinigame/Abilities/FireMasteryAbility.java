package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class FireMasteryAbility implements Ability, Listener{
	//made by emma 
	ArrayList<Player> inWater = new ArrayList<>();
	HashMap<Player, BukkitTask> particles = new HashMap<>();
	HashMap<Player, SphereEffect> sphere = new HashMap<>();
	int attackpercent = 15;
	int defencepercent = 10;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Fire Mastery";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You are now immune to fire damage. While on", 
							"fire, you take " + defencepercent + "% less damage and deal " + attackpercent +"%",
							"more damage.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.LAVA_BUCKET);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(particles.containsKey(p))
		{
			particles.get(p).cancel();
		}
		if(sphere.containsKey(p))
		{
			sphere.get(p).cancel();
		}
		if(inWater.contains(p))
		{
			inWater.remove(p);
		}
		
	}

	@EventHandler
	public void onMove (PlayerMoveEvent e)
	{
		
		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(e.getPlayer(), "Fire Mastery"))
		{
			if(inWater.contains(e.getPlayer()))
			{
				return;
			}
			
			Player p = e.getPlayer();
		    if (p.getLocation().getBlock().getType() == Material.STATIONARY_LAVA|| 
		    		p.getLocation().getBlock().getType() == Material.LAVA) {		    	
		    	inWater.add(p);	
				SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
				se.disappearWithOriginEntity = true;
				se.infinite();
				se.particle = Particle.FLAME;
				se.radius = 0.8;
				se.particleOffsetY = (float) 0.5;
				se.particles = 1;
				se.yOffset = -0.8;
				se.speed = (float) 0;
				se.setEntity(p);
				se.start()
				;
				
				sphere.put(p, se);
				
		    	BukkitTask timer = new BukkitRunnable()
		    			{

							@Override
							public void run() {
								// TODO Auto-generated method stub
							    if (p.getLocation().getBlock().getType() != Material.STATIONARY_LAVA && 
							    		p.getLocation().getBlock().getType() != Material.LAVA)
							    {
							    	inWater.remove(p);
							    	se.cancel();
							    	this.cancel();
							    	
							    }
								
							}
		    		
		    			}.runTaskTimer(VallendiaMinigame.getInstance(), 10, 10);
		    			
						particles.put(p, timer);
		    			
		    	return;
		    	
		    }

		    
		}
		    
	}
	
	@EventHandler
	public void onHit (EntityDamageByEntityEvent e)
	{
        Player p = null;
        if(e.getDamager() instanceof Projectile)
        {
        	Projectile proj = (Projectile) e.getDamager();
        	
        	if(proj.getShooter() instanceof Player)
        	{
        		
        	p = (Player) proj.getShooter();
        	
        	}
        }else
        {
        	if(e.getDamager() instanceof Player)
        	{
        	p = (Player) e.getDamager();
        	}
        	if(!(e.getDamager() instanceof Player))
        	{
        		return;
        	}
        }
        
		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Fire Mastery"))
		{
			if(!(p.getFireTicks() <= 0))
			{
			double highPercent = Utils.getPercentHigherOrLower(attackpercent, true);
			double newdamage = e.getFinalDamage()*highPercent;
			e.setDamage(0);
			e.setDamage(DamageModifier.ARMOR, newdamage);
			
			if(e.getEntity() instanceof LivingEntity)
			{
			
			LivingEntity ent = (LivingEntity) e.getEntity();
			
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.setEntity(ent);
			se.disappearWithOriginEntity = true;
			se.iterations = 10;
			se.particle = Particle.FLAME;
			se.radius = 0.8;
			se.particleOffsetY = (float) 0.5;
			se.particles = 1;
			se.yOffset = -0.8;
			se.speed = (float) 0;
			se.start();
			
			SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se2.setEntity(ent);
			se2.disappearWithOriginEntity = true;
			se2.iterations = 10;
			se2.particle = Particle.LAVA;
			se2.radius = 0.8;
			se2.particleOffsetY = (float) 0.5;
			se2.particles = 1;
			se2.yOffset = -0.8;
			se2.speed = (float) 0;
			se2.start();
			
			}
			}
			
		}
	}
	
	@EventHandler
	public void onDamage (EntityDamageEvent e)
	{
		if(!(e.getEntity() instanceof Player))
		{
			return;
		}
		
		Player p = (Player) e.getEntity();
		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Fire Mastery"))
		{
			if(e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.FIRE_TICK || e.getCause() == DamageCause.LAVA)
			{

				e.setCancelled(true);
				return;

			}
			
			if(!(p.getFireTicks() <= 0))
			{

			
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.setEntity(p);
			se.disappearWithOriginEntity = true;
			se.iterations = 10;
			se.particle = Particle.FLAME;
			se.radius = 0.8;
			se.particleOffsetY = (float) 0.5;
			se.particles = 1;
			se.yOffset = -0.8;
			se.speed = (float) 0;
			se.start();
			
			SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se2.setEntity(p);
			se2.disappearWithOriginEntity = true;
			se2.iterations = 10;
			se2.particle = Particle.LAVA;
			se2.radius = 0.8;
			se2.particleOffsetY = (float) 0.5;
			se2.particles = 1;
			se2.yOffset = -0.8;
			se2.speed = (float) 0;
			se2.start();
			
			double lowPercent = Utils.getPercentHigherOrLower(defencepercent, false);
			double newdamage = e.getFinalDamage()*lowPercent;
			e.setDamage(0);
			e.setDamage(DamageModifier.ARMOR, newdamage);
			
			}

		}
	}
}