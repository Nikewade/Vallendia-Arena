     package me.Nikewade.VallendiaMinigame.Events;
     import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
     
     public class ProjectileEvents
       implements Listener {
       VallendiaMinigame Main;
       
       public ProjectileEvents(VallendiaMinigame Main) {
       this.Main = Main;
       Main.getServer().getPluginManager().registerEvents(this, (Plugin)Main);
       }
     
     
       
       @EventHandler
       public void onHit(ProjectileHitEvent e) {
			if(!(e.getEntity().getShooter() instanceof Player))
			{
				return;
			}
			Player p = (Player) e.getEntity().getShooter();
			Block block = e.getHitBlock();
			 if(block == null)
			 {
				 return;
			 }
			 if (block.getTypeId() == 95 || block.getTypeId() == 20 || block.getTypeId() == 160 || block.getTypeId() == 102) {
     
				 if(VallendiaMinigame.getInstance().worldguard.canBuild(p
						 , block))
				 {
					 Utils.regenBlock(block, 60);
					 block.setType(Material.AIR);
					 block.getDrops().remove(Boolean.valueOf(true));
					 block.getWorld().playSound(block.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0F, 1.0F); e.getEntity().remove();
				 }else {p.sendMessage(Utils.Colorate("&c&lHey! &7Sorry, but you can't break that block here."));}
			 } 
       }
       
       
       
       //Stops arc particles (Snowballs) from getting caught on some grass blocks etc up close
       @EventHandler
       public void onShoot(ProjectileLaunchEvent e)
       {
    	   if(e.getEntity() instanceof Snowball && e.getEntity().getShooter() instanceof Player)
    	   {
    		   Player p = (Player) e.getEntity().getShooter();
    		   for(Block b : p.getLineOfSight(AbilityUtils.transparentBlocks, 10))
    		   {
    			   if(AbilityUtils.transparentBlocks.contains(b.getType()))
    			   {
        			   Utils.regenBlock(b, 10);
        			   b.setType(Material.AIR);   
    			   }
    		   }
    	   }
       }
       
     }
