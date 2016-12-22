package me.momocow.storagebank.worldgen.circulation;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import me.momocow.general.reference.Constants;
import me.momocow.general.util.MoWorldSavedData;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.config.Config;
import me.momocow.storagebank.init.ModBlocks;
import me.momocow.storagebank.reference.ID;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public final class WorldGenMushroomBlueThin extends MoWorldSavedData
{
	private static final String DATA_ID = ID.MoWorldSavedData.WorldGenMushroomBlueThin;

	public WorldGenMushroomBlueThin(String dataId)
	{
		super(dataId);
	}
	
	private WorldGenMushroomBlueThin(World w)
	{
		super(DATA_ID, w);
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
						for(int h = 10; h <= MoWorldSavedData.getHeightFromHeightMap(heightMap, bedrock) + 1; h++)
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
		if(w.provider.getDimension() == Constants.WorldDim.OVERWORLD)
		{
			MapStorage storage = w.getPerWorldStorage();
			WorldGenMushroomBlueThin worldgen = (WorldGenMushroomBlueThin) storage.getOrLoadData(WorldGenMushroomBlueThin.class, DATA_ID);
	
			if(worldgen == null)
			{
				worldgen = new WorldGenMushroomBlueThin(w);
				storage.setData(DATA_ID, worldgen);
//				LogHelper.debug("New WorldSavedData[\""+DATA_ID+"\"] added!");
			}
			else
			{
				worldgen.setWorld(w);
			}
			
			return worldgen;
		}
		
		return null;
	}
}
