package me.momocow.storagebank.proxy;

import me.momocow.general.proxy.MoProxy;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.handler.GuiHandler;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

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
//		broadcastChannel = NetworkRegistry.INSTANCE.newSimpleChannel(ID.Channel.Chat);
    }
	
	public void registerGuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(StorageBank.instance, new GuiHandler());
	}
}
