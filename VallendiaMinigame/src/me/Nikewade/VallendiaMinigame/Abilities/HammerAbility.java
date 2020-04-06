package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class HammerAbility implements Ability, Listener{
	//made by emma
	int time = 10;
	int duration = 1;
	ArrayList<Player> abilityActive = new ArrayList<>();
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Hammer";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("For the next " + time + " seconds all of your",
				"melee attacks stun the opponent for " + duration + " second.") ;
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.ANVIL);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(abilityActive.contains(p))
		{
			return false;
		}
		
		abilityActive.add(p);
		
		Language.sendAbilityUseMessage(p, "Hammer is now Active", "Hammer");
		
		new BukkitRunnable()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						if(abilityActive.contains(p))
						{
							Language.sendAbilityUseMessage(p, "Hammer is no longer Active", "Hammer");
							abilityActive.remove(p);
						}
						
					}
			
			
				}.runTaskLater(VallendiaMinigame.getInstance(), time*20);
				
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(abilityActive.contains(p))
		{
			abilityActive.remove(p);
		}
		
	}

	@EventHandler
	public void onHit (EntityDamageByEntityEvent e)
	{
		if(!(e.getDamager() instanceof Player))
		{
			return;
		}
		Player p = (Player) e.getDamager();
		
		if(abilityActive.contains(p))
		{
			if(!(e.getEntity() instanceof LivingEntity))
			{
				return;
			}
			
			
			LivingEntity target = (LivingEntity) e.getEntity();
			
			AbilityUtils.stun(p, target, "Hammer", duration*20, false);
			
		}
	
	
}
	
}