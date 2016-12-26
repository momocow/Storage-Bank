package me.momocow.storagebank.handler;

import me.momocow.storagebank.client.render.gui.GuiIDCard;
import me.momocow.storagebank.reference.ID;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

public class GuiHandler {
	public static void display(int guiId, NBTTagCompound data)
	{
		switch(guiId){
			case ID.Gui.GuiIDCard:
				Minecraft.getMinecraft().displayGuiScreen(new GuiIDCard(data));
				break;
			default:
		}
	}
}
