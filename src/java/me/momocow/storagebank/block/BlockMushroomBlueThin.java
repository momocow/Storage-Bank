package me.momocow.storagebank.block;

import java.util.Random;

import com.jcraft.jorbis.Block;

import me.momocow.general.block.MoCrop;
import me.momocow.storagebank.creativetab.CreativeTab;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMushroomBlueThin extends MoCrop{
	private static final String NAME = "MushroomBlueThin";
	protected int maxGrowthStage = 3;
	
	public BlockMushroomBlueThin(){
		this.setCreativeTab(CreativeTab.MO_TAB);
		this.setRegistryName(NAME);
		this.setUnlocalizedName(Reference.MOD_ID + "." + NAME);
	}
	
	
	/**
     * Whether this IGrowable can grow
     */
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return false;
	}

	/**
	 * Natural Growth of the plant
	 */
	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {		
	}
}
