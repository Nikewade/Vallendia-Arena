package me.Nikewade.VallendiaMinigame.Abilities;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class EnormousShieldAbility implements Ability{
	HashMap<Player, EditSession> builds = new HashMap<>();
	int time = 15;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Enormous Shield";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.DEFENSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.SHIELD);
	}

	@Override
	public boolean RunAbility(Player p) {
		File file = new File(VallendiaMinigame.getInstance().getDataFolder().getParent() + "/WorldEdit/schematics/shield.schematic");
		Location toPaste = p.getLocation();
		EditSession es = new EditSession(new BukkitWorld(toPaste.getWorld()), Integer.MAX_VALUE);
		SchematicFormat  format = SchematicFormat.getFormat(file);
		CuboidClipboard cc = null;
		try {
			cc = format.load(file);
		} catch (DataException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(Utils.yawToFace(toPaste.getYaw()) == BlockFace.NORTH )
		{
			cc.rotate2D(90);
		}
		
		if(Utils.yawToFace(toPaste.getYaw()) == BlockFace.SOUTH )
		{
			cc.rotate2D(270);
		}
		
		if(Utils.yawToFace(toPaste.getYaw()) == BlockFace.EAST )
		{
			cc.rotate2D(180);
		}
		try {
			cc.paste(es, BukkitUtil.toVector(toPaste), true, true);
		} catch (MaxChangedBlocksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		builds.put(p, es);
		
		
		BukkitTask task = Bukkit.getScheduler().runTaskLater(VallendiaMinigame.getInstance(), new Runnable() {
			  @Override
			  public void run() {             	
				  es.undo(es);
				  builds.remove(p); 
  				}
			}, time * 20L);
		
		return false;
	}

}
