package me.Nikewade.VallendiaMinigame.Graphics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class BossBarHandler implements Listener {
   public static Map<String, BossBars> playersBars = new HashMap();
   private static int time = 2;




   @EventHandler
   public void onQuitPlayer(PlayerQuitEvent event) {
      BossBarHandler.playersBars.remove(event.getPlayer().getName());
   }

   @EventHandler
   public void onEntityDamage(EntityDamageByEntityEvent event) {
	   
	  if(event.getDamager() instanceof Arrow && event.getEntity() instanceof LivingEntity)
	  {
		  Projectile ent = (Projectile) event.getDamager();
		  if(ent.getShooter() instanceof Player)
		  {
		         final Player player = (Player)ent.getShooter();
		         final LivingEntity livingEntity = (LivingEntity)event.getEntity();
		         if (event.getEntity() instanceof Player) {
		            Player twoPlayer = (Player)event.getEntity();
		            if (twoPlayer.getName().equalsIgnoreCase(player.getName())) {
		               return;
		            }
		         }

		         Bukkit.getScheduler().runTask(VallendiaMinigame.getInstance(), new Runnable() {
		            public void run() {
		               BossBarHandler.this.updateBars(player, new BossBars(BossBarHandler.this.getHealthBar(livingEntity, BarColor.RED, Utils.Colorate("&4" + event.getEntity().getName() + "'s Health")), System.currentTimeMillis()));
		            }
		         });
		  }
	  }
	   
      if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
         final Player player = (Player)event.getDamager();
         final LivingEntity livingEntity = (LivingEntity)event.getEntity();
         if (event.getEntity() instanceof Player) {
            Player twoPlayer = (Player)event.getEntity();
            if (twoPlayer.getName().equalsIgnoreCase(player.getName())) {
               return;
            }
         }

         Bukkit.getScheduler().runTask(VallendiaMinigame.getInstance(), new Runnable() {
            public void run() {
               BossBarHandler.this.updateBars(player, new BossBars(BossBarHandler.this.getHealthBar(livingEntity, BarColor.RED, Utils.Colorate("&4" + event.getEntity().getName() + "'s Health")), System.currentTimeMillis()));
            }
         });
      }

   }

   public static void removeEnemyBars() {
      Bukkit.getScheduler().runTaskTimer(VallendiaMinigame.getInstance(), new Runnable() {
         public void run() {
            Iterator var1 = BossBarHandler.playersBars.keySet().iterator();

            while(var1.hasNext()) {
               String name = (String)var1.next();
               BossBarHandler.removeEnemyBar(name);
            }

         }
      }, (long)(time * 20), (long)(time * 20));
   }

   public static void removeEnemyBar(String name) {
      BossBars bossBars = (BossBars)BossBarHandler.playersBars.get(name);
      if (System.currentTimeMillis() - bossBars.getLastUpdate() > (long)(time * 1000)) {
         deleteBar(bossBars.getEnemyHp());
      }

   }


   private void updateBars(Player player, BossBars bossBars) {
      BossBars currentBossBars = (BossBars)this.playersBars.get(player.getName());
      if (currentBossBars != null) {
         BossBarHandler.deleteBar(currentBossBars.getEnemyHp());
      }

      BossBarHandler.playersBars.put(player.getName(), bossBars);
      this.setBar(player, bossBars.getEnemyHp());
   }

   private void setBar(Player player, BossBar bossBar) {
      if (bossBar != null) {
         bossBar.addPlayer(player);
      }

   }

   private static void deleteBar(BossBar bossBar) {
      if (bossBar != null) {
         bossBar.removeAll();
      }

   }

   private BossBar getHealthBar(LivingEntity entity, BarColor barColor, String title) {
      int heath = this.getHealth(entity);
      BossBar bossBar = Bukkit.createBossBar(String.format(title, heath, Math.ceil(entity.getMaxHealth())), barColor, BarStyle.SEGMENTED_20, new BarFlag[0]);
      bossBar.setProgress((double)heath / Math.ceil(entity.getMaxHealth()));
      return bossBar;
   }

   private int getHealth(LivingEntity entity) {
      return (int)Math.ceil(entity.getHealth());
   }

   
   
}
