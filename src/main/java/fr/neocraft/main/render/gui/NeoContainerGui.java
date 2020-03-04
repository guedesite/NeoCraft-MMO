package fr.neocraft.main.render.gui;

import org.lwjgl.opengl.GL11;

import fr.neocraft.main.Server.EnumSound;
import fr.neocraft.main.Server.SoundManager;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class NeoContainerGui extends GuiContainer{

	public ResourceLocation Ress;
	private int wait = 10;
	
	public NeoContainerGui(Container p_i1072_1_, ResourceLocation ress) {
		super(p_i1072_1_);
		SoundManager.PlaySound(EnumSound.NeoMOpen.getSound());
		 Ress = ress;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); 
	     this.mc.getTextureManager().bindTexture(Ress); 
	     int k = (this.width - this.xSize) / 2; 
	     int l = (this.height - this.ySize) / 2;
	     this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		
	}

	@Override
	public void initGui()
	{
	    super.initGui();
	    wait = 10;
	}
	
	@Override
    public void onGuiClosed()
    {
    	
    	SoundManager.PlaySound(EnumSound.NeoMClose.getSound());
        if (this.mc.thePlayer != null)
        {
            this.inventorySlots.onContainerClosed(this.mc.thePlayer);
        }
    }

	
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
	
}
