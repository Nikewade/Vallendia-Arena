package me.Nikewade.VallendiaMinigame.Utils;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Location;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import net.minecraft.server.v1_12_R1.World;

public class SerializeUtils {

	 public static String SerializeLocation(Location Location) {
	      return Location.getWorld().getName() + "_" + Location.getX() + "_" + Location.getY() + "_" + Location.getZ() + "_" + Location.getYaw() + "_" + Location.getPitch();
	   }

	   public static Location DeserializeLocation(String Location) {
	      try {
	         String[] Exception = Location.split("_");
	         World World = (net.minecraft.server.v1_12_R1.World) VallendiaMinigame.getInstance().getServer().getWorld(Exception[0]);
	         if(World == null) {
	            throw new Exception();
	         } else {
	            double X = Double.parseDouble(Exception[1]);
	            double Y = Double.parseDouble(Exception[2]);
	            double Z = Double.parseDouble(Exception[3]);
	            float Yaw = Float.parseFloat(Exception[4]);
	            float Pitch = Float.parseFloat(Exception[5]);
	            Location Loc = new Location((org.bukkit.World) World, X, Y, Z);
	            Loc.setYaw(Yaw);
	            Loc.setPitch(Pitch);
	            return Loc;
	         }
	      } catch (Exception var12) {
	         var12.printStackTrace();
	         return new Location((org.bukkit.World)VallendiaMinigame.getInstance().getServer().getWorlds().get(0), 0.0D, 64.0D, 0.0D);
	      }
	   }

	   public static HashMap getStringData(String String) {
	      HashMap Data = new HashMap();
	      if(String == null) {
	         return Data;
	      } else {
	         try {
	            String[] Split = String.split(",");
	            String[] var3 = Split;
	            int var4 = Split.length;

	            for(int var5 = 0; var5 < var4; ++var5) {
	               String DataString = var3[var5];

	               try {
	                  String[] DataSplit = DataString.split(":");
	                  Data.put(DataSplit[0], DataSplit[1]);
	               } catch (Exception var8) {
	                  ;
	               }
	            }
	         } catch (Exception var9) {
	            ;
	         }

	         return Data;
	      }
	   }

	   public static String getDataString(HashMap Data) {
	      String DataString = "";
	      boolean First = true;
	      Iterator var3 = Data.keySet().iterator();

	      while(var3.hasNext()) {
	         String String = (String)var3.next();
	         Object Object = Data.get(String);
	         if(String != null && Object != null) {
	            if(First) {
	               DataString = DataString + String + ":" + (String)Data.get(String);
	               First = false;
	            } else {
	               DataString = DataString + "," + String + ":" + (String)Data.get(String);
	            }
	         }
	      }

	      return DataString;
	   }
	}

