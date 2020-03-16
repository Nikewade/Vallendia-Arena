package me.Nikewade.VallendiaMinigame.Utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class FallingBlockSet {

    private HashMap<Location, ItemStack> initialBlocksData = new HashMap<>();
    private ArrayList<Entity> fallingBlocks = new ArrayList<>();
    private ArrayList<Location> fallingBlocksLand = new ArrayList<>();
    private double durationRemaining;

    FallingBlockSet(HashMap<Location, ItemStack> initialBlocksData, ArrayList<Entity> fallingBlocks, double initialDuration) {
        this.initialBlocksData = initialBlocksData;
        this.fallingBlocks = fallingBlocks;
        this.durationRemaining = initialDuration;
    }

    public double getDurationRemaining() {
        return durationRemaining;
    }

    public void setDurationRemaining(double durationRemaining) {
        this.durationRemaining = durationRemaining;
    }

    public ArrayList<Entity> getFallingBlocks() {
        return fallingBlocks;
    }

    ArrayList<Location> getFallingBlocksLand() {
        return fallingBlocksLand;
    }

    public HashMap<Location, ItemStack> getInitialBlocksData() {
        return initialBlocksData;
    }
}
