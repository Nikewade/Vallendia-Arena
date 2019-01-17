package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
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
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.LineEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class ExplosiveArrowAbility implements Ability, Listener{
	private static ArrayList<Player> enabled = new ArrayList<>();

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
				"on impact. This arrow is slightly heavier.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SULPHUR);
	}

	@Override
	public boolean RunAbility(Player p) {
		enabled.add(p);
    	Language.sendAbilityUseMessage(p, "You ready your bow.", "Explosive Arrow");
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
                        	
                        	if(!enabled.contains(p))
                        	{
                        		return;
                        	}
                        	
                    		if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Explosive Arrow"))
                    		{
                    			return;
                    		}
                        	
                        	enabled.remove(p);
                        	
                    		block.setMetadata("Explosive Arrow", new FixedMetadataValue(VallendiaMinigame.getInstance(), block));
                        	block.getWorld().createExplosion(block.getLocation(), 3, true);
                        	
                        	arrow.remove();
                        	
                    		
                    		
                        	
                        }

                }
            }
            
            
            
            @EventHandler
            public  void onShoot (EntityShootBowEvent e)
            {
            	if(e.getEntity() instanceof Player && enabled.contains(e.getEntity()))
            	{
            		e.getProjectile().setVelocity(e.getProjectile().getVelocity().multiply(0.7));
            	}
            }
            
            
        	@EventHandler
        	public void onExplode(BlockExplodeEvent e)
        	{
        		if(e.getBlock().hasMetadata("Explosive Arrow"))
        		{
        			e.setYield(0);
            		for(Block b : e.blockList())
            		{
            				Utils.regenBlock(b, 30);
            				b.setType(Material.AIR);
            		}	
        		}
        	}
            
            
            
            
            
        };
    }
	
	
	
	
	
	

}
