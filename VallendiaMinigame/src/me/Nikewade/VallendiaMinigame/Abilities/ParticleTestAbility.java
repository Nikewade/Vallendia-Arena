package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import de.slikey.effectlib.effect.ColoredImageEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;

public class ParticleTestAbility implements Ability, Listener{
	ArrayList<Player> enabled = new ArrayList<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Particle Test";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("This is a test ability");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.BARRIER);
	}

	@Override
	public boolean RunAbility(Player p) {
		
  		ColoredImageEffect se = new ColoredImageEffect(VallendiaMinigame.getInstance().effectmanager);
  		se.fileName = "/home/mch/multicraft/servers/server61539/Untitled.png";
  		se.setLocation(p.getLocation().add(0, 2, 0));
  		se.pitch = p.getLocation().getPitch();
  		se.enableRotation = false;
  		se.transparency = true;
  		se.stepX = 3;
  		se.stepY = 3;
  		se.period = 14;
  		se.visibleRange = 20;
  		se.start();
		
		
		
		
		
		
		
		
//		try {
//			ActiveMob  ent = MythicMobs.inst().getAPIHelper().getMythicMobInstance(MythicMobs.inst().getAPIHelper().spawnMythicMob("WolfCompanion", p.getLocation()));
//
//		} catch (InvalidMobTypeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		

		
/*	 	  new BukkitRunnable(){      
              Location loc = p.getLocation();
              double t = 0;
              public void run(){
            	  //t effects speed of article
                      t = t + 1.7;
                      Location tloc = target.getLocation();
                      Vector direction = tloc.toVector().subtract(loc.toVector()).normalize();
                      double x = direction.getX() * t;
                      double y = direction.getY() * t + 1.5;
                      double z = direction.getZ() * t;
                      loc.add(x,y,z);
              		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
            		se.particle = Particle.CRIT_MAGIC;
            		se.radius = 0.2;
            		se.iterations = 10;
            		se.particles = 1;
            		se.speed = (float) 0;
            		se.visibleRange = 200;
            			se.setLocation(loc);
            			se.start();
            			if(loc.distance(tloc) <=2)
            			{
            				AbilityUtils.damageEntity(target, p, 3);
                            this.cancel();
            			}
                      loc.subtract(x,y,z);                   
                      if (t > 200){
                          this.cancel();
                  }
                    
              }
 	 	  }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);*/
		
		
		
		
		
		
		
		/*
	 	  new BukkitRunnable(){                         
              double t = 0;
            
              public void run(){
            	  //t effects speed of article
                  Location loc = p.getLocation();
                      t = t + 2;
                      Vector direction = loc.getDirection().normalize();
                      double x = direction.getX() * t;
                      double y = direction.getY() * t + 1.5;
                      double z = direction.getZ() * t;
                      loc.add(x,y,z);
              		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
            		se.particle = Particle.FLAME;
            		se.iterations = 10;
            		se.particles = 1;
            		se.speed = (float) 0;
            		se.visibleRange = 200;
            			se.setLocation(loc);
            			se.start();
            			if(loc.getBlock().getType().isSolid())
            			{
            				AbilityUtils.explode(loc, p, 4, 3, true, true, true);
                            this.cancel();
            			}
                      loc.subtract(x,y,z);
                      if (t > 200){
                          this.cancel();
                  }
                    
              }
 	 	  }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
		 */

		return false;
	}
	

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}
	
	
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		
	}

}
