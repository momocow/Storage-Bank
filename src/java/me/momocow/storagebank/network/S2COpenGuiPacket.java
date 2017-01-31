package me.momocow.storagebank.network;

import io.netty.buffer.ByteBuf;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.proxy.ClientProxy;
import me.momocow.storagebank.reference.ID;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class S2COpenGuiPacket implements IMessage
{
	private int guiID;
	private int posX;
	private int posY;
	private int posZ;

	public S2COpenGuiPacket() {}
	
	public S2COpenGuiPacket(int g, int x, int y, int z)
	{
		this.guiID = g;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.guiID = buf.readInt();
		this.posX = buf.readInt();
		this.posY = buf.readInt();
		this.posZ = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.guiID);
		buf.writeInt(this.posX);
		buf.writeInt(this.posY);
		buf.writeInt(this.posZ);
	}

	//for CLIENT
	public static class Handler implements IMessageHandler<S2COpenGuiPacket, IMessage>
	{

		@Override
		public IMessage onMessage(S2COpenGuiPacket message, MessageContext ctx) 
		{
			Runnable task = null;
			
			switch(message.guiID)
			{
				case ID.Gui.GuiIDCard:
					task = new Runnable() 
					{
						@Override
						public void run() {
							ClientProxy.getPlayer().openGui(StorageBank.instance, ID.Gui.GuiIDCard, ClientProxy.getWorld(), message.posX, message.posY, message.posZ);
						}
					};
					break;
				default:
					return null;
			}
			Minecraft.getMinecraft().addScheduledTask(task);
			
			return null;
		}
		
	}
}
