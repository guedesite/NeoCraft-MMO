package fr.neocraft.main.render.gui.internal;

import java.util.Scanner;

import org.lwjgl.opengl.GL11;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.Reference;
import fr.neocraft.main.main;
import fr.neocraft.main.Server.ClientPlayerData;
import fr.neocraft.main.render.gui.external.CarteGui;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;


@SideOnly(Side.CLIENT)
public class GuiNeoInGame extends GuiIngame{

	
	protected static final ResourceLocation GuiMmo = new ResourceLocation(Reference.MOD_ID+":textures/gui/GuiMmo.png");
	protected ClientPlayerData data;
	protected char dolar;
	public boolean renderMap = false;
	public CarteGui map;
	
	public GuiNeoInGame(Minecraft mc)
	{
		super(mc);
		dolar = I18n.format("neo.dollar").charAt(0);
		 data = main.AllPlayer.get(mc.thePlayer.getCommandSenderName());
		 map = new CarteGui(mc);

	}
	
	
	@Override
	 public void renderGameOverlay(float partialTicks, boolean hasScreen, int mouseX, int mouseY)
	 {
		
		
		
		ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		mc.entityRenderer.setupOverlayRendering();
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		

		if (Minecraft.isFancyGraphicsEnabled())
		{
			renderVignette(mc.thePlayer.getBrightness(partialTicks), width, height);
		} else {
			OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		}
		
		GL11.glScaled(0.9, 0.9, 0);
		mc.getTextureManager().bindTexture(GuiMmo);
		
		NeodrawTexturedModalRect(0,0, 0, 128, 70,73.5);
		
		drawCircleAngle(data.calculPercWithExp(), 34.8, 34.7, 34.9, width, height);
		
		NeodrawTexturedModalRect(0,0, 0, 0, 199,73.5);
		
		NeodrawTexturedModalRect(66.5,55.5, 0, 73.5, 115*( mc.thePlayer.getFoodStats().getFoodLevel() / 20 ), 4);
		
		
		NeodrawTexturedModalRect((width - 256) / 2 / 0.9, (height - 50.5) / 0.9, 0, 77.5, 256, 50.5);
		
		
		GL11.glScaled(1/0.9, 1/0.9, 0);
		
		if(data.level>9)
		{
			drawCenteredString(mc.fontRenderer, data.level+"", 11, 50, 0XFFFFFF);
		} else {
			GL11.glScaled(1.8, 1.8, 0);
			drawCenteredString(mc.fontRenderer, data.level+"", 8, 25, 0XFFFFFF);
			GL11.glScaled(1/1.8, 1/1.8, 0);
		}
		GL11.glScaled(1.1, 1.1, 0);
		drawString(mc.fontRenderer, mc.thePlayer.getCommandSenderName(), 60, 13, 0XFFFFFF);
		drawString(mc.fontRenderer, data.power+" CR", 60, 26, 0XFFFFFF);
		
		drawString(mc.fontRenderer, data.Money+""+dolar, 150 - mc.fontRenderer.getStringWidth(data.Money+""+dolar), 26, 0XFFFFFF);
		GL11.glScaled(1/1.1, 1/1.1, 0);
		
		
		if(this.renderMap)
		{
			map.drawScreen(width, height, mc.thePlayer.posX, mc.thePlayer.posZ, mc.thePlayer.rotationYawHead);
		}

		GL11.glPopMatrix();
	 }
	

	
	@Override
	 public void updateTick() {
		
	 }
	

	public void NeodrawTexturedModalRect(double p_73729_1_, double p_73729_2_, double p_73729_3_, double p_73729_4_, double p_73729_5_, double p_73729_6_)
	{
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(p_73729_1_, p_73729_2_ + p_73729_6_, (double)this.zLevel, (double)((float)(p_73729_3_) * f), (double)((float)(p_73729_4_ + p_73729_6_) * f1));
		tessellator.addVertexWithUV(p_73729_1_ + p_73729_5_, p_73729_2_ + p_73729_6_, (double)this.zLevel, (double)((float)(p_73729_3_ + p_73729_5_) * f), (double)((float)(p_73729_4_ + p_73729_6_) * f1));
		tessellator.addVertexWithUV(p_73729_1_ + p_73729_5_, (double)(p_73729_2_ + 0), (double)this.zLevel, (double)((float)(p_73729_3_ + p_73729_5_) * f), (double)((float)(p_73729_4_) * f1));
		tessellator.addVertexWithUV(p_73729_1_, p_73729_2_, (double)this.zLevel, (double)((float)(p_73729_3_) * f), (double)((float)(p_73729_4_) * f1));
		tessellator.draw();
	}
	
	public void drawCircleAngle(double pourcant, double radius, double x, double y, double width, double height)
	{

		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	//	GL11.glColor3b((byte)44, (byte)191,(byte) 21);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(6);
		
		int side = 15;
		
		double pi = Math.toRadians(pourcant);
		tessellator.setColorRGBA(28, 204, 0, 255);
		for(int i = 0; i <= side ;i++) 
		{
			double angle = (pi * i / side) - 0.636332;
			tessellator.addVertex(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, (double)this.zLevel);
		}
		tessellator.draw();
		    
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);


	
	}
}
