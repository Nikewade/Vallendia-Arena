package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class PhoenixAbility implements Listener, Ability{
	int delay = 8;
	int amplifier = 3;
	int duration = 5;
	ArrayList<Player> dead = new ArrayList<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Phoenix";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("blah blah ");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack (Material.DRAGON_EGG);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub


		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(dead.contains(p))
		{
			p.setGameMode(GameMode.SURVIVAL);
			dead.remove(p);
		}
	}
	
	@EventHandler
	public void onDeath (EntityDamageEvent e)
	{

		
		if(!(e.getEntity() instanceof Player))
		{
			return;
		}
		
		Player p = (Player) e.getEntity();
		
		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Phoenix"))
		{ // && IF ABILITY !ON COOL DOWN 
			
			if(p.getHealth() - e.getDamage() < 1)
			{
				dead.add(p);
				double playerX = p.getLocation().getX();
				double playerY = p.getLocation().getY();
				double playerZ = p.getLocation().getZ();
				Location loc = new Location(p.getWorld(), playerX, playerY - 1.5, playerZ);
				Location tploc = new Location(p.getWorld(), playerX, playerY + 1, playerZ);
				
				//effects
				//----------------------------------------------------------------------------------------------
		  		SphereEffect se4 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		  		
		        se4.particle = Particle.REDSTONE;
		        se4.color = Color.GRAY;
				se4.radius = 2;
				se4.iterations = 180;
				se4.particles = 100;
				se4.setLocation(loc);
				se4.particleOffsetX = 1F;
				se4.particleOffsetZ = 1F;
				se4.start();
				
				loc.setY(playerY - 0.9);
				
		  		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		  		
		  		se.particle = Particle.SMOKE_NORMAL;
				se.radius = 2;
				se.iterations = 180;
				se.particles = 5;
				se.setLocation(loc);
				se.start();
				
		  		SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		  		
		  		se2.particle = Particle.FLAME;
				se2.radius = 2;
				se2.iterations = 180;
				se2.particles = 2;
				se2.setLocation(loc);
				se2.start();
				
				//ADD SOUNDS
				
				//---------------------------------------------------------------------------------------------
				tploc.setPitch(50);
				tploc.setYaw(90);
				p.teleport(tploc);
				e.setCancelled(true);
				p.setGameMode(GameMode.SPECTATOR);
				p.setHealth(4);
				
				BukkitTask timer = new BukkitRunnable()
						{

							@Override
							public void run() {
								// TODO Auto-generated method stub
								
								dead.remove(p);
								p.setGameMode(GameMode.SURVIVAL);
								AbilityUtils.addPotionDuration(p, p, "Phoenix", PotionEffectType.SPEED, amplifier, duration*20);
								AbilityUtils.addPotionDuration(p, p, "Phoenix", PotionEffectType.DAMAGE_RESISTANCE, amplifier, duration);
								//START COOLDOWN
							}
					
						}.runTaskLater(VallendiaMinigame.getInstance(), delay*20);

			}
			
		}
		
		
	}
	
		@EventHandler 
		public void onMove (PlayerMoveEvent e)
		{
			Player p = e.getPlayer();
			
			if(dead.contains(p))
			{
				e.setCancelled(true);
			}
			
		}
		@EventHandler 
		public void onSwitchSlot (PlayerItemHeldEvent e)
		{
			Player p = e.getPlayer();
			
			if(dead.contains(p))
			{
				e.setCancelled(true);
			}
			
		}
		
        @EventHandler
        public void onTeleport(PlayerTeleportEvent e)
        {
        	Player p = e.getPlayer();
        	
            if (e.getCause().equals(TeleportCause.SPECTATE) && dead.contains(p))
            {
                e.setCancelled(true);
                Language.sendAbilityUseMessage(p, "Sorry, you cant do that!", "Phoenix");
            }
        }
        
        @EventHandler
        public void onInteract (PlayerInteractEvent e)
        {
        	if(dead.contains(e.getPlayer()))
        	{
        		e.setCancelled(true);
        	}
        }
	
	

}