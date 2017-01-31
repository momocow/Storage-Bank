package me.momocow.general.proxy;

import net.minecraft.util.IThreadListener;
import net.minecraft.util.text.ITextComponent;

public interface MoProxy {	
	/**
	 * @return the main thread of the game
	 */
	public IThreadListener getGame();
			
	public void broadcast(ITextComponent text);	
	
	public boolean isOverloading();
	
	public String prefix();
	
	//key binding
	public void registerKeyBindings();
	
	//render
	public void registerRender() throws Exception;
	
	//packet channel
	public void registerChannel();
}