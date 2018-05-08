package me.Nikewade.VallendiaMinigame.Abilities;

public enum AbilityType {

	OFFENSIVE, DEFENSIVE, MOVEMENT ;
	
	public boolean isType(AbilityType type)
	{
		return this == type;
	}
}
