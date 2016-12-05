package me.momocow.storagebank.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class C2SGuiPacket implements IMessage{
	private NBTTagCompound meta;
	
	public C2SGuiPacket(){
	}
	
	public C2SGuiPacket(NBTTagCompound m){
		meta = m;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		meta = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, meta);
	}
	
	//This is the packet handler for the SERVER
	public static class Handler implements IMessageHandler<C2SGuiPacket, IMessage> {

		@Override
		public IMessage onMessage(final C2SGuiPacket message, final MessageContext ctx) {
//			Runnable task = null;
//			
//			switch(message.meta.getInteger("type")){
//				default:
//					return null;
//			}
			
//			if(task != null){
//				IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
//	            mainThread.addScheduledTask(task);
//            }
			
			return null;
		}
	}
}
