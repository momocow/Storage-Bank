package me.momocow.general.tileentity;

import me.momocow.storagebank.reference.ID;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public abstract class MoTileEntity extends TileEntity
{
	public int getGuiId()
	{
		return ID.Gui.NoGui;
	}
	
	public boolean isInit()
	{
		return true;
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
	
	public void importFromNBT(NBTTagCompound data) {}
	public NBTTagCompound exportToNBT()
	{
		return new NBTTagCompound();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey(Reference.MOD_ID))
		{
			this.importFromNBT(compound.getCompoundTag(Reference.MOD_ID));
		}
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag(Reference.MOD_ID, this.exportToNBT());
		return super.writeToNBT(compound);
	}
	
	public void init(NBTTagCompound compound, EntityPlayer placer) {
		this.importFromNBT(compound);
	}
}
