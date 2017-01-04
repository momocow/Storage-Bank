package me.momocow.storagebank.handler;

import me.momocow.storagebank.client.gui.GuiIDCard;
import me.momocow.storagebank.item.IDCard;
import me.momocow.storagebank.reference.ID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch(id)
		{
			case ID.Gui.GuiIDCard:
				ItemStack idcard = player.getHeldItemMainhand();
				if(idcard.getItem() instanceof IDCard && idcard.hasTagCompound()) return new GuiIDCard(idcard.getTagCompound());
				break;
			default:
		}
		return null;
	}
}
