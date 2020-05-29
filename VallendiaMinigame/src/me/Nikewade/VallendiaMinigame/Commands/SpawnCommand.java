package me.Nikewade.VallendiaMinigame.Commands;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.SirBlobman.combatlogx.utility.CombatUtil;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.effect.ConeEffect;
import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import nl.martenm.servertutorialplus.api.ServerTutorialApi;

public class SpawnCommand implements CommandInterface, Listener{
	VallendiaMinigame  main = VallendiaMinigame.getInstance();
	YamlConfiguration config;
	ArrayList<Player> spawning = new ArrayList<>();
	HashMap<Player, Effect> effect1 = new HashMap<>();
	HashMap<Player, Effect> effect2 = new HashMap<>();
	HashMap<Player, BukkitTask> tasks = new HashMap<>();
	HashMap<Player, BukkitTask> tasks2 = new HashMap<>();
	
	int warmup = 15;
	int cooldown = 240;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
	    if(sender instanceof  Player && !sender.hasPermission("vallendia.admin"))
	    {
	    	sender.sendMessage(Utils.Colorate("&8You lack permissions!"));
	    	return false;	    	
	    }
	    if(!(args.length >1) || args.length > 3)
	    {
			sender.sendMessage(Utils.Colorate("&8&m---------------&8&l Vallendia &m---------------"));
			sender.sendMessage("");
			sender.sendMessage(Utils.Colorate("&3/spawn (basic spawn command)"));
			sender.sendMessage(Utils.Colorate("&3/vallendia spawn add &9(spawnname)"));
			sender.sendMessage(Utils.Colorate("&3/vallendia spawn remove &9(spawnname)"));
			sender.sendMessage(Utils.Colorate("&3/vallendia spawn teleport &9(spawnname)"));
			sender.sendMessage("");
			sender.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));	    
			return false;
	    }else {sender.sendMessage(Utils.Colorate("&8That command doesn't exist!"));}
	    
	    if(sender instanceof Player)
	    {
	    	Player p = (Player) sender;
		    if(args.length == 3)
		    {
		    		if(args[1].equalsIgnoreCase("add"))
		    		{
						String spawnname = args[2].toLowerCase();
						File f = new File(VallendiaMinigame.getInstance().getFileManager().getSpawnFile().getAbsolutePath() + "/" + spawnname + ".yml");
						if(!f.exists())
						{
							main.spawnhandler.createFile(spawnname, p.getLocation());
							p.sendMessage(Utils.Colorate("&8Spawn " + spawnname + " set to your location."));	
							return false;
						}else p.sendMessage(Utils.Colorate("&8Spawn " + spawnname + " already exists."));	
		    		}
		    		
		    		if(args[1].equalsIgnoreCase("remove"))
		    		{
						String spawnname = args[2].toLowerCase();
						File f = new File(VallendiaMinigame.getInstance().getFileManager().getSpawnFile().getAbsolutePath() + "/" + spawnname + ".yml");
						if(f.exists())
						{
							f.delete();
							p.sendMessage(Utils.Colorate("&8Spawn " + spawnname + " removed."));	
							return false;
						}else p.sendMessage(Utils.Colorate("&8Spawn " + spawnname + " does not exist."));
		    		}
		    		
		    		if(args[1].equalsIgnoreCase("teleport"))
		    		{
						String spawnname = args[2].toLowerCase();
						if(main.spawnhandler.teleportPlayer(p, spawnname))
						{
							p.sendMessage(Utils.Colorate("&8Teleported to " + spawnname + "."));
							return false;
						}else p.sendMessage(Utils.Colorate("&8Spawn " + spawnname + " does not exist."));
		    		}
		    }
	    }
		return false;
	}
	
	
	
	
	
	//SPAWN COMMAND
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e)
	{	
		
		if(e.getMessage().equalsIgnoreCase("/spawn") || e.getMessage().contains("/spawn"))
		{
			if(e.getMessage().contains("/spawnmob"))
			{
				return;
			}
			Player p = e.getPlayer();
			if(ServerTutorialApi.getApi().isInTutorial(p.getUniqueId()))
			{
				e.setCancelled(true);
				Language.sendDefaultMessage(p, "You can't do this while in the tutorial!");
				return;
			}
			if(CombatUtil.isInCombat(p))
			{
				Language.sendVallendiaMessage(p, "You can't perform this command in combat!");
				e.setCancelled(true);
				return;
			}
			e.setCancelled(true);
			
			
			if(!AbilityCooldown.isInCooldown(p.getUniqueId(), "spawn"))
			{  
	    		AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), "spawn", cooldown, null);
	    		c.start();	
			}else
			{
				Language.sendVallendiaMessage(p, "That command is on cooldown! (" +
				AbilityCooldown.getTimeLeft(p.getUniqueId(), "spawn") + " secs)");
				return;
			}
			
			if(spawning.contains(p))
			{
				Language.sendDefaultMessage(p, "&3You are already teleporting!");
				return;
			}else
			{
				spawning.add(p);
			}
			

			Language.sendDefaultMessage(p, "&3Teleport commencing in " + warmup + " seconds...");
			
 			ConeEffect se = new ConeEffect(VallendiaMinigame.getInstance().effectmanager);
 			Location loc = p.getLocation();
 			se.infinite();
 			loc.subtract(0, 0.4, 0);
 			se.particle = Particle.DRAGON_BREATH;
 			loc.setPitch(-90);
 			se.lengthGrow = (float) 0.015;
 			se.setLocation(loc);
 			se.start();
			
 			
 			SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
 			se2.infinite();
 			se2.particle = Particle.PORTAL;
 			se2.radius = 2;
 			se2.particles = 2;
 			loc.add(0, 0.5, 0);
 			se2.setLocation(loc);
 			se2.start();
 			effect1.put(p, se);
 			effect2.put(p, se2);
 			p.getWorld().playSound(p.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 2, (float)1);
 			
 			
 			
 			BukkitTask task = new BukkitRunnable() {
 	            @Override
 	            public void run() {
 	    			loc.getWorld().playSound(loc, Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 2, 0.2F);
 	            }
 	        }.runTaskTimer(VallendiaMinigame.getInstance(), 0, 40);
 	        
 	        tasks.put(p, task);
 	        
 	        
 	        
 	        
 			BukkitTask task2 = new BukkitRunnable() {
 	            @Override
 	            public void run() {
 	    			loc.getWorld().playSound(loc, Sound.BLOCK_PORTAL_TRAVEL, 2, 1.8F);
 		 	 		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 2, (float) 1.4);
 		 	 		p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().add(0, 1, 0), 20);
 		 	 		p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().add(0, 1, 0), 20);
 		 	 		p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().add(0, 1.8, 0), 20);
 	    			removeSpawn(p);
 	    			Bukkit.dispatchCommand(p, "spawn");
 	            }
 	        }.runTaskLater(VallendiaMinigame.getInstance(), warmup * 20);
 	        
 	        tasks2.put(p, task2);
			
		}

	}
	
	
	
	
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		if(spawning.contains(e.getPlayer()))
		{
			if(e.getTo().getBlockX() != e.getFrom().getBlockX() || e.getTo().getBlockZ() != e.getFrom().getBlockZ() 
					|| e.getTo().distance(e.getFrom()) > 5)
			{
				removeSpawn(e.getPlayer());
				Language.sendDefaultMessage(e.getPlayer(), "&3Teleportation canceled.");
			}
		}
	}
	
	
	
	@EventHandler
	public void onDamage(EntityDamageEvent e)
	{
		
		if(e.isCancelled() || e.getDamage() == 0)
		{
			return;
		}
		
		if(e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			if(spawning.contains(p))
			{
				removeSpawn(p);
				Language.sendDefaultMessage(p, "&3Teleportation canceled.");
			}
		}
	}
	
	
	
	
	public void removeSpawn(Player p)
	{
		if(spawning.contains(p))
		{
			this.effect1.get(p).cancel();
			this.effect2.get(p).cancel();
			this.tasks.get(p).cancel();
			this.tasks2.get(p).cancel();
			spawning.remove(p);
		}
	}
	
	
	

}