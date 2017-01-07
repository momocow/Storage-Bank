package me.momocow.general.block;

import me.momocow.general.client.render.MoCustomModel;
import me.momocow.storagebank.creativetab.CreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

abstract public class MoBlock extends Block implements MoCustomModel{
	//defualt material: ROCK type
	public MoBlock(){
		this(Material.ROCK);
	}
	
	//specified material
	public MoBlock(Material material){
		this(material, material.getMaterialMapColor());
	}
	
	public MoBlock(Material material, MapColor materialMapColor) {
		super(material, materialMapColor);
		this.setCreativeTab(CreativeTab.MO_TAB);
	}

	//item model/texture in the inventory
	@SideOnly(Side.CLIENT)
	@Override
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(
				Item.getItemFromBlock(this), 
				0, 
				new ModelResourceLocation(getRegistryName(), "inventory")
		);
	}
}