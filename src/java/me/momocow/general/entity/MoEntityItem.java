package me.momocow.general.entity;

import me.momocow.general.event.item.MoItemDestroyEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class MoEntityItem extends EntityItem
{
	public static final String RegistryName = "MoEntityItem";
    
	/**
	 * Required constructor by {@linkplain net.minecraftforge.fml.common.network.internal.EntitySpawnHandler#spawnEntity spawnEntity(EntitySpawnMessage spawnMsg)}
	 * @param world
	 */
	public MoEntityItem(World world)
	{
		super(world);
	}
	
    public MoEntityItem(World worldIn, Entity vanilla, ItemStack stack) 
    {
    	super(worldIn, vanilla.posX, vanilla.posY, vanilla.posZ, stack);
    	this.setPickupDelay(40);
    	this.setVelocity(vanilla.motionX, vanilla.motionY, vanilla.motionZ);
    	this.setThrower(((EntityItem)vanilla).getThrower());
    	this.setOwner(((EntityItem)vanilla).getOwner());
	}
    
    @Override
    public void setDead() {
    	super.setDead();
    	MinecraftForge.EVENT_BUS.post(new MoItemDestroyEvent(this));
    }
}
