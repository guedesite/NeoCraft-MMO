package fr.neocraft.main.render.Entity.Mob;

import org.lwjgl.opengl.GL11;

import fr.neocraft.main.Reference;
import fr.neocraft.main.proxy.ClientProxy;
import fr.neocraft.main.util.PosVec3DHelper;
import fr.neocraft.main.util.Vector3d;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderMetalSpider extends RenderLiving{

	private static ResourceLocation res = new ResourceLocation(Reference.MOD_ID+":textures/entity/mob/MetalSpider.png");
	
	public RenderMetalSpider() {
		super(new ModelMetalSpider(), 2);

	}
	
	
	@Override
	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		 float f2 = this.interpolateRotation(p_76986_1_.prevRenderYawOffset, p_76986_1_.renderYawOffset, p_76986_9_);
	    GL11.glPushMatrix();
	//    GL11.glLoadIdentity();
	    GL11.glTranslated(p_76986_2_,p_76986_4_+0.5, p_76986_6_);
	  //.  GL11.glLineWidth(ClientProxy.GuiClientManager.x);
	    GL11.glBegin(GL11.GL_LINES);
	
	    Vector3d[] all = PosVec3DHelper.Create3DCubeAtBase(new Vector3d(20,10,20));
	    
	    GL11.glColor3f(1, 0, 0);
	    
	    for(Vector3d v:all) {
	    	PosVec3DHelper.RotateArroundBase(v,Math.toRadians(337.5D-f2));
	    	 GL11.glVertex3d(v.x, v.y-5, v.z);
	    }
	    all = PosVec3DHelper.Create3DCubeAtBase(new Vector3d(20,10,20));
	    for(Vector3d v:all) {
	    	PosVec3DHelper.RotateArroundBase(v,Math.toRadians(292.5D-f2));
	    	 GL11.glVertex3d(v.x, v.y-5, v.z);
	    }
	    all = PosVec3DHelper.Create3DCubeAtBase(new Vector3d(20,10,20));
	    GL11.glColor3f(0, 1, 0);
	    for(Vector3d v:all) {
	    	 GL11.glVertex3d(v.x, v.y-5, v.z);
	    }
	    
	    GL11.glColor3f(1, 1, 1);

	    GL11.glEnd();
	    GL11.glTranslated(-p_76986_2_, -p_76986_4_+0.5, -p_76986_6_);
	    GL11.glPopMatrix();
		
		super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
	
	 private float interpolateRotation(float p_77034_1_, float p_77034_2_, float p_77034_3_)
	    {
	        float f3;

	        for (f3 = p_77034_2_ - p_77034_1_; f3 < -180.0F; f3 += 360.0F)
	        {
	            ;
	        }

	        while (f3 >= 180.0F)
	        {
	            f3 -= 360.0F;
	        }

	        return p_77034_1_ + p_77034_3_ * f3;
	    }
	
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return res;
	}
	
	@Override
	public void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
    {
     GL11.glRotatef(90F, 0, -1, 0);
    }

}
