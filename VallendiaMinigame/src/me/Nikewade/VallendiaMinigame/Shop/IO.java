package me.Nikewade.VallendiaMinigame.Shop;

import java.io.File;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;

public class IO {
	public static final File getShopFile() { return new File(VallendiaMinigame.getInstance().getDataFolder(), "/shop.txt"); }
}
