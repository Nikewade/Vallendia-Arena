package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
<<<<<<< HEAD
=======
import java.util.HashMap;
>>>>>>> second-repo/master
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
<<<<<<< HEAD
=======
import org.bukkit.Particle;
>>>>>>> second-repo/master
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

<<<<<<< HEAD
=======
import de.slikey.effectlib.effect.SphereEffect;
>>>>>>> second-repo/master
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEquipment;

public class BlurAbility implements Ability, Listener{
	private static ArrayList<Player> enabled = new ArrayList<>();
<<<<<<< HEAD
=======
	private static HashMap<Player,SphereEffect> effect1 = new HashMap<>();
>>>>>>> second-repo/master
	int enabledTime = 10;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Blur";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("For " + enabledTime + " seconds, your body becomes",
				"blurred, shifting and wavering. All melee or ranged",
<<<<<<< HEAD
				"attacks done to you have a 25% chance to miss.");
=======
				"attacks done to you have a 50% chance to miss.");
>>>>>>> second-repo/master
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.STAINED_GLASS);
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
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 2, (float) 1.6);
		
        
		new BukkitRunnable() {
            @Override
            public void run() {
        		if(enabled.contains(p))
        		{
        			Utils.hideArmor(p);
        		}else this.cancel();
            }
        }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1L);
=======
		//main.ghost.addGhost(p);
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 2, (float) 1.6);
		
		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		se.setEntity(p);
		se.disappearWithOriginEntity = true;
		se.infinite();
		se.particle = Particle.SUSPENDED;
		se.radius = 0.8;
		se.particleOffsetY = (float) 0.5;
		se.particles = 15;
		se.yOffset = -0.8;
		se.speed = (float) 0;
		se.start();
		effect1.put(p, se);
>>>>>>> second-repo/master
		
		new BukkitRunnable() {
            @Override
            public void run() {
        		if(enabled.contains(p))
        		{
        			enabled.remove(p);
<<<<<<< HEAD
        			main.ghost.removeGhost(p);
        			Language.sendAbilityUseMessage(p, "Disabled", "Blur");
        			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 2, (float) 0.6);
        			Utils.showArmor(p);
        			
        	        PacketPlayOutEntityEquipment helmetPacket = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(p.getInventory().getHelmet()));
        	        PacketPlayOutEntityEquipment chestPacket = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(p.getInventory().getChestplate()));
        	        PacketPlayOutEntityEquipment legPacket = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(p.getInventory().getLeggings()));
        	        PacketPlayOutEntityEquipment bootsPacket = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.FEET, CraftItemStack.asNMSCopy(p.getInventory().getBoots()));
    	            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(helmetPacket);
    	            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(chestPacket);
    	            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(legPacket);
    	            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(bootsPacket);
=======
        			effect1.get(p).cancel();
        			effect1.remove(p);
        			//main.ghost.removeGhost(p);
        			Language.sendAbilityUseMessage(p, "Disabled", "Blur");
        			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 2, (float) 0.6);

>>>>>>> second-repo/master
        			
        		}
            }
        }.runTaskLater(VallendiaMinigame.getInstance(), enabledTime*20L);
        return true;
	}
	
	
        	@EventHandler
        	public void onDamage(EntityDamageByEntityEvent e)
        	{
        		if(e.getEntity() instanceof Player && enabled.contains(e.getEntity()))
        		{
        			if(e.getCause() == DamageCause.ENTITY_ATTACK || e.getCause() == DamageCause.PROJECTILE || e.getCause() == DamageCause.ENTITY_EXPLOSION)
        			{
            			int random = Utils.randomNumber(1, 100);
<<<<<<< HEAD
            			if(random <= 25)
=======
            			if(random <= 50)
>>>>>>> second-repo/master
            			{
                			e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 2, (float) 1.6);
                			e.setDamage(0);
            				e.setCancelled(true);
            			}	
        			}
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
        			effect1.get(p).cancel();
        			effect1.remove(p);
        			//VallendiaMinigame.getInstance().ghost.removeGhost(p);
>>>>>>> second-repo/master
        		}
        	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
