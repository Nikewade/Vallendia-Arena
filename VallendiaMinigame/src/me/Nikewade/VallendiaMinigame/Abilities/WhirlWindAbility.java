package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.HelixEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class WhirlWindAbility implements Ability{
	int ticktime = 120;
	int damageticks = 9;
	int damage = 6;
	int radius = 4;
	HashMap<Player, BukkitTask> timers = new HashMap<>();
	
	int force = 15;
	int yForce = 4;
	int maxYForce = 6;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Whirlwind";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You spin furiously for " + ticktime/20 + " seconds, dealing " + damageticks + " ticks",
							"of " + damage + " damage to all enemies within close range and",
							"pushing them back.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.STRING);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		BukkitTask timer = new BukkitRunnable()
		{
			Float t = p.getLocation().getPitch();
			int time = 0;
			int multiplier = 1;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(time == ticktime)
				{
					this.cancel();
				}
				if(t >= 180)
				{
					t = (float) -180;
				}
				if(time == ticktime/damageticks * multiplier)
				{
					p.getWorld().playSound( p.getLocation(), Sound.UI_TOAST_IN, 2, 0.4F);
					HelixEffect se = new HelixEffect(VallendiaMinigame.getInstance().effectmanager);
				      se.particle = Particle.REDSTONE;
				      se.color = Color.fromRGB(179, 178, 177);
				      se.radius = (float) 4;
				      se.speed = (float) 0;
				      se.iterations = 1;
				      se.curve = 5;
				      se.strands = 4;
				      se.setLocation(p.getLocation().add(0,1,0));
				      se.start();
				      
						Location location = p.getLocation().add(0.0D, -1.0F, 0.0D);
					    Vector t = location.toVector();
					    
					for(Entity e : AbilityUtils.getAoeTargets(p, p.getLocation(), radius, radius, radius))
					{
						AbilityUtils.damageEntity((LivingEntity) e, p, damage);  Vector vec = e.getLocation().toVector();
					          Vector v = vec.subtract(t).normalize().multiply(force / 10.0D);
					          if (force != 0) {
					            v.setY(v.getY() * (yForce / 10.0D));
					          } else {
					            v.setY(yForce / 10.0D);
					          }
					          if (v.getY() > maxYForce / 10.0D) {
					              v.setY(maxYForce / 10.0D);
					            }
					          e.setVelocity(v);
					}
					multiplier++;
				}
				Utils.sendPacketPlayOutPosition(p, t, 0);
				time++;				
				t = t+10;

			}
			
		}.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
		
		timers.put(p, timer);
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(timers.containsKey(p))
		{
			timers.get(p).cancel();
			timers.remove(p);
		}
		
	}

}