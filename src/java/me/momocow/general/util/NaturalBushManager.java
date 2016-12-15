package me.momocow.general.util;

import java.util.HashMap;
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

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.world = nbt.getInteger("world");
		this.blockBush = Block.getBlockById(nbt.getInteger("blockId"));
		this.bushMap = new HashMap<BlockPos, Set<BlockPos>>();
		
		for(NBTTagCompound chunk: (NBTTagList) nbt.getTagList("chunkList", Constants.NBT.TAG_COMPOUND)){
			
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
	 * get the instance of NaturalBushManager, differed by dataId
	 * @param world
	 * @param dataId
	 * @param bush the block instance of the bush
	 * @return
	 */
	public static NaturalBushManager get(World world, String dataId, Block bush)
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
}
