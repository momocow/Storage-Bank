package me.momocow.storagebank;

import me.momocow.general.proxy.MoProxy;
import me.momocow.general.util.LogHelper;
import me.momocow.storagebank.init.ModBlocks;
import me.momocow.storagebank.init.ModItems;
import me.momocow.storagebank.reference.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class StorageBank {
	//mod instance
	@Mod.Instance(Reference.MOD_ID)
    public static StorageBank instance;
	
	//proxy for client/server event
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static MoProxy proxy;
     
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) throws Exception{
    	LogHelper.info("Stage: Pre-Init");
    	
    	ModItems.preinit();
		ModBlocks.preinit();
		
    	proxy.registerRender();
    	proxy.registerChannel();
    }
        
    @EventHandler
    public void init(FMLInitializationEvent e) throws Exception{
    	LogHelper.info("Stage: Init");
    	
    	ModBlocks.init();
    }
        
    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	LogHelper.info("Stage: Post-Init");
    }
}
