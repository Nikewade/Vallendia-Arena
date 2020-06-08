package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.event.CancelReason;
import net.citizensnpcs.api.ai.event.NavigationCancelEvent;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Equipment.EquipmentSlot;

public class ShadowCloneAbility implements Ability, Listener{
	ArrayList<Player> abilityActive = new ArrayList<>();
	int time = 5;
	HashMap<Player, NPC> npcList = new HashMap<>();
	List<Block> blocks = new ArrayList<>(); 
	Map<Player, BukkitTask> timers = new HashMap<>();
	Map<NPC, Location> location = new HashMap<>();
	ArrayList<Player> delayedspawn = new ArrayList<>();
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Shadow Clone";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("You step into the shadows turning invisible",
				 			  "for " + time + " seconds whilst your clone takes",
				 			  "your place.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(252,1,(short) 7);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		
		if(abilityActive.contains(p))
		{
			return false;
		}
		abilityActive.add(p);
		Location loc = findInFront(p,50);
		Float run = p.getWalkSpeed()*9;
		NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, p.getName());
		npcList.put(p, npc);
		location.put(npc, loc);
		npc.setProtected(false);
		npc.getTrait(Equipment.class).set(EquipmentSlot.BOOTS, p.getInventory().getBoots());
		npc.getTrait(Equipment.class).set(EquipmentSlot.LEGGINGS, p.getInventory().getLeggings());
		npc.getTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE, p.getInventory().getChestplate());
		npc.getTrait(Equipment.class).set(EquipmentSlot.HELMET, p.getInventory().getHelmet());
		npc.getNavigator().getDefaultParameters().baseSpeed(run);
		npc.getNavigator().getDefaultParameters().stuckAction(null);
		npc.getNavigator().getDefaultParameters().avoidWater(false);
		npc.spawn(p.getLocation());
		AbilityUtils.makeInvisible(p, "Shadow Clone");
		
		
		if(!delayedspawn.contains(p))
		{
			delayedspawn.add(p);
			new BukkitRunnable()
			{

				@Override
				public void run() {
					// TODO Auto-generated method stub
					npc.getNavigator().setTarget(loc);
				}
				
			}.runTaskLater(VallendiaMinigame.getInstance(), 20);
		}else
		{
			npc.getNavigator().setTarget(loc);
		}
		

		

		

		
			new BukkitRunnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if(AbilityUtils.isInvisible(p))
				{
					Language.sendAbilityUseMessage(p, "You reappear", "Shadow Clone");
					AbilityUtils.removeInvisibleNoDisable(p);
					if(abilityActive.contains(p))
					{
						abilityActive.remove(p);
					}
				}
				
			}
			
		}.runTaskLater(VallendiaMinigame.getInstance(), time*20);
		
		new BukkitRunnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub		
				if(npcList.containsValue(npc))
				{
					p.getWorld().playSound(npc.getEntity().getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 1);
		 	 		p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, npc.getEntity().getLocation().add(0, 1, 0), 20);
		 	 		p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, npc.getEntity().getLocation().add(0, 1, 0), 20);
		 	 		p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, npc.getEntity().getLocation().add(0, 1.8, 0), 20);
					npc.destroy();
					location.remove(npc);
					npcList.remove(p);
					if(abilityActive.contains(p))
					{
					abilityActive.remove(p);
					}
					
				}			
				
			}
			
		}.runTaskLater(VallendiaMinigame.getInstance(), 10*20);
		return true;
	}
	
	public Location findInFront(Player p, int block)
	{
		Location loc = p.getLocation().add(p.getLocation().getDirection().setY(0).normalize().multiply(block));
		
		if(!(loc.getBlock().getType() == Material.AIR))
		{
		      for (Block b : p.getLineOfSight(null, block))
		      {		  
		    	  blocks.add(b);		    	 
		      }
		      
		      for(Block b : blocks)
		      {
		    	  if(b.getType() != Material.AIR && b.getType().isSolid())
		    	  {
		    		 int num = blocks.indexOf(b)-1;
		    		 loc = blocks.get(num).getLocation();
		    	  }
		      }
		}
		
		if(loc.getBlock().getType() == Material.AIR)
		{

		while(loc.getBlock().getType() == Material.AIR)
		{
			loc.subtract(0,1,0);
		}
		while(!(loc.getBlock().getType() == Material.AIR))
		{
			loc.add(0,1,0);
		}
		}

		return loc;
	}
	
	@EventHandler
	public void onNPCDeath (NPCDeathEvent e)
	{
		if(npcList.containsValue(e.getNPC()))
		{
			Player p = Bukkit.getPlayer(e.getNPC().getName());
			NPC npc = e.getNPC();
 	 		p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, npc.getEntity().getLocation().add(0, 1, 0), 20);
 	 		p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, npc.getEntity().getLocation().add(0, 1, 0), 20);
 	 		p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, npc.getEntity().getLocation().add(0, 1.8, 0), 20);
			p.getWorld().playSound(npc.getEntity().getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 1);
			npc.destroy();
			location.remove(npc);
			Language.sendAbilityUseMessage(p, "Your clone was killed!", "Shadow Clone");
			npcList.remove(p);
			abilityActive.remove(p);
		}
	}

	
	@EventHandler
	public void onPathCancel (NavigationCancelEvent e)
	{
		if(e.getCancelReason() == CancelReason.STUCK)
		{
			Player p = Bukkit.getPlayer(e.getNPC().getName());
			Location loc = location.get(e.getNPC());
			int number = Utils.randomNumber(1, 100);
			if(number >= 1 && number <= 25)
			{
				loc.subtract(2,0,0);
			}
			if(number >= 25 && number <= 50)
			{
				loc.subtract(0,0,2);
			}
			if(number >= 50 && number <= 75)
			{
				loc.add(2,0,0);
			}
			if(number >= 75 && number <= 100)
			{
				loc.add(0,0,2);
			}

			if(!(loc.getBlock().getType() == Material.AIR))
			{
			      for (Block b : p.getLineOfSight(null, 50))
			      {		  
			    	  blocks.add(b);		    	 
			      }
			      
			      for(Block b : blocks)
			      {
			    	  if(b.getType() != Material.AIR && b.getType().isSolid())
			    	  {
			    		 int num = blocks.indexOf(b)-1;
			    		 loc = blocks.get(num).getLocation();
			    	  }
			      }
			}
			
			if(loc.getBlock().getType() == Material.AIR)
			{

			while(loc.getBlock().getType() == Material.AIR)
			{
				loc.subtract(0,1,0);
			}
			while(!(loc.getBlock().getType() == Material.AIR))
			{
				loc.add(0,1,0);
			}
			}
			e.getNavigator().setTarget(loc);
		}
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		if(p.hasMetadata("NPC"))
		{
			return;
		}
		if(npcList.containsValue(p))
		{
			return;
		}

		if(delayedspawn.contains(p))
		{
			delayedspawn.remove(p);
		}
		
		if(npcList.containsKey(p))
		{
			location.remove(npcList.get(p));
			npcList.get(p).destroy();
			npcList.remove(p);
			if(abilityActive.contains(p))
			{
				abilityActive.remove(p);
				
				if(AbilityUtils.isInvisible(p))
				{
					AbilityUtils.removeInvisibleNoDisable(p);
				}

			}
		}
		
		if(abilityActive.contains(p))
		{
			if(AbilityUtils.isInvisible(p))
			{
				AbilityUtils.removeInvisibleNoDisable(p);
			}
			abilityActive.remove(p);
		}
				


	}
	
	
	@EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamage(EntityDamageByEntityEvent e)
    {
        if(e.isCancelled())
        {
            return;
        }
        if(e.getDamager() instanceof Player && abilityActive.contains(e.getDamager()))
        {
            if(AbilityUtils.isInvisible((Player) e.getDamager()))
            {
            Language.sendAbilityUseMessage((LivingEntity) e.getDamager(), "You reappear", "Shadow Clone");
            AbilityUtils.removeInvisibleNoDisable((Player) e.getDamager());
            abilityActive.remove(e.getDamager());
            }
        }
        
        if(e.getDamager() instanceof Projectile)
        {
            Projectile proj = (Projectile) e.getDamager();
            if(abilityActive.contains(proj.getShooter()))
            {
                if(AbilityUtils.isInvisible((Player) e.getDamager()))
                {
                Language.sendAbilityUseMessage((LivingEntity) e.getDamager(), "You reappear", "Shadow Clone");
                AbilityUtils.removeInvisibleNoDisable((Player) e.getDamager());
                abilityActive.remove(e.getDamager());
                }
            }
        }
    }
    
    
	@EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageEvent e)
    {
        if(e.isCancelled())
        {
            return;
        }
        if(!(e.getEntity() instanceof Player))
        {
            return;    
        }
        
        if(abilityActive.contains(e.getEntity()))
        {
            if(AbilityUtils.isInvisible((Player) e.getEntity()))
            {
            Language.sendAbilityUseMessage((LivingEntity) e.getEntity(), "You reappear", "Shadow Clone");
            AbilityUtils.removeInvisibleNoDisable((Player) e.getEntity());
            abilityActive.remove(e.getEntity());
            }
        }
                
    }

}