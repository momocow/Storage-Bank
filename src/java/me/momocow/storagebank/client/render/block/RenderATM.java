package me.momocow.storagebank.client.render.block;

import me.momocow.storagebank.client.model.ModelATM;
import me.momocow.storagebank.reference.Reference;
import me.momocow.storagebank.tileentity.TileEntityATM;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderATM extends TileEntitySpecialRenderer<TileEntityATM>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/block/BlockATM.png");
	private ModelATM model;
	
	public RenderATM() 
	{
		this.model = new ModelATM();
	}
	
	@Override
	public void renderTileEntityAt(TileEntityATM te, double x, double y, double z, float partialTicks,
			int destroyStage) 
	{
		int meta = te.getRenderMetadata();
		
		float angle = 0F;
		
		switch(meta)
		{
			case 0:	//North
				angle = 0F;
				break;
			case 1:	//South
				angle = 180F;
				break;
			case 2:	//West
				angle = -90F;
				break;
			case 3:	//East
				angle = 90F;
				break;
		}
		
		this.bindTexture(TEXTURE);
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
		GlStateManager.rotate(180F, 0F, 0F, 1F);
		GlStateManager.rotate(angle, 0F, 1F, 0F);
		this.model.render(0.0625F);
		GlStateManager.popMatrix();
	}
}
