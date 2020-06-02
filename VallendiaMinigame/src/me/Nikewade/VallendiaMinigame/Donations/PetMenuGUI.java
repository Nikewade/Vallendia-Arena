package me.Nikewade.VallendiaMinigame.Donations;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.kirelcodes.miniaturepets.api.events.pets.PetFinishedSpawnEvent;
import com.kirelcodes.miniaturepets.api.events.pets.PetRemovedEvent;
import com.kirelcodes.miniaturepets.pets.Pet;

import me.Nikewade.VallendiaMinigame.Commands.DonateCommand;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory;
import me.Nikewade.VallendiaMinigame.Utils.AdvInventory.ClickRunnable;
import me.Nikewade.VallendiaMinigame.Utils.Language;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class PetMenuGUI implements Listener {
	public static ArrayList<Pet> pets = new ArrayList<>();
	
	public static void openPetMenu(Player p) {
		
	AdvInventory petMenu = new AdvInventory(Utils.Colorate(""), 54, Utils.placeholder((byte) 15, " "));
	
	for(int i = 10; i < 45 ; i++)
	{
		petMenu.setItem(new ItemStack(Material.AIR), i);
	}
	
	 petMenu.setItem(new ItemStack(Material.ARROW), Utils.Colorate("&8<< Back"), 45, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();			    	
		    	DonateCommand.openCosmeticMainMenu(ep);
		    }
		});
	 
	 petMenu.setItem(new ItemStack(159,1,(short) 14), Utils.Colorate("&4Remove your Pet"), 49, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();		
		    	Bukkit.dispatchCommand(ep, "mpet remove");		 
		    }
		});
	 
	 String ape = Utils.Colorate("&8&lApe");
	 if(p.hasPermission("mpet.Ape"))
	 {
		 ape = Utils.Colorate("&6&lApe");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc4NzA0ODk3OCwKICAicHJvZmlsZUlkIiA6ICIzZmM3ZmRmOTM5NjM0YzQxOTExOTliYTNmN2NjM2ZlZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJZZWxlaGEiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ5ODFjNzBlZDEwYmQ5ZGIzYzIyZjhjYzlkMzI2NjMxYzg5MzJlNTljMjNkMGQyNTY3YjlkNjNiZGE5NDJlZiIKICAgIH0KICB9Cn0=")),
	 		 ape, 9, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Ape"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Ape");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String astro = Utils.Colorate("&8&lAstronaut");
	 if(p.hasPermission("mpet.Astronaught"))
	 {
		 astro = Utils.Colorate("&6&lAstronaut");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWI5MmIwODYyMjY0ZmYyY2JhMWRiZDg4MTkyY2M4MTU0M2ZhYjU0OTY5ZTExN2Q0ZmU0MzUzYjk1MyJ9fX0=")),
	 		 astro, 10, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Astronaught"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Astronaught");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String dragon = Utils.Colorate("&8&lBaby Dragon");
	 if(p.hasPermission("mpet.BabyDragon"))
	 {
		 dragon = Utils.Colorate("&5&lBaby Dragon");
	 }
	 
	 petMenu.setItem(new ItemStack(397,1,(short) 5),
	 		 dragon, 11, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.BabyDragon"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet BabyDragon");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String bghast = Utils.Colorate("&8&lBaby Ghast");
	 if(p.hasPermission("mpet.BabyGhast"))
	 {
		 bghast = Utils.Colorate("&6&lBaby Ghast");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("eyJ0aW1lc3RhbXAiOjE1NzA3Mzg4NjMzODIsInByb2ZpbGVJZCI6ImZkNjBmMzZmNTg2MTRmMTJiM2NkNDdjMmQ4NTUyOTlhIiwicHJvZmlsZU5hbWUiOiJSZWFkIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9iMWM0NDUxZWIzYmNiMDVlZTU4MDNlNDRhNDAyNWU1ZDk4YjYxYjYxZDU1OTc4ZmY5M2M3ZGI0NmFhODc5YjQwIn19fQ==")),
	 		 bghast, 12, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.BabyGhast"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet BabyGhast");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String brottweiler = Utils.Colorate("&8&lBaby Rottweiler");
	 if(p.hasPermission("mpet.BabyRottweiler"))
	 {
		 brottweiler = Utils.Colorate("&6&lBabyRottweiler");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc4OTQzMjk1NywKICAicHJvZmlsZUlkIiA6ICIzM2ViZDMyYmIzMzk0YWQ5YWM2NzBjOTZjNTQ5YmE3ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJEYW5ub0JhbmFubm9YRCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83MzdjYTQzNWIxYzRjOWQ2MmZhMjNmODgxMWY4MWQyOTQwZmQ3YzhiZTZhNmU0ZmQwYTIxMTNlM2U4Y2M4NTk1IgogICAgfQogIH0KfQ==")),
	 		 brottweiler, 13, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.BabyRottweiler"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet BabyRottweiler");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String black = Utils.Colorate("&8&lBlack Cat");
	 if(p.hasPermission("mpet.Black"))
	 {
		 black = Utils.Colorate("&6&lBlack Cat");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc4OTUyMjMyMywKICAicHJvZmlsZUlkIiA6ICJiNzQ3OWJhZTI5YzQ0YjIzYmE1NjI4MzM3OGYwZTNjNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJTeWxlZXgiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODU0MTE4MjgxNTlmZmIxYWQ4ZDk3YjI4M2VhNDM4ODk3MmViZmRkYmI2ZTc5ZGU2MDliYjI5NjA3YjAyNGE3NCIKICAgIH0KICB9Cn0=")),
	 		 black, 14, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Black"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Black");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String blackp = Utils.Colorate("&8&lBlack Pug");
	 if(p.hasPermission("mpet.BlackPug"))
	 {
		 blackp = Utils.Colorate("&6&lBlack Pug");
	 }
	 petMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc4OTc0MzY2MSwKICAicHJvZmlsZUlkIiA6ICIyM2YxYTU5ZjQ2OWI0M2RkYmRiNTM3YmZlYzEwNDcxZiIsCiAgInByb2ZpbGVOYW1lIiA6ICIyODA3IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2JiMTY2ODJhZGNhZTYxMmQ0YjhkMWU1NTBiMDBmZGM2NTRjM2Y4ZGUyMjFmMWFkN2E3MDlkNDNkNjNiMmRmMzciCiAgICB9CiAgfQp9")),
	 		 blackp, 15, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.BlackPug"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet BlackPug");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String calico = Utils.Colorate("&8&lCalico Cat");
	 if(p.hasPermission("mpet.Calico"))
	 {
		 calico = Utils.Colorate("&6&lCalico Cat");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc4OTk5MzU3MSwKICAicHJvZmlsZUlkIiA6ICIzZmM3ZmRmOTM5NjM0YzQxOTExOTliYTNmN2NjM2ZlZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJZZWxlaGEiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTBkYzNlMWE0NWVmNzc4MGE2MjViOGZjNjA4MGMzNjRkYjg4MWNkYWIwMDUyYzA4ZjRjZGU1OTM0MmRmODIzNiIKICAgIH0KICB9Cn0=")),
	 		 calico, 16, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Calico"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Calico");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String chart = Utils.Colorate("&8&lChartreux Cat");
	 if(p.hasPermission("mpet.Black"))
	 {
		 chart = Utils.Colorate("&6&lChartreux Cat");
	 }
	 
	 petMenu.setItem(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc5MTE2NTIwOSwKICAicHJvZmlsZUlkIiA6ICJkNjBmMzQ3MzZhMTI0N2EyOWI4MmNjNzE1YjAwNDhkYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJCSl9EYW5pZWwiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDMwNGE1NDEwNzg3YTBiMWZhMjNjY2UwNzdmNzU0NWVjZmJjZmE4ZTY4YzMyY2E0ZjgyMjFjZThkZTRlOTM0ZiIKICAgIH0KICB9Cn0="),
	 		chart, 17, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Chartreux"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Chartreux");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String chimp = Utils.Colorate("&8&lChimp");
	 if(p.hasPermission("mpet.Chimp"))
	 {
		 chimp = Utils.Colorate("&6&lChimp");
	 }
	 petMenu.setItem(new ItemStack(Utils.getItem("eyJ0aW1lc3RhbXAiOjE1NzkwNzQwNTgwMDMsInByb2ZpbGVJZCI6IjU3MGIwNWJhMjZmMzRhOGViZmRiODBlY2JjZDdlNjIwIiwicHJvZmlsZU5hbWUiOiJMb3JkU29ubnkiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzQyYjE0NDFmYTQxNGYzZjkxZDc0NmJkODE1ZDAyYTYwMzk5ZGU2MTlhNDMyM2Y2YTBlZDkwNzc2MzhkYjQ0NCJ9fX0=")),
	 		 chimp, 18, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Chimp"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Chimp");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 String mimic = Utils.Colorate("&8&lMimic");
	 if(p.hasPermission("mpet.Mimic"))
	 {
		 mimic = Utils.Colorate("&6&lMimic");
	 }
	 petMenu.setItem(new ItemStack(Utils.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGU2YzY2NWI4MjczODlhYWU0MjNmMGJjNzAzYjZjY2E4M2I1MTgxZDMzZjYwZjcyNzI4MzY3NGY4MDE0YTQ1NiJ9fX0=")),
	 		 mimic, 19, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Chomp"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Chomp");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String cream = Utils.Colorate("&8&lCream Cat");
	 if(p.hasPermission("mpet.Cream"))
	 {
		 cream = Utils.Colorate("&6&lCream Cat");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc5MTM3Mjg5NSwKICAicHJvZmlsZUlkIiA6ICIzM2ViZDMyYmIzMzk0YWQ5YWM2NzBjOTZjNTQ5YmE3ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJEYW5ub0JhbmFubm9YRCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mN2Q0MjU2YzI2NjAyMzUxYjY2YzdhM2VkNmNiZmI0YjI1ZGI0NDAzNTMyN2I1YmM0MDMwM2Q5M2M5NjM4ZjM3IgogICAgfQogIH0KfQ==")),
	 		 cream, 20, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Cream"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Cream");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String crocodile = Utils.Colorate("&8&lCrocodile");
	 if(p.hasPermission("mpet.Crocodile"))
	 {
		 crocodile = Utils.Colorate("&6&lCrocodile");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJlN2ZhMmY5YjhkNmQxZTczNGVkYTVlM2NlMDI2Njg4MTM0MjkyZmNhZmMzMjViMWVhZDQzZDg5Y2MxZTEifX19")),
	 		 crocodile, 21, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Crocodile"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Crocodile");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String gorilla = Utils.Colorate("&8&lGorilla");
	 if(p.hasPermission("mpet.Gorilla"))
	 {
		 gorilla = Utils.Colorate("&6&lGorilla");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("eyJ0aW1lc3RhbXAiOjE1NzAwMDQxOTM2NzMsInByb2ZpbGVJZCI6IjVmODMyZGMzNWM4MTQ4YjI5ZmQ1MTJjOTBjYmI0OTQ5IiwicHJvZmlsZU5hbWUiOiJ0b29nZXIiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzU5NDMyNDg0Zjc3YzUyMGFhMTEzYzQzOTA5MmMyMzg2ZDUwYzBhZTRhMTMwZTc0NGM0YTE1NmUyZGQxNmFiZDgifX19")),
	 		 gorilla, 22, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Gorilla"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Gorilla");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 String jake = Utils.Colorate("&8&lJake The Dog");
	 if(p.hasPermission("mpet.Jake"))
	 {
		 jake = Utils.Colorate("&6&lJake The Dog");
	 }
	 petMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc5MTU1MTQ1NiwKICAicHJvZmlsZUlkIiA6ICI3M2ZkNzU2NWJkZTY0MGQzYWE4MGUxMWUwMTMwMjc3OCIsCiAgInByb2ZpbGVOYW1lIiA6ICJHYUJySWVMVnR6IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzc1M2JkNzhlMWNmZDg2NjY1YmZkNmE4ZjVlNWM5MjI3ZTIzNjE5NjRkMDA1ZTUzYzAyN2U1Nzc5ZDE3ZjA1ODEiCiAgICB9CiAgfQp9")),
	 		 jake, 23, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Jake"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Jake");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String koala = Utils.Colorate("&8&lKoala");
	 if(p.hasPermission("mpet.Koala"))
	 {
		 koala = Utils.Colorate("&6&lKoala");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc5MTYxOTgwNywKICAicHJvZmlsZUlkIiA6ICI5MWZlMTk2ODdjOTA0NjU2YWExZmMwNTk4NmRkM2ZlNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJoaGphYnJpcyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8zZGM1YjAyOTVlYmU1N2VkZWM3MTkyYjI4NDcwODBhODJlZTk0MGNkM2FiYmM0YmU4YmJhYzMxYTU4ZmE5NDA3IgogICAgfQogIH0KfQ==")),
	 		 koala, 24, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Koala"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Koala");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
		ItemStack head = new ItemStack(Material.SKULL_ITEM , 1 , (short) 3);
		
        SkullMeta skull = (SkullMeta) head.getItemMeta();
        skull.setDisplayName(p.getName());
        skull.setOwner(p.getName());
        head.setItemMeta(skull);
        
   	 String minime = Utils.Colorate("&8&lMini Me");
   	 if(p.hasPermission("mpet.MiniMe"))
   	 {
   		 minime = Utils.Colorate("&6&lMini Me");
   	 }
        
	 petMenu.setItem(head, minime, 25, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.MiniMe"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet MiniMe");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String monkey = Utils.Colorate("&8&lMonkey");
	 if(p.hasPermission("mpet.Monkey"))
	 {
		 monkey = Utils.Colorate("&6&lMonkey");
	 }
	 petMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc5MTk4NTE5MSwKICAicHJvZmlsZUlkIiA6ICJiMGQ0YjI4YmMxZDc0ODg5YWYwZTg2NjFjZWU5NmFhYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJNaW5lU2tpbl9vcmciLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWRjZWExYzM0MTc0NzE4YWYyYzBlZTAwM2ViZWFmMDg4NDA3MjFjY2RhMDFlM2U2ZDQwZDMwM2ZmODZkOTNlMSIKICAgIH0KICB9Cn0=")),
	 		 monkey, 26, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Monkey"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Monkey");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String mp8 = Utils.Colorate("&8&lMP8");
	 if(p.hasPermission("mpet.MP8"))
	 {
		 mp8 = Utils.Colorate("&6&lMP8");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("eyJ0aW1lc3RhbXAiOjE1MTM4MTYxNDYwMjYsInByb2ZpbGVJZCI6ImIwZDczMmZlMDBmNzQwN2U5ZTdmNzQ2MzAxY2Q5OGNhIiwicHJvZmlsZU5hbWUiOiJPUHBscyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2ExNzE2NTMxMjQ0MTA2Njc1MTY3Y2E1MzI3ZjIzZjhjMTkyYWY3YWNjZmY0NDlkNmIzYmViMDY1ODEzZjc0In19fQ==")),
	 		 mp8, 27, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.MP8"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet MP8");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String orange = Utils.Colorate("&8&lOrange Tabby Cat");
	 if(p.hasPermission("mpet.OrangeTabby"))
	 {
		 orange = Utils.Colorate("&6&lOrange Tabby Cat");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc5MjEzMzk5MCwKICAicHJvZmlsZUlkIiA6ICI5MThhMDI5NTU5ZGQ0Y2U2YjE2ZjdhNWQ1M2VmYjQxMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJCZWV2ZWxvcGVyIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2EyZjA1YzE5YjJmYzBmYjJjOGViYTQ2ZmU2Yzc5ZTIxNjVkZGM4MDBlMWFmZmM3ZmYxZjEzZTJlZmRhMWJhMzAiCiAgICB9CiAgfQp9")),
	 		 orange, 28, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.OrangeTabby"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet OrangeTabby");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String penguin = Utils.Colorate("&8&lPenguin");
	 if(p.hasPermission("mpet.Penguin"))
	 {
		 penguin = Utils.Colorate("&6&lPenguin");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc5MjIwODA1NCwKICAicHJvZmlsZUlkIiA6ICI3ZGEyYWIzYTkzY2E0OGVlODMwNDhhZmMzYjgwZTY4ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJHb2xkYXBmZWwiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQ0MDhjOTZkY2I4NDVjNDFmZDRhNmJlNjlmZjk5NTljYjQwZTBlMjI0OGIxNGM4ZTE1ZDgwMTIzYTgxNjhlZSIKICAgIH0KICB9Cn0=")),
	 		 penguin, 29, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Penguin"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Penguin");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String river = Utils.Colorate("&8&lRiver Otter");
	 if(p.hasPermission("mpet.RiverOtter"))
	 {
		 river = Utils.Colorate("&6&lRiver Otter");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc5MjUwODE4MSwKICAicHJvZmlsZUlkIiA6ICI1NjY3NWIyMjMyZjA0ZWUwODkxNzllOWM5MjA2Y2ZlOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUaGVJbmRyYSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8zM2VjNjc2OGFmZDU3ODM4YzI5N2E2NjA3OTgzZGY5NGNkNjljYjhlNTA3YjdjZWFmYWM2MTJlMDFjMDgwNzkyIgogICAgfQogIH0KfQ==")),
	 		 river, 30, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.riverOtter"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet RiverOtter");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String siamese = Utils.Colorate("&8&lSiamese Cat");
	 if(p.hasPermission("mpet.Siamese"))
	 {
		 siamese = Utils.Colorate("&6&lSiamese Cat");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc5Mjc0NDkzNiwKICAicHJvZmlsZUlkIiA6ICJkNjBmMzQ3MzZhMTI0N2EyOWI4MmNjNzE1YjAwNDhkYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJCSl9EYW5pZWwiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTg0ZWY5MTM4YzM5ZWIwNjhkZGJiOTMzNDA2ZGIxMGQ1Y2I3ODYyODY2NTZlMzc3ZmE0ZDM3ZWZiMDk1NTIxYSIKICAgIH0KICB9Cn0=")),
	 		 siamese, 31, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Siamese"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Siamese");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String tortie = Utils.Colorate("&8&lTortie Cat");
	 if(p.hasPermission("mpet.Tortie"))
	 {
		 tortie = Utils.Colorate("&6&lTortie Cat");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc5Mjg2MjUyMiwKICAicHJvZmlsZUlkIiA6ICI5MThhMDI5NTU5ZGQ0Y2U2YjE2ZjdhNWQ1M2VmYjQxMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJCZWV2ZWxvcGVyIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzRlNmQwODYxZTlkMWU4NmIzODU4MDY5NDU0YjE5OTFhMTQxNjk0ODc0ZmU3ZDllODc5OWRlY2ExNTA3YmEwYmUiCiAgICB9CiAgfQp9")),
	 		 tortie, 32, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Tortie"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Tortie");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});

	 String turtle = Utils.Colorate("&8&lTurtle");
	 if(p.hasPermission("mpet.Turtle"))
	 {
		 turtle = Utils.Colorate("&6&lTurtle");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc5MzEzNjM1NiwKICAicHJvZmlsZUlkIiA6ICJlZDUzZGQ4MTRmOWQ0YTNjYjRlYjY1MWRjYmE3N2U2NiIsCiAgInByb2ZpbGVOYW1lIiA6ICJGbGF3Q3JhQm90MDAiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTMyYWI1Y2IzYjkzMTBmZGNlMDg1MmVmNGYzMjE4NDc3Nzc4ZmM0ZDJjYjIzYmMyYjE1MDgzNDU2MDQzYzg4YiIKICAgIH0KICB9Cn0=")),
	 		 turtle, 33, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.Turtle"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet Turtle");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String white = Utils.Colorate("&8&lWhite Cat");
	 if(p.hasPermission("mpet.White"))
	 {
		 white = Utils.Colorate("&6&lWhite Cat");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjRhYmJmNTk3ZDhjNTVhYmJlMTMxY2M5NWViOGM1NjFjNDcxMzNlYTg3ZDc3NjQyZGVmYWFjNGUyMjNmZGQ1In19fQ==")),
	 		 white, 34, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.White"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet White");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	 
	 String pug = Utils.Colorate("&8&lWhite Pug");
	 if(p.hasPermission("mpet.WhitePug"))
	 {
		 pug = Utils.Colorate("&6&lWhitePug");
	 }
	 
	 petMenu.setItem(new ItemStack(Utils.getItem("ewogICJ0aW1lc3RhbXAiIDogMTU5MDc5MzI4NzM4MSwKICAicHJvZmlsZUlkIiA6ICI3ZGEyYWIzYTkzY2E0OGVlODMwNDhhZmMzYjgwZTY4ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJHb2xkYXBmZWwiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTQxZWNmNDYyM2FlMTNkZWE3MzM4NTU1OTNkMzA4NTFhZmE4N2VmMmQ5ZmY5YTY4YzMyZWQ0YmJhMTgxYTMyZCIKICAgIH0KICB9Cn0=")),
	 		 pug, 35, new ClickRunnable() {
		    @Override
		    public void run(InventoryClickEvent e) {
		    	Player ep = (Player) e.getWhoClicked();
		    	if(ep.hasPermission("mpet.WhitePug"))
		    	{
		    	Bukkit.dispatchCommand(ep, "mpet pet WhitePug");
		       	ep.closeInventory();
		    	}else
		    	{
		    		ep.sendMessage(Utils.Colorate("&cYou don't have this pet!"));
		    	}
		    }
		});
	  
	 petMenu.openInventory(p);
}
	
}