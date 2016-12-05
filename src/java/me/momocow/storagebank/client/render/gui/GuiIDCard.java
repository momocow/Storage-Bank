package me.momocow.storagebank.client.render.gui;

import com.sun.prism.paint.Color;

import me.momocow.general.client.render.gui.MoGuiScreen;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class GuiIDCard extends MoGuiScreen{
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
    	this.drawCenteredString(fontRendererObj, textTitle, 0, 50, 16777215);
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
