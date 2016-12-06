package me.momocow.general.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class MoCrop extends BlockCrops{
	public int maxGrowthLightness = 15;
	public int minGrowthLightness = 0;
	public List<Block> grndList = new ArrayList<Block>();
	
	
	public MoCrop(){
		this.setHardness(0.0F);
		this.setSoundType(SoundType.PLANT);
		this.setTickRandomly(true);
		this.disableStats();
		
		grndList.add(Blocks.DIRT);
	}

	/**
     * Whether this IGrowable can grow or not, deciding by the lightness and the block type of the ground
     */
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		Block grnd = worldIn.getBlockState(pos).getBlock();
		int lightness = worldIn.getLight(pos);
		
		if(lightness >= minGrowthLightness && lightness <= maxGrowthLightness){
			return true;
		}
		
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return false;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {		
	}
	
	public int getMaxGrowthLightness(){
		return maxGrowthLightness;
	}
	
	public int getMinGrowthLightness(){
		return minGrowthLightness;
	}
	
	public List<Block> getSuitableGroundList(){
		return grndList;
	}

}
