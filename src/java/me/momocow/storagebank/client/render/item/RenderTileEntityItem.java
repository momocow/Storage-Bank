package me.momocow.storagebank.client.render.item;

import me.momocow.storagebank.init.ModBlocks;
import me.momocow.storagebank.tileentity.TileEntityATM;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class RenderTileEntityItem extends TileEntityItemStackRenderer
{
	private static TileEntity ATM = new TileEntityATM();
	
	@Override
	public void renderByItem(ItemStack itemStackIn) 
	{
		Block block = Block.getBlockFromItem(itemStackIn.getItem());
		
		if(block == ModBlocks.BlockATM)
		{
			TileEntityRendererDispatcher.instance.renderTileEntityAt(ATM, 0D, 0D, 0D, 0F);
		}
		else
		{
			super.renderByItem(itemStackIn);
		}
	}
}
