package me.momocow.general.client.render.gui;

import net.minecraft.client.Minecraft;

public class MoCenteredGuiScreen extends MoGuiScreen
{
	public MoCenteredGuiScreen(int w, int h)
	{
		super(w, h);
		this.setCenter(this.width / 2, this.height / 2);
	}
	
	/**
	 * Gui initial size 100x100
	 */
	public MoCenteredGuiScreen()
	{
		this(100, 100);
	}
	
	@Override
	public void setWorldAndResolution(Minecraft mc, int width, int height)
    {
		super.setWorldAndResolution(mc, width, height);
		
		//recalc the offset
		this.setCenter(this.width / 2, this.height / 2);
    }
}
