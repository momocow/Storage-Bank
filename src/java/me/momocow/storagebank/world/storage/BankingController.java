package me.momocow.storagebank.world.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import me.momocow.general.util.LogHelper;
import me.momocow.storagebank.item.IDCard;
import me.momocow.storagebank.network.S2CBroadcastPacket;
import me.momocow.storagebank.proxy.CommonProxy;
import me.momocow.storagebank.proxy.ServerProxy;
import me.momocow.storagebank.reference.ID;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
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
	
	/**
	 * Return the player with the specified card id
	 * @param uuid
	 * @return
	 */
	@Nullable
	public EntityPlayer getOwnerByID(UUID card)
	{
		for(WorldServer world: ServerProxy.getWorlds())
		{
			EntityPlayer player = world.getPlayerEntityByUUID((UUID) this.bankDB.get(card, Tables.MEMBER));
			if(player != null)
			{
				return player;
			}
		}
		
		return null;
	}
	
	/**
	 * if an entry is found with the specified uuid but null is returned, the entry is automacally remove
	 * @param uuid
	 * @return
	 */
//	@Nullable
//	public ItemStack getCardByUUID(UUID uuid)
//	{
//		ItemStack card = this.mapID2Card.get(uuid);
////		if(card == null && (this.mapID2Player.containsKey(uuid) || this.mapID2Card.containsKey(uuid)))	//remove the redundant entry
////		{
////			this.deregister(uuid);
////		}
//		
//		return card;
//	}
//	
//	/**
//	 * Return the player uuid with the specified member id
//	 * @param uuid
//	 * @return
//	 */
//	public UUID getOwnerUniqueIDByID(UUID mid)
//	{
//		EntityPlayer player = this.getOwnerByUUID(uuid);
//		return (player == null)? null: player.getUniqueID();
//	}
	
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
				//CommonProxy.broadcastChannel.sendToAll(new S2CBroadcastPacket("anno." + UnlocalizedName + ".signedUp"));
			}
			else
			{
				LogHelper.info("The ItemStack to be registered is not an IDCard!");
			}
		}
	}
//	
//	public void deregister(UUID uuid)
//	{
//		LogHelper.info("uuid: "+uuid.toString());
//		LogHelper.info("isRegister: "+this.isRegistered(uuid));
//		if(uuid != null && this.isRegistered(uuid))
//		{
//			EntityPlayer player = this.mapID2Player.remove(uuid);
//			ItemStack card = this.mapID2Card.remove(uuid);
//			
//			if(player != null)
//			{
//				player.addChatMessage(new TextComponentString(I18n.format("anno." + UnlocalizedName + ".deregistered", card.getDisplayName())));
//			}
//			
//			if(card != null)
//			{
//				card.setTagCompound(null);
//			}
//		}
//	}
//	
//	/**
//	 * Return true if the key specified by uuid exists in both maps, ID-Card map and ID-Player map and the value of the specified key are both non-null; otherwise false is returned
//	 * @param uuid
//	 * @return
//	 */
//	public boolean isRegistered(UUID uuid)
//	{
//		return this.mapID2Card.containsKey(uuid) && this.mapID2Player.containsKey(uuid);
//	}
	

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
			boolean isValid = this.mapID2Card.containsKey(index) && this.mapID2Player.containsKey(index) && this.mapID2Card.get(index) != null && this.mapID2Player.get(index) != null;
			if(!isValid)
			{
				this.mapID2Card.remove(index);
				this.mapID2Player.remove(index);	
			}
			return isValid;
		}
		
		public boolean containsKey(UUID index)
		{
			return this.validate(index);
		}
		
		public void importFrom(NBTTagCompound nbt)
		{
			
		}
		
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
