package me.momocow.storagebank.item;

import java.util.List;

import me.momocow.mobasic.item.MoItem;
import me.momocow.storagebank.creativetab.CreativeTab;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MushroomBlueThin extends MoItem{
	private static final String NAME = "MushroomBlueThin";
	
	public MushroomBlueThin(){
		this.setCreativeTab(CreativeTab.TAB_StorageBank);
		this.setUnlocalizedName(Reference.MOD_ID + "." + NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(64);
		
		//register to the game
		GameRegistry.register(this);
	}
	
	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(TextFormatting.AQUA + I18n.format(getUnlocalizedName() + ".desc1"));
	}
}
