package fr.neocraft.main.block;

import fr.neocraft.main.main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockNeoStairs extends BlockStairs {

	public BlockNeoStairs(Block p_i45428_1_, int p_i45428_2_) {
		super(p_i45428_1_, p_i45428_2_);
		this.setBlockName(p_i45428_1_.getUnlocalizedName().substring(5)+"_stairs"+p_i45428_2_);
		this.setCreativeTab(main.neocraftdecoStair);
	}

}
