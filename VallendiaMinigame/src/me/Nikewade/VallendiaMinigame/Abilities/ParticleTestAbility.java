package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class ParticleTestAbility implements Ability{

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
	 	  new BukkitRunnable(){                         
              double t = 0;
              Location loc = p.getLocation();
            
              public void run(){
            	  //t effects speed of article
                      t = t + 0.5;
                      Vector direction = loc.getDirection().normalize();
                      double x = direction.getX() * t;
                      double y = direction.getY() * t + 1.5;
                      double z = direction.getZ() * t;
                      loc.add(x,y,z);
              		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
            		se.particle = Particle.FLAME;
            		se.iterations = 10;
            		se.particles = 10;
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
                      if (t > 500){
                          this.cancel();
                  }
                    
              }
 	 	  }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
		return false;
	}

}
