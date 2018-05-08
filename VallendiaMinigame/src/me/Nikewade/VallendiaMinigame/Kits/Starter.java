package me.Nikewade.VallendiaMinigame.Kits;

import java.awt.List;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;
import me.Nikewade.VallendiaMinigame.Utils.Utils;

public class Starter implements Kit {
VallendiaMinigame Main;
	
	public Starter(VallendiaMinigame Main)
	{
		this.Main = Main;
		Main.kitmanager.kits.put(this.getName(false), this);
	}

	@Override
	public String getName(Boolean colored) {
		// TODO Auto-generated method stub
		if (colored)
		{
			return Utils.Colorate("Starter");
		}else return "starter";

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getAbilities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getupgradeDiscount(String upgrade) {
		// TODO Auto-generated method stub
		return 0;
	}

}
