package me.momocow.storagebank.client.render.gui;

import java.io.IOException;

import me.momocow.general.client.render.gui.MoCenteredGuiScreen;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

public class GuiIDCard extends MoCenteredGuiScreen
{
	private final static ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/gui/idcard.png");
	private final static String NAME = "GuiIDCard";
	
	//gui component
//	GuiPageButtonList
	
	//label text
	private String textTitle;
	private String textOwner;
	private String textDepository;
	private String textDeleteDepo;
	
	//data
	private NBTTagList depoList;
	private int numDepository;
	private String stringOwnerName;
	private String stringCardID;
	
	/**
	 * Construct the Gui with a bundle of essetial data
	 */
	public GuiIDCard(NBTTagCompound data){
		super(176, 166);
		setUnlocalizedName(Reference.MOD_ID + "." + NAME);
		
		//gui text init
		textTitle = I18n.format(getUnlocalizedName() + ".textTitle");
		textOwner = I18n.format(getUnlocalizedName() + ".textOwner");
		textDepository = I18n.format(getUnlocalizedName() + ".textDepositoryList");
		textDeleteDepo = "X";
		
		//data collection
		depoList = (NBTTagList)data.getTag("depoList");
		numDepository = depoList.tagCount();
		stringCardID = data.getUniqueId("cardID").toString();
		stringOwnerName = data.getString("ownerName");
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
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	this.mc.getTextureManager().bindTexture(GuiIDCard.TEXTURE);
    	this.drawTexturedModalRect(this.offsetX, this.offsetY, 0, 0, this.getGuiWidth(), this.getGuiHeight());
    }
    
    public void drawForegroundLayer()
    {
    	this.drawCenteredString(fontRendererObj, textTitle, this.getCenterX(), this.row(1), fontRendererObj.getColorCode('1'));
    	
    	int stop = fontRendererObj.drawString(textOwner + ": ", this.col(1), this.row(3), fontRendererObj.getColorCode('0'));
    	fontRendererObj.drawString(stringOwnerName, stop, this.row(3), fontRendererObj.getColorCode('8'));
    	fontRendererObj.drawString(textDepository + ": ", this.col(1), this.row(6), fontRendererObj.getColorCode('0'));
    	fontRendererObj.drawSplitString(stringCardID, this.col(1), this.row(15), this.col(17) - this.col(1), fontRendererObj.getColorCode('7'));
    	
    	
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    	super.keyTyped(typedChar, keyCode);
    	
    	if(keyCode == 18)	//press 'e' to exit
    	{
    		this.mc.displayGuiScreen((GuiScreen)null);

            if (this.mc.currentScreen == null)
            {
                this.mc.setIngameFocus();
            }
    	}
    }
    
    @Override
    public void handleMouseInput() throws IOException {
    	// TODO Auto-generated method stub
    	super.handleMouseInput();
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
