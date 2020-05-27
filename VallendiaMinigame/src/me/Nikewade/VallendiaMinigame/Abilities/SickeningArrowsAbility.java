package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
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
import org.bukkit.potion.PotionType;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class SickeningArrowsAbility implements Ability, Listener {
    private static Map<Projectile,SphereEffect> arrow = new HashMap<>();
    private static int chance = 25;
    private static int duration = 10;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Sickening Arrows";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	
	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList(chance + "% chance to sicken enemies for " + duration + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return Utils.getTippedArrowItemStack(PotionType.JUMP, 1, false, false, "");
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
        	
            @EventHandler
            public void onShootBow(EntityShootBowEvent e){
            	
            	if(!(e.getEntity() instanceof Player))
            	{
            		return;
            	}
            	Player p = (Player) e.getEntity();
        		if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Sickening Arrows"))
        		{
        			return;
        		}
        		
        		int random = Utils.randomNumber(1, 100);
        		if(random <= chance)
        		{
            		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
            		se.setEntity(e.getProjectile());
            		se.color = Color.LIME;
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
        		se.color = Color.LIME;
        		se.start();	
        		if(e.getHitBlock() != null)
        		{
            		e.getHitBlock().getLocation().getWorld().playSound(e.getHitBlock().getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 2, (float) 0.7);	
        		}
        		if(e.getHitEntity() != null && e.getHitEntity() instanceof LivingEntity)
        		{
        			LivingEntity entity = (LivingEntity) e.getHitEntity();
        			if(entity instanceof Player)
        			{
                        if(((Player) entity).isBlocking())
                        {
                            return;
                        }
                		if(!AbilityUtils.runPassive((Player)e.getEntity().getShooter(), (Player)entity))
                		{
                			return;
                		}	
        			}
        			AbilityUtils.addPotionDuration((LivingEntity)e.getEntity().getShooter(), entity, this.getName(), PotionEffectType.CONFUSION, 3, duration * 20);
        			entity.getLocation().getWorld().playSound(entity.getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 2, 1);
        		}
         
            }
            

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}
 
 

}
