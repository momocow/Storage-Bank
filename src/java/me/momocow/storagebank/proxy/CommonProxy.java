package me.momocow.storagebank.proxy;

import me.momocow.general.proxy.MoProxy;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.handler.GuiHandler;
import me.momocow.storagebank.network.S2CBroadcastPacket;
import me.momocow.storagebank.reference.ID;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public abstract class CommonProxy implements MoProxy
{
	/**packet system
	 * channel_gui: gui sync and client input
	 */
	public static SimpleNetworkWrapper broadcastChannel;
	public boolean isRemote = true;
	
	public IThreadListener getGame() 
	{ 
		return null; 
	}
	
	public World getWorld(int worldId) 
	{
		return null;
	}
		
	public void broadcast(ITextComponent text){}
	
	public boolean isOverloading()
	{
		return false;
	}
	
	public String prefix()
	{
		return (isRemote)?"CLIENT":"SERVER";
	}
	
	public void registerKeyBindings() {}

	/**
	 * [CLIENT only]
	 */
	public void registerRender() throws Exception {}
	
	public void registerChannel() 
	{
		broadcastChannel = NetworkRegistry.INSTANCE.newSimpleChannel(ID.Channel.BroadcastChannel);
		broadcastChannel.registerMessage(S2CBroadcastPacket.Handler.class, S2CBroadcastPacket.class, ID.Packet.S2CBroadcast, Side.CLIENT);
    }
	
	public void registerGuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(StorageBank.instance, new GuiHandler());
	}
}
