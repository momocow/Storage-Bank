package me.momocow.storagebank.network;

import io.netty.buffer.ByteBuf;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.item.IDCard;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class C2SDeregisterPacket implements IMessage
{
	ItemStack card = null;
	
	public C2SDeregisterPacket(){}
	
	public C2SDeregisterPacket(ItemStack stack)
	{
		if(stack.getItem() instanceof IDCard)
		{
			card = stack;
		}
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.card = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeItemStack(buf, this.card);
	}

	//Handler for SERVER
	public static class Handler implements IMessageHandler<C2SDeregisterPacket, IMessage>
	{
		@Override
		public IMessage onMessage(C2SDeregisterPacket message, MessageContext ctx) 
		{
			final ItemStack stack = message.card;
			
			if(stack.getItem() instanceof IDCard)
			{
				((WorldServer) ctx.getServerHandler().playerEntity.worldObj).addScheduledTask(new Runnable(){
					@Override
					public void run() 
					{
						StorageBank.controller.deregister(stack);
					}
				});
			}
			
			return null;
		}
	}
}
