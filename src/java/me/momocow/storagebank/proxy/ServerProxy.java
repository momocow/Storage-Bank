package me.momocow.storagebank.proxy;

import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.config.Config;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ServerProxy extends CommonProxy{	
	public ServerProxy()
	{
		super.isRemote = false;
	}
	
	@Override
	public MinecraftServer getGame()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance();
	}
	
	@Override
	public WorldServer getWorld(int worldId)
	{
		WorldServer[] worlds = getWorlds();
				
		for (WorldServer w : worlds)
		{
			if (w.provider.getDimension() == worldId)
			{
				return w;
			}
		}
		
		return null;
	}
	
	@Override
	public void broadcast(ITextComponent text)
	{
		for(EntityPlayer p: getGame().getPlayerList().getPlayerList())
		{
			p.addChatMessage(text);
		}
	}
	
	public boolean isOverloading()
	{
		return (getAvgTimePerTick() > Config.DedicatedServer.MaxMilliSecPerTick);
	}
	
	public static WorldServer[] getWorlds()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance().worldServers;
	}
	
	public static PlayerList getPlayerList() {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
	}
	
	/**
	 * @return average time per tick in micro second
	 */
	public static double getAvgTimePerTick()
	{
		long[] period = ((MinecraftServer)StorageBank.proxy.getGame()).tickTimeArray;
		long tickSum = 0L;
		for(long i: period)
		{
			tickSum += i;
		}
		return ((double)tickSum / (double)period.length) * 1.0E-6D;
	}
}
