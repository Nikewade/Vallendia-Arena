package me.Nikewade.VallendiaMinigame.Abilities;
 
import java.util.Arrays;
import java.util.List;
 
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
 
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
 
public class LoneWolfAbility implements Listener, Ability {
    int percent = 20;
 
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "Lone Wolf";
    }
 
    @Override
    public AbilityType getAbilityType() {
        // TODO Auto-generated method stub
        return AbilityType.PASSIVE;
    }
 
    @Override
    public List<String> getDescription() {
        // TODO Auto-generated method stub
        return Arrays.asList("You feel the spirit of freedom. Take ",
                + percent + "% less damage if you are not in a party.");
    }
 
    @Override
    public ItemStack getGuiItem() {
        // TODO Auto-generated method stub
        return new ItemStack(Material.BONE);
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
    public void onDamage (EntityDamageByEntityEvent e)
    {
        if(!(e.getEntity() instanceof Player))
        {
            return;
        }
       
        if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility((Player) e.getEntity(), "Lone Wolf"))
        {
            Player p = (Player) e.getEntity();
            if (AbilityUtils.getPlayerParty(p) == null)
            {
                double lowerPercent =  Utils.getPercentHigherOrLower(percent, false);
                double damage = e.getDamage();
               
                p.sendMessage("" + damage);
               
                e.setDamage(damage*lowerPercent);
            }
        }
       
    }
}
   