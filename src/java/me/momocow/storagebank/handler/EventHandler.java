package me.momocow.storagebank.handler;

import me.momocow.general.reference.Constants;
import me.momocow.storagebank.init.ModWorldGens;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class EventHandler 
{
	private boolean isWorldGenMBTInit = false;
	
	//WorldTickEvent: this is only posted by server
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onWorldTick(WorldTickEvent e)
	{
		if(isWorldGenMBTInit && e.phase == Phase.START)
		{
			ModWorldGens.Circulation.WorldGenMushroomBlueThin.worldTick(e);
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onWorldLoad(WorldEvent.Load e)
	{
		if(!isWorldGenMBTInit && e.getWorld().provider.getDimension() == Constants.WorldDim.OVERWORLD) 
		{
			ModWorldGens.Circulation.init(e.getWorld());
			isWorldGenMBTInit = true;
		}
	}
}
