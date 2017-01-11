package me.momocow.general.entity.item;

import me.momocow.general.event.item.EnumMoItemDyingCause;
import me.momocow.general.event.item.MoItemDyingEvent;
import me.momocow.general.util.LogHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class MoEntityItem extends EntityItem
{
	public static final String RegistryName = "MoEntityItem";
	
	public MoEntityItem(World worldIn) {
		super(worldIn);
	}
	
    public MoEntityItem(World worldIn, double x, double y, double z, ItemStack stack)
    {
    	super(worldIn, x, y, z, stack);
    }
    
    public MoEntityItem(World worldIn, double x, double y, double z)
    {
    	super(worldIn, x, y, z);
    }
    
    @Override
	public boolean attackEntityFrom(DamageSource source, float amount) 
    {
    	LogHelper.info("attackEntityFrom");
		boolean ret = super.attackEntityFrom(source, amount);
		if(this.isDead)
		{
			MinecraftForge.EVENT_BUS.post(new MoItemDyingEvent(this, EnumMoItemDyingCause.DAMAGED));
		}
		
		return ret;
	}
    
    @Override
    public void setDead() {
    	// TODO Auto-generated method stub
    	super.setDead();

    	Thread.dumpStack();
    }
}
