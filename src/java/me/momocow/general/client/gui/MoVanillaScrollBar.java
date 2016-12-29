package me.momocow.general.client.gui;

import me.momocow.storagebank.reference.Reference;
import net.minecraft.util.ResourceLocation;

public class MoVanillaScrollBar implements MoScrollable
{
	private final static ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/gui/scrollbar.png");
	
	public boolean isEnabled = false;
	
	private int componentId;
	private boolean isDragged = false;
	private int width;
	private int height;
	private int posX;
	private int posY;
	private float zLevel;
	private int minY;
	private int maxY;
	private int lastMouseY = 0;
	
	public MoVanillaScrollBar(int id, int x, int y, float z, int my, int w, int h)
	{
		this.componentId = id;
		this.posX = x;
		this.posY = y;
		this.zLevel = z;
		this.minY = y;
		this.maxY = my;
		this.width = w;
		this.height = h;
	}
	
	public void drawScrollBar()
	{
		if(this.posY > this.maxY)
		{
			this.posY = this.maxY;
		}
		else if(this.posY < this.minY)
		{
			this.posY = this.minY;
		}
		
		int textureX = 0;
		if(!this.isEnabled)
		{
			textureX = 12;
			this.posY = this.minY;
		}
		
		MoGuiScreen.drawTexturedRect(MoVanillaScrollBar.TEXTURE, this.posX, this.posY, this.zLevel, textureX, 0, 12, 15, 24, 15, this.width, this.height);
	}
	
	public boolean isDragged()
	{
		return this.isDragged;
	}
	
	public boolean isMouseClicked(int mouseX, int mouseY)
	{
		this.setLastMouseY(mouseY);
		return this.isEnabled && mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.posY && mouseY <= this.posY + this.height;
	}
	 
	@Override
	public void mouseClicked()
	{
		this.isDragged = true;
    }
    
    @Override
	public void mouseClickMove(int mouseX, int mouseY) 
	{
    	this.posY += mouseY - this.lastMouseY;
    	this.setLastMouseY(mouseY);
    }
    
    @Override
	public void mouseReleased() 
    {
    	this.isDragged = false;
    }
    
    protected void setLastMouseY(int mouseY)
    {
    	this.lastMouseY = mouseY;
    	if(mouseY > this.maxY)
    	{
    		this.lastMouseY = this.maxY;
    	}
    	else if(mouseY < this.minY)
    	{
    		this.lastMouseY = this.minY;
    	}
    }
}
