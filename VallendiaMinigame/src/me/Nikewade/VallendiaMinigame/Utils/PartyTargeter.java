package me.Nikewade.VallendiaMinigame.Utils;
import java.util.HashSet;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.targeters.CustomTargeter;


public class PartyTargeter extends CustomTargeter{

	public PartyTargeter(String targeter, MythicLineConfig mlc) {
		super(targeter, mlc);
		// TODO Auto-generated constructor stub
		
	}
	
	
	public HashSet<AbstractEntity> getEntities(SkillMetadata data)
	{
		HashSet<AbstractEntity> targets = new HashSet<>();
		
		for(AbstractEntity ent : data.getEntityTargets())
		{
			targets.add(ent);
		}
		return targets;
		
	}
	
	
}