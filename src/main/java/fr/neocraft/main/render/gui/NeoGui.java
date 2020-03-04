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

public class NeoGui extends GuiScreen{
	private static ResourceLocation textures;
	private int xSize = 256, ySize = 256;
	private int wait = 10;
	
	
	protected ArrayList<GuiButton> buttonList = new ArrayList<GuiButton>();
	
	public NeoGui(ResourceLocation text) {
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
   
    
    
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {

    		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); 

    	    this.mc.getTextureManager().bindTexture(textures); 
    	    int k = (this.width - this.xSize) / 2; 
    	    int l = (this.height - this.ySize) / 2;
    	    this.drawTexturedModalRect(k , l, 0, 0, this.xSize, this.ySize);
    }
    
    @Override
    public void onGuiClosed() {
    	SoundManager.PlaySound(EnumSound.NeoMClose.getSound());
    }
    
    public class SimpleGuiButton extends GuiButton {
    	public SimpleGuiButton(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, String p_i1020_4_, ResourceLocation ress)
	    {
	        this(p_i1020_1_, p_i1020_2_, p_i1020_3_, 200, 20, p_i1020_4_, ress);
	    }

	    public  SimpleGuiButton(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_, ResourceLocation ress)
	    {
	    	super(p_i1021_1_, p_i1021_2_,  p_i1021_3_, p_i1021_4_,  p_i1021_5_, p_i1021_6_);
	    	Ress = ress;
	    }
	    
	    public ResourceLocation Ress;
	    
	    
	    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
	    {
	  	  if (this.visible)
          {
              FontRenderer fontrenderer = p_146112_1_.fontRenderer;
              GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
              this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
              int k = this.getHoverState(this.field_146123_n);
              GL11.glEnable(GL11.GL_BLEND); 
              OpenGlHelper.glBlendFunc(770, 771, 1, 0);
              GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
          
              p_146112_1_.getTextureManager().bindTexture(Ress);
              if(k == 1)
              {
            	  this.drawTexturedModalRect(this.xPosition, this.yPosition,0,0 +  this.height , this.width, this.height);
              }
              else {
              	this.drawTexturedModalRect(this.xPosition, this.yPosition,0,0 , this.width, this.height);
              }
           }
	    }
    }
}
