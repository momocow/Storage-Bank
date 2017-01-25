package me.momocow.general.block;

import java.util.ArrayList;
import java.util.List;

import me.momocow.general.tileentity.MoTileEntity;
import me.momocow.general.util.NBTHelper;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.reference.Reference;
import me.momocow.storagebank.tileentity.TileEntityDepoController;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
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
	
	/**
	 * Resume the TileEntity data from the NBT of the item, or initial the TileEntity with the bank controller
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) 
	{
		//init the property FACING
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		
		if(!worldIn.isRemote)
		{
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile != null && tile instanceof MoTileEntity)
			{
				MoTileEntity moTile = (MoTileEntity)tile;
				NBTTagCompound data = new NBTTagCompound();
				if(StorageBank.controller.hasDataTag(stack))
				{
					data = StorageBank.controller.getDataTag(stack);
				}
				
				if(placer instanceof EntityPlayer && !moTile.isInit()) moTile.init(data, (EntityPlayer)placer);
			}
			else
			{
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
			}
		}
	}
	
	/**
	 * write the tile entity data to the itemstack
	 */
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) 
	{
		List<ItemStack> list = new ArrayList<ItemStack>();
		ItemStack stack = new ItemStack(Item.getItemFromBlock(this), 1);
		
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileEntityDepoController)
		{
			NBTTagCompound data = ((MoTileEntity) tile).exportToNBT();
			
			if(stack.hasTagCompound() && data.getSize() > 0)
			{
				NBTHelper.getDataTag(stack, Reference.MOD_ID).setTag("tileEntityData", data);
			}
			else
			{
				NBTHelper.createDataTag(stack, Reference.MOD_ID).setTag("tileEntityData", data);
			}
		}
		
		list.add(stack);
		return list;
	}
}
