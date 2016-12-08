package me.momocow.storagebank.block;

import java.util.List;
import java.util.Random;

import me.momocow.general.block.MoCrop;
import me.momocow.storagebank.creativetab.CreativeTab;
import me.momocow.storagebank.init.ModItems;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockMushroomBlueThin extends MoCrop
{
	private static final String NAME = "MushroomBlueThin";
	
	public BlockMushroomBlueThin(){
		this.setUnlocalizedName(Reference.MOD_ID + "." + NAME);
		this.setCreativeTab(CreativeTab.MO_TAB);
		this.setRegistryName(NAME);
		
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), this.getRegistryName());
	}
	
	@Override
    protected Item getCrop()
    {
        return Item.getItemFromBlock(this);
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
