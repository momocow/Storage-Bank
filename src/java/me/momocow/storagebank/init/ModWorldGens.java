package me.momocow.storagebank.init;

import me.momocow.storagebank.world.gen.cyclic.WorldGenMushroomBlueThin;
import net.minecraft.world.World;

public class ModWorldGens 
{
	private static boolean isCyclicInit = false;
	
	public static WorldGenMushroomBlueThin worldGenMushroomBlueThin = null;
	
	/**
	 * Must called in the WorldEvent.Load handler
	 */
	public static void initCyclicWorldGens(World world)
	{
		worldGenMushroomBlueThin = WorldGenMushroomBlueThin.get(world);
		
		if(worldGenMushroomBlueThin != null) isCyclicInit = true;
	}
	
	public static boolean isCyclicInit()
	{
		return isCyclicInit;
	}
}
