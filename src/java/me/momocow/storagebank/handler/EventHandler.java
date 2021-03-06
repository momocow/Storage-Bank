package me.momocow.storagebank.handler;

import me.momocow.mobasic.event.item.MoItemDestroyEvent;
import me.momocow.mobasic.reference.Constants;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.client.gui.GuiContainerCreativeModified;
import me.momocow.storagebank.server.IDCardHelper;
import me.momocow.storagebank.server.MushroomBlueThinSpawner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandler 
{
	//WorldTickEvent: this is only posted by server
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onWorldTick(WorldTickEvent e)
	{
		if(e.world.provider.getDimension() == Constants.WorldDim.OVERWORLD && e.phase == Phase.START)
		{

			MushroomBlueThinSpawner.get(e.world).worldTick(e);
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onItemExpire(ItemExpireEvent e)
	{
		World world = e.getEntityItem().getEntityWorld();
		ItemStack stack = e.getEntityItem().getEntityItem();
		if(!world.isRemote && IDCardHelper.isCard(stack))
		{
			StorageBank.controller.destroyCard(stack);
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onItemDestroy(MoItemDestroyEvent e)
	{
		World world = e.getEntityItem().getEntityWorld();
		ItemStack stack = e.getEntityItem().getEntityItem();

		if(!world.isRemote && IDCardHelper.isCard(stack))
		{
			StorageBank.controller.destroyCard(stack);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.HIGH, receiveCanceled=true)
	public void onGuiOpen(GuiOpenEvent e)
	{
		Minecraft client = Minecraft.getMinecraft();
		GuiScreen screen = e.getGui();
		
		//open custom GuiContainerCreative instead of the vanilla one
		if(screen instanceof GuiContainerCreative)
		{
			client.displayGuiScreen(new GuiContainerCreativeModified(client.thePlayer));
			if(e.isCancelable())
			{
				e.setCanceled(true);
			}
		}
	}
}
