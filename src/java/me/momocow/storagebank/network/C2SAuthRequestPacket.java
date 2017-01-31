package me.momocow.storagebank.network;

import io.netty.buffer.ByteBuf;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.reference.ID.GuiAuth;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class C2SAuthRequestPacket implements IMessage
{
	private ItemStack stack;
	private GuiAuth reqId;
	
	public C2SAuthRequestPacket(){}
	
	public C2SAuthRequestPacket(ItemStack s, GuiAuth rid)
	{
		this.stack = s;
		this.reqId = rid;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.stack = ByteBufUtils.readItemStack(buf);
		this.reqId = GuiAuth.getEnum(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeItemStack(buf, stack);
		buf.writeInt(this.reqId.toInt());
	}

	//for SERVER
	public static class Handler implements IMessageHandler<C2SAuthRequestPacket, IMessage>
	{

		@Override
		public IMessage onMessage(C2SAuthRequestPacket message, MessageContext ctx) 
		{
			switch(message.reqId)
			{
				case PlayerOpenCard:
					EntityPlayer player = ctx.getServerHandler().playerEntity;
					boolean response = StorageBank.controller.authorize(player, message.stack);
					return new S2CAuthResponsePacket(response, message.reqId);
				default:
			}
			
			return null;
		}
		
	}
}
