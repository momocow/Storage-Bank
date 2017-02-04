package me.momocow.storagebank.init;

import org.apache.logging.log4j.Logger;

import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.handler.EventHandler;
import net.minecraftforge.common.MinecraftForge;

public class ModEvents {
	private static Logger logger = StorageBank.logger;
	
	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		
		logger.info("Mod Events init... Done");
	}
}
