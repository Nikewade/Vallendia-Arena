package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import net.minecraft.server.v1_12_R1.EntityLiving;

public class QuickDeathAbility implements Ability, Listener{
	//made by emma
	int amplifier = 3;
	int duration = 2;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Quick Death";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You get a burst of energy after killing a player,",
				"gaining speed " + amplifier + " for " + duration + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SUGAR);
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
	public void onDeath (EntityDeathEvent e)
	{
		
		LivingEntity ent = e.getEntity();
		
		if(!(ent.getKiller() instanceof Player))
		{
			return;
		}
		
		Player p = ent.getKiller();
		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Quick Death"))
		{
			AbilityUtils.addPotionDuration(p, p, "Quick Death", PotionEffectType.SPEED, amplifier, duration*20);
			
		}
				

	}



	
	
	
	
}