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

public class BlockTopSideBottom extends Block{

	@SideOnly(Side.CLIENT)
	public IIcon top, bottom;
	
	private String topS, sideS, bottomS;
	
	
	@Override
	public int damageDropped(int meta) {
	    return meta;
	}
	
	public BlockTopSideBottom(Material p_i45394_1_, String name, String top, String side, String bottom) {
		super(p_i45394_1_);
		this.setBlockName(name);
		topS = top;
		sideS = side;
		bottomS = bottom;
		this.setCreativeTab(main.neocraftdeco);
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
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int s, int m)
    {
		switch(s) {
			case 0:
				return this.bottom;
			case 1:
				return this.top;
			default:
				return this.blockIcon;
		}
    }
	
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
		this.blockIcon = p_149651_1_.registerIcon(Reference.MOD_ID+":"+sideS);
		this.top = p_149651_1_.registerIcon(Reference.MOD_ID+":"+topS);
		this.bottom = p_149651_1_.registerIcon(Reference.MOD_ID+":"+bottomS);
    }
	

}
