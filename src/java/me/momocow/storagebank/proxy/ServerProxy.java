package me.momocow.storagebank.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ServerProxy extends CommonProxy{
	public MinecraftServer getGame()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance();
	}
	
	public WorldServer[] getWorlds()
	{
		return getGame().worldServers;
	}
	
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
	
	public void broadcast(ITextComponent text)
	{
		for(EntityPlayer p: getGame().getPlayerList().getPlayerList())
		{
			p.addChatMessage(text);
		}
	}
}
