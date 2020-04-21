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
	private final KeyBinding KEY_x = new KeyBinding("key.x", Keyboard.KEY_NUMPAD9, KEY_CATEGORY);
	private final KeyBinding KEY_x2 = new KeyBinding("key.x2", Keyboard.KEY_NUMPAD8, KEY_CATEGORY);
	private final KeyBinding KEY_y = new KeyBinding("key.y", Keyboard.KEY_NUMPAD6, KEY_CATEGORY);
	private final KeyBinding KEY_y2 = new KeyBinding("key.y2", Keyboard.KEY_NUMPAD5, KEY_CATEGORY);
	private final KeyBinding KEY_z = new KeyBinding("key.z", Keyboard.KEY_NUMPAD3, KEY_CATEGORY);
	private final KeyBinding KEY_z2 = new KeyBinding("key.z2", Keyboard.KEY_NUMPAD2, KEY_CATEGORY);
	private final KeyBinding KEY_f = new KeyBinding("key.f", Keyboard.KEY_NUMPAD0, KEY_CATEGORY);
	private final KeyBinding KEY_f2 = new KeyBinding("key.f2", Keyboard.KEY_NUMPAD4, KEY_CATEGORY);
	
	
	private Minecraft mc;
	
	
	public TickClientEvent( Minecraft mc) {
		this.mc = mc;
		ClientRegistry.registerKeyBinding(KEY_MAP);
		ClientRegistry.registerKeyBinding(KEY_ZOOM);
		ClientRegistry.registerKeyBinding(KEY_x);
		ClientRegistry.registerKeyBinding(KEY_x2);
		ClientRegistry.registerKeyBinding(KEY_y);
		ClientRegistry.registerKeyBinding(KEY_y2);
		ClientRegistry.registerKeyBinding(KEY_z);
		ClientRegistry.registerKeyBinding(KEY_z2);
		ClientRegistry.registerKeyBinding(KEY_f);
		ClientRegistry.registerKeyBinding(KEY_f2);
		
	}
	
	@SubscribeEvent
    public void onTick(ClientTickEvent evt) {
			
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
			
			if(KEY_f.getIsKeyPressed())
			{
				ClientProxy.GuiClientManager.f -= 0.01F;
				System.out.println(ClientProxy.GuiClientManager.x+"_"+ClientProxy.GuiClientManager.y+"_"+ClientProxy.GuiClientManager.z+"_"+ClientProxy.GuiClientManager.f);
				
			}
			if(KEY_f2.getIsKeyPressed())
			{
				ClientProxy.GuiClientManager.f -= 0.01F;
				System.out.println(ClientProxy.GuiClientManager.x+"_"+ClientProxy.GuiClientManager.y+"_"+ClientProxy.GuiClientManager.z+"_"+ClientProxy.GuiClientManager.f);
				
			}
			
			if(KEY_ZOOM.getIsKeyPressed())
			{
				zoomState = true;
				ClientProxy.EntityRenderer.setReachZoom(3D);
			}else if(zoomState)
			{
				zoomState = false;
				ClientProxy.EntityRenderer.setReachZoom(1D);
						
			}
		
			if(Minecraft.getMinecraft().ingameGUI instanceof GuiNeoInGame) {
				((GuiNeoInGame)Minecraft.getMinecraft().ingameGUI).renderMap = KEY_MAP.getIsKeyPressed();
			}
		
	}
	
	private boolean zoomState = false;
}
