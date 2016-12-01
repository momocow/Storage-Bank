package me.momocow.storagebank.proxy;

import me.momocow.storagebank.init.ModItems;

public class ClientProxy extends CommonProxy{

	@Override
	public void registerKeyBindings() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerRender() throws Exception {
		ModItems.initModels();
	}

}
