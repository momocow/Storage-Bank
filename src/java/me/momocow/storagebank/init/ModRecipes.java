package me.momocow.storagebank.init;

import me.momocow.general.util.LogHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
	public static void init()
	{
		//Recipes
		GameRegistry.addRecipe(new ItemStack(ModItems.RawCard, 3), "ppp", "bbb", "ppp", 'p', Items.PAPER, 'b', ModItems.DriedMushroomBlueThin);
		GameRegistry.addRecipe(new ItemStack(ModItems.RawCard, 2), "pp", "bb", "pp", 'p', Items.PAPER, 'b', ModItems.DriedMushroomBlueThin);
		GameRegistry.addRecipe(new ItemStack(ModItems.RawCard, 1), "p", "b", "p", 'p', Items.PAPER, 'b', ModItems.DriedMushroomBlueThin);
		
		//Smelting
		GameRegistry.addSmelting(new ItemStack(ModItems.MushroomBlueThin, 1), new ItemStack(ModItems.DriedMushroomBlueThin, 1), 0.35f);
		
		LogHelper.info("Mod recipes init... Done");
	}
}
