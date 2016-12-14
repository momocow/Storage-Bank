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
		public static int MaxSpawn = 3000;
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
		cfg.setCategoryLanguageKey(CATEGORY_GENERAL, CATEGORY_GENERAL);
		cfg.setCategoryLanguageKey(CATEGORY_MUSHROOMBLUETHIN, CATEGORY_MUSHROOMBLUETHIN);
		
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "StorageBank General configuration");
		cfg.addCustomCategoryComment(CATEGORY_MUSHROOMBLUETHIN, "StorageBank MushroomBlueThin configuration");
		MushroomBlueThin.MaxSpawn = cfg.getInt("MaxSpawn", CATEGORY_MUSHROOMBLUETHIN, 
				                               MushroomBlueThin.MaxSpawn, 0, Integer.MAX_VALUE, 
				                               "max value of blue-thin mushroooms spawn in the 21*21 chunks surrounding the player");
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
