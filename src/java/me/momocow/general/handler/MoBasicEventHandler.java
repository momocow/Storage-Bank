package me.momocow.general.handler;

import me.momocow.general.event.item.EnumMoItemDyingCause;
import me.momocow.general.event.item.MoItemDyingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MoBasicEventHandler 
{
	/**
	 * When the item is expired, also fire the MoItemDyingEvent
	 * @param e
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onItemExpire(ItemExpireEvent e)
	{
		MinecraftForge.EVENT_BUS.post(new MoItemDyingEvent(e.getEntityItem(), EnumMoItemDyingCause.EXPIRED));
	}
}
