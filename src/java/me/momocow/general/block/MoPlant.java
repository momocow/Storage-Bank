package me.momocow.general.block;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import me.momocow.storagebank.creativetab.CreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class MoPlant extends BlockBush implements IGrowable
{
	public int maxGrowthLightness = 15;
	public int minGrowthLightness = 8;
	/** A set of suitable soils' block IDs for the plant **/
	public Set<Integer> soilList = new HashSet<Integer>();	
	
	public MoPlant()
	{
		this.setCreativeTab(CreativeTab.MO_TAB);
		this.setTickRandomly(true);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0f, 0f, 0f, 1.0f, 1.0f, 1.0f);
    }
	
	@Override
	protected boolean canSustainBush(IBlockState state)
    {
        return soilList.contains(Block.getIdFromBlock((state.getBlock())));
    }
	
	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
	{
        Block soil = worldIn.getBlockState(pos.down()).getBlock();
        return (worldIn.getLight(pos) >= getMinGrowthLightness() || worldIn.canSeeSky(pos)) && soilList.contains(Block.getIdFromBlock(soil));
    }
	
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return false;
	}
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
		return true;
    }

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
		
    }

	public void setMaxGrowthLightness(int max)
	{
		this.maxGrowthLightness = max;
	}
	
	public void setMinGrowthLightness(int min)
	{
		this.minGrowthLightness = min;
	}
	
	public int getMaxGrowthLightness()
	{
		return this.maxGrowthLightness;
	}
	
	public int getMinGrowthLightness()
	{
		return this.minGrowthLightness;
	}
	
	public Set<Integer> getSuitableGroundList()
	{
		return soilList;
	}

}
