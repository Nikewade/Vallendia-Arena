package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.CustomEvents.BuyAbilityEvent;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class TunnelVisionAbility implements Listener, Ability{
	HashMap<Player, BukkitTask> timers = new HashMap<>();
	int percent = 50;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Tunnel Vision";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Take permanent blindness, this makes you take "+ percent +"%",
							"less damage to players. You will also deal " + percent + "% more",
							"damage to players and mobs.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(251, 1, (short) 15);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@EventHandler
	public void onBuy (BuyAbilityEvent e)
	{
		if(e.getAbility() == this.getName())
		{
			Player p = e.getPlayer();
			
			BukkitTask timer = new BukkitRunnable()
					{

						@Override
						public void run() {
							// TODO Auto-generated method stub

							if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Tunnel Vision"))
							{
								AbilityUtils.addPotionDuration(p, p, "Tunnel Vision", PotionEffectType.BLINDNESS, 0, 10*20);
							}
							
						}
				
					}.runTaskTimer(VallendiaMinigame.getInstance(), 0, 8*20);
	
					timers.put(p, timer);
		}

	}
	
	@EventHandler
	public void onJoin (PlayerJoinEvent e)
	{
		if(timers.containsKey(e.getPlayer()))
		{

			timers.remove(e.getPlayer());
		}
		
		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(e.getPlayer(), "Tunnel Vision"))
		{
			Player p = e.getPlayer();
			BukkitTask timer = new BukkitRunnable()
			{

				@Override
				public void run() {
					// TODO Auto-generated method stub

					if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Tunnel Vision"))
					{

						AbilityUtils.addPotionDuration(p, p, "Tunnel Vision", PotionEffectType.BLINDNESS, 0, 10*20);
					}
					
				}
		
			}.runTaskTimer(VallendiaMinigame.getInstance(), 0, 8*20);
			
			
			timers.put(p, timer);
		}

	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(timers.containsKey(p))
		{
			timers.get(p).cancel();
			timers.remove(p);
		}
	}
	
	@EventHandler
	public void onHit (EntityDamageByEntityEvent e)
	{
        Player p = null;
        
        if(e.getDamager() instanceof Projectile)
        {
        	Projectile proj = (Projectile) e.getDamager();
        	
        	if(proj.getShooter() instanceof Player)
        	{
        		
        	p = (Player) proj.getShooter();
        	
        	}
        }else
        {
        	if(e.getDamager() instanceof Player)
        	{
        	p = (Player) e.getDamager();
        	}
        	if(!(e.getDamager() instanceof Player))
        	{
        		return;
        	}
        }
        
		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Tunnel Vision"))
		{
			if(p.hasPotionEffect(PotionEffectType.BLINDNESS))
			{
			double highPercent = Utils.getPercentHigherOrLower(percent, true);
			double newdamage = e.getFinalDamage()*highPercent;
			e.setDamage(0);
			e.setDamage(DamageModifier.ARMOR, newdamage);
			}
		}
		
		if(e.getEntity() instanceof Player)
		{
			
			if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility((Player) e.getEntity(), "Tunnel Vision"))
			{
				if(!(e.getDamager() instanceof Player))
				{
					return;
				}
				if(((LivingEntity) e.getEntity()).hasPotionEffect(PotionEffectType.BLINDNESS))
				{
				double highPercent = Utils.getPercentHigherOrLower(percent, false);
				double newdamage = e.getFinalDamage()*highPercent;
				e.setDamage(0);
				e.setDamage(DamageModifier.ARMOR, newdamage);
				}
			}
		}
	}

}