package me.momocow.storagebank.reference;

import me.momocow.general.EnumToInt;

public class ID 
{
	public static final class Channel
	{
		//packet channel
	    public static final String guiInput = "SB:guiInput";
	}
	
	public static final class Packet
	{
		public static final byte C2SDeregister = 0;
		public static final byte C2SGuiInput = 1;
		public static final byte C2SAuthRequest = 2;
		public static final byte S2CAuthResponse = 3;
	}
	
	public static final class Gui
	{
		public static final byte NoGui = -1;
		public static final byte GuiIDCard = 1;
		public static final byte GuiATM = 2;
		public static final byte GuiDepoCore = 3;
	}
	
	public static final class WorldSavedData
	{
		public static final String WorldGenMushroomBlueThin = Reference.MOD_ID +".MBTChunkCoolDown";
	}
	
	public enum GuiInput implements EnumToInt
	{
		GuiIDCard(0);
		
		private final int value;
		
		private GuiInput(int v)
		{
			this.value = v;
		}
		
		@Override
		public int toInt() {
			return this.value;
		}
		
		public static GuiInput getEnum(int intVal)
		{
			for(GuiInput enumVal: GuiInput.values())
			{
				if(enumVal.value == intVal)
				{
					return enumVal;
				}
			}
			
			return null;
		}
	}
	
	public enum GuiAuth implements EnumToInt
	{
		PlayerOpenCard(0);
		
		private final int value;
		
		private GuiAuth(int v)
		{
			this.value = v;
		}
		
		@Override
		public int toInt() {
			return this.value;
		}
		
		public static GuiAuth getEnum(int intVal)
		{
			for(GuiAuth enumVal: GuiAuth.values())
			{
				if(enumVal.value == intVal)
				{
					return enumVal;
				}
			}
			
			return null;
		}
	}
}
