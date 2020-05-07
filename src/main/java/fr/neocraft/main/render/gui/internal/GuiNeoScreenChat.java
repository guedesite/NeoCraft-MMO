package fr.neocraft.main.render.gui.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import fr.neocraft.main.Reference;
import fr.neocraft.main.proxy.ClientProxy;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiNeoScreenChat extends GuiScreen
{

	private static final ResourceLocation res = new ResourceLocation(Reference.MOD_ID+":textures/gui/Chat.png");
	
    private String field_146410_g = "";
    private int sentHistoryCursor = -1;
    private boolean field_146417_i;
    private boolean field_146414_r;
    private int field_146413_s;
    private List field_146412_t = new ArrayList();
    public GuiTextField inputField;
    private String defaultInputFieldText = "";

    public GuiNeoScreenChat() {}

    public GuiNeoScreenChat(String p_i1024_1_)
    {
        this.defaultInputFieldText = p_i1024_1_;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        this.inputField = new GuiTextField(this.fontRendererObj, 0, 0, 0, 12);
        this.inputField.setMaxStringLength(100);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
    }
    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
    	
    	
        mc.getTextureManager().bindTexture(res);
        double scalechat = width();
        double scale = 1.35D;
		NeodrawTexturedModalRect(0, this.height - 19.75, 0, 114.75D, 12.25D, 19.75);
		NeodrawTexturedModalRect(12.25D,this.height - 19.75, 12.25D, 114.75D, 220.5D  *scalechat , 19.75);
		NeodrawTexturedModalRect(12.25D + 220.5D  *scalechat, this.height - 19.75, 232.75D, 114.75D, 7.75D , 19.75);
		this.inputField.width = (int)(220.5D  *scalechat - 5);
		 GL11.glTranslatef(13.79999F, this.height -13.399991F, 0);
		 this.inputField.drawTextBox();
		
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
    public float width()
    {
    	return (float)( this.mc.gameSettings.chatWidth < 0.3 ? 0.3:this.mc.gameSettings.chatWidth);
    }
    
    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
       // this.mc.ingameGUI.getChatGUI().resetScroll();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    @Override
    public void updateScreen()
    {
        this.inputField.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    @Override
    public void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        this.field_146414_r = false;

        if (p_73869_2_ == 15)
        {
            this.func_146404_p_();
        }
        else
        {
            this.field_146417_i = false;
        }

        if (p_73869_2_ == 1)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else if (p_73869_2_ != 28 && p_73869_2_ != 156)
        {
            if (p_73869_2_ == 200)
            {
                this.getSentHistory(-1);
            }
            else if (p_73869_2_ == 208)
            {
                this.getSentHistory(1);
            }
            else if (p_73869_2_ == 201)
            {
                this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().func_146232_i() - 1);
            }
            else if (p_73869_2_ == 209)
            {
                this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().func_146232_i() + 1);
            }
            else
            {
                this.inputField.textboxKeyTyped(p_73869_1_, p_73869_2_);
            }
        }
        else
        {
            String s = this.inputField.getText().trim();

            if (s.length() > 0)
            {
                this.func_146403_a(s);
            }

            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }

    public void func_146403_a(String p_146403_1_)
    {
        this.mc.ingameGUI.getChatGUI().addToSentMessages(p_146403_1_);
        if (net.minecraftforge.client.ClientCommandHandler.instance.executeCommand(mc.thePlayer, p_146403_1_) != 0) return;
        this.mc.thePlayer.sendChatMessage(p_146403_1_);
    }

    /**
     * Handles mouse input.
     */
    @Override
    public void handleMouseInput()
    {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();

        if (i != 0)
        {
            if (i > 1)
            {
                i = 1;
            }

            if (i < -1)
            {
                i = -1;
            }

            if (!isShiftKeyDown())
            {
                i *= 7;
            }

            this.mc.ingameGUI.getChatGUI().scroll(i);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    @Override
    public void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {
        this.inputField.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }




    public void func_146404_p_()
    {
        String s1;

        if (this.field_146417_i)
        {
            this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());

            if (this.field_146413_s >= this.field_146412_t.size())
            {
                this.field_146413_s = 0;
            }
        }
        else
        {
            int i = this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false);
            this.field_146412_t.clear();
            this.field_146413_s = 0;
            String s = this.inputField.getText().substring(i).toLowerCase();
            s1 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
            this.func_146405_a(s1, s);

            if (this.field_146412_t.isEmpty())
            {
                return;
            }

            this.field_146417_i = true;
            this.inputField.deleteFromCursor(i - this.inputField.getCursorPosition());
        }

        if (this.field_146412_t.size() > 1)
        {
            StringBuilder stringbuilder = new StringBuilder();

            for (Iterator iterator = this.field_146412_t.iterator(); iterator.hasNext(); stringbuilder.append(s1))
            {
                s1 = (String)iterator.next();

                if (stringbuilder.length() > 0)
                {
                    stringbuilder.append(", ");
                }
            }

            this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(stringbuilder.toString()), 1);
        }

        this.inputField.writeText(EnumChatFormatting.getTextWithoutFormattingCodes((String)this.field_146412_t.get(this.field_146413_s++)));
    }

    private void func_146405_a(String p_146405_1_, String p_146405_2_)
    {
        if (p_146405_1_.length() >= 1)
        {
            net.minecraftforge.client.ClientCommandHandler.instance.autoComplete(p_146405_1_, p_146405_2_);
            this.mc.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete(p_146405_1_));
            this.field_146414_r = true;
        }
    }

    /**
     * input is relative and is applied directly to the sentHistoryCursor so -1 is the previous message, 1 is the next
     * message from the current cursor position
     */
    
    public void getSentHistory(int p_146402_1_)
    {
        int j = this.sentHistoryCursor + p_146402_1_;
        int k = this.mc.ingameGUI.getChatGUI().getSentMessages().size();

        if (j < 0)
        {
            j = 0;
        }

        if (j > k)
        {
            j = k;
        }

        if (j != this.sentHistoryCursor)
        {
            if (j == k)
            {
                this.sentHistoryCursor = k;
                this.inputField.setText(this.field_146410_g);
            }
            else
            {
                if (this.sentHistoryCursor == k)
                {
                    this.field_146410_g = this.inputField.getText();
                }

                this.inputField.setText((String)this.mc.ingameGUI.getChatGUI().getSentMessages().get(j));
                this.sentHistoryCursor = j;
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */

    public void func_146406_a(String[] p_146406_1_)
    {
        if (this.field_146414_r)
        {
            this.field_146417_i = false;
            this.field_146412_t.clear();
            String[] astring1 = p_146406_1_;
            int i = p_146406_1_.length;

            String[] complete = net.minecraftforge.client.ClientCommandHandler.instance.latestAutoComplete;
            if (complete != null)
            {
                astring1 = com.google.common.collect.ObjectArrays.concat(complete, astring1, String.class);
                i = astring1.length;
            }

            for (int j = 0; j < i; ++j)
            {
                String s = astring1[j];

                if (s.length() > 0)
                {
                    this.field_146412_t.add(s);
                }
            }

            String s1 = this.inputField.getText().substring(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false));
            String s2 = StringUtils.getCommonPrefix(p_146406_1_);

            if (s2.length() > 0 && !s1.equalsIgnoreCase(s2))
            {
                this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
                this.inputField.writeText(s2);
            }
            else if (this.field_146412_t.size() > 0)
            {
                this.field_146417_i = true;
                this.func_146404_p_();
            }
        }
    }
    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}