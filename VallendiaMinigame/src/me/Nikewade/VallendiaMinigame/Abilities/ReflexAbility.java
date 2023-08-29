package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
<<<<<<< HEAD
=======
import org.bukkit.event.EventPriority;
>>>>>>> second-repo/master
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEquipment;

public class ReflexAbility implements Ability, Listener{
	private static ArrayList<Player> enabled = new ArrayList<>();
	int enabledTime = 1;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Reflex";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You quickly dodge out of the way of any harm",
<<<<<<< HEAD
				"becoming completly invincible for " + enabledTime + " second.");
=======
				"becoming completely invincible for " + enabledTime + " second.");
>>>>>>> second-repo/master
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.LEATHER_LEGGINGS);
	}

	@Override
	public boolean RunAbility(Player p) {
		if(enabled.contains(p))
		{
			return false;
		}
		VallendiaMinigame main = VallendiaMinigame.getInstance();
		enabled.add(p);
<<<<<<< HEAD
		main.ghost.addGhost(p);
=======
		//main.ghost.addGhost(p);
>>>>>>> second-repo/master
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1, (float) 1.6);
		
        
		new BukkitRunnable() {
            @Override
            public void run() {
        		if(enabled.contains(p))
        		{
        			Utils.hideArmor(p);
        		}else this.cancel();
            }
        }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1L);
		
		new BukkitRunnable() {
            @Override
            public void run() {
        		if(enabled.contains(p))
        		{
        			enabled.remove(p);
<<<<<<< HEAD
        			main.ghost.removeGhost(p);
=======
        			//main.ghost.removeGhost(p);
>>>>>>> second-repo/master
        			Language.sendAbilityUseMessage(p, "Disabled", "Reflex");
        			Utils.showArmor(p);
        			
        	        PacketPlayOutEntityEquipment helmetPacket = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(p.getInventory().getHelmet()));
        	        PacketPlayOutEntityEquipment chestPacket = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(p.getInventory().getChestplate()));
        	        PacketPlayOutEntityEquipment legPacket = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(p.getInventory().getLeggings()));
        	        PacketPlayOutEntityEquipment bootsPacket = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.FEET, CraftItemStack.asNMSCopy(p.getInventory().getBoots()));
    	            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(helmetPacket);
    	            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(chestPacket);
    	            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(legPacket);
    	            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(bootsPacket);
        			
        		}
            }
        }.runTaskLater(VallendiaMinigame.getInstance(), enabledTime*20L);
        return true;
	}
	
	
	
        	
<<<<<<< HEAD
        	@EventHandler
=======
        	@EventHandler(priority = EventPriority.LOWEST)
>>>>>>> second-repo/master
        	public void onDamage(EntityDamageEvent e)
        	{
        		if(e.getEntity() instanceof Player && enabled.contains(e.getEntity()))
        		{
                			e.setDamage(0);
            				e.setCancelled(true);
        		}
        	}
        	
        	@EventHandler
        	public void onLeave(PlayerQuitEvent e)
        	{
        		if(enabled.contains(e.getPlayer()))
        		{
        			Player p = e.getPlayer();
        			enabled.remove(p);
<<<<<<< HEAD
        			VallendiaMinigame.getInstance().ghost.removeGhost(p);
=======
        			//VallendiaMinigame.getInstance().ghost.removeGhost(p);
>>>>>>> second-repo/master
        		}
        	}
        	

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
