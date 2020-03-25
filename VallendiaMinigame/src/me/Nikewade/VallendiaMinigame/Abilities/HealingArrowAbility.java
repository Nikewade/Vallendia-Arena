package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
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
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class HealingArrowAbility implements Ability, Listener {

	Map<Projectile,SphereEffect> arrow = new HashMap<>();
    int heal = 10;
    int delay = 15;
	ArrayList<Player> abilityActive = new ArrayList<>();
    

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Healing Arrow";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	
	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Your next Arrow heals for " + heal + " health. This works on enemies too.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return Utils.getTippedArrowItemStack(PotionType.REGEN, 1, false, false, "");
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		if(abilityActive.contains(p))
		{
			return false;
		}else
		{
			abilityActive.add(p);
			Language.sendAbilityUseMessage(p, "Healing Arrow is now Active", "Healing Arrow");
			
			new BukkitRunnable()
			{

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(abilityActive.contains(p))
					{
					
					abilityActive.remove(p);
					Language.sendAbilityUseMessage(p, "Healing Arrow is no longer active", "Healing Arrow");
					
					}
				}
				
			}.runTaskLater(VallendiaMinigame.getInstance(), delay*20);
			return true;
		}

	}
	
	
	
        	
            @EventHandler
            public void onShootBow(EntityShootBowEvent e){
            	
            	if(!(e.getEntity() instanceof Player))
            	{
            		return;
            	}
            	
            	Player p = (Player) e.getEntity();
        		if(!(abilityActive.contains(p)))
        		{
        			return;
        		}
        		

        		{
            		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
            		se.setEntity(e.getProjectile());
            		se.particle = Particle.REDSTONE;
            		se.color = Color.FUCHSIA;
            		se.particleCount = 1;
            		se.radius = 0.1;
            		se.particles = 1;
            		se.start();	
            		arrow.put((Projectile)e.getProjectile(), se);	
        		}
        		abilityActive.remove(p);
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
        		se.particle = Particle.REDSTONE;
        		se.particleCount = 2;
        		se.radius = 0.8;
        		se.duration = 1;
        		se.color = Color.FUCHSIA;
        		se.start();	
        		if(e.getHitBlock() != null)
        		{
            		e.getHitBlock().getLocation().getWorld().playSound(e.getHitBlock().getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 2, (float) 0.7);	
        		}
        		if(e.getHitEntity() != null && e.getHitEntity() instanceof LivingEntity)
        		{
        			LivingEntity entity = (LivingEntity) e.getHitEntity();
        			AbilityUtils.healEntity(entity, heal);
        			entity.getLocation().getWorld().playSound(entity.getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 2, 1);
        		}
         
            }
            

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(abilityActive.contains(p))
		{
			abilityActive.remove(p);
		}
		
	}
 
 

}