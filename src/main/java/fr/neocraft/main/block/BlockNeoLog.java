package fr.neocraft.main.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.Reference;
import fr.neocraft.main.main;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockNeoLog extends BlockLog{

	public BlockNeoLog(String name) {
		super();
		this.setCreativeTab(main.neocraftdeco);
		this.setBlockTextureName(name);
		this.setBlockName("NeologStripped");
	}
	@Override
	public int damageDropped(int meta) {
	    return meta;
	}
	
	
	public static final String[] field_150168_M = new String[] {"acacia_log", "birch_log", "dark_oak", "jungle_log", "oak_log", "spruce_log"};

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 3));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 12));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 13));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 14));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 15));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.field_150167_a = new IIcon[field_150168_M.length];
        this.field_150166_b = new IIcon[field_150168_M.length];

        for (int i = 0; i < this.field_150167_a.length; ++i)
        {
            this.field_150167_a[i] = p_149651_1_.registerIcon(Reference.MOD_ID+":"+this.getTextureName() + "_" + field_150168_M[i]);
            this.field_150166_b[i] = p_149651_1_.registerIcon(Reference.MOD_ID+":"+this.getTextureName() + "_" + field_150168_M[i] + "_top");
        }
    }

    
}
