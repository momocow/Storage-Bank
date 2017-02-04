package me.momocow.storagebank.init;

import org.apache.logging.log4j.Logger;

import me.momocow.storagebank.StorageBank;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
	private static Logger logger = StorageBank.logger;
	
	public static void init()
	{
		//Recipes
		GameRegistry.addRecipe(new ItemStack(ModItems.RawCard), "ppp", "bgb", "ppp", 'p', Items.PAPER, 'b', ModItems.DriedMushroomBlueThin, 'g', Items.GOLD_NUGGET);
		
		//Smelting
		GameRegistry.addSmelting(new ItemStack(ModItems.MushroomBlueThin, 1), new ItemStack(ModItems.DriedMushroomBlueThin, 1), 0.35f);
		
		logger.info("Mod recipes init... Done");
	}
}
