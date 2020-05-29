package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.CustomEvents.BuyAbilityEvent;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class InvestorAbility implements Ability, Listener{
	//made by emma
	List<Player> investing = new ArrayList<>();	
	HashMap<Player, BukkitTask> timers = new HashMap<>();
	int period = 60;
	int descpercent = 6;
	// percent is the 100 - the percent you want
	int percent = 94;
	int cap = 80;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Investor";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You invest your Essence and gain " + descpercent + " percent of",
								"your total Essence every " + period + " seconds. This doesn't",
								"work if you are in spawn.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.DIAMOND);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@EventHandler 
	public void onJoin (PlayerJoinEvent e)
	{
		if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(e.getPlayer(), this.getName()))
		{
			Player p = e.getPlayer();
			if(investing.contains(p))
			{
				return;
			}	
			if(timers.containsKey(p))
			{
				return;
			}
			
			investing.add(p);
			
			BukkitTask timer = new BukkitRunnable()
			{

						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(!investing.contains(p))
							{
								if(timers.containsKey(p))
								{
									timers.remove(p);
								}
								this.cancel();
							}
							if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Investor"))
							{
								if(investing.contains(p))
								{
									investing.remove(p);
								}
								if(timers.containsKey(p))
								{
									timers.remove(p);
								}
								this.cancel();
							}
							
		    		        RegionManager regionManager = VallendiaMinigame.getInstance().worldguard.getRegionManager(p.getWorld());
		    		        ApplicableRegionSet set = regionManager.getApplicableRegions(p.getLocation());

		    		        for (ProtectedRegion region : set) {

		    		            if (region != null){

		    		            	if(region.getId().equalsIgnoreCase("minigamespawn"))
		    		            	{
		    		            		return;
		    		            	}

		    		            }

		    		        }
							
							int currentpoints = VallendiaMinigame.getInstance().shopmanager.getPoints(p);
							double percentpoints =  Utils.getPercentHigherOrLower(percent, false);
							int addpoints = (int) (percentpoints*currentpoints);
							if(addpoints <= 0)
							{
								addpoints = 1;
							}
							if(addpoints >= cap)
							{
								addpoints = cap;
							}
							VallendiaMinigame.getInstance().shopmanager.addPoints(p, addpoints);
							}
				
			}.runTaskTimer(VallendiaMinigame.getInstance(), 0, period*20);
			
			timers.put(p, timer);
		}
	}
	
	@EventHandler
	public void onBuy (BuyAbilityEvent e)
	{
		if(!(e.getAbility() == this.getName()))
		{
			return;
		}		
		
		Player p = e.getPlayer();
	
		if(investing.contains(p))
		{

			return;
		}
		if(timers.containsKey(p))
		{
			return;
		}
		investing.add(p);
		
		BukkitTask timer = new BukkitRunnable()
		{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(!investing.contains(p))
						{
							if(timers.containsKey(p))
							{
								timers.remove(p);
							}
							this.cancel();
						}
						if(!VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p, "Investor"))
						{
							if(investing.contains(p))
							{
								investing.remove(p);
							}
							if(timers.containsKey(p))
							{
								timers.remove(p);
							}
							this.cancel();
						}
						
	    		        RegionManager regionManager = VallendiaMinigame.getInstance().worldguard.getRegionManager(p.getWorld());
	    		        ApplicableRegionSet set = regionManager.getApplicableRegions(p.getLocation());

	    		        for (ProtectedRegion region : set) {

	    		            if (region != null){

	    		            	if(region.getId().equalsIgnoreCase("minigamespawn"))
	    		            	{
	    		            		return;
	    		            	}

	    		            }

	    		        }
						
						int currentpoints = VallendiaMinigame.getInstance().shopmanager.getPoints(p);
						double percentpoints =  Utils.getPercentHigherOrLower(percent, false);
						int addpoints = (int) (percentpoints*currentpoints);
						if(addpoints <= 0)
						{
							addpoints = 1;
						}
						if(addpoints >= cap)
						{
							addpoints = cap;
						}
						VallendiaMinigame.getInstance().shopmanager.addPoints(p, addpoints);
						
						}
			
		}.runTaskTimer(VallendiaMinigame.getInstance(), 0, period*20);
		
		timers.put(p, timer);
	}
	

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(investing.contains(p))
		{
			investing.remove(p);
		}
		if(timers.containsKey(p))
		{
			timers.get(p).cancel();
			timers.remove(p);
		}
		
	}

}