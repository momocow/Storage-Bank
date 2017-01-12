package me.momocow.storagebank.proxy;

import me.momocow.storagebank.client.render.item.RenderTileEntityItem;
import me.momocow.storagebank.init.ModBlocks;
import me.momocow.storagebank.init.ModItems;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.model.obj.OBJLoader;

public class ClientProxy extends CommonProxy{
	public ClientProxy()
	{
		super.isRemote = true;
	}
	
	@Override
	public void registerRender() throws Exception {
		OBJLoader.INSTANCE.addDomain(Reference.MOD_ID);
		
		ModItems.initModels();
		ModBlocks.initModels();
		
		TileEntityItemStackRenderer.instance = new RenderTileEntityItem();
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
