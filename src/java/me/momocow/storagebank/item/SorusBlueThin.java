package me.momocow.storagebank.item;

import me.momocow.general.block.MoBush;
import me.momocow.general.item.MoItem;
import me.momocow.storagebank.creativetab.CreativeTab;
import me.momocow.storagebank.init.ModBlocks;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SorusBlueThin extends MoItem{
	private static final String NAME = "SorusBlueThin";
	private static final MoBush plantable = ModBlocks.BlockMushroomBlueThin;
	
	public SorusBlueThin(){
		this.setCreativeTab(CreativeTab.MO_TAB);
		this.setUnlocalizedName(Reference.MOD_ID + "." + NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(64);
		
		GameRegistry.register(this);
	}
	
	@Override
	//register to the game
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		playerIn.addChatComponentMessage(new TextComponentString(""+ plantable.getUnlocalizedName()));
		return EnumActionResult.PASS;
    }
}
