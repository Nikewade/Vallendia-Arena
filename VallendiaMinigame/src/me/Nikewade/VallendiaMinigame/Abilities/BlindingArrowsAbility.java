package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.bukkit.potion.PotionType;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class BlindingArrowsAbility implements Ability, Listener {
    private static Map<Projectile,SphereEffect> arrow = new HashMap<>();
    private static int chance = 20;
    private static int duration = 3;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Blinding Arrows";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}
	
	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList(chance + "% chance to blind enemies for " + duration + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return Utils.getTippedArrowItemStack(PotionType.WEAKNESS, 1, false, false, "");
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
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
        		if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Blinding Arrows"))
        		{
        			return;
        		}
        		
        		int random = Utils.randomNumber(1, 100);
        		if(random <= chance)
        		{
            		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
            		se.setEntity(e.getProjectile());
            		se.particleCount = 1;
            		se.radius = 0.1;
            		se.particles = 1;
            		se.start();	
            		arrow.put((Projectile)e.getProjectile(), se);	
        		}
            }

            @EventHandler
            public void onland(ProjectileHitEvent e){
            	if(!arrow.containsKey(e.getEntity()))
            	{
            		return;
            	}
        		arrow.get(e.getEntity()).cancel();
                arrow.remove(e.getEntity());
        		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
        		se.setEntity(e.getEntity());
        		se.particleCount = 2;
        		se.radius = 0.8;
        		se.duration = 1;
        		se.start();	
        		if(e.getHitBlock() != null)
        		{
            		e.getHitBlock().getLocation().getWorld().playSound(e.getHitBlock().getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 1, (float) 0.7);	
        		}
        		if(e.getHitEntity() != null && e.getHitEntity() instanceof LivingEntity)
        		{
        			LivingEntity entity = (LivingEntity) e.getHitEntity();
        			AbilityUtils.addPotionDuration(entity, PotionEffectType.BLINDNESS, 0, duration * 20);
        			entity.getLocation().getWorld().playSound(entity.getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 1, 1);
        		}
         
            }
            
            
            
            
            
        };
    }
 
 

}
