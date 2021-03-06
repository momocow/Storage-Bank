package me.momocow.storagebank.init;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import me.momocow.mobasic.item.MoItem;
import me.momocow.storagebank.StorageBank;
import me.momocow.storagebank.item.DriedMushroomBlueThin;
import me.momocow.storagebank.item.IDCard;
import me.momocow.storagebank.item.MushroomBlueThin;
import me.momocow.storagebank.item.RawCard;
import me.momocow.storagebank.item.SorusBlueThin;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems 
{
	private static Logger logger = StorageBank.logger;
	
	public static RawCard RawCard;
	public static IDCard IDCard;
	public static SorusBlueThin SorusBlueThin;
	public static MushroomBlueThin MushroomBlueThin;
	public static DriedMushroomBlueThin DriedMushroomBlueThin;
	
	//item list
	private static List<MoItem> Items;
	
	public static void preinit() throws Exception
	{
		Items = new ArrayList<MoItem>();
		
		RawCard = (RawCard) initItem(RawCard.class);
		IDCard = (IDCard) initItem(IDCard.class);
		SorusBlueThin = (SorusBlueThin) initItem(SorusBlueThin.class);
		MushroomBlueThin = (MushroomBlueThin) initItem(MushroomBlueThin.class);
		DriedMushroomBlueThin = (DriedMushroomBlueThin) initItem(DriedMushroomBlueThin.class);
		
		logger.info("Mod Items pre-init... Done");
	}
	
	private static MoItem initItem(Class<? extends MoItem> itemClass) throws Exception
	{
		try{
			MoItem i = itemClass.newInstance();
			Items.add(i);
			return i;
		}
		catch (Exception e){
			logger.info("EXCEPTION: instancing fail: "+itemClass);
			throw e;
		}
	}
	
	@SideOnly(Side.CLIENT)
    public static void initModels() 
	{
        for(MoItem item: Items){
        	item.initModel(); 
        }
        logger.info("Item Models register... Done");
    }
}
