package me.momocow.storagebank.proxy;

import me.momocow.storagebank.client.render.gui.GuiIDCard;
import me.momocow.storagebank.init.ModBlocks;
import me.momocow.storagebank.init.ModItems;
import me.momocow.storagebank.reference.ID;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

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
}
