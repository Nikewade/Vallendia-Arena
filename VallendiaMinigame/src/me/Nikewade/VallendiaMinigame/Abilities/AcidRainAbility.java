package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class AcidRainAbility implements Ability{
	HashMap<Player, BukkitTask> timers = new HashMap<>();
	int delay = 5;
	int duration = 60;
	int period = 1;
	int cycleCount = duration/period;
	int damage = 2;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Acid Rain";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You call forth the power of nature to open storm clouds",
							"filled with acidic rain. The whole map is covered in acid",
							"rain for " + duration + " seconds that does "  + damage + " damage per second",
							"to ALL players that are outside during the abilities",
							"duration. This will broadcast to all players on the server ",
							+ delay + " seconds before the ability is active.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		ItemStack potion = new ItemStack(Material.LINGERING_POTION, 1);

        PotionMeta potionmeta = (PotionMeta) potion.getItemMeta();
        potionmeta.setColor(Color.LIME);
        potion.setItemMeta(potionmeta);
		
		return new ItemStack (potion);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		Language.sendVallendiaBroadcast("ACID RAIN! Find shelter to avoid getting hurt!");	
		for(Player onlinep : Bukkit.getOnlinePlayers())
		{
			onlinep.sendTitle(Utils.Colorate("&3&lACID RAIN"), Utils.Colorate("&8Find shelter to avoid getting hurt!"), 20, 90, 50);
			onlinep.playSound(p.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1, 0.4F);
		}
		
		p.getWorld().setWeatherDuration(delay*20);
		String world = p.getWorld().getName();
		
		BukkitTask timer = new BukkitRunnable()
				{
					int t = 1;
					@Override
					public void run() {

						// TODO Auto-generated method stub
						if(t >= cycleCount)
						{
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sun " + world + " sun");
							timers.remove(p);
							this.cancel();
						}
						
						for(Player player : OutsidePlayers())
						{
                            
			    		   	RegionManager regionManager = VallendiaMinigame.getInstance().worldguard.getRegionManager(player.getWorld());
			    		   	ApplicableRegionSet arset = regionManager.getApplicableRegions(player.getLocation());
			    		   	LocalPlayer localPlayer = VallendiaMinigame.getInstance().worldguard.wrapPlayer(player);
			    		   	if(!arset.allows((StateFlag) VallendiaMinigame.blockAbilities, localPlayer))
			    		   	{
			    		   		continue;
			    		   	}
                            
                            if(!Utils.canDamage(p, player))
                            {
                                continue;
                            }
                            
                            
							AbilityUtils.damageEntity(player, p, damage);		
							
							SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
							se.disappearWithOriginEntity = true;
							se.iterations = 5;
							se.particle = Particle.REDSTONE;
							se.color = Color.GREEN;
							se.radius = 0.8;
							se.particleOffsetY = (float) 0.5;
							se.particles = 3;
							se.yOffset = -0.8;
							se.speed = (float) 0;
							se.setEntity(player);
							se.start();
							
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 0.5F);
						}
						
						t++;
					}
			
				}.runTaskTimer(VallendiaMinigame.getInstance(), delay*20, period*20);
				
				timers.put(p, timer);
						
		return true;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(timers.containsKey(p))
		{
			timers.get(p).cancel();
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sun " + p.getWorld().getName() + " sun");
			timers.remove(p);
		}
		
	}
	
	
	public Collection<Player> OutsidePlayers()
	{	
		Collection<Player> outsideEntities = new ArrayList<Player>();
		for(Player p : Bukkit.getOnlinePlayers())
		{
            if(!(p.getGameMode() == GameMode.SURVIVAL) && !(p.getGameMode() == GameMode.ADVENTURE))
            {
                continue;
            }
			if(!(p.getLocation().getBlockY() < p.getWorld().getHighestBlockYAt(p.getLocation())))
			{
				outsideEntities.add(p);
			}
		}
		return outsideEntities;
	}

}