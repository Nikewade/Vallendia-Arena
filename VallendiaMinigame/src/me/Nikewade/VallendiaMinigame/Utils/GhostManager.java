package me.Nikewade.VallendiaMinigame.Utils;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
 
public class GhostManager {
    private static final String GHOST_TEAM_NAME = "Ghosts";
    private static final long UPDATE_DELAY = 5L;
    
    // No players in the ghost factory
    private static final OfflinePlayer[] EMPTY_PLAYERS = new OfflinePlayer[0];
    
    private Team ghostTeam;
    
    // Task that must be cleaned up
    private BukkitTask task;
    private boolean closed;
    
    public GhostManager(Plugin plugin) {
        // Initialize
        createTask(plugin);
        createGetTeam();
    }

    private void createGetTeam() {
        Scoreboard board = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
        
        ghostTeam = board.getTeam(GHOST_TEAM_NAME);
        
        // Create a new ghost team if needed
        if (ghostTeam == null) {
            ghostTeam = board.registerNewTeam(GHOST_TEAM_NAME);
            ghostTeam.setCanSeeFriendlyInvisibles(true);
        }
    }
    
    private void createTask(Plugin plugin) {
        task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                for (OfflinePlayer ghostPlayer : getGhosts()) {
                    Player player = ghostPlayer.getPlayer();

                    if (player != null) {
                        if(!player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 15));
                        }
                    } else {
                        ghostTeam.removePlayer(ghostPlayer);
                    }
                }
            }
        }, UPDATE_DELAY, UPDATE_DELAY);
    }
  
    public void clearGhosts() {
        if (ghostTeam != null) {
            for (OfflinePlayer player : getGhosts()) {
                ghostTeam.removePlayer(player);
            }
        }
    }
   
    public void addGhost(Player player) {
        validateState();
        if (!ghostTeam.hasPlayer(player)) {
            ghostTeam.addPlayer(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 15));
        }
    }
    
    public boolean hasGhost(Player player) {
        validateState();
        return ghostTeam.hasPlayer(player);
    }
 
    public void removeGhost(Player player) {
        validateState();
        if (ghostTeam.removePlayer(player)) {
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
        }
    }
 
    public OfflinePlayer[] getGhosts() {
        validateState();
        Set<OfflinePlayer> players = ghostTeam.getPlayers();
        
        if (players != null) {
            return players.toArray(new OfflinePlayer[0]);
        } else {
            return EMPTY_PLAYERS;
        }
    }

    public void close() {
        if (!closed) {
            task.cancel();
            ghostTeam.unregister();
            closed = true;
        }
    }
    
    public boolean isClosed() {
        return closed;
    }
    
    private void validateState() {
        if (closed) {
            throw new IllegalStateException("Ghost factory has closed. Cannot reuse instances.");
        }
    }
}