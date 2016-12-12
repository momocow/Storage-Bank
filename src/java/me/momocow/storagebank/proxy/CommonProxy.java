package me.momocow.storagebank.proxy;

import me.momocow.general.proxy.MoProxy;
import me.momocow.storagebank.network.C2SGuiPacket;
import me.momocow.storagebank.network.S2CGuiPacket;
import me.momocow.storagebank.reference.ID;
import me.momocow.storagebank.reference.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public abstract class CommonProxy implements MoProxy
{
	/**packet system
	 * channel_gui: gui sync and client input
	 */
	public static SimpleNetworkWrapper guiChannel;	//GUI channel instance
	
	public void registerKeyBindings() {}

	/**
	 * [CLIENT only]
	 */
	public void registerRender() throws Exception {}
	
	public void registerChannel() 
	{
		//GUIinfo-exchange channel, requests from client for certain GUI data, responses from server aftrer fetching NBT data
		guiChannel = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.GUI_CHANNEL_NAME);
		guiChannel.registerMessage(C2SGuiPacket.Handler.class, C2SGuiPacket.class, ID.Packet.C2SGuiInput, Side.SERVER);
		guiChannel.registerMessage(S2CGuiPacket.Handler.class, S2CGuiPacket.class, ID.Packet.S2CGuiSync, Side.CLIENT);
    }
	
	/**
	 * [CLIENT only]
	 */
	public void displayGui(int guiID, Object... objects){}
}
