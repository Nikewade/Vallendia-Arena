package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.SphereEffect;
import de.slikey.effectlib.util.ParticleEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class MageArmorAbility implements Ability{
	private static SphereEffect se;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Mage Armor";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Forms magical armor around you.";
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.GOLD_CHESTPLATE);
	}

	@Override
	public boolean RunAbility(Player p) {
		
		se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se.setEntity(p);
		se.radius = 0.6;
		se.yOffset = -0.8;
		se.iterations = 15* 20;
		se.period = 10;
		se.particleCount = 5;
		se.particle = Particle.PORTAL;
		se.visibleRange = 1;
		se.start();	
		
        
        
		p.getWorld().playSound(p.getLocation(), Sound.BLOCK_PORTAL_AMBIENT, 1, (float) 1);
        
		new BukkitRunnable() {
            @Override
            public void run() {
            	se.cancel();
    			p.sendMessage(Utils.Colorate("&8&l Your magical shield dissipates."));
            }
        }.runTaskLaterAsynchronously(VallendiaMinigame.getInstance(), 60*20L);  
		
		AbilityUtils.addPotionDuration(p, PotionEffectType.DAMAGE_RESISTANCE, 1, 60*20);
		p.sendMessage(Utils.Colorate("&8&l A magical shield surrounds you."));
		return true;
	}
	
	

}
