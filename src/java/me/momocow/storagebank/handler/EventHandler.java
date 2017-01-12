package me.momocow.storagebank.handler;

import me.momocow.general.event.item.MoItemDestroyEvent;
import me.momocow.general.reference.Constants;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.init.ModWorldGens;
import me.momocow.storagebank.item.IDCard;
import me.momocow.storagebank.world.storage.BankingController;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
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
		World world = e.getWorld();
		if(!world.isRemote)
		{
			if(StorageBank.controller == null) StorageBank.controller = BankingController.get(world);
			if(!ModWorldGens.isCyclicInit()) ModWorldGens.initCyclicWorldGens(world);
		}
	}
	
	//WorldTickEvent: this is only posted by server
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onWorldTick(WorldTickEvent e)
	{
		if(ModWorldGens.isCyclicInit() && e.world.provider.getDimension() == Constants.WorldDim.OVERWORLD && e.phase == Phase.START)
		{
			ModWorldGens.worldGenMushroomBlueThin.worldTick(e);
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onItemExpire(ItemExpireEvent e)
	{
		World world = e.getEntityItem().getEntityWorld();
		ItemStack stack = e.getEntityItem().getEntityItem();
		if(!world.isRemote && stack.getItem() instanceof IDCard && stack.hasTagCompound() && stack.getTagCompound().hasKey("cardID"))
		{
//			StorageBank.controller.deregister(stack.getTagCompound().getUniqueId("cardID"));
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onItemDestroy(MoItemDestroyEvent e)
	{
		World world = e.getEntityItem().getEntityWorld();
		ItemStack stack = e.getEntityItem().getEntityItem();
		if(!world.isRemote && stack.getItem() instanceof IDCard && stack.hasTagCompound() && stack.getTagCompound().hasKey("cardID"))
		{
//			StorageBank.controller.deregister(stack.getTagCompound().getUniqueId("cardID"));
		}
	}
}
