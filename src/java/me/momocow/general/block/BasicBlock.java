package me.momocow.general.block;

import me.momocow.general.client.render.MoCustomModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

abstract public class BasicBlock extends Block implements MoCustomModel{
	//defualt material: ROCK type
	public BasicBlock(){
		this(Material.ROCK);
	}
	
	//specified material
	public BasicBlock(Material material){
		super(material);
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