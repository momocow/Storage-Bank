package me.momocow.storagebank.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import me.momocow.moapi.util.NBTHelper;
import me.momocow.storagebank.init.ModItems;
import me.momocow.storagebank.proxy.ServerProxy;
import me.momocow.storagebank.reference.Reference;
import me.momocow.storagebank.tileentity.TileEntityDepoCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

/**
 * <p>IDCard NBT data decoder and helper for {@link BankingController}</p>
 * <pre>NBTTagCompound
 * {
 *     {@link Reference#MOD_ID}: NBTTagCompound
 *     {
 *         "ownerID": UUID,
 *         "ownerName" : String,
 *         "cardID": UUID,
 *         "depoList": NBTTagList
 *         [
 *             #: NBTTagCompound
 *             {
 *                 "depoID": UUID,
 *                 "depoName": String,
 *                 "depoPos": int[3]
 *             },
 *             ...
 *         ]
 *     }
 * }</pre>
 * @author MomoCow
 */
public final class IDCardHelper
{
	private static final String modid = Reference.MOD_ID;
	
	//stack is not null
	//stack is ID card
	//stack has data tag
	//stack has required data to be an IDCard
	public static boolean validateData(ItemStack stack)
	{
		if(isCard(stack))
		{
			if(stack.hasTagCompound())
			{
				NBTTagCompound data = stack.getTagCompound().getCompoundTag(modid);
				if(data != null)
				{
					if(data.getUniqueId("cardID") != null
							&& data.getUniqueId("ownerID") != null
							&& data.getString("ownerName") != null
							&& data.getTagList("depoList", Constants.NBT.TAG_COMPOUND) != null)
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public static boolean validateData(NBTTagCompound data)
	{
		if(data != null)
		{
			if(data.getUniqueId("cardID") != null
					&& data.getUniqueId("ownerID") != null
					&& data.getString("ownerName") != null
					&& data.getTagList("depoList", Constants.NBT.TAG_COMPOUND) != null)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isCard(ItemStack stack)
	{
		return (stack != null) && stack.getItem() == ModItems.IDCard;
	}
	
	public static UUID getCardOwnerId(ItemStack stack)
	{
		if(validateData(stack))
		{
			NBTTagCompound dataTag = NBTHelper.getDataTag(stack, modid);
			if(NBTHelper.hasKey(dataTag, "ownerID"))
			{
				return dataTag.getUniqueId("ownerID");
			}
		}
		
		return null;
	}
	
	public static UUID getCardId(ItemStack stack)
	{
		if(validateData(stack))
		{
			NBTTagCompound dataTag = NBTHelper.getDataTag(stack, modid);
			if(NBTHelper.hasKey(dataTag, "cardID"))
			{
				return dataTag.getUniqueId("cardID");
			}
		}
		
		return null;
	}
	
	public static void setCardData(ItemStack card, EntityPlayer player)
	{
		if(isCard(card) && player != null)
		{
			//sign up the IDCard
			NBTTagCompound bundle = new NBTTagCompound(), data = new NBTTagCompound();
			NBTTagList depoList = new NBTTagList();
			data.setUniqueId("ownerID", player.getUniqueID());
			data.setString("ownerName", player.getDisplayNameString());
			data.setUniqueId("cardID", MathHelper.getRandomUUID());
			data.setTag("depoList", depoList);
			bundle.setTag(Reference.MOD_ID, data);
			card.setTagCompound(bundle);
		}
	}
	
	public static boolean isDepoAssociated(ItemStack stack, TileEntityDepoCore depo)
	{
		if(validateData(stack) && depo != null)
		{
			NBTTagList list = NBTHelper.getDataTag(stack, Reference.MOD_ID).getTagList("depoList", Constants.NBT.TAG_COMPOUND);
			for(int i = 0; i< list.tagCount(); i++)
			{
				if(list.getCompoundTagAt(i).getUniqueId("depoID").equals(depo.getId())) return true;
			}
		}
		return false;
	}
	
	public static boolean associate(ItemStack stack, TileEntityDepoCore depo)
	{
		if(IDCardHelper.validateData(stack) && depo != null && !IDCardHelper.isDepoAssociated(stack, depo))
		{
			NBTTagList list = NBTHelper.getDataTag(stack, Reference.MOD_ID).getTagList("depoList", Constants.NBT.TAG_COMPOUND);
			NBTTagCompound depoInfo = new NBTTagCompound();
			depoInfo.setUniqueId("depoID", depo.getId());
			if(!depo.getDepoNameString().isEmpty()) depoInfo.setString("depoName", depo.getDepoNameString());
			depoInfo.setInteger("depoWorld", depo.getWorld().provider.getDimension());
			depoInfo.setLong("depoPos", depo.getPos().toLong());
			list.appendTag(depoInfo);
			return true;
		}
		return false;
	}
	
	public static Map<UUID, NBTTagCompound> getDepoMap(ItemStack stack)
	{
		Map<UUID, NBTTagCompound> ret = new HashMap<UUID, NBTTagCompound>();
		if(validateData(stack))
		{
			NBTTagList depoList = NBTHelper.getDataTag(stack, modid).getTagList("depoList", Constants.NBT.TAG_COMPOUND);
			for(int i = 0; i< depoList.tagCount(); i++)
			{
				NBTTagCompound depo = depoList.getCompoundTagAt(i);
				ret.put(depo.getUniqueId("depoID"), depo);
			}
		}
		
		return ret;
	}
	
	public static Set<UUID> getDepoSet(ItemStack stack)
	{
		Set<UUID> ret = new HashSet<>();
		if(validateData(stack))
		{
			NBTTagList depoList = NBTHelper.getDataTag(stack, modid).getTagList("depoList", Constants.NBT.TAG_COMPOUND);
			for(int i = 0; i< depoList.tagCount(); i++)
			{
				ret.add(depoList.getCompoundTagAt(i).getUniqueId("depoID"));
			}
		}
		
		return ret;
	}
	
	public static TileEntityDepoCore getDepoFromDepoTag(NBTTagCompound depoTag)
	{
		if(depoTag.hasKey("depoWorld") && depoTag.hasKey("depoPos"))
		{
			World world = ServerProxy.getWorld(depoTag.getInteger("depoWorld"));
			BlockPos pos = BlockPos.fromLong(depoTag.getLong("depoPos"));
			
			if(world != null)
			{
				TileEntity tile = world.getTileEntity(pos);
				if(tile instanceof TileEntityDepoCore)
				{
					return (TileEntityDepoCore) tile;
				}
			}
		}
		
		return null;
	}
}
