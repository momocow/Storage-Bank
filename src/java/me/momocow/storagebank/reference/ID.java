package me.momocow.storagebank.reference;

public class ID 
{
	public final class Channel
	{
		//packet channel
	    public static final String Chat = "SB.Chat";
	}
	
	public final class Packet
	{
		public static final byte C2SBroadcast = 0;
		public static final byte S2CBroadcast = 1;
	}
	
	public final class Gui
	{
		public static final byte NoGui = -1;
		public static final byte GuiIDCard = 1;
		public static final byte GuiATM = 2;
	}
	
	public final class WorldSavedData
	{
		public static final String WorldGenMushroomBlueThin = Reference.MOD_ID +".MBTChunkCoolDown";
		public static final String BankingController = Reference.MOD_ID +".BankGlobalData";
	}
}
