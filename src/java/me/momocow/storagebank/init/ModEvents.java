package me.momocow.storagebank.init;

import me.momocow.general.handler.MoBasicEventHandler;
import me.momocow.general.util.LogHelper;
import me.momocow.storagebank.handler.EventHandler;
import net.minecraftforge.common.MinecraftForge;

public class ModEvents {
	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		MinecraftForge.EVENT_BUS.register(new MoBasicEventHandler());
		
		LogHelper.info("Mod Events init... Done");
	}
}
