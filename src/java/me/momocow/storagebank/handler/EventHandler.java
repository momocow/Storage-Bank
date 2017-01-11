package me.momocow.storagebank.handler;

import me.momocow.general.event.item.MoItemDyingEvent;
import me.momocow.general.reference.Constants;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.item.IDCard;
import me.momocow.storagebank.world.gen.cyclic.WorldGenMushroomBlueThin;
import me.momocow.storagebank.world.storage.BankingController;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class EventHandler 
{
	@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onWorldLoad(WorldEvent.Load e)
	{
		if(!e.getWorld().isRemote && StorageBank.controller == null)
		{
			StorageBank.controller = BankingController.get(e.getWorld());
		}
	}
	
	//WorldTickEvent: this is only posted by server
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onWorldTick(WorldTickEvent e)
	{
		if(e.world.provider.getDimension() == Constants.WorldDim.OVERWORLD && e.phase == Phase.START)
		{
			WorldGenMushroomBlueThin.get(e.world).worldTick(e);
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onItemDying(MoItemDyingEvent e)
	{
		ItemStack stack = e.getEntityItem().getEntityItem();
		if(!StorageBank.proxy.isRemote && stack.getItem() instanceof IDCard && stack.hasTagCompound())
		{
			StorageBank.controller.deregister(stack.getTagCompound().getUniqueId("cardID"));
		}
	}
}
