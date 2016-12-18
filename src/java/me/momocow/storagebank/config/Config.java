package me.momocow.storagebank.config;

import java.io.File;

import me.momocow.general.util.LogHelper;
import me.momocow.storagebank.reference.Reference;
import net.minecraftforge.common.config.Configuration;

public class Config 
{
	private Configuration cfg;
	
	public static class MushroomBlueThin
	{
		public static float SpawnChanceScale = 1.0f;
		public static int SpawnCooldown = 24000;
	}
	
	private final String CONFIG_FILE = "storagebank.cfg";
	private final String CATEGORY_GENERAL = Reference.MOD_ID + ".General";
	private final String CATEGORY_MUSHROOMBLUETHIN = Reference.MOD_ID + ".MushroomBlueThin";
	
	public Config(File directory)
	{
        cfg = new Configuration(new File(directory.getPath(), CONFIG_FILE), Reference.VERSION);
	}
	
	public void initConfig()
	{
		//General category
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "StorageBank General configuration");
		cfg.setCategoryLanguageKey(CATEGORY_GENERAL, CATEGORY_GENERAL);
		
		//MBT category
		cfg.addCustomCategoryComment(CATEGORY_MUSHROOMBLUETHIN, "StorageBank MushroomBlueThin configuration");
		cfg.setCategoryLanguageKey(CATEGORY_MUSHROOMBLUETHIN, CATEGORY_MUSHROOMBLUETHIN);
		
		MushroomBlueThin.SpawnChanceScale = cfg.getFloat("SpawnChanceScale", CATEGORY_MUSHROOMBLUETHIN, 
				                               MushroomBlueThin.SpawnChanceScale, 0, Float.MAX_VALUE, 
				                               "scale value for natural spawn chance of MushroomBlueThin");
		MushroomBlueThin.SpawnCooldown = cfg.getInt("SpawnCooldown", CATEGORY_MUSHROOMBLUETHIN, 
                                                    MushroomBlueThin.SpawnCooldown, 0, Integer.MAX_VALUE, 
                                                    "time measured in ticks (0.05 seconds) between each round of spawning");
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
            LogHelper.error("Fail to init the config!" + e);
        }
        finally 
        {
            this.save();
        }
        
        LogHelper.info("Config read!");
    }
	
	public void save()
	{
		if (this.cfg.hasChanged()) 
		{
			this.cfg.save();
			LogHelper.info("Config save!");
		}
		else LogHelper.info("Nothing changed in config!");
	}
}
