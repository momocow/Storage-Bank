package me.momocow.general.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import me.momocow.general.block.MoBush;
import me.momocow.storagebank.StorageBank;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NaturalBushManager extends WorldSavedData
{
	private MoBush blockBush;
	private int world;
	private Map<ChunkPos, Set<BlockPos>> bushMap;
	private int totalBushCount = 0;
	
	private NaturalBushManager (String name, MoBush bush, int wd) {
		super(name);
		this.blockBush = bush;
		this.world = wd;
		this.bushMap = new HashMap<ChunkPos, Set<BlockPos>>();
		
		MinecraftForge.EVENT_BUS.register(new NaturalBushManager.EventHandler(this));
	}
	
	public boolean addBush(BlockPos bush)
	{
		if(this.chunkExist(bush) && !this.bushExist(bush))
		{
			this.totalBushCount++;
			this.bushMap.get(new ChunkPos(bush)).add(bush);
			StorageBank.proxy.broadcast(new TextComponentString(this.toString()));
			return true;
		}
		return false;
	}
	
	public boolean addChunk(BlockPos anyPosInChunk)
	{
		return this.addChunk(new ChunkPos(anyPosInChunk));
	}
	
	public boolean addChunk(ChunkPos chunk)
	{
		if(!this.chunkExist(chunk))
		{
			this.bushMap.put(chunk, new HashSet<BlockPos>());
			return true;
		}
		return false;
	}
	
	public boolean removeBush(BlockPos bush)
	{
		if(this.chunkExist(bush) && this.bushExist(bush))
		{
			this.totalBushCount--;
			this.bushMap.get(new ChunkPos(bush)).remove(bush);
			StorageBank.proxy.broadcast(new TextComponentString(this.toString()));
			return true;
		}
		return false;
	}
	
	public boolean removeChunk(BlockPos anyPosInChunk)
	{
		return this.removeChunk(new ChunkPos(anyPosInChunk));
	}
	
	public boolean removeChunk(ChunkPos chunk)
	{
		if(this.chunkExist(chunk))
		{
			this.bushMap.remove(chunk);
			return true;
		}
		return false;
	}
	
	public boolean bushExist(BlockPos bush)
	{
		return (this.chunkExist(bush))? this.bushMap.get(new ChunkPos(bush)).contains(bush): false;
	}
	
	public boolean chunkExist(BlockPos anyPosInChunk)
	{
		return this.chunkExist(new ChunkPos(anyPosInChunk));
	}
	
	public boolean chunkExist(ChunkPos chunk)
	{
		return this.bushMap.containsKey(chunk);
	}
	
	public int countBush(BlockPos anyPosInChunk)
	{
		return (this.chunkExist(anyPosInChunk))? this.bushMap.get(new ChunkPos(anyPosInChunk)).size(): 0;
	}
	
	public int countBush()
	{
		return this.totalBushCount;
	}
	
	public Set<ChunkPos> getChunkSet()
	{
		return this.bushMap.keySet();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		this.world = nbt.getInteger("world");
		this.blockBush = (MoBush) Block.getBlockFromName(nbt.getString("blockName"));
		this.bushMap = new HashMap<ChunkPos, Set<BlockPos>>();
		
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
			
			this.bushMap.put(new ChunkPos(chunkPos[0], chunkPos[1]), bushes);
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
	 *     "block": (String),
	 *     "chunkList": (NBTTagList)
	 *     [
	 *         (NBTTagCompound)
	 *         {
	 *             "chunkPos": (int[2]),
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
		bushMapNBT.setString("blockName", this.blockBush.getRegistryName().toString());
		
		NBTTagList chunkList = new NBTTagList();
		for(ChunkPos chunkPos: this.bushMap.keySet())
		{
			NBTTagCompound chunk = new NBTTagCompound();
			chunk.setIntArray("chunkPos", new int[]{chunkPos.chunkXPos, chunkPos.chunkZPos});
			
			NBTTagList bushList = new NBTTagList();
			for(BlockPos bushPos: this.bushMap.get(chunkPos))
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
	 * Planted by Player
	 * @param stack ItemStack held by player
	 * @param playerIn planter
	 * @param worldIn the world
	 * @param pos soil position
	 * @param facing the side to plant the Plantable
	 * @return
	 */
	public EnumActionResult plantToSoilByPlayer(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing facing)
	{
		if (facing == EnumFacing.UP && playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
        {
			if (this.plantToSoil(worldIn, pos.up()))
			{
				--stack.stackSize;
				return EnumActionResult.SUCCESS;
			}
        }
        return EnumActionResult.FAIL;
	}
	
	/**
	 * Planted to the soil at the certain position
	 * @param worldIn the world
	 * @param pos the position to plant
	 * @return true if it is planted successfully; false if it does not
	 */
	public boolean plantToSoil(World worldIn, BlockPos pos){
		if(this.blockBush.canPlaceBlockAt(worldIn, pos) && worldIn.isAirBlock(pos))
		{
			worldIn.setBlockState(pos, this.blockBush.getDefaultState());
			this.addBush(pos);
			return true;
		}
		
		return false;
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
	public static NaturalBushManager get(World world, String dataId, MoBush bush)
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
	
	/**
	 * should be register to ma
	 */
	public static class EventHandler
	{
		private NaturalBushManager manager;
		
		public EventHandler(NaturalBushManager m)
		{
			manager = m;
		}
		
		@SubscribeEvent(priority=EventPriority.NORMAL)
		public void onBreak(BlockEvent.BreakEvent e)
		{
			if(e.getState().getBlock() == manager.getBlock())
			{
				manager.removeBush(e.getPos());
			}
		}
		
		@SubscribeEvent(priority=EventPriority.NORMAL)
		public void onHarvestDrops(BlockEvent.HarvestDropsEvent e)
		{
			if(e.getState().getBlock() == manager.getBlock())
			{
				manager.removeBush(e.getPos());
			}
		}
	}
}
