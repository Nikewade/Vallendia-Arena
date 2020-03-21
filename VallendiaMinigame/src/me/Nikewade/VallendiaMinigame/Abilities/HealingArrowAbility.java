package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class HealingArrowAbility implements Listener, Ability {
	ArrayList<Player> abilityActive = new ArrayList<>();
	int heal = 10;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Healing Arrow";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Your next arrow heals for " + heal + " damage. works on enemies too.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return Utils.getTippedArrowItemStack(PotionType.REGEN, 1, false, false, "");
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(abilityActive.contains(p))
		{
			return false;
		}
		
		abilityActive.add(p);
		Language.sendAbilityUseMessage(p, "Healing Arrow now Active", "Healing Arrow");
		
		return false;
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
		if(e.getDamager() instanceof Projectile)
		{
			Projectile arrow = (Projectile) e.getDamager();
			
			if(arrow.getShooter()instanceof Player)
			{
				
			Player attacker = (Player) arrow.getShooter();
			if(abilityActive.contains(attacker))
			{
				e.setCancelled(true);
				Language.sendAbilityUseMessage(attacker, "You heal your target", "Healing Arrow");
				LivingEntity ent = (LivingEntity) e.getEntity();
				AbilityUtils.healEntity(ent, heal);
				abilityActive.remove(attacker);

			}
			
			}
		}
			
		
	}

	


}