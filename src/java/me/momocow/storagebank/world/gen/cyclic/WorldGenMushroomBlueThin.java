package me.momocow.storagebank.world.gen.cyclic;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import me.momocow.general.reference.Constants;
import me.momocow.general.util.LogHelper;
import me.momocow.general.world.storage.MoChunksData;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.config.Config;
import me.momocow.storagebank.init.ModBlocks;
import me.momocow.storagebank.reference.ID;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public final class WorldGenMushroomBlueThin extends MoChunksData
{
	private static final String DATA_ID = ID.WorldSavedData.WorldGenMushroomBlueThin;
	
	/**
	 * Required Constructors
	 * for {@linkplain MapStorage#getOrLoadData(Class, String) MapStorage#getOrLoadData}
	 */
	public WorldGenMushroomBlueThin()
	{
		this(DATA_ID);
	}
	
	/**
	 * Required Constructors
	 * for {@linkplain MapStorage#getOrLoadData(Class, String) MapStorage#getOrLoadData}
	 */
	public WorldGenMushroomBlueThin(String dataID)
	{
		super(dataID);
		LogHelper.info("Data[\"" + dataID + "\"] is resumed.");
	}
	
	private WorldGenMushroomBlueThin(World w)
	{
		super(DATA_ID);
		LogHelper.info("Data[\"" + this.mapName + "\"] is created.");
		this.init(w);
	}
	
	@Override
	public void init(World w) {
		super.init(w);
		
		LogHelper.info("Data[\"" + this.mapName + "\"] for WorldDim#" + this.world.provider.getDimension() + " is initialized.");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		LogHelper.info("Data[\"" + DATA_ID + "\"] is loaded from NBT.");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		LogHelper.info("Data[\"" + DATA_ID + "\"] is written to NBT.");
		return super.writeToNBT(nbt);
	}
	
	private float getChance(BlockPos plantPos)
	{
		float chance = 0.005f;
		
		if(this.world.getBlockState(plantPos.north()).getBlock() == ModBlocks.BlockMushroomBlueThin)
		{
			chance *= 0.5f;
		}
		if(this.world.getBlockState(plantPos.south()).getBlock() == ModBlocks.BlockMushroomBlueThin)
		{
			chance *= 0.5f;
		}
		if(this.world.getBlockState(plantPos.east()).getBlock() == ModBlocks.BlockMushroomBlueThin)
		{
			chance *= 0.5f;
		}
		if(this.world.getBlockState(plantPos.west()).getBlock() == ModBlocks.BlockMushroomBlueThin)
		{
			chance *= 0.5f;
		}
		if(!this.world.canSeeSky(plantPos))
		{
			chance *= 2f;
		}
		if(this.world.getBlockState(plantPos.down()).getBlock() == Blocks.MOSSY_COBBLESTONE)
		{
			chance *= 5f;
		}
		
		return chance * Config.MushroomBlueThin.SpawnChanceScale;
	}
	
	public void worldTick(WorldTickEvent e)
	{
		if(world != null && e.world.provider.getDimension() == Constants.WorldDim.OVERWORLD
				&& !StorageBank.proxy.isOverloading() && world.getWorldInfo().isRaining())
		{
			Set<Chunk> loadedChunks = new HashSet<Chunk>(((ChunkProviderServer)world.getChunkProvider()).getLoadedChunks());
			for(Chunk chunk: loadedChunks)
			{
				//make a copy of chunk data for the following computing
				//Chunk does not guarantee to be loaded through the whole computing process
				ChunkPos chunkPos = chunk.getChunkCoordIntPair();
				int[] heightMap =chunk.getHeightMap().clone();
				long chunkId = ChunkPos.chunkXZ2Int(chunkPos.chunkXPos, chunkPos.chunkZPos);
				
				int MBTSpawnChunkCoolDown = (this.existChunk(chunkId))? this.getInteger(chunkId, "CoolDown"): 0;
				if(MBTSpawnChunkCoolDown <= 0)
				{
					Iterable<BlockPos> chunkPlane = BlockPos.getAllInBox(new BlockPos(chunkPos.chunkXPos << 4, 0, chunkPos.chunkZPos << 4), 
						new BlockPos(((chunkPos.chunkXPos + 1) << 4) - 1, 0, ((chunkPos.chunkZPos + 1) << 4) - 1));
					for(BlockPos bedrock: chunkPlane)
					{
						for(int h = 10; h <= MoChunksData.getHeightFromHeightMap(heightMap, bedrock) + 1; h++)
						{
							//plant method includes environnment check and spawn chance rolling
							ModBlocks.BlockMushroomBlueThin.plant(world, bedrock.up(h), this.getChance(bedrock.up(h)));
						}
					}
					//reset the cooldown
					MBTSpawnChunkCoolDown = Config.MushroomBlueThin.SpawnCooldown;
				}
			
				//store the cool down value into NBT data
				this.setInteger(chunk, "CoolDown", --MBTSpawnChunkCoolDown);
			}
		}
	}
	
	/**
	 * if provided World is not overworld (dimension == 0), null is returned
	 * @param w World
	 * @return WorldGenMushroomBlueThin
	 */
	@Nullable
	public static WorldGenMushroomBlueThin get(World w)
	{
		if(!w.isRemote && w.provider.getDimension() == Constants.WorldDim.OVERWORLD)
		{
			MapStorage storage = w.getPerWorldStorage();
			WorldGenMushroomBlueThin worldgen = (WorldGenMushroomBlueThin) storage.getOrLoadData(WorldGenMushroomBlueThin.class, DATA_ID);
	
			if(worldgen == null)	//create new Spawner for MBT
			{
				worldgen = new WorldGenMushroomBlueThin(w);
				storage.setData(DATA_ID, worldgen);
			}
			else if(!worldgen.isInit())	//resume the old one
			{
				worldgen.init(w);
			}

			return worldgen;
		}
		
		return null;
	}
}
