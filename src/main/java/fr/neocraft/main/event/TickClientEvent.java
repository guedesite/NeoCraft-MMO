package fr.neocraft.main.event;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import fr.neocraft.main.main;
import fr.neocraft.main.Server.ClientPlayerData;
import fr.neocraft.main.proxy.ClientProxy;
import fr.neocraft.main.render.gui.internal.GuiNeoInGame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class TickClientEvent {
	
	private static final String KEY_CATEGORY = "key.neocraft";
	private final KeyBinding KEY_MAP = new KeyBinding("key.map", Keyboard.KEY_M, KEY_CATEGORY);
	public final static KeyBinding KEY_ZOOM = new KeyBinding("key.zoom", Keyboard.KEY_C, KEY_CATEGORY);
	private final KeyBinding KEY_x = new KeyBinding("key.map", Keyboard.KEY_NUMPAD9, KEY_CATEGORY);
	private final KeyBinding KEY_x2 = new KeyBinding("key.map", Keyboard.KEY_NUMPAD8, KEY_CATEGORY);
	private final KeyBinding KEY_y = new KeyBinding("key.map", Keyboard.KEY_NUMPAD6, KEY_CATEGORY);
	private final KeyBinding KEY_y2 = new KeyBinding("key.map", Keyboard.KEY_NUMPAD5, KEY_CATEGORY);
	private final KeyBinding KEY_z = new KeyBinding("key.map", Keyboard.KEY_NUMPAD3, KEY_CATEGORY);
	private final KeyBinding KEY_z2 = new KeyBinding("key.map", Keyboard.KEY_NUMPAD2, KEY_CATEGORY);
	
	
	private ClientPlayerData data;
	private Minecraft mc;
	
	
	public TickClientEvent(ClientPlayerData data, Minecraft mc) {
		this.data = data;
		this.mc = mc;
		ClientRegistry.registerKeyBinding(KEY_MAP);
		ClientRegistry.registerKeyBinding(KEY_ZOOM);
	}
	
	@SubscribeEvent
    public void onTick(ClientTickEvent evt) {
	
		
		if(data != null)
		{
			

			 
			 
			if(Minecraft.getMinecraft().ingameGUI instanceof GuiNeoInGame) {
				((GuiNeoInGame)Minecraft.getMinecraft().ingameGUI).renderMap = KEY_MAP.getIsKeyPressed();
			}
			
			
			
			if(KEY_x.getIsKeyPressed())
			{
				ClientProxy.GuiClientManager.x += 0.01F;
				System.out.println(ClientProxy.GuiClientManager.x+"_"+ClientProxy.GuiClientManager.y+"_"+ClientProxy.GuiClientManager.z);
			}
			if(KEY_x2.getIsKeyPressed())
			{
				ClientProxy.GuiClientManager.x -= 0.01F;
				System.out.println(ClientProxy.GuiClientManager.x+"_"+ClientProxy.GuiClientManager.y+"_"+ClientProxy.GuiClientManager.z);
				
			}
			if(KEY_z.getIsKeyPressed())
			{
				ClientProxy.GuiClientManager.z += 0.01F;
				System.out.println(ClientProxy.GuiClientManager.x+"_"+ClientProxy.GuiClientManager.y+"_"+ClientProxy.GuiClientManager.z);
				
			}
			if(KEY_z2.getIsKeyPressed())
			{
				ClientProxy.GuiClientManager.z -= 0.01F;
				System.out.println(ClientProxy.GuiClientManager.x+"_"+ClientProxy.GuiClientManager.y+"_"+ClientProxy.GuiClientManager.z);
				
			}
			if(KEY_y.getIsKeyPressed())
			{
				ClientProxy.GuiClientManager.y += 0.01F;
				System.out.println(ClientProxy.GuiClientManager.x+"_"+ClientProxy.GuiClientManager.y+"_"+ClientProxy.GuiClientManager.z);
				
			}
			if(KEY_y2.getIsKeyPressed())
			{
				ClientProxy.GuiClientManager.y -= 0.01F;
				System.out.println(ClientProxy.GuiClientManager.x+"_"+ClientProxy.GuiClientManager.y+"_"+ClientProxy.GuiClientManager.z);
				
			}
		} else {
			data = main.AllPlayer.get(mc.thePlayer.getCommandSenderName());
		}
	}
}
