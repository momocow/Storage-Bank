package me.momocow.general.client.gui;

public interface MoScrollable 
{
	public void drawScrollBar();
	public void mouseClicked(int mouseX, int mouseY);
	public void mouseClickMove(int mouseX, int mouseY) ;
	public void mouseReleased(int mouseX, int mouseY);
	public boolean isMouseClicked(int mouseX, int mouseY);
}
