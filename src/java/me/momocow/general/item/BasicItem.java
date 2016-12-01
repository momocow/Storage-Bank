package me.momocow.general.item;

import me.momocow.general.client.render.MoCustomModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

abstract public class BasicItem extends Item implements MoCustomModel{
	public BasicItem(){
		super();
	}
	
	@Override
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
