package me.momocow.general.proxy;

public interface MoProxy {
	//mod elements initialization
	public void init() throws Exception;
	
	//key binding
	public void registerKeyBindings();
	
	//render
	public void registerRender() throws Exception;
	
	//packet channel
	public void registerChannel();
}