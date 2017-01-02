package me.momocow.general.client.gui;

import me.momocow.storagebank.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

public class MoVanillaScrollBar implements MoScrollable
{
	private final static ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/gui/scrollbar.png");
	
	private boolean isEnabled = false;
	private boolean isDragged = false;
	private int width;
	private int height;
	private int posX;
	private int posY;
	private float zLevel;
	private int minY;
	private int maxY;
	private int lastMouseY = 0;
	private int stageNum = 1;
	private int stage = 0;
	private int textureCursor = 0;
	private int stageSize = 0;
	
	public MoVanillaScrollBar(int x, int y, float z, int my, int w, int h, int s)
	{
		this.posX = x;
		this.posY = y;
		this.zLevel = z;
		this.minY = y;
		this.maxY = my;
		this.width = w;
		this.height = h;
		this.stageNum = Math.max(s, 1);
		this.stageSize = (this.maxY - this.minY) / this.stageNum;
		
		this.validate();
	}
	
	public void drawScrollBar()
	{
		MoGuiScreen.drawPartialScaleTexturedRect(MoVanillaScrollBar.TEXTURE, this.posX, this.posY, this.zLevel, this.textureCursor, 0, 12, 15, 24, 15, this.width, this.height);
	}
	
	public boolean isDragged()
	{
		return this.isDragged;
	}
	
	public boolean isEnabled()
	{
		return this.isEnabled;
	}
	
	public boolean isLastStage(int s)
	{
		return this.getStageNum() - 1 == s;
	}
	
	public void setEnabled(boolean enable)
	{
		this.isEnabled = enable;
	}
	
	public void setStage(int s)
	{
		this.stage = s;
		this.compute();
	}
	
	public void moveNextStage()
	{
		this.setStage(this.stage + 1);
	}
	
	public void moveBackStage()
	{
		this.setStage(this.stage -1);
	}
	
	public boolean isMouseClicked(int mouseX, int mouseY)
	{
		return this.isEnabled && mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.posY && mouseY <= this.posY + this.height;
	}
	 
	@Override
	public void mouseClicked(int mouseX, int mouseY)
	{
		this.setLastMouseY(mouseY);
		this.isDragged = true;
    }
    
    @Override
	public void mouseClickMove(int mouseX, int mouseY) 
	{
    	Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("mouseX: " + mouseX +" mouseY: " + mouseY));
    	Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("this.posY: " + this.posY + " this.lastMouseY: " + this.lastMouseY));
    	this.posY = this.posY +  mouseY - this.lastMouseY;
    	this.setLastMouseY(mouseY);
    	Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("this.posY: " + this.posY + " this.lastMouseY: " + this.lastMouseY));
    	
    	this.validate();
    }
    
    @Override
	public void mouseReleased(int mouseX, int mouseY) 
    {
    	this.isDragged = false;
    	
    	this.validate();
    }
    
    protected void setLastMouseY(int mouseY)
    {
    	this.lastMouseY = mouseY;
    	if(this.lastMouseY > this.maxY - this.height)
    	{
    		this.lastMouseY = this.maxY - this.height;
    	}
    	else if(this.lastMouseY < this.minY)
    	{
    		this.lastMouseY = this.minY;
    	}
    }
    
    public int getStage()
    {
    	return this.stage;
    }
    
    public int getStageNum()
    {
    	return this.stageNum;
    }
    
    private void compute()
    {
    	//check if it is enabled
		if(!this.isEnabled)
		{
			this.textureCursor = 12;
			this.posY = this.minY;
			
			return;
		}
		this.textureCursor = 0;
    	
		//set to the correct stage and the corresponding position
		this.stage = (this.posY - this.minY) / stageSize;
		if(!this.isDragged)
		{
			if(((this.posY - this.minY) % stageSize) >= (stageSize / 2))
			{
				this.posY = this.minY + stageSize * (this.stage + 1);
				this.stage ++;
			}
			else
			{
				this.posY = this.minY + stageSize * this.stage;
			}
		}
		
		//set the position for the last stage
		if(this.stageNum > 1 && this.stage >= this.stageNum - 1)
		{
			this.posY = this.maxY - this.height;
		}
		
		//check if it is in the valid range
    	if(this.posY > this.maxY - this.height)
		{
			this.posY = this.maxY - this.height;
		}
		else if(this.posY < this.minY)
		{
			this.posY = this.minY;
		}
    	
    	//check if it is in the valid range
    	if(this.stage > this.stageNum - 1)
		{
			this.stage = this.stageNum - 1;
		}
		else if(this.stage < 0)
		{
			this.stage = 0;
		}
    }
}
