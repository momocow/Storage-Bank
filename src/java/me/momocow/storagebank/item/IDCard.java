package me.momocow.storagebank.item;

import java.util.List;
import java.util.UUID;

import me.momocow.general.item.BasicItem;
import me.momocow.storagebank.client.render.gui.GuiIDCard;
import me.momocow.storagebank.creativetab.CreativeTab;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class IDCard extends BasicItem{
	private static final String NAME = "IDCard";
	
	public IDCard(){
		this.setCreativeTab(CreativeTab.MO_TAB);
		this.setUnlocalizedName(Reference.MOD_ID + "." + NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(1);
		
		//register to the game
		GameRegistry.register(this);
	}
	
	/**
	 * Display the GUIScreen for the card infomation
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand){	
		if(worldIn.isRemote){
			NBTTagCompound nbt = null;
			//Every IDCard is supposed to has its NBT data after signed up from raw card
			if(itemStackIn.hasTagCompound()) nbt = itemStackIn.getTagCompound();
			else{
				//It's supposed to be no case coming here; just in case for unpredictable situations
				//=> Generate an empty-info NBT to display
				nbt = new NBTTagCompound();
				NBTTagList depoList = new NBTTagList();
				nbt.setUniqueId("ownerID", UUID.fromString("Invalid Card"));
				nbt.setString("ownerName", "");
				nbt.setString("cardID", "");
				nbt.setTag("depoList", depoList);
			}
			Minecraft.getMinecraft().displayGuiScreen(new GuiIDCard(nbt));
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	}
	
	/**
	 * [SERVER only] Sign up the IDCard
	 * @param itemStackIn
	 * @param worldIn
	 * @param playerIn
	 */
	public static void signUp(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn){
		if(!worldIn.isRemote && !itemStackIn.hasTagCompound()){
			NBTTagCompound nbt = new NBTTagCompound();
			NBTTagList depoList = new NBTTagList();
			nbt.setUniqueId("ownerID", EntityPlayer.getUUID(playerIn.getGameProfile()));
			nbt.setString("ownerName", playerIn.getDisplayNameString());
			nbt.setString("cardID", UUID.randomUUID().toString());
			nbt.setTag("depoList", depoList);
			itemStackIn.setTagCompound(nbt);
		}
	}
	
	/**
	 * [SERVER only] Add a depository to the depoList in the IDCard's NBT data
	 * @param itemStackIn
	 * @param worldIn
	 * @param playerIn
	 */
	public static void addDepository(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, float depoX, float depoY, float depoZ, String depoName){
		if(!worldIn.isRemote && itemStackIn.hasTagCompound()){
			//write NBT tag
			NBTTagCompound depo = new NBTTagCompound();
			NBTTagList depoList = (NBTTagList) itemStackIn.getTagCompound().getTag("depoList");
			depo.setUniqueId("depoID", MathHelper.getRandomUUID());
			depo.setString("depoName", depoName);
			depo.setIntArray("depoCoord", new int[]{(int)depoX, (int)depoY, (int)depoZ});
			depoList.appendTag(depo);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced){
		String textOwner = "";
		if(stack.hasTagCompound()) textOwner = stack.getTagCompound().getString("ownerName");
		tooltip.add(TextFormatting.YELLOW + I18n.format(getUnlocalizedName() + ".desc1") + ": " + textOwner);
		tooltip.add(TextFormatting.AQUA + I18n.format(getUnlocalizedName() + ".desc2"));
	}
}
