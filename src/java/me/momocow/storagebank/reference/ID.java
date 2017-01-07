package me.momocow.storagebank.reference;

public class ID 
{
	public final class Channel
	{
		//packet channel
	    public static final String GUI_CHANNEL_NAME = "SB_GUIIO";
	}
	
	public final class Packet
	{
		public static final byte C2SGuiInput = 1;
		public static final byte S2CGuiSync = 2;
	}
	
	public final class Gui
	{
		public static final byte NoGui = -1;
		public static final byte GuiIDCard = 1;
	}
	
	public final class MoWorldSavedData
	{
		public static final String WorldGenMushroomBlueThin = "MBTChunkCoolDown";
	}
}
