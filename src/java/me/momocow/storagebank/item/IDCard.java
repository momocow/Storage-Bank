package me.momocow.storagebank.item;

import java.util.List;

import me.momocow.general.item.MoItem;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.creativetab.CreativeTab;
import me.momocow.storagebank.init.ModItems;
import me.momocow.storagebank.reference.ID;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
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
		if(!itemStackIn.hasTagCompound())
		{
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
		}
		
		//fires on the server and sync to the client by IGuiHandler
		playerIn.openGui(StorageBank.instance, ID.Gui.GuiIDCard, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	}

	
	/**
	 * [SERVER] Sign up the IDCard by filling the required NBT fields
	 * NBTTagCompound
	 * {
	 *     "ownerID": UUID,
	 *     "ownerName" : String,
	 *     "cardID": UUID,
	 *     "depoList": NBTTagList
	 *     [
	 *         NBTTagCompound
	 *         {
	 *             "depoID": UUID,
	 *             "depoName": String,
	 *             "depoPos": int[3]
	 *         },
	 *         ...
	 *     ]
	 * }
	 * @param itemStackIn
	 * @param worldIn
	 * @param playerIn
	 */
	public void signUp(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	{
		if(!worldIn.isRemote){
			if(!itemStackIn.hasTagCompound()){
				NBTTagCompound nbt = new NBTTagCompound();
				NBTTagList depoList = new NBTTagList();
				nbt.setUniqueId("ownerID", playerIn.getUniqueID());
				nbt.setString("ownerName", playerIn.getDisplayNameString());
				nbt.setUniqueId("cardID", MathHelper.getRandomUUID());
				nbt.setTag("depoList", depoList);
				itemStackIn.setTagCompound(nbt);
			}
			playerIn.addChatMessage(new TextComponentString(I18n.format(ModItems.IDCard.getUnlocalizedName()+".announce.signedUp")));
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
			//write NBT tag
			NBTTagCompound depo = new NBTTagCompound();
			NBTTagList depoList = (NBTTagList) itemStackIn.getTagCompound().getTag("depoList");
			depo.setUniqueId("depoID", MathHelper.getRandomUUID());
			depo.setString("depoName", depoName);
			depo.setIntArray("depoPos", new int[]{(int)depoX, (int)depoY, (int)depoZ});
			depoList.appendTag(depo);
		}
	}
	
	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		String textOwner = "";
		if(stack.hasTagCompound()) textOwner = stack.getTagCompound().getString("ownerName");
		tooltip.add(TextFormatting.YELLOW + I18n.format(getUnlocalizedName() + ".desc1") + ": " + textOwner);
		tooltip.add(TextFormatting.AQUA + I18n.format(getUnlocalizedName() + ".desc2"));
	}
}
