package fr.neocraft.main.Init;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.neocraft.main.Reference;
import fr.neocraft.main.main;
import fr.neocraft.main.block.BlockBarrierRed;
import fr.neocraft.main.item.ItemMetadata;
import net.minecraft.block.Block;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import fr.neocraft.main.block.*;

public class BlockMod {

	
	public static Block[] blockreg, BlockDoor = new Block[7];
	
	public static Block 
	BarrierRed, 
	BlockMoreHouse,
	BlockDoorHouse,
	BlockNeoLogStripped,
	BlockIce,
	BlockCutRedSand,
	BlockRedSand,
	BlockChiseledRedSand,
	BlockBone,
	BlockPrismarine1,
	BlockPrismarine2,
	BlockPrismarine3,
	BlockPrismarine4,
	BlockDarkPrismarine,
	BlockredNetherBrick,
	BlockPrismarineBrick,
	Magma,
	BlockStairRedSand,
	sealantern,
	andesite,granite,diorite,
	nether_wart_block;
	
	public static void init()
    {
		blockreg = new Block[] {
				BarrierRed = new BlockBarrierRed(Material.iron).setCreativeTab(main.neocraft).setBlockName("BarrierRed").setBlockUnbreakable().setBlockTextureName(Reference.MOD_ID + ":BarrierRed" )
				,BlockMoreHouse = new BlockMoreHouse(Material.iron).setCreativeTab(main.neocraft).setBlockName("MoreHouse").setBlockUnbreakable()
				,BlockDoorHouse = new BlockDoorHouse(Material.wood).setBlockName("BlockDoorHouse").setBlockTextureName(Reference.MOD_ID+":door_wood").setBlockUnbreakable()
				,BlockNeoLogStripped = new BlockNeoLog("stripped")
				,BlockIce = new NeoBlockIce().setHardness(0.5F).setLightOpacity(3).setStepSound(Block.soundTypeGlass).setBlockName("frosted_ice").setBlockTextureName("frosted_ice")
				,BlockCutRedSand = new BlockTopSideBottom(Material.rock, "cut_red_sandstone", "red_sandstone_top", "cut_red_sandstone", "red_sandstone_bottom").setStepSound(Block.soundTypePiston).setHardness(0.8F)
				,BlockRedSand = new BlockTopSideBottom(Material.rock, "red_sandstone", "red_sandstone_top", "red_sandstone", "red_sandstone_bottom").setStepSound(Block.soundTypePiston).setHardness(0.8F)
				,BlockChiseledRedSand = new BlockTopSideBottom(Material.rock, "chiseled_red_sandstone", "red_sandstone_top", "chiseled_red_sandstone", "red_sandstone_bottom").setStepSound(Block.soundTypePiston).setHardness(0.8F)
				,BlockBone = new BlockTopSideBottomMoveable(Material.rock, "stripped_bone_block", "stripped_bone_block_top", "stripped_bone_block", "stripped_bone_block_top").setStepSound(Block.soundTypeStone).setHardness(1F)
				,BlockPrismarine1 = new BlockBasicInfo("prismarine_1",Material.rock).setStepSound(Block.soundTypeStone).setHardness(0.8F)
				,BlockPrismarine2 = new BlockBasicInfo("prismarine_2",Material.rock).setStepSound(Block.soundTypeStone).setHardness(0.8F)
				,BlockPrismarine3 = new BlockBasicInfo("prismarine_3",Material.rock).setStepSound(Block.soundTypeStone).setHardness(0.8F)
				,BlockPrismarine4 = new BlockBasicInfo("prismarine_4",Material.rock).setStepSound(Block.soundTypeStone).setHardness(0.8F)
				,BlockDarkPrismarine = new BlockBasicInfo("dark_prismarine",Material.rock).setStepSound(Block.soundTypeStone).setHardness(0.9F)
				,BlockredNetherBrick = new BlockBasicInfo("red_nether_bricks",Material.rock).setStepSound(Block.soundTypeStone).setHardness(1.1F)
				,BlockPrismarineBrick = new BlockBasicInfo("prismarine_bricks",Material.rock).setStepSound(Block.soundTypeStone).setHardness(0.9F)
				,Magma = new BlockMagma("magma",Material.lava).setStepSound(Block.soundTypeStone).setHardness(0.9F)
				,sealantern = new BlockBasicInfo("sea_lantern",Material.glass).setHardness(0.3F).setStepSound(Block.soundTypeGlass).setLightLevel(1.0F)
				,andesite = new BlockPolish("andesite")
				,granite = new BlockPolish("granite")
				,diorite = new BlockPolish("diorite")
				,nether_wart_block = new BlockBasicInfo("nether_wart_block",Material.rock).setStepSound(Block.soundTypeStone).setHardness(0.5F)
				,new BlockNeoStairs(BlockRedSand, 0)
				,new BlockNeoStairs(BlockPrismarine1, 0)
				,new BlockNeoStairs(BlockPrismarine2, 0)
				,new BlockNeoStairs(BlockPrismarine3, 0)
				,new BlockNeoStairs(BlockPrismarine4, 0)
				,new BlockNeoStairs(BlockDarkPrismarine, 0)
				,new BlockNeoStairs(BlockPrismarineBrick, 0)
				,new BlockNeoStairs(BlockBone, 0)
				,new BlockNeoStairs(Blocks.stone, 0)
				,new BlockNeoStairs(BlockredNetherBrick, 0)
				,new BlockNeoStairs(Blocks.sand, 0)
				,new BlockNeoStairs(Blocks.sand, 1)
				,new BlockNeoStairs(BlockCutRedSand, 0)
				,new BlockNeoStairs(Blocks.stone_slab, 0)
				,new BlockNeoStairs(Blocks.stonebrick, 0)
				,new BlockNeoStairs(Blocks.stonebrick, 1)
				,new BlockNeoStairs(Blocks.stonebrick, 2)
				,new BlockNeoStairs(Blocks.stonebrick, 3)
				,new BlockNeoStairs(sealantern, 0)
				,new BlockNeoStairs(andesite, 0)
				,new BlockNeoStairs(granite, 0)
				,new BlockNeoStairs(diorite, 0)
				,new BlockNeoStairs(andesite, 1)
				,new BlockNeoStairs(granite,1)
				,new BlockNeoStairs(diorite, 1)
				
				,new BlockNeoSlab(Blocks.stone, 0)
				,new BlockNeoSlab(Blocks.stonebrick, 0)
				,new BlockNeoSlab(Blocks.stonebrick, 1)
				,new BlockNeoSlab(Blocks.stonebrick, 2)
				,new BlockNeoSlab(Blocks.stonebrick, 3)
				,new BlockNeoSlab(Blocks.sand, 0)
				,new BlockNeoSlab(Blocks.sand, 1)
				,new BlockNeoSlab(BlockPrismarine1, 0)
				,new BlockNeoSlab(BlockPrismarine2, 0)
				,new BlockNeoSlab(BlockPrismarine3, 0)
				,new BlockNeoSlab(BlockPrismarine4, 0)
				,new BlockNeoSlab(BlockRedSand, 0)
				,new BlockNeoSlab(BlockDarkPrismarine, 0)
				,new BlockNeoSlab(BlockPrismarineBrick, 0)
				,new BlockNeoSlab(BlockBone, 0)
				,new BlockNeoSlab(sealantern, 0)
				,new BlockNeoSlab(BlockIce, 0)
				,new BlockNeoSlab(BlockIce, 1)
				,new BlockNeoSlab(BlockIce, 2)
				,new BlockNeoSlab(BlockIce, 3)
				,new BlockNeoSlab(Blocks.ice, 3)
				,new BlockNeoSlab(BlockredNetherBrick, 0)
				,new BlockNeoSlab(andesite, 0)
				,new BlockNeoSlab(granite, 0)
				,new BlockNeoSlab(diorite, 0)
				,new BlockNeoSlab(andesite, 1)
				,new BlockNeoSlab(granite, 1)
				,new BlockNeoSlab(diorite, 1)
				
				,new BlockNeoWall(Blocks.stone, 0)
				,new BlockNeoWall(Blocks.stonebrick, 0)
				,new BlockNeoWall(Blocks.stonebrick, 1)
				,new BlockNeoWall(Blocks.stonebrick, 2)
				,new BlockNeoWall(Blocks.stonebrick, 3)
				,new BlockNeoWall(BlockPrismarine1, 0)
				,new BlockNeoWall(BlockPrismarine2, 0)
				,new BlockNeoWall(BlockPrismarine3, 0)
				,new BlockNeoWall(BlockPrismarine4, 0)
				,new BlockNeoWall(BlockDarkPrismarine, 0)
				,new BlockNeoWall(BlockPrismarineBrick, 0)
				,new BlockNeoWall(BlockBone, 0)
				,new BlockNeoWall(BlockIce, 0)
				,new BlockNeoWall(BlockIce, 1)
				,new BlockNeoWall(BlockIce, 2)
				,new BlockNeoWall(BlockIce, 3)
				,new BlockNeoWall(Blocks.ice, 3)
				,new BlockNeoWall(BlockredNetherBrick, 3)
				,new BlockNeoWall(Blocks.sandstone, 3)
				
				,BlockDoor[0] = new BlockNeoDoor("acacia_door")
				,BlockDoor[1] = new BlockNeoDoor("birch_door")
				,BlockDoor[2] = new BlockNeoDoor("dark_oak_door")
				,BlockDoor[3] = new BlockNeoDoor("iron_door")
				,BlockDoor[4] = new BlockNeoDoor("jungle_door")
				,BlockDoor[5] = new BlockNeoDoor("oak_door")
				,BlockDoor[6] = new BlockNeoDoor("spruce_door")
				
				
		};
	}
	
	 public static void register()
	 {
	    	
	    	
	    
	    //	GameRegistry.registerTileEntity(TileEntityEnchantmentTableNull.class, neoreference.MOD_ID + ":TileEntityEnchantmentTableNull");
	    
			
			
			//GameRegistry.registerBlock(OnyxR_Block, OnyxR_Block.getUnlocalizedName().substring(5));

		 for(Block b:blockreg) {
			 if(b instanceof BlockNeoLog ||  b instanceof NeoBlockIce || b instanceof BlockPolish)
			 {
				 GameRegistry.registerBlock(b, ItemMetadata.class, b.getUnlocalizedName().substring(5));
			 } else {
				 GameRegistry.registerBlock(b, b.getUnlocalizedName().substring(5));
			 }
		 }
	//	 blockreg = null;
	 }

	
}
