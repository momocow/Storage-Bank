package me.momocow.storagebank.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import me.momocow.mobasic.util.NBTHelper;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.proxy.ClientProxy;
import me.momocow.storagebank.reference.Reference;
import me.momocow.storagebank.tileentity.TileEntityDepoCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.Constants;

public class BankingController
{	
	private static final String UnlocalizedName = "anno." + Reference.MOD_ID + ".BankingController";

	//cache the GUI-opened IDCards for more easier depo list update
	private Map<UUID, ItemStack> mapCardGuiOnOpen = new HashMap<UUID, ItemStack>();
	private Map<UUID, Map<UUID, NBTTagCompound>> mapCardDepoList = new HashMap<UUID, Map<UUID, NBTTagCompound>>();
	
	public boolean authorize(EntityPlayer player, ItemStack stack)
	{
		return this.authorize(player, stack, null, BankAction.openCardGui);
	}
	
	public boolean authorize(EntityPlayer player, TileEntityDepoCore depo) {
		return this.authorize(player, null, depo, BankAction.breakDepo);
	}
	
	public boolean authorize(EntityPlayer player, ItemStack stack, TileEntityDepoCore depo)
	{
		return this.authorize(player, stack, depo, BankAction.associate);
	}
	
	public boolean authorize(EntityPlayer player, ItemStack stack, TileEntityDepoCore depo, BankAction action)
	{
		if(!StorageBank.proxy.isClientSide && player != null && stack != null)
		{
			UUID owner = IDCardHelper.getCardOwnerId(stack);
			if(owner != null && !owner.equals(player.getUniqueID()))
			{
				switch(action)
				{
					case openCardGui:
					case associate:
						player.addChatMessage(new TextComponentTranslation(UnlocalizedName + ".notYourCard"));
						//TODO
						//owner.addChatMessage(new TextComponentTranslation(UnlocalizedName + ".cardStolen", player.getDisplayNameString(), player.getEntityWorld().provider.getDimension(), (int)player.posX, (int)player.posY, (int)player.posZ));
						break;
					default:
				}
				
				return false;
			}
		}
		
		if(player != null && depo != null)
		{
			UUID owner = depo.getOwnerId();
			if(owner!= null && !owner.equals(player.getUniqueID()))
			{
				switch(action)
				{
					case breakDepo:
					case associate:
						player.addChatMessage(new TextComponentTranslation(UnlocalizedName + ".notYourDepo"));
						//TODO
						//owner.addChatMessage(new TextComponentTranslation(UnlocalizedName + ".depoRobbed", player.getDisplayNameString(), depo.getWorld().provider.getDimension(), depo.getPos().getX(), depo.getPos().getY(), depo.getPos().getZ()));
						break;
					default:
				}
				
				return false;
			}
		}
		
		return true;
	}

	public void signUpCard(ItemStack card, EntityPlayer player)
	{
		IDCardHelper.setCardData(card, player);
		player.addChatMessage(new TextComponentTranslation(UnlocalizedName + ".signUpCard"));
	}
	
	public void destroyCard(ItemStack stack)
	{
		this.disassociateCardFromAllDepo(stack);
		
		if(IDCardHelper.validateData(stack))
		{
			if(StorageBank.proxy.isClientSide)
			{
				ClientProxy.getPlayer().addChatMessage(new TextComponentTranslation(UnlocalizedName + ".destroyCard", new TextComponentTranslation(stack.getDisplayName())));
			}
			else
			{
				//TODO
			}
		}
	}
	
	public void setCardGuiOnOpen(ItemStack stack)
	{
		if(IDCardHelper.validateData(stack))
		{
			UUID cardID = NBTHelper.getDataTag(stack, Reference.MOD_ID).getUniqueId("cardID");
			this.mapCardGuiOnOpen.put(cardID, stack);
			this.mapCardDepoList.put(cardID, IDCardHelper.getDepoMap(stack));
		}
		
	}
	
	public void updateDepoList(UUID cid, NBTTagList newList)
	{
		ItemStack card = this.mapCardGuiOnOpen.remove(cid);
		Map<UUID, NBTTagCompound> originDepo = this.mapCardDepoList.remove(cid);
		Set<UUID> diff = new HashSet<UUID>(originDepo.keySet());
		
		if(IDCardHelper.validateData(card))
		{
			if(newList != null)
			{
				NBTHelper.getDataTag(card, Reference.MOD_ID).setTag("depoList", newList);
				
				Set<UUID> newDepo = IDCardHelper.getDepoSet(card);
				diff.removeAll(newDepo);
				
				for(UUID depoID: diff)
				{
					NBTTagCompound depoNBT = originDepo.get(depoID);
					TileEntityDepoCore depo = IDCardHelper.getDepoFromDepoTag(depoNBT);
					if(depo != null)
					{
						this.disassociateCardFromDepo(card, depo);
					}
				}
			}
			else
			{
				this.disassociateCardFromAllDepo(card);
				NBTHelper.getDataTag(card, Reference.MOD_ID).setTag("depoList", new NBTTagList());
			}
		}
	}
	
	public void associate(ItemStack stack, TileEntityDepoCore depo)
	{
		if(IDCardHelper.associate(stack, depo))
		{
			depo.setAssociation(IDCardHelper.getCardId(stack));
		}
	}
	
	public void disassociateCardFromDepo(ItemStack stack, TileEntityDepoCore depo)
	{
		depo.removeAssociation(IDCardHelper.getCardId(stack));
	}
	
	public void disassociateCardFromAllDepo(ItemStack stack)
	{
		if(IDCardHelper.validateData(stack))
		{
			NBTTagList depoList = NBTHelper.getDataTag(stack, Reference.MOD_ID).getTagList("depoList", Constants.NBT.TAG_COMPOUND);
			for(int i = 0; i< depoList.tagCount(); i++)
			{
				TileEntityDepoCore depo = IDCardHelper.getDepoFromDepoTag(depoList.getCompoundTagAt(i));
				if(depo != null)
				{
					this.disassociateCardFromDepo(stack, depo);
				}
			}
		}
	}
	
	private enum BankAction 
	{
		breakDepo,
		openCardGui,
		associate
	}
}
