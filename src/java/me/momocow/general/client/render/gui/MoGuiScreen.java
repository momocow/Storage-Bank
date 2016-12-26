package me.momocow.general.client.render.gui;

import net.minecraft.client.gui.GuiScreen;

public abstract class MoGuiScreen extends GuiScreen{
	private String unlocalizedName;
	public int rowHeight = 10;
	public int guiHeight;
	public int guiWidth;
	
	public MoGuiScreen(int w, int h){
		guiWidth = w;
		guiHeight = h;
	}
	
	public MoGuiScreen(){
		this(0, 0);
	}
	
	public void setUnlocalizedName(String n){
		unlocalizedName = "gui." + n;
	}
	
	public String getUnlocalizedName(){
		return unlocalizedName;
	}
	
	public int getWindowWidth()
	{
		return this.width;
	}
	
	public int getWindowHeight()
	{
		return this.height;
	}
	
	public int getGuiWidth()
	{
		return this.guiWidth;
	}
	
	public int getGuiHeight()
	{
		return this.guiHeight;
	}
	
	public int getRowHeight()
	{
		return this.rowHeight;
	}
	
	public int getGlobalX(int guiX)
	{
		return ;
	}
	
	public int getGlobalY(int guiY)
	{
		return ;
	}
	
	public int getLocalX(int globalX)
	{
		return ;
	}
	
	public int getLocalY(int globalY)
	{
		return ;
	}
}
