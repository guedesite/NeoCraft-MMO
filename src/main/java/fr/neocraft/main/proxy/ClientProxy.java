package fr.neocraft.main.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import fr.neocraft.main.entity.EntityPnjAction;
import fr.neocraft.main.event.RenderEventClient;
import fr.neocraft.main.render.Block.RenderBlockBarrierRed;
import fr.neocraft.main.render.Block.RenderBlockDoorHouse;
import fr.neocraft.main.render.Entity.NeoEntityRenderer;
import fr.neocraft.main.render.gui.internal.GuiNeoInGame;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {


	public static int RenderIdBarrierRed = 0,
					  RenderIdBlockDoorHouse = 0;
	
	public static GuiNeoInGame NeoInGame;
	

	
	public static RenderEventClient GuiClientManager = new RenderEventClient();
	
	@Override
	public void onInit() {
		Minecraft mc = Minecraft.getMinecraft();
		mc.entityRenderer = new NeoEntityRenderer(mc, mc.getResourceManager());
		mc.mcProfiler.profilingEnabled = false;
		
		EntityPnjAction.registerClientModel();
		
		MinecraftForge.EVENT_BUS.register(GuiClientManager);
		RenderIdBarrierRed = RenderingRegistry.getNextAvailableRenderId();
	    RenderingRegistry.registerBlockHandler(RenderIdBarrierRed, new RenderBlockBarrierRed());
	    
	    RenderIdBlockDoorHouse = RenderingRegistry.getNextAvailableRenderId();
	    RenderingRegistry.registerBlockHandler(RenderIdBlockDoorHouse, new RenderBlockDoorHouse());
	    
	//    MinecraftForgeClient.registerItemRenderer(ItemMod.NeoBow, new BowRender());

	    
	}
	
}
