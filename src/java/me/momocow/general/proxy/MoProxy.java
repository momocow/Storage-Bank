package me.momocow.general.proxy;

import me.momocow.storagebank.proxy.ClientProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;

public interface MoProxy {
	/**
	 * @return the main thread of the game
	 */
	public IThreadListener getGame();
	
	/**
	 * @return an array of all Worlds, note that the {@link ClientProxy#getWorlds()} only returns one World in the array
	 */
	public World[] getWorlds();
	
	public World getWorld(int worldId);
	
	public EntityPlayer getPlayer();
	
	
	//key binding
	public void registerKeyBindings();
	
	//render
	public void registerRender() throws Exception;
	
	//packet channel
	public void registerChannel();
}