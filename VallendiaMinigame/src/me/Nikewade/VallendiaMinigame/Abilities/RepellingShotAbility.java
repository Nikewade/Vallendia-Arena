package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class RepellingShotAbility implements Ability, Listener {
	 //made by Emma
	Map<Projectile,SphereEffect> arrow = new HashMap<>();
	ArrayList<Player> abilityActive = new ArrayList<>();
	Map<Player, BukkitTask> timers = new HashMap<>();
	int force = 25;
	int yForce = 30;
	int maxYForce = 10;
    int heal = 10;
    int delay = 15;
	int radius = 8;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Repelling Shot";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You repel yourself and all entities",
							"within a " + radius + " block radius");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack (Material.QUARTZ);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		if(abilityActive.contains(p))
		{
			return false;
		}else
		{
			abilityActive.add(p);
			Language.sendAbilityUseMessage(p, "Repelling Shot is now Active", "Repelling Shot");
			
			BukkitTask timer = new BukkitRunnable()
			{

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(abilityActive.contains(p))
					{
					
					abilityActive.remove(p);
					Language.sendAbilityUseMessage(p, "Repelling Shot is no longer active", "Repelling Shot");
					
					}
				}
				
			}.runTaskLater(VallendiaMinigame.getInstance(), delay*20);
			
			timers.put(p, timer);
			return true;
		}

	}
	
	
        	
            @EventHandler
            public void onShootBow(EntityShootBowEvent e){
            	
            	if(!(e.getEntity() instanceof Player))
            	{
            		return;
            	}
            	
            	Player p = (Player) e.getEntity();
        		if(!(abilityActive.contains(p)))
        		{
        			return;
        		}
        		

        		{
            		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
            		se.setEntity(e.getProjectile());
            		se.particle = Particle.CLOUD;
            		se.particleCount = 1;
            		se.radius = 0.1;
            		se.particles = 1;
            		se.start();	
            		arrow.put((Projectile)e.getProjectile(), se);	
        		}
        		if(timers.containsKey(p))
        		{
        			timers.get(p).cancel();
        		}
        		abilityActive.remove(p);
            }

            @EventHandler
            public void onland(ProjectileHitEvent e){
            	if(!arrow.containsKey(e.getEntity()))
            	{
            		return;
            	}
            	if(!(e.getEntity().getShooter() instanceof Player))
            	{
            		return;
            	}
            	
            	Player p = (Player) e.getEntity().getShooter();
        		arrow.get(e.getEntity()).cancel();
                arrow.remove(e.getEntity());

				p.getWorld().playSound( p.getLocation(), Sound.UI_TOAST_IN, 8, 0.4F);
				
        		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
        		se.setEntity(e.getEntity());
        		se.particle = Particle.CLOUD;
        		se.particleCount = 1;
        		se.radius = 8;
        		se.duration = 2;
        		se.start();	
        		if(e.getHitBlock() != null)
        		{
            		
        			Location loc = e.getHitBlock().getLocation();
        			Location location = loc.clone();
					location.add(0.0D, -1.0F, 0.0D);
				    Vector t = location.toVector();
        			
        			
        			for(Entity entity : getTargets(p, loc, radius, radius, radius))
        			{
				          Vector e2 = entity.getLocation().toVector();
				          Vector v = e2.subtract(t).normalize().multiply(force / 10.0D);
				          if (force != 0) {
				            v.setY(v.getY() * (yForce / 10.0D));
				          } else {
				            v.setY(yForce / 10.0D);
				          }
				          if (v.getY() > maxYForce / 10.0D) {
				              v.setY(maxYForce / 10.0D);
				            }
				          entity.setVelocity(v);
        			}
        			
        		}
        		if(e.getHitEntity() != null && e.getHitEntity() instanceof LivingEntity)
        		{	
        			
        			Location entloc = e.getHitEntity().getLocation();
        	      	Location location = entloc.clone();
					location.add(0.0D, -1.0F, 0.0D);
				    Vector t = location.toVector();
        			
        			
        			for(Entity entity : getTargets(p, entloc, radius, radius, radius))
        			{
				          Vector e2 = entity.getLocation().toVector();
				          Vector v = e2.subtract(t).normalize().multiply(force / 10.0D);
				          if (force != 0) {
				            v.setY(v.getY() * (yForce / 10.0D));
				          } else {
				            v.setY(yForce / 10.0D);
				          }
				          if (v.getY() > maxYForce / 10.0D) {
				              v.setY(maxYForce / 10.0D);
				            }
				          entity.setVelocity(v);
        			}
        			
        		}
         
            }
            
            
	public Collection<Entity> getTargets(Player originplayer, Location loc, double Radiusx, double Radiusy, double Radiusz)
	{
		Collection<Entity> nearbyEntities = new ArrayList<Entity>();
		for(Entity entity : loc.getWorld().getNearbyEntities(loc, Radiusx, Radiusy, Radiusz))
		{
			if(entity instanceof LivingEntity && !(entity instanceof ArmorStand))
			{
				if(entity instanceof Player)
				{
					Player entityplayer = (Player) entity;
					
					
					if(!(entityplayer.getGameMode() == GameMode.SURVIVAL) && !(entityplayer.getGameMode() == GameMode.ADVENTURE))
		        	{
						continue;
					}
				}
				nearbyEntities.add(entity);
				continue;
			}
		}
		return nearbyEntities;
	}

		
	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(abilityActive.contains(p))
		{
			abilityActive.remove(p);
		}
	}

}