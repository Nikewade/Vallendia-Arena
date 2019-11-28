package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class DivineShieldAbility implements Ability, Listener{
	private static ArrayList<Player> enabled = new ArrayList<>();
	private static HashMap<Player,SphereEffect> effect1 = new HashMap<>();
	private static HashMap<Player,SphereEffect> effect2 = new HashMap<>();
	int enabledTime = 5;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Divine Shield";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Call forth divine intervention, an impenetrable" ,
				"holy shield blocks all incoming damage",
				"for " + enabledTime + " seconds. This also stops",
				"you from dealing any damage.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		ItemStack i = new ItemStack(Material.GOLD_CHESTPLATE);
		i.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		return i;
	}

	@Override
	public boolean RunAbility(Player p) {
		if(enabled.contains(p))
		{
			return false;
		}
		enabled.add(p);
		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se.setEntity(p);
		se.disappearWithOriginEntity = true;
		se.infinite();
		se.particle = Particle.END_ROD;
		se.color = org.bukkit.Color.YELLOW;
		se.radius = 0.9;
		se.particles = 2;
		se.yOffset = -0.8;
		se.speed = (float) 0;
		se.start();
		SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se2.setEntity(p);
		se2.disappearWithOriginEntity = true;
		se2.infinite();
		se2.color = org.bukkit.Color.YELLOW;
		se2.radius = 1;
		se2.particles = 1;
		se2.yOffset = -0.8;
		se2.start();
		effect1.put(p, se);
		effect2.put(p, se2);
		p.getWorld().playSound(p.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 2, (float)1.5);
		
		new BukkitRunnable() {
            @Override
            public void run() {
        		if(enabled.contains(p))
        		{
        			enabled.remove(p);
        			Language.sendAbilityUseMessage(p, "Your holy shield dissipates.", "Divine Shield");
        			if(effect1.containsKey(p))
        			{
            			effect1.get(p).cancel();
            			effect2.get(p).cancel();
            			effect1.remove(p);
            			effect2.remove(p);
        			}
        		};
            }
        }.runTaskLaterAsynchronously(VallendiaMinigame.getInstance(), enabledTime*20L);
        return true;
	}
	
	
	
    public static Listener getListener() {
        return new Listener() {
        	@EventHandler
        	public void onDamage(EntityDamageEvent e)
        	{
        		if(e.getEntity() instanceof Player && enabled.contains(e.getEntity()))
        		{
        			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
        			se.setEntity(e.getEntity());
        			se.disappearWithOriginEntity = true;
        			se.particle = Particle.END_ROD;
        			se.color = org.bukkit.Color.YELLOW;
        			se.radius = 1.5;
        			se.iterations = 10;
        			se.particles = 2;
        			se.yOffset = -0.8;
        			se.speed = (float) 0;
        			se.start();
        			e.setCancelled(true);
        		}
        	}
        	
        	@EventHandler
        	public void onDamage(EntityDamageByEntityEvent e)
        	{
        		if(e.getDamager() instanceof Player && enabled.contains(e.getDamager()))
        		{
        			e.setCancelled(true);
        		}
        		
        		if(e.getDamager() instanceof Projectile)
        		{
        			Projectile proj = (Projectile) e.getDamager();
        			if(proj.getShooter() instanceof Player && enabled.contains(proj.getShooter()))
        			{
        				e.setCancelled(true);
        			}
        		}
        	}
        };
    }

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
