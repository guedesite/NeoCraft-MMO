package fr.neocraft.main.event;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import fr.neocraft.main.render.gui.external.CarteGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class TickClientEvent {
	
	private final String KEY_CATEGORY = "key.neocraft";
	private final KeyBinding KEY_MAP = new KeyBinding("key.map", Keyboard.KEY_M, KEY_CATEGORY);
	
	public TickClientEvent() {
		ClientRegistry.registerKeyBinding(KEY_MAP);
	}
	
	@SubscribeEvent
    public void onTick(ClientTickEvent evt) {
		if(KEY_MAP.isPressed())
		{
			Minecraft.getMinecraft().displayGuiScreen(new CarteGui());
		}
	}
}
