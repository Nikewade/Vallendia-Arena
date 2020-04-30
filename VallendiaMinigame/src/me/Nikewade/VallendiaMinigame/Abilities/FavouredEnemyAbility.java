package me.Nikewade.VallendiaMinigame.Abilities;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.CustomEvents.BuyAbilityEvent;
import me.Nikewade.VallendiaMinigame.Graphics.KitGui;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Kits.KitManager;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;

public class FavouredEnemyAbility implements Ability, Listener{
	int morepercent = 20;
	int lesspercent = 10;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Favoured Enemy";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Choose a class, you now recieve " + lesspercent + "% less damage from",
							"players of that class and deal " + morepercent + "% extra damage to",
							"them");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.NAME_TAG);
	}
	


	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
			if(!(AbilityManager.getAbilityData("Favoured Enemy", p).equalsIgnoreCase("empty")))
			{
			Language.sendAbilityUseMessage(p, "Your Favoured Enemy is " + AbilityManager.getAbilityData(this.getName(), p), "Favoured Enemy");
			return true;
			}
		
		openChooseEnemyMenu(p);
		  
		return false;
	}
		
	@EventHandler
	public void onDamage (EntityDamageByEntityEvent e)
	{
        if(e.getEntity().hasMetadata("NPC"))
        {
        	return;
        }
        Player p = null;
        if(e.getDamager() instanceof Projectile)
        {
        	Projectile proj = (Projectile) e.getDamager();
        	
        	if(proj.getShooter() instanceof Player)
        	{
        		
        	p = (Player) proj.getShooter();
        	
        	}
        }
        	if(e.getDamager() instanceof Player)
        	{
        	p = (Player) e.getDamager();
        	}
        	if(!(e.getDamager() instanceof Player))
        	{
        		return;
        	}
        
        
        if(!(e.getEntity() instanceof Player))
        {
        	return;
        }

		
		Player target = (Player) e.getEntity();

			if(AbilityManager.getAbilityData(this.getName(), p).equalsIgnoreCase(VallendiaMinigame.getInstance().kitmanager.getKit(target).getName(false)))
			{

	            double damage = e.getFinalDamage();
	            double highpercent = Utils.getPercentHigherOrLower(morepercent, true);
	            double newdamage = damage*highpercent;
				e.setDamage(0);
				e.setDamage(DamageModifier.ARMOR, newdamage);

			}
		
		
		if(!AbilityManager.getAbilityData(this.getName(), p).equalsIgnoreCase("empty"))
		{
			if(AbilityManager.getAbilityData(this.getName(), target).equalsIgnoreCase(VallendiaMinigame.getInstance().kitmanager.getKit(p).getName(false)))
			{
        		double lowerPercent =  Utils.getPercentHigherOrLower(lesspercent, false);
        		double damage = e.getFinalDamage()*lowerPercent;
    			e.setDamage(0);
				e.setDamage(DamageModifier.ARMOR, damage);	
			}
		}
		
	}
	
	   @EventHandler
	    public void onBuy (BuyAbilityEvent e)
	    {
	        new BukkitRunnable()
	        {

	        @Override
	        public void run() {
	            // TODO Auto-generated method stub
	            if(e.getAbility() == "Favoured Enemy")
	            {
	                openChooseEnemyMenu(e.getPlayer());
	            }
	        }
	        }.runTaskLater(VallendiaMinigame.getInstance(), 10);
	    }
	
	
	public void openChooseEnemyMenu(Player p)
	{

		AdvInventory kitInv = new AdvInventory(Utils.Colorate("&8&lChoose a class"), 27, Utils.placeholder((byte) 7, " "));
		  Kit warrior = VallendiaMinigame.getInstance().kitmanager.kit("warrior");
		  Kit archer = VallendiaMinigame.getInstance().kitmanager.kit("archer");
		  Kit assassin = VallendiaMinigame.getInstance().kitmanager.kit("assassin");
		  Kit mage = VallendiaMinigame.getInstance().kitmanager.kit("mage");

		  kitInv.setItem(new ItemStack(Material.IRON_SWORD), Utils.Colorate("&4&lWarrior"), 10, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openYesNoMenu(ep, warrior);
			    		
			    }
			}, Utils.Colorate("&8Choose Warrior as your favoured enemy!"));
		  
		  kitInv.setItem(new ItemStack(Material.BOW), Utils.Colorate("&2&lArcher"), 12, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openYesNoMenu(ep, archer);
			    }
			}, Utils.Colorate("&8Choose Archer as your favoured enemy!"));
		  
		  ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
		  LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
		  helmmeta.setColor(Color.BLACK);
		  kitInv.setItem(new ItemStack(helm), Utils.Colorate("&8&lAssassin"), 14, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openYesNoMenu(ep, assassin);
			    }
			}, Utils.Colorate("&8Choose Assassin as your favoured enemy!"));
		  
		  kitInv.setItem(new ItemStack(Material.EYE_OF_ENDER), Utils.Colorate("&3&lMage"), 16, new ClickRunnable() {
			    @Override
			    public void run(InventoryClickEvent e) {
			    	Player ep = (Player) e.getWhoClicked();
			    	openYesNoMenu(ep, mage);
			    }
			}, Utils.Colorate("&8Choose Mage as your favoured enemy!"));
		  kitInv.openInventory(p);
		
	}
	
	public void openYesNoMenu(Player p, Kit kit)
	{
		AdvInventory InvYesNo = new AdvInventory(Utils.Colorate("&8&lAre you sure?"), 27, Utils.placeholder((byte) 7, " "));
		String itemTitle = Utils.Colorate("&a&lFavoured Enemy: " + kit.getName(true));
		String description = Utils.Colorate("&cWARNING: &8You will not be able to change this!");
		InvYesNo.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 13), itemTitle, 11, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	AbilityManager.addAbilityData("Favoured Enemy", p, kit.getName(false));
		    	ep.closeInventory();
				Language.sendAbilityUseMessage(p, "You chose " + AbilityManager.getAbilityData("Favoured Enemy", ep) + Utils.Colorate("&8 as your Favoured Enemy!"), "Favoured Enemy");
				ep.playSound(ep.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1, 0.5F);
		    }
		}, description );
		
		
		InvYesNo.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14),  Utils.Colorate("&4&lCancel"), 15, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	openChooseEnemyMenu(ep);
		    }
		});
		
		InvYesNo.openInventory(p);
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}