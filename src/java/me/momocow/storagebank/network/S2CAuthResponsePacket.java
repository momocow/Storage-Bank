package me.momocow.storagebank.network;

import io.netty.buffer.ByteBuf;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.reference.ID;
import me.momocow.storagebank.reference.ID.GuiAuth;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class S2CAuthResponsePacket implements IMessage
{
	private boolean result = false;
	private GuiAuth reqId;

	public S2CAuthResponsePacket() {}
	
	public S2CAuthResponsePacket(boolean r, GuiAuth id)
	{
		this.result = r;
		this.reqId = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.result = buf.readBoolean();
		this.reqId = GuiAuth.getEnum(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.result);
		buf.writeInt(this.reqId.toInt());
	}

	//for CLIENT
	public static class Handler implements IMessageHandler<S2CAuthResponsePacket, IMessage>
	{

		@Override
		public IMessage onMessage(S2CAuthResponsePacket message, MessageContext ctx) 
		{
			Runnable task = null;
			
			switch(message.reqId)
			{
				case PlayerOpenCard:
					task = new Runnable()
					{
						@Override
						public void run() {
							EntityPlayer playerIn = Minecraft.getMinecraft().thePlayer;
							playerIn.openGui(StorageBank.instance, ID.Gui.GuiIDCard, Minecraft.getMinecraft().theWorld, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);							
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
