package me.momocow.storagebank.config;

import java.io.File;

import me.momocow.mobasic.util.LogHelper;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.reference.Reference;
import net.minecraftforge.common.config.Configuration;

public class Config 
{
	private Configuration cfg;
	private static LogHelper logger = StorageBank.logger;
		
	public static class MushroomBlueThin
	{
		public static float SpawnChanceScale = 1.0f;
		public static int SpawnCooldown = 24000;
	}
	
	public static class DedicatedServer
	{
		public static float MaxMilliSecPerTick = 50f;
	}
	
	private final String CONFIG_FILE = "storagebank.cfg";
	private final String CATEGORY_GENERAL = Reference.MOD_ID + ".General";
	private final String CATEGORY_MUSHROOMBLUETHIN = Reference.MOD_ID + ".MushroomBlueThin";
	private final String CATEGORY_DEDICATEDSERVER = Reference.MOD_ID + ".DedicatedServer";
	
	public Config(File directory)
	{
        cfg = new Configuration(new File(directory.getPath(), CONFIG_FILE), Reference.VERSION);
	}
	
	public void initConfig()
	{
		//General category
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "StorageBank General Configuration");
		cfg.setCategoryLanguageKey(CATEGORY_GENERAL, CATEGORY_GENERAL);
		
		//MBT category
		cfg.addCustomCategoryComment(CATEGORY_MUSHROOMBLUETHIN, "StorageBank MushroomBlueThin Configuration");
		cfg.setCategoryLanguageKey(CATEGORY_MUSHROOMBLUETHIN, CATEGORY_MUSHROOMBLUETHIN);
		
		MushroomBlueThin.SpawnChanceScale = cfg.getFloat("SpawnChanceScale", CATEGORY_MUSHROOMBLUETHIN, 
				MushroomBlueThin.SpawnChanceScale, 0, Float.MAX_VALUE, 
				"scale value for natural spawn chance of MushroomBlueThin");
		MushroomBlueThin.SpawnCooldown = cfg.getInt("SpawnCooldown", CATEGORY_MUSHROOMBLUETHIN, 
				MushroomBlueThin.SpawnCooldown, 0, Integer.MAX_VALUE, 
				"time measured in ticks (0.05 seconds) between each round of spawning");
		
		//Dedicated Server category
		cfg.addCustomCategoryComment(CATEGORY_DEDICATEDSERVER, "StorageBank Dedicated Sevrer Configuration");
		cfg.setCategoryLanguageKey(CATEGORY_DEDICATEDSERVER, CATEGORY_DEDICATEDSERVER);
		
		DedicatedServer.MaxMilliSecPerTick = cfg.getFloat("MaxMilliSecPerTick", CATEGORY_DEDICATEDSERVER, DedicatedServer.MaxMilliSecPerTick, 0, Float.MAX_VALUE, 
				"max time in milliseconds to identify if a server is overloading or not");
	}
	
	public void read() 
	{
        try
        {
            this.cfg.load();
            this.initConfig();
        }
        catch (Exception e)
        {
        	logger.error("Fail to init the config!" + e);
        }
        finally 
        {
            this.save();
        }
        
        logger.info("Config read!");
    }
	
	public void save()
	{
		if (this.cfg.hasChanged()) 
		{
			this.cfg.save();
			logger.info("Config save!");
		}
		else logger.info("Nothing changed in config!");
	}
}
