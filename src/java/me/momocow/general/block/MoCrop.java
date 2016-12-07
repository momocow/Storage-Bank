package me.momocow.general.block;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class MoCrop extends MoBush implements IGrowable
{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
	protected int maxGrowthLightness = 15;
	protected int minGrowthLightness = 8;
	/** A set of suitable soils' block IDs for the plant **/
	protected Set<Integer> soilList = new HashSet<Integer>();	
	
	public MoCrop()
	{
		this.setDefaultState(this.blockState.getBaseState().withProperty(this.getAgeProperty(), Integer.valueOf(0)));
		this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.disableStats();
   	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		super.updateTick(worldIn, pos, state, rand);
	}
	
	@Override
	protected boolean canSustainBush(IBlockState state)
    {
        return soilList.contains(Block.getIdFromBlock((state.getBlock())));
    }
	
	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
	{
        return (worldIn.getLight(pos) >= getMinGrowthLightness() || worldIn.canSeeSky(pos)) && this.canSustainBush(state);
    }
	
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return !this.isMaxAge(state);
	}
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
		return true;
    }

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) 
	{
		grow(worldIn, pos, state);
	}
	
	public void grow(World worldIn, BlockPos pos, IBlockState state)
    {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge();

        if (i > j)
        {
            i = j;
        }

        worldIn.setBlockState(pos, this.withAge(i), 2);
    }
	
	protected static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos)
    {
        float f = 1.0F;
        BlockPos blockpos = pos.down();

        for (int i = -1; i <= 1; ++i)
        {
            for (int j = -1; j <= 1; ++j)
            {
                float f1 = 0.0F;
                IBlockState iblockstate = worldIn.getBlockState(blockpos.add(i, 0, j));

                if (iblockstate.getBlock().canSustainPlant(iblockstate, worldIn, blockpos.add(i, 0, j), net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable)blockIn))
                {
                    f1 = 1.0F;

                    if (iblockstate.getBlock().isFertile(worldIn, blockpos.add(i, 0, j)))
                    {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0)
                {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos blockpos1 = pos.north();
        BlockPos blockpos2 = pos.south();
        BlockPos blockpos3 = pos.west();
        BlockPos blockpos4 = pos.east();
        boolean flag = blockIn == worldIn.getBlockState(blockpos3).getBlock() || blockIn == worldIn.getBlockState(blockpos4).getBlock();
        boolean flag1 = blockIn == worldIn.getBlockState(blockpos1).getBlock() || blockIn == worldIn.getBlockState(blockpos2).getBlock();

        if (flag && flag1)
        {
            f /= 2.0F;
        }
        else
        {
            boolean flag2 = blockIn == worldIn.getBlockState(blockpos3.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.south()).getBlock() || blockIn == worldIn.getBlockState(blockpos3.south()).getBlock();

            if (flag2)
            {
                f /= 2.0F;
            }
        }

        return f;
    }
	
	protected int getBonemealAgeIncrease(World worldIn)
    {
        return MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
    }
	
	protected Item getSeed()
    {
        return null;
    }

    protected Item getCrop()
    {
        return null;
    }
	
	/**
     * Get the Item that this Block should drop when harvested.
     */
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this.isMaxAge(state) ? this.getCrop() : this.getSeed();
    }
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		List<ItemStack> ret = super.getDrops(world, pos, state, fortune);
        int age = getAge(state);
        Random rand = world instanceof World ? ((World)world).rand : new Random();

        if (age >= getMaxAge())
        {
            for (int i = 0; i < 3 + fortune; ++i)
            {
                if (rand.nextInt(2 * getMaxAge()) <= age)
                {
                    ret.add(new ItemStack(this.getSeed(), 1, 0));
                }
            }
        }
        return ret;
	}
	
	@Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {AGE});
    }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0f, 0f, 0f, 1.0f, 1.0f, 1.0f);
    }
	
	protected PropertyInteger getAgeProperty() 
	{
		return AGE;
	}
	
	public IBlockState withAge(int age)
	{
		return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age));
	}
	
	/**
     * Convert the BlockState into the correct metadata value
     */
	@Override
    public int getMetaFromState(IBlockState state)
    {
        return this.getAge(state);
    }
	
	/**
     * Convert the given metadata into a BlockState for this Block
     */
	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.withAge(meta);
    }
	
	public int getMaxAge(){
		return 15;
	}
	
	public boolean isMaxAge(IBlockState state)
    {
        return ((Integer)state.getValue(this.getAgeProperty())).intValue() >= this.getMaxAge();
    }

	protected int getAge(IBlockState state)
    {
        return ((Integer)state.getValue(this.getAgeProperty())).intValue();
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
	
//	TODO figure out the class of the elements in colleIn and its corresponding casting into Integer (for Block ID)
//	public void removeSuitableSoilList(Collection<Object> colleIn)
//	{
//		this.soilList.removeAll(colleIn);
//	}
}
