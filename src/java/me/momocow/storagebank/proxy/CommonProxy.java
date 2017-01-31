package me.momocow.storagebank.proxy;

import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.handler.GuiHandler;
import me.momocow.storagebank.network.C2SDeregisterPacket;
import me.momocow.storagebank.network.C2SGuiInputPacket;
import me.momocow.storagebank.network.S2COpenGuiPacket;
import me.momocow.storagebank.reference.ID;
import me.momocow.storagebank.server.BankingController;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public abstract class CommonProxy
{
	public static SimpleNetworkWrapper guiChannel;
	
	public boolean isClientSide = true;
	
	public IThreadListener getGame() 
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
		return (isClientSide)?"CLIENT":"SERVER";
	}
	
	public void createController() 
	{
		StorageBank.controller = new BankingController();
	}
	
	public void registerKeyBindings() {}

	/**
	 * [CLIENT only]
	 */
	public void registerRender() throws Exception {}
	
	public void registerChannel() 
	{
		guiChannel = NetworkRegistry.INSTANCE.newSimpleChannel(ID.Channel.guiInput);
		guiChannel.registerMessage(C2SDeregisterPacket.Handler.class, C2SDeregisterPacket.class, ID.Packet.C2SDeregister, Side.SERVER);
		guiChannel.registerMessage(C2SGuiInputPacket.Handler.class, C2SGuiInputPacket.class, ID.Packet.C2SGuiInput, Side.SERVER);
		guiChannel.registerMessage(S2COpenGuiPacket.Handler.class, S2COpenGuiPacket.class, ID.Packet.S2COpenGui, Side.CLIENT);
    }
	
	public void registerGuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(StorageBank.instance, new GuiHandler());
	}
}
