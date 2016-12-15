package me.momocow.storagebank.handler;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class EventHandler {
	private static final int chunkSize = (int) me.momocow.general.reference.Reference.CHUNK_SIZE_IN_BLOCKS;
	
	//WorldTickEvent: this is only posted by server
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onWorldTick(WorldTickEvent e)
	{
		World world = e.world;
		if(e.phase == Phase.START && world.provider.getDimension() == 0 && world.getWorldInfo().isRaining())
		{
			
			for(EntityPlayer p: world.playerEntities)
			{
				Random r = p.getRNG();
				BlockPos playerChunkStartPos = new BlockPos(p.chunkCoordX * chunkSize, 0, p.chunkCoordZ * chunkSize),	//North-West
				         playerChunkEndPos = new BlockPos((p.chunkCoordX + 1) * chunkSize, 0, (p.chunkCoordZ + 1) * chunkSize);	//South-East
				int numMBT = 0,	maxAreaLength = 0;
				
				//find the largest square area which is loaded by the player
				int distanceInChunks = 0, nexDistanceInblocks = (distanceInChunks + 1) * chunkSize;
				for(; world.isAreaLoaded(playerChunkStartPos.north(nexDistanceInblocks).west(nexDistanceInblocks),
							             playerChunkEndPos.south(nexDistanceInblocks).east(nexDistanceInblocks));
					nexDistanceInblocks = (++distanceInChunks + 1) * chunkSize) {}
				maxAreaLength = (distanceInChunks * 2 + 1) * chunkSize;
								
//				p.addChatMessage(new TextComponentString("MaxSpawn: " + Config.MushroomBlueThin.MaxSpawn + " SpawnCooldown: " + Config.MushroomBlueThin.SpawnCooldown));
				
//				if(ModBlocks.BlockMushroomBlueThin.plantToSoil(, ))
//				{
//					numMBT++;
//				}
			}
		}
	}
}
