package me.momocow.general.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.util.Constants;

public class NaturalBushManager extends WorldSavedData
{
	private Block blockBush;
	private int world;
	private Map<BlockPos, Set<BlockPos>> bushMap;
	private int totalBushCount = 0;
	
	/**
	 * <p>This constructor is supposed to be call from {@link MapStorage#getOrLoadData(Class, String)}. 
	 * If you are seeking for an instance of {@link NaturalBushManager}, 
	 * call {@linkplain #get(World, String, Block) the getter} instead.</p>
	 * <p>If you still insist upon calling this constructor, 
	 * the property {@link #blockBush Block blockBush} is initially set to {@link Blocks#AIR} and
	 * {@link #world World world} is set to the id of the overworld (world id= 0).
	 * You should manually call the {@link #readFromNBT(NBTTagCompound) readFromNBT(NBTTagCompound)} 
	 * with required properties in appropriate NBT format.</p>
	 * <p>Read {@link #toNBT() toNBT()} for further information about the NBT format.</p>
	 * @see #toNBT()
	 * @see #get(World, String, Block)
	 */
	public NaturalBushManager (String name)
	{
		this(name, Blocks.AIR, 0);
	}
	
	private NaturalBushManager (String name, Block bush, int wd) {
		super(name);
		this.blockBush = bush;
		this.world = wd;
		this.bushMap = new HashMap<BlockPos, Set<BlockPos>>();
	}
	
	public boolean addBush(BlockPos bush)
	{
		if(!this.bushExist(bush))
		{
			this.totalBushCount++;
			this.bushMap.get(this.calcChunk(bush)).add(bush);
			return true;
		}
		return false;
	}
	
	public boolean removeBush(BlockPos bush)
	{
		if(this.bushExist(bush))
		{
			this.totalBushCount--;
			this.bushMap.get(this.calcChunk(bush)).remove(bush);
			return true;
		}
		return false;
	}
	
	public boolean bushExist(BlockPos bush)
	{
		return (this.chunkExist(bush))? this.bushMap.get(this.calcChunk(bush)).contains(bush): false;
	}
	
	public boolean chunkExist(BlockPos anyPosInChunk)
	{
		return this.bushMap.containsKey(this.calcChunk(anyPosInChunk));
	}
	
	public BlockPos calcChunk(BlockPos bush)
	{
		return new BlockPos(bush.getX() >> 4, 0, bush.getZ() >> 4);
	}
	
	public int countBush(BlockPos anyPosInChunk)
	{
		return (this.chunkExist(anyPosInChunk))? this.bushMap.get(this.calcChunk(anyPosInChunk)).size(): 0;
	}
	
	public int countBush()
	{
		return this.totalBushCount;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		this.world = nbt.getInteger("world");
		this.blockBush = Block.getBlockById(nbt.getInteger("blockId"));
		this.bushMap = new HashMap<BlockPos, Set<BlockPos>>();
		
		NBTTagList chunkList = (NBTTagList) nbt.getTagList("chunkList", Constants.NBT.TAG_COMPOUND);
		for(int chunkIdx = 0; chunkIdx < chunkList.tagCount(); chunkIdx ++)
		{
			NBTTagCompound chunk = chunkList.getCompoundTagAt(chunkIdx);
			int[] chunkPos = chunk.getIntArray("chunkPos");
			
			Set<BlockPos> bushes = new HashSet<BlockPos>();
			NBTTagList bushList = (NBTTagList) chunk.getTagList("bushList", Constants.NBT.TAG_COMPOUND);
			for(int bushIdx = 0; bushIdx < bushList.tagCount(); bushIdx ++)
			{
				int[] bushPos = bushList.getCompoundTagAt(bushIdx).getIntArray("bushPos");
				bushes.add(new BlockPos(bushPos[0], bushPos[1], bushPos[2]));
			}
			
			this.bushMap.put(new BlockPos(chunkPos[0], chunkPos[1], chunkPos[2]), bushes);
		}
	}

	/**
	 * Overriden function
	 * @return NBT tag as {@link #toNBT() toNBT()} returns
	 * @see #toNBT()
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		return (nbt = this.toNBT());
	}
	
	/**
	 * <pre>
	 * (NBTTagCompound)
	 * {
	 *     "world": (int),
	 *     "blockId": (int),
	 *     "chunkList": (NBTTagList)
	 *     [
	 *         (NBTTagCompound)
	 *         {
	 *             "chunkPos": (int[3]),
	 *             "bushList": (NBTTagList)
	 *             [
	 *                 (NBTTagCompound)
	 *                 {
	 *                     "bushPos": (int[3])
	 *                 },
	 *                 ...
	 *             ]
	 *         },
	 *         ...
	 *     ]
	 * }
	 * </pre>
	 * @return the bush map in NBT format
	 */
	public NBTTagCompound toNBT(){
		NBTTagCompound bushMapNBT = new NBTTagCompound();
		bushMapNBT.setInteger("world", this.world);
		bushMapNBT.setInteger("blockId", Block.getIdFromBlock(this.blockBush));
		
		NBTTagList chunkList = new NBTTagList();
		for(BlockPos chunkPos: bushMap.keySet())
		{
			NBTTagCompound chunk = new NBTTagCompound();
			chunk.setIntArray("chunkPos", new int[]{chunkPos.getX(), chunkPos.getY(), chunkPos.getZ()});
			
			NBTTagList bushList = new NBTTagList();
			for(BlockPos bushPos: bushMap.get(chunkPos))
			{
				NBTTagCompound bush = new NBTTagCompound();
				bush.setIntArray("bushPos", new int[]{bushPos.getX(), bushPos.getY(), bushPos.getZ()});
				bushList.appendTag(bush);;
			}
			chunk.setTag("bushList", bushList);
			chunkList.appendTag(chunk);
		}
		bushMapNBT.setTag("chunkList", chunkList);
		
		return bushMapNBT;
	}
	
	/**
	 * the String type of bush map which first tranforms to the NBT format
	 */
	@Override
	public String toString()
	{
		return this.toNBT().toString();
	}
	
	/**
	 * the {@link Block} type of the bush
	 * @return
	 */
	public Block getBlock()
	{
		return this.blockBush;
	}
	
	public int getWorld()
	{
		return this.world;
	}
	
	/**
	 * <p>Get the instance of NaturalBushManager, differed by dataId</p>
	 * @param world
	 * @param dataId
	 * @param bush the block instance of the bush
	 * @return Null is returned if any one of the parameters is null or the dataId is empty, otherwise an instance of NaturalBushManager will be returned.
	 */
	public static NaturalBushManager get(World world, String dataId, Block bush)
	{
		if(world != null || (dataId != null && !dataId.isEmpty()) || bush != null)
		{
			MapStorage storage = world.getPerWorldStorage();
			NaturalBushManager nbm = (NaturalBushManager) storage.getOrLoadData(NaturalBushManager.class, dataId);
	
			if(nbm == null)
			{
				nbm = new NaturalBushManager(dataId, bush, world.provider.getDimension());
				storage.setData(dataId, nbm);
			}
			
			return nbm;
		}
		
		return null;
	}
}
