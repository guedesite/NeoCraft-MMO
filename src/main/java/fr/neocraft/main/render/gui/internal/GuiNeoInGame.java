package fr.neocraft.main.render.gui.internal;

import java.text.DateFormat;
import java.util.Date;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.neocraft.main.Reference;
import fr.neocraft.main.main;
import fr.neocraft.main.Init.ItemMod;
import fr.neocraft.main.proxy.ClientProxy;
import fr.neocraft.main.render.gui.external.CarteGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;


@SideOnly(Side.CLIENT)
public class GuiNeoInGame extends GuiIngame{

	
	protected static final ResourceLocation GuiMmo = new ResourceLocation(Reference.MOD_ID+":textures/gui/GuiMmo.png");
	public boolean renderMap = false;
	public CarteGui map;
	public int updateCounter = 0;
	public final GuiNeoChat ChatGui;
	public GuiNeoInGame(Minecraft mc)
	{
		super(mc);
		this.ChatGui = new GuiNeoChat(mc);
		this.map = new CarteGui(mc);

	}
	
	
	@Override
	 public void renderGameOverlay(float partialTicks, boolean hasScreen, int mouseX, int mouseY)
	 {
		
		
		ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		FontRenderer fontrenderer = mc.fontRenderer;
		mc.entityRenderer.setupOverlayRendering();
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		
		float scale1 = 1.0399994F, scale2 = 1.1F;
		
		
		if (Minecraft.isFancyGraphicsEnabled())
		{
			renderVignette(mc.thePlayer.getBrightness(partialTicks), width, height);
		} else {
			OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		}
		

		
		GL11.glScaled(scale1, scale1, 1F);
		mc.getTextureManager().bindTexture(GuiMmo);
		
		NeodrawTexturedModalRect(0,0, 0, 87.25D, 74.75D,74.75D);
		drawCircleAngle(ClientProxy.player.calculPercWithExp(), 36.75D, 38.125D, 38.125D);
		
		NeodrawTexturedModalRect(0,0, 0, 0, 216.5D,82.25D);
		
		NeodrawTexturedModalRect(67.25D,67.5D, 0, 82.25D, 124.25D*( mc.thePlayer.getFoodStats().getFoodLevel() / 20 ), 4.75D);
		
		GL11.glScaled(1F/scale1, 1F/scale1, 1F);
		//(width - xSize* scale) / 2; 46

		
		double debx = (res.getScaledWidth() -  262D ) / 2;
		double deby = res.getScaledHeight_double() -46.75;
		NeodrawTexturedModalRect(debx, deby, 0, 171.5D, 216D, 46.75D);
		
		 GL11.glEnable(GL12.GL_RESCALE_NORMAL);
         RenderHelper.enableGUIStandardItemLighting();
         
         
         this.renderInventorySlot(0, debx + 49, deby + 24.75, partialTicks);
         this.renderInventorySlot(1, debx + 67.25D, deby + 24.75, partialTicks);
         this.renderInventorySlot(2, debx + 85.25D, deby + 24.75, partialTicks);
         this.renderInventorySlot(3, debx + 103.5D, deby + 24.75, partialTicks);
         this.renderInventorySlot(4, debx + 121.75D, deby + 24.75, partialTicks);
         this.renderInventorySlot(5, debx + 139.75D, deby + 24.75, partialTicks);
         this.renderInventorySlot(6, debx + 158, deby + 24.75, partialTicks);
         this.renderInventorySlot(7, debx + 176.25D, deby + 24.75, partialTicks);
         this.renderInventorySlot(8, debx + 194.5D, deby + 24.75, partialTicks);
         

		 RenderHelper.disableStandardItemLighting();
         GL11.glDisable(GL12.GL_RESCALE_NORMAL);
         this.mc.mcProfiler.endSection();
         GL11.glDisable(GL11.GL_BLEND);
		
         
         if (this.mc.thePlayer.getSleepTimer() > 0)
         {
             GL11.glDisable(GL11.GL_DEPTH_TEST);
             GL11.glDisable(GL11.GL_ALPHA_TEST);
             float l4 = this.mc.thePlayer.getSleepTimer();
             float f2 = (float)l4 / 100.0F;

             if (f2 > 1.0F)
             {
                 f2 = 1.0F - (float)(l4 - 100) / 10.0F;
             }

             drawRect(0, 0, width, height, (int)(220.0F * f2) << 24 | 1052704);
             GL11.glEnable(GL11.GL_ALPHA_TEST);
             GL11.glEnable(GL11.GL_DEPTH_TEST);
         }
         
         if (this.mc.gameSettings.showDebugInfo)
         {
             GL11.glPushMatrix();
             fontrenderer.drawStringWithShadow("NeoCraft MMO (" + getDebugFps(mc.debug) + ") Version du mod: "+Reference.VERSION, 2, 2, 16777215);
             fontrenderer.drawStringWithShadow(mc.debugInfoRenders(), 2, 12, 16777215);
             fontrenderer.drawStringWithShadow(mc.getEntityDebug(), 2, 22, 16777215);
             fontrenderer.drawStringWithShadow(mc.debugInfoEntities().replace("E:", EnumChatFormatting.LIGHT_PURPLE+"E:").replace(", B:", EnumChatFormatting.RESET+", B:"), 2, 32, 16777215);
             fontrenderer.drawStringWithShadow(mc.getWorldProviderName(), 2, 42, 16777215);
             long i5 = Runtime.getRuntime().maxMemory();
             long j5 = Runtime.getRuntime().totalMemory();
             long k5 = Runtime.getRuntime().freeMemory();
             long l5 = j5 - k5;
             int r = Math.round(l5 * 100L / i5);
             String s;
             if(r <= 75)
             {
             	 s = EnumChatFormatting.GREEN+"Used memory: " + l5 * 100L / i5 + "% (" + l5 / 1024L / 1024L + "MB) of " + i5 / 1024L / 1024L + "MB";
                  
             }else  if(r <= 90)
             {
            	 s = EnumChatFormatting.RED+"Used memory: " + l5 * 100L / i5 + "% (" + l5 / 1024L / 1024L + "MB) of " + i5 / 1024L / 1024L + "MB";
                 
            }
             else
             {
             	
             	 s = EnumChatFormatting.DARK_RED+"Used memory: " + l5 * 100L / i5 + "% (" + l5 / 1024L / 1024L + "MB) of " + i5 / 1024L / 1024L + "MB";
                  
             }
             int i3 = 14737632;
             this.drawString(fontrenderer, s, width - fontrenderer.getStringWidth(s) - 2, 2, 14737632);
             s = "Allocated memory: " + j5 * 100L / i5 + "% (" + j5 / 1024L / 1024L + "MB)";
             this.drawString(fontrenderer, s, width - fontrenderer.getStringWidth(s) - 2, 12, 14737632);
             int offset = 22;
           /*  for (String brd : FMLCommonHandler.instance().getBrandings(false))
             {
                 this.drawString(fontrenderer, brd, k - fontrenderer.getStringWidth(brd) - 2, offset+=10, 14737632);
             } */
             int j3 = MathHelper.floor_double(mc.thePlayer.posX);
             int k3 = MathHelper.floor_double(mc.thePlayer.posY);
             int l3 = MathHelper.floor_double(mc.thePlayer.posZ);
             this.drawString(fontrenderer, String.format(EnumChatFormatting.LIGHT_PURPLE+""+EnumChatFormatting.BOLD+"x: %.5f"+ EnumChatFormatting.RESET+" (%d) // c: %d (%d)", new Object[] {Double.valueOf(mc.thePlayer.posX), Integer.valueOf(j3), Integer.valueOf(j3 >> 4), Integer.valueOf(j3 & 15)}), 2, 64, 14737632);
             this.drawString(fontrenderer, String.format(EnumChatFormatting.LIGHT_PURPLE+""+EnumChatFormatting.BOLD+"y: %.3f"+ EnumChatFormatting.RESET+" (feet pos, %.3f eyes pos)", new Object[] {Double.valueOf(mc.thePlayer.boundingBox.minY), Double.valueOf(mc.thePlayer.posY)}), 2, 72, 14737632);
             this.drawString(fontrenderer, String.format(EnumChatFormatting.LIGHT_PURPLE+""+EnumChatFormatting.BOLD+"z: %.5f"+ EnumChatFormatting.RESET+" (%d) // c: %d (%d)", new Object[] {Double.valueOf(mc.thePlayer.posZ), Integer.valueOf(l3), Integer.valueOf(l3 >> 4), Integer.valueOf(l3 & 15)}), 2, 80, 14737632);
             int i4 = MathHelper.floor_double((double)(mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
             this.drawString(fontrenderer, "f: " + i4 + " (" + Direction.directions[i4] + ") / " + MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw), 2, 88, 14737632);

             if (mc.theWorld != null && mc.theWorld.blockExists(j3, k3, l3))
             {
                 Chunk chunk = mc.theWorld.getChunkFromBlockCoords(j3, l3);
                 this.drawString(fontrenderer, "lc: " + (chunk.getTopFilledSegment() + 15) + " b: " + chunk.getBiomeGenForWorldCoords(j3 & 15, l3 & 15, mc.theWorld.getWorldChunkManager()).biomeName + " bl: " + chunk.getSavedLightValue(EnumSkyBlock.Block, j3 & 15, k3, l3 & 15) + " sl: " + chunk.getSavedLightValue(EnumSkyBlock.Sky, j3 & 15, k3, l3 & 15) + " rl: " + chunk.getBlockLightValue(j3 & 15, k3, l3 & 15, 0), 2, 96, 14737632);
             }

             this.drawString(fontrenderer, String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", new Object[] {Float.valueOf(mc.thePlayer.capabilities.getWalkSpeed()), Float.valueOf(mc.thePlayer.capabilities.getFlySpeed()), Boolean.valueOf(mc.thePlayer.onGround), Integer.valueOf(mc.theWorld.getHeightValue(j3, l3))}), 2, 104, 14737632);

             if (mc.entityRenderer != null && mc.entityRenderer.isShaderActive())
             {
                 this.drawString(fontrenderer, String.format("shader: %s", new Object[] {mc.entityRenderer.getShaderGroup().getShaderGroupName()}), 2, 112, 14737632);
             }

             GL11.glPopMatrix();
         }
         
         ClientProxy.GuiClientManager.renderOther(res);
         
         GL11.glEnable(GL11.GL_BLEND);
         OpenGlHelper.glBlendFunc(770, 771, 1, 0);
         GL11.glDisable(GL11.GL_ALPHA_TEST);
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, mc.currentScreen != null && mc.currentScreen instanceof GuiNeoScreenChat ? height - ( 19.75F + 19.56F):(float)(height)-20.59985F,-20F);
         this.ChatGui.drawChat(this.updateCounter);
         GL11.glTranslatef(0F, 0F, 20F);
         GL11.glPopMatrix();
         GL11.glColor4f(1F, 1F, 1F,1F);
         
 		if(ClientProxy.player.level > 9) {
 			GL11.glScalef(1.599997F, 1.599997F, 1F);
 			GL11.glTranslatef(5.960041F, 39.31941F, 0F);
 		} else {
 			GL11.glScalef(1.9199994F, 1.9199994F, 1F);
 			GL11.glTranslatef(7.180069F, 32.500553F, 0F);
 		}
 		fontrenderer.drawStringWithShadow(""+ClientProxy.player.level, 0, 0, 0XFFFFFF);
 		if(ClientProxy.player.level > 9) {
 			GL11.glTranslatef(-5.960041F, -39.31941F, 0F);
 	 		GL11.glScalef(1F/1.599997F, 1F/1.599997F, 1F);
 		} else {
 			GL11.glTranslatef(-7.180069F, -32.500553F, 0F);
 	 		GL11.glScalef(1F/1.9199994F, 1F/1.9199994F, 1F);
 		}


         
	            if(this.mc.gameSettings.keyBindPlayerList.getIsKeyPressed() && main.AllPlayer != null && !main.AllPlayer.isEmpty())
	            {
		            int largeur = 300;
		            String[] all = main.AllPlayer.keySet().toArray(new String[] { });
		            int nb;
		            int nb2 = nb = all.length;
		            
		            if (!(all.length %2 == 0))
		            {
		            	nb2 ++;
		            }
		            int hauteur = (nb2 / 2) *9;
					int middle = width / 2;
					int x = middle - largeur / 2;
					int y = 25;
					int y1 = 3;
					int y3 = 15;
					DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
							DateFormat.SHORT,
							DateFormat.SHORT);
					Date aujourdhui = new Date();
					
					String date = shortDateFormat.format(aujourdhui);
					String str = EnumChatFormatting.DARK_PURPLE +""+ EnumChatFormatting.BOLD +"N" + EnumChatFormatting.LIGHT_PURPLE+"" + EnumChatFormatting.BOLD + "eo"+ EnumChatFormatting.DARK_PURPLE +""+ EnumChatFormatting.BOLD +"C"+EnumChatFormatting.LIGHT_PURPLE+""+ EnumChatFormatting.BOLD + "raft MMO";
					drawCenteredString(mc.fontRenderer, str, width / 2, y1, Integer.parseInt("FFAA00", 16));
					drawCenteredString(mc.fontRenderer, EnumChatFormatting.LIGHT_PURPLE +date, width / 2,y3, Integer.parseInt("FFAA00", 16));
					
		            drawRect(x - 1, y - 1, x + largeur + 1, y + hauteur, Integer.MIN_VALUE);
		            boolean stat = false;
		            int y2 = y;
		            int o = 0;
		            for (int i = 0; i < nb; i++)
		            {
		            	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		                GL11.glEnable(GL11.GL_ALPHA_TEST);
		                if(o < nb)
		                {
			            		drawRect(x, (y2 + i * 8), x + 149, (y2 + i * 8)+ 8, 553648127);
			                    String displayName = all[o];
			                    mc.fontRenderer.drawStringWithShadow(displayName, x+13, (y2 + i * 8), 16777215);
			                   
			                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
			                    mc.getTextureManager().bindTexture(Gui.icons);
			                    int   pingIndex = 0;
	
			                    zLevel += 100.0F;
			                    drawTexturedModalRect(x + 1, (y2 + i * 8), 0, 176 + pingIndex * 8, 10, 8);
			                    zLevel -= 100.0F;
			                    o++;
		                }
		                if(o < nb)
		                {
		                	drawRect(x + 150, (y2 + i * 8), x + 300, (y2 + i * 8)+ 8, 553648127);
		            	
	
		                    String displayName2 = all[o];
		                    mc.fontRenderer.drawStringWithShadow(displayName2, x + 150 + 13, (y2 + i * 8), 16777215);
		                   
		                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	
		                    mc.getTextureManager().bindTexture(Gui.icons);
	
		                   int pingIndex2 = 0;
		                 
	
		                    zLevel += 100.0F;
		                    drawTexturedModalRect(x + 151, (y2 + i * 8), 0, 176 + pingIndex2 * 8, 10, 8);
		                    zLevel -= 100.0F;
		                    o++;
		                }
		            	y2++;
	
		            }
	            }
        	 
		if(this.renderMap)
		{
			map.drawScreen(width, height, mc.thePlayer.posX, mc.thePlayer.posZ, mc.thePlayer.rotationYawHead);
		}

		GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
	 }
	

	
	private void renderInventorySlot(int i, double d, double e, float partialTicks) {
		ItemStack itemstack = this.mc.thePlayer.inventory.mainInventory[i];

		GL11.glTranslated(d, e, 0F);
        if (itemstack != null)
        {
            float f1 = (float)itemstack.animationsToGo - partialTicks;

            
            if (f1 > 0.0F)
            {
                GL11.glPushMatrix();
                float f2 = 1.0F + f1 / 5.0F;
                GL11.glTranslatef((float)(d + 8), (float)(e+ 12), 0.0F);
                GL11.glScalef(1.0F / f2, (f2 + 1.0F) / 2.0F, 1.0F);
                GL11.glTranslatef((float)(-(d + 8)), (float)(-(e + 12)), 0.0F);
            }

            int d2 = MathHelper.floor_double(d);
            int e2 = MathHelper.floor_double(e);
            itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), itemstack, 0, 0);

            if (f1 > 0.0F)
            {
                GL11.glPopMatrix();
            }

           
        }
        if(this.mc.thePlayer.inventory.currentItem == i)
        {
        	itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), new ItemStack(ItemMod.GuiHover), 0, 0);
        }
        if(itemstack != null)
        {
        	 itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), itemstack,  0, 0);
        }
        GL11.glTranslated(-d, -e, 0F);
		
	}
	

	@Override
	 public void updateTick() {
		++this.updateCounter;
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
	
	public void drawCircleAngle(double pourcant, double radius, double x, double y)
	{

		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	//	GL11.glColor3b((byte)44, (byte)191,(byte) 21);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(6);
		
		int side = 50;
		
		double pi = Math.toRadians(pourcant + 40);
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
	
	public String getDebugFps(String s)
	{
		int t = s.length();
		String pre = "";
		char cara, cara2;
		int d = 0;
		for(int i = 0; i < t; i++) 
		{
			
			cara = s.charAt(i);
			if(cara == ' ')
			{
				break;
			}
			else
			{
				pre += cara;
			}
		}
		int nb = Integer.parseInt(pre);
		if(nb < 15)
		{
			return EnumChatFormatting.DARK_RED+""+nb+ "fps"+ EnumChatFormatting.RESET+", "+WorldRenderer.chunksUpdated;
		}
		else if(nb < 30)
		{
			return EnumChatFormatting.RED+""+nb+ "fps"+ EnumChatFormatting.RESET+", "+WorldRenderer.chunksUpdated;
		} else 
		{
			return EnumChatFormatting.GREEN+""+nb+ "fps"+ EnumChatFormatting.RESET+", "+WorldRenderer.chunksUpdated;
		}
	}
	public GuiNewChat getChatGUI()
    {
        return this.ChatGui;
  }

    public int getUpdateCounter()
    {
        return this.updateCounter;
    }
}
