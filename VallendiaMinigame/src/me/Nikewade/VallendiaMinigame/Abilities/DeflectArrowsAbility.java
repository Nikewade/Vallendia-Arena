package me.Nikewade.VallendiaMinigame.Abilities;
 
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
 
public class DeflectArrowsAbility implements Ability, Listener{
 
    static Random random = new Random();
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "Deflect Arrows";
    }
 
    @Override
    public AbilityType getAbilityType() {
        // TODO Auto-generated method stub
        return AbilityType.PASSIVE;
    }
 
	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("25% chance to deflect arrows, taking no damage.");
	}
 
    @Override
    public ItemStack getGuiItem() {
        // TODO Auto-generated method stub
        return new ItemStack(Material.ARROW);
    }
 
    @Override
    public boolean RunAbility(Player p) {
        // TODO Auto-generated method stub
        return false;
    }
   
    public static Listener getListener() {
        return new Listener() {
            @EventHandler
            public void onDamage(EntityDamageByEntityEvent e) {
                if (!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Arrow)) {
                    return;
                }
                Player p = (Player) e.getEntity();
                Arrow a = (Arrow) e.getDamager();
                if (a.getShooter() == p || !VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Deflect Arrows")) {
                    return;
                }
                int randomNumber = random.nextInt(100)+1;
                if (randomNumber <= 25) {
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 1);
                    e.setCancelled(true);
                    a.remove();
                    p.getInventory().addItem(new ItemStack(Material.ARROW, 1));
                }
            }
        };
    }
   
   
}
