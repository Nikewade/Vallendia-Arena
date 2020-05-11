package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class LayOnHandsAbility implements Ability{
	int healAmount = 10;
	int range = 5;
	int period = 1;
	int length = 10;
	int cycleCount = length/period;
	int perSec = healAmount/cycleCount;
	HashMap <Player, BukkitTask> timers = new HashMap<>();
			
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Lay On Hands";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Heal yourself or an ally for " + healAmount + " health",
							"over " + length + " seconds.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.IRON_NUGGET);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		LivingEntity target = AbilityUtils.getHealingTarget(p, range);
		
		if(!(target instanceof Player) && target != null)
		{
			return false;
		}
		
		BukkitTask timer = new BukkitRunnable()
				{
					int t = 1;
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						if(t >= cycleCount)
						{
							this.cancel();
							timers.remove(target);
						}

						if(target == null)
						{
							AbilityUtils.healEntity(p, perSec);
							p.getWorld().playSound(p.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 1, 1.6F);
							t++;
							return;
						}
						
						AbilityUtils.healEntity(target, perSec);
						target.getWorld().playSound(target.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 1, 1.6F);
						t++;
						
					}
			
				}.runTaskTimer(VallendiaMinigame.getInstance(), 0, period*20);
				
				timers.put((Player) target, timer);

		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(timers.containsKey(p))
		{
			timers.get(p).cancel();
			timers.remove(p);
		}
		
	}

}