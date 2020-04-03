package fr.neocraft.main.block;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.Init.BlockMod;
import fr.neocraft.main.proxy.ClientProxy;
import fr.neocraft.main.util.Vector3f;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockBarrierRed extends BlockBasic{

	public BlockBarrierRed(Material p_i45394_1_) {
		super(p_i45394_1_);
		this.setHarvestLevel("pickaxe", 20);
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

    public double getDistanceSqToEntity(EntityPlayer p_70068_1_, int x, int y, int z)
	   {
	        double d0 = x - p_70068_1_.posX;
	        double d2 = z - p_70068_1_.posZ;
	        double d3 = y - p_70068_1_.posY;
	        return d0 * d0 +  d2 * d2 + d3*d3;
	   }

    
    
    public ArrayList<Vector3f> SuppChaine(World w, int x, int y, int z, ArrayList<Vector3f> ar)
    {
    	ar.add(new Vector3f(x,y,z));
    	
    	for(int x2 = -1; x2 <2; x2++)
    	{
    		for(int y2 = -1; y2 <2; y2++)
        	{
    			for(int z2 = -1; z2 <2; z2++)
            	{
            		if(w.getBlock(x+x2, y+y2, z+z2) == BlockMod.BarrierRed)
            		{
            			boolean flag = true;
            			for(int i = 0; i < ar.size(); i++)
            			{
            				if(ar.get(i).isEqual(new Vector3f(x+x2, y+y2, z+z2)))
            				{
            					flag = false;
            				}
            			}
            			if(flag) {
            				ar = SuppChaine(w, x+x2, y+y2, z+z2, ar);
            			}
            		}
            	}
        	}
    	}
    	
    	return ar;
    }
}
