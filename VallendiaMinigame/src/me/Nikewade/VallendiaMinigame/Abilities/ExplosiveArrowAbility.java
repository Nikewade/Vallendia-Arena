package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import net.minecraft.server.v1_12_R1.Explosion;

public class ExplosiveArrowAbility implements Ability, Listener{
	private static ArrayList<Player> enabled = new ArrayList<>();
	private static int damage = 8;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Explosive Arrow";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Allows you to shoot an arrow that explodes" , 
				"on impact, doing " + damage + " damage in a radius.",
				"This arrow is slightly heavier.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SULPHUR);
	}

	@Override
	public boolean RunAbility(Player p) {
		if(enabled.contains(p))
		{
			return false;
		}
    	Language.sendAbilityUseMessage(p, "You ready your bow.", "Explosive Arrow");
    	enabled.add(p);
		new BukkitRunnable() {
            @Override
            public void run() {
            	if(enabled.contains(p))
            	{
                	enabled.remove(p);
                	Language.sendAbilityUseMessage(p, "You lower your bow.", "Explosive Arrow");	
            	}
            }
        }.runTaskLater(VallendiaMinigame.getInstance(), 20 * 12);  
		return true;
	}
	
	
	
	
    public static Listener getListener() {
        return new Listener() {
            @EventHandler
            public void onProjectileHit(ProjectileHitEvent e) {
                 Entity ent = e.getEntity();
                    if (ent instanceof Arrow){
                        Arrow arrow = (Arrow) ent;
                        Block block = arrow.getLocation().getBlock();
                        if(arrow.getShooter() instanceof Player)
                        {
                        	Player p = (Player) arrow.getShooter();
                        	
                    		
                    		if(!arrow.hasMetadata("Explosive Arrow"))
                    		{
                    			return;
                    		}
                    		if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Explosive Arrow"))
                    		{
                    			return;
                    		}
                        	
                        	
                    		block.setMetadata("Explosive Arrow", new FixedMetadataValue(VallendiaMinigame.getInstance(), block));
                    		AbilityUtils.explode(block.getLocation(), p, 4, 8, true, true, true);
                        	
                        	arrow.remove();
                        	
                    		
                    		
                        	
                        }

                }
            }
            
            
            
            @EventHandler
            public  void onShoot (EntityShootBowEvent e)
            {

            	if(!enabled.contains(e.getEntity()) || !(e.getEntity() instanceof Player))
            	{
            		return;
            	}
            	Player p = (Player) e.getEntity();
        		if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Explosive Arrow"))
        		{
        			return;
        		}
            		e.getProjectile().setVelocity(e.getProjectile().getVelocity().multiply(0.6));
            		e.getProjectile().setMetadata("Explosive Arrow", new FixedMetadataValue(VallendiaMinigame.getInstance(), e.getProjectile()));
                	enabled.remove(p);
            }
            
            
            
            
            
            
        };
    }
	
	
	
	

}
