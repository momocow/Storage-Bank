package me.momocow.storagebank.block;

import me.momocow.general.block.MoBlockContainerHorizontalFacing;
import me.momocow.general.block.MoBlockHorizontalFacing;
import me.momocow.storagebank.client.render.block.RenderATM;
import me.momocow.storagebank.reference.Reference;
import me.momocow.storagebank.tileentity.TileEntityATM;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockATM extends MoBlockContainerHorizontalFacing
{
	private static final String BLOCKNAME = "BlockATM";
	private static final String TILENAME = "TileEntityATM";
	
	public BlockATM()
	{
		super(Material.ROCK);
		this.setUnlocalizedName(Reference.MOD_ID + "." + BLOCKNAME);
		this.setRegistryName(BLOCKNAME);
		this.setHardness(5F);
		this.setResistance(10F);
		this.setHarvestLevel("pickaxe", 2);

		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), this.getRegistryName());
		GameRegistry.registerTileEntity(TileEntityATM.class, Reference.MOD_ID + "." +TILENAME);
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public void initModel() {
		super.initModel();
		
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(new IProperty[] {MoBlockHorizontalFacing.FACING}).build());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityATM.class, new RenderATM());
	}
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityATM();
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) 
	{
		
	}
	
	/**
	 * 
	 */
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
	{
	}
}
