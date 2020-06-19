package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class DaggerThrowAbility implements Ability,Listener{
	ArrayList<Item> swords = new ArrayList<>();
	int hitEntityCooldown = 3;
	int pickupTime = 20;
	int range = 60;
	int damage = 4;
	double speed = 1.5;
	ArrayList<Player> active = new ArrayList<>();
	HashMap<Player, BukkitTask> timers = new HashMap<>();
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Dagger Throw";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You throw your dagger, dealing "+ damage +" damage",
							"to any enemies hit. Upon hitting an enemy",
							"using this dagger, you get a reduced " + hitEntityCooldown + " second",
							"cooldown. If you miss your dagger, it will drop",
							"onto the ground ready for you to pick up to use",
							"again. Your dagger also has a low chance to smash",
							"into pieces when hitting walls.");
		
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.DIAMOND_SWORD);
	}

	@Override
	public boolean RunAbility(Player p) {
		for(Item i: swords)
		{
			ItemStack is = i.getItemStack();
			ItemMeta im = is.getItemMeta();
			if(im.getDisplayName() == p.getName())
			{
				return false;
			}
		}
		if(active.contains(p))
		{
			return false;
		}
		active.add(p);
		p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 2, (float) 1.3);
		throwSword(p);
		return false;

    }
	

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		ListIterator<Item> iterator = swords.listIterator();
		while(iterator.hasNext())
		{
			Item i = iterator.next();
			ItemStack is = i.getItemStack();
			ItemMeta im = is.getItemMeta();
			if(im.getDisplayName() == p.getName())
			{
				i.remove();
				swords.remove(i);
			}
		}
		
		if(timers.containsKey(p))
		{
			timers.get(p).cancel();
			timers.remove(p);
		}
	}
	
	@EventHandler
	public void pickup (PlayerPickupItemEvent e)
	{
		if(swords.contains(e.getItem()))
		{
			Item i = e.getItem();
			ItemStack is = i.getItemStack();
			ItemMeta im = is.getItemMeta();
			Player p = Bukkit.getPlayer(im.getDisplayName());
			if(!(e.getPlayer() == p))
			{
				e.setCancelled(true);
				return;
			}
			
			e.setCancelled(true);
			AbilityCooldown.stop(p.getUniqueId(), this.getName());
			swords.remove(e.getItem());
			e.getItem().remove();
			if(timers.containsKey(p))
			{
				timers.get(p).cancel();
				timers.remove(p);
			}
			
			
		}
	}
	
	
	public void throwSword(Player p) {
	    Location location = p.getLocation().add(0,0.5,0);
	    ArmorStand s = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
	    s.setArms(true);
	    s.setBasePlate(false);
	    s.setVisible(false);
	    s.setGravity(false);
	    s.setCollidable(false);
	    s.setSmall(true);
	    s.setCanPickupItems(false);
	    double two =  Math.toRadians(-location.getPitch());
	    double three = Math.toRadians(180.0D);
	    s.setRightArmPose(new EulerAngle(Math.toRadians(351), two, three));
	    s.setItemInHand(new ItemStack(Material.DIAMOND_SWORD));

	    new BukkitRunnable() {

	        int t = 0;
	        Location loc = getFixedLocation(location, speed);
	        double angle = 351;
	      	      
	        @Override
	        public void run() {
	        	        	
	            t++;
	            Location behind = loc.clone();
	            loc = getFixedLocation(loc, speed);
	            s.setRightArmPose(new EulerAngle(Math.toRadians(angle), two, three));
	            s.teleport(loc);
	            
	            if(behind.getBlock().getType().isSolid())
	            {
	            	active.remove(p);
	            	p.getWorld().playSound(s.getLocation(), Sound.ENTITY_BLAZE_HURT, 2, 1.5F);
	            	s.remove();    	
	            	ItemStack itemstack = new ItemStack(Material.DIAMOND_SWORD);
	        		ItemMeta im = itemstack.getItemMeta();
	        		im.setDisplayName(p.getName());
	        		itemstack.setItemMeta(im);
	        		Item item = p.getWorld().dropItem(behind.add(0, 1, 0), itemstack);
	        		item.setVelocity(item.getVelocity().multiply(3));
	        		swords.add(item);
	        		
	    	        AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), "Dagger Throw", 
	    	       VallendiaMinigame.getInstance().abilitymanager.getCooldown("Dagger Throw", p),
	    	       AbilityManager.locateAbilityItem(p, "Dagger Throw"));
	    	        c.start();
	    	        
	    	        BukkitTask timer = new BukkitRunnable()
	        		{

						@Override
						public void run() {
							// TODO Auto-generated method stub
							ListIterator<Item> iterator = swords.listIterator();
							while(iterator.hasNext())
							{
								Item i = iterator.next();
								ItemStack is = i.getItemStack();
								ItemMeta im = is.getItemMeta();
								if(im.getDisplayName() == p.getName())
								{
									i.remove();
									swords.remove(i);
									timers.remove(p);
									break;
								}

							}

						}
	        	
	        		}.runTaskLater(VallendiaMinigame.getInstance(), pickupTime*20);
	        		
	        		timers.put(p, timer);
	    	        
	        		this.cancel();
	            }
	            if(s.getLocation().getBlock().getType().isSolid())
	            {
	            	active.remove(p);
	            	p.getWorld().playSound(s.getLocation(), Sound.ENTITY_BLAZE_HURT, 2, 1.5F);
	            	s.remove();    	
	            	ItemStack itemstack = new ItemStack(Material.DIAMOND_SWORD);
	        		ItemMeta im = itemstack.getItemMeta();
	        		im.setDisplayName(p.getName());
	        		itemstack.setItemMeta(im);
	        		Item item = p.getWorld().dropItem(s.getLocation().add(0, 1, 0), itemstack);
	        		item.setVelocity(item.getVelocity().multiply(2));
	        		swords.add(item);
	        		
	    	        AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), "Dagger Throw", 
	    	       VallendiaMinigame.getInstance().abilitymanager.getCooldown("Dagger Throw", p),
	    	       AbilityManager.locateAbilityItem(p, "Dagger Throw"));
	    	        c.start();
	    	        
	    	        BukkitTask timer = new BukkitRunnable()
	        		{

						@Override
						public void run() {
							// TODO Auto-generated method stub
							ListIterator<Item> iterator = swords.listIterator();
							while(iterator.hasNext())
							{
								Item i = iterator.next();
								ItemStack is = i.getItemStack();
								ItemMeta im = is.getItemMeta();
								if(im.getDisplayName() == p.getName())
								{
									i.remove();
									swords.remove(i);
									timers.remove(p);
									break;
								}

							}

						}
	        	
	        		}.runTaskLater(VallendiaMinigame.getInstance(), pickupTime*20);
	        		
	        		timers.put(p, timer);
	    	        
	        		this.cancel();
	            }
	            
	            for(Entity e: AbilityUtils.getAoeTargets(p, s.getLocation(), 0.5, 0.5, 0.5))
	            {
	            	active.remove(p);
	            	AbilityUtils.damageEntity((LivingEntity) e, p, damage);
	            	
	    	        AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), "Dagger Throw", 3, 
	    	    	AbilityManager.locateAbilityItem(p, "Dagger Throw"));
	    	        c.start();
	    	        
	            	new BukkitRunnable()
	            	{

						@Override
						public void run() {
							// TODO Auto-generated method stub
							s.remove();
						}
	            		
	            	}.runTaskLater(VallendiaMinigame.getInstance(), 5);
	            	this.cancel();
	            }


	            if (t > range) {
	            	active.remove(p);
	            	s.remove();    	
	            	ItemStack itemstack = new ItemStack(Material.DIAMOND_SWORD);
	        		ItemMeta im = itemstack.getItemMeta();
	        		im.setDisplayName(p.getName());
	        		itemstack.setItemMeta(im);
	        		Item item = p.getWorld().dropItem(s.getLocation().add(0, 1, 0), itemstack);
	        		item.setVelocity(item.getVelocity().multiply(2));
	        		swords.add(item);
	        		
	    	        AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), "Dagger Throw", 
	    	       VallendiaMinigame.getInstance().abilitymanager.getCooldown("Dagger Throw", p),
	    	       AbilityManager.locateAbilityItem(p, "Dagger Throw"));
	    	        c.start();
	    	        
	    	        BukkitTask timer = new BukkitRunnable()
	    	        		{

								@Override
								public void run() {
									// TODO Auto-generated method stub
									ListIterator<Item> iterator = swords.listIterator();
									while(iterator.hasNext())
									{
										Item i = iterator.next();
										ItemStack is = i.getItemStack();
										ItemMeta im = is.getItemMeta();
										if(im.getDisplayName() == p.getName())
										{
											i.remove();
											swords.remove(i);
											timers.remove(p);
											break;
										}
									}
								}
	    	        	
	    	        		}.runTaskLater(VallendiaMinigame.getInstance(), pickupTime*20);
	    	        		timers.put(p, timer);
	        		this.cancel();
	            }
	        }
	    }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 1);
	}
	
	private static Location getFixedLocation(Location location, double speed) {
	    Location loc = location.clone();

	    Vector toAdd = loc.getDirection().normalize().multiply(speed);

	    return loc.add(toAdd);
	}
	

}