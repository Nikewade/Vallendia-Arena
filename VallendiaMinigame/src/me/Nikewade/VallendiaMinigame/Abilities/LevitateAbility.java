package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class LevitateAbility implements Ability, Listener{
	//made by Emma
	
	private static List<Player> abilityActive = new ArrayList<>();
	private static List<Player> isLevitating = new ArrayList<>();
	private static List<LivingEntity> targetLevitating = new ArrayList<>();
	private static HashMap<Player,LivingEntity> storeTarget = new HashMap<>();
	private static HashMap<Player, BukkitTask> storetimers = new HashMap<>();
	private static int duration = 30;
	private static int amplifier = 2;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Levitate";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Leviate yourself by jumping or",
				"others by hitting them.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.FEATHER);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub	
		

		//player activates or deactivates ability:
		
		//checks if they have activated the ability and are not levitating anyone
		if(abilityActive.contains(p) && !isLevitating.contains(p) ||
				abilityActive.contains(p) && !targetLevitating.contains(p))
		{
			abilityActive.remove(p);
        	Language.sendAbilityUseMessage(p, "You are no longer infused with "
        			+ "magical energy.", "Levitate");
        	storetimers.get(p).cancel();
        	storetimers.remove(p);
        	
        	return false;
			
		}else
		{
		// checks if they havent activated the ability
			//checks if theyre levitating themself or someone else
			if(!p.hasPotionEffect(PotionEffectType.LEVITATION)) 
			{
			if(!isLevitating.contains(p) && (!targetLevitating.contains(p)))
			{		
				abilityActive.add(p);	
				Language.sendAbilityUseMessage(p, "You are infused with magical energy, "
						+ "Jump to levitate yourself or hit an enemy "
						+ "to make them levitate.", "Levitate");
		
				//gives them 15 seconds to use the levitate or it runs out 

				BukkitTask timer = new BukkitRunnable() {
	                @Override
	                public void run()
	                {
	                	if(abilityActive.contains(p))  
	                	{
	                	
	                	Language.sendAbilityUseMessage(p, "You are no longer infused with "
	                			+ "magical energy.", "Levitate");
	                	abilityActive.remove(p);
	                	
	                	
	                	}
	                	
	                }
	            
	            }.runTaskLater(VallendiaMinigame.getInstance(), 15*20);  	
				
				storetimers.put(p, timer);
			}
			
		}else
		{
			Language.sendAbilityUseMessage(p, "You are already levitating!", "Levitate");
		}
		
		}

		
		//player jumps with ability active
		//refer to player move event
		
		//player hits player with ability active
		//refer to entity damage by entity event
		
		
	//if caster is levitating themselves
		if(isLevitating.contains(p))
		{
			isLevitating.remove(p);
			Language.sendAbilityUseMessage(p, "You feel heavier", "Levitate");
			p.removePotionEffect(PotionEffectType.LEVITATION);	
			storetimers.get(p).cancel();
			storetimers.remove(p);
		}
		
	//if caster is levitating someone else
		if(targetLevitating.contains(p))
		{
			targetLevitating.remove(p);
			Language.sendAbilityUseMessage(storeTarget.get(p), "You feel heavier.2", "Levitate");
			storeTarget.get(p).removePotionEffect(PotionEffectType.LEVITATION);
			abilityActive.remove(p);
			storeTarget.remove(p);
			storetimers.get(p).cancel();
			storetimers.remove(p);

				
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
		
		if(isLevitating.contains(p))
		{
			p.removePotionEffect(PotionEffectType.LEVITATION);
			isLevitating.remove(p);
		}
		
		if(targetLevitating.contains(p))
		{
			targetLevitating.remove(p);
		}
		if(storeTarget.containsKey(p))
		{
			storeTarget.remove(p);
		}
		
		
		//REMOVE ALL LISTS ETC
		
	}
	
        	
        	@EventHandler
        	public void onJump (PlayerMoveEvent e)
        	{
        		Player p = e.getPlayer();
        		
        		// if player is jumping VV
        		if(e.getTo().getY() > e.getFrom().getY() && !e.getPlayer().isOnGround())
        		{
        			if(!p.hasPotionEffect(PotionEffectType.LEVITATION))
        			{
        			if(abilityActive.contains(p))
        			{
        				AbilityUtils.addPotionDuration(p, PotionEffectType.LEVITATION, amplifier, duration*20);
        				isLevitating.add(p);
        				abilityActive.remove(p);
        				
        				// if caster doesnt end levitating early all lists will be reset
        				BukkitTask timer2 = new BukkitRunnable() {
        	                @Override
        	                public void run()
        	                {
        	                	if(isLevitating.contains(p))  
        	                	{
        	 
        	                	isLevitating.remove(p);
        	        			Language.sendAbilityUseMessage(p, "You feel heavier.3", "Levitate");
        	                	
        	                	}
        	                	
        	                }
        	            
        	            }.runTaskLater(VallendiaMinigame.getInstance(), duration*20);  	
        				
        			storetimers.put(p, timer2);

        			}
        			
        		}else
        		{
        			if(abilityActive.contains(p))
        			{
        			Language.sendAbilityUseMessage(p, "You are already levitating!", "Levitate");
        			abilityActive.remove(p);
        			Language.sendAbilityUseMessage(p, "You are no longer infused with magical energy", "Levitate");
        			}
        		}
        			
        			
        		}
        		
        	}
        	
        	@EventHandler
        	public void onHit (EntityDamageByEntityEvent e)
        	{
        		if(e.getDamager() instanceof Player)
        		{
        		
        		Player p = (Player) e.getDamager();
        		LivingEntity target = (LivingEntity) e.getEntity();
        		
        		if(abilityActive.contains(p))
        		{
        			AbilityUtils.addPotionDuration(target, PotionEffectType.LEVITATION, amplifier, duration*20);
        			targetLevitating.add(p);
        			abilityActive.remove(p);
        			storeTarget.put(p, target);
        			
    				// if caster doesnt end levitation early all lists will be reset        			
    				BukkitTask timer3 = new BukkitRunnable() {
    	                @Override
    	                public void run()
    	                {
    	                	if(targetLevitating.contains(p))
    	                		
    	                	{
    	                		
        	                	targetLevitating.remove(p);
        	        			Language.sendAbilityUseMessage(target, "You feel heavier.4", "Levitate");
    	                		storeTarget.remove(p);
        	        			
    	                	}

    	                	
    	                }
    	            
    	            }.runTaskLater(VallendiaMinigame.getInstance(), duration*20);  
        		
    	            storetimers.put(p, timer3);
    	            
        		}
        		
        	}
        		
        		
        	}
        	        	
	
	

}