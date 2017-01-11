package me.momocow.general.event.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.event.entity.item.ItemEvent;

/**
 * Fired when an EntityItem is dying
 * @author MomoCow
 */
public class MoItemDyingEvent extends ItemEvent
{
	private EnumMoItemDyingCause cause;
	
	public MoItemDyingEvent(EntityItem itemEntity, EnumMoItemDyingCause c) {
		super(itemEntity);
		
		this.cause = c;
	}
	
	public EnumMoItemDyingCause getCause()
	{
		return this.cause;
	}
}
