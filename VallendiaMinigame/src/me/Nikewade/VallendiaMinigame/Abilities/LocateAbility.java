package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;

public class LocateAbility implements Ability, Listener{
	int duration = 15;
	Player tar = null;
	ArrayList<Player> onlinePlayers = new ArrayList<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Locate";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.UTILITY;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("Choose a player from the server list. For ",
							+ duration + " seconds, they will glow. " + "This ability will",
							"not reveal invisible players.");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.BEACON);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		for(Player player : Bukkit.getOnlinePlayers())
		{//if p is == player continue
			if(p == player)
			{
				continue;
			}
			if(onlinePlayers.contains(player))
			{
				continue;
			}

			onlinePlayers.add(player);
			
		}
		

		
		Runnable run = new Runnable()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						AbilityCooldown c = new AbilityCooldown(p.getUniqueId(), "Locate", 
								VallendiaMinigame.getInstance().abilitymanager.getCooldown("Locate", p), 
								AbilityManager.locateAbilityItem(p, "Locate"));
						
				    	AbilityUtils.addPotionDuration(p, tar, "Locate", PotionEffectType.GLOWING, 0, duration*20);
				    	c.start();
				    	p.closeInventory();
						p.playSound(p.getLocation(), Sound.ENTITY_EVOCATION_ILLAGER_CAST_SPELL, 1, 0.8F);
						}

				};

		openPlayerListMenu(p, onlinePlayers, run);
		
		return false;
	}
	
	public void openPlayerListMenu(Player p, ArrayList<Player> players, Runnable run)
	{
		int perPage = 44;
		AdvInventory onlinePlayers = new AdvInventory(Utils.Colorate("&8&lLocate a Player"), 54, new ItemStack(Material.AIR));
	    ItemStack item = new ItemStack(351, 1, (short) 8);
	    ItemMeta itemmeta = item.getItemMeta();
	    item.setItemMeta(itemmeta);	
		
	    ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
	    ItemMeta placeholdermeta = placeholder.getItemMeta();
	    placeholdermeta.setDisplayName("");
	    placeholder.setItemMeta(placeholdermeta);
				
		for(int x = 45; x < 54 ; x++)
		{
			  onlinePlayers.setItem(placeholder, "", x, new ClickRunnable() {
				    @Override
				    public void run(InventoryClickEvent e) {
						p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
				    }
				});

		}
		
		
		int amount = 0;
		for(Player player : players)
		{
			if(amount >= perPage)
			{
				break;
			}

			ItemStack head = new ItemStack(Material.SKULL_ITEM , 1 , (short) 3);
			
	        SkullMeta skull = (SkullMeta) head.getItemMeta();
	        skull.setDisplayName(player.getName());
	        skull.setOwner(player.getName());
	        head.setItemMeta(skull);
	        
	        String playerName = new String();
	        if(VallendiaMinigame.getInstance().levelmanager.getLevel(player) < 5)
	        {
	        	playerName = Utils.Colorate("&7&l" + player.getName());
	        }
	        if(VallendiaMinigame.getInstance().levelmanager.getLevel(player) > 4 &&
	        		VallendiaMinigame.getInstance().levelmanager.getLevel(player) < 10)
	        {
	        	playerName = Utils.Colorate("&3&l" + player.getName());
	        }
	        
	        if(VallendiaMinigame.getInstance().levelmanager.getLevel(player) > 8 &&
	        		VallendiaMinigame.getInstance().levelmanager.getLevel(player) < 13)
	        {
	        	playerName = Utils.Colorate("&9&l" + player.getName());
	        }
	        if(VallendiaMinigame.getInstance().levelmanager.getLevel(player) > 12 &&
	        		VallendiaMinigame.getInstance().levelmanager.getLevel(player) < 17)
	        {
	        	playerName = Utils.Colorate("&6&l" + player.getName());
	        }
	        if(VallendiaMinigame.getInstance().levelmanager.getLevel(player) > 16)
	        {
	        	playerName = Utils.Colorate("&4&l" + player.getName());
	        }

			
			  onlinePlayers.setItem(head, playerName, amount, new ClickRunnable() {
				    @Override
				    public void run(InventoryClickEvent e) {
				    	openYesNoMenu(p, player, players, run);
				    }
				});
				 
			 amount++;

		}
		
		
		
		if(players.size() > perPage)
		{
			  onlinePlayers.setItem(item, Utils.Colorate("&8&lNext"), 52, new ClickRunnable() {
				    @Override
				    public void run(InventoryClickEvent e) {
				    	
				    	nextPage(p, players, run, 1);
				    	
				    }
				});
		}

			onlinePlayers.openInventory(p);	

	}
	
	
	
	public void nextPage(Player p, ArrayList<Player> players, Runnable run, int interger)
	{
		int perPage = 44;	    
		AdvInventory nextPage = new AdvInventory(Utils.Colorate("&8&lLocate a Player"), 54, new ItemStack(Material.AIR));
	    ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);

	    ItemMeta placeholdermeta = placeholder.getItemMeta();
	    placeholdermeta.setDisplayName("");
	    placeholder.setItemMeta(placeholdermeta);
	    
	    ItemStack item = new ItemStack(351, 1, (short) 8);
	    ItemMeta itemmeta = item.getItemMeta();
	    item.setItemMeta(itemmeta);	
				
		for(int x = 45; x < 54 ; x++)
		{
			  nextPage.setItem(placeholder, "", x, new ClickRunnable() {
				    @Override
				    public void run(InventoryClickEvent e) {
						p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
				    }
				});
		}
		
		if(players.size() > perPage*interger)
		{
			  nextPage.setItem(item, Utils.Colorate("&8&lPrevious"), 46, new ClickRunnable() {
				    @Override
				    public void run(InventoryClickEvent e) {
				    	if(interger - 1 == 0)
				    	{
					    	openPlayerListMenu(p, players, run);
				    	}else
				    	{
				    		nextPage(p, players, run, interger - 1);
				    	}

				    }
				});
		}
		
		int amount = 0;
		for(Player player : players)
		{
			if(amount < perPage*interger)
			{
				amount++;
				continue;
			}
			if(amount > perPage*interger)
			{
				break;
			}
			
			ItemStack head = new ItemStack(Material.SKULL_ITEM , 1 , (short) 3);
			
	        SkullMeta skull = (SkullMeta) head.getItemMeta();
	        skull.setDisplayName(player.getName());
	        skull.setOwner(player.getName());
	        head.setItemMeta(skull);
	        
	        String playerName = new String();
	        if(VallendiaMinigame.getInstance().levelmanager.getLevel(player) < 5)
	        {
	        	playerName = Utils.Colorate("&7&l" + player.getName());
	        }
	        if(VallendiaMinigame.getInstance().levelmanager.getLevel(player) > 4 &&
	        		VallendiaMinigame.getInstance().levelmanager.getLevel(player) < 10)
	        {
	        	playerName = Utils.Colorate("&3&l" + player.getName());
	        }
	        
	        if(VallendiaMinigame.getInstance().levelmanager.getLevel(player) > 8 &&
	        		VallendiaMinigame.getInstance().levelmanager.getLevel(player) < 13)
	        {
	        	playerName = Utils.Colorate("&9&l" + player.getName());
	        }
	        if(VallendiaMinigame.getInstance().levelmanager.getLevel(player) > 12 &&
	        		VallendiaMinigame.getInstance().levelmanager.getLevel(player) < 17)
	        {
	        	playerName = Utils.Colorate("&6&l" + player.getName());
	        }
	        if(VallendiaMinigame.getInstance().levelmanager.getLevel(player) > 16)
	        {
	        	playerName = Utils.Colorate("&4&l" + player.getName());
	        }

			
			  nextPage.setItem(head, playerName, amount - perPage*interger, new ClickRunnable() {
				    @Override
				    public void run(InventoryClickEvent e) {
				    	openYesNoMenu(p, player,players, run);
				    }
				});
				 
			 amount++;
			
		}
		
		if(players.size() -1 > perPage*interger)
		{
			  nextPage.setItem(item, Utils.Colorate("&8&lNext"), 50, new ClickRunnable() {
				    @Override
				    public void run(InventoryClickEvent e) {
				    	
				    	nextPage(p, players,run, 1+1);
				    	
				    }
				});
		}
		
		nextPage.openInventory(p);

	}
	
	
	public void openYesNoMenu(Player p, Player target, ArrayList<Player> players, Runnable run)
	{
		tar = target;
        String targetName = new String();
        if(VallendiaMinigame.getInstance().levelmanager.getLevel(target) < 5)
        {
        	targetName = Utils.Colorate("&7&l" + target.getName());
        }
        if(VallendiaMinigame.getInstance().levelmanager.getLevel(target) > 4 &&
        		VallendiaMinigame.getInstance().levelmanager.getLevel(target) < 10)
        {
        	targetName = Utils.Colorate("&3&l" + target.getName());
        }
        
        if(VallendiaMinigame.getInstance().levelmanager.getLevel(target) > 8 &&
        		VallendiaMinigame.getInstance().levelmanager.getLevel(target) < 13)
        {
        	targetName = Utils.Colorate("&9&l" + target.getName());
        }
        if(VallendiaMinigame.getInstance().levelmanager.getLevel(target) > 12 &&
        		VallendiaMinigame.getInstance().levelmanager.getLevel(target) < 17)
        {
        	targetName = Utils.Colorate("&6&l" + target.getName());
        }
        if(VallendiaMinigame.getInstance().levelmanager.getLevel(target) > 16)
        {
        	targetName = Utils.Colorate("&4&l" + target.getName());
        }
        
		AdvInventory InvYesNo = new AdvInventory(Utils.Colorate("&8&lAre you sure?"), 27, Utils.placeholder((byte) 7, " "));
		String itemTitle = Utils.Colorate("&a&lConfirm");
		InvYesNo.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 13), itemTitle, 11, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	p.closeInventory();
		    	players.clear();
	    		run.run();

		    }
		});
		
		
		InvYesNo.setItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14),  Utils.Colorate("&4&lCancel"), 15, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	ep.closeInventory();
		    	players.clear();
		    }
		});
		
		InvYesNo.openInventory(p);
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		onlinePlayers.clear();
	}
	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		onlinePlayers.clear();
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}

}