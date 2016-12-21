package me.momocow.storagebank.init;

import me.momocow.storagebank.worldgen.circulation.WorldGenMushroomBlueThin;
import net.minecraft.world.World;

public class ModWorldGens 
{
	public static final class Circulation
	{
		public static WorldGenMushroomBlueThin WorldGenMushroomBlueThin;
		
		/**
		 * must be called after server starts
		 */
		public static void init(World world)
		{
			WorldGenMushroomBlueThin = me.momocow.storagebank.worldgen.circulation.WorldGenMushroomBlueThin.get(world);
		}
	}
}
