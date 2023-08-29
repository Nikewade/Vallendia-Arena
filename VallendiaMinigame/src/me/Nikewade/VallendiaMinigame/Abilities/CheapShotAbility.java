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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class CheapShotAbility implements Ability, Listener{
<<<<<<< HEAD
	List<LivingEntity> stunned = new ArrayList<>();
	Map<LivingEntity, BukkitTask> tasks = new HashMap<>();
	int time = 10;
=======
	int time = 200;
>>>>>>> second-repo/master

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Cheap Shot";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		return Arrays.asList("Stun your target for " + time + " seconds. Any damage done",
=======
		return Arrays.asList("Stun your target for " + time / 20 + " seconds. Any damage done",
>>>>>>> second-repo/master
				"to the target will cancel this ability.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SPIDER_EYE);
	}

	@Override
	public boolean RunAbility(Player p) {
 		LivingEntity target = AbilityUtils.getTarget(p, 5);
 		if(target != null)
 		{
<<<<<<< HEAD
 			AbilityUtils.stun(p,target, this.getName(), time);
 	 		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BIG_FALL, 1, 0.5F);
 	 		target.getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0, 1.8, 0), 20);
 	 		AbilityUtils.damageEntity(target, p, 2);
 	 		
 	 		if(!stunned.contains(target))
 	 		{
 	 	 		stunned.add(target);
 	        	BukkitTask task = new BukkitRunnable() {
 	                @Override
 	                public void run() {
 	                	stunned.remove(target);
 	                }
 	            }.runTaskLater(VallendiaMinigame.getInstance(), time *20L);	
 	            tasks.put(target, task);
 	 		}else
 	 		{
 	 			stunned.remove(target);
 	 			tasks.get(target).cancel();
 	 			tasks.remove(target);
 	 			
 	 	 		stunned.add(target);
 	        	BukkitTask task = new BukkitRunnable() {
 	                @Override
 	                public void run() {
 	                	stunned.remove(target);
 	                }
 	            }.runTaskLater(VallendiaMinigame.getInstance(), time *20L);	
 	            tasks.put(target, task);
 	 		}
 	 		
=======
 	 		AbilityUtils.damageEntity(target, p, 2);
 			AbilityUtils.stun(p,target, this.getName(), time, true);
 	 		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BIG_FALL, 1, 0.5F);
 	 		target.getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0, 1.8, 0), 20);
>>>>>>> second-repo/master

 	 		return true;
 		}
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}
	
<<<<<<< HEAD
	@EventHandler
	public void onDamage(EntityDamageEvent e)
	{
		if(!(e.getEntity() instanceof LivingEntity))
		{
			return;
		}
		
		LivingEntity en = (LivingEntity) e.getEntity();
		
		if(stunned.contains(en))
		{
	 			stunned.remove(en);
	 			tasks.get(en).cancel();
	 			tasks.remove(en);
	 			AbilityUtils.removeStun(en, this.getName());
		}
	}
=======
>>>>>>> second-repo/master

}
