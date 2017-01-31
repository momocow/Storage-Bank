package me.momocow.storagebank.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import me.momocow.moapi.client.gui.MoCenteredGuiScreen;
import me.momocow.moapi.client.gui.MoGuiScreen;
import me.momocow.moapi.client.gui.widget.MoVanillaScrollBar;
import me.momocow.storagebank.network.C2SGuiInputPacket;
import me.momocow.storagebank.proxy.CommonProxy;
import me.momocow.storagebank.reference.ID;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GuiIDCard extends MoCenteredGuiScreen
{
	private final static ResourceLocation BGGUITEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/gui/idcard.png");
	private final static ResourceLocation ACTIVETEXTFIELD = new ResourceLocation(Reference.MOD_ID + ":textures/gui/depoNameActive.png");
	private final static ResourceLocation SCROLLBAR = new ResourceLocation(Reference.MOD_ID + ":textures/gui/scrollbar.png");
	private final static String NAME = "GuiIDCard";
	
	//gui component
	private MoVanillaScrollBar scrollbar;
	private List<GuiTextField> depoNames = new ArrayList<GuiTextField>();	//a list of all depositories
	private List<GuiTextField> currentDepoNames = new ArrayList<GuiTextField>();	//a cached list of currently visible depositories
	private List<GuiButton> currentDepoDeletes = new ArrayList<GuiButton>();
	private GuiButton resetButton;
	private final int maxDepoNumInPage =  7;
	
	//state
	private int pageCursor = 0;
	
	//label text
	private String textTitle;
	private String textOwner;
	private String textDepository;
	private String textDeleteDepo;
	
	//data
	private NBTTagCompound cachedData;
	private NBTTagList depoList;
	private int depoNum;
	private String stringOwnerName;
	private String stringCardID;
	
	/**
	 * Construct the Gui with a bundle of essetial data
	 */
	public GuiIDCard(NBTTagCompound data){
		super(176, 166);
		this.setUnlocalizedName(Reference.MOD_ID + "." + NAME);
		this.pageCursor = 0;
		
		//gui text init
		textTitle = I18n.format(getUnlocalizedName() + ".textTitle");
		textOwner = I18n.format(getUnlocalizedName() + ".textOwner");
		textDepository = I18n.format(getUnlocalizedName() + ".textDepositoryList");
		textDeleteDepo = "X";
		
		//data collection
		this.cachedData = data;
		depoList = (NBTTagList)data.getTag("depoList");
		stringCardID = data.getUniqueId("cardID").toString();
		stringOwnerName = data.getString("ownerName");
	}
	
	/**
	 * gui component init 
	 */
	@Override
	public void initGui(){
		//environment preparing
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		
		//reset
		for(GuiTextField depoName: this.currentDepoNames)
		{
			depoName.setEnabled(false);
			depoName.setFocused(false);
			depoName.setVisible(false);
		}
		this.currentDepoNames.clear();
		
		for(GuiButton depoDelete: this.currentDepoDeletes)
		{
			depoDelete.visible = false;
			depoDelete.enabled = false;
		}
		this.currentDepoDeletes.clear();
		
		//required data
		this.depoNum = this.depoList.tagCount();
		
		//add the scrollbar
		int remain = (this.depoNum % this.maxDepoNumInPage > 0)? 1: 0;
		this.scrollbar = new MoVanillaScrollBar(this.getGlobalX(158), this.getGlobalY(73), this.zLevel, this.getGlobalY(142), 12, 15, this.depoNum / this.maxDepoNumInPage + remain, SCROLLBAR);
		this.scrollbar.setStage(this.pageCursor);
		
		//add the button and the textfield
		depoNames.clear();
		int btnIdx;
		for(btnIdx = 0; btnIdx < this.depoNum; btnIdx++)
		{
			//textfiled depoName
			GuiTextField depoName = new GuiTextField(btnIdx, this.fontRendererObj, this.getGlobalX(27),  this.getGlobalY(73 + 10 * (btnIdx % this.maxDepoNumInPage)), 48, this.fontRendererObj.FONT_HEIGHT);
			depoName.setEnabled(false);
			depoName.setEnableBackgroundDrawing(false);
			depoName.setVisible(false);
			depoName.setMaxStringLength(20);
			depoName.setCanLoseFocus(true);
			depoName.setTextColor(5592405);
			String dpName = (depoList.getCompoundTagAt(btnIdx).hasKey("depoName"))? depoList.getCompoundTagAt(btnIdx).getString("depoName"): I18n.format(this.getUnlocalizedName() + ".defaultDepoName");
			depoName.setText(dpName);
			depoName.setCursorPosition(0);
			depoNames.add(depoName);

			//button depoDelete
			GuiButton bt = new GuiButton(btnIdx, this.getGlobalX(144), this.getGlobalY(73 + 10 * (btnIdx % this.maxDepoNumInPage)), 10, 9, this.textDeleteDepo);
			bt.visible = false;
			bt.enabled = false;
			bt.packedFGColour = this.fontRendererObj.getColorCode('c');
			this.buttonList.add(bt);
		}
		
		//reset button
		this.buttonList.add(this.resetButton = new GuiButton(btnIdx, this.getGlobalX(125), this.row(5), 45, 20, I18n.format(this.getUnlocalizedName() + ".resetButton")));
		if(this.depoNum == 0)
		{
			this.resetButton.enabled = false;
		}
	}
	
	/**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int parWidth, int parHeight, float partialTicks){
    	this.drawGui();
    	
    	//page changes
    	if(this.scrollbar.getStage() != this.pageCursor)
    	{
    		for(GuiTextField depoName: this.currentDepoNames)
    		{
    			depoName.setEnabled(false);
    			depoName.setFocused(false);
    			depoName.setVisible(false);
    		}
    		this.currentDepoNames.clear();
    		
    		for(GuiButton depoDelete: this.currentDepoDeletes)
    		{
    			depoDelete.visible = false;
    			depoDelete.enabled = false;
    		}
    		this.currentDepoDeletes.clear();
    		
    		this.pageCursor = this.scrollbar.getStage();
    	}
    	
    	//title
    	this.drawCenteredString(fontRendererObj, textTitle, this.getCenterX(), this.row(1), fontRendererObj.getColorCode('1'));
    	
    	//owner
    	int stop = fontRendererObj.drawString(textOwner + ": ", this.col(1), this.row(3), fontRendererObj.getColorCode('0'));
    	fontRendererObj.drawString(stringOwnerName, stop, this.row(3), fontRendererObj.getColorCode('8'));
    	
    	//depo
    	fontRendererObj.drawString(textDepository + " (" + I18n.format(this.getUnlocalizedName() + ".depoPage", this.pageCursor + 1, this.scrollbar.getStageNum()) + ")", this.col(1), this.row(6), fontRendererObj.getColorCode('0'));
    	int drawDepoNum = (this.scrollbar.isLastStage(this.pageCursor))? (this.depoNum - this.maxDepoNumInPage * this.pageCursor): this.maxDepoNumInPage;
    	int startDepo = this.pageCursor * this.maxDepoNumInPage;
    	for(int i = startDepo; i < drawDepoNum + startDepo; i++)
		{
    		//row index
    		fontRendererObj.drawString("#" + (i + 1),  this.col(1), this.getGlobalY(73 + 10 * (i % this.maxDepoNumInPage)), fontRendererObj.getColorCode('0'));
    		
    		//depo name
    		GuiTextField depoName = depoNames.get(i);
    		depoName.setVisible(true);
    		depoName.setEnabled(true);
    		MoGuiScreen.drawProportionTexturedRect(GuiIDCard.ACTIVETEXTFIELD, this.getGlobalX(27), this.getGlobalY(73 + 10 * (i % this.maxDepoNumInPage)), this.zLevel, 0, 0, 47, 8, 69, 8, 47, 8);
    		depoName.drawTextBox();
    		currentDepoNames.add(depoName);
    		
    		//depo position
    		NBTTagCompound depo = depoList.getCompoundTagAt(i);
    		BlockPos depoPos = BlockPos.fromLong(depo.getLong("depoPos"));
    		fontRendererObj.drawString(fontRendererObj.trimStringToWidth("(" + depoPos.getX() + ", " + depoPos.getY() + ", " + depoPos.getZ() + ")", 66), this.getGlobalX(78), this.getGlobalY(73 + 10 * (i % this.maxDepoNumInPage)), fontRendererObj.getColorCode('0'));
    		
    		//depo delete
    		GuiButton depoDelete = this.buttonList.get(i);
    		depoDelete.visible = true;
    		depoDelete.enabled = true;
    		currentDepoDeletes.add(depoDelete);
		}
    	
    	//card id
    	fontRendererObj.drawSplitString(stringCardID, this.col(1), this.row(15), this.col(17) - this.col(1), fontRendererObj.getColorCode('7'));
    	
    	//draw button
    	super.drawScreen(parWidth, parHeight, partialTicks);
    }
    
    public void drawGui()
    {
    	this.drawDefaultBackground();
    	MoGuiScreen.drawProportionTexturedRect(GuiIDCard.BGGUITEXTURE, this.offsetX, this.offsetY, this.zLevel, 0, 0, 176, 166, 256, 256, this.guiWidth, this.guiHeight);
    	this.scrollbar.drawScrollBar();
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException 
    {
    	for(GuiTextField depoName: currentDepoNames)
		{
			if(depoName.isFocused())	//if one of the text field is clicked
			{
				depoName.textboxKeyTyped(typedChar, keyCode);	//input
				depoList.getCompoundTagAt(depoName.getId()).setString("depoName", depoName.getText());	//save to NBT data
				return;
			}
		}
    	
    	//press 'e' or 'esc' to exit
    	super.keyTyped(typedChar, keyCode);
    		
    	if(keyCode == 200)	//key up
    	{
    		this.scrollbar.moveBackStage();
    	}
    	else if(keyCode == 208)	//key down
    	{
    		this.scrollbar.moveNextStage();
    	}
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException 
    {
    	super.mouseClicked(mouseX, mouseY, mouseButton);
    	
    	if(mouseButton == 0)
    	{
    		//clicks on the scrollbar gui
    		if(this.scrollbar.isScrollBarClicked(mouseX, mouseY))
    		{
    			this.scrollbar.mouseClicked(mouseX, mouseY);
    		}
    		//clicks on the scroll field
    		else if(this.scrollbar.isScrollFieldClicked(mouseX, mouseY))
    		{
    			this.scrollbar.scrollFieldClicked(mouseX, mouseY);
    		}
    		//buttons
    		else
    		{
    			//reset the depoList
    			if(this.resetButton.mousePressed(this.mc, mouseX, mouseY))
    			{
	    			this.cachedData.setTag("depoList", this.depoList = new NBTTagList());
    				
    	    		this.buttonList.clear();
    	    		this.initGui();
    				return;
    			}
    			
    			for(GuiButton depoDelete: this.currentDepoDeletes)
    			{
    				if(depoDelete.mousePressed(this.mc, mouseX, mouseY))
    				{
    					//remove the depo from depoList and the NBT (because 'depoList' is already the reference of the NBTTagList)
    					depoList.removeTag(depoDelete.id);
    					
    					//regenerate Guis
    					this.buttonList.clear();
    					this.initGui();
    					
    					return;
    				}
    			}
    			
    			for(GuiTextField depoName: this.currentDepoNames)
        		{
            		depoName.mouseClicked(mouseX, mouseY, mouseButton);
        		}
    		}
    	}    	
    }
    
    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) 
    {
    	super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    	
    	if(this.scrollbar.isDragged())
    	{
    		this.scrollbar.mouseClickMove(mouseX, mouseY);
    	}
    }
    
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) 
    {
    	super.mouseReleased(mouseX, mouseY, state);
    	
    	if(this.scrollbar.isDragged())
    	{
    		this.scrollbar.mouseReleased(mouseX, mouseY);
    	}
    }
    
    @Override
    public void mouseWheelMove(int wheelMove) 
    {
		this.scrollbar.mouseWheelMove(wheelMove);
    }
    
    @Override
    public void onGuiClosed() 
    {
    	Keyboard.enableRepeatEvents(false);
    	
    	//store data to the server
    	NBTTagCompound data = new NBTTagCompound();
    	if(!this.depoList.hasNoTags()) data.setTag("depoList", this.depoList);
    	data.setUniqueId("cardID", this.cachedData.getUniqueId("cardID"));
    	CommonProxy.guiChannel.sendToServer(new C2SGuiInputPacket(data, ID.GuiInput.GuiIDCard));
    }
}
