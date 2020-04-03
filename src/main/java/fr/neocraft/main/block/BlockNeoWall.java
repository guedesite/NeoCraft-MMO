package fr.neocraft.main.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class BlockNeoWall extends BlockWall{

	private int meta;
	private Block b;
	public BlockNeoWall(Block p_i45435_1_, int m) {
		super(p_i45435_1_);
		meta = m;
		b=p_i45435_1_;
		this.setBlockName(p_i45435_1_.getUnlocalizedName().substring(5)+"_wall"+m);
		this.setCreativeTab(main.neocraftdeco);

	}

	
	 @SideOnly(Side.CLIENT)
	    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
	    {
	        return b.getIcon(p_149691_1_, meta);
	    }
}
