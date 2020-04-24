package fr.neocraft.main.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockLum extends Block{

	public BlockLum(Material p_i45394_1_) {
		super(p_i45394_1_);
		// TODO Auto-generated constructor stub
	}
	public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderType()
    {
        return ClientProxy.RenderIdBarrierRed;
    }
    @SideOnly(Side.CLIENT)
    public float getAmbientOcclusionLightValue()
    {
        return 1.0F;
    }
    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
	{
	    return null;
	}
}
