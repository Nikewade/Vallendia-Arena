package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class SonarArrowAbility implements Ability, Listener{
	ArrayList<Player> abilityActive = new ArrayList<>();
	int duration = 20;
	int delay = 1;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Sonar Arrow";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Shoot an arrow imbued with magical sound.",
							"The enemy that gets hit makes a loud, distinct",
							"beeping noise for 30 seconds, even while in stealth.");
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
		
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
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
			Player attacker = (Player) arrow.getShooter();
			
			if(abilityActive.contains(attacker))
			{
				Player p = (Player) e.getEntity();

				BukkitTask timer = new BukkitRunnable()
						{	
					
						int t = 0;
						
							@Override
							public void run() {
								// TODO Auto-generated method stub
								if(t == duration)
								{
									this.cancel();
									abilityActive.remove(e.getDamager());
								}
								
								p.getWorld().playSound(p.getLocation(), Sound.BLOCK_NOTE_FLUTE, 1, 4);
								
								t++;
							}
					
						}.runTaskTimer(VallendiaMinigame.getInstance(), 0, delay*20);
				
			}
		}
		
		
		
		
		
		
		
		
		
		
	}
	
}