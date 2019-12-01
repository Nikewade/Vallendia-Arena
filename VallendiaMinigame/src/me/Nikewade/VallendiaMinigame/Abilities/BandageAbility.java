package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class BandageAbility implements Ability{
	private static HashMap<LivingEntity, BukkitTask> enabled = new HashMap<>();
	int percent = 40;
	int castTime = 5;
	double healTime = 10;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Bandage";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Apply bandages to your wounds healing",
				percent + "% of your max health over "  +(int)healTime+ " seconds.",
				"If a party member is within 4 blocks of you,",
				"target that player to heal them instead.",
				"Healing another player will use their max health.",
				Utils.Colorate("&8Softcast: " + castTime));
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.PAPER);
	}

	@Override
	public boolean RunAbility(Player p) {
		
		Runnable run = new Runnable()
		{

			@Override
			public void run() {
		 		LivingEntity target = AbilityUtils.getHealingTarget(p, 4);
		 		if(target != null)
		 		{
		 			if(enabled.containsKey(target))
		 			{
		 				enabled.get(target).cancel();
		 				enabled.remove(target);
		 			}
		 			;
		 			double healthToHeal = ((target.getMaxHealth() * percent) / 100) / healTime;
		 			double startHealth = target.getHealth();
		 			double maxHealth = target.getMaxHealth();
		 			
		 			BukkitTask task = new BukkitRunnable() {
			 			double newHealth = target.getHealth();
		 	            @Override
		 	            public void run() {
		 	            	newHealth = newHealth + healthToHeal;
				    	 	if(newHealth >= startHealth + (maxHealth * percent) / 100 || target.getHealth() >= maxHealth)
				    	 	{
			 	            	this.cancel();
			 	            	return;
				    	 	}
		 	            	AbilityUtils.healEntity(target, healthToHeal);
		 	            	
		 	            }
		 	        }.runTaskTimer(VallendiaMinigame.getInstance(), 0 , 20L);  
		 	        enabled.put(target, task);
		 	        
		 	        
        			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
        			se.setEntity(target);
        			se.disappearWithOriginEntity = true;
        			se.particle = Particle.VILLAGER_HAPPY;
        			se.radius = 1.5;
        			se.iterations = 10;
        			se.particles = 2;
        			se.yOffset = -0.8;
        			se.speed = (float) 0;
        			se.start();
        			p.getWorld().playSound(target.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 1, (float)1);
        			Language.sendAbilityUseMessage((Player)target, "Your wounds have been bandaged.", "bandage");
		 	        
		 	        
		 			return;
		 		}
		 		
		 		
		 		
		 		
	 			if(enabled.containsKey(p))
	 			{
	 				enabled.get(p).cancel();
	 				enabled.remove(p);
	 			}
	 			double healthToHeal = ((p.getMaxHealth() * percent) / 100) / healTime;
	 			double startHealth = p.getHealth();
	 			double maxHealth = p.getMaxHealth();
	 			
	 			BukkitTask task = new BukkitRunnable() {
		 			double newHealth = p.getHealth();
	 	            @Override
	 	            public void run() {
	 	            	newHealth = newHealth + healthToHeal;
			    	 	if(newHealth >= startHealth + (maxHealth * percent) / 100 || p.getHealth() >= maxHealth)
			    	 	{
		 	            	this.cancel();
		 	            	return;
			    	 	}
	 	            	AbilityUtils.healEntity(p, healthToHeal);
	 	            	
	 	            }
	 	        }.runTaskTimer(VallendiaMinigame.getInstance(), 0 , 20L);  
	 	        enabled.put(p, task);
	 	        
    			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
    			se.setEntity(p);
    			se.disappearWithOriginEntity = true;
    			se.particle = Particle.VILLAGER_HAPPY;
    			se.radius = 1.5;
    			se.iterations = 10;
    			se.particles = 2;
    			se.yOffset = -0.8;
    			se.speed = (float) 0;
    			se.start();
    			p.getWorld().playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 1, (float)1);
		 		
		 		
		 		
				
			}
			
		};
		AbilityUtils.softCastAbility(p, castTime, run);

		return true;
	}

	
	public static void removeBandage(LivingEntity p)
	{
		if(enabled.containsKey(p))
		{
			enabled.get(p).cancel();
			enabled.remove(p);
		}
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}
	
}
