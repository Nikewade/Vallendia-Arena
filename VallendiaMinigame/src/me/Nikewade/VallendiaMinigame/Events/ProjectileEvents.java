/*    */ package me.Nikewade.VallendiaMinigame.Events;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.ProjectileHitEvent;
/*    */ import org.bukkit.plugin.Plugin;

/*    */ import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
/*    */ 
/*    */ import me.Nikewade.VallendiaMinigame.Utils.Utils;
/*    */ 
/*    */ public class ProjectileEvents
/*    */   implements Listener {
/*    */   VallendiaMinigame Main;
/*    */   
/*    */   public ProjectileEvents(VallendiaMinigame Main) {
/* 18 */     this.Main = Main;
/* 19 */     Main.getServer().getPluginManager().registerEvents(this, (Plugin)Main);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onHit(ProjectileHitEvent e) {
/* 26 */     Block block = e.getHitBlock();
			 if(block == null)
			 {
				 return;
			 }
/* 27 */     if (block.getTypeId() == 95 || block.getTypeId() == 20 || block.getTypeId() == 160 || block.getTypeId() == 102) {
/*    */ 
/*    */       
/* 30 */       Utils.regenBlock(block, 15);
/* 31 */       block.setType(Material.AIR);
/* 32 */       block.getDrops().remove(Boolean.valueOf(true));
/* 33 */       block.getWorld().playSound(block.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0F, 1.0F);
/* 34 */       e.getEntity().remove();
/*    */     } 
/*    */   }
/*    */ }
