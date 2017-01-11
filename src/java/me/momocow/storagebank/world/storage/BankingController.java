package me.momocow.storagebank.world.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import me.momocow.general.util.LogHelper;
import me.momocow.storagebank.item.IDCard;
import me.momocow.storagebank.reference.ID;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class BankingController extends WorldSavedData
{	
	private static final String UnlocalizedName = Reference.MOD_ID + ".BankingController";
	private static String DATA_ID = ID.WorldSavedData.BankingController;
	
	//Data fields
	Map<UUID, EntityPlayer> mapID2Player = new HashMap<UUID, EntityPlayer>();
	Map<UUID, ItemStack> mapID2Card = new HashMap<UUID, ItemStack>();
	
	private BankingController() {
		super(DATA_ID);
		LogHelper.info("Data[\"" + DATA_ID + "\"] is created.");
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
	 * if an entry is found with the specified uuid but null is returned, the entry is automacally remove
	 * @param uuid
	 * @return
	 */
	@Nullable
	public EntityPlayer getOwnerByUUID(UUID uuid)
	{
		EntityPlayer player = this.mapID2Player.get(uuid);
		if(player == null && (this.mapID2Player.containsKey(uuid) || this.mapID2Card.containsKey(uuid)))	//remove the redundant entry
		{
			this.deregister(uuid);
		}
		
		return player;
	}
	
	/**
	 * if an entry is found with the specified uuid but null is returned, the entry is automacally remove
	 * @param uuid
	 * @return
	 */
	@Nullable
	public ItemStack getIDCardByUUID(UUID uuid)
	{
		ItemStack card = this.mapID2Card.get(uuid);
		if(card == null && (this.mapID2Player.containsKey(uuid) || this.mapID2Card.containsKey(uuid)))	//remove the redundant entry
		{
			this.deregister(uuid);
		}
		
		return card;
	}
	
	/**
	 * if an entry is found with the specified uuid but null is returned, the entry is automacally remove
	 * @param uuid
	 * @return
	 */
	public UUID getOwnerUniqueIDByUUID(UUID uuid)
	{
		EntityPlayer player = this.getOwnerByUUID(uuid);
		return (player == null)? null: player.getUniqueID();
	}
	
	/**
	 * Use a random UUID to register the IDCard
	 * @param playerIn
	 * @param itemStackIn
	 */
	public void register(EntityPlayer playerIn, ItemStack itemStackIn)
	{
		if(playerIn != null && itemStackIn != null)
		{
			if(itemStackIn.getItem() instanceof IDCard)
			{
				this.register(playerIn, itemStackIn, MathHelper.getRandomUUID());
			}
			else
			{
				LogHelper.info("The ItemStack to be registered is not an IDCard!");
			}
		}
	}
	
	/**
	 * Use the specified UUID to register an entry for the IDCard and set up into the NBT of the IDCard
	 * @param playerIn
	 * @param itemStackIn
	 * @param uuid
	 */
	public void register(EntityPlayer playerIn, ItemStack itemStackIn, UUID uuid)
	{
		if(playerIn != null && itemStackIn != null)
		{
			if(uuid == null)
			{
				uuid = MathHelper.getRandomUUID();
			}
			
			if(itemStackIn.getItem() instanceof IDCard)
			{
				//sign the IDCard
				NBTTagCompound nbt = new NBTTagCompound();
				NBTTagList depoList = new NBTTagList();
				nbt.setUniqueId("ownerID", playerIn.getUniqueID());
				nbt.setString("ownerName", playerIn.getDisplayNameString());
				nbt.setUniqueId("cardID", uuid);
				nbt.setTag("depoList", depoList);
				itemStackIn.setTagCompound(nbt);
				
				//register to the controller entry
				this.mapID2Player.put(uuid, playerIn);
				this.mapID2Card.put(uuid, itemStackIn);
				
				//response
				playerIn.addChatMessage(new TextComponentString(I18n.format("anno." + UnlocalizedName + ".signedUp")));
			}
			else
			{
				LogHelper.info("The ItemStack to be registered is not an IDCard!");
			}
		}
	}
	
	public void deregister(UUID uuid)
	{
		if(uuid != null && this.isRegistered(uuid))
		{
			EntityPlayer player = this.mapID2Player.remove(uuid);
			ItemStack card = this.mapID2Card.remove(uuid);
			
			if(player != null)
			{
				player.addChatMessage(new TextComponentString(I18n.format("anno." + UnlocalizedName + ".deregistered")));
			}
			
			if(card != null)
			{
				card.setTagCompound(null);
			}
		}
	}
	
	/**
	 * Return true if the key specified by uuid exists in both maps, ID-Card map and ID-Player map and the value of the specified key are both non-null; otherwise false is returned
	 * @param uuid
	 * @return
	 */
	public boolean isRegistered(UUID uuid)
	{
		return this.mapID2Card.containsKey(uuid) && this.mapID2Player.containsKey(uuid) && this.mapID2Card.get(uuid) != null && this.mapID2Player.get(uuid) != null;
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
}
