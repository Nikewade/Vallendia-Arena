package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class ConcussiveShotAbility implements Ability, Listener {
	//made by emma	
	Map<Projectile,SphereEffect> arrow = new HashMap<>();
	ArrayList<Player> abilityActive = new ArrayList<>();
	Map<Player, BukkitTask> timers = new HashMap<>();
	int amplifier = 2;
	int desc = amplifier+1;
	int time = 10;
    int heal = 10;
    int delay = 15;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Concussive Shot";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Your shot gives your enemy slowness " + desc,
							"and nausea for " + time + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.CLAY_BALL);
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
			Language.sendAbilityUseMessage(p, "Concussive Shot is now Active", "Concussive Shot");
			
			BukkitTask timer = new BukkitRunnable()
			{

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(abilityActive.contains(p))
					{
					
					abilityActive.remove(p);
					Language.sendAbilityUseMessage(p, "Concussive Shot is no longer active", "Concussive Shot");
					
					}
				}
				
			}.runTaskLater(VallendiaMinigame.getInstance(), delay*20);
			
			timers.put(p, timer);
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
        		

        		
        		
            		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
            		se.setEntity(e.getProjectile());
            		se.color = Color.GRAY;
            		se.particleCount = 2;
            		se.radius = 0.1;
            		se.particles = 1;
            		se.start();	
            		arrow.put((Projectile)e.getProjectile(), se);	
        		
        		if(timers.containsKey(p))
        		{
        			timers.get(p).cancel();
        		}
        		abilityActive.remove(p);
            }

            @EventHandler
            public void onland(ProjectileHitEvent e){
            	if(!arrow.containsKey(e.getEntity()))
            	{
            		return;
            	}
            	
            	if(!(e.getEntity().getShooter() instanceof Player))
            	{
            		return;
            	}
        		arrow.get(e.getEntity()).cancel();
                arrow.remove(e.getEntity());
        		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
        		se.setEntity(e.getEntity());
        		se.particleCount = 1;
        		se.radius = 0.4;
        		se.duration = 1;
        		se.color = Color.GRAY;
        		se.start();	
                ((Entity) e.getEntity().getShooter()).getWorld().playSound(se.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 3, 1.5F);

        		if(e.getHitEntity() != null && e.getHitEntity() instanceof LivingEntity)
        		{
        			LivingEntity entity = (LivingEntity) e.getHitEntity();	
        			AbilityUtils.addPotionDuration((LivingEntity) e.getEntity().getShooter(), entity, "Concussive Shot", PotionEffectType.SLOW, amplifier, time*20);
        			AbilityUtils.addPotionDuration((LivingEntity) e.getEntity().getShooter(), entity, "Concussive Shot", PotionEffectType.CONFUSION, amplifier, time*20);

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