package me.momocow.storagebank.server;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import me.momocow.general.util.LogHelper;
import me.momocow.storagebank.item.IDCard;
import me.momocow.storagebank.proxy.ServerProxy;
import me.momocow.storagebank.reference.ID;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class BankingController extends WorldSavedData
{	
	private static final String UnlocalizedName = Reference.MOD_ID + ".BankingController";
	private static String DATA_ID = ID.WorldSavedData.BankingController;
	
	private BankDataBase bankDB = null;
	
	/**
	 * Required Constructors
	 * for {@linkplain MapStorage#getOrLoadData(Class, String) MapStorage#getOrLoadData}
	 */
	public BankingController() {
		super(DATA_ID);
		
		this.bankDB = new BankDataBase();
		
		LogHelper.info("Data[\"" + DATA_ID + "\"] is created.");
	}
	
	/**
	 * Required Constructors
	 * for {@linkplain MapStorage#getOrLoadData(Class, String) MapStorage#getOrLoadData}
	 */
	public BankingController(String dataID)
	{
		this();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		LogHelper.info("Data[\"" + DATA_ID + "\"] is loaded from NBT.");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound p_189551_1_) {
		LogHelper.info("Data[\"" + DATA_ID + "\"] is written to NBT.");
		return null;
	}
	
	private UUID getIDFromItemStack(ItemStack stack)
	{
		return (stack.hasTagCompound() && stack.getTagCompound().hasKey(Reference.MOD_ID) && stack.getTagCompound().getCompoundTag(Reference.MOD_ID).hasKey("cardID"))? stack.getTagCompound().getCompoundTag(Reference.MOD_ID).getUniqueId("cardID"): null;
	}
	
	/**
	 * verify if the card is a legal card by the equivalent of stack's NBTTagCompound and the one specified by its carID stored in the bank db
	 * @param stack
	 * @return
	 */
	public boolean isRegistered(ItemStack stack)
	{
		UUID card = this.getIDFromItemStack(stack);
		if(card != null && this.bankDB.containsKey(card))
		{
			NBTTagCompound storedData = (NBTTagCompound)this.bankDB.get(this.getIDFromItemStack(stack), Tables.CARD);
			if(storedData != null && storedData.equals(stack.getTagCompound().getCompoundTag(Reference.MOD_ID))) return true;
		}
		
		return false;
	}
	
	/**
	 * Return the player with the specified card id
	 * @param uuid
	 * @return
	 */
	@Nullable
	public EntityPlayer getOwnerByID(ItemStack stack)
	{	
		return (this.isRegistered(stack))? ServerProxy.getPlayerList().getPlayerByUUID((UUID)this.bankDB.get(this.getIDFromItemStack(stack), Tables.MEMBER)): null;
	}
	
	/**
	 * Return the player uuid with the specified member id
	 * @param uuid
	 * @return
	 */
	@Nullable
	public UUID getOwnerUniqueIDByID(ItemStack stack)
	{
		return (this.isRegistered(stack))? (UUID)this.bankDB.get(this.getIDFromItemStack(stack), Tables.MEMBER): null;
	}
	
	/**
	 * Use the specified UUID to register an entry for the IDCard and set up into the NBT of the IDCard
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
	 * @param playerIn
	 * @param itemStackIn
	 * @param uuid
	 */
	public void register(EntityPlayer playerIn, ItemStack itemStackIn)
	{
		if(playerIn != null && itemStackIn != null)
		{
			if(itemStackIn.getItem() instanceof IDCard)
			{
				UUID uuid = MathHelper.getRandomUUID();
				
				//sign up the IDCard
				NBTTagCompound bundle = new NBTTagCompound(), data = new NBTTagCompound();
				NBTTagList depoList = new NBTTagList();
				data.setUniqueId("ownerID", playerIn.getUniqueID());
				data.setString("ownerName", playerIn.getDisplayNameString());
				data.setUniqueId("cardID", uuid);
				data.setTag("depoList", depoList);
				bundle.setTag(Reference.MOD_ID, data);
				itemStackIn.setTagCompound(bundle);
				
				//register to db
				this.bankDB.set(uuid, playerIn.getUniqueID(), data);

				//response
				playerIn.addChatMessage(new TextComponentTranslation("anno." + UnlocalizedName + ".signedUp"));
			}
			else
			{
				LogHelper.info("The ItemStack to be registered is not an IDCard!");
			}
		}
	}
	
	public void deregister(ItemStack stack)
	{
		if(this.isRegistered(stack))
		{
			EntityPlayer player = this.getOwnerByID(stack);
			this.bankDB.delete(this.getIDFromItemStack(stack));
			
			if(player != null)
			{
				player.addChatMessage(new TextComponentTranslation("anno." + UnlocalizedName + ".deregistered", stack.getDisplayName()));
			}
		}
	}
	
	/**
	 * Called by Server only
	 * @param world
	 * @return
	 */
	public static BankingController get(World world)
	{
		if(!world.isRemote)
		{
			MapStorage storage = world.getMapStorage();
			BankingController controller = (BankingController) storage.getOrLoadData(BankingController.class, DATA_ID);
			
			if(controller == null)
			{
				controller = new BankingController();
				storage.setData(DATA_ID, controller);
			}
			return controller;
		}
		return null;
	}
	
	private class BankDataBase
	{
		//Data fields
		private Map<UUID, UUID> mapID2Player = new HashMap<UUID, UUID>();
		private Map<UUID, NBTTagCompound> mapID2Card = new HashMap<UUID, NBTTagCompound>();
		
		@Nullable
		public Object get(UUID index, Tables table)
		{
			if(!this.validate(index)) return null;
			
			switch(table)
			{
				case MEMBER:
					return this.mapID2Player.get(index);
				case CARD:
					return this.mapID2Card.get(index);
				default:
			}
			
			return null;
		}
		
		/**
		 * insert/update the db entry with the specified index
		 * @param index
		 * @param player
		 * @param card
		 */
		public void set(UUID index, UUID player, NBTTagCompound card)
		{
			if(index != null && player != null && card != null)
			{
				this.mapID2Card.put(index, card);
				this.mapID2Player.put(index, player);
			}
		}
		
		/**
		 * validate if the index exists in both maps and the values are both non-null; 
		 * if false is returned, also remove the invalid index and its corresponding values
		 * @param index
		 * @return
		 */
		private boolean validate(UUID index)
		{
			boolean isValid = index != null && this.mapID2Card.containsKey(index) && this.mapID2Player.containsKey(index) && this.mapID2Card.get(index) != null && this.mapID2Player.get(index) != null;
			if(index != null && !isValid)
			{
				this.delete(index);	
			}
			return isValid;
		}
		
		/**
		 * Alias for validate
		 * @param index
		 * @return
		 */
		public boolean containsKey(UUID index)
		{
			return this.validate(index);
		}
		
		public Map.Entry<UUID, NBTTagCompound> delete(UUID index)
		{
			return new SimpleEntry<UUID, NBTTagCompound>(this.mapID2Player.remove(index), this.mapID2Card.remove(index));
		}
		
		/**
		 * init from NBT
		 * @param nbt
		 */
		public void importFrom(NBTTagCompound nbt)
		{
			
		}
		
		/**
		 * save all to NBT
		 * @return
		 */
		public NBTTagCompound export()
		{
			NBTTagCompound bundle = new NBTTagCompound();
			
			return bundle;
		}
	}
	
	private enum Tables
	{
		MEMBER,
		CARD
	}
}
