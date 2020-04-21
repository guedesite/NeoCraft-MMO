package fr.neocraft.main.Init;


import cpw.mods.fml.common.registry.GameRegistry;
import fr.neocraft.main.Reference;
import fr.neocraft.main.main;
import fr.neocraft.main.item.ItemBlockDoor;
import fr.neocraft.main.item.ItemNeoMMOBow;
import fr.neocraft.main.item.ItemNeoMMOStick;
import fr.neocraft.main.item.ItemNeoMMOSword;
import net.minecraft.item.Item;

public class ItemMod {
	
	
	public static Item[] itemreg;
	
	public static Item MMOBow, MMOSword, MMOStick, Copyright_NeoCraft, Seeds_Stick, ItemDoorHouse, ItemChangeSkinPnjAction, ItemChangeModelPnjAction;
	
	public static void Init()
    {
		itemreg = new Item[] {
				Copyright_NeoCraft = new Item().setUnlocalizedName("Copyright_NeoCraft").setCreativeTab(main.neocraft).setTextureName(Reference.MOD_ID + ":COP")
				,Seeds_Stick = new Item().setUnlocalizedName("Seeds_Stick").setCreativeTab(main.neocraft).setTextureName(Reference.MOD_ID + ":Seeds_Stick")
				,ItemChangeSkinPnjAction = new Item().setUnlocalizedName("ItemChangeSkinPnjAction").setCreativeTab(main.neocraft).setTextureName(Reference.MOD_ID+":ItemChangeSkinPnjAction")
				,ItemChangeModelPnjAction = new Item().setUnlocalizedName("ItemChangeModelPnjAction").setCreativeTab(main.neocraft).setTextureName(Reference.MOD_ID+"::ItemChangeModelPnjAction")
				,ItemDoorHouse = new ItemBlockDoor(BlockMod.BlockDoor[0])
				,ItemDoorHouse = new ItemBlockDoor(BlockMod.BlockDoor[1])
				,ItemDoorHouse = new ItemBlockDoor(BlockMod.BlockDoor[2])
				,ItemDoorHouse = new ItemBlockDoor(BlockMod.BlockDoor[3])
				,ItemDoorHouse = new ItemBlockDoor(BlockMod.BlockDoor[4])
				,ItemDoorHouse = new ItemBlockDoor(BlockMod.BlockDoor[5])
				,ItemDoorHouse = new ItemBlockDoor(BlockMod.BlockDoor[6])
				,MMOBow = new ItemNeoMMOBow().setUnlocalizedName("Item MMO Bow")
				,MMOStick = new ItemNeoMMOStick().setUnlocalizedName("Item MMO Stick")
				,MMOStick = new ItemNeoMMOSword().setUnlocalizedName("Item MMO Sword")
														
		};
	}
	
	public static void register()
    {

		for(Item i:itemreg) {
			GameRegistry.registerItem(i, i.getUnlocalizedName().substring(5));
		}
		
    }
}
