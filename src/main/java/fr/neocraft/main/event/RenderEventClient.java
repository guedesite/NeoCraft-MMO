package fr.neocraft.main.event;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fr.neocraft.main.proxy.ClientProxy;
import fr.neocraft.main.render.Entity.PlayerRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class RenderEventClient {

	//@SubscribeEvent
	//RenderPlayerEvent
	public float x = 0;
	public float y = 0;
	public float z = 0,f=0;
	private Minecraft mc = Minecraft.getMinecraft();
	public static String CaraColor;
	public static String Dolar;
	private boolean isLaunchDecompt = false;
	private ArrayList<AltString> allText = new ArrayList<AltString>();
	private Timer t = new Timer();
	
	public PlayerRenderer PlayerRender = new PlayerRenderer();
	
	
	public static void PostInit() {
		CaraColor = I18n.format("neo.cara.color");
		Dolar = I18n.format("neo.dollar");
	}
	
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
			
				drawCenteredString(mc.fontRenderer, allText.get(0).prems, width / 2, 5, 0XFFFFFF);
				if(allText.get(0).second != null)
				{
					drawCenteredString(mc.fontRenderer, allText.get(0).second, width / 2, 30, 0XFFFFFF);
				}
				
			} else if(allText.size() > 1)
			{
				drawCenteredString(mc.fontRenderer, allText.get(1).prems, width / 2, 5, 0XFFFFFF);
				if(allText.get(0).second != null)
				{
					drawCenteredString(mc.fontRenderer, allText.get(0).second, width / 2, 30, 0XFFFFFF);
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
		//event.setCanceled(true);
	}
	
	
	@SubscribeEvent
	public void RenderPlayerEvent(RenderPlayerEvent.Pre event)
	{
		
		if(ClientProxy.player != null)
		{
		//	event.setCanceled(true);
			//PlayerRender.doRender(event.entityPlayer, event.entityPlayer.posX, event.entityPlayer.posY, event.entityPlayer.posZ, 0,  event.partialRenderTick);
			if(ClientProxy.player.race != null)
			{
				if(ClientProxy.player.race.equals("orc"))
				{
					if(ClientProxy.player.classe.equals("paladin"))
					{
				
					}else if(ClientProxy.player.classe.equals("archer"))
					{
						
					}else if(ClientProxy.player.classe.equals("mage"))
					{
						
					}
				}else if(ClientProxy.player.race.equals("humain"))
				{
					if(ClientProxy.player.classe.equals("paladin"))
					{
						
					}else if(ClientProxy.player.classe.equals("archer"))
					{
						
					}else if(ClientProxy.player.classe.equals("mage"))
					{
						
					}
				}else if(ClientProxy.player.race.equals("demon"))
				{
					if(ClientProxy.player.classe.equals("paladin"))
					{
						
					}else if(ClientProxy.player.classe.equals("archer"))
					{
						
					}else if(ClientProxy.player.classe.equals("mage"))
					{
						
					}
				}else if(ClientProxy.player.race.equals("elfe"))
				{
					if(ClientProxy.player.classe.equals("paladin"))
					{
						
					}else if(ClientProxy.player.classe.equals("archer"))
					{
						
					}else if(ClientProxy.player.classe.equals("mage"))
					{
						
					}
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
	
	private  char[] colorlvl = new char[] {'f','a','2','9','1','e','6','c','4'  };
	 @SubscribeEvent
	 public void ItemToolType(ItemTooltipEvent event)
	 {
		 if(event.itemStack.stackTagCompound != null && event.itemStack.stackTagCompound.hasKey("dmg"))
		 {
			 event.toolTip.clear();
			 String cara = CaraColor;
			 NBTTagCompound tag = event.itemStack.stackTagCompound;
			 NBTTagCompound display = null;
			 int lvl = tag.getInteger("lvl");
			 if(tag.hasKey("display"))
			 {
				 display = tag.getCompoundTag("display");
				 String name = display.getString("Name");
				if(lvl < 1)
				{
					event.toolTip.add(cara+colorlvl[0]+name);
				} else if(9 < lvl)
				{
					event.toolTip.add(cara+colorlvl[8]+cara+"l"+name);
				} else {
					event.toolTip.add(cara+colorlvl[lvl]+name);
				}
				 event.toolTip.add(I18n.format(" "));
			 }
			 event.toolTip.add(I18n.format("neo.itemtooltype.lvl", cara+lvl));
			 
			 event.toolTip.add(I18n.format("neo.itemtooltype.dmg", tag.getInteger("dmg")));
			 
			 if(tag.hasKey("dmgtype"))
			 {
				 switch(tag.getInteger("dmgtype"))
				 {
				 	case 0:
				 		event.toolTip.add(I18n.format("neo.itemtooltype.dmgtype", cara+"8"+"physique"));
				 		break;
				 	case 1:
				 		event.toolTip.add(I18n.format("neo.itemtooltype.dmgtype", cara+"b"+"glace"));
				 		break;
				 	case 2:
				 		event.toolTip.add(I18n.format("neo.itemtooltype.dmgtype", cara+"2"+"poison"));
				 		break;
				 	case 3:
				 		event.toolTip.add(I18n.format("neo.itemtooltype.dmgtype", cara+"4"+"feu"));
				 		break;
				 		
				 }
			 }
			 if(tag.hasKey("projectiletype"))
			 {
				 switch(tag.getInteger("projectiletype"))
				 {
				 	case 0:
				 		event.toolTip.add(I18n.format("neo.itemtooltype.projectiletype", "rafale de", tag.getInteger("amount")));
				 		break;
				 	case 1:
				 		event.toolTip.add(I18n.format("neo.itemtooltype.projectiletype", tag.getInteger("amount"), "horizontal(s)"));
				 		break;
				 }
			 }
			 
			 if(tag.hasKey("mf"))
			 {
				 event.toolTip.add(I18n.format("neo.itemtooltype.mf", tag.getInteger("mf")));
			 }
			 if( display != null)
			 {
				 
				 if(display.hasKey("author"))
				 {
					 event.toolTip.add(display.getString("author"));
				 }
			 }
		 }
	 }
	
	
}
