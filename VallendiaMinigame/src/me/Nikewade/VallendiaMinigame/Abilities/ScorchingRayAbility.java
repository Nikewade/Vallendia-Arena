package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.LineEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class ScorchingRayAbility implements Ability{
	int range = 100;
	int damage = 15;
	HashMap<Player, ArrayList<Block>> pb = new HashMap<>();
	ArrayList<Block> blocks = new ArrayList<>();
	List<Player> active = new ArrayList<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Scorching Ray";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Fire a long range blast of scorching sunlight which",
							"charges up for 2 seconds dealing " + damage + " damage and",
							"setting all enemies in the line on fire.");
		
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.DOUBLE_PLANT);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		if(active.contains(p))
		{
			return false;
		}
			
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BREATH, 3, 0.3F);
  		LineEffect se2 = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
		se2.particle = Particle.REDSTONE;
		se2.color = Color.fromRGB(94, 91, 76);
		se2.iterations = 3;
		se2.particles = 200;
		se2.speed = (float) 0;
		se2.visibleRange = 200;
		
  		LineEffect se = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
		se.particle = Particle.REDSTONE;
		se.color = Color.fromRGB(217, 151, 20);
		se.iterations = 3;
		se.particles = 100;
		se.speed = (float) 0;
		se.visibleRange = 200;
		se.delay = 10;
		
  		LineEffect se3 = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
		se3.particle = Particle.REDSTONE;
		se3.color = Color.fromRGB(255, 149, 0);
		se3.iterations = 3;
		se3.particles = 100;
		se3.speed = (float) 0;
		se3.visibleRange = 200;
		se3.delay = 20;
		
  		LineEffect se4 = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
		se4.particle = Particle.REDSTONE;
		se4.color = Color.fromRGB(255, 111, 0);
		se4.iterations = 3;
		se4.particles = 100;
		se4.speed = (float) 0;
		se4.visibleRange = 200;
		se4.delay = 25;
		
  		LineEffect se5 = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
		se5.particle = Particle.REDSTONE;
		se5.color = Color.fromRGB(237, 131, 50);
		se5.iterations = 3;
		se5.particles = 100;
		se5.speed = (float) 0;
		se5.visibleRange = 200;
		se5.delay = 30;
		
  		LineEffect se6 = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
		se6.particle = Particle.REDSTONE;
		se6.color = Color.fromRGB(245, 162, 29);
		se6.iterations = 3;
		se6.particles = 100;
		se6.speed = (float) 0;
		se6.visibleRange = 200;
		se6.delay = 35;
		
		
  		LineEffect se7 = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
		se7.particle = Particle.REDSTONE;
		se7.color = Color.WHITE;
		se7.iterations = 10;
		se7.particles = 200;
		se7.speed = (float) 0;
		se7.visibleRange = 200;
		se7.delay = 40;
		
		LineEffect se8 = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
		se8.particle = Particle.REDSTONE;
		se8.color = Color.YELLOW;
		se8.iterations = 10;
		se8.particles = 200;
		se8.speed = (float) 0;
		se8.visibleRange = 200;
		se8.delay = 40;
		
		active.add(p);
		pb.put(p, blocks);
		
			Location loc = p.getLocation().add(0, 1.4, 0);

			for(int i = 0; i < range+5 ; i++)
			{
				if(i >= range)
				{
					// on end of ray
					break;
				}
				
				pb.get(p).add(loc.getBlock());
				
				if(loc.getBlock().getType().isSolid())
				{
					loc = loc.getBlock().getLocation();
					break;
				}
			    loc = loc.add(loc.getDirection());			    
			}
			
		se.setLocation(p.getLocation().add(0, 1.2, 0));
		se.setTargetLocation(loc);
		se2.setLocation(p.getLocation().add(0, 1.2, 0));
		se2.setTargetLocation(loc);
		se3.setLocation(p.getLocation().add(0, 1.2, 0));
		se3.setTargetLocation(loc);
		se4.setLocation(p.getLocation().add(0, 1.2, 0));
		se4.setTargetLocation(loc);
		se5.setLocation(p.getLocation().add(0, 1.2, 0));
		se5.setTargetLocation(loc);
		se6.setLocation(p.getLocation().add(0, 1.2, 0));
		se6.setTargetLocation(loc);
		se7.setLocation(p.getLocation().add(0, 1.2, 0));
		se7.setTargetLocation(loc);
		se8.setLocation(p.getLocation().add(0, 1.2, 0));
		se8.setTargetLocation(loc);
		
		se2.start();
		se.start();
		se3.start();
		se4.start();
		se5.start();
		se6.start();
		se7.start();
		se8.start();

		
		new BukkitRunnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub			
				for(Block b : pb.get(p))
				{
         			for(Entity e : b.getWorld().getNearbyEntities(b.getLocation(), 0.5, 0.5, 0.5))
         			{
         				if(e instanceof LivingEntity && e != p)
         				{
                            if(AbilityUtils.partyCheck(p, (Player) e))
                            {
                                continue;
                            }
    
         					AbilityUtils.damageEntity((LivingEntity) e, p, damage);
         					e.setFireTicks(200);
         				}
         			}
         		
         			p.getWorld().playSound(se.getTarget(), Sound.ENTITY_EVOCATION_ILLAGER_CAST_SPELL, 10, 1.5F);
				}
			}
			
		}.runTaskLater(VallendiaMinigame.getInstance(), (long) (2*20));
		
		
		new BukkitRunnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
			if(active.contains(p))
			{
						active.remove(p);
			}
				pb.remove(p);
			}
			
		}.runTaskLater(VallendiaMinigame.getInstance(), 3*20);
		
		return true;
	}
	
	public static List<Block> select(Location loc1, Location loc2, World w){
		 
        //First of all, we create the list:
        List<Block> blocks = new ArrayList<Block>();
 
        //Next we will name each coordinate
        int x1 = loc1.getBlockX();
        int y1 = loc1.getBlockY();
        int z1 = loc1.getBlockZ();
 
        int x2 = loc2.getBlockX();
        int y2 = loc2.getBlockY();
        int z2 = loc2.getBlockZ();
 
        //Then we create the following integers
        int xMin, yMin, zMin;
        int xMax, yMax, zMax;
        int x, y, z;
 
        //Now we need to make sure xMin is always lower then xMax
        if(x1 > x2){ //If x1 is a higher number then x2
            xMin = x2;
            xMax = x1;
        }else{
            xMin = x1;
            xMax = x2;
        }
 
        //Same with Y
        if(y1 > y2){
            yMin = y2;
            yMax = y1;
        }else{
            yMin = y1;
            yMax = y2;
        }
 
        //And Z
        if(z1 > z2){
            zMin = z2;
            zMax = z1;
        }else{
            zMin = z1;
            zMax = z2;
        }
 
        //Now it's time for the loop
        for(x = xMin; x <= xMax; x ++){
            for(y = yMin; y <= yMax; y ++){
                for(z = zMin; z <= zMax; z ++){
                    Block b = new Location(w, x, y, z).getBlock();
                    blocks.add(b);
                }
            }
        }
 
        //And last but not least, we return with the list
        return blocks;
    }

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}