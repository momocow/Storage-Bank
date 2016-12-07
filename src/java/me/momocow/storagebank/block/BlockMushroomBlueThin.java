package me.momocow.storagebank.block;

import java.util.List;
import java.util.Random;

import me.momocow.general.block.MoCrop;
import me.momocow.storagebank.creativetab.CreativeTab;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
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
        return Item.getItemFromBlock(this.withAge(15).getBlock());
    }
	
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		super.updateTick(worldIn, pos, state, rand);
		
        if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
        {
            int i = this.getAge(state);

            if (i < this.getMaxAge())
            {
                float f = getGrowthChance(this, worldIn, pos);

                if (rand.nextInt((int)(25.0F / f) + 1) == 0)
                {
                    worldIn.setBlockState(pos, this.withAge(i + 1), 2);
                }
            }
        }
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
