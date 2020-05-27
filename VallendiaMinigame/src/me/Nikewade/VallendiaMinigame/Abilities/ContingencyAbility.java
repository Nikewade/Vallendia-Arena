package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class ContingencyAbility implements Ability{
	int castTime = 3;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Contingency";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

    @Override
    public List<String> getDescription() {
        // TODO Auto-generated method stub
        return Arrays.asList("Teleport to a random place on the map.",
                            Utils.Colorate("&8Cast: " + castTime + " seconds."));
    }

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.ENDER_PEARL);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		Runnable run = new Runnable()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
			 	 		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 2, (float) 1.1);
			 	 		p.getWorld().spawnParticle(Particle.SPELL_WITCH, p.getLocation().add(0, 1, 0), 20);
			 	 		p.getWorld().spawnParticle(Particle.SPELL_WITCH, p.getLocation().add(0, 1, 0), 20);
			 	 		p.getWorld().spawnParticle(Particle.SPELL_WITCH, p.getLocation().add(0, 1.8, 0), 20);
						VallendiaMinigame.getInstance().spawnhandler.teleportPlayerRandom(p);
					}
			
				};
				
				AbilityUtils.castAbility(p, castTime, run);
				
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}