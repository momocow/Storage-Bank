package me.momocow.storagebank.handler;

import java.util.Random;

import me.momocow.storagebank.init.ModBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class EventHandler {
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onWorldTick(WorldTickEvent e)
	{
		World world = e.world;
		if(e.phase == Phase.START && world.provider.getDimension() == 0 && world.getWorldInfo().isRaining())
		{
			for(EntityPlayer p: world.playerEntities)
			{
				Random r = p.getRNG();
				int numMBT = 0,
					startX = ,
					startY = 0,
					startZ = ,
					endX = ,
					endY = 255,
					endZ = ;
				
//				p.addChatMessage(new TextComponentString("X: " + p.chunkCoordX + " Z: " + p.chunkCoordZ));
//				p.addChatMessage(new TextComponentString("MaxSpawn: " + Config.MushroomBlueThin.MaxSpawn + " SpawnCooldown: " + Config.MushroomBlueThin.SpawnCooldown));
				if(ModBlocks.BlockMushroomBlueThin.plantToSoil(, ))
				{
					numMBT++;
				}
			}
		}
	}
}
