package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.slikey.effectlib.effect.CircleEffect;
import de.slikey.effectlib.effect.ConeEffect;
import de.slikey.effectlib.effect.DonutEffect;
import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class StompAbility implements Ability {
//made by Emma
	int radius = 5;
	int duration = 5;
	int damage = 6;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Stomp";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You stomp stunning enemies in a" + radius + " block radius",
				"for " + duration + " seconds. Any damage done to the",
				"target will break the stun.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.IRON_BOOTS);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(p.isOnGround())
		{
		
		Location loc = p.getLocation();
        Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);

		DonutEffect de = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
		de.particle = Particle.BLOCK_CRACK;
        if(b.getType().isSolid())
        {
            de.material = b.getType();
        }else
        {
            de.material = Material.DIRT;    
        }		
		de.radiusDonut = 3;
		de.speed = 0;
		de.iterations = 1;
		de.visibleRange = 50;
		de.xRotation = 300;
		
		de.setLocation(loc);
		de.start();
		
		DonutEffect de2 = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
		de2.particle = Particle.BLOCK_CRACK;
        if(b.getType().isSolid())
        {
            de2.material = b.getType();
        }else
        {
            de2.material = Material.DIRT;    
        }
		de2.radiusDonut = 1;
		de2.speed = 0;
		de2.iterations = 1;
		de2.visibleRange = 50;
		de2.xRotation = 300;
		de2.delay = 2;
		
		de2.setLocation(loc);
		de2.start();
		
		DonutEffect ce = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
		ce.particle = Particle.BLOCK_CRACK;
        if(b.getType().isSolid())
        {
            ce.material = b.getType();
        }else
        {
            ce.material = Material.DIRT;    
        }
		ce.radiusDonut = 5;
		ce.particleCount = 3;
		ce.speed = 0;
		ce.iterations = 1;
		ce.visibleRange = 50;
		ce.xRotation = 300;
		ce.delay = 4;

		
		ce.setLocation(loc);
		ce.start();
		
		
		p.getWorld().playSound(loc, Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 1, (float) 0.5);
				
		
		for(Entity e : AbilityUtils.getAoeTargets(p, p.getLocation(), radius, radius, radius))
		{
			
			AbilityUtils.damageEntity((LivingEntity) e, p, damage);
			AbilityUtils.stun(p, (LivingEntity) e, "Stomp", duration, true);
		}
		
		return true;
		}else
		{
			Language.sendAbilityUseMessage(p, "You can only use stomp on the ground!", "Stomp");
			return false;
		}
		
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}