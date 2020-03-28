package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class UndyingRageAbility implements Listener, Ability {
	int percent = 50;
	int period = 15;
	ArrayList<Player> raging = new ArrayList<>();
	ArrayList<Player> abilityActive = new ArrayList<>();
	Map <Player, BukkitTask> timers = new HashMap<>();
	Map <Player, BukkitTask> tasks = new HashMap<>();
	Map <Player, BukkitTask> particles = new HashMap<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Undying Rage";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Reagents: 20 Netherwart");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.BLAZE_POWDER);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(abilityActive.contains(p))
		{
			abilityActive.remove(p);
		}
		if(timers.containsKey(p))
		{
			timers.get(p).cancel();
		}
		if(tasks.containsKey(p))
		{
			tasks.get(p).cancel();
		}
		if(raging.contains(p))
		{
			raging.remove(p);
		}
		
	}
	
	@EventHandler
	public void onDeath (EntityDamageEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			
		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Undying Rage"))
		{
			if(raging.contains(p))
			{
				return;
			}
			
			if(p.getHealth() - e.getDamage() <= 0)
			{

		        ArrayList<ItemStack> items = new ArrayList<>();

		        items.add(new ItemStack(372, 20));
		        
		        if(!AbilityUtils.hasReagentsMultiple(p, items,  "Undying Rage", true))
		        {
		            return;
		        }
				
				e.setCancelled(true);
				abilityActive.add(p);
				raging.add(p);
				p.sendTitle(Utils.Colorate("&4&l Undying Rage!") , Utils.Colorate("&4 You have 15 seconds"));
				
				  BukkitTask task = new BukkitRunnable() {

			            @Override
			            public void run() {	
			        		int dist = -10000 * 10 + 1300000;
			        		Utils.sendWorldBorderPacket(p, dist, 200000D, 200000D, 0);
			            }
				    }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
				    
				    
				    tasks.put(p, task);
				    
	            	p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WOLF_GROWL, 2, 0.6F);
	    			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 0.5F, (float) 0.4);
	    			
					  BukkitTask particle = new BukkitRunnable() {

				            @Override
				            public void run() {	
				            	
						  		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
						  		
						  		se.particle = Particle.FLAME;
						  		se.radius = 0.3F;
						  		se.iterations = 2;
						  		se.particleCount = 1;
				                se.particleOffsetX = 0.3F;
				                se.particleOffsetZ = 0.3F;
						  		se.setLocation(p.getLocation());
						  		se.start();
						  		
						  		SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
						  		
						  		se2.particle = Particle.SMOKE_NORMAL;
						  		se2.radius = 0.3F;
						  		se2.iterations = 2;
						  		se2.particleCount = 1;
						  		se2.setLocation(p.getLocation());
						  		se2.start();
						  		
						  		Location particleloc = p.getLocation().add(0,1,0);
						  		
						  		SphereEffect se5 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
						  		
						  		se5.particle = Particle.EXPLOSION_NORMAL;
						  		se5.radius = 0.1F;
						  		se5.iterations = 1;
						  		se5.particleCount = 1;
						  		se5.setLocation(particleloc);
						  		se5.start();
				            	

				
				            }
					    }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 40);

				particles.put(p, particle);
				
				double lowpercent = Utils.getPercentHigherOrLower(percent, false);
			
				p.setHealth(p.getMaxHealth()*lowpercent);

				
					BukkitTask timer2 = new BukkitRunnable()
						{

							@Override
							public void run() {
								// TODO Auto-generated method stub
								
								if(abilityActive.contains(p))
								{
									abilityActive.remove(p);
									AbilityUtils.damageEntity(p, (LivingEntity) p.getLastDamageCause().getEntity(), (int) p.getMaxHealth()+100000);
								}
							}
					
						}.runTaskLater(VallendiaMinigame.getInstance(), period*20);
				
				timers.put(p, timer2);
				
			}
		}
		}
		
	}
	
	@EventHandler 
	public void onActualDeath (EntityDeathEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			
			if(abilityActive.contains(p))
			{
				abilityActive.remove(p);
			}
			if(timers.containsKey(p))
			{
				timers.get(p).cancel();
			}
			if(tasks.containsKey(p))
			{
				tasks.get(p).cancel();
			}
			if(raging.contains(p))
			{
				raging.remove(p);
			}
			if(particles.containsKey(p))
			{
				particles.get(p).cancel();
			}
			
			if(p.getKiller() instanceof Player)
			{
				Player attacker = p.getKiller();
				if(abilityActive.contains(attacker))
				{
					abilityActive.remove(attacker);
					timers.get(attacker).cancel();
					tasks.get(attacker).cancel();
					particles.get(attacker).cancel();
				}
				if(raging.contains(attacker))
				{
					raging.remove(attacker);
				}
			}
			
		}
	}

}