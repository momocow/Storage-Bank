package me.momocow.general.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.Constants;

public abstract class MoWorldSavedData extends WorldSavedData
{
	protected World world;
	protected Map<Long, NBTTagCompound> data = new HashMap<Long, NBTTagCompound>();

	protected MoWorldSavedData(String dataId, World w) {
		super(dataId);
		
		this.world = w;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.data = new HashMap<Long, NBTTagCompound>();
		
		NBTTagList chunkList = nbt.getTagList("chunkList", Constants.NBT.TAG_COMPOUND);
		for(int i = 0; i < chunkList.tagCount(); i++)
		{
			NBTTagCompound chunk = chunkList.getCompoundTagAt(i);
			this.data.put(chunk.getLong("chunkId"), chunk.getCompoundTag("chunkData"));
		}
	}
	
	/**
	 * NBTTagCompound
	 * {
	 *     "chunkList": NBTTagList
	 *     [
	 *         NBTTagCompound
	 *         {
	 *             "chunkId": long,
	 *             "chunkData": NBTTagCompound
	 *         },
	 *         ...
	 *     ]
	 * }
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagList chunkList = new NBTTagList();
		for(long chunkId: data.keySet())
		{
			NBTTagCompound chunk = new NBTTagCompound();
			chunk.setLong("chunkId", chunkId);
			chunk.setTag("chunkData", this.data.get(chunkId));
			chunkList.appendTag(chunk);
		}
		nbt.setTag("chunkList", chunkList);
		return nbt;
	}
	
	public void setInteger(Chunk chunk, String dataKey, int dataValue)
	{
		this.setInteger(ChunkPos.chunkXZ2Int(chunk.xPosition, chunk.zPosition), dataKey, dataValue);
	}
	
	public void setInteger(long chunkId, String dataKey, int dataValue)
	{
		this.touchChunk(chunkId);
		this.data.get(chunkId).setInteger(dataKey, dataValue);
	}
	
	public Integer getInteger(Chunk chunk, String dataKey)
	{
		return this.getInteger(ChunkPos.chunkXZ2Int(chunk.xPosition, chunk.zPosition), dataKey);
	}
	
	public Integer getInteger(long chunkId, String dataKey)
	{
		if(this.existChunk(chunkId))
		{
			return this.data.get(chunkId).getInteger(dataKey);
		}
		
		return null;
	}
	
	public boolean existChunk(Chunk chunk)
	{
		return this.existChunk(ChunkPos.chunkXZ2Int(chunk.xPosition, chunk.zPosition));
	}
	
	public boolean existChunk(long chunkId)
	{
		return this.data.containsKey(chunkId);
	}
	
	protected void touchChunk(long chunkId)
	{
		if(!this.existChunk(chunkId))
		{
			this.data.put(chunkId, new NBTTagCompound());
		}
	}
}
