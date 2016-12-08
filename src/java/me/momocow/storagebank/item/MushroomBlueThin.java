package me.momocow.storagebank.item;

import me.momocow.general.item.MoItem;
import me.momocow.storagebank.creativetab.CreativeTab;
import me.momocow.storagebank.reference.Reference;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MushroomBlueThin extends MoItem{
	private static final String NAME = "CropMushroomBlueThin";
	
	public MushroomBlueThin(){
		this.setCreativeTab(CreativeTab.MO_TAB);
		this.setUnlocalizedName(Reference.MOD_ID + "." + NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(64);
		
		//register to the game
		GameRegistry.register(this);
	}
}
