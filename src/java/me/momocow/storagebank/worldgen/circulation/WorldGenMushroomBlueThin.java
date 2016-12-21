package me.momocow.storagebank.worldgen.circulation;

import java.util.Collection;

import javax.annotation.Nullable;

import me.momocow.general.reference.Constants;
import me.momocow.general.util.MoWorldSavedData;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.config.Config;
import me.momocow.storagebank.init.ModBlocks;
import me.momocow.storagebank.reference.ID;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public final class WorldGenMushroomBlueThin extends MoWorldSavedData
{
	private static final String DATA_ID = ID.MoWorldSavedData.WorldGenMushroomBlueThin;
	
	private WorldGenMushroomBlueThin()
	{
		super(DATA_ID, StorageBank.proxy.getWorld(0));
	}
	
	public void worldTick(WorldTickEvent e)
	{
		if(e.world.provider.getDimension() == this.world.provider.getDimension())
		{
			if(world.getWorldInfo().isRaining())
			{
				Collection<Chunk> loadedChunks = ((ChunkProviderServer)world.getChunkProvider()).getLoadedChunks();
				for(Chunk chunk: loadedChunks)
				{
					int MBTSpawnChunkCoolDown = (this.existChunk(chunk))? this.getInteger(chunk, "CoolDown"): 0;
					if(MBTSpawnChunkCoolDown <= 0)
					{
						Iterable<BlockPos> chunkPlane = BlockPos.getAllInBox(new BlockPos(chunk.xPosition << 4, 0, chunk.zPosition << 4), 
							new BlockPos(((chunk.xPosition + 1) << 4) - 1, 0, ((chunk.zPosition + 1) << 4) - 1));
						for(BlockPos bedrock: chunkPlane)
						{
							for(int h = 1; h <= chunk.getHeight(bedrock) + 1; h++)
							{
								//plant method includes environnment check and spawn chance rolling
								if(ModBlocks.BlockMushroomBlueThin.plant(world, bedrock.up(h), 0.05f * Config.MushroomBlueThin.SpawnChanceScale))
								{
									chunk.setChunkModified();
								}
							}
						}
						//reset the cooldown
						MBTSpawnChunkCoolDown = Config.MushroomBlueThin.SpawnCooldown;
					}
					
					//store the cool down value into NBT data
					this.setInteger(chunk, "CoolDown", --MBTSpawnChunkCoolDown);
				}
				
				StorageBank.proxy.broadcast(new TextComponentString(""+this.data.size()));
			}
		}
	}
	
	@Nullable
	public static WorldGenMushroomBlueThin get(World w)
	{
		if(w.provider.getDimension() == Constants.WorldDim.OVERWORLD)
		{
			MapStorage storage = w.getPerWorldStorage();
			WorldGenMushroomBlueThin worldgen = (WorldGenMushroomBlueThin) storage.getOrLoadData(WorldGenMushroomBlueThin.class, DATA_ID);
	
			if(worldgen == null)
			{
				worldgen = new WorldGenMushroomBlueThin();
				storage.setData(DATA_ID, worldgen);
			}
			
			return worldgen;
		}
		
		return null;
	}
}
