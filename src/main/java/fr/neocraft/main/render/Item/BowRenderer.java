package fr.neocraft.main.render.Item;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import fr.neocraft.main.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.IItemRenderer;

public class BowRenderer implements IItemRenderer {

	private Minecraft mc;
	private TextureManager texturemanager;
	
	
	public BowRenderer() {
		this.mc = Minecraft.getMinecraft();
		this.texturemanager = this.mc.getTextureManager();
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		EntityClientPlayerMP entity = (EntityClientPlayerMP)data[1];
		GL11.glPopMatrix();
		if(type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glPushMatrix(); 
			this.renderItem2(entity, item, 0);
			GL11.glPopMatrix();
			if (entity.getItemInUseCount() > 0)
			{
				GL11.glPushMatrix();
				this.renderItem2(entity, item, 1);
				GL11.glPopMatrix();
			}
		} else {
			GL11.glPushMatrix();
			float f2 = 3F - (1F/3F);
			GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
			GL11.glScalef(f2, f2, f2);
			GL11.glTranslatef(-0.25F, -0.1875F, 0.1875F);
			float f3 = 0.625F;
			GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
			GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(f3, -f3, f3);
			GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			this.renderItem(entity, item, 0);
			GL11.glPopMatrix();
		}
		GL11.glPushMatrix(); // prevents GL Underflow errors
	}

	private void renderItem(EntityClientPlayerMP par1EntityLiving, ItemStack par2ItemStack, int par3) {
		IIcon iicon = par1EntityLiving.getItemIcon(par2ItemStack, par3);
		if (iicon == null)
		{
			GL11.glPopMatrix();
			return;
		}
		texturemanager.bindTexture(texturemanager.getResourceLocation(par2ItemStack.getItemSpriteNumber()));
		Tessellator tessellator = Tessellator.instance;
		float f = iicon.getMinU();
		float f1 = iicon.getMaxU();
		float f2 = iicon.getMinV();
		float f3 = iicon.getMaxV();
		float f4 = 0.0F;
		float f5 = 0.3F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef(-f4, -f5, 0.0F);
		float f6 = 1.5F;
		GL11.glScalef(f6, f6, f6);
		GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
		ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}
	
	private void renderItem2(EntityClientPlayerMP par1EntityLiving, ItemStack par2ItemStack, int par3) {
		IIcon iicon = par1EntityLiving.getItemIcon(par2ItemStack, par3);
		if (iicon == null)
		{
			GL11.glPopMatrix();
			return;
		}
		texturemanager.bindTexture(texturemanager.getResourceLocation(par2ItemStack.getItemSpriteNumber()));
		Tessellator tessellator = Tessellator.instance;
		float f = iicon.getMinU();
		float f1 = iicon.getMaxU();
		float f2 = iicon.getMinV();
		float f3 = iicon.getMaxV();
		 GL11.glScalef(1F , 2F, 1F);
		if (par1EntityLiving.getItemInUseCount() > 0)
        {
			float f10 = (float)par2ItemStack.getMaxItemUseDuration() - ((float)par1EntityLiving.getItemInUseCount()  + 1.0F);
           float f11 = f10 / 10.0F;
            f11 = (f11 * f11 + f11 * 2.0F) / 3.0F;
        
            if (f11 > 0.5F)
            {
                f11 = 0.5F;
            }
	

	        float f12 = 1.0F + f11*7 ;
	
	    //    GL11.glRotatef(angle, 1F, 0.0F, 0.0F);
	      //  GL11.glRotatef(angle, 0F, 0F, 0F);
	        //GL11.glRotatef(angle, 0F, 0F, 0f);
	        ClientProxy.EntityRenderer.setReachZoom(f2 +1.0F, 0.01F);
	        
	        GL11.glRotatef(0.399999F * f12, 1F, 0F, 0F);
	        GL11.glRotatef(-0.030999F * f12, 0F, 1F, 0F);
	        GL11.glRotatef(1.53999F * f12, 0F, 0F, 1F);
	        GL11.glTranslatef(0F, 0.04F * f12, 0f);
	        GL11.glScalef(1.0F + 0.81999F * f12, 1.0F, f12);

        }
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    	
    		GL11.glTranslatef(0, -0.8F, 0.0F);
    		float f6 = 1.5F;
    		GL11.glScalef(f6, f6, f6);
    		GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
    		GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
    		GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
        
		
	
		ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}
}