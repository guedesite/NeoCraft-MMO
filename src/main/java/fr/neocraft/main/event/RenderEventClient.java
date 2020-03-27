package fr.neocraft.main.event;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fr.neocraft.main.main;
import fr.neocraft.main.Server.ClientPlayerData;
import fr.neocraft.main.proxy.ClientProxy;
import fr.neocraft.main.render.gui.external.CarteGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class RenderEventClient {

	//@SubscribeEvent
	//RenderPlayerEvent
	
	
	private Minecraft mc = Minecraft.getMinecraft();
	private  String CaraColor = I18n.format("neo.cara.color");
	private boolean isLaunchDecompt = false;
	private ArrayList<AltString> allText = new ArrayList<AltString>();
	private Timer t = new Timer();
	
	@SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event)
    {
		if (event.type != ElementType.EXPERIENCE) {
			return;
		}
		
		ScaledResolution scaled = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();

			if(allText.size() == 1 ) 
			{
			
				drawCenteredString(mc.fontRenderer, allText.get(0).prems, width / 2, 5, Integer.parseInt("FFFFFF", 16));
				if(allText.get(0).second != null)
				{
					drawCenteredString(mc.fontRenderer, allText.get(0).second, width / 2, 30, Integer.parseInt("FFFFFF", 16));
				}
				
			} else if(allText.size() > 1)
			{
				drawCenteredString(mc.fontRenderer, allText.get(1).prems, width / 2, 5, Integer.parseInt("FFFFFF", 16));
				if(allText.get(0).second != null)
				{
					drawCenteredString(mc.fontRenderer, allText.get(0).second, width / 2, 30, Integer.parseInt("FFFFFF", 16));
				}
				if(!isLaunchDecompt ) {
					isLaunchDecompt  = true;
					t.schedule(new TimerTask() {
	
						@Override
						public void run() {
							allText.remove(1);
							isLaunchDecompt  = false;
						}
					}, 5000);
				}
			}
        
		
    }
	
	@SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Pre event)
    {
	
    }
	
	
	@SubscribeEvent
	public void RenderPlayerEventSpecial(RenderPlayerEvent.Specials.Pre event)
	{
		event.setCanceled(true);
	}
	
	@SubscribeEvent
	public void RenderPlayerEvent(RenderPlayerEvent.Pre event)
	{
		ClientPlayerData pl = main.AllPlayer.get(event.entityPlayer.getCommandSenderName());
		if(pl != null)
		{
			if(pl.race == "orc")
			{
				if(pl.race == "paladin")
				{
					
				}else if(pl.race == "archer")
				{
					
				}else if(pl.race == "mage")
				{
					
				}
			}else if(pl.race == "humain")
			{
				if(pl.race == "paladin")
				{
					
				}else if(pl.race == "archer")
				{
					
				}else if(pl.race == "mage")
				{
					
				}
			}else if(pl.race == "demon")
			{
				if(pl.race == "paladin")
				{
					
				}else if(pl.race == "archer")
				{
					
				}else if(pl.race == "mage")
				{
					
				}
			}else if(pl.race == "elfe")
			{
				if(pl.race == "paladin")
				{
					
				}else if(pl.race == "archer")
				{
					
				}else if(pl.race == "mage")
				{
					
				}
			}
		}
	}
	
	
	public void addTextToRender(String txt1, String txt2, boolean first) {
		if( first && allText.size() != 0) 
		{
			allText.set(0, new AltString((txt1 != null ? txt1.replaceAll("&", CaraColor) : ""), (txt2 != null ? txt2.replaceAll("&", CaraColor) : "")));
		} else {
			allText.add(new AltString((txt1 != null ? txt1.replaceAll("&", CaraColor) : ""), (txt2 != null ? txt2.replaceAll("&", CaraColor) : "")));
		}
	}
	
	
	
	public void drawCenteredString(FontRenderer p_73732_1_, String p_73732_2_, int p_73732_3_, int p_73732_4_, int p_73732_5_)
    {
        p_73732_1_.drawStringWithShadow(p_73732_2_, p_73732_3_ - p_73732_1_.getStringWidth(p_73732_2_) / 2, p_73732_4_, p_73732_5_);
    }
	
	public class AltString {
		public String prems, second;
		
		public AltString(String prem, String sec)
		{
			prems = prem;
			second = sec;
		}
		
	}
	
}
