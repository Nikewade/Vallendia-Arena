package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class VanishAbility implements Ability{
	private static ArrayList<Player> enabled = new ArrayList<>();
	private static HashMap<Player, BukkitTask> tasks = new HashMap<>();
	int enabledTime = 10;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Vanish";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Vanish into thin air turning",
				"invisible for " + enabledTime + " seconds.",
				"any damage delt or taken will",
				"cause you to reappear.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.STAINED_GLASS_PANE);
	}

	@Override
	public boolean RunAbility(Player p) {
		if(enabled.contains(p))
		{
			return false;
		}
		enabled.add(p);

		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se.setLocation(p.getLocation());
		se.particle = Particle.SMOKE_NORMAL;
		se.particles = 5;
		se.iterations = 3;
		se.start();
		Language.sendAbilityUseMessage(p, "You vanish.", "Vanish");
		
		new BukkitRunnable() {
            @Override
            public void run() {
        		if(enabled.contains(p))
        		{
        			for(Player player : Bukkit.getOnlinePlayers())
        			{
        				player.hidePlayer(p);
        			}
        		}else 
        		{
            		unVanish(p);	
        			this.cancel();
        		}
            }
        }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 20);
		
		
		BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
        		if(enabled.contains(p))
        		{	
            		unVanish(p);	
        		};
            }
        }.runTaskLater(VallendiaMinigame.getInstance(), enabledTime*20L);
        tasks.put(p, task);
        return true;
	}
	
	
    public static Listener getListener() {
        return new Listener() {
        	@EventHandler
        	public void playerLeave(PlayerQuitEvent e)
        	{
        		if(enabled.contains(e.getPlayer()))
        		{
        			enabled.remove(e.getPlayer());
        		}
        	}
        	
        	@EventHandler
        	public void damage(EntityDamageEvent e)
        	{
        		if(e.getEntity() instanceof Player && enabled.contains(e.getEntity()))
        		{
            		unVanish((Player)e.getEntity());	
        		}
        	}
        	
        	@EventHandler
        	public void damageDelt(EntityDamageByEntityEvent e)
        	{
        		if(e.getDamager() instanceof Player && enabled.contains(e.getDamager()))
        		{
        			Player p = (Player) e.getDamager();
        			unVanish(p);
        		}
        	}
        	
        	@EventHandler
        	public void playerDeath(PlayerDeathEvent e)
        	{
        		if(enabled.contains(e.getEntity()))
        		{
            		unVanish((Player)e.getEntity());	
        		}
        	}
        };
    }
    
    private static void unVanish(Player p)
    {
		if(enabled.contains(p))
		{
			enabled.remove(p);
			for(Player player : Bukkit.getOnlinePlayers())
			{
				player.showPlayer(p);
			}
			tasks.get(p).cancel();
			tasks.remove(p);
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.setLocation(p.getLocation());
			se.particle = Particle.SMOKE_NORMAL;
			se.particles = 5;
			se.iterations = 3;
			se.start();
			Language.sendAbilityUseMessage(p, "You reappear.", "Vanish");
		}
    }
}
