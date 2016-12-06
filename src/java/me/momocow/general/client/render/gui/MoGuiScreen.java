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
		guiWidth = 0;
		guiHeight = 0;
	}
	
	public void setUnlocalizedName(String n){
		unlocalizedName = "gui." + n;
	}
	
	public String getUnlocalizedName(){
		return unlocalizedName;
	}
}
