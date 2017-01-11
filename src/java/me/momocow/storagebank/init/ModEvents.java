package me.momocow.storagebank.init;

import me.momocow.general.util.LogHelper;
import me.momocow.storagebank.handler.EventHandler;
import net.minecraftforge.common.MinecraftForge;

public class ModEvents {
	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		
		LogHelper.info("Mod Events init... Done");
	}
}
