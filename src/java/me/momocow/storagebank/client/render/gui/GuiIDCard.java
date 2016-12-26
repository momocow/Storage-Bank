package me.momocow.storagebank.client.render.gui;

import me.momocow.general.client.render.gui.MoCenteredGuiScreen;
import me.momocow.general.util.LogHelper;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

public class GuiIDCard extends MoCenteredGuiScreen
{
	private final static ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/gui/idcard.png");
	private final static String NAME = "GuiIDCard";
	
	private String textTitle;
	private String textCardID;
	private String textOwner;
	private String textDepository;
	private String textDeleteDepo;
	
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
		textCardID = I18n.format(getUnlocalizedName() + ".textCardID");
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
    	Minecraft.getMinecraft().getTextureManager().bindTexture(GuiIDCard.TEXTURE);
    	this.drawTexturedModalRect(this.offsetX, this.offsetY, 0, 0, this.getGuiWidth(), this.getGuiHeight());
    }
    
    public void drawForegroundLayer()
    {
    	int rowIdx = 1;
    	
    	this.drawCenteredString(fontRendererObj, textTitle, this.getCenterX(), this.row(rowIdx), fontRendererObj.getColorCode('1'));
    	rowIdx += 3;
    	fontRendererObj.drawSplitString(textCardID + ": " + stringCardID, this.col(1), this.row(rowIdx), this.col(17) - this.col(1), fontRendererObj.getColorCode('0'));
    	rowIdx += 2;
    	fontRendererObj.drawString(textOwner + ": " + stringOwnerName, this.col(1), this.row(rowIdx++), fontRendererObj.getColorCode('0'));
    	fontRendererObj.drawString(textDepository + ": ", this.col(1), this.row(rowIdx++), fontRendererObj.getColorCode('0'));
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
