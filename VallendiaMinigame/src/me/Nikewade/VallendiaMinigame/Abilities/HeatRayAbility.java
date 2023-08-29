package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import de.slikey.effectlib.effect.LineEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class HeatRayAbility implements Ability{
	int damage = 6;
	int range = 20;
	int fireticks = 60; 

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Heat Ray";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Instantly fire a superheated ray up to " + range + " blocks,",
							"dealing " + damage + " damage and setting enemies ablaze.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.BLAZE_POWDER);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
  		LineEffect se2 = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
		se2.particle = Particle.REDSTONE;
		se2.color = Color.ORANGE;
		se2.iterations = 10;
		se2.particles = 70;
		se2.speed = (float) 0;
		se2.visibleRange = 200;
		
  		LineEffect se = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
		se.particle = Particle.FLAME;
		se.iterations = 1;
		se.particles = 70;
		se.speed = (float) 0;
		se.visibleRange = 200;
		
  		LineEffect se3 = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
		se3.particle = Particle.REDSTONE;
		se3.color = Color.WHITE;
		se3.iterations = 5;
		se3.particles = 40;
		se3.speed = (float) 0;
		se3.visibleRange = 200;
		
		
			Location loc = p.getLocation().add(0, 1.4, 0);
			for(int i = 0; i < range+5 ; i++)
			{
				if(i >= range)
				{
					// on end of ray
					break;
				}
				
				Boolean players = false;
				for(Entity e : loc.getWorld().getNearbyEntities(loc, 0.6, 0.6, 0.6))
				{	
					if(e instanceof LivingEntity && e != p)
					{
      					if(e instanceof Player)
      					{
                            if(AbilityUtils.partyCheck(p, (Player) e))
                            {
                                continue;
                            }	
      					}
					AbilityUtils.damageEntity((LivingEntity) e, p, damage);
					e.setFireTicks(fireticks);
					p.getWorld().playSound(loc, Sound.BLOCK_LAVA_EXTINGUISH, 2, 0.6F);
					players = true;	
					}
				}
				if(players)
				{
					break;
				}
				if(loc.getBlock().getType().isSolid())
				{
					// on hit block
					break;
				}
			    loc = loc.add(loc.getDirection());			    
			}
			
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ILLUSION_ILLAGER_CAST_SPELL, 1, 1);
		se.setLocation(p.getLocation().add(0, 1.2, 0));
		se.setTargetLocation(loc);
		se.start();		
		se2.setLocation(p.getLocation().add(0, 1.2, 0));
		se2.setTargetLocation(loc);
		se2.start();
		se3.setLocation(p.getLocation().add(0, 1.2, 0));
		se3.setTargetLocation(loc);
		se3.start();
		
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}