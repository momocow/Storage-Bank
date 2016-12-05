package me.momocow.storagebank.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class S2CGuiPacket implements IMessage{
	private NBTTagCompound meta;
	
	public S2CGuiPacket(){
	}
	
	public S2CGuiPacket(NBTTagCompound m){
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
	
	//This is the packet handler for the CLIENT
	public static class Handler implements IMessageHandler<S2CGuiPacket, IMessage> {

		@Override
		public IMessage onMessage(final S2CGuiPacket message, final MessageContext ctx) {
//			Runnable task = null;
//			
//			switch(message.meta.getInteger("type")){
//				case ID.PacketHandler.GetHeldItemNBT:
//					task = new Runnable(){
//						@Override
//						public void run() {
//							Minecraft.getMinecraft().displayGuiScreen(new GuiIDCard((NBTTagCompound) message.meta.getTag("data")));
//						}
//					};
//					break;
//				default:
//					return null;
//			}
//			
//			if(task != null){
//				IThreadListener mainThread = Minecraft.getMinecraft();
//	            mainThread.addScheduledTask(task);
//            }
//			
			return null;
		}
	}
}
