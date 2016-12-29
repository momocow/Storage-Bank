package me.momocow.storagebank.client.render.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import me.momocow.general.client.gui.MoCenteredGuiScreen;
import me.momocow.general.client.gui.MoGuiScreen;
import me.momocow.general.client.gui.MoVanillaScrollBar;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiIDCard extends MoCenteredGuiScreen
{
	private final static ResourceLocation BGGUITEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/gui/idcard.png");
	private final static ResourceLocation ACTIVETEXTFIELD = new ResourceLocation(Reference.MOD_ID + ":textures/gui/depoNameActive.png");
	private final static String NAME = "GuiIDCard";
	
	//gui component
	private MoVanillaScrollBar scrollbar;
	private List<GuiTextField> depoNames = new ArrayList<GuiTextField>();
	private List<GuiButton> depoDeletes = new ArrayList<GuiButton>();
	
	//state
	private int depoCursor = 0;
	
	//label text
	private String textTitle;
	private String textOwner;
	private String textDepository;
	private String textDeleteDepo;
	
	//data
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
		
		//gui text init
		textTitle = I18n.format(getUnlocalizedName() + ".textTitle");
		textOwner = I18n.format(getUnlocalizedName() + ".textOwner");
		textDepository = I18n.format(getUnlocalizedName() + ".textDepositoryList");
		textDeleteDepo = "X";
		
		//data collection
		depoList = (NBTTagList)data.getTag("depoList");
		stringCardID = data.getUniqueId("cardID").toString();
		stringOwnerName = data.getString("ownerName");
	}
	
	/**
	 * gui component init 
	 */
	@Override
	public void initGui(){
		Keyboard.enableRepeatEvents(true);
		
		this.setCenter(width / 2, height / 2);
		
		int compoNum = 0;
		scrollbar = new MoVanillaScrollBar(compoNum++, this.getGlobalX(158), this.getGlobalY(73), this.zLevel, this.getGlobalY(127), 12, 15);
		
		this.depoNum = this.depoList.tagCount();
		this.scrollbar.isEnabled = false;
		if(this.depoNum > 7)
		{
			//enable the scrollbar
			this.scrollbar.isEnabled = true;
		}
		
		depoNames.clear();
		for(int i = 0; i < this.depoNum; i++)
		{
			GuiTextField depoName = new GuiTextField(compoNum++, this.fontRendererObj, this.getGlobalX(27),  this.getGlobalY(73 + 10 * i), 48, this.fontRendererObj.FONT_HEIGHT);
			depoName.setEnabled(true);
			depoName.setEnableBackgroundDrawing(false);
			depoName.setVisible(false);
			depoName.setMaxStringLength(12);
			depoName.setCanLoseFocus(true);
			depoName.setTextColor(11184810);
			depoName.setCursorPosition(0);
			depoNames.add(depoName);
		}
	}
	
	@Override
    public void updateScreen() {
    }
	
	/**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int parWidth, int parHeight, float partialTicks){
    	super.drawScreen(parWidth, parHeight, partialTicks);
    	
    	this.drawGui();
    	
    	//title
    	this.drawCenteredString(fontRendererObj, textTitle, this.getCenterX(), this.row(1), fontRendererObj.getColorCode('1'));
    	
    	//owner
    	int stop = fontRendererObj.drawString(textOwner + ": ", this.col(1), this.row(3), fontRendererObj.getColorCode('0'));
    	fontRendererObj.drawString(stringOwnerName, stop, this.row(3), fontRendererObj.getColorCode('8'));
    	
    	//depo
    	fontRendererObj.drawString(textDepository + " (" + I18n.format(this.getUnlocalizedName() + ".depoPage", this.depoCursor, Math.min(this.depoCursor + 6, this.depoNum), this.depoNum) + ")", this.col(1), this.row(6), fontRendererObj.getColorCode('0'));
    	int drawDepoNum = Math.min(this.depoNum, 7);
    	for(int i = 0; i < drawDepoNum; i++)
		{
    		fontRendererObj.drawString("#" + (i + 1),  this.col(1), this.getGlobalY(73 + 10 * i), fontRendererObj.getColorCode('0'));
    		
    		GuiTextField depoName = depoNames.get(i);
    		depoName.setVisible(true);
    		MoGuiScreen.drawTexturedRect(GuiIDCard.ACTIVETEXTFIELD, this.getGlobalX(27), this.getGlobalY(73 + 10 * i), this.zLevel, 0, 0, 47, 8, 69, 8, 47, 8);
    		depoName.drawTextBox();
    		
    		NBTTagCompound depo = depoList.getCompoundTagAt(i);
    		int[] depoPos = depo.getIntArray("depoPos");
    		fontRendererObj.drawString(fontRendererObj.trimStringToWidth("(" + depoPos[0] + ", " + depoPos[1] + ", " + depoPos[2] + ")", 66), this.getGlobalX(78), this.getGlobalY(73 + 10 * i), fontRendererObj.getColorCode('0'));
		}
    	
    	//card id
    	fontRendererObj.drawSplitString(stringCardID, this.col(1), this.row(15), this.col(17) - this.col(1), fontRendererObj.getColorCode('7'));
    }
    
    public void drawGui()
    {
    	this.drawDefaultBackground();
    	MoGuiScreen.drawTexturedRect(GuiIDCard.BGGUITEXTURE, this.offsetX, this.offsetY, this.zLevel, 0, 0, 176, 166, 256, 256, this.guiWidth, this.guiHeight);
    	this.scrollbar.drawScrollBar();
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    	super.keyTyped(typedChar, keyCode);
    	
    	for(GuiTextField depoName: depoNames)
		{
			if(depoName.isFocused()) depoName.textboxKeyTyped(typedChar, keyCode);
		}
    		
    	
    	if(keyCode == 18)	//press 'e' to exit
    	{
    		this.mc.displayGuiScreen((GuiScreen)null);

            if (this.mc.currentScreen == null)
            {
                this.mc.setIngameFocus();
            }
    	}
    	else if (keyCode == 44)
    	{
    		NBTTagCompound depo = new NBTTagCompound();
			depo.setUniqueId("depoID", MathHelper.getRandomUUID());
			depo.setString("depoName", "");
			Random r = new Random();
			depo.setIntArray("depoPos", new int[]{r.nextInt()%100000, r.nextInt()%256, r.nextInt()%100000});
			depoList.appendTag(depo);
			
			this.initGui();
    	}
    	else if (keyCode == 45)
    	{
    		this.mc.thePlayer.getHeldItemMainhand().getTagCompound().setTag("depoList", new NBTTagList());;
    		this.initGui();
    	}
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException 
    {
    	super.mouseClicked(mouseX, mouseY, mouseButton);
    	
    	if(mouseButton == 0)
    	{
    		if(this.scrollbar.isMouseClicked(mouseX, mouseY))
    		{
    			this.scrollbar.mouseClicked();
    		}

			for(GuiTextField depoName: depoNames)
			{
				depoName.mouseClicked(mouseX, mouseY, mouseButton);
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
    		this.scrollbar.mouseReleased();
    	}
    }
    
    @Override
    public void onGuiClosed() {
    	super.onGuiClosed();
    	Keyboard.enableRepeatEvents(false);
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
