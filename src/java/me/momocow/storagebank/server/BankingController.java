package me.momocow.storagebank.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import me.momocow.general.util.LogHelper;
import me.momocow.general.util.NBTHelper;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.init.ModItems;
import me.momocow.storagebank.proxy.ServerProxy;
import me.momocow.storagebank.reference.ID;
import me.momocow.storagebank.reference.Reference;
import me.momocow.storagebank.tileentity.TileEntityDepoController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.util.Constants;

public class BankingController
{	
	private static final String UnlocalizedName = Reference.MOD_ID + ".BankingController";
	
//	private Map<UUID, ItemStack>;
//	private BankDataBase bankDB = null;
//	
//	/**
//	 * Required Constructors
//	 * for {@linkplain MapStorage#getOrLoadData(Class, String) MapStorage#getOrLoadData}
//	 */
//	public BankingController() {
//		super(DATA_ID);
//		
//		this.bankDB = new BankDataBase(this);
//		
//		LogHelper.info("Data[\"" + DATA_ID + "\"] is created.");
//	}
//	
//	/**
//	 * Required Constructors
//	 * for {@linkplain MapStorage#getOrLoadData(Class, String) MapStorage#getOrLoadData}
//	 */
//	public BankingController(String dataID)
//	{
//		this();
//	}
//	
//	@Override
//	public void readFromNBT(NBTTagCompound nbt) {
//		this.bankDB.importFrom(nbt);
//		LogHelper.info("Data[\"" + DATA_ID + "\"] is loaded from NBT.");
//	}
//
//	@Override
//	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
//		LogHelper.info("Data[\"" + DATA_ID + "\"] is written to NBT.");
//		return nbt = this.bankDB.export();
//	}
//	
	public UUID getCardID(ItemStack stack)
	{
		return (stack.getItem() == ModItems.IDCard && stack.hasTagCompound() && stack.getTagCompound().hasKey(Reference.MOD_ID) && NBTHelper.hasKey(stack.getTagCompound().getCompoundTag(Reference.MOD_ID), "cardID"))? stack.getTagCompound().getCompoundTag(Reference.MOD_ID).getUniqueId("cardID"): null;
	}
//	
//	/**
//	 * verify if the card is a legal card by the equivalent of stack's NBTTagCompound and the one specified by its carID stored in the bank db
//	 * @param stack
//	 * @return
//	 */
//	public boolean isCardRegistered(ItemStack stack)
//	{
//		if(stack.getItem() == ModItems.IDCard)
//		{
//			UUID card = this.getIDFromItemStack(stack);
//			
//			if(card != null && this.bankDB.containsCard(card))
//			{
//				NBTTagCompound storedData = (NBTTagCompound)this.bankDB.get(card, Tables.CARD);
//				if(storedData != null && storedData.getUniqueId("cardID").equals(card)) return true;
//			}
//		}
//		
//		return false;
//	}
//	
//	public boolean isDepoRegistered(UUID depo)
//	{
//		return this.bankDB.containsDepo(depo);
//	}
//	
//	public boolean isDepoRegistered(World world, BlockPos depoPos)
//	{
//		TileEntityDepoController tile = this.getTileEntity(world, depoPos);
//		return (tile != null)? this.isDepoRegistered(tile.getId()): false;
//	}
//	
//	public NBTTagCompound getDataTag(ItemStack stack)
//	{
//		return (this.hasDataTag(stack))? stack.getTagCompound().getCompoundTag(Reference.MOD_ID): null;
//	}
//	
//	public boolean hasDataTag(ItemStack stack)
//	{
//		return stack != null && stack.getItem() == ModItems.IDCard && stack.hasTagCompound() && stack.getTagCompound().hasKey(Reference.MOD_ID);
//	}
//	
//	public void setDataTag(ItemStack stack, NBTTagCompound data)
//	{
//		if(stack.hasTagCompound())
//		{
//			stack.getTagCompound().setTag(Reference.MOD_ID, data);
//		}
//		else
//		{
//			NBTTagCompound dataTag = new NBTTagCompound();
//			dataTag.setTag(Reference.MOD_ID, data);
//			stack.setTagCompound(dataTag);
//		}
//	}
//	
//	/**
//	 * Return the player with the specified card id
//	 * @param uuid
//	 * @return
//	 */
//	@Nullable
//	public EntityPlayer getOwnerByCard(ItemStack stack)
//	{
//		return (this.isCardRegistered(stack))? ServerProxy.getPlayerList().getPlayerByUUID((UUID)this.getOwnerUniqueIDByCard(stack)): null;
//	}
//	
//	/**
//	 * Return the player uuid with the specified member id
//	 * @param uuid
//	 * @return
//	 */
//	@Nullable
//	public UUID getOwnerUniqueIDByCard(ItemStack stack)
//	{
//		return (this.isCardRegistered(stack))? (UUID)this.bankDB.get(this.getIDFromItemStack(stack), Tables.CARD_PLAYER): null;
//	}
//	
//	/**
//	 * Use the specified UUID to register an entry for the IDCard and set up into the NBT of the IDCard
//	 * <pre>NBTTagCompound
//	 * {
//	 *     {@link Reference#MOD_ID}: NBTTagCompound
//	 *     {
//	 *         "ownerID": UUID,
//	 *         "ownerName" : String,
//	 *         "cardID": UUID,
//	 *         "depoList": NBTTagList
//	 *         [
//	 *             #: NBTTagCompound
//	 *             {
//	 *                 "depoID": UUID,
//	 *                 "depoName": String,
//	 *                 "depoPos": int[3]
//	 *             },
//	 *             ...
//	 *         ]
//	 *     }
//	 * }</pre>
//	 * @param playerIn
//	 * @param itemStackIn
//	 * @param uuid
//	 */
//	public void register(EntityPlayer playerIn, ItemStack itemStackIn)
//	{
//		if(playerIn != null && itemStackIn != null)
//		{
//			if(itemStackIn.getItem() == ModItems.IDCard)
//			{
//				UUID uuid = MathHelper.getRandomUUID();
//				
//				//sign up the IDCard
//				NBTTagCompound bundle = new NBTTagCompound(), data = new NBTTagCompound();
//				NBTTagList depoList = new NBTTagList();
//				data.setUniqueId("ownerID", playerIn.getUniqueID());
//				data.setString("ownerName", playerIn.getDisplayNameString());
//				data.setUniqueId("cardID", uuid);
//				data.setTag("depoList", depoList);
//				bundle.setTag(Reference.MOD_ID, data);
//				itemStackIn.setTagCompound(bundle);
//				
//				//register to db
//				this.bankDB.setCard(uuid, playerIn.getUniqueID(), data);
//
//				//response
//				playerIn.addChatMessage(new TextComponentTranslation("anno." + UnlocalizedName + ".signedUp"));
//			}
//			else
//			{
//				LogHelper.info("The ItemStack to be registered is not an IDCard!");
//			}
//		}
//	}
//	
//	public void deregister(ItemStack stack)
//	{
//		if(this.isCardRegistered(stack))
//		{
//			EntityPlayer player = this.getOwnerByCard(stack);
//			this.bankDB.deleteCard(this.getIDFromItemStack(stack));
//			
//			if(player != null)
//			{
//				player.addChatMessage(new TextComponentTranslation("anno." + UnlocalizedName + ".deregistered", new TextComponentTranslation(stack.getDisplayName())));
//			}
//		}
//	}
//	
//	@Nullable
//	public TileEntityDepoController getTileEntity(World world, BlockPos pos)
//	{
//		TileEntity tile = world.getTileEntity(pos);
//		if(tile instanceof TileEntityDepoController)
//		{
//			return (TileEntityDepoController)tile;
//		}
//		
//		return null;
//	}
//	
////	@Nullable
////	public TileEntityDepoController getTileEntity(UUID depoID)
////	{
////		NBTTagCompound depo = (NBTTagCompound)this.bankDB.get(depoID, Tables.DEPO);
////		if(depo.hasKey("depoPos") && depo.hasKey("depoWorld"))
////		{
////			int[] posIntArr = depo.getIntArray("depoPos");
////			BlockPos pos = new BlockPos(posIntArr[0], posIntArr[1], posIntArr[2]);
////			World world = StorageBank.proxy.getWorld(depo.getInteger("depoWorld"));
////			
////			return this.getTileEntity(world, pos);
////		}
////		return null;
////	}
//	
//	/**
//	 * NBTTagCompound
//	 * {
//	 *     "depoID": UUID,
//	 *     "depoName": String,
//	 *     "depoPos": int[3]
//	 * }
//	 * @param card
//	 * @param world
//	 * @param depoPos
//	 */
//	public void registerDepository(TileEntityDepoController depo, EntityPlayer placer)
//	{
//		if(depo != null && depo.getId() == null && placer != null)
//		{
//			depo.setId(MathHelper.getRandomUUID());
//			depo.setOwnerId(placer.getUniqueID());
//			
//			NBTTagCompound depoTag = new NBTTagCompound();
//			depoTag.setUniqueId("depoID", depo.getId());
//			if(!depo.getDepoNameString().isEmpty()) depoTag.setString("depoName", depo.getDepoNameString());
//			depoTag.setLong("depoPos", depo.getPos().toLong());
//			depoTag.setInteger("depoWorld", depo.getWorld().provider.getDimension());
//			
//			//register on the db
//			this.bankDB.set(depo.getId(), depoTag, Tables.DEPO);
//			
//			//response
//			placer.addChatMessage(new TextComponentTranslation("anno." + UnlocalizedName + ".depoRegistered"));
//		}
//	}
//	
//	public void addDepoList(ItemStack card, TileEntityDepoController depo, EntityPlayer actioner)
//	{
//		if(this.authorization(card, depo, actioner))
//		{
//			if(depo != null && this.hasDataTag(card) && this.isDepoRegistered(depo.getId()))
//			{
//				if(NBTHelper.hasKey(this.getDataTag(card), "cardID"))
//				{
//					UUID cardID = this.getDataTag(card).getUniqueId("cardID");
//					
//					if(!this.bankDB.hasAssociated(cardID, depo.getId()))
//					{
//						//write NBT tag
//						NBTTagList depoList = (NBTTagList) this.getDataTag(card).getTag("depoList");
//						NBTTagCompound depoTag = new NBTTagCompound();
//						depoTag.setUniqueId("depoID", depo.getId());
//						if(!depo.getDepoNameString().isEmpty()) depoTag.setString("depoName", depo.getDepoNameString());
//						depoTag.setLong("depoPos", depo.getPos().toLong());
//						depoTag.setInteger("depoWorld", depo.getWorld().provider.getDimension());
//						depoList.appendTag(depoTag);
//						
//						this.bankDB.associate(cardID, depo.getId());
//					}
//					else
//					{
//						actioner.addChatMessage(new TextComponentTranslation("anno." + UnlocalizedName + ".duplicateDepo"));
//					}
//				}
//			}
//		}
//	}
//	
//	public boolean authorization(ItemStack card, TileEntityDepoController depo, EntityPlayer actioner)
//	{
//		EntityPlayer cardOwner = this.getOwnerByCard(card);
//		EntityPlayer depoOwner = ServerProxy.getPlayerList().getPlayerByUUID(depo.getOwnerId());
//		
//		EnumGuilt guilt = EnumGuilt.LEGAL;
//		boolean cond1, cond2;
//		if(cond1 = !cardOwner.equals(actioner))
//		{
//			cardOwner.addChatMessage(new TextComponentTranslation("anno." + UnlocalizedName + ".cardStolen", actioner.getDisplayName(), depo.getDepoName(), depo.getPos().getX(), depo.getPos().getY(), depo.getPos().getZ(), depoOwner.getDisplayName()));
//			guilt = EnumGuilt.THIEF;
//		}
//		
//		if(cond2 = !depoOwner.equals(actioner))
//		{
//			depoOwner.addChatMessage(new TextComponentTranslation("anno." + UnlocalizedName + ".depoRobbed", actioner.getDisplayName(), depo.getDepoName(), depo.getPos().getX(), depo.getPos().getY(), depo.getPos().getZ(), depoOwner.getDisplayName(), cardOwner.getDisplayName()));
//			if(guilt == EnumGuilt.THIEF)
//			{
//				guilt = EnumGuilt.THIEF_ROBBER;
//			}
//			else
//			{
//				guilt = EnumGuilt.ROBBER;
//			}
//		}
//		
//		this.punish(actioner, guilt);
//		
//		return !cond1 && !cond2;
//	}
//	
//	private void punish(EntityPlayer criminal, EnumGuilt guilt)
//	{
//		TextComponentTranslation crime = null;
//		switch(guilt)
//		{
//			case ROBBER:
//				crime = new TextComponentTranslation("anno." + UnlocalizedName + ".accuseOfRobbing");
//				break;
//			case THIEF:
//				crime = new TextComponentTranslation("anno." + UnlocalizedName + ".accuseOfStealing");
//				break;
//			case THIEF_ROBBER:
//				crime = new TextComponentTranslation("anno." + UnlocalizedName + ".accuseOfStealingAndRobbing");
//				break;
//			case LEGAL:
//			default:
//				return;
//		}
//		
//		criminal.addChatMessage(new TextComponentTranslation("anno." + UnlocalizedName + ".crime", crime));
//	}
//	
//	/**
//	 * <p>if the nbt does not contain key "depoList", all depos associated with the card are disassociated; the new set of depos associated with the card is defined as the depoList;
//	 * if a depo which originally belonged to the card is not in the depoList, then the relationship between the card and the depo is removed</p>
//	 * <pre>
//	 * NBTTagCompound
//	 * {
//	 *     "cardID": UUID,
//	 *     "depoList": NBTTagList
//	 *     [
//	 *         NBTTagCompound
//	 *         {
//	 *             "depoID": UUID,
//	 *             "depoName": String,	//only exists if the String is non-empty
//	 *             "depoPos": Long,
//	 *             "depoWorld": int
//	 *         }
//	 *     ]
//	 * }</pre>
//	 * @param data
//	 */
//	public void updateDepoList(NBTTagCompound data)
//	{
//		if(data != null && NBTHelper.hasKey(data, "cardID"))
//		{
//			UUID card = data.getUniqueId("cardID");
//			if(data.hasKey("depoList"))
//			{
//				@SuppressWarnings("unchecked")
//				Set<UUID> originalOldDepos = (Set<UUID>)this.bankDB.get(card, Tables.CARD_DEPOS);
//				List<UUID> oldDepos = (originalOldDepos == null)? new ArrayList<UUID>(): new ArrayList<UUID>(originalOldDepos);
//				NBTTagList newDepos = data.getTagList("depoList", Constants.NBT.TAG_COMPOUND);
//				
//				//write to NBT, automatically sync with client
//				EntityPlayer player = ServerProxy.getPlayerList().getPlayerByUUID(((UUID)(this.bankDB.get(card, Tables.CARD_PLAYER))));
//				ItemStack stack = player.getHeldItemMainhand();
//				if(stack.getItem() == ModItems.IDCard && NBTHelper.hasKey(this.getDataTag(stack), "cardID") && this.getDataTag(stack).getUniqueId("cardID").equals(card))
//				{
//					this.getDataTag(stack).setTag("depoList", newDepos);
//				}
//				else
//				{
//					//search all player's itemstack
//					for(ItemStack stackOfPlayer: player.inventoryContainer.inventoryItemStacks)
//					{
//						if(stackOfPlayer.getItem() == ModItems.IDCard && NBTHelper.hasKey(this.getDataTag(stackOfPlayer), "cardID") && this.getDataTag(stackOfPlayer).getUniqueId("cardID").equals(stackOfPlayer))
//						{
//							this.getDataTag(stackOfPlayer).setTag("depoList", newDepos);
//							break;
//						}
//					}
//				}
//				
//				if(oldDepos.isEmpty())	//no old depos, therefore directly set all new depo into the db
//				{
//					for(int i = 0; i< newDepos.tagCount(); i++)
//					{
//						NBTTagCompound depo = newDepos.getCompoundTagAt(i);
//						if(NBTHelper.hasKey(depo, "depoID"))
//						{
//							UUID depoID = depo.getUniqueId("depoID");
//							this.bankDB.setDepo(depoID, card, depo);
//						}
//					}
//				}
//				else	//update depos
//				{
//					for(int i = 0; i< newDepos.tagCount(); i++)
//					{
//						NBTTagCompound newDepo = newDepos.getCompoundTagAt(i);
//						if(NBTHelper.hasKey(newDepo, "depoID"))
//						{
//							UUID depoID = newDepo.getUniqueId("depoID");
//							if(oldDepos.contains(depoID))	//valid depo
//							{
//								oldDepos.remove(depoID);
//								this.bankDB.set(depoID, newDepo, Tables.DEPO);
//							}
//						}
//					}
//					//the rest of the oldDepo will be removed
//					for(UUID oldDepo: oldDepos)
//					{
//						this.bankDB.disassociate(card, oldDepo);
//					}
//				}
//				
//				
//			}
//		}
//	}
//	
//	@Nullable
//	public TileEntityDepoController getTileEntity(UUID depo)
//	{
//		NBTTagCompound depoInfo = (NBTTagCompound)this.bankDB.get(depo, Tables.DEPO);
//		if(depoInfo.hasKey("depoWorld") && depoInfo.hasKey("depoPos"))
//		{
//			World world = StorageBank.proxy.getWorld(depoInfo.getInteger("depoWorld"));
//			BlockPos pos = BlockPos.fromLong(depoInfo.getLong("depoPos"));
//			
//			if(world != null && pos != null)
//			{
//				TileEntity tile = world.getTileEntity(pos);
//				if(tile != null && tile instanceof TileEntityDepoController)
//				{
//					return (TileEntityDepoController)tile;
//				}
//			}
//		}
//		
//		return null;
//	}
//	
//	public void deregisterDepo(TileEntityDepoController tile)
//	{
//		this.bankDB.deleteDepo(tile.getId());
//	}
//	
//	public void dumpAll()
//	{
//		this.bankDB.dump(Tables.CARD);
//		this.bankDB.dump(Tables.CARD_DEPOS);
//		this.bankDB.dump(Tables.CARD_PLAYER);
//		this.bankDB.dump(Tables.DEPO);
//		this.bankDB.dump(Tables.DEPO_CARDS);
//	}
//	
//	public void dump(Tables table)
//	{
//		this.bankDB.dump(table);
//	}
//	
//	/**
//	 * Called by Server only
//	 * @param world
//	 * @return
//	 */
//	public static BankingController get(World world)
//	{
//		if(!world.isRemote)
//		{
//			MapStorage storage = world.getMapStorage();
//			BankingController controller = (BankingController) storage.getOrLoadData(BankingController.class, DATA_ID);
//			
//			if(controller == null)
//			{
//				controller = new BankingController();
//				storage.setData(DATA_ID, controller);
//			}
//			return controller;
//		}
//		return null;
//	}
//	
//	private class BankDataBase
//	{
//		//key: depository id
//		private Map<UUID, NBTTagCompound> mapDID2Depo = new HashMap<UUID, NBTTagCompound>();
//		private Map<UUID, Set<UUID>> mapDID2CIDs = new HashMap<UUID, Set<UUID>>();
//		
//		//key: card id
//		private Map<UUID, NBTTagCompound> mapCID2Card = new HashMap<UUID, NBTTagCompound>();
//		private Map<UUID, UUID> mapCID2Player = new HashMap<UUID, UUID>();
//		private Map<UUID, Set<UUID>> mapCID2DIDs = new HashMap<UUID, Set<UUID>>();	//cache
//		
//		private BankingController controller = null;
//		
//		public BankDataBase(BankingController ctrl) 
//		{
//			this.controller = ctrl;
//		}
//		
//		@Nullable
//		public Object get(UUID index, Tables table)
//		{
//			//index == null will be returned
//			if(!this.validate(index, table)) return null;
//			
//			switch(table)
//			{
//				case CARD_PLAYER:
//					return this.mapCID2Player.get(index);
//				case CARD:
//					return this.mapCID2Card.get(index);
//				case DEPO:
//					return this.mapDID2Depo.get(index);
//				case DEPO_CARDS:
//					return this.mapDID2CIDs.get(index);
//				case CARD_DEPOS:
//					return this.mapCID2DIDs.get(index);
//				default:
//			}
//			
//			return null;
//		}
//		
//		/**
//		 * insert/update the db entry with the specified index
//		 */
//		public void set(UUID index, Object data, Tables table)
//		{
//			if(index != null && data != null)
//			{
//				switch(table)
//				{
//					case DEPO:
//						if(data instanceof NBTTagCompound) 
//						{
//							this.mapDID2Depo.put(index, (NBTTagCompound)data);
//							
//							NBTTagCompound nbtData = (NBTTagCompound)data;
//							if(nbtData.hasKey("depoName"))
//							{
//								this.controller.getTileEntity(index).setDepoName(nbtData.getString("depoName"));
//							}
//							
//							this.controller.markDirty();
//						}
//						return;
//					case CARD_PLAYER:
//						if(data instanceof UUID) 
//						{
//							this.mapCID2Player.put(index, (UUID)data);
//							this.controller.markDirty();
//						}
//						return;
//					case CARD:
//						if(data instanceof NBTTagCompound) 
//						{
//							this.mapCID2Card.put(index, (NBTTagCompound)data);
//							this.controller.markDirty();
//						}
//						return;
//					case DEPO_CARDS:
//						if(data instanceof UUID)
//						{
//							if(this.mapDID2CIDs.get(index) == null)
//							{
//								Set<UUID> cids = new HashSet<UUID>();
//								cids.add((UUID)data);
//								this.mapDID2CIDs.put(index, cids);
//							}
//							else
//							{
//								this.mapDID2CIDs.get(index).add((UUID)data);
//								this.controller.getTileEntity(index).setAssociation((UUID)data);
//							}
//							
//							//make cache
//							if(this.mapCID2DIDs.get((UUID)data) == null)
//							{
//								Set<UUID> dids = new HashSet<UUID>();
//								dids.add(index);
//								this.mapCID2DIDs.put((UUID)data, dids);
//							}
//							else
//							{
//								this.mapCID2DIDs.get((UUID)data).add(index);
//							}
//							
//							this.controller.markDirty();
//						}
//						return;
//					default:
//						return;
//				}
//			}
//		}
//		
//		public void setCard(UUID cardID, UUID player, NBTTagCompound card)
//		{
//			if(cardID != null && player != null && card != null)
//			{
//				this.set(cardID, player, Tables.CARD_PLAYER);
//				this.set(cardID, card, Tables.CARD);
//				this.mapCID2DIDs.put(cardID, new HashSet<UUID>());
//			}
//		}
//		
//		public void setDepo(UUID depoID, UUID card, NBTTagCompound depo)
//		{
//			if(depoID != null && card != null && depo != null)
//			{
//				this.set(depoID, card, Tables.DEPO_CARDS);
//				this.set(depoID, depo, Tables.DEPO);
//			}
//		}
//		
//		public void associate(UUID card, UUID depo)
//		{
//			if(card != null && depo != null)
//			{
//				this.set(depo, card, Tables.DEPO_CARDS);
//			}
//		}
//		
//		public void delete(UUID index, Tables table)
//		{
//			if(index != null)
//			{
//				switch(table)
//				{
//					case CARD:
//						this.mapCID2Card.remove(index);
//						break;
//					case DEPO:
//						this.mapDID2Depo.remove(index);
//						break;
//					case DEPO_CARDS:
//						Set<UUID> cards = this.mapDID2CIDs.remove(index);
//						for(UUID card: new ArrayList<UUID>(cards))
//						{
//							if(this.mapCID2DIDs.get(card) != null) this.mapCID2DIDs.get(card).remove(index);
//						}
//						this.controller.getTileEntity(index).clearAssociation();
//						break;
//					case CARD_PLAYER:
//						this.mapCID2Player.remove(index);
//						break;
//					default:
//				}
//				
//				this.controller.markDirty();
//			}
//		}
//		
//		public void delete(UUID index, UUID target, Tables table)
//		{
//			if(index != null)
//			{
//				switch(table)
//				{
//					case DEPO_CARDS:
//						this.mapDID2CIDs.get(index).remove(target);
//						this.mapCID2DIDs.get(target).remove(index);
//						break;
//					default:
//				}
//				this.controller.markDirty();
//			}
//		}
//		
//		/**
//		 * delete everything related to the card
//		 * @param card
//		 */
//		public void deleteCard(UUID card)
//		{
//			if(card != null)
//			{
//				this.delete(card, Tables.CARD);
//				this.delete(card, Tables.CARD_PLAYER);
//				if(this.mapCID2DIDs.get(card) != null)
//				{
//					List<UUID> dids = new ArrayList<UUID>(this.mapCID2DIDs.get(card));
//					if(dids != null)
//					{
//						for(UUID depo: dids)
//						{
//							this.disassociate(card, depo);
//						}
//					}
//				}
//			}
//		}
//		
//		public void deleteDepo(UUID depo)
//		{
//			if(depo != null)
//			{
//				this.delete(depo, Tables.DEPO);
//				if(this.mapDID2CIDs.get(depo) != null)
//				{
//					List<UUID> cids = new ArrayList<UUID>(this.mapDID2CIDs.get(depo));
//					if(cids != null)
//					{
//						for(UUID card: cids)
//						{
//							this.disassociate(card, depo);
//							LogHelper.info(card.toString());
//						}
//					}
//				}
//			}
//		}
//		
//		/**
//		 * disassociate the depo with the card
//		 * @param card
//		 */
//		public void disassociate(UUID card, UUID depo)
//		{
//			this.delete(depo, card, Tables.DEPO_CARDS);
//		}
//		
//		/**
//		 * validate if the index exists in map and the value is non-null; 
//		 * if false is returned, also remove the invalid index and its corresponding value
//		 * @param index
//		 * @return
//		 */
//		private boolean validate(UUID index, Tables table)
//		{
//			if(index != null)
//			{
//				boolean isValid = false;
//				switch(table)
//				{
//					case CARD:
//						isValid = this.mapCID2Card.get(index) != null;
//						break;
//					case DEPO:
//						isValid = this.mapDID2Depo.get(index) != null;
//						break;
//					case DEPO_CARDS:
//						isValid = this.mapDID2CIDs.get(index) != null && !this.mapDID2CIDs.get(index).isEmpty();
//						break;
//					case CARD_PLAYER:
//						isValid = this.mapCID2Player.get(index) != null;
//						break;
//					case CARD_DEPOS:
//						isValid = this.mapCID2DIDs.get(index) != null&& !this.mapCID2DIDs.get(index).isEmpty();
//						break;
//					default:
//				}
//				
//				if(!isValid)
//				{
//					this.delete(index, table);
//				}
//				return isValid;
//			}
//			return false;
//		}
//		
//		private boolean validateCard(UUID card)
//		{
//			boolean cond1 = this.validate(card, Tables.CARD);
//			boolean cond2 = this.validate(card, Tables.CARD_PLAYER);
//			
//			if(cond1 && cond2)
//			{
//				return true;
//			}
//			
//			if(cond1 && !cond2)
//			{
//				this.delete(card, Tables.CARD);
//			}
//			else if(!cond1 && cond2)
//			{
//				this.delete(card, Tables.CARD_PLAYER);
//			}
//			
//			return false;
//		}
//		
//		/**
//		 * Alias for validate
//		 * @param index
//		 * @return
//		 */
//		public boolean containsCard(UUID card)
//		{
//			return this.validateCard(card);
//		}
//		
//		public boolean containsDepo(UUID depo)
//		{
//			return this.validate(depo, Tables.DEPO);
//		}
//		
//		public boolean hasAssociated(UUID card, UUID depo)
//		{
//			return this.hasAssociated(depo) && ((UUID)this.get(depo, Tables.DEPO_CARDS)).equals(card);
//		}
//		
//		public boolean hasAssociated(UUID depo)
//		{
//			return this.validate(depo, Tables.DEPO_CARDS);
//		}
//		
//		/**
//		 * init from NBT
//		 * @param nbt
//		 */
//		public void importFrom(NBTTagCompound nbt)
//		{
//			NBTTagList CID2Player = nbt.getTagList("CID2Player", Constants.NBT.TAG_COMPOUND),
//					CID2Card = nbt.getTagList("CID2Card", Constants.NBT.TAG_COMPOUND),
//					DID2Depo = nbt.getTagList("DID2Depo", Constants.NBT.TAG_COMPOUND),
//					DID2CIDs = nbt.getTagList("DID2CIDs", Constants.NBT.TAG_COMPOUND);
//			
//			//reset
//			this.mapCID2Card.clear();
//			this.mapCID2Player.clear();
//			this.mapDID2Depo.clear();
//			this.mapDID2CIDs.clear();
//			this.mapCID2DIDs.clear();
//			
//			//ID2Player
//			for(int i = 0; i< CID2Player.tagCount(); i++)
//			{
//				NBTTagCompound entry = CID2Player.getCompoundTagAt(i);
//				this.mapCID2Player.put(entry.getUniqueId("id"), entry.getUniqueId("data"));
//			}
//			
//			//ID2Card
//			for(int i = 0; i< CID2Card.tagCount(); i++)
//			{
//				NBTTagCompound entry = CID2Card.getCompoundTagAt(i);
//				this.mapCID2Card.put(entry.getUniqueId("id"), entry.getCompoundTag("data"));
//			}
//			
//			//DID2Depo && Depo2DID
//			for(int i = 0; i< DID2Depo.tagCount(); i++)
//			{
//				NBTTagCompound entry = DID2Depo.getCompoundTagAt(i);
//				this.mapDID2Depo.put(entry.getUniqueId("id"), entry.getCompoundTag("data"));
//			}
//			
//			//DID2CIDs & mapCID2DIDs
//			/**
//			 * "DID2CIDs": NBTTagList
//			 * [
//			 *     NBTTagCompound
//			 *     {
//			 *         "id": UUID,
//			 *         "data": NBTTagList
//			 *         [
//			 *             NBTTagCompound
//			 *             {
//			 *                 "cardID": UUID	//actually stored in two long intergers
//			 *             },
//			 *             ...
//			 *         ]
//			 *     },
//			 *     ...
//			 * ]
//			 */
//			for(int i = 0; i< DID2CIDs.tagCount(); i++)
//			{
//				NBTTagCompound entry = DID2CIDs.getCompoundTagAt(i);
//				NBTTagList cidList = entry.getTagList("data", Constants.NBT.TAG_COMPOUND);
//				Set<UUID> cids = new HashSet<UUID>();
//				for(int j = 0; j< cidList.tagCount(); j++)
//				{
//					NBTTagCompound cidData = cidList.getCompoundTagAt(j);
//					if(NBTHelper.hasKey(cidData, "cardID"))
//					{
//						cids.add(cidData.getUniqueId("cardID"));
//					}
//				}
//				this.mapDID2CIDs.put(entry.getUniqueId("id"), cids);
//				
//				for(UUID cid: new ArrayList<UUID>(cids))
//				{
//					Set<UUID> dids = this.mapCID2DIDs.get(cid);
//					if(dids != null)
//					{
//						dids.add(entry.getUniqueId("id"));
//					}
//					else
//					{
//						dids = new HashSet<UUID>();
//						dids.add(entry.getUniqueId("id"));
//						this.mapCID2DIDs.put(cid, dids);
//					}
//				}
//			}
//		}
//		
//		/**
//		 * save all to NBT
//		 * @return
//		 */
//		public NBTTagCompound export()
//		{
//			NBTTagCompound bundle = new NBTTagCompound();
//			NBTTagList CID2Player = new NBTTagList(), CID2Card = new NBTTagList(), DID2Depo = new NBTTagList(), DID2CIDs = new NBTTagList();
//			
//			//mapCID2Card
//			for(Map.Entry<UUID, NBTTagCompound> entry: this.mapCID2Card.entrySet())
//			{
//				NBTTagCompound pair = new NBTTagCompound();
//				pair.setUniqueId("id", (UUID)entry.getKey());
//				pair.setTag("data", (NBTTagCompound)entry.getValue());
//				CID2Card.appendTag(pair);
//			}
//			
//			//mapCID2Player
//			for(Map.Entry<UUID, UUID> entry: this.mapCID2Player.entrySet())
//			{
//				NBTTagCompound pair = new NBTTagCompound();
//				pair.setUniqueId("id", (UUID)entry.getKey());
//				pair.setUniqueId("data", (UUID)entry.getValue());
//				CID2Player.appendTag(pair);
//			}
//			
//			//mapDID2Depo
//			for(Map.Entry<UUID, NBTTagCompound> entry: this.mapDID2Depo.entrySet())
//			{
//				NBTTagCompound pair = new NBTTagCompound();
//				pair.setUniqueId("id", (UUID)entry.getKey());
//				pair.setTag("data", (NBTTagCompound)entry.getValue());
//				DID2Depo.appendTag(pair);
//			}
//			
//			//mapDID2CID
//			/**
//			 * "DID2CIDs": NBTTagList
//			 * [
//			 *     NBTTagCompound
//			 *     {
//			 *         "id": UUID,
//			 *         "data": NBTTagList
//			 *         [
//			 *             NBTTagCompound
//			 *             {
//			 *                 "cardID": UUID	//actually stored in two long intergers
//			 *             },
//			 *             ...
//			 *         ]
//			 *     },
//			 *     ...
//			 * ]
//			 */
//			for(Map.Entry<UUID, Set<UUID>> entry: this.mapDID2CIDs.entrySet())
//			{
//				NBTTagCompound pair = new NBTTagCompound();
//				pair.setUniqueId("id", (UUID)entry.getKey());
//				
//				NBTTagList cidList = new NBTTagList();
//				for(UUID cid: (Set<UUID>)entry.getValue())
//				{
//					NBTTagCompound cidData = new NBTTagCompound();
//					cidData.setUniqueId("cardID", cid);
//					cidList.appendTag(cidData);
//				}
//				pair.setTag("data", cidList);
//				DID2CIDs.appendTag(pair);
//			}
//			
//			//make data bundle
//			bundle.setTag("CID2Card", CID2Card);
//			bundle.setTag("CID2Player", CID2Player);
//			bundle.setTag("DID2Depo", DID2Depo);
//			bundle.setTag("DID2CIDs", DID2CIDs);
//			
//			return bundle;
//		}
//		
//		/**
//		 * utilization function
//		 */
//		public void dump(Tables table)
//		{
//			switch(table)
//			{
//				case CARD:
//					LogHelper.info("===================== StorageBank DB Dump [Table: CARD] =====================");
//					for(Entry<UUID, NBTTagCompound> entry: this.mapCID2Card.entrySet())
//					{
//						LogHelper.info("[" + ((UUID)entry.getKey()).toString() + "] -> " + ((NBTTagCompound)entry.getValue()).toString());
//					}
//					LogHelper.info("===================== StorageBank DB Dump [Table: CARD] =====================");
//					break;
//				case DEPO:
//					LogHelper.info("===================== StorageBank DB Dump [Table: DEPO] =====================");
//					for(Entry<UUID, NBTTagCompound> entry: this.mapDID2Depo.entrySet())
//					{
//						LogHelper.info("[" + ((UUID)entry.getKey()).toString() + "] -> " + ((NBTTagCompound)entry.getValue()).toString());
//					}
//					LogHelper.info("===================== StorageBank DB Dump [Table: DEPO] =====================");
//					break;
//				case DEPO_CARDS:
//					LogHelper.info("===================== StorageBank DB Dump [Table: DEPO_CARDS] =====================");
//					for(Entry<UUID, Set<UUID>> entry: this.mapDID2CIDs.entrySet())
//					{
//						LogHelper.info("[" + ((UUID)entry.getKey()).toString() + "] -> " + ((Set<UUID>)entry.getValue()).toString());
//					}
//					LogHelper.info("===================== StorageBank DB Dump [Table: DEPO_CARDS] =====================");
//					break;
//				case CARD_PLAYER:
//					LogHelper.info("===================== StorageBank DB Dump [Table: CARD_PLAYER] =====================");
//					for(Entry<UUID, UUID> entry: this.mapCID2Player.entrySet())
//					{
//						LogHelper.info("[" + ((UUID)entry.getKey()).toString() + "] -> " + ((UUID)entry.getValue()).toString());
//					}
//					LogHelper.info("===================== StorageBank DB Dump [Table: CARD_PLAYER] =====================");
//					break;
//				case CARD_DEPOS:
//					LogHelper.info("===================== StorageBank DB Dump [Table: CARD_DEPOS] =====================");
//					for(Entry<UUID, Set<UUID>> entry: this.mapCID2DIDs.entrySet())
//					{
//						LogHelper.info("[" + ((UUID)entry.getKey()).toString() + "] -> " + ((Set<UUID>)entry.getValue()).toString());
//					}
//					LogHelper.info("===================== StorageBank DB Dump [Table: CARD_DEPOS] =====================");
//					break;
//				default:
//			}
//		}
//	}
//	
//	public enum Tables
//	{
//		CARD_PLAYER,
//		CARD,
//		DEPO_CARDS,
//		CARD_DEPOS,
//		DEPO,
//	}
	
	public enum EnumGuilt
	{
		THIEF,
		ROBBER,
		THIEF_ROBBER,
		LEGAL
	}
}
