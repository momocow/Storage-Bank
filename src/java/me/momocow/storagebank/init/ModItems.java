package me.momocow.storagebank.init;

import java.util.ArrayList;
import java.util.List;

import me.momocow.general.item.MoItem;
import me.momocow.general.util.LogHelper;
import me.momocow.storagebank.item.IDCard;
import me.momocow.storagebank.item.MushroomBlueThin;
import me.momocow.storagebank.item.RawCard;
import me.momocow.storagebank.item.SorusBlueThin;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
	public static RawCard RawCard;
	public static IDCard IDCard;
	public static SorusBlueThin SorusBlueThin;
	public static MushroomBlueThin MushroomBlueThin;
	
	//item list
	private static List<MoItem> Items;
	
	public static void init() throws Exception
	{
		Items = new ArrayList<MoItem>();
		
		RawCard = (RawCard) initItem(RawCard.class);
		IDCard = (IDCard) initItem(IDCard.class);
		SorusBlueThin = (SorusBlueThin) initItem(SorusBlueThin.class);
		MushroomBlueThin = (MushroomBlueThin) initItem(MushroomBlueThin.class);
		
		LogHelper.info("Mod Items init... Done");
	}
	
	private static MoItem initItem(Class<? extends MoItem> itemClass) throws Exception
	{
		try{
			MoItem i = itemClass.newInstance();
			Items.add(i);
			return i;
		}
		catch (Exception e){
			LogHelper.info("EXCEPTION: instancing fail: "+itemClass);
			throw e;
		}
	}
	
	@SideOnly(Side.CLIENT)
    public static void initModels() 
	{
        for(MoItem item: Items){
        	item.initModel(); 
        }
        LogHelper.info("Item Models init... Done");
    }
}
