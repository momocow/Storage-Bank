package me.momocow.general.block;

import me.momocow.general.tileentity.MoTileEntity;
import me.momocow.storagebank.StorageBank;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class MoBlockContainerHorizontalFacing extends MoBlockHorizontalFacing implements ITileEntityProvider
{	
	public MoBlockContainerHorizontalFacing()
	{
		this(Material.ROCK);
	}
	
	public MoBlockContainerHorizontalFacing(Material material)
	{
		this(Material.ROCK, Material.ROCK.getMaterialMapColor());
	}

	public MoBlockContainerHorizontalFacing(Material material, MapColor color)
    {
        super(material, color);
    }
	
	@Override
	public abstract TileEntity createNewTileEntity(World worldIn, int meta);
	
	
	public boolean canDropInventory(IBlockState state)
	{
		return true;
	}
	
	public boolean canAlertBlockChange()
	{
		return true;
	}
		
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		
		if(this.canDropInventory(state) && tile instanceof IInventory)
		{
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tile);
		}
		
		if(this.canAlertBlockChange())
		{
			worldIn.updateComparatorOutputLevel(pos, this);
		}
		
		super.breakBlock(worldIn, pos, state);
	}
	
	//open the gui whan activated by the player
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		if(worldIn.isRemote)
		{
			return true;
		}
		
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
