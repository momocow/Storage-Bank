package me.momocow.general.client.render.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public abstract class MoGuiScreen extends GuiScreen
{
	private String unlocalizedName;
	public int rowHeight = 10;
	public int colWidth = 10;
	protected int guiHeight;
	protected int guiWidth;
	protected int offsetX;
	protected int offsetY;
	
	public MoGuiScreen(int w, int h, int ox, int oy)
	{
		this.guiWidth = w;
		this.guiHeight = h;
		this.offsetX = ox;
		this.offsetY = oy;
	}
	
	public MoGuiScreen(int w, int h)
	{
		this(w, h, 0, 0);
	}
	
	/**
	 * Gui initial size 100x100
	 */
	public MoGuiScreen(){
		this(100, 100);
	}
	
	public void setOffset(int ox, int oy)
	{
		this.offsetX = ox;
		this.offsetY = oy;
	}
	
	public void setCenter(int cx, int cy)
	{
		this.offsetX = cx - this.getGuiWidth() / 2;
		this.offsetY = cy - this.getGuiHeight() / 2;
	}
	
	public void setUnlocalizedName(String n)
	{
		unlocalizedName = "gui." + n;
	}
	
	public String getUnlocalizedName()
	{
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
	
	public int getGlobalX(int guiX)
	{
		return this.offsetX + guiX;
	}
	
	public int getGlobalY(int guiY)
	{
		return this.offsetY + guiY;
	}
	
	public int getLocalX(int windowX)
	{
		return windowX - this.offsetX;
	}
	
	public int getLocalY(int windowY)
	{
		return windowY - this.offsetY;
	}
	
	public int getCenterX()
	{
		return this.offsetX + this.getGuiWidth() / 2;
	}
	
	public int getCenterY()
	{
		return this.offsetY + this.getGuiHeight() / 2;
	}
	
	/**
	 * get the Y coord of the nth row
	 * @param rowIdx
	 * @return
	 */
	public int row(int rowIdx)
	{
		return this.getGlobalY(rowIdx * this.rowHeight);
	}
	
	/**
	 * get the X coord of the nth column
	 * @param rowIdx
	 * @return
	 */
	public int col(int colIdx)
	{
		return this.getGlobalX(colIdx * this.colWidth);
	}
	
	@Override
	public void setWorldAndResolution(Minecraft mc, int width, int height)
    {
		super.setWorldAndResolution(mc, width, height);
		
		//gui size validation
		this.guiWidth = (this.guiWidth > width)? width: this.guiWidth;
		this.guiHeight = (this.guiWidth > height)? height: this.guiHeight;
    }
}
