package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
 
public class OneManArmyAbility implements Listener, Ability {
	//made by emma
    int percent = 5;
    int distance = 10;
    int cappercent = 60;
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
                ("You feel like the protagonist of an old spice commercial.",
                "For every enemy within " + distance + " blocks, you deal " + percent + "% more damage",
                "and take" + percent + "% less damage. This caps at 60%.");
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
        if(!(e.getEntity() instanceof Player))
        {
            return;
        }
       
        Player p = (Player) e.getDamager();
       
        //IF PLAYER IS ATTACKER
        if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "One Man Army"))
        {
 
            int amount = 0;
           
            for (Entity ent : AbilityUtils.getAoeTargetsNonDamage(p, p.getLocation(), distance, distance, distance))
            {
                if(ent instanceof Player)
                {
                    // if cap is 60% == 12
                    if(amount >= cappercent/percent)
                    {
                        break;
                    }
                   
                    amount ++;
 
                }
            }
            attackerEnemies.put(p, amount);
           
            double damage = e.getDamage();
            int multiplier = attackerEnemies.get(p);
            double highpercent = Utils.getPercentHigherOrLower(percent*multiplier, true);
            double newdamage = damage*highpercent;
            p.sendMessage("enemies = " + multiplier);
            p.sendMessage("initial attack damage = " + damage);
            p.sendMessage("new attack damage = " + newdamage);
            e.setDamage(newdamage);
           
            attackerEnemies.remove(p);
 
        }
       
        //IF PLAYER IS ATTACKED
        if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility((Player) e.getEntity(), "One Man Army"))
        {
            int amount = 0;
           
            for (Entity ent : AbilityUtils.getAoeTargetsNonDamage((Player) e.getEntity(), e.getEntity().getLocation(),
                    distance, distance, distance))
            {
                if(ent instanceof Player)
                {
                    // if cap is 60% == 12
                    if(amount >= cappercent/percent)
                    {
                        break;
                    }
                   
                    amount ++;
 
                }
            }
            damageeEnemies.put((Player) e.getEntity(), amount);
           
            double damage = e.getDamage();
            int multiplier = damageeEnemies.get(e.getEntity());
            double lowpercent = Utils.getPercentHigherOrLower(percent*multiplier, false);
            double newdamage = damage*lowpercent;
            p.sendMessage("enemies = " + multiplier);
            p.sendMessage("initial damage = " + damage);
            p.sendMessage("new damage = " + newdamage);
            e.setDamage(newdamage);
           
            damageeEnemies.remove(e.getEntity());          
                   
        }
       
    }
   
 
}