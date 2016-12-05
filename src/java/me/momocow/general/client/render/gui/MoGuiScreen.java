package me.momocow.general.client.render.gui;

import net.minecraft.client.gui.GuiScreen;

public abstract class MoGuiScreen extends GuiScreen{
	private static String unlocalizedName;
	
	public void setUnlocalizedName(String n){
		unlocalizedName = "gui." + n;
	}
	
	public String getUnlocalizedName(){
		return unlocalizedName;
	}
}
