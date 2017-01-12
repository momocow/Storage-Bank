package me.momocow.storagebank.item;

import java.util.List;

import me.momocow.general.item.MoItem;
import me.momocow.storagebank.creativetab.CreativeTab;
import me.momocow.storagebank.init.ModItems;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RawCard extends MoItem{
	private static final String NAME = "RawCard";
	
	public RawCard(){
		this.setCreativeTab(CreativeTab.MO_TAB);
		this.setUnlocalizedName(Reference.MOD_ID + "." + NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(64);
		
		//register to the game
		GameRegistry.register(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		itemStackIn.stackSize--;
		ItemStack toSign = new ItemStack(ModItems.IDCard, 1);
		if(!worldIn.isRemote)
		{
			ModItems.IDCard.signUp(toSign, worldIn, playerIn);	
		}
		playerIn.inventory.addItemStackToInventory(toSign);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced){
		tooltip.add(TextFormatting.AQUA + I18n.format(getUnlocalizedName() + ".desc1"));
	}
}
