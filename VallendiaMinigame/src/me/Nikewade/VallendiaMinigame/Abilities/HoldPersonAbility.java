package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
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

import de.slikey.effectlib.effect.ConeEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class HoldPersonAbility implements Ability, Listener {
<<<<<<< HEAD
	List<LivingEntity> stunned = new ArrayList<>();
	Map<LivingEntity, BukkitTask> tasks = new HashMap<>();
	int time = 10;
=======
	int time = 200;
>>>>>>> second-repo/master
	int range = 15;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Hold Person";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Stun your target from up to " + range + " blocks away", 
<<<<<<< HEAD
				"for " + time + " seconds. Any damage done to the",
=======
				"for " + time / 20 + " seconds. Any damage done to the",
>>>>>>> second-repo/master
				"target will cancel this ability.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.TOTEM);
	}

	@Override
	public boolean RunAbility(Player p) {
 		LivingEntity target = AbilityUtils.getTarget(p, range);
 		if(target != null)
 		{
<<<<<<< HEAD
 			AbilityUtils.stun(p,target, this.getName(), time);
 	 		p.getWorld().playSound(p.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 1, 0.8F);
 	 		
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
 			AbilityUtils.stun(p,target, this.getName(), time, true);
 	 		p.getWorld().playSound(p.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 1, 0.8F);
 	 		
>>>>>>> second-repo/master
 			ConeEffect se = new ConeEffect(VallendiaMinigame.getInstance().effectmanager);
 			se.particle = Particle.CRIT_MAGIC;
 			Location loc = target.getLocation();
 			loc.setPitch(-90);
 			se.lengthGrow = (float) 0.01;
 			se.iterations = 20;
 			se.setLocation(loc);
 			se.start();
 	 		

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
