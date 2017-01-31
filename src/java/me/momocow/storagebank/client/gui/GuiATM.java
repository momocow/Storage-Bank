package me.momocow.storagebank.client.gui;

import me.momocow.moapi.client.gui.MoCenteredGuiScreen;
import me.momocow.moapi.client.gui.MoGuiScreen;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.util.ResourceLocation;

public class GuiATM extends MoCenteredGuiScreen
{
	private final static ResourceLocation BGGUITEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/gui/atm.png");

	public GuiATM() 
	{
		super(176, 166);
	}
	
	/**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int parWidth, int parHeight, float partialTicks)
    {
    	this.drawDefaultBackground();
    	MoGuiScreen.drawProportionTexturedRect(GuiATM.BGGUITEXTURE, this.offsetX, this.offsetY, this.zLevel, 0, 0, 176, 166, 256, 256, this.guiWidth, this.guiHeight);
    }
}
