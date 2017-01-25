package me.momocow.storagebank.item;

import java.util.List;

import me.momocow.general.entity.MoEntityItem;
import me.momocow.general.item.MoItem;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.creativetab.CreativeTab;
import me.momocow.storagebank.init.ModBlocks;
import me.momocow.storagebank.reference.ID;
import me.momocow.storagebank.reference.Reference;
import me.momocow.storagebank.server.BankingController;
import me.momocow.storagebank.tileentity.TileEntityDepoController;
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
		//Every IDCard is supposed to has its NBT data after signed up from raw card
		if(!worldIn.isRemote && !StorageBank.controller.isCardRegistered(itemStackIn))
		{
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
		}
		
		//fires on the server and sync to the client by IGuiHandler
		playerIn.openGui(StorageBank.instance, ID.Gui.GuiIDCard, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	}
	
	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
			EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) 
	{
		if(!world.isRemote && world.getBlockState(pos).getBlock() == ModBlocks.BlockDepoController)
		{
			TileEntity tile = world.getTileEntity(pos);
			if(tile instanceof TileEntityDepoController)
			{
				StorageBank.controller.addDepoList(stack, ((TileEntityDepoController)tile), player);
			}
			
			return EnumActionResult.SUCCESS;
		}
		
		return EnumActionResult.PASS;
	}

	/**
	 * <p>[SERVER] Sign up the IDCard by filling the required NBT fields</p>
	 * @see BankingController#register(EntityPlayer, ItemStack)
	 * @param itemStackIn
	 * @param worldIn
	 * @param playerIn
	 */
	public void signUp(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	{
		if(!worldIn.isRemote){
			StorageBank.controller.register(playerIn, itemStackIn);
		}
	}
	
	/**
	 * [SERVER only] Add a depository to the depoList in the IDCard's NBT data
	 * @param itemStackIn
	 * @param worldIn
	 * @param playerIn
	 */
	public static void addDepository(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, float depoX, float depoY, float depoZ, String depoName)
	{
		if(!worldIn.isRemote && itemStackIn.hasTagCompound()){
			
		}
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
