package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.comphenix.protocol.wrappers.EnumWrappers.Hand;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class IceTrapAbility implements Ability, Listener {
	Map<Player, Location> clickedBlock = new HashMap<>();
	List<Player> trapMode = new ArrayList<>();
	Map<Player, BukkitTask> trapModeTask = new HashMap<>();
	int stunTime = 8;
	int trapModeTime = 15;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Ice Trap";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("ekrv");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.ICE);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		if(!trapMode.contains(p))
		{
			// ALready had a trap
			if(AbilityUtils.getTrap(p, this.getName()) != null) 
			{
				Language.sendAbilityUseMessage(p, "Trap Mode: Enabled - Left click a trap or sneak left click "
						+ "to delete all traps.", this.getName());
			}else
			{
				Language.sendAbilityUseMessage(p, "Trap Mode: Enabled - Left click a block to place a trap." , this.getName());	
			}
			trapMode.add(p);
			BukkitTask task = new BukkitRunnable()
			{

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(trapMode.contains(p))
					{
						trapMode.remove(p);
						Language.sendAbilityUseMessage(p, "Trap Mode: Disabled", "Ice Trap");
						trapModeTask.remove(p);
					}
				}
			}.runTaskLater(VallendiaMinigame.getInstance(), trapModeTime * 20);
			trapModeTask.put(p, task);
			return true;	
		}
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		AbilityUtils.removeTrap(p, this.getName());
		
	}
	
	
	@EventHandler
	public void onClickBlock(PlayerInteractEvent e)
	{
		if(e.getAction() != Action.LEFT_CLICK_BLOCK || e.getHand() == EquipmentSlot.OFF_HAND)
		{
			//Remove all traps
			Player p = e.getPlayer();
			if(trapMode.contains(e.getPlayer()))
			{
				if(p.isSneaking())
				{
					trapMode.remove(p);
					trapModeTask.get(p).cancel();
					trapModeTask.remove(p);
					AbilityUtils.removeTrap(p, this.getName());
					Language.sendAbilityUseMessage(p, "Trap Mode: All traps removed.", this.getName());
					e.setCancelled(true);
				}
			}
			return;
		}
		if(trapMode.contains(e.getPlayer()))
		{
			Player p = e.getPlayer();
			Block b = e.getClickedBlock();
			Location loc = b.getLocation().add(0.5, 1, 0.5);
			if(b.getType().isSolid() && !b.getRelative(BlockFace.UP).getType().isSolid())
			{
				Runnable run = new Runnable()
				{
					

					@Override
					public void run() {
						// TODO Auto-generated method stub
                  		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
                		se.particle = Particle.REDSTONE;
                		se.color = Color.AQUA;
                		se.iterations = 2;
                		se.particles = 30;
                		se.radius = 2.2;
                		se.setLocation(loc);
                		se.start();
                  		SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
                		se2.particle = Particle.BLOCK_CRACK;
                		se2.material = Material.ICE;
                		se2.iterations = 2;
                		se2.particles = 30;
                		se2.radius = 2.2;
                		se2.setLocation(loc);
                		se2.start();
                		loc.getWorld().playSound(loc, Sound.BLOCK_GLASS_BREAK, 2, 1);
	                	for(Entity e : AbilityUtils.getAoeTargets(p, loc, 2, 2.8, 2))
	                	{
	                		AbilityUtils.stun((LivingEntity)p, (LivingEntity)e, "Ice Trap", stunTime);
	                  		SphereEffect se3 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
	                		se3.particle = Particle.BLOCK_CRACK;
	                		se3.material = Material.ICE;
	                		se3.iterations = 2;
	                		se3.particles = 5;
	                		se3.radius = 1;
	                		se3.yOffset = 0.5;
	                		se3.setLocation(e.getLocation());
	                		se3.start();
	                	}
						Language.sendAbilityUseMessage(p, "Your trap was triggered!", "Ice Trap");
					}
					
				};
				
				
				
				if(AbilityUtils.makeTrap(p.getName(), this.getName(), loc, run))
				{
					trapMode.remove(p);
					trapModeTask.get(p).cancel();
					trapModeTask.remove(p);
					Language.sendAbilityUseMessage(p, "Trap Mode: Disabled - Placing trap...", this.getName());
					e.setCancelled(true);
					return;	
				}else
				{
					if(loc.getX() != AbilityUtils.getTrap(p, this.getName()).getX() || loc.getZ() != 
							AbilityUtils.getTrap(p, this.getName()).getZ() || loc.getY() != AbilityUtils.getTrap(p, this.getName()).getY())
					{
						Language.sendAbilityUseMessage(p, "Trap Mode: You already have a trap placed!", this.getName());	
						e.setCancelled(true);
					}else
					{
						trapMode.remove(p);
						trapModeTask.get(p).cancel();
						trapModeTask.remove(p);
						AbilityUtils.removeTrap(p, this.getName());
						Language.sendAbilityUseMessage(p, "Trap Mode: Trap removed.", this.getName());
						e.setCancelled(true);
					}
				}
				
			}else
			{
				if((b.getType() != Material.AIR && !b.isLiquid() &&  !b.getType().isSolid()))
				{
					if(!b.getRelative(BlockFace.DOWN).getType().isSolid())
					{
						loc.subtract(0,2,0);	
					}else
					{
						loc.subtract(0,1,0);
					}
					Runnable run = new Runnable()
					{
						

						@Override
						public void run() {
							// TODO Auto-generated method stub
	                  		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
	                		se.particle = Particle.REDSTONE;
	                		se.color = Color.AQUA;
	                		se.iterations = 2;
	                		se.particles = 30;
	                		se.radius = 2.2;
	                		se.setLocation(loc);
	                		se.start();
	                  		SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
	                		se2.particle = Particle.BLOCK_CRACK;
	                		se2.material = Material.ICE;
	                		se2.iterations = 2;
	                		se2.particles = 30;
	                		se2.radius = 2.2;
	                		se2.setLocation(loc);
	                		se2.start();
	                		loc.getWorld().playSound(loc, Sound.BLOCK_GLASS_BREAK, 2, 1);
		                	for(Entity e : AbilityUtils.getAoeTargets(p, loc, 2, 2.8, 2))
		                	{
		                		AbilityUtils.stun((LivingEntity)p, (LivingEntity)e, "Ice Trap", 8);
		                  		SphereEffect se3 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		                		se3.particle = Particle.BLOCK_CRACK;
		                		se3.material = Material.ICE;
		                		se3.iterations = 2;
		                		se3.particles = 5;
		                		se3.radius = 1;
		                		se3.yOffset = 0.5;
		                		se3.setLocation(e.getLocation());
		                		se3.start();
		                	}
							Language.sendAbilityUseMessage(p, "Your trap was triggered!", "Ice Trap");
						}
						
					};
					
					
					
					if(AbilityUtils.makeTrap(p.getName(), this.getName(), loc, run))
					{
						trapMode.remove(p);
						trapModeTask.get(p).cancel();
						trapModeTask.remove(p);
						Language.sendAbilityUseMessage(p, "Trap Mode: Disabled - Placing trap...", this.getName());
						e.setCancelled(true);
						return;	
					}else
					{
						if(loc.getX() != AbilityUtils.getTrap(p, this.getName()).getX() || loc.getZ() != 
								AbilityUtils.getTrap(p, this.getName()).getZ() || loc.getY() != AbilityUtils.getTrap(p, this.getName()).getY())
						{
							Language.sendAbilityUseMessage(p, "Trap Mode: You already have a trap placed!", this.getName());	
							e.setCancelled(true);
						}else
						{
							trapMode.remove(p);
							trapModeTask.get(p).cancel();
							trapModeTask.remove(p);
							AbilityUtils.removeTrap(p, this.getName());
							Language.sendAbilityUseMessage(p, "Trap Mode: Trap removed.", this.getName());
							e.setCancelled(true);
						}
					}
				}
				Language.sendAbilityUseMessage(p, "You Can't place a trap here.", this.getName());	
			}
		}
	}
}
