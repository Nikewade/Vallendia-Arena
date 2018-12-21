package me.Nikewade.VallendiaMinigame.Commands;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.Interface.CommandInterface;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class CommandHandler implements CommandExecutor
{
 
    //This is where we will store the commands
    private static HashMap<String, CommandInterface> commands = new HashMap<String, CommandInterface>();
 
    //Register method. When we register commands in our onEnable() we will use this.
    public void register(String name, CommandInterface cmd) {
 
        //When we register the command, this is what actually will put the command in the hashmap.
        commands.put(name, cmd);
    }
 
    //This will be used to check if a string exists or not.
    public boolean exists(String name) {
 
        //To actually check if the string exists, we will return the hashmap
        return commands.containsKey(name);
    }
 
    //Getter method for the Executor.
    public CommandInterface getExecutor(String name) {
 
        //Returns a command in the hashmap of the same name.
        return commands.get(name);
    }
 
    //This will be a template. All commands will have this in common.
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
 
        //For example, in each command, it will check if the sender is a player and if not, send an error message.
            //If there aren't any arguments, what is the command name going to be? For this example, we are going to call it /example.
            //This means that all commands will have the base of /example.
            if(args.length == 0) {
                getExecutor("vallendia").onCommand(sender, cmd, commandLabel, args);
                return true;
            }
 
            //What if there are arguments in the command? Such as /example args
            if(args.length > 0) {
 
                //If that argument exists in our registration in the onEnable();
                if(exists(args[0].toLowerCase())){
 
                    //Get The executor with the name of args[0]. With our example, the name of the executor will be args because in
                    //the command /example args, args is our args[0].
                    getExecutor(args[0].toLowerCase()).onCommand(sender, cmd, commandLabel, args);
                    return true;
                } else {
 
                    //We want to send a message to the sender if the command doesn't exist.
                    sender.sendMessage(Utils.Colorate("&8That command doesn't exist!"));
                    return true;
                }
            }

        return false;
    }
 
}
