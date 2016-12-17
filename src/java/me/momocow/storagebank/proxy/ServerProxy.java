package me.momocow.storagebank.proxy;

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
	
	public static WorldServer[] getWorlds()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance().worldServers;
	}
	
	public static PlayerList getPlayerList() {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
	}
}
