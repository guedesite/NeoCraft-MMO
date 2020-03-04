package fr.neocraft.main.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import fr.neocraft.main.event.RenderEventClient;
import fr.neocraft.main.event.TickClientEvent;
import fr.neocraft.main.render.Block.RenderBlockBarrierRed;
import fr.neocraft.main.render.Block.RenderBlockDoorHouse;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {


	public static int RenderIdBarrierRed = 0,
					  RenderIdBlockDoorHouse = 0;
	
	public static RenderEventClient GuiClientManager = new RenderEventClient();
	
	@Override
	public void onInit() {
		
		FMLCommonHandler.instance().bus().register(new TickClientEvent());
		MinecraftForge.EVENT_BUS.register(GuiClientManager);
		
		
		RenderIdBarrierRed = RenderingRegistry.getNextAvailableRenderId();
	    RenderingRegistry.registerBlockHandler(RenderIdBarrierRed, new RenderBlockBarrierRed());
	    
	    RenderIdBlockDoorHouse = RenderingRegistry.getNextAvailableRenderId();
	    RenderingRegistry.registerBlockHandler(RenderIdBlockDoorHouse, new RenderBlockDoorHouse());
	}
	
}
