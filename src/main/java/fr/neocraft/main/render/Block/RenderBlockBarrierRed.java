package fr.neocraft.main.render.Block;


import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import fr.neocraft.main.proxy.ClientProxy;
import net.minecraft.block.Block;

import net.minecraft.client.renderer.RenderBlocks;

import net.minecraft.world.IBlockAccess;

public class RenderBlockBarrierRed implements ISimpleBlockRenderingHandler{
	
	@Override
	  public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	  {

	  }


	  @Override
	  public boolean renderWorldBlock(IBlockAccess world, int p_147736_2_, int p_147736_3_, int p_147736_4_, Block block, int modelId, RenderBlocks renderer)
	  {

	        return true;

	  }
	  
	   
	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return ClientProxy.RenderIdBarrierRed;
	}
	
}
