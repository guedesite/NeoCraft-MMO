package fr.neocraft.main.block;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.Reference;
import fr.neocraft.main.main;
import fr.neocraft.main.Init.BlockMod;
import fr.neocraft.main.Server.HouseManager;
import fr.neocraft.main.Server.ServerPlayerData;
import fr.neocraft.main.util.Vector3f;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockMoreHouse extends BlockBasic {


	 @SideOnly(Side.CLIENT)
	 private IIcon arriere;
	 @SideOnly(Side.CLIENT)
	 private IIcon autre;
	
	 
	public BlockMoreHouse(Material p_i45394_1_, int level) {
		super(p_i45394_1_, level);
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int s, int m)
    {
		return s == m ? this.blockIcon : this.autre;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(Reference.MOD_ID+":BlockMoreHouse_front");
    
        this.autre = p_149651_1_.registerIcon(Reference.MOD_ID+":none");
    }
    
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (p_149727_1_.isRemote)
        {
            return true;
        }
        else
        {
        	ServerPlayerData data = main.AllPlayerServer.get(p_149727_5_.getCommandSenderName());
        	if(HouseManager.isUnderHouse(data))
        	{
	        	if(data.hasMoneyAndNotif(HouseManager.price[data.HouseBy]))
	        	{
	        		data.RemoveMoney(HouseManager.price[data.HouseBy]);
	        		data.HouseBy++;
	        		data.UpdateVal("HouseBy", data.HouseBy);
	        		by(p_149727_1_,p_149727_2_, p_149727_3_, p_149727_4_);
	        		
	        	}
	        	return true;
        	} else {
        		return false;
        	}
        }
      
    }
    
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
        int l = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 2, 2);
        }

        if (l == 1)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 5, 2);
        }

        if (l == 2)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 3, 2);
        }

        if (l == 3)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 4, 2);
        }

    }
	
	public void by(World w, int x, int y, int z)
	{
		 ArrayList<Vector3f> ar = new ArrayList<Vector3f>();
		 for(int x2 = -1; x2 <2; x2++)
	    	{
	    		for(int y2 = -1; y2 <2; y2++)
	        	{
	    			for(int z2 = -1; z2 <2; z2++)
	            	{
	            		if(w.getBlock(x+x2, y+y2, z+z2) == BlockMod.BarrierRed)
	            		{
	            			ar = ((BlockBarrierRed) w.getBlock(x+x2, y+y2, z+z2)).SuppChaine(w, x+x2, y+y2, z+z2, ar);
	            		}
	            	}
	        	}
	    	}
		 
		for(int i = 0; i < ar.size(); i++)
		{
			Vector3f v = ar.get(i);
			w.setBlockToAir(v.x, v.y, v.z);
		}
		w.setBlockToAir(x, y, z);
	}

}
