package me.momocow.storagebank.block;

import java.util.List;

import me.momocow.general.block.MoBlockContainerHorizontalFacing;
import me.momocow.storagebank.reference.Reference;
import me.momocow.storagebank.tileentity.TileEntityDepoCore;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockDepoCore extends MoBlockContainerHorizontalFacing
{
	private static final String BLOCKNAME = "BlockDepoCore";
	private static final String TILENAME = "TileEntityDepoCore";
	
	public BlockDepoCore()
	{
		super(Material.ROCK);
		this.setUnlocalizedName(Reference.MOD_ID + "." + BLOCKNAME);
		this.setRegistryName(BLOCKNAME);
		this.setHardness(5F);
		this.setResistance(10F);
		this.setHarvestLevel("pickaxe", 2);

		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), this.getRegistryName());
		GameRegistry.registerTileEntity(TileEntityDepoCore.class, Reference.MOD_ID + "." +TILENAME);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		tooltip.add(TextFormatting.AQUA + I18n.format(this.getUnlocalizedName() + ".desc1"));
	}

	@Override
	public boolean isFullCube(IBlockState state) 
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) 
	{
		return false;
	}
	
	@Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }
	
	@Override
    public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        return true;
    }
    
	@Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }
	
	@Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
		Material mat = world.getBlockState(pos.offset(face)).getMaterial();
		
		if (mat == Material.WATER || mat == Material.LAVA)
		{
			return true;
		}
		
        return false;
    }
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDepoCore();
	}
}
