package fr.neocraft.main.Init;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.neocraft.main.Reference;
import fr.neocraft.main.main;
import fr.neocraft.main.block.BlockBarrierRed;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import fr.neocraft.main.block.BlockMoreHouse;
import fr.neocraft.main.block.BlockDoorHouse;

public class BlockMod {

	public static Block 
	BarrierRed, 
	BlockMoreHouse,
	BlockDoorHouse;
	
	public static void init()
    {
	
		BarrierRed = new BlockBarrierRed(Material.iron, 5).setCreativeTab(main.neocraft).setBlockName("BarrierRed").setBlockUnbreakable().setBlockTextureName(Reference.MOD_ID + ":BarrierRed" );
		BlockMoreHouse = new BlockMoreHouse(Material.iron, 5).setCreativeTab(main.neocraft).setBlockName("MoreHouse").setBlockUnbreakable();
		BlockDoorHouse = new BlockDoorHouse(Material.wood).setBlockName("BlockDoorHouse").setBlockTextureName(Reference.MOD_ID+":door_wood");
    }
	
	 public static void register()
	 {
	    	
	    	
	    
	    //	GameRegistry.registerTileEntity(TileEntityEnchantmentTableNull.class, neoreference.MOD_ID + ":TileEntityEnchantmentTableNull");
	    
			
			
			//GameRegistry.registerBlock(OnyxR_Block, OnyxR_Block.getUnlocalizedName().substring(5));
		 
		 GameRegistry.registerBlock(BarrierRed, BarrierRed.getUnlocalizedName().substring(5));
		 GameRegistry.registerBlock(BlockMoreHouse, BlockMoreHouse.getUnlocalizedName().substring(5));
		 GameRegistry.registerBlock(BlockDoorHouse,BlockDoorHouse.getUnlocalizedName().substring(5));
	 }
	
}
