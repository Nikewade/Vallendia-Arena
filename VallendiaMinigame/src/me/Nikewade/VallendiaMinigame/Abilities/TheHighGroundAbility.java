package me.Nikewade.VallendiaMinigame.Abilities;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftAreaEffectCloud;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
<<<<<<< HEAD
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
=======
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;
>>>>>>> second-repo/master

public class TheHighGroundAbility implements Ability, Listener{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "The High Ground";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("When higher than your enemy,", "and on the ground,", "deal 50% more damage and take" , "20% less damage.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SMOOTH_STAIRS);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
            	@EventHandler
            	public void onDamage(EntityDamageByEntityEvent e)
            	{
            		double lowerPercent =  0.8 ; //20%
            		double higherPercent =  1.5 ; //50%
<<<<<<< HEAD
            		double damage = e.getDamage();
            		double lowerDamage = damage * lowerPercent;
            		double higherDamage = damage * higherPercent;
=======
            		double damage = e.getFinalDamage();
            		double lowerDamage = damage * lowerPercent;
            		double higherDamage = damage * higherPercent;
            		
>>>>>>> second-repo/master

            		if(e.getEntity() instanceof LivingEntity || e.getDamager() instanceof Projectile)
            		{
            			LivingEntity damager = null;
            			LivingEntity target = (LivingEntity) e.getEntity();
            			
                		//person attacking has the ability
            			if(e.getDamager() instanceof Player || e.getDamager() instanceof Projectile)
            			{
                			if(e.getDamager() instanceof Projectile)
                			{
                				Projectile p = (Projectile) e.getDamager();
                				if(p.getShooter() instanceof Player)
                				{
                    				damager = (Player) p.getShooter();	
                				}
                			}else damager = (Player) e.getDamager();	
                    		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility((Player)damager, "The High Ground"))
                    		{
                    			if(damager.getLocation().getY() > target.getLocation().getY() && damager.isOnGround() || damager.isInsideVehicle())
                    			{
<<<<<<< HEAD
                    				e.setDamage(higherDamage);
=======
                        			e.setDamage(0);
                    				e.setDamage(DamageModifier.ARMOR, higherDamage);
>>>>>>> second-repo/master
                    	 	 		target.getWorld().playSound(target.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 2, 1);
                    	 	 		target.getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0, 1.8, 0), 20);
                    			}
                    		}	
            			}
                		
                		
            			//person getting damaged has the ability
                		if(target instanceof Player)
                		{
                			LivingEntity targetP = target;
                			if(e.getDamager() instanceof Projectile)
                			{
                				Projectile p = (Projectile) e.getDamager();
<<<<<<< HEAD
                				damager = (Player) p.getShooter();
=======
                				if(p.getShooter() instanceof Player)
                				{
                    				damager = (Player) p.getShooter();
                				}

>>>>>>> second-repo/master
                			}else if(e.getDamager() instanceof LivingEntity) damager = (LivingEntity) e.getDamager();	
                    		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility((Player) targetP, "The High Ground"))
                    		{
                    			if(targetP.getLocation().getY() > damager.getLocation().getY() && targetP.isOnGround())
                    			{
<<<<<<< HEAD
                    				e.setDamage(lowerDamage);	
=======
                        			e.setDamage(0);
                    				e.setDamage(DamageModifier.ARMOR, lowerDamage);
>>>>>>> second-repo/master
                    	 	 		damager.getWorld().playSound(target.getLocation(), Sound.ITEM_SHIELD_BLOCK, 2, 1);
                    			}
                    		}
                		}
                		
            		}
            	}

            


	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
