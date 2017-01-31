package me.momocow.storagebank.proxy;

import me.momocow.storagebank.client.render.item.RenderTileEntityItem;
import me.momocow.storagebank.init.ModBlocks;
import me.momocow.storagebank.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy{
	public ClientProxy()
	{
		super.isClientSide = true;
	}
	
	@Override
	public void registerRender() throws Exception {
		ModItems.initModels();
		ModBlocks.initModels();
		
		TileEntityItemStackRenderer.instance = new RenderTileEntityItem();
	}
	
	@Override
	public Minecraft getGame()
	{
		return Minecraft.getMinecraft();
	}
	
	public static WorldClient getWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}
	
	public static EntityPlayer getPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}
}
