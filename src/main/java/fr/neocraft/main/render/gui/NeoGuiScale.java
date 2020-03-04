package fr.neocraft.main.render.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import fr.neocraft.main.Server.EnumSound;
import fr.neocraft.main.Server.SoundManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.common.MinecraftForge;

public abstract class NeoGuiScale extends GuiScreen {
	private static ResourceLocation textures;
	private int xSize = 256, ySize = 256;
	public int MultOf256 = 1;
	private int wait = 10;
	
	protected ArrayList<ScaleGuiButton> buttonList = new ArrayList<ScaleGuiButton>();
	
	public NeoGuiScale(ResourceLocation text) {
		textures = text;
		SoundManager.PlaySound(EnumSound.NeoMOpen.getSound());
	}
		
	@Override
	public void initGui()
	{
	    super.initGui();
	    wait = 10;
	}
	
	private int debutx =0, debuty = 0;

	private GuiButton selectedButton;


    @Override
    public void updateScreen()
    {
        super.updateScreen();
        if(wait > 0)
        {
        	wait--;
        }
        if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead)
        {
            this.mc.thePlayer.closeScreen();
        }
    }
    
    public boolean canDoAction() {
    	return wait == 0;
    }

    @Override
    public void onGuiClosed() {
    	SoundManager.PlaySound(EnumSound.NeoMClose.getSound());
    }
    
   private static float scale = 1F;
   
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {

    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	this.mc.getTextureManager().bindTexture(textures);
  

            GL11.glPushMatrix();
           // float scale = 1.625F;
            scale=((float)this.height) / ((float)256);
            if(scale < 1)
            {
            	scale = 1f;
            }
         
           GL11.glScalef(scale, scale, 1F);
      	 this.debuty = Math.round((this.height - this.ySize*scale) / 2);
      	 this.debutx  = Math.round((this.width - this.xSize* scale) / (2*scale));

           
            this.drawTexturedModalRect(this.debutx,this.debuty, 0, 0, this.xSize, this.ySize);
            GL11.glTranslatef(this.debutx, this.debuty, 0);
            for (int k = 0; k < this.buttonList.size(); ++k)
            {
            	
            	this.buttonList.get(k).drawButton(this.mc, p_73863_1_, p_73863_2_, scale, this.debutx, this.debuty, MultOf256);
            	
            }
            extendsRender(this.debutx,this.debuty,scale);
            GL11.glPopMatrix();
    }
	
	public abstract void extendsRender(int debutx, int debuty, float scale);
	
	
	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {
        if (p_73864_3_ == 0)
        {
            for (int l = 0; l < this.buttonList.size(); ++l)
            {
            	ScaleGuiButton guibutton = this.buttonList.get(l);

                if (guibutton.mousePressed(this.mc, p_73864_1_, p_73864_2_, scale, this.debutx, this.debuty,MultOf256))
                {
                    ActionPerformedEvent.Pre event = new ActionPerformedEvent.Pre(this, guibutton, this.buttonList);
                    if (MinecraftForge.EVENT_BUS.post(event))
                        break;
                    this.selectedButton = event.button;
                    event.button.func_146113_a(this.mc.getSoundHandler());
                    this.actionPerformed(event.button);
                    if (this.equals(this.mc.currentScreen))
                        MinecraftForge.EVENT_BUS.post(new ActionPerformedEvent.Post(this, event.button, this.buttonList));
                }
            }
        }
    }
	
	
	public class ScaleGuiButton extends GuiButton {
		
		public ScaleGuiButton(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, ResourceLocation ress)
	    {
	        this(p_i1020_1_, p_i1020_2_, p_i1020_3_, 200, 20, ress);
	    }

	    public ScaleGuiButton(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_,  ResourceLocation ress)
	    {
	    	super(p_i1021_1_, p_i1021_2_,  p_i1021_3_, p_i1021_4_,  p_i1021_5_, "");
	    	Ress = ress;
	    }
	    
	    public ResourceLocation Ress;
	    
	    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_, float scale, int x, int y, int MultOf256)
	    {
	    	  if (this.visible)
	          {
	              FontRenderer fontrenderer = p_146112_1_.fontRenderer;
	              p_146112_1_.getTextureManager().bindTexture(Ress);
	              GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	         
	             	int kx = Math.round((x+this.xPosition/MultOf256)*scale);
	          	int ky = Math.round((y+this.yPosition/MultOf256)*scale);
	              	this.field_146123_n = p_146112_2_ >= kx && p_146112_3_ >= ky && p_146112_2_ < kx + (this.width/MultOf256*scale) && p_146112_3_ < ky + (this.height/MultOf256*scale);   
	                 
	   

	             int k = this.getHoverState(this.field_146123_n);
	              GL11.glEnable(GL11.GL_BLEND); 
	              OpenGlHelper.glBlendFunc(770, 771, 1, 0);
	              GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	              GL11.glScalef(1F/MultOf256, 1F/MultOf256, 0F);
	              p_146112_1_.getTextureManager().bindTexture(Ress);
	              if(k == 1)
	              {
	            	  this.drawTexturedModalRect(this.xPosition, this.yPosition,0,0 +  this.height , this.width, this.height);
	              }
	              else {
	              	
	              	this.drawTexturedModalRect(this.xPosition, this.yPosition,0,0 , this.width, this.height);
	              }
	              GL11.glScalef(MultOf256, MultOf256, 0F);
	          
	           }
	    }
	    
	    public boolean mousePressed(Minecraft p_146116_1_, int p_146112_2_, int p_146112_3_, float scale, int x, int y, int MultOf256)
	    {
	    	int kx = Math.round((x+this.xPosition/MultOf256)*scale);
	    	int ky = Math.round((y+this.yPosition/MultOf256)*scale);
	    	return this.enabled && this.visible && p_146112_2_ >= kx && p_146112_3_ >= ky && p_146112_2_ < kx + (this.width/MultOf256*scale) && p_146112_3_ < ky + (this.height/MultOf256*scale);   

	    }
	}
	
	
	
	
}

