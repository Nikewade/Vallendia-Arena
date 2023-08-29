package me.Nikewade.VallendiaMinigame.Donations;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.kirelcodes.miniaturepets.api.events.pets.PetFinishedSpawnEvent;
import com.kirelcodes.miniaturepets.api.events.pets.PetRemovedEvent;
import com.kirelcodes.miniaturepets.api.events.pets.PetSpawnEvent;
import com.kirelcodes.miniaturepets.utils.APIUtils;

import de.slikey.effectlib.effect.ConeEffect;
import de.slikey.effectlib.effect.DonutEffect;
import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Utils.Language;

public class PetParticleEvents implements Listener{
	VallendiaMinigame main;
	
	public PetParticleEvents(VallendiaMinigame main)
	{
		this.main = main;	
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onSpawn (PetFinishedSpawnEvent e)
	{
		if(e.getPet().getType().equalsIgnoreCase("babydragon"))
		{
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.disappearWithOriginEntity = true;
			se.setEntity(e.getPet().getNavigator());
			se.infinite();
			se.particle = Particle.DRAGON_BREATH;
			se.radius = 0.8;
			se.particleOffsetY = (float) 0.5;
			se.particles = 1;
			se.yOffset = 2;
			se.particleOffsetX = 1;
			se.speed = (float) 0;
			se.start();

		}
		
		if(e.getPet().getType().equalsIgnoreCase("astronaught"))
		{
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.disappearWithOriginEntity = true;
			se.setEntity(e.getPet().getNavigator());
			se.infinite();
			se.particle = Particle.CLOUD;
			se.radius = 0.1;
			se.particles = 1;
			se.yOffset = -0.8F;
			se.speed = (float) 0;
			se.start();

		}
	
	
		
		if(e.getPet().getType().equalsIgnoreCase("devil"))
		{
			SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
			se.disappearWithOriginEntity = true;
			se.setEntity(e.getPet().getNavigator());
			se.infinite();
			se.particle = Particle.FLAME;
			se.radius = 0.1;
			se.particles = 1;
			se.yOffset = 0.8;
			se.speed = (float) 0;
			se.start();
			
		}
				

	}
	

}