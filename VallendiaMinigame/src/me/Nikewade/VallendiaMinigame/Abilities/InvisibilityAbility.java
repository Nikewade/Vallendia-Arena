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
import org.bukkit.event.EventPriority;
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

public class InvisibilityAbility implements Ability, Listener {
	//made by emma
	ArrayList<Player> enabled = new ArrayList<>();
	HashMap<Player, BukkitTask> tasks = new HashMap<>();
	HashMap<Player, BukkitTask> countDown = new HashMap<>();
	HashMap <Player, SphereEffect> particles = new HashMap<>();
	int enabledTime = 60;
	int castTime = 5;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Invisibility";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You turn invisibile for " + enabledTime + " seconds,",
							"hitting players or being hit will break this invisibility.",
							"Cast Time: " + castTime + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack (Material.STAINED_GLASS_PANE);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		if(enabled.contains(p))
		{
			Language.sendAbilityUseMessage(p, "You are already using this.", this.getName());
			return false;
		}
		Runnable run = new Runnable()
		{
			@Override
			public void run() {
                if(AbilityUtils.isNoInvisible(p))
                {
                    return;
                }
		enabled.add(p);

		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se.setLocation(p.getLocation());
		se.particle = Particle.CRIT_MAGIC;
		se.radius = 1;
		se.particles = 10;
		se.yOffset = 0.6;
		se.iterations = 3;
		se.start();
		Language.sendAbilityUseMessage(p, "You turn invisible.", "Invisibility");
		SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se2.setEntity(p);
		se2.disappearWithOriginEntity = true;
		se2.infinite();
		se2.particle = Particle.SUSPENDED;
		se2.radius = 0.8;
		se2.particleOffsetY = (float) 0.5;
		se2.particles = 15;
		se2.yOffset = -0.8;
		se2.speed = (float) 0;
		se2.start();
		particles.put(p, se2);
		AbilityUtils.makeInvisible(p, "Invisibility");
		
		
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
        		        p.sendTitle(Utils.Colorate("&3&lInvisibility " + x + " seconds"), null, 0, 26, 0);
            		}
            		if(x <= 5)
            		{
        		        p.sendTitle(Utils.Colorate("&3&lInvisibility " + x + " seconds"), null, 0, 26, 0);
            		}
            		x--;
            	}else this.cancel();
            }
        }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 20L);
        countDown.put(p, countdown);
		}};
        if(!AbilityUtils.isNoInvisible(p))
        {
            AbilityUtils.castAbility(p, castTime, run);
            return true;
        }else
        {
        	Language.sendDefaultMessage(p, "You are unable to use this ability!");
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
			particles.get(p).cancel();
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.setLocation(p.getLocation());
			se.particle = Particle.CRIT_MAGIC;
			se.radius = 1;
			se.particles = 10;
			se.yOffset = 0.6;
			se.iterations = 3;
			se.start();
			particles.get(p).cancel();
			Language.sendAbilityUseMessage(p, "You reappear.", "Invisibility");
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
			Language.sendAbilityUseMessage(p, "You reappear.", "Invisibility");
		};
	}
	
	
	
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamage(EntityDamageByEntityEvent e)
	{
		
		if(e.isCancelled())
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
		if(e.isCancelled())
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

}