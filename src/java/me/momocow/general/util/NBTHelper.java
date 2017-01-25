package me.momocow.general.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper
{
	public static boolean hasKey(NBTTagCompound nbt, String key)
	{
		//if nbt.hasKey(key)==false
		//check if it is the key of uuid
		return (nbt.hasKey(key))? true: (nbt.hasKey(key + "Most") && nbt.hasKey(key + "Least"));
	}
	
	public static NBTTagCompound createDataTag(ItemStack stack, String modid)
	{
		if(stack != null)
		{
			if(!stack.hasTagCompound())
			{
				stack.setTagCompound(new NBTTagCompound());
			}
			
			NBTTagCompound ret = new NBTTagCompound();
			stack.getTagCompound().setTag(modid, ret);
			return ret;
		}
		
		return null;
	}
	
	public static NBTTagCompound getDataTag(ItemStack stack, String modid)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(modid))
		{
			return stack.getTagCompound().getCompoundTag(modid);
		}
		
		return null;
	}
	
	public static boolean hasDataTag(ItemStack stack)
	{
		return stack != null  && stack.hasTagCompound() && stack.getTagCompound().hasKey(Reference.MOD_ID);
	}
}
