package me.Nikewade.VallendiaMinigame.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import net.md_5.bungee.api.ChatColor;

public class AbilityCooldown {
    private static Map<String, AbilityCooldown> cooldowns = new HashMap<String, AbilityCooldown>();
    private static ArrayList<Integer> cooldownSlots = new ArrayList<>();
    private long start;
    private final int timeInSeconds;
    private final UUID id;
    private final String cooldownName;
    private final ItemStack i;
	private int x = -1;
 
    public AbilityCooldown(UUID id, String cooldownName, int timeInSeconds, ItemStack i){
        this.id = id;
        this.cooldownName = cooldownName;
        this.timeInSeconds = timeInSeconds;
        this.i = i;
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
        
        Player p = Bukkit.getPlayer(id);
        
		ItemStack abilityItem = null;
		for(ItemStack item : p.getInventory().getContents())
		{
			x++;
			if(item != null)
			{
				if(item.getType() == Material.INK_SACK && item.getDurability() == 10 && item.getItemMeta().getDisplayName() == i.getItemMeta().getDisplayName())
				{
					abilityItem = item;
					break;
				}
			}
		}
        
        //item display
		new BukkitRunnable() {
            @Override
            public void run() {
            	ItemStack xitem = p.getInventory().getItem(x);
            	if(xitem.getType() != Material.INK_SACK || xitem.getDurability() != 10)
            	{
            		this.cancel();
            		return;
            	}
            	if(getTimeLeft(p.getUniqueId(), cooldownName) > 1)
            	{
        			p.getInventory().getItem(x).setAmount(getTimeLeft(p.getUniqueId(), cooldownName));
            	}else
            			{
            			p.getInventory().getItem(x).setAmount(1);
            			this.cancel(); 
            			}
            }
        }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 20L); 
    }
    
	
	
}
