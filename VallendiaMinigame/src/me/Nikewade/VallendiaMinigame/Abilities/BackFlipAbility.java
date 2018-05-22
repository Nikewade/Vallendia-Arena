package me.Nikewade.VallendiaMinigame.Abilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.Nikewade.VallendiaMinigame.Interface.Ability;

public class BackFlipAbility implements Ability{
	double forwardVelocity = -10 / 10D;;
	double upwardVelocity = 8 / 10D;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Backflip";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.RABBIT_FOOT);
	}

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean RunAbility(Player p) {
		Vector v = p.getLocation().getDirection().normalize();
		v.multiply(this.forwardVelocity).setY(this.upwardVelocity);
		p.setVelocity(v);
		p.setFallDistance(0);
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 2, (float) 1.3);
		return true;

	}

}
