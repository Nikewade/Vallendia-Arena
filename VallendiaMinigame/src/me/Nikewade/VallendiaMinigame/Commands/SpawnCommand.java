package me.Nikewade.VallendiaMinigame.Commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class SpawnCommand implements CommandInterface{
	VallendiaMinigame  main = VallendiaMinigame.getInstance();
	YamlConfiguration config;

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
			sender.sendMessage(Utils.Colorate("&3/vallendia spawn add &9(spawnname)"));
			sender.sendMessage(Utils.Colorate("&3/vallendia spawn remove &9(spawnname)"));
			sender.sendMessage(Utils.Colorate("&3/vallendia spawn teleport &9(spawnname)"));
			sender.sendMessage("");
			sender.sendMessage(Utils.Colorate("&8&m-------------------------------------------"));	    
			return false;
	    }
	    
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
						File f = new File(VallendiaMinigame.getInstance().getFileManager().getSpawnFile().getAbsolutePath() + "/" + spawnname + ".yml");
						if(f.exists())
						{
							this.config = YamlConfiguration.loadConfiguration(f);
							World w = Bukkit.getServer().getWorld(config.getString("location.World"));
							double x = config.getDouble("location.X");
							double y = config.getDouble("location.Y");
							double z = config.getDouble("location.Z");
							double yaw = config.getDouble("location.Yaw");
							double pitch = config.getDouble("location.Pitch");
							p.teleport(new Location(w, x, y, z, (float)yaw , (float)pitch));
							p.sendMessage(Utils.Colorate("&8Teleported to " + spawnname + "."));	
							return false;
						}else p.sendMessage(Utils.Colorate("&8Spawn " + spawnname + " does not exist."));
		    		}
		    }
	    }
		return false;
	}

}