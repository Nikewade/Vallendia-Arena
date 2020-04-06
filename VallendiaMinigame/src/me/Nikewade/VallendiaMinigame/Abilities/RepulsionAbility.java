package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class RepulsionAbility implements Ability {
	// cycle is in ticks
	int cycle = 20;
	int duration = 15;
	int radius = 5;
	int force = 20;
	int yForce = 8;
	int maxYForce = 10;
	ArrayList<Player> abilityActive = new ArrayList<>();
	HashMap <Player, BukkitTask> timers = new HashMap<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Repulsion";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("For " + duration + " seconds, enemies who approach",
				"you are thrown away violently.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack (Material.BEETROOT_SEEDS);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		if(abilityActive.contains(p))
		{
		return false;
		}
		
		abilityActive.add(p);
		Language.sendAbilityUseMessage(p, "Repulsion is now Active!", "Repulsion");
		
		BukkitTask timer = new BukkitRunnable()
				{
					int time = 0;
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(time >= duration)
						{
							if(abilityActive.contains(p))
							{
								abilityActive.remove(p);
							}
							Language.sendAbilityUseMessage(p, "Repulsion is no longer Active.", "Repulsion");
							this.cancel();
						}
						

							p.getWorld().playSound( p.getLocation(), Sound.UI_TOAST_IN, 2, 0.4F);
				 	 		p.getWorld().spawnParticle(Particle.CLOUD, p.getLocation().add(0, 1, 0), 20);
				 	 		p.getWorld().spawnParticle(Particle.CLOUD, p.getLocation().add(0, 1, 0), 20);
				 	 		p.getWorld().spawnParticle(Particle.CLOUD, p.getLocation().add(0, 1.8, 0), 20);
						
						
						Location location = p.getLocation().add(0.0D, -1.0F, 0.0D);
					    Vector t = location.toVector();

						for(Entity entity : AbilityUtils.getAoeTargets(p, p.getLocation(), radius, radius, radius))
						{
					          Vector e = entity.getLocation().toVector();
					          Vector v = e.subtract(t).normalize().multiply(force / 10.0D);
					          if (force != 0) {
					            v.setY(v.getY() * (yForce / 10.0D));
					          } else {
					            v.setY(yForce / 10.0D);
					          }
					          if (v.getY() > maxYForce / 10.0D) {
					              v.setY(maxYForce / 10.0D);
					            }
					          entity.setVelocity(v);

						}
						
				          time++;
					}
			
				}.runTaskTimer(VallendiaMinigame.getInstance(), 0, cycle);
				
				timers.put(p, timer);
		
		return true;
		
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(abilityActive.contains(p))
		{
			abilityActive.remove(p);
		}
		if(timers.containsKey(p))
		{
			timers.get(p).cancel();
			timers.remove(p);
		}
	}

}