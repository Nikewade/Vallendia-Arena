package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import de.slikey.effectlib.effect.LineEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class AcidicRayAbility implements Ability, Listener{
	int range = 20;
	int damage= 5;
	ArrayList<Player> active = new ArrayList<>();
	ArrayList<Block> blocks = new ArrayList<>();
	

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Acidic Ray";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Instantly fire a corrosive ray up to 20 blocks,",
							"dealing 5 damage and poisoning enemies.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SLIME_BALL);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
  		LineEffect se2 = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
		se2.particle = Particle.BLOCK_CRACK;
		se2.material = Material.LIME_SHULKER_BOX;
		se2.iterations = 2;
		se2.particles = 70;
		se2.speed = (float) 0;
		se2.visibleRange = 200;
		
  		LineEffect se = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
		se.particle = Particle.BLOCK_CRACK;
		se.material = Material.SLIME_BLOCK;
		se.iterations = 2;
		se.particles = 70;
		se.speed = (float) 0;
		se.visibleRange = 200;
		
  		LineEffect se3 = new LineEffect(VallendiaMinigame.getInstance().effectmanager);
		se3.particle = Particle.REDSTONE;
		se3.color = Color.GREEN;
		se3.iterations = 2;
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
	                    if(AbilityUtils.partyCheck(p, (Player) e))
	                    {
	                        continue;
	                    }
					AbilityUtils.damageEntity((LivingEntity) e, p, damage);
					p.getWorld().playSound(loc, Sound.BLOCK_LAVA_EXTINGUISH, 2, 0.6F);
					AbilityUtils.addPotionDuration(p, (LivingEntity) e, "Acidic Ray", PotionEffectType.POISON, 0, 10*20);
					loc = e.getLocation();
					loc.add(0,1,0);
					players = true;	
					}
				}
				if(players)
				{
					break;
				}
				if(loc.getBlock().getType().isSolid())
				{
					active.add(p);
					AbilityUtils.explode(loc, p, 1, 0, false, true, false);
					loc = loc.getBlock().getLocation();
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
	
	@EventHandler
	public void onExplode (EntityExplodeEvent e)
	{	
		if(active.contains(e.getEntity()))
		{
			active.remove(e.getEntity());
			e.setCancelled(true);
			e.getEntity().getWorld().playSound(e.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 2, 0.6F);
  			for(Entity ent : e.getLocation().getWorld().getNearbyEntities(e.getLocation(), 300, 300, 300))
  			{
  				if(ent instanceof Player)
  				{
  					Player entity = (Player) ent;
  					entity.stopSound(Sound.ENTITY_GENERIC_EXPLODE);
  				}
  			}
			for(Block b : e.blockList())
			{
				blocks.add(b);
			}
			for(Block b : blocks)
			{
				Utils.regenBlock(b, 10);
				b.setType(Material.AIR);
			}
		}
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}