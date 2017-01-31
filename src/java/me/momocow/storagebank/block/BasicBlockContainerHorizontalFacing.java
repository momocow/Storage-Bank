package me.momocow.storagebank.block;

import me.momocow.moapi.block.MoBlockContainerHorizontalFacing;
import me.momocow.moapi.tileentity.MoTileEntity;
import me.momocow.storagebank.StorageBank;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BasicBlockContainerHorizontalFacing extends MoBlockContainerHorizontalFacing
{
	public BasicBlockContainerHorizontalFacing()
	{
		this(Material.ROCK);
	}
	
	public BasicBlockContainerHorizontalFacing(Material material)
	{
		this(Material.ROCK, Material.ROCK.getMaterialMapColor());
	}

	public BasicBlockContainerHorizontalFacing(Material material, MapColor color)
    {
        super(material, color);
    }
	
	//open the gui whan activated by the player
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		if(!playerIn.isSneaking())
		{
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile instanceof MoTileEntity && ((MoTileEntity) tile).getGuiId() > 0)
			{
				playerIn.openGui(StorageBank.instance, ((MoTileEntity) tile).getGuiId(), worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}
}
