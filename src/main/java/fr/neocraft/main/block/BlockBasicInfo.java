package fr.neocraft.main.block;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.neocraft.main.Reference;
import fr.neocraft.main.main;
import fr.neocraft.main.Init.BlockMod;
import net.minecraft.block.material.Material;

public class BlockBasicInfo extends BlockBasic{

	public BlockBasicInfo(String name, Material p_i45394_1_) {
		super(p_i45394_1_);
		this.setBlockName(name);
		this.setBlockTextureName(Reference.MOD_ID+":"+name);
		this.setCreativeTab(main.neocraftdeco);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int damageDropped(int meta) {
	    return meta;
	}
}
