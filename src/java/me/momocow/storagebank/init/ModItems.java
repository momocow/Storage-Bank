package me.momocow.storagebank.init;

import java.util.ArrayList;
import java.util.List;

import me.momocow.general.item.BasicItem;
import me.momocow.general.util.LogHelper;
import me.momocow.storagebank.item.IDCard;
import me.momocow.storagebank.item.RawCard;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
	public static BasicItem RawCard;
	public static BasicItem IDCard;
	public int[] a =null;
	
	//item list
	private static List<BasicItem> Items;
	
	public static void init() throws Exception{
		Items = new ArrayList<BasicItem>();
		
		RawCard = initItem(RawCard.class);
		IDCard = initItem(IDCard.class);
		LogHelper.info("\tMod Items init... Done");
	}
	
	private static BasicItem initItem(Class<? extends BasicItem> itemClass) throws Exception{
		try{
			BasicItem i = itemClass.newInstance();
			Items.add(i);
			return i;
		}
		catch (Exception e){
			LogHelper.info("EXCEPTION: instancing fail: "+itemClass);
			throw e;
		}
	}
	
	@SideOnly(Side.CLIENT)
    public static void initModels() {
        for(BasicItem item: Items){
        	item.initModel(); 
        }
        LogHelper.info("\tItem Models init... Done");
    }
}
