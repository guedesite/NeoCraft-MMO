package fr.neocraft.main.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockBasic extends Block
{

	public BlockBasic(Material p_i45394_1_)
	{
		super(p_i45394_1_);
	}
	
	 public int quantityDropped(Random p_149745_1_)
	    {
	        return 1;
	    }

	    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	    {
	        return Item.getItemFromBlock(this);
	    }
	
	public boolean isOpaqueCube()
	{
		return false;
	}
	@SideOnly(Side.CLIENT)
	public float getAmbientOcclusionLightValue()
	{
		return 1.0F;
	}
}
