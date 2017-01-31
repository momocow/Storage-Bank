package me.momocow.storagebank.init;

import me.momocow.moapi.util.LogHelper;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.handler.EventHandler;
import net.minecraftforge.common.MinecraftForge;

public class ModEvents {
	private static LogHelper logger = StorageBank.logger;
	
	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		
		logger.info("Mod Events init... Done");
	}
}
