package me.momocow.storagebank.init;

import java.util.ArrayList;
import java.util.List;

import me.momocow.general.block.MoBush;
import me.momocow.general.client.render.MoCustomModel;
import me.momocow.general.util.LogHelper;
import me.momocow.storagebank.block.BlockMushroomBlueThin;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks 
{
	public static BlockMushroomBlueThin BlockMushroomBlueThin;

	//bush list
	private static List<Block> blocks;
	
	public static void preinit() throws Exception
	{
		blocks = new ArrayList<Block>();
		
		BlockMushroomBlueThin = (BlockMushroomBlueThin) initBlock(BlockMushroomBlueThin.class);
		
		LogHelper.info("Mod Blocks pre-init... Done");
	}
	
	private static Block initBlock(Class<? extends Block> blockClass) throws Exception
	{
		try{
			Block block = blockClass.newInstance();
			blocks.add(block);
			return block;
		}
		catch (Exception e){
			LogHelper.info("EXCEPTION: instancing fail: "+ blockClass);
			throw e;
		}
	}
	
	public static void init() throws Exception
	{
		for(Block block: blocks){
			if(block instanceof MoBush)
			{
				initBush((MoBush)block);
			}
        }
		
		LogHelper.info("Mod Blocks init... Done");
	}
	
	private static void initBush(MoBush bush) throws Exception
	{
		bush.init();
	}
	
	@SideOnly(Side.CLIENT)
    public static void initModels() 
	{
		for(Block block: blocks){
			((MoCustomModel)block).initModel(); 
        }
		
		LogHelper.info("Block Models register... Done");
	}
}