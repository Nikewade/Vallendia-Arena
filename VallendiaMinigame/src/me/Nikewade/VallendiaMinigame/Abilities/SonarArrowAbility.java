package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class SonarArrowAbility implements Ability, Listener{
	ArrayList<Player> abilityActive = new ArrayList<>();
	ArrayList<Projectile> arrows = new ArrayList<>();
	int duration = 20;
	int delay = 1;
	HashMap<Player, BukkitTask> timers = new HashMap<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Sonar Arrow";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Shoot an arrow imbued with magical sound.",
							"The enemy that gets hit makes a loud, distinct",
							"beeping noise for " + duration + " seconds, even while in stealth.",
							"This ability will also work on party members.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.NOTE_BLOCK);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(abilityActive.contains(p))
		{
			return false;
		}
		
		Language.sendAbilityUseMessage(p, "Sonar Arrow is now active", "Sonar Arrow");
		abilityActive.add(p);
		
		new BukkitRunnable()
		{	
	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if(abilityActive.contains(p))
				{
					abilityActive.remove(p);
					Language.sendAbilityUseMessage(p, "Sonar Arrow is no longer active", "Sonar Arrow");
					
				}
				
				
			}
	
		}.runTaskLater(VallendiaMinigame.getInstance(), 15*20);
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
		}
		
	}

	@EventHandler
	public void onHit (EntityDamageByEntityEvent e)
	{
		if(!(e.getEntity() instanceof Player))
		{
			return;
		}
		
		if(e.getDamager() instanceof Projectile)
		{
			
			Projectile arrow = (Projectile) e.getDamager();
			
			if(!(arrow.getShooter() instanceof Player))
			{
				return;
			}
			
			Player attacker = (Player) arrow.getShooter();
			
			if(arrows.contains(arrow))
			{
				Player p = (Player) e.getEntity();
				arrows.remove(arrow);

					BukkitTask beeps = new BukkitRunnable()
						{	
					
						int t = 0;
						
							@Override
							public void run() {
								// TODO Auto-generated method stub
								if(t == duration)
								{
									this.cancel();

								}
								
								p.getWorld().playSound(p.getLocation(), Sound.BLOCK_NOTE_FLUTE, 4, 4);
								
								t++;
							}
					
						}.runTaskTimer(VallendiaMinigame.getInstance(), 0, delay*20);
				
						timers.put(p, beeps);
			}
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
  		if(!(e.getProjectile() instanceof Projectile))
  		{
  			return;
  		}
  		
  		abilityActive.remove(p);
  		arrows.add((Projectile) e.getProjectile());
	
	  }
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
}