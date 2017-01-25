package me.momocow.storagebank.network;

import io.netty.buffer.ByteBuf;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.reference.ID;
import me.momocow.storagebank.reference.ID.GuiInput;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class C2SGuiInputPacket implements IMessage
{
	NBTTagCompound data = null;
	int type;
	
	public C2SGuiInputPacket(){}
	
	public C2SGuiInputPacket(NBTTagCompound d, ID.GuiInput t)
	{
		this.data = d;
		this.type = t.toInt();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.type = buf.readInt();
		this.data = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(type);
		ByteBufUtils.writeTag(buf, this.data);
	}

	//Handler for SERVER
	public static class Handler implements IMessageHandler<C2SGuiInputPacket, IMessage>
	{
		@Override
		public IMessage onMessage(C2SGuiInputPacket message, MessageContext ctx) 
		{
			Runnable task = null;
			IMessage reply = null;
			final NBTTagCompound data = message.data;
			
			switch(GuiInput.getEnum(message.type))
			{
				case GuiIDCard:
					task = new Runnable()
					{
						@Override
						public void run() {
							StorageBank.controller.updateDepoList(data);
						}
					};
					break;
				default:
			}
			
			if(task != null) ((WorldServer) ctx.getServerHandler().playerEntity.worldObj).addScheduledTask(task);
			
			return reply;
		}
	}
}
