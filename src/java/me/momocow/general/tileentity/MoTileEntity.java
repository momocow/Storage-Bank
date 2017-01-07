package me.momocow.general.tileentity;

import me.momocow.storagebank.reference.ID;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public abstract class MoTileEntity extends TileEntity
{
	public int getGuiId()
	{
		return ID.Gui.NoGui;
	}
	
	public int getRenderMetadata()
	{
		if (this.getWorld() == null || this.pos == BlockPos.ORIGIN)
		{
			return -1;
		}
		else
		{
			return this.getBlockMetadata();
		}
	}
}
