package fr.neocraft.main.Init;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.neocraft.main.Reference;
import fr.neocraft.main.main;
import fr.neocraft.main.item.ItemBlockDoor;
import net.minecraft.item.Item;

public class ItemMod {
	
	
	public static Item[] itemreg;
	
	public static Item Copyright_NeoCraft, Seeds_Stick, ItemDoorHouse, ItemChangePnjAction;
	
	public static void Init()
    {
		itemreg = new Item[] {
				Copyright_NeoCraft = new Item().setUnlocalizedName("Copyright_NeoCraft").setCreativeTab(main.neocraft).setTextureName(Reference.MOD_ID + ":COP")
				,Seeds_Stick = new Item().setUnlocalizedName("Seeds_Stick").setCreativeTab(main.neocraft).setTextureName(Reference.MOD_ID + ":Seeds_Stick")
				,ItemDoorHouse = new ItemBlockDoor().setUnlocalizedName("ItemDoorHouse").setTextureName("door_wood")
		};
	}
	
	public static void register()
    {
	
		
		for(Item i:itemreg) {
			GameRegistry.registerItem(i, i.getUnlocalizedName().substring(5));
		}
		
    }
}
