package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.ProtocolManager;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEquipment;

public class BlinkAbility implements Ability{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Blink";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	
	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Teleport a short distance.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.ENDER_PEARL);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		Block b = p.getTargetBlock(null, 15);
		if(b.getType() != Material.AIR && b.getLocation().add(0, 1, 0).getBlock().getType() == Material.AIR)
		{
	 	 		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 2, (float) 1.4);
	 	 		p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().add(0, 1, 0), 20);
	 	 		p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().add(0, 1, 0), 20);
	 	 		p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().add(0, 1.8, 0), 20);
	 	 		if(!(p.getFallDistance() > 12))
	 	 		{
					p.setFallDistance(0);	
	 	 		}
		        p.teleport(new Location(b.getLocation().getWorld(), b.getLocation().getX(), b.getLocation().getY(), b.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()).add(0, 1, 0));
	 	 		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 2, (float) 1.4);
	 	 		p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().add(0, 1, 0), 20);
	 	 		p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().add(0, 1, 0), 20);
	 	 		p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().add(0, 1, 0), 20);
		        return true;	
		}else Language.sendAbilityUseMessage(p, "Could not teleport.", "Blink");
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}
