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
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class GrapplingHookAbility implements Ability, Listener {
	private static double forwardVelocity = 10 / 10D;;
	private static double upwardVelocity = 8 / 10D;
	private static ArrayList<Player> grappling = new ArrayList<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Grappling Hook";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	
	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Shoot your bow to travel to a location." , 
				"This makes your arrow a little heavier.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.TRIPWIRE_HOOK);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		grappling.add(p);
    	Language.sendAbilityUseMessage(p, "You ready your bow.", "Grappling Hook");
		new BukkitRunnable() {
            @Override
            public void run() {
            	if(grappling.contains(p))
            	{
                	grappling.remove(p);
                	Language.sendAbilityUseMessage(p, "You lower your bow.", "Grappling Hook");	
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
                        	
                        	if(!grappling.contains(p))
                        	{
                        		return;
                        	}
                        	
                    		if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Grappling Hook"))
                    		{
                    			return;
                    		}
                        	
                        	
                        	if(block.getY() <= p.getLocation().getBlockY()  || block.getY() - p.getLocation().getBlockY() < 4)
                        	{
                            	Language.sendAbilityUseMessage(p, "Hook too low.", "Grappling Hook");
                        		return;
                        	}
                        	
                        	if(p.getLocation().subtract(0, p.getLocation().getBlockY(), 0).distance(block.getLocation().subtract(0, block.getY(), 0)) > 30)
                        	{
                            	Language.sendAbilityUseMessage(p, "Hook went too far.", "Grappling Hook");
                        		return;
                        	}
                        	
                        	grappling.remove(p);
                    		
                			new BukkitRunnable() {
                            	int timer = 0;
                                @Override
                                public void run() {
                                	timer++;
                                	if(timer == 30)
                                	{
                                		this.cancel();
                                	}
                            		Vector blockv = block.getLocation().toVector();
                            		Vector playerv = p.getLocation().toVector();
                            		Vector v = blockv.subtract(playerv).normalize();
                            		v.multiply(forwardVelocity).setY(upwardVelocity);
                            		p.setVelocity(v);
                            		
                            		
                            		if(p.getLocation().distance(block.getLocation()) < 3 || p.getLocation().getBlockY() - block.getY() > 1)
                            		{
                            			this.cancel();
                            			p.setFallDistance(0);
                            			AbilityUtils.addPotionDuration(p, PotionEffectType.JUMP, 2, 3 * 20);
                            		}
                                }
                            }.runTaskTimer(VallendiaMinigame.getInstance(), 5L, 0L);  
                            
                    		LineEffect line = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
                    		line.setLocation(p.getLocation());
                    		line.setTargetLocation(e.getEntity().getLocation());
                    		line.iterations = 100;
                    		line.particleCount = 1;
                    		line.particle = Particle.CRIT;
                    		line.start();
    	        			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LEASHKNOT_PLACE, 2, 1);
                    		
                    		
                        	
                        }

                }
            }
            
            
            
            @EventHandler
            public  void onShoot (EntityShootBowEvent e)
            {
            	if(e.getEntity() instanceof Player && grappling.contains(e.getEntity()))
            	{
            		e.getProjectile().setVelocity(e.getProjectile().getVelocity().multiply(0.6));
            	}
            }
            
            
            
            
            
            
            
        };
    }
	
	
	
	
	
	

}
