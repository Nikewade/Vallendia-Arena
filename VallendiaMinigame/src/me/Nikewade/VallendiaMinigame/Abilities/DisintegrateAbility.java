package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class DisintegrateAbility implements Ability{
<<<<<<< HEAD
	int percent = 80;
=======
	int percent = 60;
>>>>>>> second-repo/master
	int castTime = 3;
	double speed = 0.2;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Disintegrate";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		return Arrays.asList("Shoot a very slow green orb that deals",
				percent +"% of the targets max health.",
				Utils.Colorate("&8Cast: " + castTime + " seconds."));
=======
		return Arrays.asList("Shoot a very slow green orb that deals damage",
				"equal to " + percent +"% of the targets max health.",
				Utils.Colorate("&8Cast: " + castTime + " seconds"));
>>>>>>> second-repo/master
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SUGAR);
	}

	@Override
	public boolean RunAbility(Player p) {
		Runnable run = new Runnable()
		{

			@Override
			public void run() {
			 	  new BukkitRunnable(){                         
		              double t = 0;
		              Location loc = p.getLocation();
		            
		              public void run(){
		            	  //t effects speed of article
		                      t = t + speed;
		                      Vector direction = loc.getDirection().normalize();
		                      double x = direction.getX() * t;
		                      double y = direction.getY() * t + 1.5;
		                      double z = direction.getZ() * t;
		                      loc.add(x,y,z);
		              		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		              		se.color = Color.LIME;
		            		se.radius = 0.4;
		            		se.iterations = 10;
		            		se.particles = 1;
		            		se.speed = (float) 0;
		            		se.visibleRange = 50;
		            			se.setLocation(loc);
		            			se.start();
			              		SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			              		se2.particle = Particle.TOWN_AURA;
			            		se2.radius = 0.4;
			            		se2.iterations = 10;
			            		se2.particles = 10;
			            		se2.speed = (float) 0;
			            		se2.visibleRange = 50;
			            			se2.setLocation(loc);
			            			se2.start();
			    					for(Entity e : loc.getWorld().getNearbyEntities(loc, 0.6, 0.6, 0.6))
			    					{
			    						if(e instanceof LivingEntity && e != p)
			    						{
			    							LivingEntity ent = (LivingEntity) e;
			    							AbilityUtils.damageEntity((LivingEntity)e, p, (int) ((ent.getMaxHealth() * percent) / 100));
			    							this.cancel();
			    						}
			    					}
		            			if(loc.getBlock().getType().isSolid())
		            			{
		                            this.cancel();
		            			}
		                      loc.subtract(x,y,z);
		                      if (t > 100){
		                          this.cancel();
		                  }
		                    
		              }
		 	 	  }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
				
			}
			
		};
	
	AbilityUtils.castAbility(p, castTime, run);
	return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
