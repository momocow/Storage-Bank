package me.momocow.general.proxy;

import net.minecraft.util.IThreadListener;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public interface MoProxy {	
	/**
	 * @return the main thread of the game
	 */
	public IThreadListener getGame();
	
	public World getWorld(int worldId);
		
	public void broadcast(ITextComponent text);	
	
	//key binding
	public void registerKeyBindings();
	
	//render
	public void registerRender() throws Exception;
	
	//packet channel
	public void registerChannel();
}