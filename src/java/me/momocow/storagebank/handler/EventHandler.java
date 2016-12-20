package me.momocow.storagebank.handler;

import java.util.Collection;
import java.util.Random;

import me.momocow.general.util.LogHelper;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.config.Config;
import me.momocow.storagebank.init.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class EventHandler {
	private static final int chunkSize = (int) me.momocow.general.reference.Constants.CHUNK_SIZE_IN_BLOCKS;
	private int MBTSpawnCDCounter = 0;
	
	//WorldTickEvent: this is only posted by server
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onWorldTick(WorldTickEvent e)
	{
		if(e.phase == Phase.START)
		{
			this.MBTSpawnCDCounter --;
			
			World world = e.world;
			if(world.provider.getDimension() == 0 && world.getWorldInfo().isRaining() && this.MBTSpawnCDCounter <= 0)
			{
				Collection<Chunk> loadedChunks = ((ChunkProviderServer)world.getChunkProvider()).getLoadedChunks();
				for(Chunk chunk: loadedChunks)
				{					
					Iterable<BlockPos> chunkPlane = BlockPos.getAllInBox(new BlockPos(chunk.xPosition << 4, 0, chunk.zPosition << 4), 
																		 new BlockPos(((chunk.xPosition + 1) << 4) - 1, 0, ((chunk.zPosition + 1) << 4) - 1));

					for(BlockPos bedrock: chunkPlane)
					{
						for(int h = 1; h <= chunk.getHeight(bedrock) + 1; h++)
						{
							BlockPos candidatePos = bedrock.up(h);
							if(chunk.getBlockState(candidatePos).getBlock() == Blocks.AIR 
									&& ModBlocks.BlockMushroomBlueThin.canBlockStay(world, candidatePos, chunk.getBlockState(candidatePos.down())))
							{
								Random r = chunk.getRandomWithSeed(ChunkPos.chunkXZ2Int(chunk.xPosition, chunk.zPosition));
								if(r.nextFloat() <= 0.01 * Config.MushroomBlueThin.SpawnChanceScale)
								{
									world.setBlockState(candidatePos, ModBlocks.BlockMushroomBlueThin.getDefaultState(), 3);
								}
							}
						}
					}
				}
				
				//reset the cooldown
				this.MBTSpawnCDCounter = Config.MushroomBlueThin.SpawnCooldown;
			}
		}
	}
}
