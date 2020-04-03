package fr.neocraft.main.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemMetadata extends ItemBlockWithMetadata
{
	 public ItemMetadata(Block block) {
        super(block, block);
	 }
	 @Override
	 public String getUnlocalizedName(ItemStack stack) {
	     return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	 }
}
