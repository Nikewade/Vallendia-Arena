package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

<<<<<<< HEAD
import org.bukkit.Material;
import org.bukkit.Sound;
=======
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
>>>>>>> second-repo/master
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

<<<<<<< HEAD
import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
=======
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.CustomEvents.BuyAbilityEvent;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Interface.Kit;
import me.Nikewade.VallendiaMinigame.Utils.AbilityCooldown;
import me.Nikewade.VallendiaMinigame.Utils.Language;
>>>>>>> second-repo/master
import me.Nikewade.VallendiaMinigame.Utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class AbilityManager {
	VallendiaMinigame Main;
	private ArrayList<Ability> abilities = new ArrayList<Ability>();
<<<<<<< HEAD
	private HashMap<UUID, Long> cooldown = new HashMap<>();
=======
	public static ArrayList<Ability> disabledAbilities = new ArrayList<Ability>();
	private HashMap<UUID, Long> cooldown = new HashMap<>();
	private static HashMap<Player, ArrayList<String>> playerAbilities = new HashMap<>();
	//Hashmap Ability name , SLOT
	private static HashMap<Player, HashMap<String, String>> abilitySlots = new HashMap<>();
>>>>>>> second-repo/master
	
	
	public AbilityManager(VallendiaMinigame Main)
	{
		this.Main = Main;
		
		abilities.add(new ClimbAbility());
		abilities.add(new BackFlipAbility());
		abilities.add(new DeflectArrowsAbility());
		abilities.add(new MageArmorAbility());
		abilities.add(new RageAbility());
		abilities.add(new LeapAbility());
		abilities.add(new BashAbility());
		abilities.add(new DashAbility());
		abilities.add(new SneakAbility());
		abilities.add(new BackstabAbility());
		abilities.add(new GrapplingHookAbility());
<<<<<<< HEAD
		abilities.add(new BlinkAbility());
=======
		abilities.add(new DimensionDoorAbility());
>>>>>>> second-repo/master
		abilities.add(new ShadowstepAbility());
		abilities.add(new TheHighGroundAbility());
		abilities.add(new BlindingArrowsAbility());
		abilities.add(new PoisonArrowsAbility());
		abilities.add(new SickeningArrowsAbility());
		abilities.add(new WeakeningArrowsAbility());
		abilities.add(new SlowingArrowsAbility());
		abilities.add(new MagicArrowsAbility());
		abilities.add(new RootAbility());
		abilities.add(new BullRushAbility());
		abilities.add(new MomentumAbility());
		abilities.add(new ExplosiveArrowAbility());
		abilities.add(new EquipBowAbility());
		abilities.add(new PillageAbility());
		abilities.add(new PickPocketAbility());
		abilities.add(new KickAbility());
		abilities.add(new MountAbility());
		abilities.add(new BlurAbility());
		abilities.add(new DivineShieldAbility());
		abilities.add(new VanishAbility());
		abilities.add(new VampiricTouchAbility());
		abilities.add(new ParticleTestAbility());
		abilities.add(new LastStandAbility());
		abilities.add(new BandageAbility());
		abilities.add(new SurvivalistAbility());
		abilities.add(new FireballAbility());
		abilities.add(new LightningBoltAbility());
		abilities.add(new DisintegrateAbility());
		abilities.add(new FlyAbility());
		abilities.add(new FlashbangAbility());
		abilities.add(new SwapAbility());
<<<<<<< HEAD
		abilities.add(new LevitateAbility());
=======
>>>>>>> second-repo/master
		abilities.add(new ReflexAbility());
		abilities.add(new CheapShotAbility());
		abilities.add(new KidneyShotAbility());
		abilities.add(new HoldPersonAbility());
		abilities.add(new EnvenomAbility());
		abilities.add(new EscapeArtistAbility());
		abilities.add(new FanOfKnivesAbility());
		abilities.add(new SniperAbility());
		abilities.add(new SiphonAbility());
		abilities.add(new RepurposeAbility());
		abilities.add(new StunningBlowsAbility());
		abilities.add(new CamouflageAbility());
<<<<<<< HEAD
=======
        abilities.add(new RepellingShotAbility());
>>>>>>> second-repo/master
		abilities.add(new IceTrapAbility());
        abilities.add(new SunderWeaponAbility());
        abilities.add(new StompAbility());
        abilities.add(new PiercingRoarAbility());
        abilities.add(new SunderArmorAbility());
        abilities.add(new PummelAbility());
<<<<<<< HEAD
        abilities.add(new QuakeAbility());
		
=======
        abilities.add(new BearTrapAbility());
        abilities.add(new ExplosiveTrapAbility());
        abilities.add(new QuickDeathAbility());
        abilities.add(new HealingBlastAbility());
        abilities.add(new MartyrdomAbility());
        abilities.add(new CatFallAbility());
        abilities.add(new PhoenixAbility());
        abilities.add(new ThornShotAbility());
        abilities.add(new HealingArrowAbility());
        abilities.add(new SonarArrowAbility());
        abilities.add(new AcidSplashAbility());
        abilities.add(new InitiateAbility());
        abilities.add(new VampiricAuraAbility());
        abilities.add(new HealAbility());
        abilities.add(new LoneWolfAbility());
        abilities.add(new OneManArmyAbility());
        abilities.add(new BreakFreeAbility());
        abilities.add(new NoxiousGasTrapAbility());
        abilities.add(new HealingBurstAbility());
        abilities.add(new ZapAbility());
        abilities.add(new MindThrustAbility());
        abilities.add(new ThunderShotAbility());
        abilities.add(new RepellingBlastAbility());
        abilities.add(new DelayedBlastFireballAbility());
        abilities.add(new WaterMasteryAbility());
        abilities.add(new QuakeAbility());
        abilities.add(new BlindnessAbility());
        abilities.add(new HamstringAbility());
        abilities.add(new EnthrallAbility());
        abilities.add(new RepulsionAbility());
        abilities.add(new HealingAuraAbility());
        abilities.add(new GlitterdustAbility());
        abilities.add(new InvisibilityAbility());
        abilities.add(new AllureAbility());
        abilities.add(new FaerieFireAbility());
        abilities.add(new LayOnHandsAbility());
        abilities.add(new RallyUpAbility());
        abilities.add(new BlessingAuraAbility());
        abilities.add(new EntangleAbility());
        abilities.add(new BolasAbility());
        abilities.add(new CharmPersonAbility());
        abilities.add(new FireMasteryAbility());
        abilities.add(new TauntCreaturesAbility());
        abilities.add(new ThroughTheSeamsAbility());
        abilities.add(new ConcussiveShotAbility());
        abilities.add(new VendettaAbility());
        abilities.add(new NightCrawlerAbility());
        abilities.add(new FavouredEnemyAbility());
        abilities.add(new CameraAbility());
        abilities.add(new ShieldAbility());
        abilities.add(new LocateAbility());
        abilities.add(new AvasculateAbility());
        abilities.add(new ShadowCloneAbility());
        abilities.add(new InvestorAbility());
        abilities.add(new AcidRainAbility());
        abilities.add(new ContingencyAbility());
        abilities.add(new IceShardAbility());
        abilities.add(new FireBlastAbility());
        abilities.add(new RayOfEnfeeblementAbility());
        abilities.add(new HeatRayAbility());
        abilities.add(new FreezeRayAbility());
        abilities.add(new AcidicRayAbility());
        abilities.add(new PickOffAbility());
        abilities.add(new StoneSkinAbility());
        abilities.add(new ScorchingRayAbility());
        abilities.add(new RayOfFrostAbility());
        abilities.add(new GhostTouchAbility());
        abilities.add(new SunStrikeAbility());
        abilities.add(new TunnelVisionAbility());
        abilities.add(new TauntAbility());
        abilities.add(new WhirlWindAbility());
        abilities.add(new BakerAbility());
        abilities.add(new ExpeditiousRetreatAbility());
        abilities.add(new UncannyDodgeAbility());
        abilities.add(new DisruptiveWandAbility());
        abilities.add(new FlameImbuedWandAbility());
        abilities.add(new ExtendWandAbility());
        abilities.add(new QuickenWandAbility());
        abilities.add(new DaggerThrowAbility());
>>>>>>> second-repo/master
		int totalAbilities = abilities.size();
		Utils.log("&3[Abilities]");
		Utils.log("&3Total: " + totalAbilities);
		
		this.generateAbilityPrices();
<<<<<<< HEAD
=======
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			AbilityManager.saveAbilities(p);
			
		}
		ConfigurationSection section = VallendiaMinigame.getInstance().getConfig().getConfigurationSection("DisabledAbilities");
		for(String s : section.getKeys(false))
		{
			disabledAbilities.add(this.getAbility(s));
		}
		
>>>>>>> second-repo/master
	}
	
	
	public ArrayList<Ability> getAbilities() {
		return abilities;
	}
	
		
	
<<<<<<< HEAD
=======
	public static void saveAbilities(Player p)
	{
		String slot1 = AbilityManager.getAbilitySlot("1", p);
		String slot2 = AbilityManager.getAbilitySlot("2", p);
		String slot3 = AbilityManager.getAbilitySlot("3", p);
		String slot4 = AbilityManager.getAbilitySlot("4", p);
		String slot5 = AbilityManager.getAbilitySlot("5", p);
		String slot6 = AbilityManager.getAbilitySlot("6", p);
		ArrayList<String> playerAbilitiesList = new ArrayList<String>();
		HashMap<String, String> playerAbilitySlots = new HashMap<>();
		if(slot1 != "empty")
		{
			playerAbilitiesList.add(slot1);	
			playerAbilitySlots.put(slot1, "1");
		}
		if(slot2 != "empty")
		{
			playerAbilitiesList.add(slot2);	
			playerAbilitySlots.put(slot2, "2");
		}
		if(slot3 != "empty")
		{
			playerAbilitiesList.add(slot3);	
			playerAbilitySlots.put(slot3, "3");
		}
		if(slot4 != "empty")
		{
			playerAbilitiesList.add(slot4);	
			playerAbilitySlots.put(slot4, "4");
		}
		if(slot5 != "empty")
		{
			playerAbilitiesList.add(slot5);	
			playerAbilitySlots.put(slot5, "5");
		}
		if(slot6 != "empty")
		{
			playerAbilitiesList.add(slot6);	
			playerAbilitySlots.put(slot6, "6");
		}
		
		playerAbilities.put(p, playerAbilitiesList);
		abilitySlots.put(p, playerAbilitySlots);
	}
	
	public static void unsaveAbilities(Player p)
	{
		if(playerAbilities.containsKey(p))
		{
			playerAbilities.get(p).clear();
			abilitySlots.get(p).clear();	
		}
	}
	
	//Disables all abilities except the one that is specified 
	public void disableAllExceptAbility(Player p, String abilityName)
	{
		for(String s : AbilityManager.playerAbilities.get(p))
		{
			if(!s.equalsIgnoreCase(abilityName))
			{
				this.getAbility(s).DisableAbility(p);
			}
		}
	}
	
	
>>>>>>> second-repo/master
	public Ability getAbility(String name) 
		{
					for(Ability ability : abilities)
					{
						if(ability.getName().equalsIgnoreCase(name))
						{
							return ability;
						}
					}
			return null;
		}
	
<<<<<<< HEAD
	public String getAbilitySlot(int slot, Player p)
	{
		return Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot" + slot);
=======
	public static String getAbilitySlot(String slot, Player p)
	{
		return VallendiaMinigame.getInstance().playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot " + slot);
	}
	
	public static String getSlotOfAbility(String abilityname, Player p)
	{
		if(abilitySlots.containsKey(p))
		{
			HashMap<String, String> h = abilitySlots.get(p);
			
			if(h.containsKey(abilityname))
			{
				return h.get(abilityname);
			}
		}
		return null;
	}
	
	
	public static String getAbilityData(String abilityname, Player p)
	{
		return VallendiaMinigame.getInstance().playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot " + 
	AbilityManager.getSlotOfAbility(abilityname, p) + " data");
	}
	
	public static void addAbilityData(String abilityname, Player p, String data)
	{
		VallendiaMinigame.getInstance().playerdatamanager.editData(p.getUniqueId(), "Abilities.slot " + 
	AbilityManager.getSlotOfAbility(abilityname, p) + " data", data);
	}
	
	
	public static void removeAbilityData(String abilityname, Player p)
	{
		VallendiaMinigame.getInstance().playerdatamanager.editData(p.getUniqueId(), "Abilities.slot " + 
	AbilityManager.getSlotOfAbility(abilityname, p) + " data", "empty");
	}
	
	
	public static void removeAllAbilityData(Player p)
	{
		for(int x = 1; x < 7; x++)
		{
			VallendiaMinigame.getInstance().playerdatamanager.editData(p.getUniqueId(), "Abilities.slot " + x + " data", "empty");	
		}
>>>>>>> second-repo/master
	}
	
	public int getPrice(String ability, Player p)
	{
		return Main.getConfig().getInt("Abilities." + ability + "." + Main.kitmanager.getKit(p).getName(false).toLowerCase() + ".price");
	}
	
	public int getCooldown(String ability, Player p)
	{
		return Main.getConfig().getInt("Abilities." + ability + "." + Main.kitmanager.getKit(p).getName(false).toLowerCase() + ".cooldown");
	}
	
	public boolean hasAbility(String ability ,Kit kit)
	{
		if(kit.getAbilities().contains(this.getAbility(ability)))
		{
			return true;
		}else return false;
	}
	
	public boolean playerHasAbility(Player p, String ability)
	{
<<<<<<< HEAD
		String slot1 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 1");
		String slot2 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 2");
		String slot3 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 3");
		String slot4 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 4");
		String slot5 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 5");
		String slot6 = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities.slot 6");
		if(slot1.equalsIgnoreCase(ability) || slot2.equalsIgnoreCase(ability) || slot3.equalsIgnoreCase(ability) || slot4.equalsIgnoreCase(ability) || slot5.equalsIgnoreCase(ability) || slot6.equalsIgnoreCase(ability))
		{
			return true;
		}
=======
		
		if(p == null)
		{
			return false;
		}
		
		if(p.hasPlayedBefore())
		{
			if(playerAbilities.get(p) == null) // Maybe optimize this
			{
				String slot1 = Main.playerdatamanager.getRawPlayerStringData(p.getUniqueId(), "Abilities.slot 1");
				String slot2 = Main.playerdatamanager.getRawPlayerStringData(p.getUniqueId(), "Abilities.slot 2");
				String slot3 = Main.playerdatamanager.getRawPlayerStringData(p.getUniqueId(), "Abilities.slot 3");
				String slot4 = Main.playerdatamanager.getRawPlayerStringData(p.getUniqueId(), "Abilities.slot 4");
				String slot5 = Main.playerdatamanager.getRawPlayerStringData(p.getUniqueId(), "Abilities.slot 5");
				String slot6 = Main.playerdatamanager.getRawPlayerStringData(p.getUniqueId(), "Abilities.slot 6");
				if(slot1.equalsIgnoreCase(ability) || slot2.equalsIgnoreCase(ability) || slot3.equalsIgnoreCase(ability) || slot4.equalsIgnoreCase(ability) || slot5.equalsIgnoreCase(ability) || slot6.equalsIgnoreCase(ability))
				{
					return true;
				}

				return false;
			}
			if(playerAbilities.get(p).contains(ability))
			{
				return true;
			}	
		}
		
>>>>>>> second-repo/master
		return false;
	}
	
	
	public void runAbility(String ability, Player p)
	{
		
		if(this.hasAbility(ability, Main.kitmanager.getKit(p)))
		{
			this.getAbility(ability).RunAbility(p);
		}else
		{
<<<<<<< HEAD
			p.sendMessage("YOU DONT HAVE THIS ABILITY!");
=======
		 Language.sendDefaultMessage(p, "You don't have the ability (" + ability + ")");
>>>>>>> second-repo/master
		}
	}
	
	
	public void addAbility(String abilityname, int abilityslot, Player p)
	{
		
		if(this.hasAbility(abilityname, Main.kitmanager.getKit(p)))
		{
			String slot = "Abilities.slot " + abilityslot;
			Ability ability = this.getAbility(abilityname);
<<<<<<< HEAD
=======
			String previousAbility = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), slot);
>>>>>>> second-repo/master
			ItemStack abilityItem = new ItemStack(Material.INK_SACK, 1 , (short)10);
			ItemMeta abilityim = abilityItem.getItemMeta();
			abilityim.setDisplayName(Utils.Colorate("&8&l" + abilityname +  " &7(" + ability.getAbilityType() + ")"));
			ArrayList<String> lore = new ArrayList<String>();
			lore.add( Utils.Colorate("&8&lSlot " + abilityslot));
			if(ability.getAbilityType() != AbilityType.PASSIVE && this.getCooldown(abilityname, p ) != 0)
			{
				lore.add(Utils.Colorate("&8&lCooldown: &7" + this.getCooldown(abilityname, p) + " seconds"));	
			}
			for (String s : ability.getDescription()) {
				lore.add(ChatColor.GRAY + s);
			}
			abilityim.setLore(lore);
			abilityItem.setItemMeta(abilityim);
			
			if(Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), slot).equalsIgnoreCase(abilityname)) //player already has that ability
			{
				return;
			}
			
			if(!Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), slot).equalsIgnoreCase("empty")) //Ability is being swapped... so disable the ability if needed.
			{
				if(VallendiaMinigame.getInstance().abilitymanager.getAbility((Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), slot))) != null ){
					VallendiaMinigame.getInstance().abilitymanager.getAbility(Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), slot)).DisableAbility(p);	
<<<<<<< HEAD
=======
					AbilityCooldown.stop(p.getUniqueId(), Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), "Abilities." + ChatColor.stripColor(Utils.Colorate(abilityItem.getItemMeta().getLore().get(0).toLowerCase()))));
>>>>>>> second-repo/master
				}
			}
			
			if(Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), slot).equalsIgnoreCase("empty"))
			{
				
				int x = -1;
				for(ItemStack item : p.getInventory().getContents())
				{
					x++;
				if(item != null)
				{
					if(item.getType() == Material.INK_SACK && item.getDurability() == 8 && item.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.Colorate("&8&lAbility Slot " + abilityslot)))
					{
							p.getInventory().setItem(x, abilityItem);
							break;
					}
				}
				}
				Main.playerdatamanager.editData(p.getUniqueId(), slot, abilityname);	
<<<<<<< HEAD
=======
				if(previousAbility != null)
				{
					AbilityManager.removeAbilityData(previousAbility, p);
					playerAbilities.get(p).remove(previousAbility);	
					abilitySlots.get(p).remove(previousAbility);
				}
				playerAbilities.get(p).add(abilityname);
				abilitySlots.get(p).put(abilityname, Integer.toString(abilityslot));
>>>>>>> second-repo/master
			}else
			{
				int x = -1;
				for(ItemStack item : p.getInventory().getContents())
				{
					x++;
				if(item != null)
				{
					if(item.getType() == Material.INK_SACK && item.getDurability() == 10 && ChatColor.stripColor(item.getItemMeta().getLore().get(0)).equalsIgnoreCase("Slot " + abilityslot))
					{
							p.getInventory().setItem(x, abilityItem);
							break;
					}
				}
				}
				Main.playerdatamanager.editData(p.getUniqueId(), slot, abilityname);
<<<<<<< HEAD
=======
				
				if(previousAbility != null)
				{
					AbilityManager.removeAbilityData(previousAbility, p);
					playerAbilities.get(p).remove(previousAbility);	
					abilitySlots.get(p).remove(previousAbility);
				}
				playerAbilities.get(p).add(abilityname);
				abilitySlots.get(p).put(abilityname, Integer.toString(abilityslot));
				
>>>>>>> second-repo/master
			}
			
			
			
			
			
<<<<<<< HEAD
=======
			
>>>>>>> second-repo/master
		}
	}
	
	public void buyAbility(String abilityname, int abilityslot ,Player p)
	{
		int points = Main.shopmanager.getPoints(p);
		int price = this.getPrice(abilityname, p);
		String slot = "Abilities.slot " + abilityslot;
		
<<<<<<< HEAD
=======
		if(AbilityManager.disabledAbilities.contains(Main.abilitymanager.getAbility(abilityname)))
		{
	        p.sendTitle(Utils.Colorate("&4&lX"), Utils.Colorate("&4&lThat ability is disabled!"), 20, 40, 40);
	        p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 1, 1);
			return;
		}
		
>>>>>>> second-repo/master
		if(points >= price)
		{
			if(!(Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), slot).equalsIgnoreCase("empty")))
	        {
	        	String abilityName = Main.playerdatamanager.getPlayerStringData(p.getUniqueId(), slot);
	        	Main.levelmanager.subtractExp(p,Main.getConfig().getInt("Abilities." + abilityName + "." + Main.kitmanager.getKit(p).getName(false).toLowerCase() + ".exp"));
	        }
			this.addAbility(abilityname, abilityslot, p);
			Main.shopmanager.subtractPoints(p, price);
			Main.playerdatamanager.addData(p.getUniqueId(), "PointsSpent", price);
<<<<<<< HEAD
	        p.sendTitle(Utils.Colorate("&3&lAbility " + abilityname), "", 20, 40, 40);
	        p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 2, 0);
	        Main.levelmanager.addEXP(p, Main.getConfig().getInt("Abilities." + abilityname + "." + Main.kitmanager.getKit(p).getName(false).toLowerCase() + ".exp"));
=======
	        p.sendTitle(Utils.Colorate("&3&l" + abilityname), "", 20, 40, 40);
	        p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 2, 0);
	        Main.levelmanager.addEXP(p, Main.getConfig().getInt("Abilities." + abilityname + "." + Main.kitmanager.getKit(p).getName(false).toLowerCase() + ".exp"));
	        
	        //EVENT FIRE BuyAbilityEvent
	        BuyAbilityEvent event = new BuyAbilityEvent(p, abilityname);
	        Bukkit.getPluginManager().callEvent(event);
>>>>>>> second-repo/master
			
		}else
		{
	        p.sendTitle(Utils.Colorate("&4&lX"), Utils.Colorate("&4&lToo expensive!"), 20, 40, 40);
	        p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 1, 1);
		}
		
	}
	
	
	public void resetAbilities(Player p)
	{
		Main.playerdatamanager.editData(p.getUniqueId(), "Abilities.slot 1", "empty");
		Main.playerdatamanager.editData(p.getUniqueId(), "Abilities.slot 2", "empty");
		Main.playerdatamanager.editData(p.getUniqueId(), "Abilities.slot 3", "empty");
		Main.playerdatamanager.editData(p.getUniqueId(), "Abilities.slot 4", "empty");
		Main.playerdatamanager.editData(p.getUniqueId(), "Abilities.slot 5", "empty");
		Main.playerdatamanager.editData(p.getUniqueId(), "Abilities.slot 6", "empty");
<<<<<<< HEAD
=======

		if(playerAbilities.containsKey(p))
		{
			playerAbilities.get(p).clear();
			abilitySlots.get(p).clear();	
		}
>>>>>>> second-repo/master
	}
	
	
	
	
	public void generateAbilityPrices()
	{
		for(Ability ability : this.getAbilities())
		{
			String path = "Abilities." + ability.getName() + ".";
			
			if(Main.getConfig().get(path + "warrior.price") == null || Main.getConfig().get(path + "warrior.cooldown", 0)	== null)
			{
				Main.getConfig().set(path + "warrior.price", 0);	
				Main.getConfig().set(path + "warrior.cooldown", 0);	
				Main.getConfig().set(path + "warrior.exp", 0);	
			}
			
			if(Main.getConfig().get(path + "assassin.price") == null || Main.getConfig().get(path + "assassin.cooldown", 0) == null)
			{
				Main.getConfig().set(path + "assassin.price", 0);	
				Main.getConfig().set(path + "assassin.cooldown", 0);	
				Main.getConfig().set(path + "assassin.exp", 0);	
			}
			
			if(Main.getConfig().get(path + "mage.price") == null || Main.getConfig().get(path + "mage.cooldown", 0) == null)
			{
				Main.getConfig().set(path + "mage.price", 0);	
				Main.getConfig().set(path + "mage.cooldown", 0);	
				Main.getConfig().set(path + "mage.exp", 0);	
			}
			
			if(Main.getConfig().get(path + "archer.price") == null || Main.getConfig().get(path + "archer.cooldown", 0)	== null)
			{
				Main.getConfig().set(path + "archer.price", 0);	
				Main.getConfig().set(path + "archer.cooldown", 0);	
				Main.getConfig().set(path + "archer.exp", 0);	
			}
			
			Main.saveConfig();
		}
	}
	
	
	
<<<<<<< HEAD
=======
	public static ItemStack locateAbilityItem(Player p, String abilityname)
	{
		ItemStack itemstack = null;
		String slot = null;
		for(int x = 1; x < 7; x++)
		{
			if(AbilityManager.getAbilitySlot(Integer.toString(x), p).equalsIgnoreCase(abilityname))
			{
				slot = Integer.toString(x);
				break;
			}
		}
		
		if(slot == null)
		{
			return null;
		}
		
		
		for(ItemStack item : p.getInventory().getContents())
		{
			if(item != null)
			{
				if(item.getType() == Material.INK_SACK && item.getDurability() == 10 && item.getItemMeta().hasLore())
				{
					for(String s : item.getItemMeta().getLore())
					{
						if(s.equalsIgnoreCase(Utils.Colorate("&8&lSlot " + slot)))
						{
							itemstack = item;
							break;
						}
					}
				}
			}
		}
		
        
		
		
		return itemstack;
		
	}
	
	
	
>>>>>>> second-repo/master
}
