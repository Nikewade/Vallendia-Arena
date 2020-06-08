package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class StoneSkinAbility implements Ability, Listener{
	int attacksIgnored = 5;
	int time = 45;
	HashMap<Player, Integer> ignored = new HashMap<>();
	ArrayList<Player> abilityActive = new ArrayList<>(); 
	HashMap<Player, BukkitTask> timers = new HashMap<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Stone Skin";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You harden your skin. You ignore the next " + attacksIgnored + " attacks",
							"dealt against you. This effect wears off after " + time,
							"seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.STONE);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		abilityActive.add(p);
		ignored.put(p, 0);
		p.getWorld().playSound(p.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 2, (float) 0.5);
		p.getWorld().playSound(p.getLocation(), Sound.BLOCK_STONE_BREAK, 2, 0.4F);
		BukkitTask timer = new BukkitRunnable()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						disable(p);
			 	 		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 2, (float) 0.4);
			 			p.getWorld().playSound(p.getLocation(), Sound.BLOCK_STONE_BREAK, 2, 0.4F);
					}
			
				}.runTaskLater(VallendiaMinigame.getInstance(), time*20);
				
				timers.put(p, timer);
			
			
		return true;
	}
	
	public void disable(Player p)
	{
		if(abilityActive.contains(p))
		{
			abilityActive.remove(p);
		}
		if(ignored.containsKey(p))
		{
			ignored.remove(p);
		}
		if(timers.containsKey(p))
		{
			BukkitTask timer = timers.get(p);
			timers.remove(p);
			timer.cancel();
		}
	}
	
	@EventHandler
	public void onDamage (EntityDamageEvent e)
	{
		if(abilityActive.contains(e.getEntity()))
		{
			Player p = (Player) e.getEntity();
			if(!ignored.containsKey(p))
			{
				return;
			}
			if(ignored.get(p) >= attacksIgnored)
			{
				abilityActive.remove(p);
				ignored.remove(p);
	 	 		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 2, (float) 0.4);
	 			p.getWorld().playSound(p.getLocation(), Sound.BLOCK_STONE_BREAK, 2, 0.4F);
				return;
			}
			e.setDamage(0);
 	 		p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BLOCK, 2, (float) 0.6);
 			p.getWorld().playSound(p.getLocation(), Sound.BLOCK_STONE_BREAK, 2, 0.4F);
			int x = ignored.get(p);
			ignored.remove(p);
			int newx = x+1;
			ignored.put(p, newx);
			
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.disappearWithOriginEntity = true;
			se.iterations = 2;
			se.particle = Particle.BLOCK_CRACK;
			se.material = Material.STONE;
			se.radius = 0.8;
			se.particleOffsetY = (float) 0.5;
			se.particles = 8;
			se.yOffset = -0.8;
			se.speed = (float) 0;
			se.setEntity(p);
			se.start();
		}
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		disable(p);
	}

}