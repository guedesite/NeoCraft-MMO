package fr.neocraft.main.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.Reference;
import fr.neocraft.main.main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockPolish extends Block {

	 @SideOnly(Side.CLIENT)
	public IIcon polish;
	
	public BlockPolish(String name)
	{
		super(Material.rock);
		this.setBlockName(name);
		this.setHardness(0.7F);
		this.setCreativeTab(main.neocraftdeco);
	}
	
	@Override
	public int damageDropped(int meta) {
	    return meta;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		 this.blockIcon = p_149651_1_.registerIcon(Reference.MOD_ID+":"+this.getUnlocalizedName().substring(5));
		 this.polish = p_149651_1_.registerIcon(Reference.MOD_ID+":polished_"+this.getUnlocalizedName().substring(5));
		  
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return p_149691_2_ == 0 ? this.blockIcon: this.polish;
    }
	
	
	 @SideOnly(Side.CLIENT)
	    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
	    {
	        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
	        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
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
