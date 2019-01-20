package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityData;
import me.Nikewade.VallendiaMinigame.Utils.StaticValues;

public class KineticBarrierAbility implements Ability,  Listener {


    private static HashMap<String, AbilityData> abilityUsers = new HashMap<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Kinetic Barrier";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SHIELD);
	}

	@Override
	public boolean RunAbility(Player p) {
        AbilityData abilityData = new AbilityData();
        abilityData.setData("KINETICBARRIER_TimeRemaining", StaticValues.KINETICBARRIER_DURATION);
        abilityData.setData("KINETICBARRIER_DamageAbsorbed", 0D);
        abilityUsers.put(p.getUniqueId().toString(), abilityData);
        return true;
	}
	
	
	
   public static void startTimeCountTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<String> usersToRemove = new ArrayList<>();
                for (String user : abilityUsers.keySet()) {
                    AbilityData abilityData = abilityUsers.get(user);
                    double timeRemaining = (double) abilityData.getData("KINETICBARRIER_TimeRemaining");
                    if (timeRemaining <= 0) {
                        usersToRemove.add(user);
                        continue;
                    }
                    abilityData.setData("KINETICBARRIER_TimeRemaining", timeRemaining - 0.25);
                }

                for (String user : usersToRemove)
                    abilityUsers.remove(user);
            }
        }.runTaskTimer(VallendiaMinigame.getInstance(), 5, 5);
    }


	
	
    public static Listener getListener() {
        return new Listener() {
        	
            @EventHandler(priority = EventPriority.HIGHEST)
            public void onEntityDamage(EntityDamageEvent event) {
                if (!(event.getEntity() instanceof Player)) return;
                if (event.isCancelled()) return;
                Player player = (Player) event.getEntity();
                if (!abilityUsers.containsKey(player.getUniqueId().toString())) return;
                double finalDamage = event.getFinalDamage();
                event.setDamage(event.getDamage() / StaticValues.KINETICBARRIER_DAMAGEDIVIDER);
                finalDamage -= event.getFinalDamage();
                AbilityData abilityData = abilityUsers.get(player.getUniqueId().toString());
                abilityData.setData("KINETICBARRIER_DamageAbsorbed", ((double) abilityData.getData("KINETICBARRIER_DamageAbsorbed")) + finalDamage);
            }

            @EventHandler(priority = EventPriority.HIGHEST)
            public void onEntityDaamgeByEntityEvent(EntityDamageByEntityEvent event) {
                if (!(event.getDamager() instanceof Player)) return;
                if (event.isCancelled()) return;
                Player player = (Player) event.getDamager();
                if (!abilityUsers.containsKey(player.getUniqueId().toString())) return;
                double damageAbsorbed = (double) abilityUsers.get(player.getUniqueId().toString()).getData("KINETICBARRIER_DamageAbsorbed");
                event.setDamage(event.getDamage() + damageAbsorbed);
                event.getDamager().sendMessage("" + damageAbsorbed);
                abilityUsers.remove(player.getUniqueId().toString());
            }
	
        };
    }
	
	
}
