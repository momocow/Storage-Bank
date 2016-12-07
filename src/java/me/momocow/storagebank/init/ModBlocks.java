package me.momocow.storagebank.init;

import java.util.ArrayList;
import java.util.List;

import me.momocow.general.client.render.MoCustomModel;
import me.momocow.general.util.LogHelper;
import me.momocow.storagebank.block.BlockMushroomBlueThin;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
	public static BlockMushroomBlueThin BlockMushroomBlueThin;

	//bush list
	private static List<Block> blocks;
	
	public static void init() throws Exception
	{
		blocks = new ArrayList<Block>();
		
		BlockMushroomBlueThin = (BlockMushroomBlueThin) initBlock(BlockMushroomBlueThin.class);
		
		LogHelper.info("Mod Blocks init... Done");
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
	
	@SideOnly(Side.CLIENT)
    public static void initModels() 
	{
		for(Block block: blocks){
			((MoCustomModel)block).initModel(); 
        }
	}
}