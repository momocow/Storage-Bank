package me.momocow.storagebank.init;

import me.momocow.general.entity.item.MoEntityItem;
import me.momocow.storagebank.StorageBank;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities 
{
	private static int entityID = 1;
	
	public static void preInit()
	{
		EntityRegistry.registerModEntity(MoEntityItem.class, MoEntityItem.RegistryName, entityID++, StorageBank.instance, 64, 4, false);
	}
	
	public static void initModels()
	{
		
	}
}
