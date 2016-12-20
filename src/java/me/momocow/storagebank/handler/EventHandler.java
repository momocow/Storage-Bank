package me.momocow.storagebank.handler;

import me.momocow.general.util.NaturalBushManager;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.config.Config;
import me.momocow.storagebank.init.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class EventHandler {
	private static final int chunkSize = (int) me.momocow.general.reference.Constants.CHUNK_SIZE_IN_BLOCKS;
	private static final int LoadedChunkRadius = Config.MaxLoadedChunkRadius;
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
				//find the loaded chunks
				for (int playerIdx = 0; playerIdx < world.playerEntities.size(); playerIdx++)
				{
					BlockPos chunk = new ChunkPos(world.playerEntities.get(playerIdx).getPosition()).getBlock(0, 0, 0);
					
					int radiusIdx = 0;
					for(radiusIdx = 0; radiusIdx <= LoadedChunkRadius; radiusIdx ++)
					{
						
					}
				}
				
				//reset the cooldown
				this.MBTSpawnCDCounter = Config.MushroomBlueThin.SpawnCooldown;
			}
		}
	}
	
	/**
	 * there is only one phase (PHASE.NORMAL) in the event
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onWorldLoad(WorldEvent.Load e)
	{
		//get the NaturalBushManager
		if(!StorageBank.proxy.isRemote && e.getWorld().provider.getDimension() == 0)
		{
			ModBlocks.BlockMushroomBlueThin.manager = NaturalBushManager.get(StorageBank.proxy.getWorld(0), "", ModBlocks.BlockMushroomBlueThin);
		}
	}
}
