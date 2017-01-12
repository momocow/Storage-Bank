package me.momocow.storagebank.init;

import me.momocow.general.entity.MoEntityItem;
import me.momocow.storagebank.StorageBank;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities 
{
	private static int entityID = 1;
	
	public static void preInit()
	{
		EntityRegistry.registerModEntity(MoEntityItem.class, MoEntityItem.RegistryName, entityID++, StorageBank.instance, 64, 1, false);
	}
}
