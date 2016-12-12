package me.momocow.general.proxy;

public interface MoProxy {
	//display gui
	public void displayGui(int guiID, Object... objects);
	
	//key binding
	public void registerKeyBindings();
	
	//render
	public void registerRender() throws Exception;
	
	//packet channel
	public void registerChannel();
}