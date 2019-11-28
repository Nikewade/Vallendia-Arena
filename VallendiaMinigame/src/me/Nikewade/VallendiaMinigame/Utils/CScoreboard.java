package me.Nikewade.VallendiaMinigame.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class CScoreboard {
	   private final String name;
	   private final String criterion;
	   private final Scoreboard bukkitScoreboard;
	   private final Objective obj;
	   String title;
	   private CScoreboard.Row[] rows = new CScoreboard.Row[0];
	   private List<CScoreboard.Row> rowCache = new ArrayList();
	   private boolean finished = false;

	   public CScoreboard(String name, String criterion, String title) {
	      this.name = name;
	      this.criterion = criterion;
	      this.title = title;
	      this.bukkitScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	      this.obj = this.bukkitScoreboard.registerNewObjective(name, criterion);
	      this.obj.setDisplaySlot(DisplaySlot.SIDEBAR);
	      this.obj.setDisplayName(title);
	   }

	   public void setTitle(String title) {
	      this.title = title;
	      this.obj.setDisplayName(title);
	   }

	   public void display(Player player) {
	      player.setScoreboard(this.bukkitScoreboard);
	   }

	   @Nullable
	   public CScoreboard.Row addRow(String message) {
	      if (this.finished) {
	         (new NullPointerException("Can not add rows if scoreboard is already finished")).printStackTrace();
	         return null;
	      } else {
	         try {
	            CScoreboard.Row row = new CScoreboard.Row(this, message, this.rows.length);
	            this.rowCache.add(row);
	            return row;
	         } catch (Exception var3) {
	            return null;
	         }
	      }
	   }

	   public void finish() {
	      if (this.finished) {
	         (new NullPointerException("Can not finish if scoreboard is already finished")).printStackTrace();
	      } else {
	         this.finished = true;

	         for(int i = this.rowCache.size() - 1; i >= 0; --i) {
	            CScoreboard.Row row = (CScoreboard.Row)this.rowCache.get(i);
	            Team team = this.bukkitScoreboard.registerNewTeam(this.name + "." + this.criterion + "." + (i + 1));
	            team.addEntry("" + ChatColor.values()[i]);
	            this.obj.getScore("" + ChatColor.values()[i]).setScore(this.rowCache.size() - i);
	            row.team = team;
	            row.setMessage(row.message);
	         }

	         this.rows = (CScoreboard.Row[])this.rowCache.toArray(new CScoreboard.Row[this.rowCache.size()]);
	      }
	   }

	   public static class Row {
	      private final CScoreboard scoreboard;
	      private Team team;
	      private final int rowInScoreboard;
	      private String message;

	      public Row(CScoreboard sb, String message, int row) {
	         this.scoreboard = sb;
	         this.rowInScoreboard = row;
	         this.message = message;
	      }

	      public void setMessage(String message) {
	         this.message = message;
	         if (this.scoreboard.finished) {
	            String[] parts = splitStringWithChatcolorInHalf(message);
	            this.team.setPrefix(parts[0]);
	            this.team.setSuffix(parts[1]);
	         }

	      }

	      private static String[] splitStringWithChatcolorInHalf(String str) {
	         String[] strs = new String[2];
	         ChatColor cc1 = ChatColor.WHITE;
	         ChatColor cc2 = null;
	         Character lastChar = null;
	         strs[0] = "";

	         for(int i = 0; i < str.length() / 2; ++i) {
	            char c = str.charAt(i);
	            if (lastChar != null) {
	               ChatColor cc = charsToChatColor(new char[]{lastChar, c});
	               if (cc != null) {
	                  if (cc.isFormat()) {
	                     cc2 = cc;
	                  } else {
	                     cc1 = cc;
	                     cc2 = null;
	                  }
	               }
	            }

	            strs[0] = strs[0] + c;
	            lastChar = c;
	         }

	         strs[1] = "" + (cc1 != null ? cc1 : "") + (cc2 != null ? cc2 : "") + str.substring(str.length() / 2);
	         return strs;
	      }

	      @Nullable
	      private static ChatColor charsToChatColor(char[] chars) {
	         ChatColor[] var4;
	         int var3 = (var4 = ChatColor.values()).length;

	         for(int var2 = 0; var2 < var3; ++var2) {
	            ChatColor cc = var4[var2];
	            char[] ccChars = cc.toString().toCharArray();
	            int same = 0;

	            for(int i = 0; i < 2; ++i) {
	               if (ccChars[i] == chars[i]) {
	                  ++same;
	               }
	            }

	            if (same == 2) {
	               return cc;
	            }
	         }

	         return null;
	      }
	   }
	}
