package me.momocow.storagebank.handler;

import me.momocow.storagebank.client.render.gui.GuiIDCard;
import me.momocow.storagebank.reference.ID;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{
//	public static void display(int guiId, NBTTagCompound data)
//	{
//		switch(guiId){
//			case ID.Gui.GuiIDCard:
//				Minecraft.getMinecraft().displayGuiScreen(new GuiIDCard(data));
//				break;
//			default:
//		}
//	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}
}
