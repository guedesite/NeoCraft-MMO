package fr.neocraft.main.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.Reference;
import fr.neocraft.main.main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockTopSideBottomMoveable extends Block{

	public String top, bottom, side;
	
    public BlockTopSideBottomMoveable(Material p_i45394_1_, String name, String Top, String Side, String Bottom) {
		super(p_i45394_1_);
		this.setCreativeTab(main.neocraftdeco);
		top=Top;
		side=Side;
		bottom=Bottom;
		this.setBlockName(name);
	}

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @SideOnly(Side.CLIENT)
    private IIcon bottomIcon;
    @SideOnly(Side.CLIENT)
    private IIcon topIcon;
	
	 @SideOnly(Side.CLIENT)
	    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
	    {
		 
		 	int k = p_149691_2_ & 7;
	        return k > 5 ? this.blockIcon : (p_149691_1_ == k ? this.topIcon : (p_149691_1_ == Facing.oppositeSide[k] ? this.bottomIcon : this.blockIcon));
	    }

	 @SideOnly(Side.CLIENT)
	    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
	    {
	        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
	        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 6));
	    }
	 
	 
	    @SideOnly(Side.CLIENT)
	    public void registerBlockIcons(IIconRegister p_149651_1_)
	    {
	        this.blockIcon = p_149651_1_.registerIcon(Reference.MOD_ID + ":"+side);
	        this.bottomIcon = p_149651_1_.registerIcon(Reference.MOD_ID+":"+bottom);
	        this.topIcon = p_149651_1_.registerIcon(Reference.MOD_ID+":"+top);
	    }
	    
	    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
	    {
	        int l = BlockPistonBase.determineOrientation(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_);
	        p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, l, 2);

	    }
	    
	  
}
