package me.momocow.storagebank.client.render.gui;

import me.momocow.general.client.render.gui.MoGuiScreen;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

public class GuiIDCard extends MoGuiScreen{
	private final static ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/gui/idcard.png");
	private final static String NAME = "GuiIDCard";
	
	private String textTitle;
	private String textOwner;
	private String textDepository;
	private String textDeleteDepo;
	
	private NBTTagList depoList;
	private int numDepository;
	private String textOwnerName;
	private String textCardID;
	
	/**
	 * Construct the Gui with a bundle of essetial data
	 */
	public GuiIDCard(NBTTagCompound data){
		super(256, 256);
		setUnlocalizedName(Reference.MOD_ID + "." + NAME);
		
		//gui text init
		textTitle = I18n.format(getUnlocalizedName() + ".textTitle");
		textOwner = I18n.format(getUnlocalizedName() + ".textOwner");
		textDepository = I18n.format(getUnlocalizedName() + ".textDepositoryList");
		textDeleteDepo = "X";
		
		//data collection
		depoList = (NBTTagList)data.getTag("depoList");
		numDepository = depoList.tagCount();
		textCardID = data.getString("cardID");
		textOwnerName = data.getString("ownerName");
	}
	
	/**
	 * gui component init 
	 */
	@Override
	public void initGui(){
		this.buttonList.clear();
		//this.buttonList.add(e);
	}
	
	@Override
    public void updateScreen() {
    }
	
	/**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int parWidth, int parHeight, float p_73863_3_){
    	this.drawDefaultBackground();
    	this.drawBackgroundLayer();
    	this.drawForegroundLayer();
    }
    
    public void drawBackgroundLayer()
    {
    	GlStateManager.color(1.0f, 1.0f, 1.0f);
    	Minecraft.getMinecraft().getTextureManager().bindTexture(GuiIDCard.TEXTURE);
    	this.drawTexturedModalRect(this.getWindowWidth() / 2 - this.getGuiWidth() / 2, 
    			this.getWindowHeight() / 2 - this.getGuiHeight() / 2, 
    			0, 0, this.getGuiWidth(), this.getGuiHeight());
    }
    
    public void drawForegroundLayer()
    {
    	int row = 0;
    	this.drawString(fontRendererObj, textTitle, 
    			this.getWindowWidth() / 2, 
    			this.getWindowHeight() / 2 - this.getGuiHeight() / 2 + this.getRowHeight() * row++, 
    			fontRendererObj.getColorCode('3'));
    	this.drawString(fontRendererObj, textOwner + ": " + textOwnerName, 
    			this.getWindowWidth() / 2, 
    			this.getWindowHeight() / 2 - this.getGuiHeight() / 2 + this.getRowHeight() * row++, 
    			fontRendererObj.getColorCode('1'));
    	this.drawString(fontRendererObj, textDepository + ": ", 
    			this.getWindowWidth() / 2, 
    			this.getWindowHeight() / 2 - this.getGuiHeight() / 2 + this.getRowHeight() * row++, 
    			fontRendererObj.getColorCode('2'));
    }
    
    /**
     * Returns true if this GUI should pause the game when it is displayed in 
     * single-player
     */
    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
