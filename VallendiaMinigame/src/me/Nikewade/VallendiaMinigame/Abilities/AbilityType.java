package me.Nikewade.VallendiaMinigame.Abilities;

public enum AbilityType {

	OFFENSIVE, DEFENSIVE, UTILITY, PASSIVE ;
	
	public boolean isType(AbilityType type)
	{
		return this == type;
	}
}
