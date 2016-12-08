package me.momocow.general.block;

import java.util.HashSet;
import java.util.Set;

import me.momocow.general.client.render.MoCustomModel;
import me.momocow.storagebank.creativetab.CreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public abstract class MoBush extends BlockBush implements MoCustomModel
{
	protected int maxGrowthLightness = 15;
	protected int minGrowthLightness = 8;
	/** A set of suitable soils' block IDs for the plant **/
	protected Set<Integer> soilList = new HashSet<Integer>();	
	
	public MoBush()
	{
		this(Material.PLANTS);
	}
	
	protected MoBush(Material materialIn)
    {
		this(materialIn, materialIn.getMaterialMapColor());
    }

    protected MoBush(Material materialIn, MapColor mapColorIn)
    {
    	super(materialIn, mapColorIn);
    	this.setTickRandomly(true);
        this.setCreativeTab(CreativeTab.MO_TAB);
    }
	
    @Override
	protected boolean canSustainBush(IBlockState soilState)
    {
        return soilList.contains(Block.getIdFromBlock((soilState.getBlock())));
    }
	
	@Override
	public boolean canBlockStay(World worldIn, BlockPos plantablePos, IBlockState soilState)
	{
        return (worldIn.getLight(plantablePos) >= this.getMinGrowthLightness() || worldIn.canSeeSky(plantablePos)) && this.canSustainBush(soilState);
    }
	
	/**
	 * Called when the player tries to put the plantable onto a block
	 */
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos plantablePos)
    {
		return this.canBlockStay(worldIn, plantablePos, worldIn.getBlockState(plantablePos.down()));
    }
	
	public EnumActionResult plantToSoil(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing facing)
	{
		if (facing == EnumFacing.UP && playerIn.canPlayerEdit(pos.offset(facing), facing, stack) && this.canPlaceBlockAt(worldIn, pos.up()) && worldIn.isAirBlock(pos.up()))
        {
			worldIn.setBlockState(pos.up(), this.getDefaultState());
			--stack.stackSize;
			return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
	}
	
	public void initModel() 
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
	}
	
	public void setMaxGrowthLightness(int max)
	{
		this.maxGrowthLightness = Math.min(max, 15);
	}
	
	public void setMinGrowthLightness(int min)
	{
		this.minGrowthLightness = Math.max(min, 0);
	}
	
	public int getMaxGrowthLightness()
	{
		return this.maxGrowthLightness;
	}
	
	public int getMinGrowthLightness()
	{
		return this.minGrowthLightness;
	}
	
	public Set<Integer> getSuitableSoilList()
	{
		return new HashSet<Integer>(soilList);
	}

	public void setSuitableSoilList(Set<Integer> listIn)
	{
		if(listIn != null) soilList = listIn;
	}
	
	public void addSuitableSoilList(Integer blockIdIn)
	{
		soilList.add(blockIdIn);
	}
	
	public void addSuitableSoilList(Block blockIn)
	{
		this.addSuitableSoilList(Block.getIdFromBlock(blockIn));
	}
	
	public void addSuitableSoilList(IBlockState blockStateIn)
	{
		this.addSuitableSoilList(blockStateIn.getBlock());
	}
	
	public void removeSuitableSoilList(Integer blockIdIn)
	{
		this.soilList.remove(blockIdIn);
	}
	
	public void removeSuitableSoilList(Block blockIn)
	{
		this.removeSuitableSoilList(Block.getIdFromBlock(blockIn));
	}
	
	public void removeSuitableSoilList(IBlockState blockStateIn)
	{
		this.removeSuitableSoilList(blockStateIn.getBlock());
	}
}
