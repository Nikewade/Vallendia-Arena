package me.Nikewade.VallendiaMinigame.Abilities;
 
import java.util.Arrays;
import java.util.List;
 
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
 
import de.slikey.effectlib.effect.SphereEffect;
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;
 
public class FlashbangAbility implements Ability{
 //made by Emma
	int blindTime = 8;
	int range = 8;
     @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "Flashbang";
    }
 
    @Override
    public AbilityType getAbilityType() {
        // TODO Auto-generated method stub
        return AbilityType.UTILITY;
    }
 
    @Override
    public List<String> getDescription() {
        // TODO Auto-generated method stub
        return Arrays.asList("Throw a flashbang projectile",
        		"that explodes when landing,",
                "blinding nearby enemies within",
                + range + " blocks for " + blindTime + " seconds.");
    }
 
    @Override
    public ItemStack getGuiItem() {
        // TODO Auto-generated method stub
        return new ItemStack(Material.SULPHUR);
    }
 
    @Override
    public boolean RunAbility(Player p) {
        // TODO Auto-generated method stub
    	p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 0.6F);
    	
    
        
        SphereEffect se = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
        se.particle = Particle.SMOKE_NORMAL;
        se.disappearWithOriginEntity = true;
        se.infinite();
        se.radius = 0.1;
        se.particles = 5;
        se.speed = (float) 0;
        se.visibleRange = 50;
        
        SphereEffect se2 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
        se2.particle = Particle.CLOUD;
        se2.radius = 9;
        se2.particles = 80;
        se2.speed = (float) 0;  
        se2.iterations = 2;
        se2.visibleRange = 50;
        
        SphereEffect se3 = new SphereEffect(VallendiaMinigame.getInstance().effectmanager);
        se3.particle = Particle.EXPLOSION_LARGE;
        se3.radius = 3;
        se3.particles = 1;
        se3.speed = (float) 0; 
        se3.iterations = 5;
        se3.visibleRange = 50;
     
        
        Runnable run = new Runnable()
        {
 
            @Override
            public void run() {
                // TODO Auto-generated method stub
            	se3.setLocation(se.getLocation());
            	se3.start();                
                se2.setLocation(se.getLocation());  
                se2.start();
                
                p.getWorld().playSound(se.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 3, 1.5F); 
 
                for(Entity e : AbilityUtils.getAoeTargets(p, se.getLocation(), range, range, range))
                {
                
                    AbilityUtils.addPotionDuration((LivingEntity) e, PotionEffectType.BLINDNESS, 2, blindTime*20);
                
                }
                
 
            }
            
        };
        
        AbilityUtils.arcParticle(p, se, 1, run);
        
 
        
        return true;
    }
 
    @Override
    public void DisableAbility(Player p) {
        // TODO Auto-generated method stub
        
    }
 
}