package me.Nikewade.VallendiaMinigame.Utils;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityUtils {
	//If the player already has a potion effect, this will add the existing effects duration to the new one.
	public static void addPotionDuration(LivingEntity e, PotionEffectType p, int amplifier, int duration )
	{
		if(!e.hasPotionEffect(p))
		{
			e.addPotionEffect(new PotionEffect(p, duration, amplifier));
			return;
		}
		int existingAmp = e.getPotionEffect(p).getAmplifier();
		int existingDuration = e.getPotionEffect(p).getDuration();
		
			if(existingAmp < amplifier)
			{
				e.removePotionEffect(p);
				e.addPotionEffect(new PotionEffect(p, duration, existingAmp + amplifier));	
			}
			
			if(existingAmp > amplifier)
			{
				e.removePotionEffect(p);
				e.addPotionEffect(new PotionEffect(p, existingDuration, existingAmp + amplifier));
			}
			
			if(existingAmp == amplifier)
			{
			  if(existingDuration > duration)
			  {
					e.removePotionEffect(p);
					e.addPotionEffect(new PotionEffect(p, existingDuration, existingAmp));
			  }else
			  {
					e.removePotionEffect(p);
					e.addPotionEffect(new PotionEffect(p, duration, existingAmp));
			  }
			} 
	}
}
