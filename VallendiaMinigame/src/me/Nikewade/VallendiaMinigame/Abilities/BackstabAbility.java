package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;

public class BackstabAbility implements Ability , Listener {
	private static Random random = new Random();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Backstab";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Hitting your target in the back does 2-4 extra damage.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.IRON_SWORD);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return true;
	}
	
	
    public static Listener getListener() {
        return new Listener() {
        	
        	
        	
        	
            	@EventHandler
            	public void onDamage(EntityDamageByEntityEvent e)
            	{

            		if(e.getDamager() instanceof Player)
            		{
            			Player p = (Player) e.getDamager();
                		if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Backstab") || !(p.getItemInHand().getType() == Material.IRON_SWORD) && !(p.getItemInHand().getType() == Material.DIAMOND_SWORD) && !(p.getItemInHand().getType() == Material.STONE_SWORD))
                		{
                			return;
                		}
            			double damage = e.getDamage();
            			int addedDamage = random.nextInt(4) + 2;
            	        if (e.getEntity().getLocation().getDirection().dot(p.getLocation().getDirection()) <= 0.0D) 
            	          {
            	            return;
            	          }
            	        p.sendMessage("" + e.getFinalDamage());
            			e.setDamage(damage + addedDamage);
            			p.sendMessage("" + e.getFinalDamage());
            			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_HURT, 22, (float) 1.5);
            			p.getWorld().playEffect(e.getEntity().getLocation().add(0,1,0), Effect.STEP_SOUND, Material.REDSTONE_WIRE.getId());
            		}
            	}

           
            
            
            
            
        };
    }
	
	

}
