package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class EnvenomAbility implements Ability, Listener {
	//Made by Emma
	private static int tickspd = 0;
	private static int amplifier = 0;	
	private static int duration = 15;
	private static List<Player> abilityActive = new ArrayList<>();
	private static HashMap<LivingEntity, BukkitTask> storeTimer = new HashMap<>();


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Envenom";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Your attacks from your sword apply poison to your ", 
				"target for the next " + duration + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return Utils.getPotionItemStack(PotionType.POISON, 2, false, false, "");
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(amplifier == 0)
		{
			tickspd = 25;
		}
		if (amplifier == 1)
		{
			tickspd = 12;
		}
		
		//if its their first time right clicking
		if(!abilityActive.contains(p))
		{
			abilityActive.add(p);
			Language.sendAbilityUseMessage(p, "Envenom now active.", "Envenom");

			
			BukkitTask timer = new BukkitRunnable() {
                @Override
                public void run()
                {
                	if(abilityActive.contains(p))
                	{
                		abilityActive.remove(p);
                		Language.sendAbilityUseMessage(p, "Envenom is no longer active.", "Envenom");
                	}
                }
            
            }.runTaskLater(VallendiaMinigame.getInstance(), duration*20);  
                     

            
		
		}else
		{
			Language.sendAbilityUseMessage(p, "Envenom is already active!", "Envenom");
			return false;
			
		}
		
		return true;	
		

	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(abilityActive.contains(p))
		{
			abilityActive.remove(p);
			
		}
		
		if(storeTimer.containsKey(p))
		{
			storeTimer.get(p).cancel();
			storeTimer.remove(p);
			
		}
	}
	
	
	@EventHandler
	public void onHit (EntityDamageByEntityEvent e)
	{
		if(!(e.getDamager() instanceof Player))
		{
			return;
		}
		if(!(e.getEntity() instanceof LivingEntity))
		{
			return;
		}
		
		
		LivingEntity target = (LivingEntity) e.getEntity();
		Player p = (Player) e.getDamager();
		
		if(abilityActive.contains(p))
		{
			if(p.getInventory().getItemInMainHand().getType() == Material.WOOD_SWORD ||
					p.getInventory().getItemInMainHand().getType() == Material.STONE_SWORD ||
					p.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD ||
					p.getInventory().getItemInMainHand().getType() == Material.GOLD_SWORD ||
					p.getInventory().getItemInMainHand().getType() == Material.DIAMOND_SWORD)
		{
				
			
			AbilityUtils.addPotionDuration(p ,target, this.getName(), PotionEffectType.POISON, amplifier, duration*20);

			
			BukkitTask timer2 = new BukkitRunnable() {
								
                @Override
                public void run()
                {
                	
                	if(target.isDead() == true)
                	{
                		this.cancel();
                	}

        			
                	if(target.hasPotionEffect(PotionEffectType.POISON))
                	{
                        
            	        SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
            	        se.particle = Particle.REDSTONE;
            	        se.color = Color.GREEN;
            	        se.radius = 1;
            	        se.particles = 5;
            	        se.speed = (float) 0;  
            	        se.iterations = 1;
            	        se.visibleRange = 50;
            	        
            	         
            	        se.setLocation(target.getLocation().add(0, 1, 0));
            	        
            	        se.start();
            	        
            	        
                	}else
                	{
                		this.cancel();
                	}
                }

            }.runTaskTimer(VallendiaMinigame.getInstance(), tickspd, tickspd); 
            
            storeTimer.put(p, timer2);
            
            
		}

		
	}
		
	}
	
	
    
}