package me.momocow.storagebank.item;

import java.util.List;

import me.momocow.general.entity.MoEntityItem;
import me.momocow.general.item.MoItem;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.creativetab.CreativeTab;
import me.momocow.storagebank.init.ModBlocks;
import me.momocow.storagebank.network.C2SAuthRequestPacket;
import me.momocow.storagebank.proxy.CommonProxy;
import me.momocow.storagebank.reference.ID;
import me.momocow.storagebank.reference.Reference;
import me.momocow.storagebank.tileentity.TileEntityDepoCore;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class IDCard extends MoItem
{
	private static final String NAME = "IDCard";
	
	public IDCard()
	{
		this.setCreativeTab(CreativeTab.MO_TAB);
		this.setUnlocalizedName(Reference.MOD_ID + "." + NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(1);
		
		//register to the game
		GameRegistry.register(this);
	}
	
	/**
	 * [CLIENT only]Display the GUIScreen for the card infomation
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		if(worldIn.isRemote)
		{
			CommonProxy.guiChannel.sendToServer(new C2SAuthRequestPacket(itemStackIn, ID.GuiAuth.PlayerOpenCard));
		}
		else if(StorageBank.controller.authorize(playerIn, itemStackIn))
		{
			playerIn.openGui(StorageBank.instance, ID.Gui.GuiIDCard, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	}
	
	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
			EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) 
	{
		if(!world.isRemote && world.getBlockState(pos).getBlock() == ModBlocks.BlockDepoCore)
		{
			TileEntity tile = world.getTileEntity(pos);
			if(tile instanceof TileEntityDepoCore && StorageBank.controller.authorize(player, stack, (TileEntityDepoCore)tile))
			{
				StorageBank.controller.associate(stack, (TileEntityDepoCore)tile);
			}
			
			return EnumActionResult.SUCCESS;
		}
		
		return EnumActionResult.PASS;
	}
	
	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		String textOwner = "";
		if(stack.hasTagCompound()) textOwner = stack.getTagCompound().getCompoundTag(Reference.MOD_ID).getString("ownerName");
		tooltip.add(TextFormatting.YELLOW + I18n.format(getUnlocalizedName() + ".desc1") + ": " + textOwner);
		tooltip.add(TextFormatting.AQUA + I18n.format(getUnlocalizedName() + ".desc2"));
	}
	
	@Override
	public boolean hasCustomEntity(ItemStack stack) 
	{
		return true;
	}
	
	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack)
	{
		return new MoEntityItem(world, location, itemstack);
	}
}
