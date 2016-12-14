package me.momocow.storagebank.item;

import java.util.List;

import me.momocow.general.item.MoItem;
import me.momocow.storagebank.creativetab.CreativeTab;
import me.momocow.storagebank.reference.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DriedMushroomBlueThin extends MoItem{
	private static String NAME = "DriedMushroomBlueThin";
	
	public DriedMushroomBlueThin()
	{
		this.setCreativeTab(CreativeTab.MO_TAB);
		this.setUnlocalizedName(Reference.MOD_ID + "." + NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(64);
		
		GameRegistry.register(this);
	}
	
	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(TextFormatting.AQUA + I18n.format(getUnlocalizedName() + ".desc1"));
	}
}
