package fr.neocraft.main.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class BlockMagma extends BlockBasicInfo{

	public BlockMagma(String name, Material p_i45394_1_) {
		super(name, p_i45394_1_);
		// TODO Auto-generated constructor stub
	}
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
    {
    	p_149670_5_.setFire(6);
    }
	
	

}
