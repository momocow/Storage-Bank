package me.momocow.storagebank.proxy;

import me.momocow.storagebank.init.ModBlocks;
import me.momocow.storagebank.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy{
	public ClientProxy()
	{
		super.isRemote = true;
	}
	
	@Override
	public void registerRender() throws Exception {
		ModItems.initModels();
		ModBlocks.initModels();
	}
	
	@Override
	public Minecraft getGame()
	{
		return Minecraft.getMinecraft();
	}
	
	@Override
	public WorldClient getWorld(int worldId)
	{
		return Minecraft.getMinecraft().theWorld;
	}
	
	public static EntityPlayer getPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}
}
