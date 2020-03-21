package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class YeetAbility implements Listener, Ability{
	//made by Emma
	int distance = 5;
	int duration = 8;
	double forwardVelocity = 15 / 10D;
	double upwardVelocity = 2 / 10D;
	// caster , passenger
	HashMap<Player, LivingEntity> abilityActive = new HashMap<>();
	// caster , timer
	HashMap<Player, BukkitTask> timers = new HashMap<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Yeet";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You pick up and yeet your target.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SNOW_BALL);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(abilityActive.containsKey(p))
		{
			p.sendMessage("return false");
			return false;
		}
		
			if(AbilityUtils.getTargetBoth(p, distance) == null)
		{
				return false;
		}
				
		LivingEntity target = AbilityUtils.getTargetBoth(p, distance);
		abilityActive.put(p, target);
		p.setPassenger(target);
		
		BukkitTask timer = new BukkitRunnable() 
		{

			@Override
			public void run() {
				
				if(abilityActive.containsKey(p))
				{
					abilityActive.remove(p);
					target.leaveVehicle();
					Language.sendAbilityUseMessage(p, "Your target escapes your grasp!", "Yeet");
				}

			}
			
			
		}.runTaskLater(VallendiaMinigame.getInstance(), duration*20);
		
		timers.put(p, timer);
	
		return true;
		
		
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(abilityActive.containsKey(p))
		{
			abilityActive.remove(p);
			if(!(p.getPassenger() == null))
			{
				p.getPassenger().leaveVehicle();
			}
		}

		
	}
	
	@EventHandler
	public void onClick (PlayerAnimationEvent e)
	{
		if(!(e.getAnimationType()==PlayerAnimationType.ARM_SWING))
		{
			return;
		}
		
		Player p = e.getPlayer();
		if(abilityActive.containsKey(p))
		{
			
			abilityActive.remove(p);
			timers.get(p).cancel();
			p.sendMessage("tttttt");
			LivingEntity target = (LivingEntity) p.getPassenger();
			target.leaveVehicle();
			Vector v = p.getLocation().getDirection();
			v.setY(0).normalize().multiply(forwardVelocity).setY(upwardVelocity);
			target.setVelocity(v);
			
		}
		
}
	
	@EventHandler
	public void onDamage (EntityDamageByEntityEvent e)
	{

		if(abilityActive.containsValue(e.getEntity()) ||
				abilityActive.containsKey(e.getEntity()))
		{
			e.setCancelled(true);

		}
		
	}
	
	@EventHandler
	public void onVehicleLeave (VehicleExitEvent e)
	{
		if(abilityActive.containsKey(e.getVehicle()))
		{
			abilityActive.remove(e.getVehicle());
		}
	}
	

}