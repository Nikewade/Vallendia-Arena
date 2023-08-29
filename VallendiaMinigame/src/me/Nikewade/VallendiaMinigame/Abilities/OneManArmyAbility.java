package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
 
public class OneManArmyAbility implements Listener, Ability {
	//made by emma
    double percent;
    int distance = 10;
	int partypercent = 10;
	int solopercent = 25;
    HashMap<Player, Integer> attackerEnemies = new HashMap<>();
    HashMap<Player, Integer> damageeEnemies = new HashMap<>();
   
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "One Man Army";
    }
 
    @Override
    public AbilityType getAbilityType() {
        // TODO Auto-generated method stub
        return AbilityType.PASSIVE;
    }
 
    @Override
    public List<String> getDescription() {
        // TODO Auto-generated method stub
        return Arrays.asList
        		("You feel like the protagonist of an",
                        "old spice commercial. For every enemy within " + distance,
                        "blocks, you deal " +partypercent + "% more damage and take " +partypercent+"%",
                        "less damage. This gors up to " + solopercent + "%",
                        "when you are not in a party.");
    }
 
    @Override
    public ItemStack getGuiItem() {
        // TODO Auto-generated method stub
        return new ItemStack(Material.IRON_HELMET);
    }
 
    @Override
    public boolean RunAbility(Player p) {
        // TODO Auto-generated method stub
        return false;
    }
 
    @Override
    public void DisableAbility(Player p) {
        // TODO Auto-generated method stub
       
        if(attackerEnemies.containsKey(p))
        {
            attackerEnemies.remove(p);
        }
       
        if(damageeEnemies.containsKey(p))
        {
            damageeEnemies.remove(p);
        }
    }
   
    @EventHandler
    public void onDamage (EntityDamageByEntityEvent e)
    {
        Player p = null;
        if(e.getDamager() instanceof Projectile)
        {
        	Projectile proj = (Projectile) e.getDamager();
        	
        	if(!(proj.getShooter() instanceof Player))
        	{
        		return;
        	}
        	
        	if(proj.getShooter() instanceof Player)
        	{ 	
        		
        	p = (Player) proj.getShooter();
        	
        	}
        }else
        {
        	if(e.getDamager() instanceof Player)
        	{
        	p = (Player) e.getDamager();
        	}
        	if(!(e.getDamager() instanceof Player))
        	{
        		return;
        	}
        }
               
        //IF PLAYER IS ATTACKER
        if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "One Man Army"))
        {
            if(AbilityUtils.getPlayerParty(p) == "")
            {
            	int solo = solopercent/100;
            	solo = solo+1;
            	percent = solo;
            }else
            {
            	int party = partypercent/100;
            	party = party+1;
            	percent = party;
            }
            
            int amount = 0;
           
            for (Entity ent : AbilityUtils.getAoeTargetsNonDamage(p, p.getLocation(), distance, distance, distance))
            {
                if(ent instanceof Player)
                {
                	if(!AbilityUtils.partyCheck(p, (Player) ent))
                	{
                		                    
                    amount ++;
                    
                	} 
                }
            }
            amount = amount -1;
            attackerEnemies.put(p, amount);
           
            double damage = e.getFinalDamage();
            double multiplier = Math.pow(percent, attackerEnemies.get(p));
            double newdamage = damage*multiplier;
			e.setDamage(0);
			e.setDamage(DamageModifier.ARMOR, newdamage);
           
            attackerEnemies.remove(p);
 
        }
        
        //IF PLAYER IS ATTACKED
        if(e.getEntity() instanceof Player)
        {
       
        if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility((Player) e.getEntity(), "One Man Army"))
        {
            if(AbilityUtils.getPlayerParty((Player) e.getEntity()) == "")
            {
            	percent = 0.75;
            }else
            {
            	percent = 0.90;
            }
            
            int amount = 0;
           
            for (Entity ent : AbilityUtils.getAoeTargetsNonDamage((Player) e.getEntity(), e.getEntity().getLocation(),
                    distance, distance, distance))
            {
                if(ent instanceof Player)
                {                   
                    amount ++;
 
                }
            }
            amount = amount - 1;
            damageeEnemies.put((Player) e.getEntity(), amount);
           
            double damage = e.getFinalDamage();
            double multiplier = Math.pow(percent, damageeEnemies.get(e.getEntity()));
            double newdamage = damage*multiplier;
			e.setDamage(0);
			e.setDamage(DamageModifier.ARMOR, newdamage);
           
            damageeEnemies.remove(e.getEntity());          
                   
        }
        
        }
       
    }
   
 
}