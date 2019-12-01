package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import de.slikey.effectlib.effect.EquationEffect;
import de.slikey.effectlib.util.DynamicLocation;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;

public class ParticleTestAbility implements Ability, Listener{

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
		
  		EquationEffect ee = new EquationEffect(VallendiaMinigame.getInstance().effectmanager);
  		ee.x2Equation = "sin (0.3 *t)";
  		ee.y2Equation = "0.25t";
  		ee.zEquation = "cos(0.3 * t)";
  		ee.duration = 99999;
  		ee.particles = 20;
  		ee.cycleMiniStep = false;
  		ee.setDynamicOrigin(new DynamicLocation(p.getLocation()));
  		ee.start();
		
		
		
		/*
	 	  new BukkitRunnable(){                         
              double t = 0;
              Location loc = p.getLocation();
            
              public void run(){
            	  //t effects speed of article
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
                      if (t > 50){
                          this.cancel();
                  }
                    
              }
 	 	  }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
		 */
		return false;
	}
	
    public static Listener getListener() {
        return new Listener() {
        

        };
    }

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
