package me.momocow.storagebank;

import me.momocow.mobasic.util.LogHelper;
import me.momocow.storagebank.config.Config;
import me.momocow.storagebank.init.ModBlocks;
import me.momocow.storagebank.init.ModEntities;
import me.momocow.storagebank.init.ModEvents;
import me.momocow.storagebank.init.ModItems;
import me.momocow.storagebank.init.ModRecipes;
import me.momocow.storagebank.proxy.CommonProxy;
import me.momocow.storagebank.reference.Reference;
import me.momocow.storagebank.server.BankingController;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class StorageBank 
{
	//mod instance
	@Mod.Instance(Reference.MOD_ID)
    public static StorageBank instance;
	
	//proxy for client/server event
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static CommonProxy proxy;
	
	public static Config config;	//mod config
	public static BankingController controller;	//Bank instance to control the interaction with StorageBank
    public static LogHelper logger = new LogHelper(Reference.MOD_NAME);
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) throws Exception
    {
    	logger.info("Stage: Pre-Init");
    
    	config = new Config(e.getModConfigurationDirectory());
    	config.read();
    	
    	ModItems.preinit();
		ModBlocks.preinit();
		ModEntities.preInit();
		
		proxy.createController();
    	proxy.registerRender();
    	proxy.registerChannel();
    }
        
    @EventHandler
    public void init(FMLInitializationEvent e) throws Exception
    {
    	logger.info("Stage: Init");
    	
    	ModBlocks.init();
    	ModRecipes.init();
    	ModEvents.init();
    	
    	proxy.registerGuiHandler();
    }
        
    @EventHandler
    public void postInit(FMLPostInitializationEvent e) 
    {
    	logger.info("Stage: Post-Init");
    	
    	config.save();
    }
}
