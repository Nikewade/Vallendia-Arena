package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class StunningBlowsAbility implements Ability, Listener{
	//made by Emma
	int percent = 5;
	int duration = 10;
			

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Stunning Blows";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("All of Yyour hits and damage abilities have a",
				percent + "% chance to stun your target for",
				+ (float) (duration) / 20 + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.ANVIL);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}
	
	@EventHandler
	public void onHit (EntityDamageByEntityEvent e)
	{
		if(e.getDamage() == 0)
		{
			return;
		}
		if(!(e.getEntity() instanceof LivingEntity))
		{
			return;
		}
		if(e.getDamager() instanceof Player)
		{
			Player p = (Player) e.getDamager();
			
			if(!AbilityUtils.runPassive(p, null))
			{
				return;
			}
					
			if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Stunning Blows"))
					{
						int random = Utils.randomNumber(1, 100);
						
						if(random <= percent)
						{
							AbilityUtils.stun(p, (LivingEntity) e.getEntity(), "Stunning Blows", duration, false);
							e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 0.6F);
				 	 		e.getEntity().getWorld().spawnParticle(Particle.CRIT, e.getEntity().getLocation().add(0, 1.8, 0), 20);
				 	 		Language.sendAbilityUseMessage(p, "You stun your target!", "Stunning Blows");
						}
					}
					
			
		}
		
	}

}