package me.momocow.general.event.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.event.entity.item.ItemEvent;

/**
 * Fired when an EntityItem is dying
 * @author MomoCow
 */
public class MoItemDestroyEvent extends ItemEvent
{
	public MoItemDestroyEvent(EntityItem itemEntity) {
		super(itemEntity);
	}
}
