package me.momocow.storagebank.tileentity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import me.momocow.general.tileentity.MoTileEntity;
import me.momocow.general.util.NBTHelper;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.server.BankingController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.Constants;

public class TileEntityDepoController extends MoTileEntity
{
	private BankingController controller = StorageBank.controller;
	
	private String depoName = "";
	private UUID depoID;
	private UUID ownerID;
	private Set<UUID> associatedCards = new HashSet<UUID>();
	
	public ITextComponent getDepoName()
	{
		return (this.depoName.isEmpty())? new TextComponentTranslation("gui.storagebank.GuiIDCard.defaultDepoName"): new TextComponentString(this.depoName);
	}
	
	public String getDepoNameString()
	{
		return this.depoName;
	}
	
	public void setDepoName(String n)
	{
		if(n != null) this.depoName = n;
	}
	
	public UUID getId()
	{
		return this.depoID;
	}
	
	public void setId(UUID did)
	{
		if(this.depoID == null) this.depoID = did;
	}
	
	public UUID getOwnerId()
	{
		return this.ownerID;
	}
	
	public void setOwnerId(UUID oid)
	{
		if(this.ownerID == null) this.ownerID = oid;
	}
	
	public void setAssociation(UUID card)
	{
		if(card != null)
		{
			this.associatedCards.add(card);
		}
	}
	
	public void removeAssociation(UUID card)
	{
		if(card != null)
		{
			this.associatedCards.remove(card);
		}
	}
	
	public void clearAssociation()
	{
		this.associatedCards.clear();
	}
	
	@Override
	public boolean isInit() {
		return this.depoID != null && ownerID != null;
	}
	
	@Override
	public void init(NBTTagCompound compound, EntityPlayer placer) {
		if(compound != null && placer != null)
		{
			//import from NBT
			super.init(compound, placer);
			
			//validated by bank controller
			if(!this.controller.isDepoRegistered(this.depoID))
			{
				this.controller.registerDepository(this, placer);
			}
		}
	}
	
//	should the tile entity refreshes when new blockstate is applied to the position
//	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)

	@Override
	public void importFromNBT(NBTTagCompound data) {		
		if(data != null)
		{
			this.associatedCards.clear();
			
			if(NBTHelper.hasKey(data, "depoID"))
			{
				this.depoID = data.getUniqueId("depoID");
			}
			
			if(data.hasKey("depoName"))
			{
				this.depoName = data.getString("depoName");
			}
			
			if(NBTHelper.hasKey(data, "ownerID"))
			{
				this.ownerID = data.getUniqueId("ownerID");
			}
			
			if(data.hasKey("associatedCards"))
			{
				NBTTagList cardList = data.getTagList("associatedCards", Constants.NBT.TAG_COMPOUND);
				for(int i = 0; i< cardList.tagCount(); i++)
				{
					NBTTagCompound cid = cardList.getCompoundTagAt(i);
					if(NBTHelper.hasKey(cid, "cardID")) this.associatedCards.add(cid.getUniqueId("cardID"));
				}
			}
		}
	}
	
	@Override
	public NBTTagCompound exportToNBT() {
		NBTTagCompound SBTileData = new NBTTagCompound();
		SBTileData.setString("depoName", this.depoName);
		if(this.isInit()) 
		{
			SBTileData.setUniqueId("depoID", this.depoID);
			SBTileData.setUniqueId("ownerID", this.ownerID);
		}
		NBTTagList cardList = new NBTTagList();
		for(UUID cid: new ArrayList<UUID>(this.associatedCards))
		{
			NBTTagCompound cardID = new NBTTagCompound();
			cardID.setUniqueId("cardID", cid);
			cardList.appendTag(cardID);
		}
		SBTileData.setTag("associatedCards", cardList);
		
		return SBTileData;
	}
}
