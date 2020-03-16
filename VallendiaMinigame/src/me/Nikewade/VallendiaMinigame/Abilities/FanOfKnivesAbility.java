package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;

public class FanOfKnivesAbility implements Ability, Listener{
	//made by Emma
	
	int arrowSpread = 15;	
	private static int damage = 6;
	private static List<Snowball> arrows = new ArrayList<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Fan Of Knives";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You spray a fan of knives around you, each",
				"dealing " + damage + " damage.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.IRON_SWORD);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		Location loc = p.getLocation();
		loc.setPitch(0);
		for(int x = 0; x <=360; x = x + arrowSpread)
		{
						
			loc.setYaw( x);
			Snowball arrow = p.launchProjectile(Snowball.class, loc.getDirection() );	
			arrow.setShooter(p);
			
			for(Player all : Bukkit.getServer().getOnlinePlayers()) 
			{
			    PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(arrow.getEntityId());
			    ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packet);
			}
			
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
	        se.particle = Particle.CRIT;
	        se.disappearWithOriginEntity = true;
	        se.infinite();
	        se.radius = 0.1;
	        se.particles = 1;
	        se.speed = (float) 0;
	        se.visibleRange = 50;
	        se.setEntity(arrow);
	        se.start();
        	        
	        arrows.add(arrow);
			
		}
 	 	
		new BukkitRunnable() {
        	int time = 0;
            @Override
            public void run() {
            	time ++;
            	if(time >= 5)
            	{
            		this.cancel();
            	}
         	 	p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, (float) 1);
            }
		}.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}
	
        	@EventHandler
        	public void onDamage (EntityDamageByEntityEvent e)
        	{
        		if(e.getEntity()instanceof LivingEntity && e.getDamager()instanceof Snowball)
        		{
        			LivingEntity target = (LivingEntity) e.getEntity();
        			Projectile ball = (Projectile) e.getDamager();
        			
        			if(ball.getShooter() instanceof Player)
        			{    				
        				Player p = (Player) ball.getShooter();
        				
        				if(arrows.contains(ball))
        				{
        					e.setCancelled(true);
        					AbilityUtils.damageEntity(target, p, damage);
        					
        					arrows.remove(ball);
        				}
        				
        			}
        					       			
        			
        		}
        	}
        	
        	public void onHit (ProjectileHitEvent e)
        	{
        		if(e.getEntity()instanceof Snowball)
        		{
        			if(arrows.contains(e.getEntity()))
        			{        				
        				if(!(e.getHitEntity() instanceof Player) || !(e.getHitEntity() instanceof LivingEntity))
        				arrows.remove(e.getEntity());
        			}
        		}
        	}

}