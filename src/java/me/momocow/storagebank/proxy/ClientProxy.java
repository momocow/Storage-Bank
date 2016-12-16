package me.momocow.storagebank.proxy;

import me.momocow.storagebank.client.render.gui.GuiIDCard;
import me.momocow.storagebank.init.ModBlocks;
import me.momocow.storagebank.init.ModItems;
import me.momocow.storagebank.reference.ID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerRender() throws Exception {
		ModItems.initModels();
		ModBlocks.initModels();
	}

	@Override
	public void displayGui(int guiID, Object... objects){
		switch(guiID){
			case ID.Gui.GuiIDCard:
				NBTTagCompound data = (NBTTagCompound) objects[0];
				Minecraft.getMinecraft().displayGuiScreen(new GuiIDCard(data));
				break;
			default:
		}
	}
	
	public Minecraft getGame()
	{
		return Minecraft.getMinecraft();
	}
	
	public WorldClient getWorld(int worldId)
	{
		return Minecraft.getMinecraft().theWorld;
	}
	
	public WorldClient[] getWorlds()
	{
		return new WorldClient[]{this.getWorld(0)};
	}
	
	public EntityPlayer getPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}
}
