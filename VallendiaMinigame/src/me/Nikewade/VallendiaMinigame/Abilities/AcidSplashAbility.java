package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;

import com.google.common.graph.ElementOrder.Type;

import de.slikey.effectlib.effect.DonutEffect;
import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class AcidSplashAbility implements Ability{
	int range = 3;
	int damage = 6;
	int radius = 8;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Acid Splash";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Throw a projectile which, upon hitting, splashes all",
							"enemies in a " + range + " block radius with acid for " + damage,
							"damage");
									
		
	}

	@Override
	public ItemStack getGuiItem() {
		ItemStack potion = new ItemStack(Material.SPLASH_POTION, 1);

        PotionMeta potionmeta = (PotionMeta) potion.getItemMeta();
        potionmeta.setColor(Color.LIME);
        potion.setItemMeta(potionmeta);
		
		return new ItemStack (potion);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
  		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se.particle = Particle.BLOCK_CRACK;
		se.material = Material.LIME_SHULKER_BOX;
		se.disappearWithOriginEntity = true;
		se.infinite();
		se.radius = 0.5;
		se.particles = 2;
		se.speed = (float) 0;
		se.visibleRange = 50;
		
		
  		SphereEffect se3 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se3.particle = Particle.REDSTONE;
		se3.color = Color.GREEN;
		se3.disappearWithOriginEntity = true;
		se3.infinite();
		se3.radius = 0.3;
		se3.particles = 2;
		se3.speed = (float) 0;
		se3.visibleRange = 50;
		
  		SphereEffect se4 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se4.particle = Particle.REDSTONE;
		se4.color = Color.GREEN;
		se4.radius = range;
		se4.particles = 25;
		se4.iterations = 3;
		se4.speed = (float) 0;
		se4.visibleRange = 50;
				
  		SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se2.particle = Particle.BLOCK_CRACK;
		se2.material = Material.LIME_SHULKER_BOX;
		se2.radius = range;
		se2.particles = 25;
		se2.iterations = 3;
		se2.speed = (float) 0;
		se2.visibleRange = 50;
		  		
		Runnable run2 = new Runnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		
		};
		Runnable run = new Runnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Location seloc = se.getLocation();
				
				if(seloc.getBlock().getType() == Material.LONG_GRASS)
				{
					seloc.subtract(0,1,0);
				}

				p.getWorld().playSound(seloc, Sound.BLOCK_SLIME_BREAK, 1, (float) 1.4);
				p.getWorld().playSound(seloc, Sound.BLOCK_FIRE_EXTINGUISH, 1, 1F);
				
				for(Entity e : AbilityUtils.getAoeTargets(p, seloc, range, range, range) )
				{
					LivingEntity le = (LivingEntity) e;
					AbilityUtils.damageEntity(le, p, damage);
				}
				
				se2.setLocation(seloc);
				se2.start();	
				se4.setLocation(seloc);
				se4.start();
		}
			
		};
		
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SPLASH_POTION_THROW, 1, (float) 0.6);
		AbilityUtils.arcParticle(p, se, 0.4, run);	
		AbilityUtils.arcParticle(p, se3, 0.4, run2);	
		
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}