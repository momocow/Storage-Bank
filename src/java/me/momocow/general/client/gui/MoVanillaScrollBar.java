package me.momocow.general.client.gui;

import me.momocow.storagebank.reference.Reference;
import net.minecraft.util.ResourceLocation;

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
	private int stageNum = 1;
	private int stage = 0;
	private int stageUnit;
	/**
	 * local position corresponding to the scrollbar gui which is clicked by the mouse
	 * It is only meaningful when the gui is dragged
	 */
	private int clickedGuiY = 0;
	
	
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
		this.isEnabled = (this.stageNum > 1);

		
		this.stageUnit = (int)((double)(this.maxY - this.minY) / (double)(this.stageNum * 2 - 2));
	}
	
	public void drawScrollBar()
	{
		int textureX = (this.isEnabled)? 0: 12;
		MoGuiScreen.drawPartialScaleTexturedRect(MoVanillaScrollBar.TEXTURE, this.posX, this.posY, this.zLevel, textureX, 0, 12, 15, 24, 15, this.width, this.height);
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
		this.setStage(this.stage - 1);
	}
	
	public boolean isMouseClicked(int mouseX, int mouseY)
	{
		return this.isEnabled && mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.posY && mouseY <= this.posY + this.height;
	}
	 
	@Override
	public void mouseClicked(int mouseX, int mouseY)
	{
		this.isDragged = true;
		this.clickedGuiY = mouseY - this.posY;
    }
    
    @Override
	public void mouseClickMove(int mouseX, int mouseY) 
	{
    	this.posY = mouseY - this.clickedGuiY;
    	if(this.posY > this.maxY - this.height)
    	{
    		this.posY = this.maxY - this.height;
    	}
    	else if(this.posY < this.minY)
    	{
    		this.posY = this.minY;
    	}
    	
    	//derive the stage from pos
    	this.stage = getStageFromPos(this.posY);
    }
    
    @Override
	public void mouseReleased(int mouseX, int mouseY) 
    {
    	this.isDragged = false;
    	
    	this.posY = mouseY - this.clickedGuiY;
    	if(this.posY > this.maxY - this.height)
    	{
    		this.posY = this.maxY - this.height;
    	}
    	else if(this.posY < this.minY)
    	{
    		this.posY = this.minY;
    	}
    	
    	//derive the stage from pos
    	this.stage = getStageFromPos(this.posY);
    	this.compute();
    }
    
    private int getStageFromPos(int pos)
    {
    	int deltaY = pos + this.height / 2 - this.minY,
    			remain = (deltaY % this.stageUnit > 0)? 1: 0;
    	return (int)Math.floor((double)(deltaY / this.stageUnit + remain) / 2.0D);
    }
    
    public int getStage()
    {
    	return this.stage;
    }
    
    public int getStageNum()
    {
    	return this.stageNum;
    }
    
    /**
     * compute the valid position with the given stage
     */
    private void compute()
    {
		//stage validation
		if(this.stage < 0)
		{
			this.stage = 0;
		}
		else if(this.stage > this.stageNum - 1)
		{
			this.stage = this.stageNum - 1;
		}
		
		//derive the position
		if(this.stage == 0)
		{
			this.posY = this.minY;
		}
		else if(this.isLastStage(this.stage))
		{
			this.posY = this.maxY - this.height;
		}
		else if(this.stage > 0 && this.stage < this.stageNum - 1)
		{
			this.posY = this.minY + 2 * this.stage * this.stageUnit - this.height / 2;
		}
    }
}
