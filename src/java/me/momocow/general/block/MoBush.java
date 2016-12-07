package me.momocow.general.block;

import me.momocow.general.client.render.MoCustomModel;
import me.momocow.storagebank.creativetab.CreativeTab;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public abstract class MoBush extends BlockBush implements MoCustomModel
{
	public MoBush()
	{
		this(Material.PLANTS);
	}
	
	protected MoBush(Material materialIn)
    {
		this(materialIn, materialIn.getMaterialMapColor());
    }

    protected MoBush(Material materialIn, MapColor mapColorIn)
    {
    	super(materialIn, mapColorIn);
    	this.setTickRandomly(true);
        this.setCreativeTab(CreativeTab.MO_TAB);
    }
	
	public void initModel() 
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
	}
}
