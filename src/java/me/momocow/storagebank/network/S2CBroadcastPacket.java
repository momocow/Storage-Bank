package me.momocow.storagebank.network;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class S2CBroadcastPacket implements IMessage
{
	private String unformatted;
	private int paramCount;
	private List<String> params = new ArrayList<String>();
	
	/**
	 * Required constructor by FML
	 */
	public S2CBroadcastPacket() {}
	
	public S2CBroadcastPacket(String u, String... p){
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
		
		ByteBufUtils.getContentDump(buf);
		
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
	
	//This is the packet handler for the CLIENT
	public static class Handler implements IMessageHandler<S2CBroadcastPacket, IMessage> {
		@Override
		public IMessage onMessage(final S2CBroadcastPacket message, final MessageContext ctx) {
			Runnable task = new Runnable(){
				@Override
				public void run() {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentTranslation(message.unformatted, message.params));
				}
			};
			
			Minecraft.getMinecraft().addScheduledTask(task);
			
			return null;
		}
	}
}
