package me.momocow.storagebank.creativetab;

import me.momocow.storagebank.init.ModItems;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTab 
{
	public static final CreativeTabs TAB_StorageBank = new CreativeTabs (Reference.MOD_ID){
		@Override
		public Item getTabIconItem(){
			return ModItems.IDCard;
		}
	};
}
