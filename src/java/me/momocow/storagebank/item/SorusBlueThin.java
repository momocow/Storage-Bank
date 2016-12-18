package me.momocow.storagebank.item;

import me.momocow.general.item.MoItem;
import me.momocow.general.item.MoSeed;
import me.momocow.storagebank.block.BlockMushroomBlueThin;
import me.momocow.storagebank.creativetab.CreativeTab;
import me.momocow.storagebank.init.ModBlocks;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SorusBlueThin extends MoItem implements MoSeed{
	private static final String NAME = "SorusBlueThin";
	
	public SorusBlueThin(){
		this.setCreativeTab(CreativeTab.MO_TAB);
		this.setUnlocalizedName(Reference.MOD_ID + "." + NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(64);
		
		GameRegistry.register(this);
	}
	
	/**
	 * Should be called after preInit othrwise null will be returned
	 */
	public BlockMushroomBlueThin getGrowable()
	{
		return ModBlocks.BlockMushroomBlueThin;
	}
	
	@Override
	//register to the game
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if(!worldIn.isRemote) return getGrowable().manager.plantToSoilByPlayer(stack, playerIn, worldIn, pos, facing);
		return EnumActionResult.FAIL;
    }
}
