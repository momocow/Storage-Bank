package me.momocow.storagebank.proxy;

import me.momocow.storagebank.client.render.gui.GuiIDCard;
import me.momocow.storagebank.init.ModBlocks;
import me.momocow.storagebank.init.ModItems;
import me.momocow.storagebank.reference.ID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

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
	public void displayGui(int guiID, NBTTagCompound data){
		switch(guiID){
			case ID.Gui.GuiIDCard:
				Minecraft.getMinecraft().displayGuiScreen(new GuiIDCard(data));
				break;
			default:
		}
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
