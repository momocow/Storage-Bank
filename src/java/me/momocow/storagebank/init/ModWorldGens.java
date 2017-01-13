package me.momocow.storagebank.init;

import me.momocow.storagebank.server.MushroomBlueThinSpawner;
import net.minecraft.world.World;

public class ModWorldGens 
{
	private static boolean isCyclicInit = false;
	
	public static MushroomBlueThinSpawner worldGenMushroomBlueThin = null;
	
	/**
	 * Must called in the WorldEvent.Load handler
	 */
	public static void initCyclicWorldGens(World world)
	{
		worldGenMushroomBlueThin = MushroomBlueThinSpawner.get(world);
		
		if(worldGenMushroomBlueThin != null) isCyclicInit = true;
	}
	
	public static boolean isCyclicInit()
	{
		return isCyclicInit;
	}
}
