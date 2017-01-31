package me.momocow.storagebank.tileentity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import me.momocow.general.tileentity.MoTileEntity;
import me.momocow.general.util.NBTHelper;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.reference.ID;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class TileEntityDepoCore extends MoTileEntity
{	
	public TileEntityDepoCore() {
		super(Reference.MOD_ID);
	}

	private String depoName = "";
	private UUID depoID = MathHelper.getRandomUUID();
	private UUID ownerID;
	private Set<UUID> associatedCards = new HashSet<UUID>();
	
	@Override
	public int getGuiId() {
		return ID.Gui.GuiDepoCore;
	}
	
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
		this.depoName = n;
		this.markDirty();
	}
	
	public UUID getId()
	{
		return this.depoID;
	}
	
	public UUID getOwnerId()
	{
		return this.ownerID;
	}
	
	public void setOwner(EntityPlayer player)
	{
		this.ownerID = player.getUniqueID();
		this.markDirty();
	}

	public void setAssociation(UUID card)
	{
		if(card != null)
		{
			this.associatedCards.add(card);
			this.markDirty();
		}
	}
	
	public boolean isAssociated(UUID card)
	{
		if(card != null)
		{
			return this.associatedCards.contains(card);
		}
		return false;
	}
	
	public void removeAssociation(UUID card)
	{
		if(card != null)
		{
			this.associatedCards.remove(card);
			this.markDirty();
		}
	}
	
	public void clearAssociation()
	{
		this.associatedCards.clear();
		this.markDirty();
	}
	
//	should the tile entity refreshes when new blockstate is applied to the position
//	default: if new blockstate is not equal to new one, return true to force the refresh
//	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)

	@Override
	public void onBlockPlacedBy(EntityPlayer placer, ItemStack stack)
	{
		this.setOwner(placer);
		super.onBlockPlacedBy(placer, stack);
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) 
	{
		if(!worldIn.isRemote) StorageBank.controller.authorize(player, this);
	}
	
	@Override
	public void importFromNBT(NBTTagCompound data) 
	{
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
	public NBTTagCompound exportToNBT(NBTTagCompound SBTileData) 
	{
		if(!this.depoName.isEmpty()) SBTileData.setString("depoName", this.depoName);
		if(this.depoID != null) SBTileData.setUniqueId("depoID", this.depoID);
		if(this.ownerID != null) SBTileData.setUniqueId("ownerID", this.ownerID);
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
