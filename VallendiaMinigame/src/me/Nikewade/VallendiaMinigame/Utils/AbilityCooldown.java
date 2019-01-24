package me.Nikewade.VallendiaMinigame.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import net.md_5.bungee.api.ChatColor;

public class AbilityCooldown {
    private static Map<String, AbilityCooldown> cooldowns = new HashMap<String, AbilityCooldown>();
    private long start;
    private final int timeInSeconds;
    private final UUID id;
    private final String cooldownName;
 
    public AbilityCooldown(UUID id, String cooldownName, int timeInSeconds){
        this.id = id;
        this.cooldownName = cooldownName;
        this.timeInSeconds = timeInSeconds;
    }
 
    public static boolean isInCooldown(UUID id, String cooldownName){
        if(getTimeLeft(id, cooldownName)>=1){
            return true;
        } else {
            stop(id, cooldownName);
            return false;
        }
    }
 
    private static void stop(UUID id, String cooldownName){
        AbilityCooldown.cooldowns.remove(id+cooldownName);
    }
 
    private static AbilityCooldown getCooldown(UUID id, String cooldownName){
        return cooldowns.get(id.toString()+cooldownName);
    }
 
    public static int getTimeLeft(UUID id, String cooldownName){
        AbilityCooldown cooldown = getCooldown(id, cooldownName);
        int f = -1;
        if(cooldown!=null){
            long now = System.currentTimeMillis();
            long cooldownTime = cooldown.start;
            int totalTime = cooldown.timeInSeconds;
            int r = (int) (now - cooldownTime) / 1000;
            f = (r - totalTime) * (-1);
        }
        return f;
    }
 
    public void start(){
        this.start = System.currentTimeMillis();
        cooldowns.put(this.id.toString()+this.cooldownName, this);
    }
    
    public static void itemCooldown(Player p, String abilitySlot)
    {
		int x = -1;
		ItemStack abilityItem = null;
		for(ItemStack item : p.getInventory().getContents())
		{
			x++;
			if(item != null)
			{
				if(item.getType() == Material.INK_SACK && item.getDurability() == 10 &&
						ChatColor.stripColor(Utils.Colorate(item.getItemMeta().getLore().get(0).toLowerCase())) == abilitySlot)
				{
					abilityItem = item;
					break;
				}
			}
		}
	   	String ability = VallendiaMinigame.getInstance().playerdatamanager.getPlayerStringData(p.getUniqueId(), 
	   			"Abilities." + ChatColor.stripColor(Utils.Colorate(abilityItem.getItemMeta().getLore().get(0).toLowerCase())));
		p.sendMessage("" + VallendiaMinigame.getInstance().abilitymanager.getAbility(ability).getName());
    }
	
	
}
