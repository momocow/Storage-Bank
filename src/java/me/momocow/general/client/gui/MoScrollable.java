package me.momocow.general.client.gui;

public interface MoScrollable 
{
	public void drawScrollBar();
	public void mouseClicked();
	public void mouseClickMove(int mouseX, int mouseY) ;
	public void mouseReleased();
	public boolean isMouseClicked(int mouseX, int mouseY);
}
