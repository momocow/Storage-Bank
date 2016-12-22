package me.momocow.storagebank.handler;

import me.momocow.general.reference.Constants;
import me.momocow.storagebank.worldgen.circulation.WorldGenMushroomBlueThin;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class EventHandler 
{
	//WorldTickEvent: this is only posted by server
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onWorldTick(WorldTickEvent e)
	{
		if(e.world.provider.getDimension() == Constants.WorldDim.OVERWORLD && e.phase == Phase.START)
		{
			WorldGenMushroomBlueThin.get(e.world).worldTick(e);
		}
	}
}
