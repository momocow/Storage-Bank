package me.momocow.storagebank.item;

import me.momocow.general.item.BasicItem;
import me.momocow.storagebank.creativetab.CreativeTab;
import me.momocow.storagebank.reference.Reference;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class IDCard extends BasicItem{
	private static final String NAME = "IDCard";
	
	public IDCard(){
		this.setCreativeTab(CreativeTab.MO_TAB);
		this.setUnlocalizedName(Reference.MOD_ID + "." + NAME);
		this.setRegistryName(NAME);
		
		//register to the game
		GameRegistry.register(this);
	}
}
