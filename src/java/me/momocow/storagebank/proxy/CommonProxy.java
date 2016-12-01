package me.momocow.storagebank.proxy;

import me.momocow.general.proxy.MoProxy;
import me.momocow.general.util.LogHelper;
import me.momocow.storagebank.init.ModItems;
import me.momocow.storagebank.reference.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public abstract class CommonProxy implements MoProxy{
	/**packet system
	 * channel_gui: gui sync and client input
	 */
	public static SimpleNetworkWrapper channel_gui;	//GUI channel instance
	
	public void init() throws Exception{
		try{
			ModItems.init();
		}
		catch(Exception e){
			LogHelper.error("Elements initialization fails");
			throw e;
		}
	}
	
	public void registerChannel() {
		channel_gui = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.GUI_CHANNEL_NAME);
    }
}
