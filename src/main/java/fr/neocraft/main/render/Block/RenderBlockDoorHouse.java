package fr.neocraft.main.render.Block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import fr.neocraft.main.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderBlockDoorHouse implements ISimpleBlockRenderingHandler{
	
	@Override
	  public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	  {

	  }


	  @Override
	  public boolean renderWorldBlock(IBlockAccess blockAccess, int p_147760_2_, int p_147760_3_, int p_147760_4_, Block p_147760_1_, int modelId, RenderBlocks renderer)
	  {
		  Tessellator tessellator = Tessellator.instance;
	        int l = blockAccess.getBlockMetadata(p_147760_2_, p_147760_3_, p_147760_4_);
	        if ((l & 8) != 0)
	        {
	            if (blockAccess.getBlock(p_147760_2_, p_147760_3_ - 1, p_147760_4_) != p_147760_1_)
	            {
	                return false;
	            }
	        }
	        else if (blockAccess.getBlock(p_147760_2_, p_147760_3_ + 1, p_147760_4_) != p_147760_1_)
	        {
	            return false;
	        }
	        float f = 0.5F;
	        float f1 = 1.0F;
	        float f2 = 0.8F;
	        float f3 = 0.6F;
	        int i1 = p_147760_1_.getMixedBrightnessForBlock(blockAccess, p_147760_2_, p_147760_3_, p_147760_4_);
	        tessellator.setBrightness(renderer.renderMinY > 0.0D ? i1 : p_147760_1_.getMixedBrightnessForBlock(blockAccess, p_147760_2_, p_147760_3_ - 1, p_147760_4_));
	        tessellator.setColorOpaque_F(f, f, f);
	        renderer.renderFaceYNeg(p_147760_1_, (double)p_147760_2_, (double)p_147760_3_, (double)p_147760_4_, renderer.getBlockIcon(p_147760_1_, blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 0));
	        tessellator.setBrightness(renderer.renderMaxY < 1.0D ? i1 : p_147760_1_.getMixedBrightnessForBlock(blockAccess, p_147760_2_, p_147760_3_ + 1, p_147760_4_));
	        tessellator.setColorOpaque_F(f1, f1, f1);
	        renderer.renderFaceYPos(p_147760_1_, (double)p_147760_2_, (double)p_147760_3_, (double)p_147760_4_, renderer.getBlockIcon(p_147760_1_, blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 1));
	        tessellator.setBrightness(renderer.renderMinZ > 0.0D ? i1 : p_147760_1_.getMixedBrightnessForBlock(blockAccess, p_147760_2_, p_147760_3_, p_147760_4_ - 1));
	        tessellator.setColorOpaque_F(f2, f2, f2);
	        IIcon iicon = renderer.getBlockIcon(p_147760_1_, blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 2);
	        renderer.renderFaceZNeg(p_147760_1_, (double)p_147760_2_, (double)p_147760_3_, (double)p_147760_4_, iicon);
	        renderer.flipTexture = false;
	        tessellator.setBrightness(renderer.renderMaxZ < 1.0D ? i1 : p_147760_1_.getMixedBrightnessForBlock(blockAccess, p_147760_2_, p_147760_3_, p_147760_4_ + 1));
	        tessellator.setColorOpaque_F(f2, f2, f2);
	        iicon = renderer.getBlockIcon(p_147760_1_, blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 3);
	        renderer.renderFaceZPos(p_147760_1_, (double)p_147760_2_, (double)p_147760_3_, (double)p_147760_4_, iicon);
	        renderer.flipTexture = false;
	        tessellator.setBrightness(renderer.renderMinX > 0.0D ? i1 : p_147760_1_.getMixedBrightnessForBlock(blockAccess, p_147760_2_ - 1, p_147760_3_, p_147760_4_));
	        tessellator.setColorOpaque_F(f3, f3, f3);
	        iicon = renderer.getBlockIcon(p_147760_1_, blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 4);
	        renderer.renderFaceXNeg(p_147760_1_, (double)p_147760_2_, (double)p_147760_3_, (double)p_147760_4_, iicon);
	        renderer.flipTexture = false;
	        tessellator.setBrightness(renderer.renderMaxX < 1.0D ? i1 : p_147760_1_.getMixedBrightnessForBlock(blockAccess, p_147760_2_ + 1, p_147760_3_, p_147760_4_));
	        tessellator.setColorOpaque_F(f3, f3, f3);
	        iicon = renderer.getBlockIcon(p_147760_1_, blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 5);
	        renderer.renderFaceXPos(p_147760_1_, (double)p_147760_2_, (double)p_147760_3_, (double)p_147760_4_, iicon);
	        renderer.flipTexture = false;
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
		return ClientProxy.RenderIdBlockDoorHouse;
	}
	
}
