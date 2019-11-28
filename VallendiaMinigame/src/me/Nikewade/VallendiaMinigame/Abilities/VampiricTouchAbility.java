package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class VampiricTouchAbility implements Ability, Listener{
	private static ArrayList<Player> enabled = new ArrayList<>();
	private int time = 5;
	private static int Percent = 30;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Vampiric Touch";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("For " + time + " seconds, " + Percent + "% of the melee damage",
				"you deal, is canceled and instead heals",
				"you for the amount.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.GHAST_TEAR);
	}

	@Override
	public boolean RunAbility(Player p) {
		if(enabled.contains(p))
		{
			return false;
		}
		Language.sendAbilityUseMessage(p, "Your hits now drain your target of life.", "Vampiric Touch");
		enabled.add(p);
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_DEATH, 2, (float) 0.7);
		
		new BukkitRunnable() {
            @Override
            public void run() {
        		if(enabled.contains(p))
        		{
        			enabled.remove(p);
        			Language.sendAbilityUseMessage(p, "The vampiric magic fades.", "Vampiric Touch");
        		};
            }
        }.runTaskLater(VallendiaMinigame.getInstance(), time*20L);
        
		return true;
	}
	
	
    public static Listener getListener() {
        return new Listener() {
        	
        	@EventHandler
        	public void onDamage(EntityDamageByEntityEvent e)
        	{
    			if(!(e.getDamager() instanceof Player))
    			{
    				return;
    			}
        		
        		double lowerPercent =  Utils.getPercentHigherOrLower(Percent, false);
        		double damage = e.getDamage();
        		double finalDamage = e.getFinalDamage();
        		double lowerDamage = damage * lowerPercent;

        		if(e.getEntity() instanceof LivingEntity && e.getCause() == DamageCause.ENTITY_ATTACK)
        		{
        			Player damager =  (Player) e.getDamager();
        			LivingEntity target = (LivingEntity) e.getEntity();
        			
                		if(enabled.contains(damager))
                		{
                				e.setDamage(lowerDamage);
                				AbilityUtils.healEntity(damager, (finalDamage - e.getFinalDamage()));
                				damager.getWorld().playSound(damager.getLocation(), Sound.ENTITY_GENERIC_DRINK, 2, (float) 1.6);
                				
                				LineEffect line = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
                				line.setLocation(damager.getLocation().add(0, 1, 0));
                				line.setTargetLocation(target.getLocation().add(0, 1, 0));
                				line.particles = 5;
                				line.particle = Particle.REDSTONE;
                				line.iterations = 1;
                				line.start();
                				
                				SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
                				se.setEntity(target);
                				se.particle = Particle.REDSTONE;
                				se.iterations = 1;
                				se.radius = 0.9;
                				se.yOffset = -0.8;
                				se.speed = (float) 0;
                				se.start();
                	 	 		
                		}	
        		}
        	}
        	
        	
        	
        };
    }

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}
}
