package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class RageAbility implements Ability{
private static ArrayList<Player> raging = new ArrayList<>();
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Rage";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.OFFENSIVE;
	}

	
	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Go into a frenzied rage, increasing damage and speed.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		ItemStack item = Utils.getPotionItemStack(PotionType.STRENGTH, 1, false, false, "");
		return item;
	}

	@Override
	public boolean RunAbility(Player p) {
			int ragetime = 60;
			int force = 17;
			int yForce = 8;
			int maxYForce = 10;
			p.sendTitle(ChatColor.DARK_RED + "" + ChatColor.BOLD + "YOU RAGE!", ChatColor.RED + " Your rage will last for " + ragetime + " seconds" , 20, 90, 50);	
			if(p.getHealth() < p.getMaxHealth() && p.getHealth() + 5 < p.getMaxHealth())
			{
		    	p.setHealth(p.getHealth() + 5 );	
			}else p.setHealth(p.getMaxHealth());
			AbilityUtils.addPotionDuration(p, PotionEffectType.INCREASE_DAMAGE, 0, ragetime * 20);
			AbilityUtils.addPotionDuration(p, PotionEffectType.SPEED, 1, ragetime * 20);
			AbilityUtils.addPotionDuration(p, PotionEffectType.DAMAGE_RESISTANCE, 0, ragetime * 20);
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 2, (float) 0.4);
			raging.add(p);
			Location location = p.getLocation().add(0.0D, -1.0F, 0.0D);
		    Vector t = location.toVector();

			for(Entity entity : AbilityUtils.getAoeTargets(p, 8, 8, 8))
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
			

	    	
	    	new BukkitRunnable() {
	    		int ragetimer = ragetime;
	    		int maxtime = 0;
	            @Override
	            public void run() {	
	            		ragetimer--;
	            		maxtime++;
	            		if(maxtime == 300) //5mins
	            		{
	            			this.cancel();
	            		}
	            		if(!raging.contains(p))
	            		{
	            			this.cancel();
	            		}
	                	if (!p.isOnline())
	                	{
	                		ragetimer = ragetimer + 1;
	                	}
	                	if(ragetimer  == 5)
	                	{
	                		Bukkit.getServer().getPlayer(p.getName()).sendTitle(ChatColor.RED + "You begin to tire", ChatColor.RED + " Your rage will end in " + ragetimer + " seconds...", 20, 50, 20);	
	                	}	
	                	if(ragetimer == 0)
	                	{
	    	                AbilityUtils.addPotionDuration(p, PotionEffectType.WEAKNESS, 0, ragetime*20);
	    	                AbilityUtils.addPotionDuration(p, PotionEffectType.CONFUSION, 1, 8*20);
	    	                AbilityUtils.addPotionDuration(p, PotionEffectType.SLOW, 1, ragetime*20);
	    	        		p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Rage]" + ChatColor.RED + " You feel fatigued.");
	    	        		
	                		this.cancel();
	                	}
	            }
		}.runTaskTimer(VallendiaMinigame.getInstance(), 20L, 20L);	
		return true;
	}
	
	
	public static void onDie(Player p)
	{
		raging.remove(p);
	}

}
