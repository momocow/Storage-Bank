package me.momocow.storagebank.network;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class C2SBroadcastPacket implements IMessage{
	private String unformatted;
	private int paramCount;
	private List<String> params = new ArrayList<String>();
	
	public C2SBroadcastPacket(String u, String... p){
		this.unformatted = u;
		for(String s: p)
		{
			this.params.add(s);
		}
		this.paramCount = this.params.size();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		//init
		this.params.clear();
		
		this.unformatted = ByteBufUtils.readUTF8String(buf);
		this.paramCount = buf.readInt();
		for(int i = 0; i< this.paramCount; i++)
		{
			this.params.add(ByteBufUtils.readUTF8String(buf));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.unformatted);
		buf.writeInt(this.paramCount);
		for(String s: this.params)
		{
			ByteBufUtils.writeUTF8String(buf, s);
		}
	}
	
	//This is the packet handler for the SERVER
	public static class Handler implements IMessageHandler<C2SBroadcastPacket, IMessage> {
		@Override
		public IMessage onMessage(final C2SBroadcastPacket message, final MessageContext ctx) {
			Runnable task = new Runnable(){
				@Override
				public void run() {
					
				}
			};
			
			Minecraft.getMinecraft().addScheduledTask(task);
			
			return null;
		}
	}
}
