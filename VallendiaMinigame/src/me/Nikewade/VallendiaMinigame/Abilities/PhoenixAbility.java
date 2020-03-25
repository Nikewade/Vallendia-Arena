package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.DonutEffect;
import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class PhoenixAbility implements Listener, Ability{
	int delay = 8;
	int amplifier = 3;
	int duration = 5;
	int radius = 5;
	int force = 20;
	int yForce = 8;
	int maxYForce = 10;
	ArrayList<Player> dead = new ArrayList<>();
	ArrayList<Player> onFire = new ArrayList<>();

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
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onDeath (EntityDamageEvent e)
	{

		
		if(!(e.getEntity() instanceof Player))
		{
			return;
		}
		
		Player p = (Player) e.getEntity();
		
		
		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Phoenix"))
		{ // && IF ABILITY !ON COOL DOWN 
			
				if(e.isCancelled() || e.getDamage() == 0)
				{
					return;
				}
				if(p.getHealth() - e.getDamage() <= 0)
				{
			
		        ArrayList<ItemStack> items = new ArrayList<>();

		        items.add(new ItemStack(Material.DIAMOND, 4));
		        items.add(new ItemStack(372, 5));
		        
		        if(!AbilityUtils.hasReagentsMultiple(p, items,  "Phoenix", true))
		        {
		            return;
		        }
				dead.add(p);
				double playerX = p.getLocation().getX();
				double playerY = p.getLocation().getY();
				double playerZ = p.getLocation().getZ();
				Location block = p.getLocation();
				Location loc = new Location(p.getWorld(), playerX, playerY , playerZ);
				
				
				while (!loc.getBlock().getType().isSolid()) {
				   
					loc.subtract(0, 1, 0);
					
					if(loc.getBlock().getType().isSolid())
					{
						block = loc;
						
					}
				}
				
				block.add(0, 1F,0);
				Location tploc = block.clone();
				tploc.add(0,1,0);
				
						
				
				p.teleport(tploc);
				
				
				
				//effects
				//----------------------------------------------------------------------------------------------
		  		DonutEffect de = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
		  		
		        de.particle = Particle.REDSTONE;
		        de.color = Color.GRAY;
				de.radiusDonut = 0.9F;
				de.radiusTube = 0.1F;
				de.iterations = 15;
				de.speed = 0;
				de.visibleRange = 50;
				de.xRotation = 300;
				de.setLocation(block);
				
				de.start();
				
		  		DonutEffect de2 = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
		  		
		        de2.particle = Particle.REDSTONE;
		        de2.color = Color.GRAY;
				de2.radiusDonut = 0.6F;
				de2.radiusTube = 0.1F;
				de2.iterations = 15;
				de2.speed = 0;
				de2.visibleRange = 50;
				de2.xRotation = 300;
				de2.particleOffsetY = 2;
				de2.setLocation(block);

				de2.start();
				
		  		DonutEffect de3 = new DonutEffect(VallendiaMinigame.getInstance().effectmanager);
		  		
		        de3.particle = Particle.REDSTONE;
		        de3.color = Color.GRAY;
				de3.radiusDonut = 0.2F;
				de3.radiusTube = 0.1F;
				de3.iterations = 15;
				de3.speed = 0;
				de3.visibleRange = 50;
				de3.xRotation = 300;
				de3.particleOffsetY = 2;
				de3.setLocation(block);

				de3.start();
				
				
		  		SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		  		
		  		se.particle = Particle.SMOKE_NORMAL;
				se.radius = 0.6;
				se.iterations = 170;
				se.particles = 5;
				se.setLocation(block);
                se.particleOffsetX = 0.3F;
                se.particleOffsetZ = 0.3F;
				se.start();
				
		  		SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
		  		
		  		se2.particle = Particle.FLAME;
				se2.radius = 0.6;
				se2.iterations = 170;
				se2.particles = 1;
				se2.setLocation(block);
                se2.particleOffsetX = 0.3F;
                se2.particleOffsetZ = 0.3F;
				se2.start();
				
				p.getWorld().playSound(block, Sound.BLOCK_FIRE_AMBIENT, 1, 0.5F);
				
				new BukkitRunnable()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						p.getWorld().playSound(p.getLocation(), Sound.BLOCK_FIRE_AMBIENT, 1, 0.5F);

						
					}
					
				}.runTaskLater(VallendiaMinigame.getInstance(), 3*20);
				
				new BukkitRunnable()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 0.5F);
						p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1, 0.8F);
						p.getWorld().playSound(p.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1, 0.5F);
						
					}
					
				}.runTaskLater(VallendiaMinigame.getInstance(), delay*20);

				
				//---------------------------------------------------------------------------------------------



				e.setCancelled(true);
				p.setGameMode(GameMode.SPECTATOR);
	 	 		p.getWorld().spawnParticle(Particle.FLAME, p.getLocation().add(0, 1, 0), 20);
	 	 		p.getWorld().spawnParticle(Particle.FLAME, p.getLocation().add(0, 1, 0), 20);
	 	 		p.getWorld().spawnParticle(Particle.FLAME, p.getLocation().add(0, 1.8, 0), 20);
				p.setHealth(p.getMaxHealth()*0.1);
				
				new BukkitRunnable()
						{

							@Override
							public void run() {
								// TODO Auto-generated method stub
								
								dead.remove(p);
								Vector v2 = p.getVelocity().multiply(0);
								p.setVelocity(v2);
								p.teleport(tploc);
								p.setGameMode(GameMode.SURVIVAL);
					 	 		p.getWorld().spawnParticle(Particle.FLAME, p.getLocation().add(0, 1, 0), 20);
					 	 		p.getWorld().spawnParticle(Particle.FLAME, p.getLocation().add(0, 1, 0), 20);
					 	 		p.getWorld().spawnParticle(Particle.FLAME, p.getLocation().add(0, 1.8, 0), 20);
					 	 		
								Location location = p.getLocation().add(0.0D, -1.0F, 0.0D);
							    Vector t = location.toVector();
					 	 		
								for(Entity entity : AbilityUtils.getAoeTargets(p, p.getLocation(), 8, 8, 8))
								{
							          Vector e = entity.getLocation().toVector();
							          Vector v = e.subtract(t).normalize().multiply(force / 10.0D);
							          if (force != 0) {
							            v.setY(v.getY() * (yForce / 10.0D));
							          } else {
							            v.setY(yForce / 10.0D);
							          }
							          if (v.getY() > maxYForce / 10.0D) {
							              v.setY(maxYForce / 10.0D);
							            }
							          entity.setVelocity(v);
								}
					 	 		
					 	 		
					 	 		
								AbilityUtils.addPotionDuration(p, p, "Pheonix", PotionEffectType.SPEED, amplifier, duration*20);
								AbilityUtils.addPotionDuration(p, p, "Pheonix", PotionEffectType.DAMAGE_RESISTANCE, amplifier, duration);
								onFire.add(p);
								p.setFireTicks(duration*20);
								
								new BukkitRunnable()
								{

									@Override
									public void run() {
										// TODO Auto-generated method stub
										
										onFire.remove(p);
										
									}
									
									
								}.runTaskLater(VallendiaMinigame.getInstance(), duration*20);

								
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
			
			if(e.getFrom().getX() != e.getTo().getX() ||
					e.getFrom().getZ() != e.getTo().getZ()||
					e.getFrom().getY() != e.getTo().getY())
			{
				e.setCancelled(true); 
			}		
			
			}
		
			
		}
		
		@EventHandler(priority = EventPriority.LOWEST)
		public void onFireTick (EntityDamageEvent e)
		{
			if(!(e.getEntity() instanceof Player))
			{
				return;
			}
			Player p = (Player) e.getEntity();
			if(onFire.contains(p))
			{
				if(e.getCause() == DamageCause.FIRE_TICK)
				{
					e.setCancelled(true);
				}
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
                e.getPlayer().setSpectatorTarget(null);
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
        
        @EventHandler
        public void onInteract (PlayerInteractEntityEvent e)
        {
        	if(dead.contains(e.getPlayer()))
        	{
        		e.setCancelled(true);
        	}
        }
        
        @EventHandler
        public void onCommand (PlayerCommandPreprocessEvent e)
        {
        	if(dead.contains(e.getPlayer()))
        	{
        		e.setCancelled(true);
        		Language.sendAbilityUseMessage(e.getPlayer(), "Sorry, you can't do that right now!", "Phoenix");
        	}
        }
	
	

}