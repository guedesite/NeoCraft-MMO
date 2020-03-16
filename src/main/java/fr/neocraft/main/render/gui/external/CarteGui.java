package fr.neocraft.main.render.gui.external;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import fr.neocraft.main.Reference;
import fr.neocraft.main.main;
import fr.neocraft.main.Server.EnumSound;
import fr.neocraft.main.Server.SoundManager;
import fr.neocraft.main.util.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class CarteGui extends GuiScreen {

	private ResourceLocation textures = new ResourceLocation(Reference.MOD_ID,"textures/gui/carte/carte.png");
	private ResourceLocation arrow = new ResourceLocation(Reference.MOD_ID,"textures/gui/carte/carteArrow.png");
	private ResourceLocation map = new ResourceLocation(Reference.MOD_ID,"textures/gui/carte/carteMap.png");
	private ResourceLocation player = new ResourceLocation(Reference.MOD_ID,"textures/gui/carte/cartePlayer.png");
	private ResourceLocation pos = new ResourceLocation(Reference.MOD_ID,"textures/gui/carte/pos.png");
	private int xSize = 256, ySize = 256;
	private double debutx =0, debuty = 0;
	private float scale = 1F;
	private float scaleMap = 1F;
	protected double zLevel;
	
	private double xpos;
	private double zpos;
	public double MapSize = 256;
	 
	
	public CarteGui() {
		SoundManager.PlaySound(EnumSound.NeoMOpen.getSound());
		xpos = Minecraft.getMinecraft().thePlayer.posX;
		zpos = Minecraft.getMinecraft().thePlayer.posZ;
	}
	
	@Override
    public void onGuiClosed() {
    	SoundManager.PlaySound(EnumSound.NeoMClose.getSound());
    }
	
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {

		
			MapSize += Mouse.getDWheel() *-0.04;
			if(256 < MapSize )
			{
				MapSize = 256;
			} else if(MapSize < 50)
			{
				MapSize = 50;
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        GL11.glPushMatrix();
	        scale=(height) / ((float)270);
	        if(scale < 1)
	        {
	        	scale = 1f;
	        }
	     
	       GL11.glScalef(scale, scale, 1F);
		  	debuty = (height - ySize*scale) / 2;
		  	debutx  =  (width - xSize* scale) / (2*scale);
		   GL11.glTranslated(debutx, debuty, 0);
		   GL11.glScalef(1F/scale, 1F/scale, 1F);
		   debuty = 0;
		   debutx = 0;

		   scaleMap = (float) ((256 * scale) /MapSize);
		   mc.getTextureManager().bindTexture(map);
		   
		   GL11.glScalef(scaleMap,scaleMap, 1F);
		   
		  double debutMapx = (xpos - ((MapSize*8)/2)*9.809570313 > -10000 ? (xpos + ((MapSize*8)/2)*9.809570313 < 10000 ? ((xpos+10000)*2048)/20000 - ((MapSize*8)/2) : 2048-(MapSize*8)) : 0) / 8; 
		  double debutMapy = (zpos - ((MapSize*8)/2)*9.809570313 > -10000 ? (zpos + ((MapSize*8)/2)*9.809570313 < 10000 ? ((zpos+10000)*2048)/20000 - ((MapSize*8)/2): 2048-(MapSize*8)) : 0) / 8; 
		  double playerx = (((((xpos+10000)*2048)/20000) / 8) - debutMapx)*scaleMap;
		  double playery = (((((zpos+10000)*2048)/20000) / 8) - debutMapy)*scaleMap;

		   drawTexturedModalRect(0,0, debutMapx, debutMapy, MapSize, MapSize);
		    

		    GL11.glScalef(1F/scaleMap, 1F/scaleMap, 1F);
		    
		    mc.getTextureManager().bindTexture(player);
		    GL11.glScalef(0.03125F, 0.03125F, 1F);
		    drawTexturedModalRect(playerx *32 -4,playery*32-4, 0, 0, 256, 256);
		    GL11.glScalef(32F, 32F, 1F);
		    //mc.getTextureManager().deleteTexture(player);
		    if(main.AllPlayer.get(mc.thePlayer.getCommandSenderName()).posMap != null)
		    {
		    	mc.getTextureManager().bindTexture(pos);
		    	for(Vector3f v:main.AllPlayer.get(mc.thePlayer.getCommandSenderName()).posMap)
		    	{
		    		drawTexturedModalRect((((((v.x+10000)*2048)/20000) / 8) - debutMapx)*scaleMap-8,(((((v.z+10000)*2048)/20000) / 8) - debutMapy)*scaleMap-8, 0, 0, 16, 16);
		    	}
		    	//mc.getTextureManager().deleteTexture(pos);
		    }
		  
		    GL11.glScalef(scale, scale, 1F);
		    mc.getTextureManager().bindTexture(textures);
		    drawTexturedModalRect(0,0, 0, 0, xSize,ySize);
		    GL11.glScalef(1F/scale, 1F/scale, 1F);

		    
	        GL11.glPopMatrix();
	}
	
	private void drawTexturedModalRect(double p_73729_1_, double p_73729_2_, double p_73729_3_, double p_73729_4_, double p_73729_5_, double p_73729_6_)
    {
		   float f = 0.00390625F;
	        float f1 = 0.00390625F;
	        Tessellator tessellator = Tessellator.instance;
	        tessellator.startDrawingQuads();
	        tessellator.addVertexWithUV((double)(p_73729_1_ + 0), (double)(p_73729_2_ + p_73729_6_), (double)this.zLevel, (double)((float)(p_73729_3_ + 0) * f), (double)((float)(p_73729_4_ + p_73729_6_) * f1));
	        tessellator.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + p_73729_6_), (double)this.zLevel, (double)((float)(p_73729_3_ + p_73729_5_) * f), (double)((float)(p_73729_4_ + p_73729_6_) * f1));
	        tessellator.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + 0), (double)this.zLevel, (double)((float)(p_73729_3_ + p_73729_5_) * f), (double)((float)(p_73729_4_ + 0) * f1));
	        tessellator.addVertexWithUV((double)(p_73729_1_ + 0), (double)(p_73729_2_ + 0), (double)this.zLevel, (double)((float)(p_73729_3_ + 0) * f), (double)((float)(p_73729_4_ + 0) * f1));
	        tessellator.draw();
    }
	
}
