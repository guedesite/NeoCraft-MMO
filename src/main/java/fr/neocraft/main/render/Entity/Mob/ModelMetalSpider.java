package fr.neocraft.main.render.Entity.Mob;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMetalSpider  extends ModelBase
{
	    ModelRenderer Head;
	    ModelRenderer body;
	    ModelRenderer end;
	    ModelRenderer dent1;
	    ModelRenderer dent2;
	    ModelRenderer queue;
	    ModelRenderer RightLegs1A;
	    ModelRenderer RightLegs1B;
	    ModelRenderer RightLegs2A;
	    ModelRenderer RightLegs2B;
	    ModelRenderer RightLegs3A;
	    ModelRenderer RightLegs3B;
	    ModelRenderer LeftLegs1A;
	    ModelRenderer LeftLegs1B;
	    ModelRenderer LeftLegs2A;
	    ModelRenderer LeftLegs2B;
	    ModelRenderer LeftLegs3A;
	    ModelRenderer LeftLegs3B;
	  
	  public ModelMetalSpider()
	  {
	    textureWidth = 128;
	    textureHeight = 64;
	    
	      Head = new ModelRenderer(this, 54, 0);
	      Head.addBox(-12F, -4F, -4F, 12, 8, 8);
	      Head.setRotationPoint(-20F, 4F, 0F);
	      Head.setTextureSize(128, 64);
	      Head.mirror = true;
	      setRotation(Head, 0F, 0F, -0.4363323F);
	      body = new ModelRenderer(this, 20, 27);
	      body.addBox(0F, -5F, -5F, 44, 10, 10);
	      body.setRotationPoint(-22F, 4F, 0F);
	      body.setTextureSize(128, 64);
	      body.mirror = true;
	      setRotation(body, 0F, 0F, 0F);
	      end = new ModelRenderer(this, 94, 0);
	      end.addBox(0F, -3F, -3F, 3, 6, 6);
	      end.setRotationPoint(22F, 4F, 0F);
	      end.setTextureSize(128, 64);
	      end.mirror = true;
	      setRotation(end, 0F, 0F, 0F);
	      dent1 = new ModelRenderer(this, 112, 0);
	      dent1.addBox(-16F, 2F, 1.5F, 4, 0, 2);
	      dent1.setRotationPoint(-20F, 4F, 0F);
	      dent1.setTextureSize(128, 64);
	      dent1.mirror = true;
	      setRotation(dent1, 0F, 0F, -0.4363323F);
	      dent2 = new ModelRenderer(this, 112, 3);
	      dent2.addBox(-16F, 2F, -3.5F, 4, 0, 2);
	      dent2.setRotationPoint(-20F, 4F, 0F);
	      dent2.setTextureSize(128, 64);
	      dent2.mirror = true;
	      setRotation(dent2, 0F, 0F, -0.4363323F);
	      queue = new ModelRenderer(this, 112, 8);
	      queue.addBox(3F, -1F, -1F, 1, 2, 2);
	      queue.setRotationPoint(22F, 4F, 0F);
	      queue.setTextureSize(128, 64);
	      queue.mirror = true;
	      setRotation(queue, 0F, 0F, 0F);
	      RightLegs1A = new ModelRenderer(this, 0, 0);
	      RightLegs1A.addBox(-2F, -2F, -22F, 5, 5, 22);
	      RightLegs1A.setRotationPoint(-14F, 4F, -3F);
	      RightLegs1A.setTextureSize(128, 64);
	      RightLegs1A.mirror = true;
	      setRotation(RightLegs1A, -0.4363323F, 0F, 0F);
	      RightLegs1B = new ModelRenderer(this, 0, 27);
	      RightLegs1B.addBox(-1.5F, -7F, -20.5F, 4, 27, 4);
	      RightLegs1B.setRotationPoint(-14F, 4F, -3F);
	      RightLegs1B.setTextureSize(128, 64);
	      RightLegs1B.mirror = true;
	      setRotation(RightLegs1B, 0F, 0F, 0F);
	      RightLegs2A = new ModelRenderer(this, 0, 0);
	      RightLegs2A.addBox(-2F, -2F, -22F, 5, 5, 22);
	      RightLegs2A.setRotationPoint(0F, 4F, -3F);
	      RightLegs2A.setTextureSize(128, 64);
	      RightLegs2A.mirror = true;
	      setRotation(RightLegs2A, -0.4363323F, 0F, 0F);
	      RightLegs2B = new ModelRenderer(this, 0, 27);
	      RightLegs2B.addBox(-1.5F, -7F, -20.5F, 4, 27, 4);
	      RightLegs2B.setRotationPoint(0F, 4F, -3F);
	      RightLegs2B.setTextureSize(128, 64);
	      RightLegs2B.mirror = true;
	      setRotation(RightLegs2B, 0F, 0F, 0F);
	      RightLegs3A = new ModelRenderer(this, 0, 0);
	      RightLegs3A.addBox(-2F, -2F, -22F, 5, 5, 22);
	      RightLegs3A.setRotationPoint(14F, 4F, -3F);
	      RightLegs3A.setTextureSize(128, 64);
	      RightLegs3A.mirror = true;
	      setRotation(RightLegs3A, -0.4363323F, 0F, 0F);
	      RightLegs3B = new ModelRenderer(this, 0, 27);
	      RightLegs3B.addBox(-1.5F, -7F, -20.5F, 4, 27, 4);
	      RightLegs3B.setRotationPoint(14F, 4F, -3F);
	      RightLegs3B.setTextureSize(128, 64);
	      RightLegs3B.mirror = true;
	      setRotation(RightLegs3B, 0F, 0F, 0F);
	      LeftLegs1A = new ModelRenderer(this, 0, 0);
	      LeftLegs1A.addBox(-2F, -2F, 0F, 5, 5, 22);
	      LeftLegs1A.setRotationPoint(-14F, 4F, 3F);
	      LeftLegs1A.setTextureSize(128, 64);
	      LeftLegs1A.mirror = true;
	      setRotation(LeftLegs1A, 0.4363323F, 0F, 0F);
	      LeftLegs1B = new ModelRenderer(this, 0, 27);
	      LeftLegs1B.addBox(-1.5F, -7F, 16.5F, 4, 27, 4);
	      LeftLegs1B.setRotationPoint(-14F, 4F, 3F);
	      LeftLegs1B.setTextureSize(128, 64);
	      LeftLegs1B.mirror = true;
	      setRotation(LeftLegs1B, 0F, 0F, 0F);
	      LeftLegs2A = new ModelRenderer(this, 0, 0);
	      LeftLegs2A.addBox(-2F, -2F, 0F, 5, 5, 22);
	      LeftLegs2A.setRotationPoint(0F, 4F, 3F);
	      LeftLegs2A.setTextureSize(128, 64);
	      LeftLegs2A.mirror = true;
	      setRotation(LeftLegs2A, 0.4363323F, 0F, 0F);
	      LeftLegs2B = new ModelRenderer(this, 0, 27);
	      LeftLegs2B.addBox(-1.5F, -7F, 16.5F, 4, 27, 4);
	      LeftLegs2B.setRotationPoint(0F, 4F, 3F);
	      LeftLegs2B.setTextureSize(128, 64);
	      LeftLegs2B.mirror = true;
	      setRotation(LeftLegs2B, 0F, 0F, 0F);
	      LeftLegs3A = new ModelRenderer(this, 0, 0);
	      LeftLegs3A.addBox(-2F, -2F, 0F, 5, 5, 22);
	      LeftLegs3A.setRotationPoint(14F, 4F, 3F);
	      LeftLegs3A.setTextureSize(128, 64);
	      LeftLegs3A.mirror = true;
	      setRotation(LeftLegs3A, 0.4363323F, 0F, 0F);
	      LeftLegs3B = new ModelRenderer(this, 0, 27);
	      LeftLegs3B.addBox(-1.5F, -7F, 16.5F, 4, 27, 4);
	      LeftLegs3B.setRotationPoint(14F, 4F, 3F);
	      LeftLegs3B.setTextureSize(128, 64);
	      LeftLegs3B.mirror = true;
	      setRotation(LeftLegs3B, 0F, 0F, 0F);
	  }
	  
	  @Override
	  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	    Head.render(f5);
	    body.render(f5);
	    end.render(f5);
	    dent1.render(f5);
	    dent2.render(f5);
	    queue.render(f5);
	    RightLegs1A.render(f5);
	    RightLegs1B.render(f5);
	    RightLegs2A.render(f5);
	    RightLegs2B.render(f5);
	    RightLegs3A.render(f5);
	    RightLegs3B.render(f5);
	    LeftLegs1A.render(f5);
	    LeftLegs1B.render(f5);
	    LeftLegs2A.render(f5);
	    LeftLegs2B.render(f5);
	    LeftLegs3A.render(f5);
	    LeftLegs3B.render(f5);

	  }
	  
	  private void setRotation(ModelRenderer model, float x, float y, float z)
	  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }
	  
	  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	  {
		  Head.rotateAngleY = dent1.rotateAngleY = dent2.rotateAngleY = f3 / (180F / (float)Math.PI);
		  Head.rotateAngleX = dent1.rotateAngleX = dent2.rotateAngleX = f4 / (180F / (float)Math.PI);
	  //  System.out.println(f+" - "+f1+" - "+f2+" - "+f3+" - "+f4+" - "+f5);
	  }
}
