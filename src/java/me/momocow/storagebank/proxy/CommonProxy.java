package me.momocow.storagebank.proxy;

import me.momocow.general.proxy.MoProxy;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.handler.GuiHandler;
import me.momocow.storagebank.network.C2SDeregisterPacket;
import me.momocow.storagebank.network.C2SGuiInputPacket;
import me.momocow.storagebank.reference.ID;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public abstract class CommonProxy implements MoProxy
{
	public static SimpleNetworkWrapper guiInputChannel;
	
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
	
	public void createController() {}
	public void registerKeyBindings() {}

	/**
	 * [CLIENT only]
	 */
	public void registerRender() throws Exception {}
	
	public void registerChannel() 
	{
		guiInputChannel = NetworkRegistry.INSTANCE.newSimpleChannel(ID.Channel.guiInput);
		guiInputChannel.registerMessage(C2SDeregisterPacket.Handler.class, C2SDeregisterPacket.class, ID.Packet.C2SDeregister, Side.SERVER);
		guiInputChannel.registerMessage(C2SGuiInputPacket.Handler.class, C2SGuiInputPacket.class, ID.Packet.C2SGuiInput, Side.SERVER);
    }
	
	public void registerGuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(StorageBank.instance, new GuiHandler());
	}
}
