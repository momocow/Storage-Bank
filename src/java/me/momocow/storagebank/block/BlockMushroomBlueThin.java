package me.momocow.storagebank.block;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import me.momocow.general.block.MoCrop;
import me.momocow.storagebank.creativetab.CreativeTab;
import me.momocow.storagebank.init.ModItems;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockMushroomBlueThin extends MoCrop
{
	private static final String NAME = "MushroomBlueThin";
	private static final AxisAlignedBB[] AABB = new AxisAlignedBB[]
			{
					new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 0.5D, 0.8D),
					new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.9D, 0.9D)
			};
	
	public BlockMushroomBlueThin(){
		super();
		this.setUnlocalizedName(Reference.MOD_ID + "." + NAME);
		this.setCreativeTab(CreativeTab.MO_TAB);
		this.setRegistryName(NAME);
		
		GameRegistry.register(this);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		if(this.getAge(state) == this.getMaxAge()) return AABB[1];
		return AABB[0];
	}
	
	@Override
	public int getMaxGrowthLightness()
	{
		return 12;
	}
	
	@Override
	public int getMinGrowthLightness()
	{
		return 0;
	}
	
	@Override
	public Set<Integer> getSuitableSoilList()
	{
		Set<Integer> ret = new HashSet<Integer>();
		ret.add(Block.getIdFromBlock(Blocks.FARMLAND));
		ret.add(Block.getIdFromBlock(Blocks.DIRT));
		ret.add(Block.getIdFromBlock(Blocks.COBBLESTONE));
		ret.add(Block.getIdFromBlock(Blocks.STONE));
		ret.add(Block.getIdFromBlock(Blocks.LOG));
		ret.add(Block.getIdFromBlock(Blocks.LOG2));
		ret.add(Block.getIdFromBlock(Blocks.GRASS));
		return ret;
	}
	
	@Override
    protected Item getCrop()
    {
        return ModItems.MushroomBlueThin;
    }
	
	@Override
    protected Item getSeed()
    {
        return ModItems.SorusBlueThin;
    }
	
	@Override
	public int quantityDropped(Random random)
    {
		int i = random.nextInt(100);
		if(i == 0)	//1%
		{
			return 10;
		}
		else if( i < 6)	//5%
		{
			return 5;
		}
		else if(i < 26)	//20%
		{
			return 3;
		}
		
        return 1;	//74%
    }
	
	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(TextFormatting.AQUA + I18n.format(getUnlocalizedName() + ".desc1"));
	}

//	@Override
//	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
//		return false;
//	}

//	@Override
//	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {	
//	}
}
