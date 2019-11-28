package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class MagicArrowsAbility implements Ability, Listener{
	private static ArrayList<Player> enabled = new ArrayList<>();
	private static ArrayList<Projectile> arrow = new ArrayList<>();
    private static Map<Projectile,SphereEffect> arrowParticle = new HashMap<>();
	private static int despawnTime = 15;
	private static int enabledTime = 15;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Magic Arrows";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Your arrows magically ignore gravity " , "for " + enabledTime + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SPECTRAL_ARROW);
	}

	@Override
	public boolean RunAbility(Player p) {
		if(enabled.contains(p))
		{
			return false;
		}
		Language.sendAbilityUseMessage(p, "Your arrows are infused with magic for " + enabledTime + " seconds.", "Magic Arrows");
		enabled.add(p);
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, (float) 0.5);
		
		new BukkitRunnable() {
            @Override
            public void run() {
        		if(enabled.contains(p))
        		{
        			enabled.remove(p);
        			Language.sendAbilityUseMessage(p, "The magic in your arrows fade.", "Magic Arrows");
        		};
            }
        }.runTaskLaterAsynchronously(VallendiaMinigame.getInstance(), enabledTime*20L);
        
		return true;
	}

	
	
	
    public static Listener getListener() {
        return new Listener() {
        	
        	
        	
        	
            @EventHandler
            public void onShootBow(EntityShootBowEvent e){
            	
            	if(!(e.getEntity() instanceof Player))
            	{
            		return;
            	}
            	Player p = (Player) e.getEntity();
        		if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Magic Arrows"))
        		{
        			return;
        		}
        		if(!enabled.contains(p))
        		{
        			return;
        		}
        		Projectile projectile = (Projectile) e.getProjectile();
        		e.getProjectile().setGravity(false);
        		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
        		se.particle = Particle.CRIT_MAGIC;
        		se.setEntity(e.getProjectile());
        		se.particleCount = 1;
        		se.radius = 0.1;
        		se.particles = 1;
        		se.start();	
        		arrow.add(projectile);
        		arrowParticle.put(projectile, se);
    			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_SHOOT, 2, (float) 1.4);
        		
        		new BukkitRunnable() {
                    @Override
                    public void run() {
                    	arrowParticle.get(projectile).cancel();
            			e.getProjectile().setGravity(true);
            			arrow.remove(projectile);
                    }
                }.runTaskLaterAsynchronously(VallendiaMinigame.getInstance(), despawnTime*20L);

            }

            @EventHandler
            public void onland(ProjectileHitEvent e){
            	if(!arrow.contains(e.getEntity()))
            	{
            		return;
            	}
            	e.getEntity().setGravity(true);
            	arrow.remove(e.getEntity());
            	arrowParticle.get(e.getEntity()).cancel();

         
            }
 
        };
    }
    
    
    
    public static void gravatizeArrows()
    {
    	for(Projectile a : arrow)
    	{
    		a.setGravity(true);
    	}
    }

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}
}
