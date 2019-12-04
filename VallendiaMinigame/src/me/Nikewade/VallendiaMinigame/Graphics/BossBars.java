package me.Nikewade.VallendiaMinigame.Graphics;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;

public class BossBars {
	   public BossBar enemyHp;
	   public static BossBar enemyHp2;
	   private long lastUpdate;

	   public BossBars(BossBar enemyHp) {
	      this.enemyHp = enemyHp;
	      enemyHp2 = enemyHp;
	   }

	   public BossBars(BossBar enemyHp, long lastUpdate) {
	      this.enemyHp = enemyHp;
	      enemyHp2 = enemyHp;
	      this.lastUpdate = lastUpdate;
	   }

	   public long getLastUpdate() {
	      return this.lastUpdate;
	   }

	   public BossBar getEnemyHp() {
	      return this.enemyHp;
	   }
	   
	   public static void removeAll()
	   {
		   if(BossBars.enemyHp2 != null)
		   {
			   BossBars.enemyHp2.removeAll();   
		   }
	   }
	}